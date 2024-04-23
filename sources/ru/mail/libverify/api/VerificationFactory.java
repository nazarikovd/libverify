package ru.mail.libverify.api;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.jvm.functions.Function1;
import ru.mail.libverify.R;
import ru.mail.libverify.api.h;
import ru.mail.libverify.platform.core.PlatformCoreService;
import ru.mail.verify.core.api.ApiComponent;
import ru.mail.verify.core.api.UncaughtExceptionListener;
import ru.mail.verify.core.gcm.GcmProcessService;
import ru.mail.verify.core.storage.Installation;
import ru.mail.verify.core.storage.UnsafeInstallation;
import ru.mail.verify.core.utils.FileLog;
import ru.mail.verify.core.utils.LogReceiver;
import ru.mail.verify.core.utils.SocketFactoryProvider;
import ru.mail.verify.core.utils.components.BusMessageType;
import ru.mail.verify.core.utils.components.MessageBusUtils;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/VerificationFactory.class */
public final class VerificationFactory {
    public static final String LIBVERIFY_RESOURCE_APPLICATION_NAME_KEY = "libverify_application_name";
    public static final String LIBVERIFY_MANIFEST_APPLICATION_NAME_KEY = "ru.mail.libverify.application_name";
    public static final String LIBVERIFY_RESOURCE_APPLICATION_KEY_KEY = "libverify_application_key";
    public static final String LIBVERIFY_MANIFEST_APPLICATION_KEY_KEY = "ru.mail.libverify.application_key";
    public static final String LIBVERIFY_MANIFEST_SERVER_ID_KEY = "ru.mail.libverify.server_id";
    public static final String LIBVERIFY_RESOURCE_SERVER_ID_KEY = "libverify_server_id";
    public static final String LIBVERIFY_GCM_TOKEN_BROADCAST_ACTION = "ru.mail.libverify.gcm_token";
    public static final String LIBVERIFY_GCM_TOKEN = "gcm_token";
    private static volatile ru.mail.libverify.b.d a;
    private static final AtomicBoolean b = new AtomicBoolean(false);

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/VerificationFactory$a.class */
    public class a implements Runnable {
        final /* synthetic */ Context a;

        a(Context context) {
            this.a = context;
        }

        @Override // java.lang.Runnable
        public final void run() {
            Context context = this.a;
            if (VerificationFactory.a == null) {
                synchronized (ru.mail.libverify.v.a.class) {
                    if (VerificationFactory.a == null) {
                        ApiComponent c = ru.mail.libverify.v.a.c(context);
                        VerificationFactory.a = h.a().a(c).a(ru.mail.libverify.v.a.a()).a();
                    }
                }
            }
            ((h.b) VerificationFactory.a).a();
        }
    }

    @NonNull
    public static VerificationApi get(@NonNull Context context) {
        if (!b.get()) {
            initialize(context);
            FileLog.e("VerificationFactory", "Libverify must be initialized with initialize() method before this call");
        }
        if (a == null) {
            synchronized (ru.mail.libverify.v.a.class) {
                if (a == null) {
                    ApiComponent c = ru.mail.libverify.v.a.c(context);
                    a = h.a().a(c).a(ru.mail.libverify.v.a.a()).a();
                }
            }
        }
        return ((h.b) a).a();
    }

    public static void initialize(@NonNull Context context) {
        if (b.compareAndSet(false, true)) {
            PlatformCoreService orInitPlatformService = ru.mail.libverify.v.a.a().getOrInitPlatformService(context);
            if (orInitPlatformService != null) {
                FileLog.v("VerificationFactory", "platform type: %s", orInitPlatformService.getClass().getName());
            } else {
                FileLog.v("VerificationFactory", "platform service not found");
            }
            FileLog.v("VerificationFactory", "Initialize Verify");
            ru.mail.libverify.v.a.a(new a(context));
        }
    }

    public static PlatformCoreService getPlatformService(Context context) {
        return ru.mail.libverify.v.a.a().getOrInitPlatformService(context);
    }

    public static void bootstrap(@NonNull Context context) {
        ru.mail.libverify.v.a.a(context);
    }

    public static void enableDebugMode() {
        ru.mail.libverify.v.a.a().setDebugMode();
    }

    public static ArrayList<String> getSmsHash(Context context) {
        return new ru.mail.libverify.b.b(context).a();
    }

    public static void enableSimpleDebugCodeParser() {
        ru.mail.libverify.v.a.a().setSimpleDebugCodeParser();
    }

    public static void setLogReceiver(@NonNull LogReceiver receiver) {
        ru.mail.libverify.v.a.a().setLogReceiver(receiver);
    }

    public static void setUncaughtExceptionListener(@NonNull UncaughtExceptionListener listener) {
        ru.mail.libverify.v.a.a().setUncaughtExceptionListener(listener);
    }

