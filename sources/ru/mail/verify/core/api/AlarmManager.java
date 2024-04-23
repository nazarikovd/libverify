package ru.mail.verify.core.api;

import ru.mail.verify.core.utils.AlarmReceiver;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/api/AlarmManager.class */
public interface AlarmManager {
    AlarmReceiver.AlarmBuilder createBuilder();

    void cancelAll();
}
