package ru.mail.verify.core.api;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import dagger.Module;
import dagger.Provides;
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil;
import java.lang.Thread;
import java.util.Map;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import kotlin.jvm.functions.Function1;
import ru.mail.libverify.m.l;
import ru.mail.libverify.platform.core.IInternalFactory;
import ru.mail.libverify.platform.core.ILog;
import ru.mail.libverify.platform.core.PlatformCoreService;
import ru.mail.libverify.platform.storage.KeyValueStorage;
import ru.mail.platform.libverify.sms.SmsRetrieverService;
import ru.mail.verify.core.gcm.GcmProcessService;
import ru.mail.verify.core.platform.TimeProvider;
import ru.mail.verify.core.platform.TimeProviderImpl;
import ru.mail.verify.core.storage.Installation;
import ru.mail.verify.core.storage.UnsafeInstallation;
import ru.mail.verify.core.timer.TimerManager;
import ru.mail.verify.core.timer.TimerManagerImpl;
import ru.mail.verify.core.utils.DebugUtils;
import ru.mail.verify.core.utils.FileLog;
import ru.mail.verify.core.utils.LogReceiver;
import ru.mail.verify.core.utils.SocketFactoryProvider;

@Module
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/api/ApplicationModule.class */
public final class ApplicationModule {
    private static final String LOG_TAG = "NotifyCore";
    private static final String ERROR_MESSAGE_FORMAT = "discard runnable %s on executor %s as it was shut down";
    private static final String ERROR_TEXT = "wrong libverify instance object state";
    private static final String INSTANCE_ID_DEBUG_KEY = "instance_id";
    private volatile UncaughtExceptionListener listener;
    private volatile SocketFactoryProvider provider;
    private PlatformCoreService platformCoreService;
    private final ApplicationStartConfig config = new ApplicationStartConfig(false);
    private final NetworkPolicyConfig networkPolicyConfig = new NetworkPolicyConfig(NetworkSyncMode.DEFAULT, BackgroundAwakeMode.DEFAULT, null);
    private final Thread.UncaughtExceptionHandler handler = new d();
    private final c rejectedHandler = new c();

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/api/ApplicationModule$ApplicationStartConfig.class */
    public static class ApplicationStartConfig {
        private volatile Context a;
        private volatile boolean b;
        private volatile Handler c;
        private volatile KeyValueStorage d;

        public ApplicationStartConfig(boolean debugMode) {
            this.b = debugMode;
        }

