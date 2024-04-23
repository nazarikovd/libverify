package ru.mail.libverify.s;

import android.content.Context;
import android.telephony.SubscriptionInfo;
import android.telephony.TelephonyManager;
import androidx.annotation.RequiresApi;
import java.lang.reflect.Method;
import java.util.Arrays;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.mail.verify.core.utils.FileLog;

@RequiresApi(api = 22)
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/s/c.class */
public final class c extends a {
    @NotNull
    private final TelephonyManager g;
    @NotNull
    private final SubscriptionInfo h;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public c(@NotNull Context context, int i, int i2, @NotNull String str, int i3, @NotNull TelephonyManager telephonyManager, @NotNull SubscriptionInfo subscriptionInfo) {
        super(i, i2, str, i3, telephonyManager, context);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(str, "activePhoneNumber");
        Intrinsics.checkNotNullParameter(telephonyManager, "generalManager");
        Intrinsics.checkNotNullParameter(subscriptionInfo, "subscriptionInfo");
        this.g = telephonyManager;
        this.h = subscriptionInfo;
    }

    @Override // ru.mail.libverify.s.a
    @NotNull
    public final String i() {
        String str;
        try {
            String a = a(this.g, "getDeviceId", b());
            str = a;
            if (a == null) {
                str = "";
            }
        } catch (Exception e) {
            FileLog.e("ReflectionTelephonyManager", "getImsi exception: ", e);
            str = "";
        }
        return str;
    }

    @Override // ru.mail.libverify.s.a
    @Nullable
    public final String a(@NotNull String str) {
        String str2;
        Intrinsics.checkNotNullParameter(str, "systemId");
        try {
            String a = a(this.g, "getSimSerialNumber", b());
            str2 = a;
            if (a == null) {
                str2 = "";
            }
        } catch (Exception e) {
            FileLog.e("ReflectionTelephonyManager", "get fist sim card unqiue number exception: ", e);
            str2 = "";
        }
        return str2;
    }

    @Override // ru.mail.libverify.s.a
    @Nullable
    public final String m() {
        return a(this.g, "getSubscriberId", b());
    }

    @Override // ru.mail.libverify.s.a
    @Nullable
    public final String e() {
        String str;
        try {
            Class<?> cls = this.g.getClass();
            Class<?>[] clsArr = new Class[1];
            clsArr[0] = Integer.TYPE;
            Method method = cls.getMethod("getNetworkOperatorName", clsArr);
            TelephonyManager telephonyManager = this.g;
            Object[] objArr = new Object[1];
            objArr[0] = Integer.valueOf(this.h.getSubscriptionId());
            str = (String) method.invoke(telephonyManager, objArr);
        } catch (Exception unused) {
            str = null;
        }
        return str;
    }

    @Override // ru.mail.libverify.s.a
    @Nullable
    public final String d() {
        String str;
        Method method;
        String str2;
        try {
            Class<?> cls = this.g.getClass();
            Class[] clsArr = new Class[1];
            Class cls2 = Integer.TYPE;
            clsArr[0] = cls2;
            try {
                method = cls.getMethod("getNetworkOperatorForSubscription", (Class[]) Arrays.copyOf(clsArr, 1));
            } catch (NoSuchMethodException unused) {
                method = null;
            }
            if (method == null) {
                Class[] clsArr2 = new Class[1];
                clsArr2[0] = cls2;
                try {
                    method = cls.getMethod("getNetworkOperator", (Class[]) Arrays.copyOf(clsArr2, 1));
                } catch (NoSuchMethodException unused2) {
                    method = null;
                }
            }
            if (method != null) {
                Method method2 = method;
                TelephonyManager telephonyManager = this.g;
                Object[] objArr = new Object[1];
                objArr[0] = Integer.valueOf(this.h.getSubscriptionId());
                str2 = method2.invoke(telephonyManager, objArr);
            } else {
                str2 = null;
            }
            str = str2;
        } catch (Exception e) {
            e.printStackTrace();
            str = null;
        }
        return str;
    }

