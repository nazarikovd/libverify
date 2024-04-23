package ru.mail.libverify.utils;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.mail.verify.core.storage.BroadcastManager;

@Metadata(mv = {BroadcastManager.FLAG_NETWORK_RECEIVER, 7, BroadcastManager.FLAG_NETWORK_RECEIVER}, k = BroadcastManager.FLAG_NETWORK_RECEIVER, xi = 48, d1 = {"��$\n\u0002\u0018\u0002\n\u0002\u0010��\n��\n\u0002\u0010\u000b\n��\n\u0002\u0010\t\n\u0002\b\u000f\n\u0002\u0010\b\n��\n\u0002\u0010\u000e\n��\b\u0086\b\u0018��2\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u0005HÂ\u0003¢\u0006\u0002\u0010\u000fJ$\u0010\u0010\u001a\u00020��2\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005HÆ\u0001¢\u0006\u0002\u0010\u0011J\u0013\u0010\u0012\u001a\u00020\u00032\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001R\u0011\u0010\u0007\u001a\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0011\u0010\n\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n��\u001a\u0004\b\u0002\u0010\u000bR\u0012\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\f¨\u0006\u0018"}, d2 = {"Lru/mail/libverify/utils/ScreenState;", "", "isScreenActive", "", "mInactiveTime", "", "(ZLjava/lang/Long;)V", "inactiveTime", "getInactiveTime", "()J", "isInactiveTimeAvailable", "()Z", "Ljava/lang/Long;", "component1", "component2", "()Ljava/lang/Long;", "copy", "(ZLjava/lang/Long;)Lru/mail/libverify/utils/ScreenState;", "equals", "other", "hashCode", "", "toString", "", "libverify_release"})
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/utils/ScreenState.class */
public final class ScreenState {
    private final boolean isScreenActive;
    @Nullable
    private final Long mInactiveTime;

    public ScreenState(boolean isScreenActive, @Nullable Long mInactiveTime) {
        this.isScreenActive = isScreenActive;
        this.mInactiveTime = mInactiveTime;
    }

    private final Long component2() {
        return this.mInactiveTime;
    }

    public static /* synthetic */ ScreenState copy$default(ScreenState screenState, boolean z, Long l, int i, Object obj) {
        if ((i & 1) != 0) {
            z = screenState.isScreenActive;
        }
        if ((i & 2) != 0) {
            l = screenState.mInactiveTime;
        }
        return screenState.copy(z, l);
    }

    public final boolean isScreenActive() {
        return this.isScreenActive;
    }

    public final long getInactiveTime() {
        Long l = this.mInactiveTime;
        if (l != null) {
            return l.longValue();
        }
        return 0L;
    }

    public final boolean isInactiveTimeAvailable() {
        return this.mInactiveTime != null;
    }

    public final boolean component1() {
        return this.isScreenActive;
    }

    @NotNull
    public final ScreenState copy(boolean isScreenActive, @Nullable Long mInactiveTime) {
        return new ScreenState(isScreenActive, mInactiveTime);
    }

    @NotNull
    public String toString() {
        return "ScreenState(isScreenActive=" + this.isScreenActive + ", mInactiveTime=" + this.mInactiveTime + ')';
    }

    public int hashCode() {
        boolean z = this.isScreenActive;
        boolean z2 = z;
        if (z) {
            z2 = true;
        }
        int i = (z2 ? 1 : 0) * 31;
        Long l = this.mInactiveTime;
        return i + (l == null ? 0 : l.hashCode());
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof ScreenState) {
            ScreenState screenState = (ScreenState) other;
            return this.isScreenActive == screenState.isScreenActive && Intrinsics.areEqual(this.mInactiveTime, screenState.mInactiveTime);
        }
        return false;
    }
}
