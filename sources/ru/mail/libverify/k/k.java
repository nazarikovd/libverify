package ru.mail.libverify.k;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.mail.verify.core.utils.Gsonable;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/k/k.class */
public final class k implements Gsonable {
    private final long timestamp;
    @NotNull
    private final String from;
    @NotNull
    private final String extracted;

    public k() {
        this(0L, "", "");
    }

    @NotNull
    public final String b() {
        return this.from;
    }

    @NotNull
    public final String a() {
        return this.extracted;
    }

    @NotNull
    public final String toString() {
        return "SmsItem(timestamp=" + this.timestamp + ", from=" + this.from + ", extracted=" + this.extracted + ')';
    }

    public final int hashCode() {
        int hashCode = this.from.hashCode();
        return this.extracted.hashCode() + ((hashCode + (Long.hashCode(this.timestamp) * 31)) * 31);
    }

    public final boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof k) {
            k kVar = (k) obj;
            return this.timestamp == kVar.timestamp && Intrinsics.areEqual(this.from, kVar.from) && Intrinsics.areEqual(this.extracted, kVar.extracted);
        }
        return false;
    }

    public k(long j, @NotNull String str, @NotNull String str2) {
        Intrinsics.checkNotNullParameter(str, "from");
        Intrinsics.checkNotNullParameter(str2, "extracted");
        this.timestamp = j;
        this.from = str;
        this.extracted = str2;
    }
}
