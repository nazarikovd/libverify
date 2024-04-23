package ru.mail.libverify.j;

import androidx.annotation.Nullable;
import ru.mail.verify.core.requests.response.ResponseBase;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/j/h.class */
public final class h extends ResponseBase {
    private final int httpCode;
    @Nullable
    private final String locationHeader;
    private final long contentLength;

    public h(int i, @Nullable String str, long j) {
        this.httpCode = i;
        this.locationHeader = str;
        this.contentLength = j;
    }

    public final int a() {
        return this.httpCode;
    }

    @Override // ru.mail.verify.core.requests.response.ResponseBase
    public final boolean isOk() {
        return true;
    }

    @Nullable
    public final String b() {
        return this.locationHeader;
    }
}
