package ru.mail.libverify.api;

import android.os.Message;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import java.util.Arrays;
import java.util.concurrent.Future;
import ru.mail.libverify.api.q;
import ru.mail.libverify.platform.api.AttestationFailedException;
import ru.mail.libverify.platform.api.GAPIClientFailedException;
import ru.mail.libverify.platform.core.JwsServiceCallback;
import ru.mail.libverify.platform.core.PlatformCoreService;
import ru.mail.verify.core.api.ApiPlugin;
import ru.mail.verify.core.platform.TimeProvider;
import ru.mail.verify.core.utils.FileLog;
import ru.mail.verify.core.utils.Utils;
import ru.mail.verify.core.utils.components.BusMessageType;
import ru.mail.verify.core.utils.components.MessageBus;
import ru.mail.verify.core.utils.components.MessageBusUtils;
import ru.mail.verify.core.utils.components.MessageHandler;
import ru.mail.verify.core.utils.json.JsonParser;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/f.class */
public final class f implements MessageHandler, ApiPlugin {
    private final CommonContext a;
    private final MessageBus b;
    private Future c;
    private String d;
    private ru.mail.libverify.m.n e;
    private TimeProvider f;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/f$a.class */
    public class a implements Runnable {
        final /* synthetic */ String a;
        final /* synthetic */ String b;

        /* renamed from: ru.mail.libverify.api.f$a$a  reason: collision with other inner class name */
        /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/f$a$a.class */
        class C0001a implements JwsServiceCallback {
            C0001a() {
            }

            public final void onSuccess(String str) {
                f.this.e.a(str);
                f.this.a(str, e.SUCCESS);
            }

            public final void onFailure(Exception exc) {
                if (exc instanceof AttestationFailedException) {
                    FileLog.e("ApplicationChecker", "application check failed", exc);
                    f.this.a(null, e.ATTESTATION_FAILED);
                } else if (exc instanceof GAPIClientFailedException) {
                    FileLog.e("ApplicationChecker", "application check failed", exc);
                    f.this.a(null, e.GP_SERVICE_NOT_AVAILABLE);
                } else if (exc instanceof InterruptedException) {
                    FileLog.e("ApplicationChecker", "application check interrupted", exc);
                } else {
                    FileLog.e("ApplicationChecker", "application check failed", exc);
                    f.this.a(null, e.GENERAL_ERROR);
                }
            }
        }

        a(String str, String str2) {
            this.a = str;
            this.b = str2;
        }

        @Override // java.lang.Runnable
        public final void run() {
            if (Thread.interrupted()) {
                FileLog.d("ApplicationChecker", "application check interrupted");
                return;
            }
            try {
                PlatformCoreService platformService = VerificationFactory.getPlatformService(f.this.a.getConfig().getContext());
                if (platformService == null) {
                    return;
                }
                platformService.getJwsService().getJws(f.this.a.getConfig().getContext(), Utils.decodeBase64FromString(this.a), this.b, new C0001a());
            } catch (Throwable th) {
                FileLog.e("ApplicationChecker", "application check failed", th);
                f.this.a(null, e.GENERAL_ERROR);
            }
        }
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/f$b.class */
    static /* synthetic */ class b {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[BusMessageType.values().length];
            a = iArr;
            try {
                iArr[BusMessageType.APPLICATION_CHECKER_COMPLETED_INTERNAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[BusMessageType.VERIFY_API_RESET.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[BusMessageType.API_RESET.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public f(@NonNull CommonContext commonContext) {
        this.a = commonContext;
        q.f fVar = (q.f) commonContext;
        this.b = fVar.getBus();
        this.e = new ru.mail.libverify.m.n(fVar);
        this.f = fVar.getConfig().getTimeProvider();
    }

    private void b() {
        if (TextUtils.isEmpty(this.a.getSettings().getValue("app_check_started"))) {
            FileLog.v("ApplicationChecker", "no pending job");
            return;
        }
        String str = this.d;
        if (str == null) {
            FileLog.d("ApplicationChecker", "no safetyNetItem");
            return;
        }
        ru.mail.libverify.d.c cVar = (ru.mail.libverify.d.c) JsonParser.fromJson(str, ru.mail.libverify.d.c.class);
        if (cVar == null) {
            FileLog.d("ApplicationChecker", "no safetyNetPair");
            return;
        }
        String b2 = cVar.b();
        String a2 = cVar.a();
        if (TextUtils.isEmpty(b2)) {
            FileLog.d("ApplicationChecker", "no nonce");
        } else if (TextUtils.isEmpty(a2)) {
            FileLog.d("ApplicationChecker", "apiKey");
        } else if (this.c == null) {
            this.c = this.a.getBackgroundWorker().submit(new a(b2, a2));
        }
    }

    @Override // ru.mail.verify.core.api.ApiPlugin
    public final void initialize() {
        this.b.register(Arrays.asList(BusMessageType.APPLICATION_CHECKER_COMPLETED_INTERNAL, BusMessageType.API_RESET, BusMessageType.VERIFY_API_RESET), this);
        b();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void a() {
        FileLog.d("ApplicationChecker", "application check requested");
        this.a.getSettings().putValue("app_check_started", Long.toString(this.f.getLocalTime())).commit();
        Future future = this.c;
        if (future != null) {
            future.cancel(true);
            this.c = null;
        }
        this.d = null;
        b();
    }

    @Override // ru.mail.verify.core.utils.components.MessageHandler
    public final boolean handleMessage(@NonNull Message message) {
        int i = b.a[MessageBusUtils.getType(message, "ApplicationChecker").ordinal()];
        if (i == 1) {
            String str = (String) MessageBusUtils.getArg(message, String.class, 0);
            e eVar = (e) MessageBusUtils.getArg(message, e.class, 1);
            FileLog.v("ApplicationChecker", "application check completed jws %s, result %s", str, eVar);
            this.c = null;
            this.d = null;
            this.a.getSettings().removeValue("app_check_started").commit();
            this.b.post(MessageBusUtils.createMultipleArgs(BusMessageType.APPLICATION_CHECKER_COMPLETED, str, eVar));
            return true;
        } else if (i == 2 || i == 3) {
            Future future = this.c;
            if (future != null) {
                future.cancel(true);
                this.c = null;
            }
            this.d = null;
            this.a.getSettings().removeValue("app_check_started").commit();
            return true;
        } else {
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void a(@NonNull String str) {
        FileLog.d("ApplicationChecker", "application check server id %s received", str);
        this.d = str;
        this.a.getSettings().putValue("app_check_started", Long.toString(this.f.getLocalTime())).commit();
        b();
    }

    private void a(String str, e eVar) {
        this.a.getDispatcher().sendMessage(MessageBusUtils.createMultipleArgs(BusMessageType.APPLICATION_CHECKER_COMPLETED_INTERNAL, str, eVar));
    }
}
