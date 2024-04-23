package ru.mail.libverify.b;

import java.util.List;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.mail.libverify.api.VerificationApi;
import ru.mail.libverify.k.k;
import ru.mail.verify.core.utils.Gsonable;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/b/a.class */
public final class a implements Gsonable {
    @Nullable
    private final List<k> items;
    @NotNull
    private final VerificationApi.AccountCheckResult status;

    public a() {
        this(null, VerificationApi.AccountCheckResult.OK);
    }

    @NotNull
    public final String toString() {
        return "AccountCheckFormatter(items=" + this.items + ", status=" + this.status + ')';
    }

    public final int hashCode() {
        List<k> list = this.items;
        return this.status.hashCode() + ((list == null ? 0 : list.hashCode()) * 31);
    }

    public final boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof a) {
            a aVar = (a) obj;
            return Intrinsics.areEqual(this.items, aVar.items) && this.status == aVar.status;
        }
        return false;
    }

    @JvmOverloads
    public a(@Nullable List<k> list) {
        this(list, null, 2, null);
    }

    @JvmOverloads
    public a(@Nullable List<k> list, @NotNull VerificationApi.AccountCheckResult accountCheckResult) {
        Intrinsics.checkNotNullParameter(accountCheckResult, "status");
        this.items = list;
        this.status = accountCheckResult;
    }

    public /* synthetic */ a(List list, VerificationApi.AccountCheckResult accountCheckResult, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(list, (i & 2) != 0 ? VerificationApi.AccountCheckResult.OK : accountCheckResult);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public a(@NotNull VerificationApi.AccountCheckResult accountCheckResult) {
        this(null, accountCheckResult);
        Intrinsics.checkNotNullParameter(accountCheckResult, "status");
    }
}
