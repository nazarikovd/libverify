package ru.mail.libverify.i;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.inject.Provider;

@ScopeMetadata("ru.mail.libverify.api.VerificationScope")
@DaggerGenerated
@QualifierMetadata
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/i/o.class */
public final class o implements Factory<n> {
    private final Provider<ru.mail.libverify.m.l> a;

    public o(Provider<ru.mail.libverify.m.l> provider) {
        this.a = provider;
    }

    public final Object get() {
        return new n((ru.mail.libverify.m.l) this.a.get());
    }
}
