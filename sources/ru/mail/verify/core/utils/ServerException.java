package ru.mail.verify.core.utils;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/utils/ServerException.class */
public class ServerException extends Exception {
    private final int a;

    public ServerException(int statusCode) {
        super("response code is " + statusCode);
        this.a = statusCode;
    }

    public int getStatusCode() {
        return this.a;
    }
}
