package ru.mail.verify.core.requests;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import ru.mail.verify.core.utils.Gsonable;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/requests/ConstantRequestData.class */
public class ConstantRequestData implements Gsonable, RequestPersistentId {
    private String data;
    private String tag;
    private transient String a;

    private ConstantRequestData() {
        this.data = null;
    }

    @Override // ru.mail.verify.core.requests.RequestPersistentId
    public String getId() {
        if (this.tag == null) {
            return this.data;
        }
        if (this.a == null) {
            this.a = this.data + this.tag;
        }
        return this.a;
    }

    public String getTag() {
        return this.tag;
    }

    public String getData() {
        return this.data;
    }

    public ConstantRequestData(@NonNull String data, @Nullable String tag) {
        this.data = data;
        this.tag = tag;
    }
}
