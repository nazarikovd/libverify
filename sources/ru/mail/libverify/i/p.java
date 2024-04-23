package ru.mail.libverify.i;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import ru.mail.libverify.R;
import ru.mail.libverify.storage.InstanceConfig;
import ru.mail.verify.core.requests.ApiRequestParams;
import ru.mail.verify.core.requests.RequestPersistentId;
import ru.mail.verify.core.requests.RequestSerializedData;
import ru.mail.verify.core.requests.response.ResponseBase;
import ru.mail.verify.core.storage.InstanceConfig;
import ru.mail.verify.core.utils.DebugUtils;
import ru.mail.verify.core.utils.Utils;
import ru.mail.verify.core.utils.json.JsonParseException;
import ru.mail.verify.core.utils.json.JsonParser;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/i/p.class */
public final class p extends c<ru.mail.libverify.j.n> {
    private static final String[] w = {"sms_retriever"};
    private final String j;
    private final String k;
    private final String l;
    @NonNull
    private final b[] m;
    private final String n;
    private final boolean o;
    private final a p;
    private final String q;
    @Nullable
    private String r;
    @Nullable
    private String s;
    private boolean t;
    @NonNull
    private String u;
    String v;

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/i/p$a.class */
    public enum a {
        VKCONNECT("VKCONNECT"),
        EMPTY("EMPTY");
        
        public final String value;

        a(String str) {
            this.value = str;
        }
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/i/p$b.class */
    public enum b {
        IVR("ivr"),
        SMS("sms"),
        CALL("call"),
        PUSH("push"),
        CALLUI("callui"),
        VKC("vkc"),
        MOBILEID("mobileid"),
        CALLIN("callin");
        
        public final String value;

        b(String str) {
            this.value = str;
        }
    }

    public p(@NonNull InstanceConfig instanceConfig, @NonNull String str, @NonNull String str2, @Nullable String str3, @Nullable String str4, @NonNull b[] bVarArr, @NonNull a aVar, @Nullable String str5, boolean z, @Nullable String str6, @Nullable String str7, @NonNull String str8, boolean z2) {
        super(instanceConfig);
        this.v = null;
        this.j = str;
        this.k = str2;
        this.l = str4;
        this.n = str3;
        this.m = bVarArr;
        this.q = str5;
        this.p = aVar;
        this.o = z;
        this.r = str6;
        this.s = str7;
        this.t = z2;
        this.u = str8;
    }

    @Override // ru.mail.libverify.i.c
    protected final String[] d() {
        return w;
    }

    @Override // ru.mail.libverify.i.c, ru.mail.verify.core.requests.RequestBase
    protected final ApiRequestParams getMethodParams() {
        b[] bVarArr;
        b[] bVarArr2 = this.m;
        if (((bVarArr2 != null && bVarArr2.length == 1 && bVarArr2[0] == b.VKC) ? false : true) && TextUtils.isEmpty(this.n) && TextUtils.isEmpty(this.l)) {
            throw new IllegalArgumentException("Can't prepare verification request without phone number or user id");
        }
        ApiRequestParams methodParams = super.getMethodParams();
        methodParams.put("session_id", this.j);
        methodParams.put("service", this.k);
        methodParams.put("language", Utils.getLocaleUnixId(this.e.getCurrentLocale()));
        if (!TextUtils.isEmpty(this.r)) {
            methodParams.put("jws", this.r);
        }
        if (!TextUtils.isEmpty(this.u)) {
            methodParams.put("request_id", this.u);
        }
        if (!TextUtils.isEmpty(this.s)) {
            methodParams.put("external_id", this.s);
        }
        String fullDeviceName = Utils.getFullDeviceName(this.e.getStringProperty(InstanceConfig.PropertyType.DEVICE_VENDOR), this.e.getStringProperty(InstanceConfig.PropertyType.DEVICE_NAME));
        if (!TextUtils.isEmpty(fullDeviceName)) {
            methodParams.put("device_name", fullDeviceName);
        }
        if (this.t) {
            methodParams.put("resend", "1");
        }
        if (this.m.length != 0) {
            StringBuilder sb = new StringBuilder();
            for (b bVar : this.m) {
                if (bVar == null) {
                    DebugUtils.safeThrow("VerifyApiRequest", "VerifyChecks can't be null!", new RuntimeException());
                } else {
                    if (sb.length() != 0) {
                        sb.append(",");
                    }
                    sb.append(bVar.value);
                }
            }
            methodParams.put("checks", sb.toString());
            try {
                if (this.v == null) {
                    this.v = this.context.getString(R.string.libverify_debug_checks);
                }
                String str = this.v;
                if (!TextUtils.isEmpty(str)) {
                    methodParams.put("checks", str);
                }
            } catch (Exception unused) {
            }
            String extendedPhoneInfo = this.e.getExtendedPhoneInfo();
            if (!TextUtils.isEmpty(extendedPhoneInfo)) {
                methodParams.put("ext_info", Utils.getBase64String(extendedPhoneInfo));
            }
        }
        if (this.o) {
            methodParams.put("manual_routes", "1");
        }
        if (!TextUtils.isEmpty(this.l)) {
            methodParams.put("user_id", this.l);
            methodParams.put("verify_by_user_id", "1");
        }
        if (!TextUtils.isEmpty(this.n)) {
            methodParams.put(ru.mail.verify.core.storage.InstanceConfig.DEVICE_TYPE_PHONE, this.n);
        }
        String registrationId = this.e.getRegistrar().getRegistrationId();
        if (!TextUtils.isEmpty(registrationId)) {
            methodParams.put("push_token", registrationId);
        }
        String serverKey = this.e.getServerKey();
        if (!TextUtils.isEmpty(serverKey)) {
            methodParams.put("server_key", serverKey);
        }
        a aVar = this.p;
        if (aVar != null && aVar != a.EMPTY) {
            methodParams.put("auth_type", aVar.value);
        }
        if (!TextUtils.isEmpty(this.q)) {
            methodParams.put("src_application", this.q);
        }
        return methodParams;
    }

    @Override // ru.mail.libverify.i.c
    protected final boolean b() {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // ru.mail.verify.core.requests.RequestBase
    public final String getMethodName() {
        return "verify";
    }

    @Override // ru.mail.verify.core.requests.RequestBase
    protected final boolean postUrlData() {
        return !TextUtils.isEmpty(this.r);
    }

    @Override // ru.mail.verify.core.requests.RequestBase
    protected final RequestPersistentId getRequestData() {
        return null;
    }

    @Override // ru.mail.verify.core.requests.RequestBase
    public final RequestSerializedData getSerializedData() {
        return null;
    }

    @Override // ru.mail.verify.core.requests.RequestBase
    protected final ResponseBase parseJsonAnswer(String str) throws JsonParseException {
        return (ru.mail.libverify.j.n) JsonParser.fromJson(str, ru.mail.libverify.j.n.class);
    }
}
