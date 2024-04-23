package ru.mail.libverify.m;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;
import ru.mail.verify.core.utils.Utils;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/m/f.class */
final class f extends Lambda implements Function0<Object> {
    final /* synthetic */ Context a;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public f(Context context) {
        super(0);
        this.a = context;
    }

    @Nullable
    public final Object invoke() {
        Context context = this.a;
        JSONObject jSONObject = new JSONObject();
        if (Utils.hasSelfPermission(context, "android.permission.ACCESS_NETWORK_STATE")) {
            String str = "Not connected";
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager != null) {
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                    String typeName = activeNetworkInfo.getTypeName();
                    str = typeName;
                    Intrinsics.checkNotNullExpressionValue(typeName, "active.typeName");
                } else {
                    NetworkInfo[] allNetworkInfo = connectivityManager.getAllNetworkInfo();
                    Intrinsics.checkNotNullExpressionValue(allNetworkInfo, "allNetworkInfo");
                    for (NetworkInfo networkInfo : allNetworkInfo) {
                        if (networkInfo.isConnected()) {
                            String typeName2 = networkInfo.getTypeName();
                            str = typeName2;
                            Intrinsics.checkNotNullExpressionValue(typeName2, "networkInfo.typeName");
                        }
                    }
                }
                jSONObject.put("type", str);
            }
        }
        return jSONObject;
    }
}
