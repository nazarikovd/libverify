package ru.mail.verify.core.utils.components;

import dagger.Lazy;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.inject.Provider;
import ru.mail.verify.core.api.ApiManager;

@ScopeMetadata
@DaggerGenerated
@QualifierMetadata
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/utils/components/MessageBusImpl_Factory.class */
public final class MessageBusImpl_Factory implements Factory<MessageBusImpl> {
    private final Provider<ApiManager> managerProvider;

    public MessageBusImpl_Factory(Provider<ApiManager> managerProvider) {
        this.managerProvider = managerProvider;
    }

    public static MessageBusImpl_Factory create(Provider<ApiManager> managerProvider) {
        return new MessageBusImpl_Factory(managerProvider);
    }

    public static MessageBusImpl newInstance(Lazy<ApiManager> manager) {
        return new MessageBusImpl(manager);
    }

    /* renamed from: get */
    public MessageBusImpl m155get() {
        return newInstance(DoubleCheck.lazy(this.managerProvider));
    }
}
