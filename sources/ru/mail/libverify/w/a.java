package ru.mail.libverify.w;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import dagger.Lazy;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.inject.Inject;
import kotlin.Unit;
import ru.mail.libverify.api.VerificationFactory;
import ru.mail.libverify.platform.gcm.IdProviderService;
import ru.mail.libverify.platform.storage.KeyValueStorage;
import ru.mail.verify.core.api.ApiManager;
import ru.mail.verify.core.api.ResourceParamsBase;
import ru.mail.verify.core.gcm.GcmRegistrar;
import ru.mail.verify.core.storage.LockManager;
import ru.mail.verify.core.utils.FileLog;
import ru.mail.verify.core.utils.GCMTokenCheckType;
import ru.mail.verify.core.utils.Utils;
import ru.mail.verify.core.utils.components.BusMessageType;
import ru.mail.verify.core.utils.components.MessageBus;
import ru.mail.verify.core.utils.components.MessageBusUtils;
import ru.mail.verify.core.utils.components.MessageHandler;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/w/a.class */
public final class a implements GcmRegistrar, MessageHandler {
    private final MessageBus c;
    private final ApiManager d;
    private final ResourceParamsBase e;
    private final Context f;
    private final LockManager g;
    private final Lazy<KeyValueStorage> h;
    private final AtomicBoolean a = new AtomicBoolean(false);
    private final AtomicBoolean b = new AtomicBoolean(false);
    IdProviderService.IdProviderCallback i = new C0024a();

    /* renamed from: ru.mail.libverify.w.a$a  reason: collision with other inner class name */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/w/a$a.class */
    class C0024a implements IdProviderService.IdProviderCallback {
        C0024a() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final void onIdProviderCallback(@NonNull String str) {
            if (TextUtils.isEmpty(str)) {
                return;
            }
            FileLog.v("GcmRegistrar", "GCM registration id %s was received and stored in shared preferences", str);
            a aVar = a.this;
            synchronized (aVar) {
                int appVersion = Utils.getAppVersion(aVar.f);
                Object[] objArr = new Object[2];
                objArr[0] = str;
                objArr[1] = Integer.valueOf(appVersion);
                FileLog.v("GcmRegistrar", "save GCM token %s on app version %s", objArr);
                ((KeyValueStorage) aVar.h.get()).putValue("gcm_registration_id" + aVar.e.getServerId(), str).putValue("gcm_app_version" + aVar.e.getServerId(), Integer.toString(appVersion)).commitSync();
            }
            a.this.c.post(MessageBusUtils.createOneArg(BusMessageType.GCM_TOKEN_UPDATED, str));
        }

        public final void onException(@NonNull Throwable th) {
            FileLog.e("GcmRegistrar", "GCM service access error", th);
            FileLog.e("GcmRegistrar", "not enough permissions to register GCM channel or other error", th);
            a.this.c.post(MessageBusUtils.createMultipleArgs(BusMessageType.GCM_TOKEN_UPDATE_FAILED, th, Boolean.FALSE));
            VerificationFactory.getPlatformService(a.this.f).isServiceAvailable(a.this.f, str -> {
                if (a.this.b.compareAndSet(false, true)) {
                    a.this.c.post(MessageBusUtils.createOneArg(BusMessageType.GCM_NO_GOOGLE_PLAY_SERVICES_INSTALLED, str));
                }
                FileLog.e("GcmRegistrar", "fatal play services check status: %s", str);
                return Unit.INSTANCE;
            });
        }
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/w/a$b.class */
    static /* synthetic */ class b {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[BusMessageType.values().length];
            a = iArr;
            try {
                iArr[BusMessageType.API_RESET.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[BusMessageType.GCM_REFRESH_TOKEN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Inject
    public a(@NonNull Context context, @NonNull LockManager lockManager, @NonNull ApiManager apiManager, @NonNull MessageBus messageBus, @NonNull ResourceParamsBase resourceParamsBase, @NonNull Lazy<KeyValueStorage> lazy) {
        this.f = context;
        this.g = lockManager;
        this.h = lazy;
        this.c = messageBus;
        this.d = apiManager;
        this.e = resourceParamsBase;
    }

    private synchronized void a() {
        FileLog.v("GcmRegistrar", "clear GCM token");
        ((KeyValueStorage) this.h.get()).removeValue("gcm_registration_id" + this.e.getServerId()).removeValue("gcm_app_version" + this.e.getServerId()).commitSync();
    }

    private void c() {
        if (this.b.get() || VerificationFactory.getPlatformService(this.f) == null || !this.a.compareAndSet(false, true)) {
            return;
        }
        this.g.acquireLock(this, false, 0);
        FileLog.v("GcmRegistrar", "initialize registration for %s", this.e.getServerId());
        this.d.getBackgroundWorker().submit(this::b);
    }

    private void b() {
        try {
            a();
            Context context = this.f;
            VerificationFactory.getPlatformService(context).getIdProviderService().getId(context, this.e.getServerId(), this.i);
            this.g.releaseLock(this);
            this.a.set(false);
        }
    }

    @Override // ru.mail.verify.core.gcm.GcmRegistrar
    public final String getRegistrationId() {
        String value = ((KeyValueStorage) this.h.get()).getValue("gcm_registration_id" + this.e.getServerId());
        if (TextUtils.isEmpty(value)) {
            FileLog.v("GcmRegistrar", "GCM token not found");
            c();
            return null;
        } else if (TextUtils.equals(((KeyValueStorage) this.h.get()).getValue("gcm_app_version" + this.e.getServerId()), Integer.toString(Utils.getAppVersion(this.f)))) {
            return value;
        } else {
            FileLog.v("GcmRegistrar", "app version changed");
            c();
            return null;
        }
    }

    @Override // ru.mail.verify.core.gcm.GcmRegistrar
    public final boolean isRegistered() {
        return !TextUtils.isEmpty(getRegistrationId());
    }

    @Override // ru.mail.verify.core.api.ApiPlugin
    public final void initialize() {
        this.c.register(Arrays.asList(BusMessageType.API_RESET, BusMessageType.GCM_REFRESH_TOKEN), this);
        getRegistrationId();
    }

    @Override // ru.mail.verify.core.utils.components.MessageHandler
    public final boolean handleMessage(@NonNull Message message) {
        int i = b.a[MessageBusUtils.getType(message, "GcmRegistrar").ordinal()];
        if (i == 1) {
            a();
            return true;
        } else if (i != 2) {
            return false;
        } else {
            FileLog.v("GcmRegistrar", "refresh token with type: %s", GCMTokenCheckType.valueOf(((Bundle) MessageBusUtils.getArg(message, Bundle.class)).getString(ApiManager.GCM_TOKEN_CHECK_TYPE)));
            a();
            getRegistrationId();
            this.c.post(MessageBusUtils.createOneArg(BusMessageType.GCM_TOKEN_REFRESHED, (Object) null));
            return true;
        }
    }
}
