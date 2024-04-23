package ru.mail.verify.core.utils;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;

@ScopeMetadata
@DaggerGenerated
@QualifierMetadata
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/utils/SessionIdGeneratorImpl_Factory.class */
public final class SessionIdGeneratorImpl_Factory implements Factory<SessionIdGeneratorImpl> {

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/utils/SessionIdGeneratorImpl_Factory$a.class */
    private static final class a {
        private static final SessionIdGeneratorImpl_Factory a = new SessionIdGeneratorImpl_Factory();
    }

    public static SessionIdGeneratorImpl_Factory create() {
        return a.a;
    }

    public static SessionIdGeneratorImpl newInstance() {
        return new SessionIdGeneratorImpl();
    }

    /* renamed from: get */
    public SessionIdGeneratorImpl m149get() {
        return newInstance();
    }
}
