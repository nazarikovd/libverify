package ru.mail.verify.core.storage;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@DaggerGenerated
@QualifierMetadata
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/storage/LocationProviderImpl_Factory.class */
public final class LocationProviderImpl_Factory implements Factory<LocationProviderImpl> {
    private final Provider<Context> contextProvider;

    public LocationProviderImpl_Factory(Provider<Context> contextProvider) {
        this.contextProvider = contextProvider;
    }

    public static LocationProviderImpl_Factory create(Provider<Context> contextProvider) {
        return new LocationProviderImpl_Factory(contextProvider);
    }

    public static LocationProviderImpl newInstance(Context context) {
        return new LocationProviderImpl(context);
    }

    /* renamed from: get */
    public LocationProviderImpl m132get() {
        return newInstance((Context) this.contextProvider.get());
    }
}
