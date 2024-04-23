package ru.mail.verify.core.api;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil;
import javax.inject.Provider;

@ScopeMetadata
@DaggerGenerated
@QualifierMetadata
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/api/ApplicationModule_ProvidePhoneNumberUtilFactory.class */
public final class ApplicationModule_ProvidePhoneNumberUtilFactory implements Factory<PhoneNumberUtil> {
    private final ApplicationModule module;
    private final Provider<Context> contextProvider;

    public ApplicationModule_ProvidePhoneNumberUtilFactory(ApplicationModule module, Provider<Context> contextProvider) {
        this.module = module;
        this.contextProvider = contextProvider;
    }

    public static ApplicationModule_ProvidePhoneNumberUtilFactory create(ApplicationModule module, Provider<Context> contextProvider) {
        return new ApplicationModule_ProvidePhoneNumberUtilFactory(module, contextProvider);
    }

    public static PhoneNumberUtil providePhoneNumberUtil(ApplicationModule instance, Context context) {
        return (PhoneNumberUtil) Preconditions.checkNotNullFromProvides(instance.providePhoneNumberUtil(context));
    }

    /* renamed from: get */
    public PhoneNumberUtil m102get() {
        return providePhoneNumberUtil(this.module, (Context) this.contextProvider.get());
    }
}
