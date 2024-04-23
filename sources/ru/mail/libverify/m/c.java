package ru.mail.libverify.m;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;
import ru.mail.libverify.storage.InstanceConfig;
import ru.mail.verify.core.accounts.SimCardData;
import ru.mail.verify.core.accounts.SimCardItem;
import ru.mail.verify.core.api.NetworkManager;
import ru.mail.verify.core.storage.InstanceConfig;
import ru.mail.verify.core.utils.FileLog;
import ru.mail.verify.core.utils.Utils;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/m/c.class */
public final class c {

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/m/c$a.class */
    static final class a extends Lambda implements Function0<Object> {
        final /* synthetic */ InstanceConfig a;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        a(InstanceConfig instanceConfig) {
            super(0);
            this.a = instanceConfig;
        }

        @Nullable
        public final Object invoke() {
            return Utils.getBatteryLevel(this.a.getContext());
        }
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/m/c$b.class */
    static final class b extends Lambda implements Function0<Object> {
        public static final b a = new b();

        b() {
            super(0);
        }

        @Nullable
        public final Object invoke() {
            return Utils.getCurrentLocaleUnixId();
        }
    }

    /* renamed from: ru.mail.libverify.m.c$c  reason: collision with other inner class name */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/m/c$c.class */
    static final class C0015c extends Lambda implements Function0<Object> {
        public static final C0015c a = new C0015c();

        C0015c() {
            super(0);
        }

        @Nullable
        public final Object invoke() {
            return c.a();
        }
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/m/c$d.class */
    static final class d extends Lambda implements Function0<Object> {
        final /* synthetic */ InstanceConfig a;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        d(InstanceConfig instanceConfig) {
            super(0);
            this.a = instanceConfig;
        }

        @Nullable
        public final Object invoke() {
            return Utils.stringToSHA256(this.a.getStringProperty(InstanceConfig.PropertyType.SYSTEM_ID));
        }
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/m/c$e.class */
    static final class e extends Lambda implements Function0<Object> {
        final /* synthetic */ ru.mail.libverify.storage.InstanceConfig a;
        final /* synthetic */ NetworkManager b;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        e(ru.mail.libverify.storage.InstanceConfig instanceConfig, NetworkManager networkManager) {
            super(0);
            this.a = instanceConfig;
            this.b = networkManager;
        }

        @Nullable
        public final Object invoke() {
            Context context = this.a.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "config.context");
            return c.a(context, this.b);
        }
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/m/c$f.class */
    static final class f extends Lambda implements Function0<Object> {
        public static final f a = new f();

        f() {
            super(0);
        }

        @Nullable
        public final Object invoke() {
            return Build.VERSION.RELEASE;
        }
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/m/c$g.class */
    static final class g extends Lambda implements Function0<Object> {
        final /* synthetic */ ru.mail.libverify.storage.InstanceConfig a;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        g(ru.mail.libverify.storage.InstanceConfig instanceConfig) {
            super(0);
            this.a = instanceConfig;
        }

        @Nullable
        public final Object invoke() {
            Context context = this.a.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "config.context");
            return c.b(context);
        }
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/m/c$h.class */
    static final class h extends Lambda implements Function0<Object> {
        final /* synthetic */ ru.mail.libverify.storage.InstanceConfig a;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        h(ru.mail.libverify.storage.InstanceConfig instanceConfig) {
            super(0);
            this.a = instanceConfig;
        }

        @Nullable
        public final Object invoke() {
            Context context = this.a.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "config.context");
            SimCardData simCardData = this.a.getSimCardData();
            Intrinsics.checkNotNullExpressionValue(simCardData, "config.simCardData");
            return c.a(context, simCardData);
        }
    }

    private c() {
    }

    public static final JSONObject a() {
        JSONObject jSONObject = new JSONObject();
        a(jSONObject, "manufacturer", ru.mail.libverify.m.a.a);
        a(jSONObject, "model", ru.mail.libverify.m.b.a);
        return jSONObject;
    }

    public static final JSONObject b(Context context) {
        JSONObject jSONObject = new JSONObject();
        for (String str : CollectionsKt.listOf(new String[]{"android.permission.ACCESS_NETWORK_STATE", "android.permission.CALL_PHONE", "android.permission.READ_CALL_LOG", "android.permission.READ_PHONE_STATE", "android.permission.READ_SMS", "android.permission.RECEIVE_SMS"})) {
            jSONObject.put(StringsKt.removePrefix(str, "android.permission."), Utils.hasSelfPermission(context, str));
        }
        if (Build.VERSION.SDK_INT >= 26) {
            jSONObject.put("READ_PHONE_NUMBERS", Utils.hasSelfPermission(context, "android.permission.READ_PHONE_NUMBERS"));
        }
        jSONObject.put("getCellularSignalLevel", Utils.hasSelfPermission(context, "android.permission.ACCESS_COARSE_LOCATION") || Utils.hasSelfPermission(context, "android.permission.ACCESS_FINE_LOCATION"));
        return jSONObject;
    }

