package ru.mail.libverify.notifications;

import android.app.Activity;
import android.util.TypedValue;
import androidx.annotation.NonNull;
import androidx.appcompat.R;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/notifications/b.class */
final class b {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static int a(@NonNull Activity activity) {
        TypedValue typedValue = new TypedValue();
        activity.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }
}
