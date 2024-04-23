package ru.mail.verify.core.api;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.inject.Provider;
import ru.mail.libverify.m.l;
import ru.mail.verify.core.platform.TimeProvider;

@ScopeMetadata
@DaggerGenerated
@QualifierMetadata
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/api/ApplicationModule_ProvideStartTimeFactory.class */
public final class ApplicationModule_ProvideStartTimeFactory implements Factory<ru.mail.libverify.q.b> {
    private final ApplicationModule module;
    private final Provider<l> dataProvider;
    private final Provider<ru.mail.libverify.q.c> repositoryProvider;
    private final Provider<TimeProvider> timeProvider;

    public ApplicationModule_ProvideStartTimeFactory(ApplicationModule module, Provider<l> provider, Provider<ru.mail.libverify.q.c> provider2, Provider<TimeProvider> timeProvider) {
        this.module = module;
        this.dataProvider = provider;
        this.repositoryProvider = provider2;
        this.timeProvider = timeProvider;
    }

    public static ApplicationModule_ProvideStartTimeFactory create(ApplicationModule module, Provider<l> provider, Provider<ru.mail.libverify.q.c> provider2, Provider<TimeProvider> timeProvider) {
        return new ApplicationModule_ProvideStartTimeFactory(module, provider, provider2, timeProvider);
    }

    public static ru.mail.libverify.q.b provideStartTime(ApplicationModule applicationModule, l lVar, ru.mail.libverify.q.c cVar, TimeProvider timeProvider) {
        return (ru.mail.libverify.q.b) Preconditions.checkNotNullFromProvides(applicationModule.provideStartTime(lVar, cVar, timeProvider));
    }

    /* renamed from: get */
    public ru.mail.libverify.q.b m107get() {
        return provideStartTime(this.module, (l) this.dataProvider.get(), (ru.mail.libverify.q.c) this.repositoryProvider.get(), (TimeProvider) this.timeProvider.get());
    }
}
