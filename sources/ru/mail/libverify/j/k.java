package ru.mail.libverify.j;

import ru.mail.verify.core.utils.Gsonable;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/j/k.class */
public final class k implements Gsonable {
    String crypted_key;
    String crypted_message;

    public final String a() {
        return this.crypted_key;
    }

    public final String b() {
        return this.crypted_message;
    }
}
