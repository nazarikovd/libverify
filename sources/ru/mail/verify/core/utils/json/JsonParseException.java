package ru.mail.verify.core.utils.json;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/utils/json/JsonParseException.class */
public class JsonParseException extends RuntimeException {
    public JsonParseException(String message) {
        super(message);
    }

    public JsonParseException(String message, Throwable throwable) {
        super(null, throwable);
    }
}
