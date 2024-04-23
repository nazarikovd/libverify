package ru.mail.libverify.api;

import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import java.lang.Thread;
import java.util.concurrent.RejectedExecutionHandler;
import javax.inject.Provider;
import ru.mail.verify.core.api.AlarmManager;
import ru.mail.verify.core.api.ApiManager;
import ru.mail.verify.core.api.ApplicationModule_ProvideSimCardDataUtilsFactory;
import ru.mail.verify.core.gcm.GcmRegistrar;
import ru.mail.verify.core.platform.TimeProvider;
import ru.mail.verify.core.requests.ActionExecutor;
import ru.mail.verify.core.timer.TimerManager;
import ru.mail.verify.core.ui.notifications.NotificationBarManager;
import ru.mail.verify.core.utils.components.MessageBus;

@ScopeMetadata("ru.mail.libverify.api.VerificationScope")
@DaggerGenerated
@QualifierMetadata
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/w.class */
public final class w implements Factory<q> {
    private final Provider<ApiManager> a;
    private final Provider<ru.mail.libverify.m.l> b;
    private final Provider<MessageBus> c;
    private final Provider<e0> d;
    private final Provider<AlarmManager> e;
    private final Provider<GcmRegistrar> f;
    private final Provider<ActionExecutor> g;
    private final Provider<ru.mail.libverify.l.d> h;
    private final Provider<NotificationBarManager> i;
    private final Provider<Thread.UncaughtExceptionHandler> j;
    private final Provider<RejectedExecutionHandler> k;
    private final Provider<TimerManager> l;
    private final Provider<ru.mail.libverify.q.b> m;
    private final Provider<ru.mail.libverify.q.c> n;
    private final Provider<TimeProvider> o;
    private final Provider<ru.mail.libverify.a.b> p;

    public w(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, ApplicationModule_ProvideSimCardDataUtilsFactory applicationModule_ProvideSimCardDataUtilsFactory) {
        this.a = provider;
        this.b = provider2;
        this.c = provider3;
        this.d = provider4;
        this.e = provider5;
        this.f = provider6;
        this.g = provider7;
        this.h = provider8;
        this.i = provider9;
        this.j = provider10;
        this.k = provider11;
        this.l = provider12;
        this.m = provider13;
        this.n = provider14;
        this.o = provider15;
        this.p = applicationModule_ProvideSimCardDataUtilsFactory;
    }

    public final Object get() {
        return new q((ApiManager) this.a.get(), (ru.mail.libverify.m.l) this.b.get(), (MessageBus) this.c.get(), (e0) this.d.get(), DoubleCheck.lazy(this.e), DoubleCheck.lazy(this.f), DoubleCheck.lazy(this.g), DoubleCheck.lazy(this.h), DoubleCheck.lazy(this.i), (Thread.UncaughtExceptionHandler) this.j.get(), (RejectedExecutionHandler) this.k.get(), (TimerManager) this.l.get(), (ru.mail.libverify.q.b) this.m.get(), (ru.mail.libverify.q.c) this.n.get(), (TimeProvider) this.o.get(), (ru.mail.libverify.a.b) this.p.get());
    }
}
