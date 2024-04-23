package ru.mail.libverify.a;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.mail.verify.core.storage.InstanceConfig;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/a/a.class */
public final class a {
    @NotNull
    private final String a;
    @NotNull
    private final String b;

    public a(@NotNull String str) {
        Intrinsics.checkNotNullParameter("", "source");
        Intrinsics.checkNotNullParameter(str, InstanceConfig.DEVICE_TYPE_PHONE);
        this.a = "";
        this.b = str;
    }

    @NotNull
    public final String b() {
        return this.a;
    }

    @NotNull
    public final String a() {
        return this.b;
    }

    @NotNull
    public final String toString() {
        return "PhoneAccount(source=" + this.a + ", phone=" + this.b + ')';
    }

    public final int hashCode() {
        return this.b.hashCode() + (this.a.hashCode() * 31);
    }

    public final boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof a) {
            a aVar = (a) obj;
            return Intrinsics.areEqual(this.a, aVar.a) && Intrinsics.areEqual(this.b, aVar.b);
        }
        return false;
    }
}
