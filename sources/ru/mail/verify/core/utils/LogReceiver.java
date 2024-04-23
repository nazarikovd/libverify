package ru.mail.verify.core.utils;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/utils/LogReceiver.class */
public interface LogReceiver {
    void v(String str, String str2);

    void v(String str, String str2, Throwable th);

    void e(String str, String str2);

    void e(String str, String str2, Throwable th);

    void d(String str, String str2);

    void d(String str, String str2, Throwable th);
}
