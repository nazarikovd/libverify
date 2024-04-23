package ru.mail.libverify.i;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.net.MalformedURLException;
import java.util.List;
import ru.mail.libverify.j.e;
import ru.mail.libverify.storage.InstanceConfig;
import ru.mail.verify.core.requests.ApiRequestParams;
import ru.mail.verify.core.requests.CustomUrlHelper;
import ru.mail.verify.core.requests.RequestPersistentId;
import ru.mail.verify.core.requests.RequestSerializedData;
import ru.mail.verify.core.requests.response.ResponseBase;
import ru.mail.verify.core.utils.json.JsonParseException;
import ru.mail.verify.core.utils.json.JsonParser;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/i/g.class */
public final class g extends c<ru.mail.libverify.j.e> {
    private static final Integer m = 1800000;
    private static final Integer n = 40000;
    private static final Long o = 1800000L;
    private final CustomUrlHelper j;
    private final long k;
    @Nullable
    private final String l;

    public g(@NonNull InstanceConfig instanceConfig, @NonNull String str, long j, @Nullable String str2) throws MalformedURLException {
        super(instanceConfig);
        this.j = new CustomUrlHelper(str);
        this.k = j;
        this.l = str2;
    }

    @Override // ru.mail.verify.core.requests.RequestBase
    public final RequestSerializedData getSerializedData() {
        return null;
    }

    @Override // ru.mail.libverify.i.c
    protected final boolean b() {
        return true;
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
    @Override // ru.mail.libverify.i.c, ru.mail.verify.core.requests.RequestBase
    public final ApiRequestParams getMethodParams() {
        ApiRequestParams methodParams = super.getMethodParams();
        methodParams.put("application_id", this.e.getHashedId());
        return methodParams;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // ru.mail.verify.core.requests.RequestBase
    public final String getMethodName() {
        return this.j.getMethodName();
    }

    @Override // ru.mail.verify.core.requests.RequestBase
    protected final Integer getReadTimeout() {
        return m;
    }

    @Override // ru.mail.verify.core.requests.RequestBase
    protected final Integer getConnectTimeout() {
        return n;
    }

    @Override // ru.mail.verify.core.requests.RequestBase
    protected final Long getLastResponseTimestamp() {
        long j = this.k;
        return j == 0 ? Long.valueOf(this.e.getTimeProvider().getLocalTime() - o.longValue()) : Long.valueOf(j);
    }

    @Override // ru.mail.verify.core.requests.RequestBase
    @Nullable
    protected final String getLastETag() {
        return this.l;
    }

    @Override // ru.mail.verify.core.requests.RequestBase
    protected final boolean isLastModifiedNeeded() {
        return true;
    }

    @Override // ru.mail.verify.core.requests.RequestBase
    protected final boolean isETagNeeded() {
        return true;
    }

    @Override // ru.mail.verify.core.requests.RequestBase
    protected final RequestPersistentId getRequestData() {
        return null;
    }

    @Override // ru.mail.verify.core.requests.RequestBase
    protected final ResponseBase parseJsonAnswer(String str) throws JsonParseException {
        if (TextUtils.isEmpty(str)) {
            throw new JsonParseException("jsonAnswer can't be null");
        }
        int lastIndexOf = str.lastIndexOf("}");
        if (lastIndexOf != str.length() - 1 && lastIndexOf != -1) {
            str = str.substring(0, lastIndexOf + 1);
        }
        StringBuilder sb = new StringBuilder(str.length());
        if (!str.startsWith("[") && !str.endsWith("]")) {
            str = sb.append("[").append(str).append("]").toString();
        }
        List<e.a> listFromJson = JsonParser.listFromJson(str, e.a.class);
        for (e.a aVar : listFromJson) {
            if (aVar != null && aVar.a() != null) {
                aVar.a().a(this.e.getTimeProvider().getLocalTime());
            }
        }
        return new ru.mail.libverify.j.e(listFromJson);
    }
}
