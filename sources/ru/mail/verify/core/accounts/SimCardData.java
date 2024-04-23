package ru.mail.verify.core.accounts;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.mail.verify.core.storage.BroadcastManager;
import ru.mail.verify.core.utils.Utils;

@Metadata(mv = {BroadcastManager.FLAG_NETWORK_RECEIVER, 7, BroadcastManager.FLAG_NETWORK_RECEIVER}, k = BroadcastManager.FLAG_NETWORK_RECEIVER, d1 = {"��2\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\u0010\u0002\n��\n\u0002\u0010\u000b\n��\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n��\n\u0002\u0010 \n\u0002\b\u001e\b\u0086\b\u0018�� 52\u00020\u0001:\u00015B9\u0012\b\u0010\u001a\u001a\u0004\u0018\u00010\u0012\u0012\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u0012\u0012\n\b\u0002\u0010\u001c\u001a\u0004\u0018\u00010\u0016\u0012\u000e\b\u0002\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00160\u0018¢\u0006\u0004\b3\u00104J\u0006\u0010\u0003\u001a\u00020\u0002J\u0006\u0010\u0005\u001a\u00020\u0004J\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006J\b\u0010\b\u001a\u0004\u0018\u00010\u0006J\b\u0010\t\u001a\u0004\u0018\u00010\u0006J\b\u0010\n\u001a\u0004\u0018\u00010\u0006J\u000f\u0010\u000b\u001a\u0004\u0018\u00010\u0004¢\u0006\u0004\b\u000b\u0010\fJ\u000f\u0010\r\u001a\u0004\u0018\u00010\u0004¢\u0006\u0004\b\r\u0010\fJ\b\u0010\u000e\u001a\u0004\u0018\u00010\u0006J\b\u0010\u000f\u001a\u0004\u0018\u00010\u0006J\b\u0010\u0010\u001a\u0004\u0018\u00010\u0006J\b\u0010\u0011\u001a\u0004\u0018\u00010\u0006J\u0012\u0010\u0013\u001a\u0004\u0018\u00010\u0012HÆ\u0003¢\u0006\u0004\b\u0013\u0010\u0014J\u0012\u0010\u0015\u001a\u0004\u0018\u00010\u0012HÆ\u0003¢\u0006\u0004\b\u0015\u0010\u0014J\u000b\u0010\u0017\u001a\u0004\u0018\u00010\u0016HÆ\u0003J\u000f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00160\u0018HÆ\u0003JD\u0010\u001e\u001a\u00020��2\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\u00122\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u00122\n\b\u0002\u0010\u001c\u001a\u0004\u0018\u00010\u00162\u000e\b\u0002\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00160\u0018HÆ\u0001¢\u0006\u0004\b\u001e\u0010\u001fJ\t\u0010 \u001a\u00020\u0006HÖ\u0001J\t\u0010!\u001a\u00020\u0012HÖ\u0001J\u0013\u0010#\u001a\u00020\u00042\b\u0010\"\u001a\u0004\u0018\u00010\u0001HÖ\u0003R\u0019\u0010\u001a\u001a\u0004\u0018\u00010\u00128\u0006¢\u0006\f\n\u0004\b$\u0010%\u001a\u0004\b&\u0010\u0014R\u0019\u0010\u001b\u001a\u0004\u0018\u00010\u00128\u0006¢\u0006\f\n\u0004\b'\u0010%\u001a\u0004\b(\u0010\u0014R\u0019\u0010\u001c\u001a\u0004\u0018\u00010\u00168\u0006¢\u0006\f\n\u0004\b)\u0010*\u001a\u0004\b+\u0010,R\u001d\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00160\u00188\u0006¢\u0006\f\n\u0004\b-\u0010.\u001a\u0004\b/\u00100R\u0011\u00101\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b1\u00102¨\u00066"}, d2 = {"Lru/mail/verify/core/accounts/SimCardData;", "", "", "setNoPermissions", "", "hasAtLeastOneReadySim", "", "getHashedImsi", "getHashedImei", "getDoubleHashedImsi", "getDoubleHashedImei", "getRoaming", "()Ljava/lang/Boolean;", "getRoamingNetAllowed", "getSimState", "getNetworkOperator", "getSimOperator", "getSimIsoCountryCode", "", "component1", "()Ljava/lang/Integer;", "component2", "Lru/mail/verify/core/accounts/SimCardItem;", "component3", "", "component4", "slotCount", "simCount", "activeSim", "simList", "copy", "(Ljava/lang/Integer;Ljava/lang/Integer;Lru/mail/verify/core/accounts/SimCardItem;Ljava/util/List;)Lru/mail/verify/core/accounts/SimCardData;", "toString", "hashCode", "other", "equals", "a", "Ljava/lang/Integer;", "getSlotCount", "b", "getSimCount", "c", "Lru/mail/verify/core/accounts/SimCardItem;", "getActiveSim", "()Lru/mail/verify/core/accounts/SimCardItem;", "d", "Ljava/util/List;", "getSimList", "()Ljava/util/List;", "isValid", "()Z", "<init>", "(Ljava/lang/Integer;Ljava/lang/Integer;Lru/mail/verify/core/accounts/SimCardItem;Ljava/util/List;)V", "Companion", "libverify_release"})
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/accounts/SimCardData.class */
public final class SimCardData {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @Nullable
    private final Integer a;
    @Nullable
    private final Integer b;
    @Nullable
    private final SimCardItem c;
    @NotNull
    private final List<SimCardItem> d;
    @Nullable
    private String e;

