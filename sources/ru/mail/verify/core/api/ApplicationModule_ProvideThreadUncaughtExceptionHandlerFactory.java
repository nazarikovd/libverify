package ru.mail.verify.core.api;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import java.lang.Thread;

@ScopeMetadata
@DaggerGenerated
@QualifierMetadata
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/api/ApplicationModule_ProvideThreadUncaughtExceptionHandlerFactory.class */
public final class ApplicationModule_ProvideThreadUncaughtExceptionHandlerFactory implements Factory<Thread.UncaughtExceptionHandler> {
    private final ApplicationModule module;

    public ApplicationModule_ProvideThreadUncaughtExceptionHandlerFactory(ApplicationModule module) {
        this.module = module;
    }

    public static ApplicationModule_ProvideThreadUncaughtExceptionHandlerFactory create(ApplicationModule module) {
        return new ApplicationModule_ProvideThreadUncaughtExceptionHandlerFactory(module);
    }

    public static Thread.UncaughtExceptionHandler provideThreadUncaughtExceptionHandler(ApplicationModule instance) {
        return (Thread.UncaughtExceptionHandler) Preconditions.checkNotNullFromProvides(instance.provideThreadUncaughtExceptionHandler());
    }

    /* renamed from: get */
    public Thread.UncaughtExceptionHandler m109get() {
        return provideThreadUncaughtExceptionHandler(this.module);
    }
}
