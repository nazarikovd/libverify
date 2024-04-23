package ru.mail.verify.core.requests;

import androidx.annotation.NonNull;
import ru.mail.verify.core.utils.DebugUtils;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/requests/Utils.class */
public class Utils {
    public static void safeExecute(@NonNull String tag, @NonNull ActionExecutor executor, @NonNull RequestBase request) {
        try {
            executor.execute(request);
        } catch (Throwable th) {
            DebugUtils.safeThrow(tag, th, "Failed to launch request", new Object[0]);
        }
    }
}
