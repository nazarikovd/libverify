package ru.mail.verify.core.api;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import ru.mail.verify.core.api.ApplicationModule;

@ScopeMetadata
@DaggerGenerated
@QualifierMetadata
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/api/ApplicationModule_ProvideStartConfigFactory.class */
public final class ApplicationModule_ProvideStartConfigFactory implements Factory<ApplicationModule.ApplicationStartConfig> {
    private final ApplicationModule module;

    public ApplicationModule_ProvideStartConfigFactory(ApplicationModule module) {
        this.module = module;
    }

    public static ApplicationModule_ProvideStartConfigFactory create(ApplicationModule module) {
        return new ApplicationModule_ProvideStartConfigFactory(module);
    }

    public static ApplicationModule.ApplicationStartConfig provideStartConfig(ApplicationModule instance) {
        return (ApplicationModule.ApplicationStartConfig) Preconditions.checkNotNullFromProvides(instance.provideStartConfig());
    }

    /* renamed from: get */
    public ApplicationModule.ApplicationStartConfig m106get() {
        return provideStartConfig(this.module);
    }
}
