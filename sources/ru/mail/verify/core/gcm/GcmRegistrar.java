package ru.mail.verify.core.gcm;

import ru.mail.verify.core.api.ApiPlugin;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/gcm/GcmRegistrar.class */
public interface GcmRegistrar extends ApiPlugin {
    String getRegistrationId();

    boolean isRegistered();
}
