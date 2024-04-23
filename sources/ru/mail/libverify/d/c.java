package ru.mail.libverify.d;

import ru.mail.verify.core.utils.Gsonable;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/d/c.class */
public final class c implements Gsonable {
    String nonce;
    String api_key;

    public final String b() {
        return this.nonce;
    }

    public final String a() {
        return this.api_key;
    }
}
