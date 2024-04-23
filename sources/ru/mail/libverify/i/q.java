package ru.mail.libverify.i;

import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.mail.verify.core.utils.Gsonable;
import ru.mail.verify.core.utils.json.SerializedName;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/i/q.class */
public final class q implements Gsonable {
    @NotNull
    public static final a Companion = new a(0);
    @SerializedName("isCallUIEnabled")
    @Nullable
    private final Boolean callUIEnabled;
    @SerializedName("isCallInEnabled")
    private final boolean callInEnabled;
    @Nullable
    private final String externalId;
    @SerializedName("isStateChangeOnErrorEnabled")
    private final boolean stateChangeOnErrorEnabled;
    private final boolean callEnabled;
    private final boolean mobileIdEnabled;
    private final boolean pushEnabled;
    private final boolean smsEnabled;

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/i/q$a.class */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(int i) {
            this();
        }
    }

    public q() {
        this(null, false, null, false, true, true, true, true, 4, null);
    }

    @Nullable
    public final Boolean c() {
        return this.callUIEnabled;
    }

    public final boolean b() {
        return this.callInEnabled;
    }

    @Nullable
    public final String d() {
        return this.externalId;
    }

    public final boolean h() {
        return this.stateChangeOnErrorEnabled;
    }

    public final boolean a() {
        return this.callEnabled;
    }

    public final boolean e() {
        return this.mobileIdEnabled;
    }

    public final boolean f() {
        return this.pushEnabled;
    }

    public final boolean g() {
        return this.smsEnabled;
    }

    public q(@Nullable Boolean bool, boolean z, @Nullable String str, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6) {
        this.callUIEnabled = bool;
        this.callInEnabled = z;
        this.externalId = str;
        this.stateChangeOnErrorEnabled = z2;
        this.callEnabled = z3;
        this.mobileIdEnabled = z4;
        this.pushEnabled = z5;
        this.smsEnabled = z6;
    }

    public /* synthetic */ q(Boolean bool, boolean z, String str, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : bool, (i & 2) != 0 ? false : z, (i & 4) != 0 ? null : str, (i & 8) != 0 ? true : z2, (i & 16) != 0 ? true : z3, (i & 32) != 0 ? true : z4, (i & 64) != 0 ? true : z5, (i & 128) != 0 ? true : z6);
    }
}
