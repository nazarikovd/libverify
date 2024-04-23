package ru.mail.verify.core.utils;

import androidx.annotation.Nullable;
import java.util.Locale;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/utils/FileLog.class */
public class FileLog {
    private static volatile LogReceiver a;

    public static void init(@Nullable LogReceiver logReceiver) {
        a = logReceiver;
    }

    private static boolean a() {
        return a != null;
    }

    public static void e(String tag, String message) {
        if (a()) {
            a.e(tag, message);
        }
    }

    public static void d(String tag, String message) {
        if (a()) {
            a.d(tag, message);
        }
    }

    public static void v(String tag, String message) {
        if (a()) {
            a.v(tag, message);
        }
    }

    public static void e(String tag, String message, Throwable exception) {
        if (a()) {
            a.e(tag, message, exception);
        }
    }

    public static void d(String tag, String message, Throwable exception) {
        if (a()) {
            a.d(tag, message, exception);
        }
    }

    public static void v(String tag, String message, Throwable exception) {
        if (a()) {
            a.v(tag, message, exception);
        }
    }

    public static void e(String tag, String messageFormat, Object... arguments) {
        if (a()) {
            a.e(tag, String.format(Locale.US, messageFormat, arguments));
        }
    }

    public static void d(String tag, String messageFormat, Object... arguments) {
        if (a()) {
            a.d(tag, String.format(Locale.US, messageFormat, arguments));
        }
    }

    public static void v(String tag, String messageFormat, Object... arguments) {
        if (a()) {
            a.v(tag, String.format(Locale.US, messageFormat, arguments));
        }
    }

    public static void e(String tag, Throwable exception, String messageFormat, Object... arguments) {
        if (a()) {
            a.e(tag, String.format(Locale.US, messageFormat, arguments), exception);
        }
    }

    public static void d(String tag, Throwable exception, String messageFormat, Object... arguments) {
        if (a()) {
            a.d(tag, String.format(Locale.US, messageFormat, arguments), exception);
        }
    }

    public static void v(String tag, Throwable exception, String messageFormat, Object... arguments) {
        if (a()) {
            a.v(tag, String.format(Locale.US, messageFormat, arguments), exception);
        }
    }
}
