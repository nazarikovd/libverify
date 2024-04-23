package ru.mail.libverify.fetcher;

import android.content.Context;
import android.os.Build;
import androidx.annotation.NonNull;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/fetcher/a.class */
public final class a {
    public static void a(@NonNull Context context) {
        if (Build.VERSION.SDK_INT < 26 || !FetcherJobService.a(context)) {
            FetcherService.a(context);
        }
    }

    public static void b(@NonNull Context context) {
        if (Build.VERSION.SDK_INT < 26 || !FetcherJobService.b(context)) {
            FetcherService.b(context);
        }
    }
}
