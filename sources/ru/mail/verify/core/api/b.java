package ru.mail.verify.core.api;

import android.os.Message;
import android.util.Pair;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import dagger.Lazy;
import java.lang.Thread;
import java.util.Collections;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import javax.inject.Inject;
import javax.inject.Singleton;
import ru.mail.verify.core.api.ApplicationModule;
import ru.mail.verify.core.storage.LockManager;
import ru.mail.verify.core.utils.DebugUtils;
import ru.mail.verify.core.utils.FileLog;
import ru.mail.verify.core.utils.components.BusMessageType;
import ru.mail.verify.core.utils.components.CustomHandler;
import ru.mail.verify.core.utils.components.MessageBus;
import ru.mail.verify.core.utils.components.MessageBusUtils;
import ru.mail.verify.core.utils.components.MessageHandler;

@Singleton
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/api/b.class */
final class b implements MessageHandler, ApiManager {
    private final HashSet a = new HashSet();
    private final ApplicationModule.ApplicationStartConfig b;
    private final Lazy<LockManager> c;
    private final MessageBus d;
    private final c e;

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/api/b$a.class */
    static /* synthetic */ class a {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[BusMessageType.values().length];
            a = iArr;
            try {
                iArr[BusMessageType.EMPTY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[BusMessageType.API_INTERNAL_INITIALIZE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[BusMessageType.API_INITIALIZE_API_GROUP.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                a[BusMessageType.API_INTERNAL_UNHANDLED_EXCEPTION.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                a[BusMessageType.GCM_SERVER_INFO_RECEIVED.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                a[BusMessageType.GCM_REFRESH_TOKEN.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                a[BusMessageType.GCM_FETCHER_INFO_RECEIVED.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                a[BusMessageType.GCM_MESSAGE_RECEIVED.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                a[BusMessageType.NETWORK_STATE_CHANGED.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
        }
    }

    /* renamed from: ru.mail.verify.core.api.b$b  reason: collision with other inner class name */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/api/b$b.class */
    private class C0026b implements DebugUtils.ErrorListener {
        private C0026b() {
        }

        @Override // ru.mail.verify.core.utils.DebugUtils.ErrorListener
        public final void onSilentException(@Nullable String str, @NonNull Throwable th) {
            Pair pair = new Pair(Thread.currentThread(), th);
            FileLog.e("ApiManager", th, "Fatal error %s in thread: %s", str, pair.first);
            b.this.e.b().sendMessage(MessageBusUtils.createOneArg(BusMessageType.API_INTERNAL_SILENT_EXCEPTION, pair));
        }

        @Override // ru.mail.verify.core.utils.DebugUtils.ErrorListener
        public final void onUnhandledException(@NonNull Throwable th) {
            Pair pair = new Pair(Thread.currentThread(), th);
            FileLog.e("ApiManager", th, "Fatal error in thread: %s", pair.first);
            b.this.e.b().sendMessage(MessageBusUtils.createOneArg(BusMessageType.API_INTERNAL_UNHANDLED_EXCEPTION, pair));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Inject
    public b(@NonNull MessageBus messageBus, @NonNull Thread.UncaughtExceptionHandler uncaughtExceptionHandler, @NonNull ApplicationModule.ApplicationStartConfig applicationStartConfig, @NonNull RejectedExecutionHandler rejectedExecutionHandler, @NonNull Lazy lazy) {
        this.b = applicationStartConfig;
        this.c = lazy;
        this.d = messageBus;
        this.e = new c(uncaughtExceptionHandler, rejectedExecutionHandler, this);
        DebugUtils.setListener(new C0026b());
        a();
    }

    private void a() {
        FileLog.d("ApiManager", "prepare internal members %d", Integer.valueOf(hashCode()));
        this.e.b().sendMessage(MessageBusUtils.createOneArg(BusMessageType.API_INTERNAL_INITIALIZE, (Object) null));
    }

    @Override // ru.mail.verify.core.api.ApiManager
    public final void addApiGroup(@NonNull ApiGroup apiGroup) {
        this.e.b().sendMessage(MessageBusUtils.createOneArg(BusMessageType.API_INITIALIZE_API_GROUP, apiGroup));
    }

    @Override // ru.mail.verify.core.api.ApiManager
    public final void post(@NonNull Message message) {
        this.e.b().sendMessage(message);
    }

    @Override // ru.mail.verify.core.api.ApiManager
    public final void postAndWait(@NonNull Message message) {
        this.e.b().postAndWait(message);
    }

    @Override // ru.mail.verify.core.api.ApiManager
    public final CustomHandler getDispatcher() {
        return this.e.b();
    }

    @Override // ru.mail.verify.core.api.ApiManager
    public final ExecutorService getBackgroundWorker() {
        return this.e.a();
    }

    @Override // ru.mail.verify.core.utils.components.MessageHandler
    public final boolean handleMessage(@NonNull Message message) {
        int i = a.a[MessageBusUtils.getType(message, "ApiManager", this.b.isDebugMode() ? MessageBusUtils.TraceType.EXTENDED : MessageBusUtils.TraceType.NONE).ordinal()];
        if (i != 1) {
            if (i == 2) {
                this.d.register(Collections.emptyList(), this);
                return true;
            } else if (i != 3) {
                this.d.post(message);
                return true;
            } else {
                ApiGroup apiGroup = (ApiGroup) MessageBusUtils.getArg(message, ApiGroup.class);
                apiGroup.initialize();
                for (Lazy<ApiPlugin> lazy : apiGroup.getPlugins()) {
                    ApiPlugin apiPlugin = (ApiPlugin) lazy.get();
                    if (this.a.add(apiPlugin)) {
                        apiPlugin.initialize();
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override // ru.mail.verify.core.api.ApiManager
    public final void stop() {
        FileLog.d("ApiManager", "stop started");
        this.d.post(MessageBusUtils.createOneArg(BusMessageType.API_STOP, (Object) null));
        this.e.e();
        ((LockManager) this.c.get()).releaseAllLocks();
        FileLog.d("ApiManager", "stop completed");
    }

    @Override // ru.mail.verify.core.api.ApiManager
    public final void reset() {
        FileLog.d("ApiManager", "reset started");
        this.d.post(MessageBusUtils.createOneArg(BusMessageType.API_RESET, (Object) null));
        ((LockManager) this.c.get()).releaseAllLocks();
        FileLog.d("ApiManager", "reset completed");
    }

    @Override // ru.mail.verify.core.api.ApiManager
    public final void prepare(@NonNull Runnable runnable) {
        this.e.a(runnable);
    }

    @Override // ru.mail.verify.core.api.ApiManager
    public final void onApplicationStartConfigChanged() {
        FileLog.v("ApiManager", "application start config changed");
        this.d.post(MessageBusUtils.createOneArg(BusMessageType.API_APPLICATION_START_CONFIG_CHANGED, (Object) null));
    }

    @Override // ru.mail.verify.core.api.ApiManager
    public final void shutdown() {
        FileLog.d("ApiManager", "shutdown started");
        this.d.post(MessageBusUtils.createOneArg(BusMessageType.API_SHUTDOWN, (Object) null));
        this.e.d();
        ((LockManager) this.c.get()).releaseAllLocks();
        FileLog.d("ApiManager", "shutdown completed");
    }
}
