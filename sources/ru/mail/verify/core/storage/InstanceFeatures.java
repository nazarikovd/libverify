package ru.mail.verify.core.storage;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/storage/InstanceFeatures.class */
public interface InstanceFeatures {
    Boolean broadcastOnDemand();

    Boolean interceptAlienSms();

    Boolean singleFetcher();

    Boolean useSafetyNet();

    Boolean accountCheckWithSms();

    Boolean trackPackageUpdate();

    Boolean sendCallStats();

    Boolean updateAlarms();

    Boolean backgroundVerify();

    Boolean writeHistory();

    Boolean addShortcut();
}
