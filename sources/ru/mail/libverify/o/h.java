package ru.mail.libverify.o;

import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.inject.Provider;
import ru.mail.libverify.m.l;

@ScopeMetadata
@DaggerGenerated
@QualifierMetadata
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/o/h.class */
public final class h implements Factory<g> {
    private final Provider<b> a;
    private final Provider<l> b;

    public h(Provider<b> provider, Provider<l> provider2) {
        this.a = provider;
        this.b = provider2;
    }

    public final Object get() {
        return new g(DoubleCheck.lazy(this.a), (l) this.b.get());
    }
}
