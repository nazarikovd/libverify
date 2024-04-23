package ru.mail.libverify.i;

import androidx.annotation.NonNull;
import java.net.MalformedURLException;
import ru.mail.libverify.api.VerificationApi;
import ru.mail.libverify.storage.InstanceConfig;
import ru.mail.verify.core.requests.ApiRequestParams;
import ru.mail.verify.core.requests.CustomUrlHelper;
import ru.mail.verify.core.requests.RequestPersistentId;
import ru.mail.verify.core.requests.RequestSerializedData;
import ru.mail.verify.core.requests.response.ResponseBase;
import ru.mail.verify.core.utils.json.JsonParseException;
import ru.mail.verify.core.utils.json.JsonParser;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/i/a.class */
public final class a extends c<ru.mail.libverify.j.a> {
    private final CustomUrlHelper j;
    private final b k;

    public a(@NonNull ru.mail.libverify.m.l lVar, @NonNull String str, @NonNull String str2, @NonNull String str3) throws MalformedURLException {
        super(lVar);
        this.j = new CustomUrlHelper(str);
        this.k = new b(str, str2, VerificationApi.VerificationSource.APPLICATION_EXTERNAL, str3);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // ru.mail.libverify.i.c, ru.mail.verify.core.requests.RequestBase
    public final ApiRequestParams getMethodParams() {
        ApiRequestParams apiRequestParams = new ApiRequestParams(this.j.getQuery());
        apiRequestParams.put("application", this.e.getApplicationName());
        apiRequestParams.put("platform", "android");
        apiRequestParams.put("code", this.k.code);
        apiRequestParams.put("application_id", this.k.applicationId);
        apiRequestParams.put("code_source", this.k.codeSource.toString());
        return apiRequestParams;
    }

    @Override // ru.mail.libverify.i.c
    protected final boolean b() {
        return false;
    }

    @Override // ru.mail.verify.core.requests.RequestBase
    public final RequestSerializedData getSerializedData() throws JsonParseException {
        return new RequestSerializedData(JsonParser.toJson(this.k));
    }

    @Override // ru.mail.libverify.i.c, ru.mail.verify.core.requests.RequestBase
    protected final String getApiHost() {
        return this.j.getApiHost();
    }

    @Override // ru.mail.libverify.i.c, ru.mail.verify.core.requests.RequestBase
    @NonNull
    protected final String getApiPath() {
        return this.j.getApiPath();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // ru.mail.verify.core.requests.RequestBase
    public final String getMethodName() {
        return this.j.getMethodName();
    }

    @Override // ru.mail.verify.core.requests.RequestBase
    protected final RequestPersistentId getRequestData() {
        return this.k;
    }

    @Override // ru.mail.verify.core.requests.RequestBase
    protected final ResponseBase parseJsonAnswer(String str) throws JsonParseException {
        ru.mail.libverify.j.a aVar = (ru.mail.libverify.j.a) JsonParser.fromJson(str, ru.mail.libverify.j.a.class);
        if (aVar != null && aVar.f() != null) {
            aVar.f().a(this.e.getTimeProvider().getLocalTime());
        }
        return aVar;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(@NonNull ru.mail.libverify.m.l lVar, @NonNull RequestSerializedData requestSerializedData) throws JsonParseException, MalformedURLException {
        super(lVar);
        b bVar = (b) JsonParser.fromJson(requestSerializedData.json, b.class);
        this.k = bVar;
        this.j = new CustomUrlHelper(bVar.verificationUrl);
    }

    public a(@NonNull InstanceConfig instanceConfig, @NonNull String str, @NonNull String str2, VerificationApi.VerificationSource verificationSource) throws MalformedURLException {
        super(instanceConfig);
        this.j = new CustomUrlHelper(str);
        this.k = new b(str, str2, verificationSource, instanceConfig.getId());
    }
}
