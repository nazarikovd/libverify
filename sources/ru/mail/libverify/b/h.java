package ru.mail.libverify.b;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.inject.Provider;
import ru.mail.libverify.m.l;
import ru.mail.verify.core.api.ApplicationModule;
import ru.mail.verify.core.api.ApplicationModule_ProvideSocketFactoryProviderFactory;
import ru.mail.verify.core.utils.SocketFactoryProvider;
import ru.mail.verify.core.utils.components.MessageBus;

@ScopeMetadata("ru.mail.libverify.api.VerificationScope")
@DaggerGenerated
@QualifierMetadata
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/b/h.class */
public final class h implements Factory<g> {
    private final Provider<Context> a;
    private final Provider<l> b;
    private final Provider<MessageBus> c;
    private final Provider<ApplicationModule.NetworkPolicyConfig> d;
    private final Provider<SocketFactoryProvider> e;

    public h(Provider provider, Provider provider2, Provider provider3, Provider provider4, ApplicationModule_ProvideSocketFactoryProviderFactory applicationModule_ProvideSocketFactoryProviderFactory) {
        this.a = provider;
        this.b = provider2;
        this.c = provider3;
        this.d = provider4;
        this.e = applicationModule_ProvideSocketFactoryProviderFactory;
    }

    public final Object get() {
        return new g((Context) this.a.get(), (l) this.b.get(), (MessageBus) this.c.get(), (ApplicationModule.NetworkPolicyConfig) this.d.get(), (SocketFactoryProvider) this.e.get());
    }
}
