package ru.mail.verify.core.api;

import dagger.Lazy;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import java.lang.Thread;
import java.util.concurrent.RejectedExecutionHandler;
import javax.inject.Provider;
import ru.mail.verify.core.api.ApplicationModule;
import ru.mail.verify.core.storage.LockManager;
import ru.mail.verify.core.utils.components.MessageBus;

@ScopeMetadata("javax.inject.Singleton")
@DaggerGenerated
@QualifierMetadata
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/api/ApiManagerImpl_Factory.class */
public final class ApiManagerImpl_Factory implements Factory<b> {
    private final Provider<MessageBus> busProvider;
    private final Provider<Thread.UncaughtExceptionHandler> exceptionHandlerProvider;
    private final Provider<ApplicationModule.ApplicationStartConfig> configProvider;
    private final Provider<ApplicationModule.NetworkPolicyConfig> networkConfigProvider;
    private final Provider<RejectedExecutionHandler> rejectedHandlerProvider;
    private final Provider<LockManager> locksProvider;

    public ApiManagerImpl_Factory(Provider<MessageBus> busProvider, Provider<Thread.UncaughtExceptionHandler> exceptionHandlerProvider, Provider<ApplicationModule.ApplicationStartConfig> configProvider, Provider<ApplicationModule.NetworkPolicyConfig> networkConfigProvider, Provider<RejectedExecutionHandler> rejectedHandlerProvider, Provider<LockManager> locksProvider) {
        this.busProvider = busProvider;
        this.exceptionHandlerProvider = exceptionHandlerProvider;
        this.configProvider = configProvider;
        this.networkConfigProvider = networkConfigProvider;
        this.rejectedHandlerProvider = rejectedHandlerProvider;
        this.locksProvider = locksProvider;
    }

    public static ApiManagerImpl_Factory create(Provider<MessageBus> busProvider, Provider<Thread.UncaughtExceptionHandler> exceptionHandlerProvider, Provider<ApplicationModule.ApplicationStartConfig> configProvider, Provider<ApplicationModule.NetworkPolicyConfig> networkConfigProvider, Provider<RejectedExecutionHandler> rejectedHandlerProvider, Provider<LockManager> locksProvider) {
        return new ApiManagerImpl_Factory(busProvider, exceptionHandlerProvider, configProvider, networkConfigProvider, rejectedHandlerProvider, locksProvider);
    }

    public static b newInstance(MessageBus bus, Thread.UncaughtExceptionHandler exceptionHandler, ApplicationModule.ApplicationStartConfig config, ApplicationModule.NetworkPolicyConfig networkConfig, RejectedExecutionHandler rejectedHandler, Lazy<LockManager> locks) {
        return new b(bus, exceptionHandler, config, rejectedHandler, locks);
    }

    /* renamed from: get */
    public b m98get() {
        return newInstance((MessageBus) this.busProvider.get(), (Thread.UncaughtExceptionHandler) this.exceptionHandlerProvider.get(), (ApplicationModule.ApplicationStartConfig) this.configProvider.get(), (ApplicationModule.NetworkPolicyConfig) this.networkConfigProvider.get(), (RejectedExecutionHandler) this.rejectedHandlerProvider.get(), DoubleCheck.lazy(this.locksProvider));
    }
}
