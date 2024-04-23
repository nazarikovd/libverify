package ru.mail.libverify.api;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.inject.Provider;
import ru.mail.verify.core.api.ApplicationModule_ProvideContextFactory;

@ScopeMetadata("ru.mail.libverify.api.VerificationScope")
@DaggerGenerated
@QualifierMetadata
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/f0.class */
public final class f0 implements Factory<e0> {
    private final Provider<Context> a;

    public f0(ApplicationModule_ProvideContextFactory applicationModule_ProvideContextFactory) {
        this.a = applicationModule_ProvideContextFactory;
    }

    public final Object get() {
        return new e0((Context) this.a.get());
    }
}
