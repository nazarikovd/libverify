package ru.mail.libverify.g;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;

@ScopeMetadata
@DaggerGenerated
@QualifierMetadata
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/g/e.class */
public final class e implements Factory<d> {

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/g/e$a.class */
    private static final class a {
        private static final e a = new e();
    }

    public static e a() {
        return a.a;
    }

    public final Object get() {
        return new d();
    }
}
