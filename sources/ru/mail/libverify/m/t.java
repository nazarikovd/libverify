package ru.mail.libverify.m;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.inject.Provider;
import ru.mail.verify.core.api.ApiManager;

@ScopeMetadata("ru.mail.libverify.api.VerificationScope")
@DaggerGenerated
@QualifierMetadata
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/m/t.class */
public final class t implements Factory<s> {
    private final Provider<ApiManager> a;
    private final Provider<Context> b;

    public t(Provider<ApiManager> provider, Provider<Context> provider2) {
        this.a = provider;
        this.b = provider2;
    }

    public final Object get() {
        return new s((Context) this.b.get(), (ApiManager) this.a.get());
    }
}
