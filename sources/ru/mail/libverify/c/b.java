package ru.mail.libverify.c;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.mail.verify.core.utils.Gsonable;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/c/b.class */
public final class b implements Gsonable {
    @NotNull
    private final String location;
    private final int code;

    public b() {
        this("", 0);
    }

    @NotNull
    public final String a() {
        return this.location;
    }

    @NotNull
    public final String toString() {
        return "SessionMobileIdRoute(location=" + this.location + ", code=" + this.code + ')';
    }

    public final int hashCode() {
        return Integer.hashCode(this.code) + (this.location.hashCode() * 31);
    }

    public final boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof b) {
            b bVar = (b) obj;
            return Intrinsics.areEqual(this.location, bVar.location) && this.code == bVar.code;
        }
        return false;
    }

    public b(@NotNull String str, int i) {
        Intrinsics.checkNotNullParameter(str, "location");
        this.location = str;
        this.code = i;
    }
}
