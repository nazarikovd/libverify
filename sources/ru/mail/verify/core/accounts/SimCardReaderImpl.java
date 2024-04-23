package ru.mail.verify.core.accounts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.telephony.SubscriptionManager;
import java.util.ArrayList;
import java.util.Locale;
import javax.inject.Inject;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import ru.mail.libverify.s.a;
import ru.mail.verify.core.accounts.SimCardItem;
import ru.mail.verify.core.storage.BroadcastManager;
import ru.mail.verify.core.utils.FileLog;
import ru.mail.verify.core.utils.Utils;

@Metadata(mv = {BroadcastManager.FLAG_NETWORK_RECEIVER, 7, BroadcastManager.FLAG_NETWORK_RECEIVER}, k = BroadcastManager.FLAG_NETWORK_RECEIVER, d1 = {"��\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018�� \b2\u00020\u0001:\u0001\bB\u0011\b\u0007\u0012\u0006\u0010\u0005\u001a\u00020\u0004¢\u0006\u0004\b\u0006\u0010\u0007J\b\u0010\u0003\u001a\u00020\u0002H\u0016¨\u0006\t"}, d2 = {"Lru/mail/verify/core/accounts/SimCardReaderImpl;", "Lru/mail/verify/core/accounts/SimCardReader;", "Lru/mail/verify/core/accounts/SimCardData;", "getSimCardData", "Landroid/content/Context;", "context", "<init>", "(Landroid/content/Context;)V", "Companion", "libverify_release"})
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/accounts/SimCardReaderImpl.class */
public final class SimCardReaderImpl implements SimCardReader {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final Context a;

    @Metadata(mv = {BroadcastManager.FLAG_NETWORK_RECEIVER, 7, BroadcastManager.FLAG_NETWORK_RECEIVER}, k = BroadcastManager.FLAG_NETWORK_RECEIVER, xi = 48, d1 = {"��\u0012\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0010\u000e\n��\b\u0086\u0003\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n��¨\u0006\u0005"}, d2 = {"Lru/mail/verify/core/accounts/SimCardReaderImpl$Companion;", "", "()V", "LOG_TAG", "", "libverify_release"})
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/accounts/SimCardReaderImpl$Companion.class */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    @Inject
    public SimCardReaderImpl(@NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.a = context;
    }

    @SuppressLint({"HardwareIds", "MissingPermission"})
    private static SimCardItem a(a aVar, String str) {
        String str2;
        SimCardItem.Builder builder = new SimCardItem.Builder();
        int f = aVar.f();
        switch (f) {
            case 0:
                str2 = "unknown";
                break;
            case BroadcastManager.FLAG_NETWORK_RECEIVER /* 1 */:
                str2 = "absent";
                break;
            case 2:
                str2 = "pin_required";
                break;
            case 3:
                str2 = "puk_required";
                break;
            case 4:
                str2 = "network_locked";
                break;
            case 5:
                str2 = "ready";
                break;
            case 6:
                str2 = "not_ready";
                break;
            case 7:
                str2 = "perm_disabled";
                break;
            case 8:
                str2 = "card_io_error";
                break;
            default:
                str2 = null;
                break;
        }
        builder.setSimState(str2);
        builder.setReady(f == 5);
        if (f == 5) {
            builder.setSimPhoneNumber(aVar.a());
            builder.setImei(aVar.i());
            builder.setSubscriberId(aVar.m());
            builder.setOperator(aVar.j());
            builder.setOperatorName(aVar.k());
            builder.setNetworkOperator(aVar.d());
            builder.setNetworkOperatorName(aVar.e());
            builder.setNetworkCountryIso(aVar.c());
            builder.setNetworkRoaming(aVar.o());
            builder.setRoamingDataAllowed(aVar.p());
            builder.setSlotIndex(Integer.valueOf(aVar.b()));
            String h = aVar.h();
            if (h != null) {
                String upperCase = h.toUpperCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(upperCase, "this as java.lang.String).toUpperCase(Locale.ROOT)");
                builder.setSimCountryIso(upperCase);
            } else {
                Intrinsics.checkNotNullExpressionValue(builder.setSimCountryIso(""), "setSimCountryIso(\"\")");
            }
            builder.setImsi(aVar.a(str));
        }
        SimCardItem build = builder.build();
        Intrinsics.checkNotNullExpressionValue(build, "builder.apply {\n        …      }\n        }.build()");
        return build;
    }

