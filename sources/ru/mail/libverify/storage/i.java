package ru.mail.libverify.storage;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil;
import javax.inject.Provider;
import ru.mail.libverify.platform.storage.KeyValueStorage;
import ru.mail.verify.core.accounts.SimCardReader;
import ru.mail.verify.core.api.AlarmManager;
import ru.mail.verify.core.api.ApplicationModule_ProvidePhoneNumberUtilFactory;
import ru.mail.verify.core.api.NetworkManager;
import ru.mail.verify.core.api.ResourceParamsBase;
import ru.mail.verify.core.gcm.GcmRegistrar;
import ru.mail.verify.core.storage.LocationProvider;

@ScopeMetadata
@DaggerGenerated
@QualifierMetadata
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/storage/i.class */
public final class i implements Factory<h> {
    private final Provider<Context> a;
    private final Provider<ResourceParamsBase> b;
    private final Provider<AlarmManager> c;
    private final Provider<GcmRegistrar> d;
    private final Provider<LocationProvider> e;
    private final Provider<NetworkManager> f;
    private final Provider<KeyValueStorage> g;
    private final Provider<SimCardReader> h;
    private final Provider<PhoneNumberUtil> i;

    public i(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, ApplicationModule_ProvidePhoneNumberUtilFactory applicationModule_ProvidePhoneNumberUtilFactory) {
        this.a = provider;
        this.b = provider2;
        this.c = provider3;
        this.d = provider4;
        this.e = provider5;
        this.f = provider6;
        this.g = provider7;
        this.h = provider8;
        this.i = applicationModule_ProvidePhoneNumberUtilFactory;
    }

    public final Object get() {
        return new h((Context) this.a.get(), (ResourceParamsBase) this.b.get(), DoubleCheck.lazy(this.c), DoubleCheck.lazy(this.d), DoubleCheck.lazy(this.e), DoubleCheck.lazy(this.f), DoubleCheck.lazy(this.g), DoubleCheck.lazy(this.h), DoubleCheck.lazy(this.i));
    }
}