    @Metadata(mv = {BroadcastManager.FLAG_NETWORK_RECEIVER, 7, BroadcastManager.FLAG_NETWORK_RECEIVER}, k = BroadcastManager.FLAG_NETWORK_RECEIVER, xi = 48, d1 = {"��\u0012\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0010\u000e\n��\b\u0086\u0003\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n��¨\u0006\u0005"}, d2 = {"Lru/mail/verify/core/accounts/SimCardData$Companion;", "", "()V", "NO_PERMISSION_SIM_STATE", "", "libverify_release"})
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/accounts/SimCardData$Companion.class */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SimCardData(@Nullable Integer slotCount, @Nullable Integer simCount, @Nullable SimCardItem activeSim, @NotNull List<? extends SimCardItem> list) {
        Intrinsics.checkNotNullParameter(list, "simList");
        this.a = slotCount;
        this.b = simCount;
        this.c = activeSim;
        this.d = list;
    }

    public static /* synthetic */ SimCardData copy$default(SimCardData simCardData, Integer num, Integer num2, SimCardItem simCardItem, List list, int i, Object obj) {
        if ((i & 1) != 0) {
            num = simCardData.a;
        }
        if ((i & 2) != 0) {
            num2 = simCardData.b;
        }
        if ((i & 4) != 0) {
            simCardItem = simCardData.c;
        }
        if ((i & 8) != 0) {
            list = simCardData.d;
        }
        return simCardData.copy(num, num2, simCardItem, list);
    }

    @Nullable
    public final Integer getSlotCount() {
        return this.a;
    }

    @Nullable
    public final Integer getSimCount() {
        return this.b;
    }

    @Nullable
    public final SimCardItem getActiveSim() {
        return this.c;
    }

    @NotNull
    public final List<SimCardItem> getSimList() {
        return this.d;
    }

    public final void setNoPermissions() {
        this.e = "no_permission";
    }

    public final boolean isValid() {
        SimCardItem simCardItem = this.c;
        String simState = simCardItem != null ? simCardItem.getSimState() : null;
        return ((simState == null || StringsKt.isBlank(simState)) || Intrinsics.areEqual(simState, "no_permission")) ? false : true;
    }

    public final boolean hasAtLeastOneReadySim() {
        SimCardItem simCardItem;
        if (!isValid() || (simCardItem = this.c) == null) {
            return false;
        }
        return simCardItem.f;
    }

    @Nullable
    public final String getHashedImsi() {
        SimCardItem simCardItem = this.c;
        if (simCardItem == null) {
            return null;
        }
        String str = simCardItem.a;
        if (str == null || StringsKt.isBlank(str)) {
            return null;
        }
        return Utils.stringToSHA256(simCardItem.a);
    }

    @Nullable
    public final String getHashedImei() {
        SimCardItem simCardItem = this.c;
        if (simCardItem == null) {
            return null;
        }
        String str = simCardItem.b;
        if (str == null || StringsKt.isBlank(str)) {
            return null;
        }
        return Utils.stringToSHA256(simCardItem.b);
    }

