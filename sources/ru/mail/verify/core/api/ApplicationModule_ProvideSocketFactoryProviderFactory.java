package ru.mail.verify.core.api;

import androidx.annotation.Nullable;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import ru.mail.verify.core.utils.SocketFactoryProvider;

@ScopeMetadata
@DaggerGenerated
@QualifierMetadata
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/api/ApplicationModule_ProvideSocketFactoryProviderFactory.class */
public final class ApplicationModule_ProvideSocketFactoryProviderFactory implements Factory<SocketFactoryProvider> {
    private final ApplicationModule module;

    public ApplicationModule_ProvideSocketFactoryProviderFactory(ApplicationModule module) {
        this.module = module;
    }

    public static ApplicationModule_ProvideSocketFactoryProviderFactory create(ApplicationModule module) {
        return new ApplicationModule_ProvideSocketFactoryProviderFactory(module);
    }

    @Nullable
    public static SocketFactoryProvider provideSocketFactoryProvider(ApplicationModule instance) {
        return instance.provideSocketFactoryProvider();
    }

    @Nullable
    /* renamed from: get */
    public SocketFactoryProvider m105get() {
        return provideSocketFactoryProvider(this.module);
    }
}
