package ru.mail.libverify.storage;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/storage/DecryptionError.class */
public class DecryptionError extends Exception {
    public DecryptionError(Exception exc) {
        super(exc);
    }

    public DecryptionError(String str) {
        super(str);
    }
}
