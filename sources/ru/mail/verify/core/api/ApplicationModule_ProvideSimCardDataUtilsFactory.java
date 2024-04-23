package ru.mail.verify.core.api;

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
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/api/ApplicationModule_ProvideSimCardDataUtilsFactory.class */
public final class ApplicationModule_ProvideSimCardDataUtilsFactory implements Factory<ru.mail.libverify.a.b> {
    private final ApplicationModule module;
    private final Provider<PhoneNumberUtil> phoneNumberUtilProvider;

    public ApplicationModule_ProvideSimCardDataUtilsFactory(ApplicationModule module, Provider<PhoneNumberUtil> phoneNumberUtilProvider) {
        this.module = module;
        this.phoneNumberUtilProvider = phoneNumberUtilProvider;
    }

    public static ApplicationModule_ProvideSimCardDataUtilsFactory create(ApplicationModule module, Provider<PhoneNumberUtil> phoneNumberUtilProvider) {
        return new ApplicationModule_ProvideSimCardDataUtilsFactory(module, phoneNumberUtilProvider);
    }

    public static ru.mail.libverify.a.b provideSimCardDataUtils(ApplicationModule applicationModule, PhoneNumberUtil phoneNumberUtil) {
        return (ru.mail.libverify.a.b) Preconditions.checkNotNullFromProvides(applicationModule.provideSimCardDataUtils(phoneNumberUtil));
    }

    /* renamed from: get */
    public ru.mail.libverify.a.b m104get() {
        return provideSimCardDataUtils(this.module, (PhoneNumberUtil) this.phoneNumberUtilProvider.get());
    }
}
