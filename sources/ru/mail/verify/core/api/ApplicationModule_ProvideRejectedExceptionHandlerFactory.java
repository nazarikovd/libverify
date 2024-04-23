package ru.mail.verify.core.api;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import java.util.concurrent.RejectedExecutionHandler;

@ScopeMetadata
@DaggerGenerated
@QualifierMetadata
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/api/ApplicationModule_ProvideRejectedExceptionHandlerFactory.class */
public final class ApplicationModule_ProvideRejectedExceptionHandlerFactory implements Factory<RejectedExecutionHandler> {
    private final ApplicationModule module;

    public ApplicationModule_ProvideRejectedExceptionHandlerFactory(ApplicationModule module) {
        this.module = module;
    }

    public static ApplicationModule_ProvideRejectedExceptionHandlerFactory create(ApplicationModule module) {
        return new ApplicationModule_ProvideRejectedExceptionHandlerFactory(module);
    }

    public static RejectedExecutionHandler provideRejectedExceptionHandler(ApplicationModule instance) {
        return (RejectedExecutionHandler) Preconditions.checkNotNullFromProvides(instance.provideRejectedExceptionHandler());
    }

    /* renamed from: get */
    public RejectedExecutionHandler m103get() {
        return provideRejectedExceptionHandler(this.module);
    }
}
