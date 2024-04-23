package ru.mail.verify.core.api;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.inject.Provider;
import ru.mail.verify.core.api.ApplicationModule;
import ru.mail.verify.core.utils.SocketFactoryProvider;
import ru.mail.verify.core.utils.components.MessageBus;

@ScopeMetadata
@DaggerGenerated
@QualifierMetadata
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/api/NetworkManagerImpl_Factory.class */
public final class NetworkManagerImpl_Factory implements Factory<NetworkManagerImpl> {
    private final Provider<Context> contextProvider;
    private final Provider<MessageBus> busProvider;
    private final Provider<ApplicationModule.NetworkPolicyConfig> configProvider;
    private final Provider<SocketFactoryProvider> providerProvider;

    public NetworkManagerImpl_Factory(Provider<Context> contextProvider, Provider<MessageBus> busProvider, Provider<ApplicationModule.NetworkPolicyConfig> configProvider, Provider<SocketFactoryProvider> providerProvider) {
        this.contextProvider = contextProvider;
        this.busProvider = busProvider;
        this.configProvider = configProvider;
        this.providerProvider = providerProvider;
    }

    public static NetworkManagerImpl_Factory create(Provider<Context> contextProvider, Provider<MessageBus> busProvider, Provider<ApplicationModule.NetworkPolicyConfig> configProvider, Provider<SocketFactoryProvider> providerProvider) {
        return new NetworkManagerImpl_Factory(contextProvider, busProvider, configProvider, providerProvider);
    }

    public static NetworkManagerImpl newInstance(Context context, MessageBus bus, ApplicationModule.NetworkPolicyConfig config, SocketFactoryProvider provider) {
        return new NetworkManagerImpl(context, bus, config, provider);
    }

    /* renamed from: get */
    public NetworkManagerImpl m115get() {
        return newInstance((Context) this.contextProvider.get(), (MessageBus) this.busProvider.get(), (ApplicationModule.NetworkPolicyConfig) this.configProvider.get(), (SocketFactoryProvider) this.providerProvider.get());
    }
}
