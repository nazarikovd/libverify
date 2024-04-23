package ru.mail.libverify.api;

import android.os.Message;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import ru.mail.libverify.api.VerificationApi;
import ru.mail.libverify.api.q;
import ru.mail.libverify.platform.storage.KeyValueStorage;
import ru.mail.verify.core.platform.TimeProvider;
import ru.mail.verify.core.storage.BroadcastManager;
import ru.mail.verify.core.utils.DebugUtils;
import ru.mail.verify.core.utils.FileLog;
import ru.mail.verify.core.utils.Utils;
import ru.mail.verify.core.utils.components.BusMessageType;
import ru.mail.verify.core.utils.components.MessageBus;
import ru.mail.verify.core.utils.components.MessageBusUtils;
import ru.mail.verify.core.utils.json.JsonParser;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/n.class */
public final class n {
    private volatile ConcurrentHashMap<String, x> a;
    private final CommonContext f;
    private final MessageBus g;
    private final p h;
    @NonNull
    private final TimeProvider j;
    private volatile ConcurrentHashMap<String, x> b = new ConcurrentHashMap<>();
    private final LinkedList<x> c = new LinkedList<>();
    private final ConcurrentHashMap<String, x> d = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, x> e = new ConcurrentHashMap<>();
    private final Runnable i = new a();

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/n$a.class */
    class a implements Runnable {
        a() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            n.this.e();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/n$b.class */
    public static /* synthetic */ class b {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[c.values().length];
            a = iArr;
            try {
                try {
                    try {
                        iArr[c.ALL.ordinal()] = 1;
                    } catch (NoSuchFieldError unused) {
                    }
                } catch (NoSuchFieldError unused2) {
                }
            } catch (NoSuchFieldError unused3) {
            }
            try {
                try {
                    try {
                        try {
                            a[c.ALL_HASHED.ordinal()] = 2;
                        } catch (NoSuchFieldError unused4) {
                        }
                    } catch (NoSuchFieldError unused5) {
                    }
                } catch (NoSuchFieldError unused6) {
                }
            } catch (NoSuchFieldError unused7) {
            }
            try {
                try {
                    try {
                        try {
                            a[c.TEMPORARY.ordinal()] = 3;
                        } catch (NoSuchFieldError unused8) {
                        }
                    } catch (NoSuchFieldError unused9) {
                    }
                } catch (NoSuchFieldError unused10) {
                }
            } catch (NoSuchFieldError unused11) {
            }
            try {
                try {
                    try {
                        try {
                            a[c.NOT_TEMPORARY.ordinal()] = 4;
                        } catch (NoSuchFieldError unused12) {
                        }
                    } catch (NoSuchFieldError unused13) {
                    }
                } catch (NoSuchFieldError unused14) {
                }
            } catch (NoSuchFieldError unused15) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/n$c.class */
    public enum c {
        ALL,
        ALL_HASHED,
        TEMPORARY,
        NOT_TEMPORARY
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public n(@NonNull CommonContext commonContext, @NonNull q.i iVar) {
        this.f = commonContext;
        q.f fVar = (q.f) commonContext;
        this.g = fVar.getBus();
        this.h = iVar;
        this.j = fVar.getConfig().getTimeProvider();
    }

    private void e() {
        KeyValueStorage putValue;
        if (this.a == null) {
            return;
        }
        try {
            if (this.a.isEmpty()) {
                putValue = this.f.getSettings().putValue("api_verification_sessions_data", "");
            } else {
                ArrayList arrayList = new ArrayList();
                for (x xVar : this.a.values()) {
                    arrayList.add(xVar.h());
                }
                putValue = this.f.getSettings().putValue("api_verification_sessions_data", JsonParser.toJson(arrayList));
            }
            putValue.commit();
        } catch (Exception e) {
            DebugUtils.safeThrow("SessionContainer", "Failed to save sessions", e);
        }
        Iterator<x> it = this.c.iterator();
        while (it.hasNext()) {
            x next = it.next();
            if (!this.d.containsKey(next.e())) {
                this.g.post(MessageBusUtils.createOneArg(BusMessageType.SESSION_CONTAINER_REMOVED_SESSION, next));
            }
        }
        this.c.clear();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v2, types: [ru.mail.libverify.api.n] */
    /* JADX WARN: Type inference failed for: r0v3, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v5 */
    private void d() {
        MessageBus messageBus;
        Message createOneArg;
        if (this.a == null) {
            ?? r0 = this;
            synchronized (r0) {
                if (r0.a == null) {
                    this.a = new ConcurrentHashMap<>();
                    this.b.clear();
                    String value = this.f.getSettings().getValue("api_verification_sessions_data");
                    if (!TextUtils.isEmpty(value)) {
                        for (String str : JsonParser.listFromJson(value, String.class)) {
                            if (!TextUtils.isEmpty(str)) {
                                x a2 = ((q.i) this.h).a(str);
                                long localTime = this.j.getLocalTime() - a2.l();
                                if (localTime < 0 || localTime > 43200000) {
                                    messageBus = this.g;
                                    createOneArg = MessageBusUtils.createOneArg(BusMessageType.SESSION_CONTAINER_REMOVED_SESSION, a2);
                                } else {
                                    this.a.put(a2.e(), a2);
                                    this.b.put(Utils.stringToSHA256(a2.e()), a2);
                                    messageBus = this.g;
                                    createOneArg = MessageBusUtils.createOneArg(BusMessageType.SESSION_CONTAINER_ADDED_SESSION, a2);
                                }
                                messageBus.post(createOneArg);
                            }
                        }
                    }
                }
                r0 = this;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean c() {
        d();
        return this.a.isEmpty() && this.d.isEmpty();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final int f() {
        d();
        return this.d.size() + this.a.size();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final x b(@NonNull String str) {
        return a(str, c.ALL);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void a() {
        if (this.a == null) {
            return;
        }
        for (x xVar : this.a.values()) {
            xVar.a();
        }
        this.d.clear();
        this.e.clear();
        this.c.clear();
        this.c.addAll(this.a.values());
        this.a.clear();
        this.b.clear();
        e();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0085 A[ORIG_RETURN, RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean a(@androidx.annotation.NonNull java.lang.String r4) {
        /*
            r3 = this;
            r0 = r4
            ru.mail.libverify.api.n$c r1 = ru.mail.libverify.api.n.c.ALL
            r5 = r1
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 == 0) goto Lf
        Lb:
            r0 = 0
            goto L86
        Lf:
            r0 = r3
            r0.d()
            int[] r0 = ru.mail.libverify.api.n.b.a
            r1 = r5
            int r1 = r1.ordinal()
            r0 = r0[r1]
            switch(r0) {
                case 1: goto L6f;
                case 2: goto L56;
                case 3: goto L4b;
                case 4: goto L40;
                default: goto L38;
            }
        L38:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            r1 = r0
            r1.<init>()
            throw r0
        L40:
            r0 = r3
            java.util.concurrent.ConcurrentHashMap<java.lang.String, ru.mail.libverify.api.x> r0 = r0.a
            r1 = r4
            boolean r0 = r0.containsKey(r1)
            goto L86
        L4b:
            r0 = r3
            java.util.concurrent.ConcurrentHashMap<java.lang.String, ru.mail.libverify.api.x> r0 = r0.d
            r1 = r4
            boolean r0 = r0.containsKey(r1)
            goto L86
        L56:
            r0 = r3
            java.util.concurrent.ConcurrentHashMap<java.lang.String, ru.mail.libverify.api.x> r0 = r0.b
            r1 = r4
            boolean r0 = r0.containsKey(r1)
            if (r0 != 0) goto L85
            r0 = r3
            java.util.concurrent.ConcurrentHashMap<java.lang.String, ru.mail.libverify.api.x> r0 = r0.e
            r1 = r4
            boolean r0 = r0.containsKey(r1)
            if (r0 == 0) goto Lb
            goto L85
        L6f:
            r0 = r3
            java.util.concurrent.ConcurrentHashMap<java.lang.String, ru.mail.libverify.api.x> r0 = r0.a
            r1 = r4
            boolean r0 = r0.containsKey(r1)
            if (r0 != 0) goto L85
            r0 = r3
            java.util.concurrent.ConcurrentHashMap<java.lang.String, ru.mail.libverify.api.x> r0 = r0.d
            r1 = r4
            boolean r0 = r0.containsKey(r1)
            if (r0 == 0) goto Lb
        L85:
            r0 = 1
        L86:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: ru.mail.libverify.api.n.a(java.lang.String):boolean");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean e(@NonNull String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        d();
        boolean containsKey = this.a.containsKey(str);
        if (containsKey) {
            FileLog.v("SessionContainer", "session with id = %s touched", str);
            this.f.getDispatcher().removeCallbacks(this.i);
            this.f.getDispatcher().postDelayed(this.i, 300L);
        }
        return containsKey;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void c(@NonNull String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        d();
        String stringToSHA256 = Utils.stringToSHA256(str);
        x remove = this.a.remove(str);
        this.b.remove(stringToSHA256);
        if (remove != null) {
            FileLog.v("SessionContainer", "session with id = %s marked as temporary", str);
            this.d.put(str, remove);
            this.e.put(stringToSHA256, remove);
            this.c.add(remove);
            this.f.getDispatcher().removeCallbacks(this.i);
            this.f.getDispatcher().postDelayed(this.i, 300L);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean b() {
        d();
        Iterator it = new ArrayList(this.a.values()).iterator();
        while (it.hasNext()) {
            if (((x) it.next()).k().getState() != VerificationApi.VerificationState.FINAL) {
                return true;
            }
        }
        Iterator it2 = new ArrayList(this.d.values()).iterator();
        while (it2.hasNext()) {
            if (((x) it2.next()).k().getState() != VerificationApi.VerificationState.FINAL) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final x a(@NonNull String str, c cVar) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        d();
        switch (b.a[cVar.ordinal()]) {
            case BroadcastManager.FLAG_NETWORK_RECEIVER /* 1 */:
                x xVar = this.a.get(str);
                x xVar2 = xVar;
                if (xVar == null) {
                    xVar2 = this.d.get(str);
                }
                return xVar2;
            case 2:
                x xVar3 = this.b.get(str);
                x xVar4 = xVar3;
                if (xVar3 == null) {
                    xVar4 = this.e.get(str);
                }
                return xVar4;
            case 3:
                return this.d.get(str);
            case 4:
                return this.a.get(str);
            default:
                throw new IllegalArgumentException();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final x d(@NonNull String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        d();
        String stringToSHA256 = Utils.stringToSHA256(str);
        x remove = this.a.remove(str);
        x xVar = remove;
        this.b.remove(stringToSHA256);
        if (remove == null) {
            xVar = this.d.remove(str);
            this.e.remove(stringToSHA256);
        }
        if (xVar != null) {
            FileLog.v("SessionContainer", "session with id = %s removed", str);
            this.c.add(xVar);
            this.f.getDispatcher().removeCallbacks(this.i);
            this.f.getDispatcher().postDelayed(this.i, 300L);
        }
        return xVar;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final Collection<x> b(c cVar) {
        d();
        switch (b.a[cVar.ordinal()]) {
            case BroadcastManager.FLAG_NETWORK_RECEIVER /* 1 */:
            case 2:
                ArrayList arrayList = new ArrayList(this.a.values());
                arrayList.addAll(this.d.values());
                return arrayList;
            case 3:
                return this.d.values();
            case 4:
                return this.a.values();
            default:
                throw new IllegalArgumentException();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final ArrayList a(c cVar) {
        ArrayList<x> arrayList;
        d();
        ArrayList arrayList2 = new ArrayList();
        switch (b.a[cVar.ordinal()]) {
            case BroadcastManager.FLAG_NETWORK_RECEIVER /* 1 */:
            case 2:
                arrayList = new ArrayList(this.a.values());
                arrayList.addAll(this.d.values());
                break;
            case 3:
                arrayList = r0;
                ArrayList arrayList3 = new ArrayList(this.d.values());
                break;
            case 4:
                arrayList = r0;
                ArrayList arrayList4 = new ArrayList(this.a.values());
                break;
            default:
                throw new IllegalArgumentException();
        }
        for (x xVar : arrayList) {
            arrayList2.add(xVar.e());
        }
        return arrayList2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void a(@NonNull String str, @NonNull x xVar) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        d();
        x put = this.a.put(str, xVar);
        this.b.put(Utils.stringToSHA256(str), xVar);
        if (put == null) {
            FileLog.v("SessionContainer", "session with id = %s added", str);
            this.g.post(MessageBusUtils.createOneArg(BusMessageType.SESSION_CONTAINER_ADDED_SESSION, xVar));
            this.f.getDispatcher().removeCallbacks(this.i);
            this.f.getDispatcher().postDelayed(this.i, 300L);
        }
    }
}
