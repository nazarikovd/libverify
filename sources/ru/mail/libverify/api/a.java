package ru.mail.libverify.api;

import android.os.Message;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Future;
import java.util.regex.Pattern;
import ru.mail.libverify.api.VerificationApi;
import ru.mail.libverify.api.g;
import ru.mail.libverify.api.q;
import ru.mail.verify.core.api.ApiPlugin;
import ru.mail.verify.core.platform.TimeProvider;
import ru.mail.verify.core.storage.BroadcastManager;
import ru.mail.verify.core.utils.DebugUtils;
import ru.mail.verify.core.utils.FileLog;
import ru.mail.verify.core.utils.components.BusMessageType;
import ru.mail.verify.core.utils.components.MessageBus;
import ru.mail.verify.core.utils.components.MessageBusUtils;
import ru.mail.verify.core.utils.components.MessageHandler;
import ru.mail.verify.core.utils.json.JsonParseException;
import ru.mail.verify.core.utils.json.JsonParser;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/a.class */
public final class a implements MessageHandler, ApiPlugin {
    private static final Pattern j = Pattern.compile("^.*(\\d{4,}).*$");
    private final CommonContext a;
    private final MessageBus b;
    private volatile ru.mail.libverify.j.l c;
    private volatile List<ru.mail.libverify.k.k> d;
    private VerificationApi.AccountCheckListener e;
    private String f;
    private HashMap g;
    private Future h;
    private TimeProvider i;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: ru.mail.libverify.api.a$a  reason: collision with other inner class name */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/a$a.class */
    public class RunnableC0000a implements Runnable {
        RunnableC0000a() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            ru.mail.libverify.j.l lVar = a.this.c;
            if (!((lVar == null || lVar.c() == null || lVar.c().length == 0 || lVar.d() == null || lVar.d().isEmpty()) ? false : true)) {
                a.this.b.post(MessageBusUtils.createOneArg(BusMessageType.ACCOUNT_CHECKER_NO_SMS_INFO_INTERNAL, (Object) null));
                return;
            }
            a.this.b.post(MessageBusUtils.createOneArg(BusMessageType.ACCOUNT_CHECKER_SMS_PARSING_STARTED, (Object) null));
            ru.mail.libverify.k.c knownSmsFinder = a.this.a.getConfig().getKnownSmsFinder();
            c cVar = new c();
            g.a aVar = new g.a();
            aVar.a = lVar.c();
            try {
                cVar.c = ((ru.mail.libverify.k.d) knownSmsFinder).a(new ru.mail.libverify.api.c(cVar, aVar, lVar));
                a.this.b.post(MessageBusUtils.createOneArg(BusMessageType.ACCOUNT_CHECKER_SMS_SEARCH_COMPLETED_INTERNAL, cVar));
            } catch (InterruptedException e) {
                FileLog.d("AccountChecker", "query user sms messages interrupted", e);
            } catch (Throwable th) {
                FileLog.e("AccountChecker", "failed to query user sms messages", th);
                a.this.b.post(MessageBusUtils.createOneArg(BusMessageType.ACCOUNT_CHECKER_GENERAL_ERROR_INTERNAL, (Object) null));
            }
        }
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/a$b.class */
    static /* synthetic */ class b {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[BusMessageType.values().length];
            a = iArr;
            try {
                iArr[BusMessageType.ACCOUNT_CHECKER_NO_SMS_INFO_INTERNAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[BusMessageType.ACCOUNT_CHECKER_SMS_SEARCH_COMPLETED_INTERNAL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[BusMessageType.ACCOUNT_CHECKER_GENERAL_ERROR_INTERNAL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                a[BusMessageType.ACCOUNT_CHECKER_MAX_SMS_INFO_WAIT_TIMEOUT_INTERNAL.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                a[BusMessageType.VERIFY_API_RESET.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                a[BusMessageType.API_RESET.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/a$c.class */
    public static class c {
        boolean a = false;
        boolean b = false;
        List<ru.mail.libverify.k.k> c = null;

        private c() {
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(@NonNull CommonContext commonContext) {
        this.a = commonContext;
        q.f fVar = (q.f) commonContext;
        this.b = fVar.getBus();
        this.i = fVar.getConfig().getTimeProvider();
    }

    private void b() {
        if (TextUtils.isEmpty(this.f)) {
            FileLog.d("AccountChecker", "no application json");
        } else if (this.d != null) {
            FileLog.d("AccountChecker", "application check has been already completed");
            a(this.d);
        } else if (this.h != null) {
            FileLog.d("AccountChecker", "sms finding process for the account data %s has been already started", this.f);
        } else {
            FileLog.d("AccountChecker", "start sms finding process for the account data %s", this.f);
            this.h = this.a.getBackgroundWorker().submit(new RunnableC0000a());
        }
    }

    private void d() {
        if (this.f != null) {
            this.a.getSettings().putValue("account_check_app_json", this.f);
        } else {
            this.a.getSettings().removeValue("account_check_time");
        }
        if (this.g != null) {
            try {
                this.a.getSettings().putValue("account_check_intercepted_sms", JsonParser.toJson(this.g));
            } catch (Throwable unused) {
                FileLog.e("AccountChecker", "failed to save intercepted sms");
                this.g = null;
            }
        } else {
            this.a.getSettings().removeValue("account_check_intercepted_sms");
        }
        this.a.getSettings().commit();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Removed duplicated region for block: B:15:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x005f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void a(@androidx.annotation.NonNull java.lang.String r11, @androidx.annotation.Nullable ru.mail.libverify.api.VerificationApi.AccountCheckListener r12) {
        /*
            r10 = this;
            r0 = r11
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 == 0) goto Lf
            r0 = r10
            ru.mail.libverify.api.VerificationApi$AccountCheckResult r1 = ru.mail.libverify.api.VerificationApi.AccountCheckResult.EMPTY_ACCOUNT_DATA
            r0.a(r1)
            return
        Lf:
            r0 = r10
            ru.mail.libverify.api.CommonContext r0 = r0.a
            ru.mail.libverify.platform.storage.KeyValueStorage r0 = r0.getSettings()
            java.lang.String r1 = "account_check_time"
            java.lang.String r0 = r0.getValue(r1)
            r1 = r0
            r13 = r1
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L48
            r0 = r10
            ru.mail.verify.core.platform.TimeProvider r0 = r0.i
            long r0 = r0.getLocalTime()
            r1 = r13
            long r1 = java.lang.Long.parseLong(r1)
            long r0 = r0 - r1
            r1 = r0; r1 = r0; 
            r13 = r1
            r1 = 0
            int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r0 < 0) goto L48
            r0 = r13
            r1 = 43200000(0x2932e00, double:2.1343636E-316)
            int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r0 >= 0) goto L48
            r0 = 0
            goto L49
        L48:
            r0 = 1
        L49:
            if (r0 != 0) goto L5f
            r0 = 1
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r1 = r0
            r10 = r1
            r1 = 0
            r2 = r11
            r0[r1] = r2
            java.lang.String r0 = "AccountChecker"
            java.lang.String r1 = "account data %s check dismissed by timeout"
            r2 = r10
            ru.mail.verify.core.utils.FileLog.d(r0, r1, r2)
            return
        L5f:
            r0 = r10
            r1 = r0
            r2 = r1
            r3 = r11
            r4 = r10
            r5 = r12
            r6 = 1
            java.lang.Object[] r6 = new java.lang.Object[r6]
            r7 = r6
            r12 = r7
            r7 = 0
            r8 = r11
            r6[r7] = r8
            java.lang.String r6 = "AccountChecker"
            java.lang.String r7 = "account data %s check started"
            r8 = r12
            ru.mail.verify.core.utils.FileLog.d(r6, r7, r8)
            r4.e = r5
            r2.f = r3
            r1.d()
            ru.mail.libverify.api.CommonContext r0 = r0.a
            ru.mail.libverify.storage.InstanceConfig r0 = r0.getConfig()
            android.content.Context r0 = r0.getContext()
            java.lang.String r1 = "android.permission.READ_SMS"
            boolean r0 = ru.mail.verify.core.utils.Utils.hasSelfPermission(r0, r1)
            if (r0 != 0) goto La0
            r0 = r10
            ru.mail.libverify.api.VerificationApi$AccountCheckResult r1 = ru.mail.libverify.api.VerificationApi.AccountCheckResult.NO_SMS_PERMISSION
            r0.a(r1)
            goto Ld5
        La0:
            r0 = r10
            ru.mail.libverify.j.l r0 = r0.c
            if (r0 != 0) goto Ld1
            r0 = r10
            r1 = r0
            ru.mail.verify.core.utils.components.MessageBus r1 = r1.b
            ru.mail.verify.core.utils.components.BusMessageType r2 = ru.mail.verify.core.utils.components.BusMessageType.ACCOUNT_CHECKER_REQUEST_SMS_INFO
            r3 = 0
            android.os.Message r2 = ru.mail.verify.core.utils.components.MessageBusUtils.createOneArg(r2, r3)
            r1.post(r2)
            ru.mail.libverify.api.CommonContext r0 = r0.a
            android.os.Handler r0 = r0.getDispatcher()
            ru.mail.verify.core.utils.components.BusMessageType r1 = ru.mail.verify.core.utils.components.BusMessageType.ACCOUNT_CHECKER_MAX_SMS_INFO_WAIT_TIMEOUT_INTERNAL
            r2 = 0
            android.os.Message r1 = ru.mail.verify.core.utils.components.MessageBusUtils.createOneArg(r1, r2)
            r2 = 1800000(0x1b7740, double:8.89318E-318)
            boolean r0 = r0.sendMessageDelayed(r1, r2)
            goto Ld5
        Ld1:
            r0 = r10
            r0.b()
        Ld5:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: ru.mail.libverify.api.a.a(java.lang.String, ru.mail.libverify.api.VerificationApi$AccountCheckListener):void");
    }

    @Override // ru.mail.verify.core.api.ApiPlugin
    public final void initialize() {
        this.b.register(Arrays.asList(BusMessageType.ACCOUNT_CHECKER_NO_SMS_INFO_INTERNAL, BusMessageType.ACCOUNT_CHECKER_SMS_SEARCH_COMPLETED_INTERNAL, BusMessageType.ACCOUNT_CHECKER_GENERAL_ERROR_INTERNAL, BusMessageType.ACCOUNT_CHECKER_MAX_SMS_INFO_WAIT_TIMEOUT_INTERNAL, BusMessageType.API_RESET, BusMessageType.VERIFY_API_RESET), this);
        if (this.f == null) {
            this.f = this.a.getSettings().getValue("account_check_app_json");
        }
        if (this.g == null) {
            try {
                String value = this.a.getSettings().getValue("account_check_intercepted_sms");
                if (!TextUtils.isEmpty(value)) {
                    this.g = JsonParser.mapFromJson(value, ru.mail.libverify.k.k.class);
                }
            } catch (Throwable unused) {
                FileLog.e("AccountChecker", "failed to restore intercepted sms");
                this.a.getSettings().removeValue("account_check_intercepted_sms").commit();
            }
        }
        if (TextUtils.isEmpty(this.f)) {
            return;
        }
        a(this.f, this.e);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void c() {
        FileLog.d("AccountChecker", "reset started");
        a();
        this.a.getSettings().removeValue("account_check_time").commit();
        this.c = null;
        this.d = null;
        Future future = this.h;
        if (future != null) {
            future.cancel(true);
            this.h = null;
        }
        FileLog.d("AccountChecker", "reset completed");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v13, types: [java.lang.Throwable, ru.mail.libverify.api.a$c] */
    /* JADX WARN: Type inference failed for: r0v3 */
    /* JADX WARN: Type inference failed for: r0v5, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v6, types: [ru.mail.libverify.api.a] */
    /* JADX WARN: Type inference failed for: r1v1, types: [int[]] */
    @Override // ru.mail.verify.core.utils.components.MessageHandler
    public final boolean handleMessage(@NonNull Message message) {
        ?? r0 = b.a[MessageBusUtils.getType(message, "AccountChecker").ordinal()];
        switch (r0) {
            case BroadcastManager.FLAG_NETWORK_RECEIVER /* 1 */:
                this.h = null;
                a(VerificationApi.AccountCheckResult.NO_SMS_INFO);
                return true;
            case 2:
                ?? r02 = (c) MessageBusUtils.getArg(message, c.class);
                try {
                    List<ru.mail.libverify.k.k> list = r02.c;
                    if (list == null || list.isEmpty()) {
                        a(r02.b ? VerificationApi.AccountCheckResult.NO_SMS_FOUND_HAS_CODE : r02.a ? VerificationApi.AccountCheckResult.NO_SMS_FOUND_HAS_SOURCE_MATCH : VerificationApi.AccountCheckResult.NO_SMS_FOUND);
                    } else {
                        this.d = r02.c;
                        a(this.d);
                    }
                    this.h = null;
                    this.b.post(MessageBusUtils.createOneArg(BusMessageType.ACCOUNT_CHECKER_SMS_PARSING_COMPLETED, (Object) null));
                    return true;
                } finally {
                }
            case 3:
                try {
                    r0 = this;
                    r0.d = new ArrayList();
                    r0.a(VerificationApi.AccountCheckResult.GENERAL_ERROR);
                    r0.h = null;
                    r0.b.post(MessageBusUtils.createOneArg(BusMessageType.ACCOUNT_CHECKER_SMS_PARSING_COMPLETED, (Object) null));
                    return true;
                } finally {
                }
            case 4:
                FileLog.e("AccountChecker", "sms info request timeout expired");
                a(VerificationApi.AccountCheckResult.NO_SMS_INFO);
                return true;
            case 5:
            case 6:
                c();
                return false;
            default:
                return false;
        }
    }

    private void a() {
        this.a.getDispatcher().removeMessages(BusMessageType.ACCOUNT_CHECKER_MAX_SMS_INFO_WAIT_TIMEOUT_INTERNAL.ordinal());
        this.a.getSettings().removeValue("account_check_app_json").removeValue("account_check_intercepted_sms").commit();
        this.f = null;
        this.g = null;
    }

    private void a(@NonNull List<ru.mail.libverify.k.k> list) {
        String str;
        FileLog.d("AccountChecker", "account data %s check completed, sms found %d", this.f, Integer.valueOf(list.size()));
        MessageBus messageBus = this.b;
        BusMessageType busMessageType = BusMessageType.ACCOUNT_CHECKER_COMPLETED;
        Object[] objArr = new Object[3];
        objArr[0] = this.f;
        if (list.isEmpty()) {
            str = null;
        } else {
            try {
                str = JsonParser.toJson(new ru.mail.libverify.b.a(list));
            } catch (JsonParseException e) {
                DebugUtils.safeThrow("AccountChecker", "failed to format json", e);
                str = null;
            }
        }
        objArr[1] = str;
        VerificationApi.AccountCheckResult accountCheckResult = VerificationApi.AccountCheckResult.OK;
        objArr[2] = accountCheckResult;
        messageBus.post(MessageBusUtils.createMultipleArgs(busMessageType, objArr));
        VerificationApi.AccountCheckListener accountCheckListener = this.e;
        if (accountCheckListener != null) {
            accountCheckListener.onComplete(accountCheckResult);
        }
        this.a.getSettings().putValue("account_check_time", Long.toString(this.i.getLocalTime())).commit();
        a();
    }

    private void a(VerificationApi.AccountCheckResult accountCheckResult) {
        String str;
        FileLog.e("AccountChecker", "failed to check account data %s, error %s", this.f, accountCheckResult);
        MessageBus messageBus = this.b;
        BusMessageType busMessageType = BusMessageType.ACCOUNT_CHECKER_COMPLETED;
        Object[] objArr = new Object[3];
        objArr[0] = this.f;
        try {
            str = JsonParser.toJson(new ru.mail.libverify.b.a(accountCheckResult));
        } catch (JsonParseException e) {
            DebugUtils.safeThrow("AccountChecker", "failed to format json", e);
            str = null;
        }
        objArr[1] = str;
        objArr[2] = accountCheckResult;
        messageBus.post(MessageBusUtils.createMultipleArgs(busMessageType, objArr));
        VerificationApi.AccountCheckListener accountCheckListener = this.e;
        if (accountCheckListener != null) {
            accountCheckListener.onComplete(accountCheckResult);
        }
        if (accountCheckResult == VerificationApi.AccountCheckResult.GENERAL_ERROR) {
            c();
            return;
        }
        this.a.getSettings().putValue("account_check_time", Long.toString(this.i.getLocalTime())).commit();
        a();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void a(@NonNull ru.mail.libverify.k.k kVar) {
        FileLog.v("AccountChecker", "process alien sms from %s with text %s", kVar.b(), kVar.a());
        if (this.f == null) {
            this.f = this.a.getSettings().getValue("account_check_app_json");
        }
        if (this.g == null) {
            try {
                String value = this.a.getSettings().getValue("account_check_intercepted_sms");
                if (!TextUtils.isEmpty(value)) {
                    this.g = JsonParser.mapFromJson(value, ru.mail.libverify.k.k.class);
                }
            } catch (Throwable unused) {
                FileLog.e("AccountChecker", "failed to restore intercepted sms");
                this.a.getSettings().removeValue("account_check_intercepted_sms").commit();
            }
        }
        if (this.g == null) {
            this.g = new HashMap();
        }
        this.g.put(kVar.b(), kVar);
        d();
        if (this.c == null) {
            this.b.post(MessageBusUtils.createOneArg(BusMessageType.ACCOUNT_CHECKER_REQUEST_SMS_INFO, (Object) null));
            this.a.getDispatcher().sendMessageDelayed(MessageBusUtils.createOneArg(BusMessageType.ACCOUNT_CHECKER_MAX_SMS_INFO_WAIT_TIMEOUT_INTERNAL, (Object) null), 1800000L);
            return;
        }
        HashMap hashMap = this.g;
        if (hashMap == null || hashMap.isEmpty()) {
            FileLog.d("AccountChecker", "no intercepted sms");
            return;
        }
        ru.mail.libverify.j.l lVar = this.c;
        if ((lVar == null || lVar.c() == null || lVar.c().length == 0 || lVar.d() == null || lVar.d().isEmpty()) ? false : true) {
            a();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void a(@Nullable ru.mail.libverify.j.l lVar) {
        this.c = lVar;
        b();
        HashMap hashMap = this.g;
        if (hashMap == null || hashMap.isEmpty()) {
            FileLog.d("AccountChecker", "no intercepted sms");
            return;
        }
        ru.mail.libverify.j.l lVar2 = this.c;
        if ((lVar2 == null || lVar2.c() == null || lVar2.c().length == 0 || lVar2.d() == null || lVar2.d().isEmpty()) ? false : true) {
            a();
        }
    }
}