    public static final Boolean a(Context context) {
        Boolean bool;
        try {
            Class<?> cls = Class.forName("miui.telephony.TelephonyManager");
            Object invoke = Class.forName("miui.telephony.TelephonyManagerEx").getMethod("isDualVolteSupported", new Class[0]).invoke(cls.getMethod("getDefault", new Class[0]).invoke(cls, new Object[0]), new Object[0]);
            Intrinsics.checkNotNull(invoke, "null cannot be cast to non-null type kotlin.Boolean");
            bool = (Boolean) invoke;
        } catch (Exception e2) {
            FileLog.e("ExtendedPhoneInfo", "Failed to read dual4g info: " + e2);
            bool = null;
        }
        if (bool != null) {
            if (!bool.booleanValue()) {
                return Boolean.FALSE;
            }
            int i = Settings.Global.getInt(context.getContentResolver(), "dual_4g_mode_enabled", -1);
            if (i != -1) {
                return Boolean.valueOf(i == 1);
            }
        }
        return null;
    }

    public static final JSONObject a(Context context, NetworkManager networkManager) {
        JSONObject jSONObject = new JSONObject();
        a(jSONObject, "isVoiceCapable", new ru.mail.libverify.m.d(context));
        a(jSONObject, "roaming", new ru.mail.libverify.m.e(context));
        a(jSONObject, "data", new ru.mail.libverify.m.f(context));
        a(jSONObject, "hasVpnConnection", new ru.mail.libverify.m.g(networkManager));
        a(jSONObject, "hasCellularConnection", new ru.mail.libverify.m.h(context, networkManager));
        return jSONObject;
    }

    public static final JSONObject a(Context context, SimCardData simCardData) {
        JSONObject jSONObject = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        for (SimCardItem simCardItem : simCardData.getSimList()) {
            try {
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("operator", simCardItem.getOperator());
                jSONObject2.put("operatorName", simCardItem.getOperatorName());
                jSONObject2.put("countryId", simCardItem.getSimCountryIso());
                jSONObject2.put("roaming", simCardItem.isNetworkRoaming());
                jSONObject2.put("networkOperator", simCardItem.getNetworkOperator());
                jSONObject2.put("networkOperatorName", simCardItem.getNetworkOperatorName());
                jSONObject2.put("networkCountryId", simCardItem.getNetworkCountryIso());
                jSONObject2.put("slotIndex", simCardItem.getSlotIndex());
                jSONArray.put(jSONObject2);
            } catch (Throwable th) {
                FileLog.e("ExtendedPhoneInfo", "failed to get sim card info", th);
            }
        }
        a(jSONObject, "simCount", new i(simCardData));
        a(jSONObject, "simSlotsCount", new j(simCardData));
        a(jSONObject, "isDual4gSupported", new k(context));
        jSONObject.put("simList", jSONArray);
        return jSONObject;
    }

    @JvmStatic
    @NotNull
    public static final String a(@NotNull ru.mail.libverify.storage.InstanceConfig instanceConfig, @NotNull NetworkManager networkManager) {
        Intrinsics.checkNotNullParameter(instanceConfig, "config");
        Intrinsics.checkNotNullParameter(networkManager, "networkManager");
        instanceConfig.getTimeProvider().getLocalTime();
        JSONObject jSONObject = new JSONObject();
        a(jSONObject, "batteryLevel", new a(instanceConfig));
        a(jSONObject, "defaultLocale", b.a);
        a(jSONObject, "device", C0015c.a);
        a(jSONObject, "deviceId", new d(instanceConfig));
        a(jSONObject, "networkInfo", new e(instanceConfig, networkManager));
        a(jSONObject, "os", f.a);
        a(jSONObject, "permissions", new g(instanceConfig));
        a(jSONObject, "simCardsInfo", new h(instanceConfig));
        jSONObject.put("platform", "Android");
        String jSONObject2 = jSONObject.toString();
        Intrinsics.checkNotNullExpressionValue(jSONObject2, "extendedInfo.toString()");
        return jSONObject2;
    }

    @JvmStatic
    private static final void a(JSONObject jSONObject, String str, Function0 function0) {
        Object obj;
        try {
            obj = function0.invoke();
        } catch (Throwable unused) {
            obj = "undefined";
        }
        if (obj != null) {
            Intrinsics.checkNotNullExpressionValue(jSONObject.put(str, obj), "this.put(name, safeParam)");
        }
    }
}
