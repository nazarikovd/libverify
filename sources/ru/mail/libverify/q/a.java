package ru.mail.libverify.q;

import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.mail.verify.core.utils.Gsonable;
import ru.mail.verify.core.utils.json.SerializedName;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/q/a.class */
public final class a implements Gsonable {
    @SerializedName("app_version")
    @NotNull
    private final String appVersion;
    private long min;
    private long max;
    private long avg;
    private int count;

    public a() {
        this("");
    }

    @NotNull
    public final String a() {
        return this.appVersion;
    }

    public final boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (Intrinsics.areEqual(a.class, obj != null ? obj.getClass() : null)) {
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type ru.mail.libverify.time.StartTimeData");
            return Intrinsics.areEqual(this.appVersion, ((a) obj).appVersion);
        }
        return false;
    }

    public final int hashCode() {
        return this.appVersion.hashCode();
    }

    @NotNull
    public final String toString() {
        return "StartTimeData(appVersion=" + this.appVersion + ')';
    }

    public a(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "appVersion");
        this.appVersion = str;
        this.min = Long.MAX_VALUE;
        this.max = Long.MIN_VALUE;
    }

    public final void a(long j) {
        this.min = Math.min(this.min, j);
        this.max = Math.max(this.max, j);
        int i = this.count;
        this.avg = MathKt.roundToLong(((float) ((i * this.avg) + j)) / (i + 1.0f));
        this.count++;
    }
}
