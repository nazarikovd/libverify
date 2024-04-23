package ru.mail.libverify.o;

import java.io.Serializable;
import java.util.Arrays;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/o/a.class */
public final class a implements Serializable {
    @NotNull
    private final String a;
    @NotNull
    private final byte[] b;

    public a(@NotNull String str, @NotNull byte[] bArr) {
        Intrinsics.checkNotNullParameter(str, "eTag");
        Intrinsics.checkNotNullParameter(bArr, "byteArray");
        this.a = str;
        this.b = bArr;
    }

    @NotNull
    public final String b() {
        return this.a;
    }

    @NotNull
    public final byte[] a() {
        return this.b;
    }

    public final boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (Intrinsics.areEqual(a.class, obj != null ? obj.getClass() : null)) {
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type ru.mail.libverify.storage.images.CachedResponse");
            a aVar = (a) obj;
            return Intrinsics.areEqual(this.a, aVar.a) && Arrays.equals(this.b, aVar.b);
        }
        return false;
    }

    public final int hashCode() {
        return Arrays.hashCode(this.b) + (this.a.hashCode() * 31);
    }

    @NotNull
    public final String toString() {
        return "CachedResponse(eTag=" + this.a + ", byteArray=" + Arrays.toString(this.b) + ')';
    }
}
