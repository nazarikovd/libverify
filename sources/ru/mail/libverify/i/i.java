package ru.mail.libverify.i;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import ru.mail.libverify.storage.InstanceConfig;
import ru.mail.verify.core.requests.ApiRequestParams;
import ru.mail.verify.core.requests.RequestPersistentId;
import ru.mail.verify.core.requests.RequestSerializedData;
import ru.mail.verify.core.requests.response.ResponseBase;
import ru.mail.verify.core.utils.Utils;
import ru.mail.verify.core.utils.json.JsonParseException;
import ru.mail.verify.core.utils.json.JsonParser;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/i/i.class */
public final class i extends c<ru.mail.libverify.j.i> {
    private final InstanceConfig j;
    private final String k;
    private final String l;
    private final String m;
    private final boolean n;

    public i(@NonNull InstanceConfig instanceConfig, @NonNull String str, @NonNull String str2, String str3, boolean z) {
        super(instanceConfig);
        this.j = instanceConfig;
        this.k = str;
        this.l = str2;
        this.m = str3;
        this.n = z;
    }

    @Override // ru.mail.verify.core.requests.RequestBase
    public final RequestSerializedData getSerializedData() {
        return null;
    }

    @Override // ru.mail.libverify.i.c
    protected final boolean b() {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // ru.mail.verify.core.requests.RequestBase
    public final String getMethodName() {
        return "smsphoneinfo";
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // ru.mail.libverify.i.c, ru.mail.verify.core.requests.RequestBase
    public final ApiRequestParams getMethodParams() {
        ApiRequestParams apiRequestParams = new ApiRequestParams();
        if (!TextUtils.isEmpty(this.m)) {
            apiRequestParams.put("id", this.m);
        }
        apiRequestParams.put("lang", Utils.getLocaleUnixId(this.j.getCurrentLocale()));
        apiRequestParams.put("service", this.l);
        apiRequestParams.put(ru.mail.verify.core.storage.InstanceConfig.DEVICE_TYPE_PHONE, this.k);
        apiRequestParams.put("platform", "android");
        String simIsoCountryCode = this.j.getSimCardData().getSimIsoCountryCode();
        if (!TextUtils.isEmpty(simIsoCountryCode)) {
            apiRequestParams.put("iso_country_code", simIsoCountryCode);
        }
        if (this.n) {
            apiRequestParams.put("info", "typing_check");
        }
        return apiRequestParams;
    }

    @Override // ru.mail.verify.core.requests.RequestBase
    protected final RequestPersistentId getRequestData() {
        return null;
    }

    @Override // ru.mail.verify.core.requests.RequestBase
    protected final ResponseBase parseJsonAnswer(String str) throws JsonParseException {
        return (ru.mail.libverify.j.i) JsonParser.fromJson(str, ru.mail.libverify.j.i.class);
    }
}
