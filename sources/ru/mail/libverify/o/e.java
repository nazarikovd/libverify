package ru.mail.libverify.o;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.inject.Provider;
import ru.mail.libverify.m.l;

@ScopeMetadata
@DaggerGenerated
@QualifierMetadata
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/o/e.class */
public final class e implements Factory<c> {
    private final Provider<l> a;

    public e(Provider<l> provider) {
        this.a = provider;
    }

    public final Object get() {
        return new c((l) this.a.get());
    }
}
