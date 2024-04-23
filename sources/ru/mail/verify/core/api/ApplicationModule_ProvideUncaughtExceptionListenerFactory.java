package ru.mail.verify.core.api;

import androidx.annotation.Nullable;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;

@ScopeMetadata
@DaggerGenerated
@QualifierMetadata
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/api/ApplicationModule_ProvideUncaughtExceptionListenerFactory.class */
public final class ApplicationModule_ProvideUncaughtExceptionListenerFactory implements Factory<UncaughtExceptionListener> {
    private final ApplicationModule module;

    public ApplicationModule_ProvideUncaughtExceptionListenerFactory(ApplicationModule module) {
        this.module = module;
    }

    public static ApplicationModule_ProvideUncaughtExceptionListenerFactory create(ApplicationModule module) {
        return new ApplicationModule_ProvideUncaughtExceptionListenerFactory(module);
    }

    @Nullable
    public static UncaughtExceptionListener provideUncaughtExceptionListener(ApplicationModule instance) {
        return instance.provideUncaughtExceptionListener();
    }

    @Nullable
    /* renamed from: get */
    public UncaughtExceptionListener m111get() {
        return provideUncaughtExceptionListener(this.module);
    }
}
