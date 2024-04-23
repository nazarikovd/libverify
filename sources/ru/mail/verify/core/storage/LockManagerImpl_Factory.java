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
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/storage/LockManagerImpl_Factory.class */
public final class LockManagerImpl_Factory implements Factory<LockManagerImpl> {
    private final Provider<Context> contextProvider;

    public LockManagerImpl_Factory(Provider<Context> contextProvider) {
        this.contextProvider = contextProvider;
    }

    public static LockManagerImpl_Factory create(Provider<Context> contextProvider) {
        return new LockManagerImpl_Factory(contextProvider);
    }

    public static LockManagerImpl newInstance(Context context) {
        return new LockManagerImpl(context);
    }

    /* renamed from: get */
    public LockManagerImpl m133get() {
        return newInstance((Context) this.contextProvider.get());
    }
}
