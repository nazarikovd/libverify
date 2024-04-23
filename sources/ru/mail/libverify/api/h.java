package ru.mail.libverify.api;

import dagger.internal.DaggerGenerated;
import dagger.internal.DelegateFactory;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import ru.mail.verify.core.accounts.SimCardReader;
import ru.mail.verify.core.api.AlarmManager;
import ru.mail.verify.core.api.ApiComponent;
import ru.mail.verify.core.api.ApiManager;
import ru.mail.verify.core.api.ApplicationModule;
import ru.mail.verify.core.api.ApplicationModule_ProvideContextFactory;
import ru.mail.verify.core.api.ApplicationModule_ProvideCurrentTimeProviderFactory;
import ru.mail.verify.core.api.ApplicationModule_ProvideNotifyPolicyConfigFactory;
import ru.mail.verify.core.api.ApplicationModule_ProvidePhoneNumberUtilFactory;
import ru.mail.verify.core.api.ApplicationModule_ProvideRejectedExceptionHandlerFactory;
import ru.mail.verify.core.api.ApplicationModule_ProvideSimCardDataUtilsFactory;
import ru.mail.verify.core.api.ApplicationModule_ProvideSocketFactoryProviderFactory;
import ru.mail.verify.core.api.ApplicationModule_ProvideStartTimeFactory;
import ru.mail.verify.core.api.ApplicationModule_ProvideStartTimingsRepositoryFactory;
import ru.mail.verify.core.api.ApplicationModule_ProvideThreadUncaughtExceptionHandlerFactory;
import ru.mail.verify.core.api.ApplicationModule_ProvideTimerMangerFactory;
import ru.mail.verify.core.gcm.GcmRegistrar;
import ru.mail.verify.core.requests.ActionExecutor;
import ru.mail.verify.core.requests.ActionExecutorImpl_Factory;
import ru.mail.verify.core.storage.LocationProvider;
import ru.mail.verify.core.storage.LockManager;
import ru.mail.verify.core.ui.notifications.NotificationBarManager;
import ru.mail.verify.core.ui.notifications.NotificationBarManagerImpl_Factory;
import ru.mail.verify.core.ui.notifications.NotificationChannelSettings;
import ru.mail.verify.core.utils.components.MessageBus;

@DaggerGenerated
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/h.class */
public final class h {

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/h$a.class */
    public static final class a {
        private ApplicationModule a;
        private ApiComponent b;

        private a() {
        }

        public final a a(ApiComponent apiComponent) {
            this.b = (ApiComponent) Preconditions.checkNotNull(apiComponent);
            return this;
        }

        public final a a(ApplicationModule applicationModule) {
            this.a = (ApplicationModule) Preconditions.checkNotNull(applicationModule);
            return this;
        }

