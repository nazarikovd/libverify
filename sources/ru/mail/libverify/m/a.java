package ru.mail.libverify.m;

import android.os.Build;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.Nullable;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/m/a.class */
final class a extends Lambda implements Function0<Object> {
    public static final a a = new a();

    a() {
        super(0);
    }

    @Nullable
    public final Object invoke() {
        return Build.MANUFACTURER;
    }
}