    @Override // ru.mail.libverify.s.a
    @Nullable
    public final String c() {
        String str;
        Method method;
        String str2;
        try {
            Class<?> cls = this.g.getClass();
            Class[] clsArr = new Class[1];
            Class cls2 = Integer.TYPE;
            clsArr[0] = cls2;
            try {
                method = cls.getMethod("getNetworkCountryIsoForSubscription", (Class[]) Arrays.copyOf(clsArr, 1));
            } catch (NoSuchMethodException unused) {
                method = null;
            }
            if (method == null) {
                Class[] clsArr2 = new Class[1];
                clsArr2[0] = cls2;
                try {
                    method = cls.getMethod("getNetworkCountryIso", (Class[]) Arrays.copyOf(clsArr2, 1));
                } catch (NoSuchMethodException unused2) {
                    method = null;
                }
            }
            if (method != null) {
                Method method2 = method;
                TelephonyManager telephonyManager = this.g;
                Object[] objArr = new Object[1];
                objArr[0] = Integer.valueOf(this.h.getSubscriptionId());
                str2 = method2.invoke(telephonyManager, objArr);
            } else {
                str2 = null;
            }
            str = str2;
        } catch (Exception unused3) {
            str = null;
        }
        return str;
    }

    @Override // ru.mail.libverify.s.a
    public final boolean o() {
        try {
            Class<?> cls = this.g.getClass();
            Class<?>[] clsArr = new Class[1];
            clsArr[0] = Integer.TYPE;
            Method method = cls.getMethod("isNetworkRoaming", clsArr);
            TelephonyManager telephonyManager = this.g;
            Object[] objArr = new Object[1];
            objArr[0] = Integer.valueOf(this.h.getSubscriptionId());
            Boolean bool = (Boolean) method.invoke(telephonyManager, objArr);
            return bool != null ? bool.booleanValue() : false;
        } catch (Exception unused) {
            return false;
        }
    }

    @Override // ru.mail.libverify.s.a
    public final boolean p() {
        return false;
    }

    @Override // ru.mail.libverify.s.a
    public final int f() {
        int i;
        String a;
        try {
            a = a(this.g, "getSimState", b());
        } catch (Throwable unused) {
        }
        if (a != null) {
            i = Integer.parseInt(a);
            return i;
        }
        i = 0;
        return i;
    }

    @Override // ru.mail.libverify.s.a
    @NotNull
    public final String j() {
        return this.h.getMcc() + StringsKt.padStart(String.valueOf(this.h.getMnc()), 2, '0');
    }

    /* JADX WARN: Code restructure failed: missing block: B:5:0x0012, code lost:
        if (r0 == null) goto L8;
     */
    @Override // ru.mail.libverify.s.a
    @org.jetbrains.annotations.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String k() {
        /*
            r3 = this;
            r0 = r3
            android.telephony.SubscriptionInfo r0 = r0.h
            java.lang.CharSequence r0 = r0.getCarrierName()
            r1 = r0
            r4 = r1
            if (r0 == 0) goto L15
            r0 = r4
            java.lang.String r0 = r0.toString()
            r1 = r0
            r4 = r1
            if (r0 != 0) goto L1d
        L15:
            r0 = r3
            android.telephony.TelephonyManager r0 = r0.g
            java.lang.String r0 = r0.getSimOperatorName()
            r4 = r0
        L1d:
            r0 = r4
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: ru.mail.libverify.s.c.k():java.lang.String");
    }

    @Override // ru.mail.libverify.s.a
    @Nullable
    public final String h() {
        return this.h.getCountryIso();
    }

    private static String a(TelephonyManager telephonyManager, String str, int i) {
        try {
            Class<?> cls = telephonyManager.getClass();
            Class<?>[] clsArr = new Class[1];
            clsArr[0] = Integer.TYPE;
            Method method = cls.getMethod(str, clsArr);
            Object[] objArr = new Object[1];
            objArr[0] = Integer.valueOf(i);
            Object invoke = method.invoke(telephonyManager, objArr);
            return invoke != null ? invoke.toString() : null;
        } catch (Throwable unused) {
            return null;
        }
    }
}
