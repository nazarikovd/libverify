package ru.mail.verify.core.utils;

import java.security.SecureRandom;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/utils/SessionIdGeneratorImpl.class */
public class SessionIdGeneratorImpl implements SessionIdGenerator {
    @Override // ru.mail.verify.core.utils.SessionIdGenerator
    public String generateId() {
        PRNGFixes.apply();
        byte[] bArr = new byte[16];
        new SecureRandom().nextBytes(bArr);
        return Utils.bytesToHexString(bArr);
    }
}
