package ru.mail.verify.core.utils;

import android.content.Context;
import android.os.Build;
import androidx.annotation.NonNull;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/utils/VerificationServiceProcessor.class */
public class VerificationServiceProcessor {
    public static void acquire(@NonNull Context context, @NonNull Object owner, boolean wakeLock) {
        if (Build.VERSION.SDK_INT < 26 || !VerificationJobService.a(context, owner)) {
            VerificationService.a(context, owner, wakeLock);
        }
    }

    public static void release(@NonNull Context context, @NonNull Object owner) {
        if (Build.VERSION.SDK_INT < 26 || !VerificationJobService.b(context, owner)) {
            VerificationService.a(owner);
        }
    }

    public static void releaseAll(@NonNull Context context) {
        if (Build.VERSION.SDK_INT < 26 || !VerificationJobService.a(context)) {
            VerificationService.b();
        }
    }
}