    @Override // ru.mail.verify.core.accounts.SimCardReader
    @NotNull
    public SimCardData getSimCardData() {
        boolean z;
        a aVar;
        SimCardData simCardData;
        a aVar2;
        Integer num;
        FileLog.v("SimCardReader", "sim card read start");
        String systemId = Utils.getSystemId(this.a);
        String str = systemId;
        if (systemId == null) {
            str = "";
        }
        if (Utils.hasSelfPermission(this.a, new String[]{"android.permission.READ_PHONE_STATE"})) {
            z = true;
        } else {
            FileLog.v("SimCardReader", "can't read sim data without %s", "android.permission.READ_PHONE_STATE");
            z = false;
        }
        if (!z) {
            if (Build.VERSION.SDK_INT >= 22) {
                SubscriptionManager subscriptionManager = (SubscriptionManager) this.a.getSystemService("telephony_subscription_service");
                num = subscriptionManager != null ? Integer.valueOf(subscriptionManager.getActiveSubscriptionInfoCountMax()) : null;
            } else {
                num = 1;
            }
            simCardData = r0;
            SimCardData simCardData2 = new SimCardData(num, null, null, null, 14, null);
            simCardData2.setNoPermissions();
        } else {
            FileLog.v("SimCardReader", "readData started");
            SimCardItem simCardItem = null;
            Integer num2 = null;
            Integer num3 = null;
            ArrayList arrayList = new ArrayList();
            try {
                try {
                    aVar = a.C0020a.a(this.a, null);
                } catch (Exception e) {
                    Object[] objArr = new Object[1];
                    objArr[0] = e.getMessage();
                    FileLog.e("SimCardReader", "Failed to getTelephonyManager: ", objArr);
                    aVar = null;
                }
            } catch (Exception e2) {
                FileLog.e("SimCardReader", "readData failed to read sim card data items", e2.getMessage());
            }
            if (aVar == null) {
                throw new IllegalStateException("Can not create telephonyManager");
            }
            a aVar3 = aVar;
            num2 = Integer.valueOf(aVar3.g());
            Integer valueOf = Integer.valueOf(aVar3.l());
            num3 = valueOf;
            simCardItem = a(aVar, str);
            int intValue = valueOf.intValue();
            for (int i = 0; i < intValue; i++) {
                try {
                    try {
                        aVar2 = a.C0020a.a(this.a, Integer.valueOf(i));
                    } catch (Exception e3) {
                        Object[] objArr2 = new Object[1];
                        objArr2[0] = e3.getMessage();
                        FileLog.e("SimCardReader", "Failed to getTelephonyManager: ", objArr2);
                        aVar2 = null;
                    }
                } catch (Exception e4) {
                    Object[] objArr3 = new Object[1];
                    objArr3[0] = e4.getMessage();
                    FileLog.e("SimCardReader", "readData failed to read sim card item", objArr3);
                }
                if (aVar2 == null) {
                    throw new IllegalStateException("Can not create telephonyManager");
                    break;
                }
                arrayList.add(a(aVar2, str));
            }
            simCardData = r0;
            SimCardData simCardData3 = new SimCardData(num3, num2, simCardItem, arrayList);
        }
        SimCardData simCardData4 = simCardData;
        FileLog.v("SimCardReader", "sim card read result %s", Boolean.valueOf(simCardData.isValid()));
        return simCardData4;
    }
}
