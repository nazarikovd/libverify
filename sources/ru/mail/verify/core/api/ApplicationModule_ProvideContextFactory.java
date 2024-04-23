package ru.mail.verify.core.api;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;

@ScopeMetadata
@DaggerGenerated
@QualifierMetadata
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/api/ApplicationModule_ProvideContextFactory.class */
public final class ApplicationModule_ProvideContextFactory implements Factory<Context> {
    private final ApplicationModule module;

    public ApplicationModule_ProvideContextFactory(ApplicationModule module) {
        this.module = module;
    }

    public static ApplicationModule_ProvideContextFactory create(ApplicationModule module) {
        return new ApplicationModule_ProvideContextFactory(module);
    }

    public static Context provideContext(ApplicationModule instance) {
        return (Context) Preconditions.checkNotNullFromProvides(instance.provideContext());
    }

    /* renamed from: get */
    public Context m99get() {
        return provideContext(this.module);
    }
}
