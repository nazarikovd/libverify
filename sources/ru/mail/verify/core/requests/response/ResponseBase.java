package ru.mail.verify.core.requests.response;

import androidx.annotation.NonNull;
import ru.mail.verify.core.requests.RequestBase;
import ru.mail.verify.core.utils.Gsonable;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/requests/response/ResponseBase.class */
public abstract class ResponseBase<T extends RequestBase> implements Gsonable {
    private transient T a;
    private transient long b = 0;
    private transient long c = 0;

    @NonNull
    public T getOwner() {
        return this.a;
    }

    public void setOwner(@NonNull T owner) {
        this.a = owner;
    }

    public abstract boolean isOk();

    public long getSentTimestamp() {
        return this.b;
    }

    public void setSentTimestamp(long sentTimestamp) {
        this.b = sentTimestamp;
    }

    public long getReceiveTimestamp() {
        return this.c;
    }

    public void setReceiveTimestamp(long receiveTimestamp) {
        this.c = receiveTimestamp;
    }
}
