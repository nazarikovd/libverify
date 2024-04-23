package ru.mail.verify.core.accounts;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.inject.Provider;

@ScopeMetadata
@DaggerGenerated
@QualifierMetadata
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/accounts/SimCardReaderImpl_Factory.class */
public final class SimCardReaderImpl_Factory implements Factory<SimCardReaderImpl> {
    private final Provider<Context> contextProvider;

    public SimCardReaderImpl_Factory(Provider<Context> contextProvider) {
        this.contextProvider = contextProvider;
    }

    public static SimCardReaderImpl_Factory create(Provider<Context> contextProvider) {
        return new SimCardReaderImpl_Factory(contextProvider);
    }

    public static SimCardReaderImpl newInstance(Context context) {
        return new SimCardReaderImpl(context);
    }

    /* renamed from: get */
    public SimCardReaderImpl m96get() {
        return newInstance((Context) this.contextProvider.get());
    }
}