        public final ru.mail.libverify.b.d a() {
            if (this.a == null) {
                this.a = new ApplicationModule();
            }
            Preconditions.checkBuilderRequirement(this.b, ApiComponent.class);
            return new b(this.a, this.b);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/h$b.class */
    public static final class b implements ru.mail.libverify.b.d {
        private Provider<ApiManager> a;
        private ApplicationModule_ProvideContextFactory b;
        private Provider<e0> c;
        private Provider<AlarmManager> d;
        private Provider<LockManager> e;
        private Provider<MessageBus> f;
        private Provider<ru.mail.libverify.m.s> g;
        private Provider<GcmRegistrar> h;
        private Provider<LocationProvider> i;
        private DelegateFactory j;
        private ApplicationModule_ProvideNotifyPolicyConfigFactory k;
        private Provider<ru.mail.libverify.b.g> l;
        private Provider<SimCardReader> m;
        private ApplicationModule_ProvidePhoneNumberUtilFactory n;
        private Provider<ru.mail.libverify.i.n> o;
        private ApplicationModule_ProvideCurrentTimeProviderFactory p;
        private Provider<ActionExecutor> q;
        private Provider<ru.mail.libverify.u.a> r;
        private Provider<NotificationChannelSettings> s;
        private Provider<ru.mail.libverify.g.b> t;
        private Provider<NotificationBarManager> u;
        private ApplicationModule_ProvideThreadUncaughtExceptionHandlerFactory v;
        private ApplicationModule_ProvideRejectedExceptionHandlerFactory w;
        private ApplicationModule_ProvideTimerMangerFactory x;
        private ApplicationModule_ProvideStartTimingsRepositoryFactory y;
        private ApplicationModule_ProvideStartTimeFactory z;
        private Provider<q> A;

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/h$b$a.class */
        public static final class a implements Provider<AlarmManager> {
            private final ApiComponent a;

            a(ApiComponent apiComponent) {
                this.a = apiComponent;
            }

            public final Object get() {
                return (AlarmManager) Preconditions.checkNotNullFromComponent(this.a.getAlarmManager());
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: ru.mail.libverify.api.h$b$b  reason: collision with other inner class name */
        /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/h$b$b.class */
        public static final class C0002b implements Provider<MessageBus> {
            private final ApiComponent a;

            C0002b(ApiComponent apiComponent) {
                this.a = apiComponent;
            }

            public final Object get() {
                return (MessageBus) Preconditions.checkNotNullFromComponent(this.a.getBus());
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/h$b$c.class */
        public static final class c implements Provider<LocationProvider> {
            private final ApiComponent a;

            c(ApiComponent apiComponent) {
                this.a = apiComponent;
            }

            public final Object get() {
                return (LocationProvider) Preconditions.checkNotNullFromComponent(this.a.getLocation());
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/h$b$d.class */
        public static final class d implements Provider<LockManager> {
            private final ApiComponent a;

            d(ApiComponent apiComponent) {
                this.a = apiComponent;
            }

            public final Object get() {
                return (LockManager) Preconditions.checkNotNullFromComponent(this.a.getLock());
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/h$b$e.class */
        public static final class e implements Provider<ApiManager> {
            private final ApiComponent a;

            e(ApiComponent apiComponent) {
                this.a = apiComponent;
            }

            public final Object get() {
                return (ApiManager) Preconditions.checkNotNullFromComponent(this.a.get());
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/h$b$f.class */
        public static final class f implements Provider<SimCardReader> {
            private final ApiComponent a;

            f(ApiComponent apiComponent) {
                this.a = apiComponent;
            }

            public final Object get() {
                return (SimCardReader) Preconditions.checkNotNullFromComponent(this.a.getSimCardReader());
            }
        }

        private b(ApplicationModule applicationModule, ApiComponent apiComponent) {
            a(applicationModule, apiComponent);
        }

        public final VerificationApi a() {
            return (VerificationApi) this.A.get();
        }

        private void a(ApplicationModule applicationModule, ApiComponent apiComponent) {
            this.a = new e(apiComponent);
            ApplicationModule_ProvideContextFactory create = ApplicationModule_ProvideContextFactory.create(applicationModule);
            this.b = create;
            this.c = DoubleCheck.provider(new f0(create));
            this.d = new a(apiComponent);
            this.e = new d(apiComponent);
            this.f = new C0002b(apiComponent);
            Provider<ru.mail.libverify.m.s> provider = DoubleCheck.provider(new ru.mail.libverify.m.t(this.a, this.b));
            this.g = provider;
            this.h = DoubleCheck.provider(new ru.mail.libverify.w.b(this.b, this.e, this.a, this.f, this.c, provider));
            this.i = new c(apiComponent);
            this.j = new DelegateFactory();
            this.k = ApplicationModule_ProvideNotifyPolicyConfigFactory.create(applicationModule);
            this.l = DoubleCheck.provider(new ru.mail.libverify.b.h(this.b, this.j, this.f, this.k, ApplicationModule_ProvideSocketFactoryProviderFactory.create(applicationModule)));
            this.m = new f(apiComponent);
            ApplicationModule_ProvidePhoneNumberUtilFactory create2 = ApplicationModule_ProvidePhoneNumberUtilFactory.create(applicationModule, this.b);
            this.n = create2;
            DelegateFactory.setDelegate(this.j, DoubleCheck.provider(new ru.mail.libverify.storage.i(this.b, this.c, this.d, this.h, this.i, this.l, this.g, this.m, create2)));
            this.o = DoubleCheck.provider(new ru.mail.libverify.i.o(this.j));
            ApplicationModule_ProvideCurrentTimeProviderFactory create3 = ApplicationModule_ProvideCurrentTimeProviderFactory.create(applicationModule, this.g);
            this.p = create3;
            this.q = DoubleCheck.provider(ActionExecutorImpl_Factory.create(this.a, this.l, this.g, this.f, this.e, this.o, create3));
            this.r = DoubleCheck.provider(new ru.mail.libverify.u.b(this.b));
            this.s = DoubleCheck.provider(ru.mail.libverify.g.e.a());
            this.t = DoubleCheck.provider(new ru.mail.libverify.g.c(this.b, this.j));
            this.u = DoubleCheck.provider(NotificationBarManagerImpl_Factory.create(this.b, this.f, this.a, this.s, this.t, DoubleCheck.provider(new ru.mail.libverify.o.h(DoubleCheck.provider(new ru.mail.libverify.o.e(this.j)), this.j))));
            this.v = ApplicationModule_ProvideThreadUncaughtExceptionHandlerFactory.create(applicationModule);
            this.w = ApplicationModule_ProvideRejectedExceptionHandlerFactory.create(applicationModule);
            this.x = ApplicationModule_ProvideTimerMangerFactory.create(applicationModule);
            ApplicationModule_ProvideStartTimingsRepositoryFactory create4 = ApplicationModule_ProvideStartTimingsRepositoryFactory.create(applicationModule, this.j);
            this.y = create4;
            this.z = ApplicationModule_ProvideStartTimeFactory.create(applicationModule, this.j, create4, this.p);
            this.A = DoubleCheck.provider(new w(this.a, this.j, this.f, this.c, this.d, this.h, this.q, this.r, this.u, this.v, this.w, this.x, this.z, this.y, this.p, ApplicationModule_ProvideSimCardDataUtilsFactory.create(applicationModule, this.n)));
        }
    }

    public static a a() {
        return new a();
    }
}