    @Nullable
    public final String getDoubleHashedImsi() {
        SimCardItem simCardItem = this.c;
        if (simCardItem == null) {
            return null;
        }
        String str = simCardItem.a;
        if (str == null || StringsKt.isBlank(str)) {
            return null;
        }
        return Utils.stringToSHA256(Utils.stringToSHA256(simCardItem.a));
    }

    @Nullable
    public final String getDoubleHashedImei() {
        SimCardItem simCardItem = this.c;
        if (simCardItem == null) {
            return null;
        }
        String str = simCardItem.b;
        if (str == null || StringsKt.isBlank(str)) {
            return null;
        }
        return Utils.stringToSHA256(Utils.stringToSHA256(simCardItem.b));
    }

    @Nullable
    public final Boolean getRoaming() {
        SimCardItem simCardItem = this.c;
        if (simCardItem != null) {
            return Boolean.valueOf(simCardItem.g);
        }
        return null;
    }

    @Nullable
    public final Boolean getRoamingNetAllowed() {
        SimCardItem simCardItem = this.c;
        if (simCardItem != null) {
            return Boolean.valueOf(simCardItem.h);
        }
        return null;
    }

    @Nullable
    public final String getSimState() {
        if (this.e == null) {
            SimCardItem simCardItem = this.c;
            this.e = simCardItem != null ? simCardItem.e : null;
        }
        return this.e;
    }

    @Nullable
    public final String getNetworkOperator() {
        SimCardItem simCardItem = this.c;
        if (simCardItem == null || simCardItem.getNetworkMCC() == null || simCardItem.getNetworkMNC() == null) {
            return null;
        }
        return simCardItem.getNetworkMCC() + '-' + simCardItem.getNetworkMNC();
    }

    @Nullable
    public final String getSimOperator() {
        SimCardItem simCardItem = this.c;
        if (simCardItem == null || simCardItem.getOperatorMCC() == null || simCardItem.getOperatorMNC() == null) {
            return null;
        }
        return simCardItem.getOperatorMCC() + '-' + simCardItem.getOperatorMNC();
    }

    @Nullable
    public final String getSimIsoCountryCode() {
        SimCardItem simCardItem = this.c;
        if (simCardItem != null) {
            return simCardItem.c;
        }
        return null;
    }

    @Nullable
    public final Integer component1() {
        return this.a;
    }

    @Nullable
    public final Integer component2() {
        return this.b;
    }

    @Nullable
    public final SimCardItem component3() {
        return this.c;
    }

    @NotNull
    public final List<SimCardItem> component4() {
        return this.d;
    }

    @NotNull
    public final SimCardData copy(@Nullable Integer slotCount, @Nullable Integer simCount, @Nullable SimCardItem activeSim, @NotNull List<? extends SimCardItem> list) {
        Intrinsics.checkNotNullParameter(list, "simList");
        return new SimCardData(slotCount, simCount, activeSim, list);
    }

    @NotNull
    public String toString() {
        return "SimCardData(slotCount=" + this.a + ", simCount=" + this.b + ", activeSim=" + this.c + ", simList=" + this.d + ')';
    }

    public int hashCode() {
        Integer num = this.a;
        int hashCode = (num == null ? 0 : num.hashCode()) * 31;
        Integer num2 = this.b;
        int hashCode2 = (hashCode + (num2 == null ? 0 : num2.hashCode())) * 31;
        SimCardItem simCardItem = this.c;
        return this.d.hashCode() + ((hashCode2 + (simCardItem == null ? 0 : simCardItem.hashCode())) * 31);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof SimCardData) {
            SimCardData simCardData = (SimCardData) other;
            return Intrinsics.areEqual(this.a, simCardData.a) && Intrinsics.areEqual(this.b, simCardData.b) && Intrinsics.areEqual(this.c, simCardData.c) && Intrinsics.areEqual(this.d, simCardData.d);
        }
        return false;
    }

    public /* synthetic */ SimCardData(Integer num, Integer num2, SimCardItem simCardItem, List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(num, (i & 2) != 0 ? null : num2, (i & 4) != 0 ? null : simCardItem, (i & 8) != 0 ? CollectionsKt.emptyList() : list);
    }
}
