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
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/api/ApplicationModule_ProvideNotifyPolicyConfigFactory.class */
public final class ApplicationModule_ProvideNotifyPolicyConfigFactory implements Factory<ApplicationModule.NetworkPolicyConfig> {
    private final ApplicationModule module;

    public ApplicationModule_ProvideNotifyPolicyConfigFactory(ApplicationModule module) {
        this.module = module;
    }

    public static ApplicationModule_ProvideNotifyPolicyConfigFactory create(ApplicationModule module) {
        return new ApplicationModule_ProvideNotifyPolicyConfigFactory(module);
    }

    public static ApplicationModule.NetworkPolicyConfig provideNotifyPolicyConfig(ApplicationModule instance) {
        return (ApplicationModule.NetworkPolicyConfig) Preconditions.checkNotNullFromProvides(instance.provideNotifyPolicyConfig());
    }

    /* renamed from: get */
    public ApplicationModule.NetworkPolicyConfig m101get() {
        return provideNotifyPolicyConfig(this.module);
    }
}
