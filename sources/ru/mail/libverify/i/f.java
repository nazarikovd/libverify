package ru.mail.libverify.i;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import ru.mail.verify.core.api.ApplicationModule;
import ru.mail.verify.core.api.NetworkManager;
import ru.mail.verify.core.requests.ConstantRequestData;
import ru.mail.verify.core.requests.RequestBase;
import ru.mail.verify.core.requests.RequestPersistentId;
import ru.mail.verify.core.requests.RequestSerializedData;
import ru.mail.verify.core.requests.response.ResponseBase;
import ru.mail.verify.core.utils.json.JsonParseException;
import ru.mail.verify.core.utils.json.JsonParser;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/i/f.class */
public abstract class f<T extends ResponseBase> extends RequestBase<T> {
    protected final ConstantRequestData e;

    /* JADX INFO: Access modifiers changed from: package-private */
    public f(@NonNull Context context, @NonNull NetworkManager networkManager, @NonNull ApplicationModule.ApplicationStartConfig applicationStartConfig, @NonNull ConstantRequestData constantRequestData) {
        super(context, networkManager, applicationStartConfig);
        this.e = constantRequestData;
    }

    @Override // ru.mail.verify.core.requests.RequestBase
    public RequestSerializedData getSerializedData() throws JsonParseException {
        return new RequestSerializedData(JsonParser.toJson(this.e));
    }

    @Override // ru.mail.verify.core.requests.RequestBase
    protected final String getRequestUrl() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return this.e.getData();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // ru.mail.verify.core.requests.RequestBase
    public String getMethodName() {
        return this.e.getData();
    }

    @Override // ru.mail.verify.core.requests.RequestBase
    protected RequestPersistentId getRequestData() {
        return this.e;
    }

    @Override // ru.mail.verify.core.requests.RequestBase
    @Nullable
    protected String getApiHost() {
        return null;
    }
}