    @NonNull
    public static VerificationApi getInstance(@NonNull Context context) throws IllegalArgumentException {
        return get(context);
    }

    public static boolean hasInstallation(@NonNull Context context) {
        return Installation.hasInstallation(context);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Class<ru.mail.libverify.v.a>] */
    /* JADX WARN: Type inference failed for: r0v2, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v4 */
    public static void release(@NonNull Context context) {
        if (a != null) {
            ?? r0 = ru.mail.libverify.v.a.class;
            synchronized (r0) {
                if (a != null) {
                    ru.mail.libverify.v.a.a(context, MessageBusUtils.createOneArg(BusMessageType.API_SHUTDOWN, (Object) null));
                    b.set(false);
                    a = null;
                }
                r0 = r0;
            }
        }
    }

    @NonNull
    public static String[] getRequiredPermissions(Context context) {
        return new String[]{"android.permission.READ_SMS", "android.permission.RECEIVE_SMS", "android.permission.READ_CALL_LOG", "android.permission.CALL_PHONE", "android.permission.READ_PHONE_STATE", "android.permission.READ_PHONE_NUMBERS", "android.permission.ACCESS_COARSE_LOCATION"};
    }

    @NonNull
    public static String getGcmServerId(@NonNull Context context) {
        return context.getResources().getString(R.string.libverify_server_id);
    }

    public static void deliverGcmMessageIntent(@NonNull Context context, String from, Bundle data) {
        if (TextUtils.equals(from, ru.mail.libverify.v.a.d(context))) {
            if (UnsafeInstallation.hasInstallation(context) || Installation.hasInstallation(context)) {
                GcmProcessService.processMessage(context, from, data);
            }
        }
    }

    public static void refreshGcmToken(@NonNull Context context) {
        if (!b.get()) {
            initialize(context);
            FileLog.e("VerificationFactory", "Libverify must be initialized with initialize() method before this call");
        }
        int i = ru.mail.libverify.v.a.f;
        if (UnsafeInstallation.hasInstallation(context) || Installation.hasInstallation(context)) {
            GcmProcessService.refreshToken(context);
        }
    }

    public static void signOut(@NonNull Context context, boolean dropAllInstances) {
        if (hasInstallation(context)) {
            get(context).signOut(dropAllInstances);
        }
    }

    public static void softSignOut(@NonNull Context context) {
        if (hasInstallation(context)) {
            get(context).softSignOut();
        }
    }

    public static void setCustomLocale(@NonNull Context context, @NonNull Locale locale) {
        get(context).setCustomLocale(locale);
    }

    public static void setDisableSimDataSend(@NonNull Context context, boolean disableSimDataSend) {
        get(context).setSimDataSendDisabled(disableSimDataSend);
    }

    public static void setLocationUsage(@NonNull Context context, boolean enable) {
    }

    public static void setApiEndpoints(@NonNull Context context, @NonNull Map<String, String> endpoints) {
        get(context).setApiEndpoints(endpoints);
    }

    public static void setApiEndpoint(@NonNull Context context, @Nullable String domain) {
        get(context).setApiEndpoint(domain);
    }

    public static void removeApiEndpoint(@NonNull Context context) {
        get(context).removeApiEndpoint();
    }

    public static void setSocketFactoryProvider(@NonNull SocketFactoryProvider provider) {
        ru.mail.libverify.v.a.a().setSocketFactoryProvider(provider);
    }

    public static void setPlatformService(Context context, PlatformCoreService... platformCoreServices) {
        PlatformCoreService platformCoreService = null;
        int length = platformCoreServices.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            PlatformCoreService platformCoreService2 = platformCoreService;
            PlatformCoreService platformCoreService3 = platformCoreServices[i];
            if (platformCoreService2 == null) {
                platformCoreService = platformCoreService3;
            }
            if (platformCoreService3.isServiceAvailable(context, (Function1) null)) {
                platformCoreService = platformCoreService3;
                break;
            }
            i++;
        }
        ru.mail.libverify.v.a.a().setPlatformService(platformCoreService);
    }

    public static void signOut(@NonNull Context context, boolean dropAllInstances, @Nullable SignOutCallback callback) {
        if (hasInstallation(context)) {
            get(context).signOut(dropAllInstances, callback);
        }
    }

    public static void softSignOut(@NonNull Context context, SignOutCallback callback) {
        if (hasInstallation(context)) {
            get(context).softSignOut(callback);
        }
    }

    public static void deliverGcmMessageIntent(@NonNull Context context, String from, Map<String, String> data) {
        if (!b.get()) {
            initialize(context);
            FileLog.e("VerificationFactory", "Libverify must be initialized with initialize() method before this call");
        }
        if (TextUtils.equals(from, ru.mail.libverify.v.a.d(context))) {
            if (UnsafeInstallation.hasInstallation(context) || Installation.hasInstallation(context)) {
                GcmProcessService.processMessage(context, from, data);
            }
        }
    }
}
