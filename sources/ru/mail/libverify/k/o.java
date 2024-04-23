package ru.mail.libverify.k;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.mail.platform.libverify.sms.SmsRetrieverService;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/k/o.class */
public final class o {
    @NotNull
    private final SmsRetrieverService a;
    private final int b;
    @NotNull
    private final String c;
    private final long d;

    public o(@NotNull SmsRetrieverService smsRetrieverService, int i, @NotNull String str, long j) {
        Intrinsics.checkNotNullParameter(smsRetrieverService, "smsRetrieverService");
        Intrinsics.checkNotNullParameter(str, "resultMessage");
        this.a = smsRetrieverService;
        this.b = i;
        this.c = str;
        this.d = j;
    }

    @NotNull
    public final SmsRetrieverService c() {
        return this.a;
    }

    public final int b() {
        return this.b;
    }

    @NotNull
    public final String a() {
        return this.c;
    }

    public final long d() {
        return this.d;
    }

    @NotNull
    public final String toString() {
        return "SmsRetrieverState(smsRetrieverService=" + this.a + ", resultStatus=" + this.b + ", resultMessage=" + this.c + ", time=" + this.d + ')';
    }

    public final int hashCode() {
        int hashCode = Integer.hashCode(this.b);
        int hashCode2 = this.c.hashCode();
        return Long.hashCode(this.d) + ((hashCode2 + ((hashCode + (this.a.hashCode() * 31)) * 31)) * 31);
    }

    public final boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof o) {
            o oVar = (o) obj;
            return Intrinsics.areEqual(this.a, oVar.a) && this.b == oVar.b && Intrinsics.areEqual(this.c, oVar.c) && this.d == oVar.d;
        }
        return false;
    }
}