        public boolean isDebugMode() {
            return this.b;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v13, types: [ru.mail.verify.core.api.ApplicationModule$ApplicationStartConfig] */
        /* JADX WARN: Type inference failed for: r0v14, types: [java.lang.Throwable] */
        /* JADX WARN: Type inference failed for: r0v16 */
        @Nullable
        public KeyValueStorage getDebugSettings() {
            if (!this.b || this.a == null || this.c == null) {
                return null;
            }
            if (this.d == null) {
                ?? r0 = this;
                synchronized (r0) {
                    if (r0.d == null) {
                        this.d = new ru.mail.verify.core.api.d(this.c, this.a);
                    }
                    r0 = this;
                }
            }
            return this.d;
        }

        public boolean isSimpleCodeParser() {
            return false;
        }
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/api/ApplicationModule$NetworkPolicyConfig.class */
    public static final class NetworkPolicyConfig {
        private volatile NetworkSyncMode a;
        private volatile BackgroundAwakeMode b;
        private volatile NetworkSyncInterceptor c;

        public NetworkPolicyConfig(NetworkSyncMode networkSyncMode, BackgroundAwakeMode backgroundAwakeMode, @Nullable NetworkSyncInterceptor networkInterceptor) {
            this.a = NetworkSyncMode.DEFAULT;
            this.b = BackgroundAwakeMode.DEFAULT;
            this.a = networkSyncMode;
            this.b = backgroundAwakeMode;
            this.c = networkInterceptor;
        }

        public NetworkSyncMode getNetworkSyncMode() {
            return this.a;
        }

        public BackgroundAwakeMode getBackgroundAwakeMode() {
            return this.b;
        }

        @Nullable
        public NetworkSyncInterceptor getNetworkInterceptor() {
            return this.c;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/api/ApplicationModule$a.class */
    public class a implements ILog {
        a() {
        }

        public final void v(String str, String str2) {
            FileLog.v(str, str2);
        }

        public final void d(String str, String str2) {
            FileLog.d(str, str2);
        }

        public final void e(String str, String str2) {
            FileLog.e(str, str2);
        }

        public final void e(String str, String str2, Throwable th) {
            FileLog.e(str, str2, th);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/api/ApplicationModule$b.class */
    public class b implements IInternalFactory {
        b() {
        }

        public final void deliverGcmMessageIntent(Context context, String str, Map<String, String> map) {
            if (TextUtils.equals(str, ru.mail.libverify.v.a.d(context))) {
                if (UnsafeInstallation.hasInstallation(context) || Installation.hasInstallation(context)) {
                    GcmProcessService.processMessage(context, str, map);
                }
            }
        }

        public final void refreshGcmToken(@NonNull Context context) {
            int i = ru.mail.libverify.v.a.f;
            if (UnsafeInstallation.hasInstallation(context) || Installation.hasInstallation(context)) {
                GcmProcessService.refreshToken(context);
            }
        }
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/api/ApplicationModule$c.class */
    private final class c implements RejectedExecutionHandler {
        private c() {
        }

        @Override // java.util.concurrent.RejectedExecutionHandler
        public final void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
            if (threadPoolExecutor.isShutdown() || threadPoolExecutor.isTerminating() || threadPoolExecutor.isTerminated()) {
                FileLog.d(ApplicationModule.LOG_TAG, ApplicationModule.ERROR_MESSAGE_FORMAT, runnable, threadPoolExecutor);
                return;
            }
            IllegalStateException illegalStateException = new IllegalStateException(String.format(ApplicationModule.ERROR_MESSAGE_FORMAT, runnable, threadPoolExecutor));
            FileLog.e(ApplicationModule.LOG_TAG, ApplicationModule.ERROR_TEXT, illegalStateException);
            UncaughtExceptionListener uncaughtExceptionListener = ApplicationModule.this.listener;
            if (uncaughtExceptionListener != null) {
                uncaughtExceptionListener.uncaughtException(null, illegalStateException);
            }
            DebugUtils.crashIfDebug(illegalStateException);
        }
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/api/ApplicationModule$d.class */
    private final class d implements Thread.UncaughtExceptionHandler {
        private d() {
        }

        @Override // java.lang.Thread.UncaughtExceptionHandler
        public final void uncaughtException(Thread thread, Throwable th) {
            FileLog.e(ApplicationModule.LOG_TAG, th, "FATAL ERROR due to notify unhandled exception in thread %s (%d)", thread.getName(), Long.valueOf(thread.getId()));
            UncaughtExceptionListener uncaughtExceptionListener = ApplicationModule.this.listener;
            if (uncaughtExceptionListener != null) {
                uncaughtExceptionListener.uncaughtException(thread, th);
            }
            DebugUtils.crashIfDebug(th);
        }
    }

    private static PlatformCoreService getDefaultPlatformService(Context context) {
        PlatformCoreService[] platformCoreServiceArr = {"ru.mail.libverify.platform.firebase.FirebaseCoreService", "ru.mail.libverify.platform.huawei.HuaweiCoreService"};
        PlatformCoreService platformCoreService = null;
        int i = 0;
        while (true) {
            if (i >= 2) {
                break;
            }
            PlatformCoreService platformCoreService2 = platformCoreServiceArr[i];
            try {
                platformCoreService2 = Class.forName(platformCoreService2);
                try {
                    platformCoreService2 = (PlatformCoreService) platformCoreService2.newInstance();
                    if (platformCoreService2 != null && platformCoreService == null) {
                        platformCoreService = platformCoreService2;
                    }
                    if (platformCoreService2 != null && platformCoreService2.isServiceAvailable(context, (Function1) null)) {
                        FileLog.e(LOG_TAG, "Platform %s initialized", platformCoreService2);
                        platformCoreService = platformCoreService2;
                        break;
                    }
                } catch (IllegalAccessException unused) {
                    platformCoreService2.printStackTrace();
                } catch (InstantiationException unused2) {
                    platformCoreService2.printStackTrace();
                }
            } catch (ClassNotFoundException unused3) {
                platformCoreService2.printStackTrace();
            }
            i++;
        }
        return platformCoreService;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Provides
    @NonNull
    public TimerManager provideTimerManger() {
        return TimerManagerImpl.getInstance();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Provides
    @NonNull
    public Context provideContext() {
        return this.config.a;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Provides
    @NonNull
    public PhoneNumberUtil providePhoneNumberUtil(Context context) {
        return PhoneNumberUtil.createInstance(context);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Provides
    @NonNull
    public ru.mail.libverify.a.b provideSimCardDataUtils(PhoneNumberUtil phoneNumberUtil) {
        return new ru.mail.libverify.a.b(phoneNumberUtil);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Provides
    @NonNull
    public Thread.UncaughtExceptionHandler provideThreadUncaughtExceptionHandler() {
        return this.handler;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Provides
    @NonNull
    public RejectedExecutionHandler provideRejectedExceptionHandler() {
        return this.rejectedHandler;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Provides
    @Nullable
    public UncaughtExceptionListener provideUncaughtExceptionListener() {
        return this.listener;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Provides
    @Nullable
    public SocketFactoryProvider provideSocketFactoryProvider() {
        return this.provider;
    }

    @Provides
    @NonNull
    public NetworkPolicyConfig provideNotifyPolicyConfig() {
        return this.networkPolicyConfig;
    }

    @Provides
    @NonNull
    public TimeProvider provideCurrentTimeProvider(@NonNull KeyValueStorage settings) {
        return new TimeProviderImpl(settings);
    }

    @Provides
    @NonNull
    public ru.mail.libverify.q.c provideStartTimingsRepository(@NonNull l lVar) {
        return new ru.mail.libverify.q.d(lVar);
    }

    @Provides
    @NonNull
    public ru.mail.libverify.q.b provideStartTime(@NonNull l lVar, @NonNull ru.mail.libverify.q.c cVar, @NonNull TimeProvider timeProvider) {
        return new ru.mail.libverify.q.b(lVar, cVar, timeProvider);
    }

    public void setSocketFactoryProvider(@Nullable SocketFactoryProvider provider) {
        this.provider = provider;
    }

    public void setUncaughtExceptionListener(@NonNull UncaughtExceptionListener listener) {
        this.listener = listener;
    }

    public void setLogReceiver(@NonNull LogReceiver receiver) {
        FileLog.init(receiver);
    }

    public void setDebugMode() {
        FileLog.v(LOG_TAG, "Debug logs are enabled");
        this.config.b = true;
        this.config.c = new Handler();
    }

    public void setSimpleDebugCodeParser() {
    }

    public void setContext(@NonNull Context context) {
        this.config.a = context;
    }

    public void setNetworkSyncMode(NetworkSyncMode mode) {
        this.networkPolicyConfig.a = mode;
    }

    public void setBackgroundAwakeMode(BackgroundAwakeMode mode) {
        this.networkPolicyConfig.b = mode;
    }

    public void setNetworkSyncInterceptor(@NonNull NetworkSyncInterceptor interceptor) {
        this.networkPolicyConfig.c = interceptor;
    }

    public Object getDebugParam(@NonNull String key) {
        Context context;
        KeyValueStorage debugSettings = this.config.getDebugSettings();
        if (debugSettings == null) {
            return null;
        }
        return (!INSTANCE_ID_DEBUG_KEY.equals(key) || (context = this.config.a) == null) ? debugSettings.getValue(key) : UnsafeInstallation.id(context);
    }

    public void setDebugParam(@NonNull String key, @Nullable Object value) {
        KeyValueStorage debugSettings;
        if (INSTANCE_ID_DEBUG_KEY.equals(key) || (debugSettings = this.config.getDebugSettings()) == null) {
            return;
        }
        if (value != null) {
            if (value instanceof Integer) {
                debugSettings.putValue(key, ((Integer) value).intValue());
            } else if (value instanceof Long) {
                debugSettings.putValue(key, ((Long) value).longValue());
            } else if (!(value instanceof String)) {
                debugSettings.putValue(key, value.toString());
            } else if (!TextUtils.isEmpty((CharSequence) value)) {
                try {
                    debugSettings.putValue(key, Long.valueOf(Long.parseLong((String) value)).longValue());
                } catch (NumberFormatException unused) {
                    debugSettings.putValue(key, (String) value);
                }
            }
            debugSettings.commit();
        }
        debugSettings.removeValue(key);
        debugSettings.commit();
    }

    @Provides
    @NonNull
    public ApplicationStartConfig provideStartConfig() {
        return this.config;
    }

    public void setPlatformService(PlatformCoreService platformCoreService) {
        this.platformCoreService = platformCoreService;
        if (platformCoreService == null) {
            return;
        }
        platformCoreService.setLog(new a());
        platformCoreService.setInternalFactory(new b());
        platformCoreService.setSmsRetrieverService(applicationContext, intent -> {
            SmsRetrieverService.enqueueWork(applicationContext, intent);
        });
    }

    public PlatformCoreService getOrInitPlatformService(Context context) {
        if (this.platformCoreService == null) {
            PlatformCoreService defaultPlatformService = getDefaultPlatformService(context);
            this.platformCoreService = defaultPlatformService;
            if (defaultPlatformService == null) {
                FileLog.e(LOG_TAG, "platform service is not defined");
            }
            setPlatformService(this.platformCoreService);
        }
        return this.platformCoreService;
    }
}
