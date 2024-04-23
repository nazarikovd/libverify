package ru.mail.libverify.api;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.mail.verify.core.storage.BroadcastManager;
import ru.mail.verify.core.utils.Gsonable;
import ru.mail.verify.core.utils.json.SerializedName;

@Metadata(mv = {BroadcastManager.FLAG_NETWORK_RECEIVER, 7, BroadcastManager.FLAG_NETWORK_RECEIVER}, k = BroadcastManager.FLAG_NETWORK_RECEIVER, xi = 48, d1 = {"��\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\u000e\n\u0002\b\u0013\u0018��2\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0015\u0010\u001a\u001a\u00020��2\b\u0010\b\u001a\u0004\u0018\u00010\u0004¢\u0006\u0002\u0010\u001bJ\u0015\u0010\u001c\u001a\u00020��2\b\u0010\n\u001a\u0004\u0018\u00010\u0004¢\u0006\u0002\u0010\u001bJ\u0010\u0010\u001d\u001a\u00020��2\b\u0010\u000f\u001a\u0004\u0018\u00010\u000eJ6\u0010\u001e\u001a\u00020��2\u0006\u0010\n\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0014\u001a\u00020\u0004J\u000e\u0010\u001f\u001a\u00020��2\u0006\u0010 \u001a\u00020\u0004R\u001e\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004@BX\u0086\u000e¢\u0006\b\n��\u001a\u0004\b\u0006\u0010\u0007R \u0010\b\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048\u0006@BX\u0087\u000e¢\u0006\b\n��\u001a\u0004\b\t\u0010\u0007R&\u0010\n\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@BX\u0087\u000e¢\u0006\n\n\u0002\u0010\r\u001a\u0004\b\u000b\u0010\fR\"\u0010\u000f\u001a\u0004\u0018\u00010\u000e2\b\u0010\u0003\u001a\u0004\u0018\u00010\u000e@BX\u0086\u000e¢\u0006\b\n��\u001a\u0004\b\u0010\u0010\u0011R\u001e\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004@BX\u0086\u000e¢\u0006\b\n��\u001a\u0004\b\u0013\u0010\u0007R\u001e\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004@BX\u0086\u000e¢\u0006\b\n��\u001a\u0004\b\u0015\u0010\u0007R\u001e\u0010\u0016\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004@BX\u0086\u000e¢\u0006\b\n��\u001a\u0004\b\u0017\u0010\u0007R \u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048\u0006@BX\u0087\u000e¢\u0006\b\n��\u001a\u0004\b\u0019\u0010\u0007¨\u0006!"}, d2 = {"Lru/mail/libverify/api/VerificationParameters;", "Lru/mail/verify/core/utils/Gsonable;", "()V", "<set-?>", "", "callEnabled", "getCallEnabled", "()Z", "callInEnabled", "getCallInEnabled", "callUIEnabled", "getCallUIEnabled", "()Ljava/lang/Boolean;", "Ljava/lang/Boolean;", "", "externalId", "getExternalId", "()Ljava/lang/String;", "mobileIdEnabled", "getMobileIdEnabled", "pushEnabled", "getPushEnabled", "smsEnabled", "getSmsEnabled", "stateChangeOnErrorEnabled", "getStateChangeOnErrorEnabled", "setCallInEnabled", "(Ljava/lang/Boolean;)Lru/mail/libverify/api/VerificationParameters;", "setCallUIEnabled", "setExternalId", "setRoutes", "setStateChangeOnError", "enabled", "libverify_release"})
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/VerificationParameters.class */
public final class VerificationParameters implements Gsonable {
    @Nullable
    private String externalId;
    @SerializedName("isCallUIEnabled")
    @Nullable
    private Boolean callUIEnabled;
    @SerializedName("isCallInEnabled")
    private boolean callInEnabled;
    private boolean callEnabled = true;
    private boolean mobileIdEnabled = true;
    private boolean pushEnabled = true;
    private boolean smsEnabled = true;
    @SerializedName("isStateChangeOnErrorEnabled")
    private boolean stateChangeOnErrorEnabled = true;

    @Nullable
    public final String getExternalId() {
        return this.externalId;
    }

    @Nullable
    public final Boolean getCallUIEnabled() {
        return this.callUIEnabled;
    }

    public final boolean getCallInEnabled() {
        return this.callInEnabled;
    }

    public final boolean getCallEnabled() {
        return this.callEnabled;
    }

    public final boolean getMobileIdEnabled() {
        return this.mobileIdEnabled;
    }

    public final boolean getPushEnabled() {
        return this.pushEnabled;
    }

    public final boolean getSmsEnabled() {
        return this.smsEnabled;
    }

    public final boolean getStateChangeOnErrorEnabled() {
        return this.stateChangeOnErrorEnabled;
    }

    @NotNull
    public final VerificationParameters setCallUIEnabled(@Nullable Boolean callUIEnabled) {
        this.callUIEnabled = callUIEnabled;
        return this;
    }

    @NotNull
    public final VerificationParameters setCallInEnabled(@Nullable Boolean callInEnabled) {
        this.callInEnabled = Intrinsics.areEqual(callInEnabled, Boolean.TRUE);
        return this;
    }

    @NotNull
    public final VerificationParameters setExternalId(@Nullable String externalId) {
        this.externalId = externalId;
        return this;
    }

    @NotNull
    public final VerificationParameters setStateChangeOnError(boolean enabled) {
        this.stateChangeOnErrorEnabled = enabled;
        return this;
    }

    @NotNull
    public final VerificationParameters setRoutes(boolean callUIEnabled, boolean callInEnabled, boolean mobileIdEnabled, boolean smsEnabled, boolean callEnabled, boolean pushEnabled) {
        this.callInEnabled = callInEnabled;
        this.callUIEnabled = Boolean.valueOf(callUIEnabled);
        this.mobileIdEnabled = mobileIdEnabled;
        this.smsEnabled = smsEnabled;
        this.callEnabled = callEnabled;
        this.pushEnabled = pushEnabled;
        return this;
    }
}
