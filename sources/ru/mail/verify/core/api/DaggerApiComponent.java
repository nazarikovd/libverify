package ru.mail.verify.core.api;

import dagger.internal.DaggerGenerated;
import dagger.internal.DelegateFactory;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import ru.mail.verify.core.accounts.SimCardReader;
import ru.mail.verify.core.accounts.SimCardReaderImpl_Factory;
import ru.mail.verify.core.storage.LocationProvider;
import ru.mail.verify.core.storage.LocationProviderImpl;
import ru.mail.verify.core.storage.LocationProviderImpl_Factory;
import ru.mail.verify.core.storage.LockManager;
import ru.mail.verify.core.storage.LockManagerImpl;
import ru.mail.verify.core.storage.LockManagerImpl_Factory;
import ru.mail.verify.core.utils.SessionIdGenerator;
import ru.mail.verify.core.utils.SessionIdGeneratorImpl_Factory;
import ru.mail.verify.core.utils.components.MessageBus;
import ru.mail.verify.core.utils.components.MessageBusImpl_Factory;

@DaggerGenerated
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/api/DaggerApiComponent.class */
public final class DaggerApiComponent {

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/api/DaggerApiComponent$Builder.class */
    public static final class Builder {
        private ApplicationModule a;

        private Builder() {
        }

        public Builder applicationModule(ApplicationModule applicationModule) {
            this.a = (ApplicationModule) Preconditions.checkNotNull(applicationModule);
            return this;
        }

        public ApiComponent build() {
            if (this.a == null) {
                this.a = new ApplicationModule();
            }
            return new a(this.a);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/api/DaggerApiComponent$a.class */
    public static final class a implements ApiComponent {
        private DelegateFactory a;
        private Provider<MessageBus> b;
        private ApplicationModule_ProvideThreadUncaughtExceptionHandlerFactory c;
        private ApplicationModule_ProvideStartConfigFactory d;
        private ApplicationModule_ProvideNotifyPolicyConfigFactory e;
        private ApplicationModule_ProvideRejectedExceptionHandlerFactory f;
        private ApplicationModule_ProvideContextFactory g;
        private Provider<LockManagerImpl> h;
        private Provider<AlarmManager> i;
        private Provider<LocationProviderImpl> j;
        private Provider<SessionIdGenerator> k;
        private Provider<SimCardReader> l;

        private a(ApplicationModule applicationModule) {
            a(applicationModule);
        }

        private void a(ApplicationModule applicationModule) {
            DelegateFactory delegateFactory = new DelegateFactory();
            this.a = delegateFactory;
            this.b = DoubleCheck.provider(MessageBusImpl_Factory.create(delegateFactory));
            this.c = ApplicationModule_ProvideThreadUncaughtExceptionHandlerFactory.create(applicationModule);
            this.d = ApplicationModule_ProvideStartConfigFactory.create(applicationModule);
            this.e = ApplicationModule_ProvideNotifyPolicyConfigFactory.create(applicationModule);
            this.f = ApplicationModule_ProvideRejectedExceptionHandlerFactory.create(applicationModule);
            ApplicationModule_ProvideContextFactory create = ApplicationModule_ProvideContextFactory.create(applicationModule);
            this.g = create;
            Provider<LockManagerImpl> provider = DoubleCheck.provider(LockManagerImpl_Factory.create(create));
            this.h = provider;
            DelegateFactory.setDelegate(this.a, DoubleCheck.provider(ApiManagerImpl_Factory.create(this.b, this.c, this.d, this.e, this.f, provider)));
            this.i = DoubleCheck.provider(AlarmManagerImpl_Factory.create(this.g, this.e));
            this.j = DoubleCheck.provider(LocationProviderImpl_Factory.create(this.g));
            this.k = DoubleCheck.provider(SessionIdGeneratorImpl_Factory.create());
            this.l = DoubleCheck.provider(SimCardReaderImpl_Factory.create(this.g));
        }

        @Override // ru.mail.verify.core.api.ApiComponent
        public final ApiManager get() {
            return (ApiManager) this.a.get();
        }

        @Override // ru.mail.verify.core.api.ApiComponent
        public final LockManager getLock() {
            return (LockManager) this.h.get();
        }

        @Override // ru.mail.verify.core.api.ApiComponent
        public final MessageBus getBus() {
            return (MessageBus) this.b.get();
        }

        @Override // ru.mail.verify.core.api.ApiComponent
        public final AlarmManager getAlarmManager() {
            return (AlarmManager) this.i.get();
        }

        @Override // ru.mail.verify.core.api.ApiComponent
        public final LocationProvider getLocation() {
            return (LocationProvider) this.j.get();
        }

        @Override // ru.mail.verify.core.api.ApiComponent
        public final SessionIdGenerator getSessionIdGenerator() {
            return (SessionIdGenerator) this.k.get();
        }

        @Override // ru.mail.verify.core.api.ApiComponent
        public final SimCardReader getSimCardReader() {
            return (SimCardReader) this.l.get();
        }
    }

    private DaggerApiComponent() {
    }

    public static Builder builder() {
        return new Builder();
    }

    public static ApiComponent create() {
        return new Builder().build();
    }
}
