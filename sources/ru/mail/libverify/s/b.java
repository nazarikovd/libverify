package ru.mail.libverify.s;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import androidx.core.app.ActivityCompat;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.mail.verify.core.utils.FileLog;
import ru.mail.verify.core.utils.Utils;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/s/b.class */
public final class b extends a {
    @NotNull
    private final TelephonyManager g;
    @NotNull
    private final Context h;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public b(int i, int i2, @NotNull String str, int i3, @NotNull TelephonyManager telephonyManager, @NotNull Context context) {
        super(i, i2, str, i3, telephonyManager, context);
        Intrinsics.checkNotNullParameter(str, "activePhoneNumber");
        Intrinsics.checkNotNullParameter(telephonyManager, "generalManager");
        Intrinsics.checkNotNullParameter(context, "context");
        this.g = telephonyManager;
        this.h = context;
    }

    @Override // ru.mail.libverify.s.a
    @Nullable
    public final String i() {
        String str;
        try {
            str = Build.VERSION.SDK_INT < 29 ? this.g.getDeviceId() : "";
        } catch (SecurityException e) {
            FileLog.e("NotReflectionTelephonyManager", "getSimImei exception: ", e);
            str = "";
        }
        return str;
    }

    @Override // ru.mail.libverify.s.a
    @Nullable
    public final String a(@NotNull String str) {
        String str2;
        String str3;
        Intrinsics.checkNotNullParameter(str, "systemId");
        try {
        } catch (Exception e) {
            FileLog.e("NotReflectionTelephonyManager", "get fist sim card unqiue number exception: ", e);
            str2 = "";
        }
        if (Build.VERSION.SDK_INT < 29) {
            str3 = this.g.getSimSerialNumber();
        } else {
            String simOperator = this.g.getSimOperator();
            Intrinsics.checkNotNullExpressionValue(simOperator, "generalManager.simOperator");
            int b = b();
            if (!StringsKt.isBlank(str) && !StringsKt.isBlank(simOperator)) {
                String stringToSHA256 = Utils.stringToSHA256(str + simOperator + b);
                str2 = stringToSHA256;
                Intrinsics.checkNotNullExpressionValue(stringToSHA256, "stringToSHA256(systemId …Operator + simSlotNumber)");
                return str2;
            }
            str3 = "";
        }
        str2 = str3;
        return str2;
    }

    @Override // ru.mail.libverify.s.a
    @Nullable
    public final String m() {
        if (Build.VERSION.SDK_INT < 29) {
            return this.g.getSubscriberId();
        }
        return null;
    }

    @Override // ru.mail.libverify.s.a
    @Nullable
    public final String e() {
        return this.g.getNetworkOperatorName();
    }

    @Override // ru.mail.libverify.s.a
    @Nullable
    public final String d() {
        return this.g.getNetworkOperator();
    }

    @Override // ru.mail.libverify.s.a
    @Nullable
    public final String c() {
        return this.g.getNetworkCountryIso();
    }

    @Override // ru.mail.libverify.s.a
    public final boolean o() {
        return this.g.isNetworkRoaming();
    }

    @Override // ru.mail.libverify.s.a
    public final boolean p() {
        if (ActivityCompat.checkSelfPermission(this.h, "android.permission.READ_PHONE_STATE") == 0 && Build.VERSION.SDK_INT >= 29) {
            return this.g.isDataRoamingEnabled();
        }
        return false;
    }

    @Override // ru.mail.libverify.s.a
    public final int f() {
        return this.g.getSimState();
    }

    @Override // ru.mail.libverify.s.a
    @Nullable
    public final String j() {
        return this.g.getSimOperator();
    }

    @Override // ru.mail.libverify.s.a
    @Nullable
    public final String k() {
        return this.g.getSimOperatorName();
    }

    @Override // ru.mail.libverify.s.a
    @Nullable
    public final String h() {
        return this.g.getSimCountryIso();
    }
}
