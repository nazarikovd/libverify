package ru.mail.libverify.w;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.inject.Provider;
import ru.mail.libverify.platform.storage.KeyValueStorage;
import ru.mail.verify.core.api.ApiManager;
import ru.mail.verify.core.api.ResourceParamsBase;
import ru.mail.verify.core.storage.LockManager;
import ru.mail.verify.core.utils.components.MessageBus;

@ScopeMetadata
@DaggerGenerated
@QualifierMetadata
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/w/b.class */
public final class b implements Factory<a> {
    private final Provider<Context> a;
    private final Provider<LockManager> b;
    private final Provider<ApiManager> c;
    private final Provider<MessageBus> d;
    private final Provider<ResourceParamsBase> e;
    private final Provider<KeyValueStorage> f;

    public b(Provider<Context> provider, Provider<LockManager> provider2, Provider<ApiManager> provider3, Provider<MessageBus> provider4, Provider<ResourceParamsBase> provider5, Provider<KeyValueStorage> provider6) {
        this.a = provider;
        this.b = provider2;
        this.c = provider3;
        this.d = provider4;
        this.e = provider5;
        this.f = provider6;
    }

    public final Object get() {
        return new a((Context) this.a.get(), (LockManager) this.b.get(), (ApiManager) this.c.get(), (MessageBus) this.d.get(), (ResourceParamsBase) this.e.get(), DoubleCheck.lazy(this.f));
    }
}
