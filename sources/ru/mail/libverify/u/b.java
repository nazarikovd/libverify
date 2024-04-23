package ru.mail.libverify.u;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.inject.Provider;

@ScopeMetadata("ru.mail.libverify.api.VerificationScope")
@DaggerGenerated
@QualifierMetadata
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/u/b.class */
public final class b implements Factory<a> {
    private final Provider<Context> a;

    public b(Provider<Context> provider) {
        this.a = provider;
    }

    public final Object get() {
        return new a((Context) this.a.get());
    }
}
