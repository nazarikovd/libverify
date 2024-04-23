package ru.mail.verify.core.api;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.inject.Provider;
import ru.mail.libverify.m.l;

@ScopeMetadata
@DaggerGenerated
@QualifierMetadata
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/api/ApplicationModule_ProvideStartTimingsRepositoryFactory.class */
public final class ApplicationModule_ProvideStartTimingsRepositoryFactory implements Factory<ru.mail.libverify.q.c> {
    private final ApplicationModule module;
    private final Provider<l> dataProvider;

    public ApplicationModule_ProvideStartTimingsRepositoryFactory(ApplicationModule module, Provider<l> provider) {
        this.module = module;
        this.dataProvider = provider;
    }

    public static ApplicationModule_ProvideStartTimingsRepositoryFactory create(ApplicationModule module, Provider<l> provider) {
        return new ApplicationModule_ProvideStartTimingsRepositoryFactory(module, provider);
    }

    public static ru.mail.libverify.q.c provideStartTimingsRepository(ApplicationModule applicationModule, l lVar) {
        return (ru.mail.libverify.q.c) Preconditions.checkNotNullFromProvides(applicationModule.provideStartTimingsRepository(lVar));
    }

    /* renamed from: get */
    public ru.mail.libverify.q.c m108get() {
        return provideStartTimingsRepository(this.module, (l) this.dataProvider.get());
    }
}
