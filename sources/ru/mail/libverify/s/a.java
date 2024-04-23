package ru.mail.libverify.s;

import android.content.Context;
import android.os.Build;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import androidx.annotation.RequiresPermission;
import androidx.core.content.PermissionChecker;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.mail.verify.core.storage.InstanceConfig;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/s/a.class */
public abstract class a {
    private final int a;
    private final int b;
    @NotNull
    private final String c;
    private final int d;
    @NotNull
    private final TelephonyManager e;
    @NotNull
    private final Context f;

    /* renamed from: ru.mail.libverify.s.a$a  reason: collision with other inner class name */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/s/a$a.class */
    public static final class C0020a {
        @JvmStatic
        @RequiresPermission(anyOf = {"android.permission.READ_PHONE_STATE", "android.permission.READ_PRIVILEGED_PHONE_STATE"})
        @NotNull
        public static a a(@NotNull Context context, @Nullable Integer num) {
            SubscriptionInfo subscriptionInfo;
            a aVar;
            SubscriptionInfo subscriptionInfo2;
            int subscriptionId;
            b bVar;
            Intrinsics.checkNotNullParameter(context, "context");
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(InstanceConfig.DEVICE_TYPE_PHONE);
            if (telephonyManager != null) {
                int i = Build.VERSION.SDK_INT;
                if (i >= 22) {
                    SubscriptionManager subscriptionManager = (SubscriptionManager) context.getSystemService("telephony_subscription_service");
                    if (subscriptionManager != null) {
                        int activeSubscriptionInfoCount = subscriptionManager.getActiveSubscriptionInfoCount();
                        int activeSubscriptionInfoCountMax = subscriptionManager.getActiveSubscriptionInfoCountMax();
                        if (i < 24) {
                            if (num == null) {
                                try {
                                    subscriptionInfo = (SubscriptionInfo) subscriptionManager.getClass().getMethod("getDefaultDataSubscriptionInfo", new Class[0]).invoke(subscriptionManager, new Object[0]);
                                } catch (Exception unused) {
                                    subscriptionInfo = null;
                                }
                            } else {
                                SubscriptionInfo activeSubscriptionInfoForSimSlotIndex = subscriptionManager.getActiveSubscriptionInfoForSimSlotIndex(num.intValue());
                                if (activeSubscriptionInfoForSimSlotIndex == null) {
                                    throw new IllegalArgumentException("Failed to create subscriptionInfo for slot[" + num + ']');
                                }
                                subscriptionInfo = activeSubscriptionInfoForSimSlotIndex;
                            }
                            Integer valueOf = subscriptionInfo != null ? Integer.valueOf(subscriptionInfo.getSimSlotIndex()) : null;
                            if (subscriptionInfo == null || valueOf == null) {
                                aVar = r0;
                                b bVar2 = new b(activeSubscriptionInfoCount, activeSubscriptionInfoCountMax, "", 0, telephonyManager, context);
                            } else {
                                String number = subscriptionInfo.getNumber();
                                String str = number;
                                if (number == null) {
                                    str = "";
                                }
                                aVar = new c(context, activeSubscriptionInfoCount, activeSubscriptionInfoCountMax, str, valueOf.intValue(), telephonyManager, subscriptionInfo);
                            }
                            return aVar;
                        }
                        if (num == null) {
                            int defaultSubscriptionId = SubscriptionManager.getDefaultSubscriptionId();
                            subscriptionId = defaultSubscriptionId;
                            subscriptionInfo2 = subscriptionManager.getActiveSubscriptionInfo(defaultSubscriptionId);
                        } else {
                            SubscriptionInfo activeSubscriptionInfoForSimSlotIndex2 = subscriptionManager.getActiveSubscriptionInfoForSimSlotIndex(num.intValue());
                            subscriptionInfo2 = activeSubscriptionInfoForSimSlotIndex2;
                            if (!(activeSubscriptionInfoForSimSlotIndex2 != null)) {
                                throw new IllegalArgumentException(("Failed to create subscriptionInfo for slot[" + num + ']').toString());
                            }
                            subscriptionId = subscriptionInfo2.getSubscriptionId();
                        }
                        if (subscriptionInfo2 == null) {
                            bVar = r0;
                            b bVar3 = new b(activeSubscriptionInfoCount, activeSubscriptionInfoCountMax, "", -1, telephonyManager, context);
                        } else {
                            TelephonyManager createForSubscriptionId = telephonyManager.createForSubscriptionId(subscriptionId);
                            if (createForSubscriptionId == null) {
                                throw new IllegalArgumentException("Can not create telephonyManager for subId:" + subscriptionId);
                            }
                            String number2 = subscriptionInfo2.getNumber();
                            String str2 = number2;
                            if (number2 == null) {
                                str2 = "";
                            }
                            bVar = new b(activeSubscriptionInfoCount, activeSubscriptionInfoCountMax, str2, subscriptionInfo2.getSimSlotIndex(), createForSubscriptionId, context);
                        }
                        return bVar;
                    }
                    throw new Exception("Failed to get TELEPHONY_SUBSCRIPTION_SERVICE");
                } else if ((num != null && num.intValue() == 0) || num == null) {
                    String line1Number = telephonyManager.getLine1Number();
                    String str3 = line1Number;
                    if (line1Number == null) {
                        str3 = "";
                    }
                    return new b(1, 1, str3, 0, telephonyManager, context);
                } else {
                    throw new UnsupportedOperationException("Failed to create " + C0020a.class.getName() + " for slot[" + num + "] on single SIM device.");
                }
            }
            throw new Exception("Failed to get TELEPHONY_SERVICE");
        }
    }

    public a(int i, int i2, @NotNull String str, int i3, @NotNull TelephonyManager telephonyManager, @NotNull Context context) {
        Intrinsics.checkNotNullParameter(str, "activePhoneNumber");
        Intrinsics.checkNotNullParameter(telephonyManager, "generalManager");
        Intrinsics.checkNotNullParameter(context, "context");
        this.a = i;
        this.b = i2;
        this.c = str;
        this.d = i3;
        this.e = telephonyManager;
        this.f = context;
    }

    public final int g() {
        return this.a;
    }

    public final int l() {
        return this.b;
    }

    @NotNull
    public final String a() {
        return this.c;
    }

    public final int b() {
        return this.d;
    }

    @Nullable
    public abstract String i();

    @Nullable
    public abstract String a(@NotNull String str);

    @Nullable
    public abstract String m();

    @Nullable
    public abstract String e();

    @Nullable
    public abstract String d();

    @Nullable
    public abstract String c();

    public abstract boolean o();

    public abstract boolean p();

    public abstract int f();

    @Nullable
    public abstract String j();

    @Nullable
    public abstract String k();

    @Nullable
    public abstract String h();

    public final boolean n() {
        return ((Build.VERSION.SDK_INT < 26 || (!(PermissionChecker.checkSelfPermission(this.f, "android.permission.READ_PHONE_STATE") == 0) && !(PermissionChecker.checkSelfPermission(this.f, "android.permission.ACCESS_NETWORK_STATE") == 0))) ? false : this.e.isDataEnabled()) || (this.e.getDataState() == 2);
    }
}
