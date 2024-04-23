package ru.mail.verify.core.api;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import ru.mail.verify.core.timer.TimerManager;

@ScopeMetadata
@DaggerGenerated
@QualifierMetadata
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/api/ApplicationModule_ProvideTimerMangerFactory.class */
public final class ApplicationModule_ProvideTimerMangerFactory implements Factory<TimerManager> {
    private final ApplicationModule module;

    public ApplicationModule_ProvideTimerMangerFactory(ApplicationModule module) {
        this.module = module;
    }

    public static ApplicationModule_ProvideTimerMangerFactory create(ApplicationModule module) {
        return new ApplicationModule_ProvideTimerMangerFactory(module);
    }

    public static TimerManager provideTimerManger(ApplicationModule instance) {
        return (TimerManager) Preconditions.checkNotNullFromProvides(instance.provideTimerManger());
    }

    /* renamed from: get */
    public TimerManager m110get() {
        return provideTimerManger(this.module);
    }
}
