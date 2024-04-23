package ru.mail.libverify.c;

import java.util.ArrayList;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.mail.verify.core.utils.Gsonable;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/c/a.class */
public final class a implements Gsonable {
    @NotNull
    private final ArrayList<b> redirects;

    public a() {
        this(new ArrayList());
    }

    @NotNull
    public final String toString() {
        return "MobileIdInfo(redirects=" + this.redirects + ')';
    }

    public final int hashCode() {
        return this.redirects.hashCode();
    }

    public final boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof a) && Intrinsics.areEqual(this.redirects, ((a) obj).redirects);
    }

    public a(@NotNull ArrayList<b> arrayList) {
        Intrinsics.checkNotNullParameter(arrayList, "redirects");
        this.redirects = arrayList;
    }
}
