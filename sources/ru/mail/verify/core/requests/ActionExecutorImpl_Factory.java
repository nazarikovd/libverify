package ru.mail.verify.core.requests;

import dagger.Lazy;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.inject.Provider;
import ru.mail.libverify.platform.storage.KeyValueStorage;
import ru.mail.verify.core.api.ApiManager;
import ru.mail.verify.core.api.NetworkManager;
import ru.mail.verify.core.platform.TimeProvider;
import ru.mail.verify.core.storage.LockManager;
import ru.mail.verify.core.utils.components.MessageBus;

@ScopeMetadata
@DaggerGenerated
@QualifierMetadata
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/requests/ActionExecutorImpl_Factory.class */
public final class ActionExecutorImpl_Factory implements Factory<ActionExecutorImpl> {
    private final Provider<ApiManager> managerProvider;
    private final Provider<NetworkManager> networkProvider;
    private final Provider<KeyValueStorage> storageProvider;
    private final Provider<MessageBus> busProvider;
    private final Provider<LockManager> lockProvider;
    private final Provider<ActionFactory> factoryProvider;
    private final Provider<TimeProvider> timeProvider;

    public ActionExecutorImpl_Factory(Provider<ApiManager> managerProvider, Provider<NetworkManager> networkProvider, Provider<KeyValueStorage> storageProvider, Provider<MessageBus> busProvider, Provider<LockManager> lockProvider, Provider<ActionFactory> factoryProvider, Provider<TimeProvider> timeProvider) {
        this.managerProvider = managerProvider;
        this.networkProvider = networkProvider;
        this.storageProvider = storageProvider;
        this.busProvider = busProvider;
        this.lockProvider = lockProvider;
        this.factoryProvider = factoryProvider;
        this.timeProvider = timeProvider;
    }

    public static ActionExecutorImpl_Factory create(Provider<ApiManager> managerProvider, Provider<NetworkManager> networkProvider, Provider<KeyValueStorage> storageProvider, Provider<MessageBus> busProvider, Provider<LockManager> lockProvider, Provider<ActionFactory> factoryProvider, Provider<TimeProvider> timeProvider) {
        return new ActionExecutorImpl_Factory(managerProvider, networkProvider, storageProvider, busProvider, lockProvider, factoryProvider, timeProvider);
    }

    public static ActionExecutorImpl newInstance(ApiManager manager, NetworkManager network, KeyValueStorage storage, MessageBus bus, LockManager lock, Lazy<ActionFactory> factory, TimeProvider timeProvider) {
        return new ActionExecutorImpl(manager, network, storage, bus, lock, factory, timeProvider);
    }

    /* renamed from: get */
    public ActionExecutorImpl m125get() {
        return newInstance((ApiManager) this.managerProvider.get(), (NetworkManager) this.networkProvider.get(), (KeyValueStorage) this.storageProvider.get(), (MessageBus) this.busProvider.get(), (LockManager) this.lockProvider.get(), DoubleCheck.lazy(this.factoryProvider), (TimeProvider) this.timeProvider.get());
    }
}
