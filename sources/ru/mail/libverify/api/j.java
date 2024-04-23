package ru.mail.libverify.api;

import android.content.Context;
import androidx.annotation.NonNull;
import ru.mail.libverify.R;
import ru.mail.libverify.api.VerificationApi;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/j.class */
final class j {
    private static VerificationApi.FailReason a;
    private static VerificationApi.FailReason b;
    private static VerificationApi.FailReason c;
    private static Context d;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static VerificationApi.FailReason a() {
        if (a == null) {
            VerificationApi.FailReason failReason = VerificationApi.FailReason.GENERAL_ERROR;
            Context context = d;
            a = failReason.a(context != null ? context.getString(R.string.general_error_description) : null);
        }
        return a;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static VerificationApi.FailReason c() {
        if (b == null) {
            VerificationApi.FailReason failReason = VerificationApi.FailReason.NO_NETWORK;
            Context context = d;
            b = failReason.a(context != null ? context.getString(R.string.network_error_description) : null);
        }
        return b;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static VerificationApi.FailReason b() {
        if (c == null) {
            VerificationApi.FailReason failReason = VerificationApi.FailReason.NETWORK_ERROR;
            Context context = d;
            c = failReason.a(context != null ? context.getString(R.string.general_error_description) : null);
        }
        return c;
    }

    public static void a(@NonNull Context context) {
        d = context;
    }
}
