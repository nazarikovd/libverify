package ru.mail.libverify.i;

import androidx.annotation.NonNull;
import ru.mail.libverify.storage.InstanceConfig;
import ru.mail.verify.core.requests.RequestPersistentId;
import ru.mail.verify.core.requests.RequestSerializedData;
import ru.mail.verify.core.requests.response.ResponseBase;
import ru.mail.verify.core.utils.json.JsonParseException;
import ru.mail.verify.core.utils.json.JsonParser;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/i/e.class */
public final class e extends c<ru.mail.libverify.j.n> {
    private final String j;

    public e(@NonNull InstanceConfig instanceConfig) {
        super(instanceConfig);
        this.j = "gcm";
    }

    @Override // ru.mail.verify.core.requests.RequestBase
    public final RequestSerializedData getSerializedData() throws JsonParseException {
        return null;
    }

    @Override // ru.mail.libverify.i.c
    protected final boolean b() {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // ru.mail.verify.core.requests.RequestBase
    public final String getMethodName() {
        return this.j;
    }

    @Override // ru.mail.verify.core.requests.RequestBase
    protected final RequestPersistentId getRequestData() {
        return null;
    }

    @Override // ru.mail.verify.core.requests.RequestBase
    protected final ResponseBase parseJsonAnswer(String str) throws JsonParseException {
        return (ru.mail.libverify.j.n) JsonParser.fromJson(str, ru.mail.libverify.j.n.class);
    }
}
