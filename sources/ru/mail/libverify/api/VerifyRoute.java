package ru.mail.libverify.api;

import ru.mail.verify.core.utils.Gsonable;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/VerifyRoute.class */
public enum VerifyRoute implements Gsonable {
    SMS,
    PUSH,
    CALL,
    CALLUI,
    CALLIN,
    VKCLogin;

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/VerifyRoute$InvalidRoute.class */
    public static class InvalidRoute extends RuntimeException {
    }
}
