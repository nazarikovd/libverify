package ru.mail.verify.core.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import androidx.annotation.NonNull;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/utils/IntentProcessServiceProcessor.class */
public class IntentProcessServiceProcessor {
    public static final String LOG_TAG = "IntentProcessService";

    public static void start(@NonNull Context context, @NonNull Intent intent) {
        try {
            if (Build.VERSION.SDK_INT >= 26) {
                IntentProcessJobService.a(context, intent);
            } else {
                IntentProcessService.a(context, intent);
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "critical exception", e);
        }
    }
}
