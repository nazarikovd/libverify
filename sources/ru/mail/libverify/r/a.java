package ru.mail.libverify.r;

import android.os.Build;
import org.jetbrains.annotations.NotNull;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/r/a.class */
public final class a {
    private int a;

    @NotNull
    public final a d() {
        this.a |= 134217728;
        return this;
    }

    @NotNull
    public final a b() {
        this.a |= 268435456;
        return this;
    }

    @NotNull
    public final a c() {
        this.a |= Build.VERSION.SDK_INT >= 23 ? 67108864 : 0;
        return this;
    }

    public final int a() {
        return this.a;
    }
}
