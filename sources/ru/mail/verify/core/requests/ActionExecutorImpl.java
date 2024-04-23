package ru.mail.verify.core.requests;

import android.os.Message;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import dagger.Lazy;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import javax.inject.Inject;
import ru.mail.libverify.platform.storage.KeyValueStorage;
import ru.mail.verify.core.api.ApiManager;
import ru.mail.verify.core.api.NetworkManager;
import ru.mail.verify.core.platform.TimeProvider;
import ru.mail.verify.core.requests.FutureWrapper;
import ru.mail.verify.core.requests.response.ResponseBase;
import ru.mail.verify.core.storage.LockManager;
import ru.mail.verify.core.utils.ClientException;
import ru.mail.verify.core.utils.DebugUtils;
import ru.mail.verify.core.utils.FileLog;
import ru.mail.verify.core.utils.ServerException;
import ru.mail.verify.core.utils.components.BusMessageType;
import ru.mail.verify.core.utils.components.MessageBus;
import ru.mail.verify.core.utils.components.MessageBusUtils;
import ru.mail.verify.core.utils.components.MessageHandler;
import ru.mail.verify.core.utils.json.JsonParseException;
import ru.mail.verify.core.utils.json.JsonParser;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/requests/ActionExecutorImpl.class */
public class ActionExecutorImpl implements ActionExecutor, MessageHandler {
    private HashMap a;
    private long b = 0;
    private final ApiManager c;
    private final NetworkManager d;
    private final KeyValueStorage e;
    private final MessageBus f;
    private final LockManager g;
    private final Lazy<ActionFactory> h;
    private TimeProvider i;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/requests/ActionExecutorImpl$a.class */
    public class a implements Runnable {
        a() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            ActionExecutorImpl.this.a(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/requests/ActionExecutorImpl$b.class */
    public class b implements Runnable {
        final /* synthetic */ f a;

        b(f fVar) {
            this.a = fVar;
        }

        @Override // java.lang.Runnable
        public final void run() {
            if (ActionExecutorImpl.this.a(this.a, false)) {
                ActionExecutorImpl.this.b();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/requests/ActionExecutorImpl$c.class */
    public class c implements FutureWrapper.FutureListener<ResponseBase> {
        final /* synthetic */ f a;

        c(f fVar) {
            this.a = fVar;
        }

        @Override // ru.mail.verify.core.requests.FutureWrapper.FutureListener
        public final void onComplete(Future<ResponseBase> future) {
            if (future.isCancelled()) {
                FileLog.v("ActionExecutor", "Future from action %s has been cancelled before", Integer.valueOf(this.a.c.hashCode()));
                return;
            }
            try {
                ResponseBase responseBase = future.get();
                Object[] objArr = new Object[1];
                objArr[0] = Integer.valueOf(this.a.c.hashCode());
                FileLog.v("ActionExecutor", "Action %s completed", objArr);
                ActionExecutorImpl.this.f.post(MessageBusUtils.createOneArg(BusMessageType.SERVER_ACTION_RESULT, responseBase));
                ActionExecutorImpl.this.a(this.a);
            } catch (ExecutionException e) {
                e = e;
                Throwable cause = e.getCause();
                if (cause != null) {
                    boolean z = cause instanceof ServerException;
                    boolean z2 = cause instanceof IOException;
                    if (!z && !z2) {
                        boolean z3 = false;
                        if (cause instanceof ClientException) {
                            ClientException clientException = (ClientException) cause;
                            if (clientException.getReason() == ClientException.ClientReason.REJECTED_BY_POLICY || clientException.getReason() == ClientException.ClientReason.REJECTED_BY_INTERCEPTOR_ERROR) {
                                z3 = true;
                            }
                        }
                        if (z3) {
                            FileLog.d("ActionExecutor", e, "Action %s rejected by an application", Integer.valueOf(this.a.c.hashCode()));
                        } else {
                            ActionExecutorImpl.this.a(this.a, e);
                        }
                        ActionExecutorImpl.this.a(this.a);
                        return;
                    }
                    f fVar = this.a;
                    fVar.d = null;
                    fVar.e = cause;
                    ActionExecutorImpl.this.a(false);
                    if (z) {
                        FileLog.e("ActionExecutor", e, "Action %s failed by server", Integer.valueOf(this.a.c.hashCode()));
                    } else {
                        FileLog.d("ActionExecutor", e, "Action %s failed by network", Integer.valueOf(this.a.c.hashCode()));
                    }
                    ActionExecutorImpl actionExecutorImpl = ActionExecutorImpl.this;
                    f fVar2 = this.a;
                    actionExecutorImpl.f.post(MessageBusUtils.createMultipleArgs(BusMessageType.SERVER_ACTION_FAILURE, fVar2.b, cause, Boolean.TRUE));
                    if (z2) {
                        return;
                    }
                    FileLog.e("ActionExecutor", cause, "Action %s recoverable error", Integer.valueOf(fVar2.c.hashCode()));
                    DebugUtils.safeThrow("ActionExecutor", cause, "Action recoverable error", new Object[0]);
                    return;
                }
                ActionExecutorImpl.this.a(this.a, e);
                ActionExecutorImpl.this.a(this.a);
            } catch (Throwable th) {
                e = th;
                ActionExecutorImpl.this.a(this.a, e);
                ActionExecutorImpl.this.a(this.a);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/requests/ActionExecutorImpl$d.class */
    public class d implements Comparator<ActionDescriptor> {
        d() {
        }

        @Override // java.util.Comparator
        public final int compare(ActionDescriptor actionDescriptor, ActionDescriptor actionDescriptor2) {
            return ru.mail.verify.core.utils.Utils.compareLong(actionDescriptor.createdTimestamp, actionDescriptor2.createdTimestamp);
        }
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/requests/ActionExecutorImpl$e.class */
    static /* synthetic */ class e {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[BusMessageType.values().length];
            a = iArr;
            try {
                iArr[BusMessageType.NETWORK_STATE_CHANGED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[BusMessageType.API_RESET.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/requests/ActionExecutorImpl$f.class */
    public static class f {
        final ActionDescriptor a;
        final RequestBase b;
        final String c;
        Future d;
        Throwable e;

        f(@NonNull ActionFactory actionFactory, @NonNull ActionDescriptor actionDescriptor) throws UnsupportedEncodingException, NoSuchAlgorithmException, MalformedURLException, JsonParseException {
            this.a = actionDescriptor;
            RequestBase createRequest = actionFactory.createRequest(actionDescriptor);
            if (createRequest == null) {
                throw new IllegalArgumentException("Request must have supported type");
            }
            this.b = createRequest;
            this.c = createRequest.getId();
        }

        f(@NonNull ActionFactory actionFactory, @NonNull RequestBase requestBase) throws JsonParseException {
            ActionDescriptor createDescriptor = actionFactory.createDescriptor(requestBase);
            if (createDescriptor == null) {
                throw new IllegalArgumentException("Request must have supported type");
            }
            this.a = createDescriptor;
            this.b = requestBase;
            this.c = requestBase.getId();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Inject
    public ActionExecutorImpl(@NonNull ApiManager apiManager, @NonNull NetworkManager networkManager, @NonNull KeyValueStorage keyValueStorage, @NonNull MessageBus messageBus, @NonNull LockManager lockManager, @NonNull Lazy<ActionFactory> lazy, @NonNull TimeProvider timeProvider) {
        this.c = apiManager;
        this.d = networkManager;
        this.e = keyValueStorage;
        this.f = messageBus;
        this.g = lockManager;
        this.h = lazy;
        this.i = timeProvider;
    }

    private void a(boolean z) {
        a();
        boolean z2 = false;
        Iterator it = new ArrayList(this.a.values()).iterator();
        while (it.hasNext()) {
            z2 |= a((f) it.next(), z);
        }
        if (z2) {
            b();
        }
    }

    private void b() {
        KeyValueStorage putValue;
        ArrayList arrayList = new ArrayList();
        for (f fVar : this.a.values()) {
            ActionDescriptor actionDescriptor = fVar.a;
            if (actionDescriptor.attemptCount <= 10) {
                arrayList.add(actionDescriptor);
            }
        }
        try {
            if (arrayList.isEmpty()) {
                putValue = this.e.removeValue("serializable_actions_data");
            } else {
                putValue = this.e.putValue("serializable_actions_data", JsonParser.toJson(arrayList));
            }
            putValue.commit();
        } catch (JsonParseException e2) {
            DebugUtils.safeThrow("ActionExecutor", "failed to save actions", e2);
        }
    }

    @Override // ru.mail.verify.core.requests.ActionExecutor
    @NonNull
    public String execute(@NonNull RequestBase request) throws UnsupportedEncodingException, NoSuchAlgorithmException, IllegalArgumentException, MalformedURLException, JsonParseException {
        return execute(request, 0);
    }

    @Override // ru.mail.verify.core.requests.ActionExecutor
    public void remove(@NonNull String actionId) {
        a();
        f fVar = (f) this.a.get(actionId);
        if (fVar == null) {
            return;
        }
        a(fVar);
    }

    @Override // ru.mail.verify.core.api.ApiPlugin
    public void initialize() {
        this.f.register(Arrays.asList(BusMessageType.NETWORK_STATE_CHANGED, BusMessageType.API_RESET), this);
        a(false);
    }

    @Override // ru.mail.verify.core.utils.components.MessageHandler
    public boolean handleMessage(@NonNull Message msg) {
        int i = e.a[MessageBusUtils.getType(msg, "ActionExecutor").ordinal()];
        if (i == 1) {
            if (((Boolean) MessageBusUtils.getArg(msg, Boolean.class)).booleanValue()) {
                long nanoTime = System.nanoTime();
                long j = this.b;
                long j2 = (nanoTime - j) / 1000000;
                if (j == 0 || j2 > 10000 || j2 < 0) {
                    a(true);
                } else {
                    FileLog.v("ActionExecutor", "Skip connection change by timeout (diff: %d)", Long.valueOf(j2));
                }
                this.b = nanoTime;
                return true;
            }
            return true;
        } else if (i != 2) {
            return false;
        } else {
            HashMap hashMap = this.a;
            if (hashMap == null) {
                return true;
            }
            for (f fVar : hashMap.values()) {
                Future future = fVar.d;
                if (future != null) {
                    future.cancel(true);
                    fVar.d = null;
                    this.g.releaseLock(fVar);
                    this.f.post(MessageBusUtils.createOneArg(BusMessageType.SERVER_ACTION_REMOVED, fVar.b));
                }
            }
            this.a.clear();
            b();
            return true;
        }
    }

    /* JADX WARN: Not initialized variable reg: 4, insn: ?: MOVE  (r1 I:??) = (r4 I:??), block:B:6:0x002e */
    /* JADX WARN: Type inference failed for: r3v12, types: [long, java.lang.Object[]] */
    private boolean a(@NonNull f fVar, boolean z) {
        long j;
        ?? r3;
        if (fVar.a.attemptCount > 10) {
            FileLog.d("ActionExecutor", "Action %s dropped by max attempt count", Integer.valueOf(fVar.c.hashCode()));
            a(fVar);
            return false;
        } else if (this.i.getLocalTime() - fVar.a.lastAttemptTimestamp < 0) {
            FileLog.d("ActionExecutor", "Action %s dropped by wrong timestamp", Integer.valueOf(fVar.c.hashCode()));
            a(fVar);
            return false;
        } else {
            long localTime = this.i.getLocalTime();
            ActionDescriptor actionDescriptor = fVar.a;
            long j2 = localTime - actionDescriptor.createdTimestamp;
            int i = actionDescriptor.actionTimeout;
            if (i > 0 && i < j2) {
                FileLog.d("ActionExecutor", "Action %s dropped by total action timeout", Integer.valueOf(fVar.c.hashCode()));
                a(fVar);
                return false;
            }
            if (fVar.d != null) {
                if (!z) {
                    return false;
                }
                r3 = new Object[]{Integer.valueOf(fVar.c.hashCode())};
                FileLog.v("ActionExecutor", "Action %s cancelled", (Object[]) r3);
                fVar.d.cancel(true);
                fVar.d = null;
                fVar.a.attemptCount = 0;
            }
            Throwable th = fVar.e;
            long j3 = th != null && (th instanceof ServerException) ? 10000L : 5000L;
            int i2 = fVar.a.attemptCount;
            if (j > j3 * i2 * i2) {
                FileLog.v("ActionExecutor", "Action %s will be started now as timeout %d ms passed", Integer.valueOf(fVar.c.hashCode()), Long.valueOf((long) r3));
                return b(fVar);
            }
            long j4 = r3 - j;
            if (i > 0) {
                j4 = Math.min(i - j2, j4);
            }
            if (j4 < 0) {
                j4 = 0;
            }
            FileLog.v("ActionExecutor", "Action %s will be started after %d ms", Integer.valueOf(fVar.c.hashCode()), Long.valueOf(j4));
            this.c.getDispatcher().postDelayed(new b(fVar), j4);
            return false;
        }
    }

    private boolean b(@NonNull f fVar) {
        boolean z = fVar.b.canRunOffline() && fVar.a.attemptCount == 0;
        if (!this.d.hasNetwork() && !z) {
            FileLog.v("ActionExecutor", "Action %s initialize delayed", Integer.valueOf(fVar.c.hashCode()));
            return false;
        }
        FileLog.v("ActionExecutor", "Start action %s (last start diff: %d, attempt: %d, last error: %s)", Integer.valueOf(fVar.c.hashCode()), Long.valueOf(fVar.a.lastAttemptTimestamp == 0 ? 0L : this.i.getLocalTime() - fVar.a.lastAttemptTimestamp), Integer.valueOf(fVar.a.attemptCount), fVar.e);
        ActionDescriptor actionDescriptor = fVar.a;
        actionDescriptor.attemptCount++;
        actionDescriptor.lastAttemptTimestamp = this.i.getLocalTime();
        fVar.d = fVar.b.executeAsync(this.c.getBackgroundWorker(), this.c.getDispatcher(), new c(fVar));
        FileLog.v("ActionExecutor", "Action id %s url %s started (attemptCount %d)", Integer.valueOf(fVar.c.hashCode()), fVar.c, Integer.valueOf(fVar.a.attemptCount));
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x0051, code lost:
        if (r9.a.containsKey(r1.c) == false) goto L17;
     */
    @Override // ru.mail.verify.core.requests.ActionExecutor
    @androidx.annotation.NonNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String execute(@androidx.annotation.NonNull ru.mail.verify.core.requests.RequestBase r10, int r11) throws java.io.UnsupportedEncodingException, java.security.NoSuchAlgorithmException, java.lang.IllegalArgumentException, java.net.MalformedURLException, ru.mail.verify.core.utils.json.JsonParseException {
        /*
            r9 = this;
            r0 = r9
            r1 = r0
            r1.a()
            ru.mail.verify.core.requests.ActionExecutorImpl$f r1 = new ru.mail.verify.core.requests.ActionExecutorImpl$f
            r2 = r1
            r3 = r2
            r12 = r3
            r3 = r9
            dagger.Lazy<ru.mail.verify.core.requests.ActionFactory> r3 = r3.h
            java.lang.Object r3 = r3.get()
            ru.mail.verify.core.requests.ActionFactory r3 = (ru.mail.verify.core.requests.ActionFactory) r3
            r4 = r10
            r2.<init>(r3, r4)
            ru.mail.verify.core.requests.ActionDescriptor r1 = r1.a
            r2 = r11
            r1.actionTimeout = r2
            java.util.HashMap r0 = r0.a
            r1 = r12
            java.lang.String r1 = r1.c
            java.lang.Object r0 = r0.get(r1)
            ru.mail.verify.core.requests.ActionExecutorImpl$f r0 = (ru.mail.verify.core.requests.ActionExecutorImpl.f) r0
            r1 = r0
            r10 = r1
            r1 = 0
            r11 = r1
            if (r0 != 0) goto L39
            goto L54
        L39:
            r0 = r9
            r1 = r10
            r2 = 0
            boolean r0 = r0.a(r1, r2)
            if (r0 == 0) goto L46
            r0 = r9
            r0.b()
        L46:
            r0 = r9
            java.util.HashMap r0 = r0.a
            r1 = r12
            java.lang.String r1 = r1.c
            boolean r0 = r0.containsKey(r1)
            if (r0 != 0) goto L56
        L54:
            r0 = 1
            r11 = r0
        L56:
            r0 = r11
            if (r0 == 0) goto Lb2
            r0 = r9
            r1 = r12
            r2 = r9
            r3 = r2
            r4 = r2; r5 = r3; 
            java.util.HashMap r5 = r5.a
            r6 = r12
            java.lang.String r6 = r6.c
            r7 = r12
            java.lang.Object r5 = r5.put(r6, r7)
            ru.mail.verify.core.storage.LockManager r4 = r4.g
            r5 = r12
            r6 = r5
            ru.mail.verify.core.requests.RequestBase r6 = r6.b
            boolean r6 = r6.keepSystemLock()
            r7 = 1
            r4.acquireLock(r5, r6, r7)
            ru.mail.verify.core.utils.components.MessageBus r3 = r3.f
            ru.mail.verify.core.utils.components.BusMessageType r4 = ru.mail.verify.core.utils.components.BusMessageType.SERVER_ACTION_ADDED
            r5 = r12
            ru.mail.verify.core.requests.RequestBase r5 = r5.b
            android.os.Message r4 = ru.mail.verify.core.utils.components.MessageBusUtils.createOneArg(r4, r5)
            r3.post(r4)
            r2.b()
            boolean r0 = r0.b(r1)
            if (r0 != 0) goto Lce
            r0 = r9
            ru.mail.verify.core.api.ApiManager r0 = r0.c
            ru.mail.verify.core.utils.components.CustomHandler r0 = r0.getDispatcher()
            ru.mail.verify.core.requests.ActionExecutorImpl$a r1 = new ru.mail.verify.core.requests.ActionExecutorImpl$a
            r2 = r1
            r3 = r9
            r2.<init>()
            r2 = 5000(0x1388, double:2.4703E-320)
            boolean r0 = r0.postDelayed(r1, r2)
            goto Lce
        Lb2:
            r0 = 1
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r1 = r0
            r9 = r1
            r1 = r12
            java.lang.String r1 = r1.c
            int r1 = r1.hashCode()
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
            r2 = 0
            r3 = r1; r1 = r2; r2 = r3; 
            r0[r1] = r2
            java.lang.String r0 = "ActionExecutor"
            java.lang.String r1 = "request %s dropped as a duplicate"
            r2 = r9
            ru.mail.verify.core.utils.FileLog.v(r0, r1, r2)
        Lce:
            r0 = r12
            java.lang.String r0 = r0.c
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: ru.mail.verify.core.requests.ActionExecutorImpl.execute(ru.mail.verify.core.requests.RequestBase, int):java.lang.String");
    }

    private void a() {
        if (this.a != null) {
            return;
        }
        this.a = new HashMap();
        String value = this.e.getValue("serializable_actions_data");
        if (TextUtils.isEmpty(value)) {
            return;
        }
        try {
            List<ActionDescriptor> listFromJson = JsonParser.listFromJson(value, ActionDescriptor.class);
            Collections.sort(listFromJson, new d());
            for (ActionDescriptor actionDescriptor : listFromJson) {
                f fVar = new f((ActionFactory) this.h.get(), actionDescriptor);
                this.a.put(fVar.c, fVar);
                this.g.acquireLock(fVar, false, 1);
                this.f.post(MessageBusUtils.createOneArg(BusMessageType.SERVER_ACTION_ADDED, fVar.b));
            }
        } catch (Throwable th) {
            DebugUtils.safeThrow("ActionExecutor", "Failed to read saved items", th);
            HashMap hashMap = this.a;
            if (hashMap == null) {
                return;
            }
            for (f fVar2 : hashMap.values()) {
                Future future = fVar2.d;
                if (future != null) {
                    future.cancel(true);
                    fVar2.d = null;
                    this.g.releaseLock(fVar2);
                    this.f.post(MessageBusUtils.createOneArg(BusMessageType.SERVER_ACTION_REMOVED, fVar2.b));
                }
            }
            this.a.clear();
            b();
        }
    }

    private void a(@NonNull f fVar, @NonNull Throwable th) {
        this.f.post(MessageBusUtils.createMultipleArgs(BusMessageType.SERVER_ACTION_FAILURE, fVar.b, th, Boolean.FALSE));
        FileLog.e("ActionExecutor", "Action %s failed", Integer.valueOf(fVar.c.hashCode()));
        DebugUtils.safeThrow("ActionExecutor", th, "Action failed", new Object[0]);
    }

    private void a(@NonNull f fVar) {
        this.a.remove(fVar.c);
        this.g.releaseLock(fVar);
        this.f.post(MessageBusUtils.createOneArg(BusMessageType.SERVER_ACTION_REMOVED, fVar.b));
        b();
    }
}
