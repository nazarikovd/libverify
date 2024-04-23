package ru.mail.libverify.g;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.inject.Provider;
import ru.mail.libverify.m.l;
import ru.mail.libverify.notifications.f;

@ScopeMetadata
@DaggerGenerated
@QualifierMetadata
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/g/c.class */
public final class c implements Factory<f> {
    private final Provider<Context> a;
    private final Provider<l> b;

    public c(Provider<Context> provider, Provider<l> provider2) {
        this.a = provider;
        this.b = provider2;
    }

    public final Object get() {
        return new f((Context) this.a.get(), (l) this.b.get());
    }
}
