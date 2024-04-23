package ru.mail.libverify.i;

import android.net.Network;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;
import ru.mail.libverify.AppStateModel;
import ru.mail.verify.core.requests.ApiRequestParams;
import ru.mail.verify.core.requests.RequestPersistentId;
import ru.mail.verify.core.requests.RequestSerializedData;
import ru.mail.verify.core.requests.response.ResponseBase;
import ru.mail.verify.core.storage.InstanceConfig;
import ru.mail.verify.core.utils.Utils;
import ru.mail.verify.core.utils.json.JsonParseException;
import ru.mail.verify.core.utils.json.JsonParser;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/i/l.class */
public final class l extends c<ru.mail.libverify.j.m> {
    @NonNull
    private final m j;
    @Nullable
    private String k;

    public l(@NonNull ru.mail.libverify.m.l lVar, @NonNull m mVar, @Nullable Network network) {
        super(lVar);
        this.j = mVar;
        this.customNetwork = network;
    }

    @Override // ru.mail.verify.core.requests.RequestBase
    @NonNull
    public final RequestSerializedData getSerializedData() throws JsonParseException {
        return new RequestSerializedData(JsonParser.toJson(this.j));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // ru.mail.libverify.i.c, ru.mail.verify.core.requests.RequestBase
    @NonNull
    public final ApiRequestParams getMethodParams() {
        String str;
        ApiRequestParams methodParams = super.getMethodParams();
        if (!TextUtils.isEmpty(this.j.pushToken)) {
            methodParams.put("push_token", this.j.pushToken);
        }
        int i = this.j.blockTimeoutSec;
        if (i > 0) {
            methodParams.put("block_timeout", Integer.toString(i));
        }
        if (!TextUtils.isEmpty(this.j.from)) {
            methodParams.put("from", this.j.from);
        }
        String str2 = this.j.action;
        if (str2 != null && !TextUtils.equals(str2, null)) {
            methodParams.put("action_type", this.j.action);
        }
        if (!TextUtils.isEmpty(this.j.checkParams)) {
            methodParams.put("checkparams", Utils.getBase64String(this.j.checkParams));
        }
        if (!TextUtils.isEmpty(this.j.smsParams)) {
            methodParams.put("smsparams", Utils.getBase64String(this.j.smsParams));
        }
        methodParams.put("language", Utils.getLocaleUnixId(this.e.getCurrentLocale()));
        String str3 = this.j.policy;
        if (str3 != null && !TextUtils.equals(str3, null)) {
            methodParams.put("drop", this.j.policy);
        }
        if (!TextUtils.isEmpty(this.j.appCheckParams)) {
            methodParams.put("jws", this.j.appCheckParams);
        }
        String serverKey = this.e.getServerKey();
        if (!TextUtils.isEmpty(serverKey) && this.k != serverKey) {
            methodParams.put("server_key", serverKey);
            this.k = serverKey;
        }
        if (!TextUtils.isEmpty(this.j.sessionId)) {
            methodParams.put("session_id", this.j.sessionId);
        }
        if (!TextUtils.isEmpty(this.j.sign)) {
            methodParams.put("request_id", this.j.sign);
        }
        ArrayList<ru.mail.libverify.c.b> arrayList = this.j.mobileIdRoutes;
        if (arrayList != null && arrayList.size() > 0) {
            methodParams.put("mobileid_info", JsonParser.toJson(new ru.mail.libverify.c.a(this.j.mobileIdRoutes)));
        }
        if (Objects.equals(this.j.action, "callin_call") && (str = this.j.phone) != null) {
            methodParams.put(InstanceConfig.DEVICE_TYPE_PHONE, str);
        }
        Set<ru.mail.libverify.q.a> set = this.j.startTimings;
        if (set != null) {
            methodParams.put("init_time", JsonParser.toJson(set));
        }
        methodParams.put("use_lifecycle", AppStateModel.canUseLifecycle());
        return methodParams;
    }

    @Override // ru.mail.libverify.i.c
    protected final boolean b() {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // ru.mail.verify.core.requests.RequestBase
    @NonNull
    public final String getMethodName() {
        return "libverifysettings";
    }

    @Override // ru.mail.verify.core.requests.RequestBase
    protected final boolean postUrlData() {
        ArrayList<ru.mail.libverify.c.b> arrayList;
        return !TextUtils.isEmpty(this.j.appCheckParams) || ((arrayList = this.j.mobileIdRoutes) != null && arrayList.size() > 0);
    }

    @Override // ru.mail.verify.core.requests.RequestBase
    @NonNull
    protected final RequestPersistentId getRequestData() {
        return this.j;
    }

    @Nullable
    public final String f() {
        return this.j.pushToken;
    }

    @Override // ru.mail.verify.core.requests.RequestBase
    @Nullable
    protected final ResponseBase parseJsonAnswer(@NonNull String str) throws JsonParseException {
        ru.mail.libverify.j.m mVar = (ru.mail.libverify.j.m) JsonParser.fromJson(str, ru.mail.libverify.j.m.class);
        if (mVar != null) {
            if (TextUtils.equals(this.j.action, "request_sms_info")) {
                mVar.p();
            }
            if (mVar.k() != null) {
                mVar.k().a(this.e.getTimeProvider().getLocalTime());
            }
            if (mVar.j() != null) {
                mVar.j().a(this.e.getTimeProvider().getLocalTime());
            }
        }
        return mVar;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public l(@NonNull ru.mail.libverify.m.l lVar, @NonNull RequestSerializedData requestSerializedData) throws JsonParseException {
        super(lVar);
        this.j = (m) JsonParser.fromJson(requestSerializedData.json, m.class);
    }

    public l(@NonNull ru.mail.libverify.storage.InstanceConfig instanceConfig, @NonNull m mVar) {
        super(instanceConfig);
        this.j = mVar;
    }
}
