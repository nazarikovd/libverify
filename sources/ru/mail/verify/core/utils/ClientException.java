package ru.mail.verify.core.utils;

import androidx.annotation.NonNull;
import java.io.IOException;
import org.json.JSONException;
import ru.mail.verify.core.utils.json.JsonParseException;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/utils/ClientException.class */
public class ClientException extends Exception {
    private final ClientReason a;

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/utils/ClientException$ClientReason.class */
    public enum ClientReason {
        DEFAULT,
        CANCELLED,
        REJECTED_BY_POLICY,
        REJECTED_BY_INTERCEPTOR_ERROR,
        NO_INTERNET_PERMISSION
    }

    public ClientException(@NonNull IOException ex) {
        super(ex);
        this.a = ClientReason.DEFAULT;
    }

    public ClientReason getReason() {
        return this.a;
    }

    public ClientException(@NonNull SecurityException ex) {
        super(ex);
        this.a = ClientReason.NO_INTERNET_PERMISSION;
    }

    public ClientException(@NonNull String message, ClientReason reason) {
        super(message);
        this.a = reason;
    }

    public ClientException(@NonNull JSONException ex) {
        super(ex);
        this.a = ClientReason.DEFAULT;
    }

    public ClientException(@NonNull JsonParseException ex) {
        super(ex);
        this.a = ClientReason.DEFAULT;
    }
}
