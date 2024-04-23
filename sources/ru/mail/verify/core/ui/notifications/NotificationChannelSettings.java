package ru.mail.verify.core.ui.notifications;

import android.app.NotificationChannel;
import android.content.Context;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/ui/notifications/NotificationChannelSettings.class */
public abstract class NotificationChannelSettings {
    public NotificationChannel getLowNotificationChannel(@NonNull Context context) {
        if (Build.VERSION.SDK_INT < 26) {
            return null;
        }
        NotificationChannel notificationChannel = new NotificationChannel(context.getString(getLowNotificationIdRes()), context.getString(getLowNotificationNameRes()), 3);
        notificationChannel.setDescription(context.getString(getLowNotificationDescriptionRes()));
        notificationChannel.enableLights(false);
        notificationChannel.enableVibration(false);
        return notificationChannel;
    }

    @StringRes
    protected abstract int getLowNotificationIdRes();

    @StringRes
    protected abstract int getLowNotificationNameRes();

    @StringRes
    protected abstract int getLowNotificationDescriptionRes();

    /* JADX INFO: Access modifiers changed from: protected */
    @StringRes
    public abstract int getHighNotificationIdRes();

    /* JADX INFO: Access modifiers changed from: protected */
    @StringRes
    public abstract int getHighNotificationNameRes();

    /* JADX INFO: Access modifiers changed from: protected */
    @StringRes
    public abstract int getHighNotificationDescriptionRes();

    /* JADX INFO: Access modifiers changed from: protected */
    @StringRes
    public abstract int getLedColorRes();
}
