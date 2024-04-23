package ru.mail.libverify.l;

import android.os.Bundle;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/l/e.class */
public final class e {
    @NotNull
    private final Bundle a = new Bundle();

    @NotNull
    public final Bundle a() {
        return this.a;
    }

    public final void a(@NotNull String str, @Nullable Boolean bool) {
        Intrinsics.checkNotNullParameter(str, "name");
        Intrinsics.checkNotNullParameter(str, "name");
        if (bool == null) {
            this.a.putString(str, null);
            return;
        }
        this.a.putBoolean(str, bool.booleanValue());
        bool.booleanValue();
    }

    public final void a(@NotNull String str, @Nullable Integer num) {
        Intrinsics.checkNotNullParameter(str, "name");
        Intrinsics.checkNotNullParameter(str, "name");
        this.a.putInt(str, num != null ? num.intValue() : 0);
    }

    public final void a(@NotNull String str, @Nullable String str2) {
        Intrinsics.checkNotNullParameter(str, "name");
        Intrinsics.checkNotNullParameter(str, "name");
        this.a.putString(str, str2);
    }
}
