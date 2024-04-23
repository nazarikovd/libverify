package ru.mail.verify.core.utils;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationManagerCompat;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/utils/NotificationUtils.class */
public class NotificationUtils {
    @TargetApi(19)
    public static boolean isNotificationEnabled(@NonNull Context context, @Nullable String channelId) {
        if (Build.VERSION.SDK_INT < 19) {
            return true;
        }
        return isNotificationChannelEnabled(context, channelId);
    }

    public static boolean isNotificationChannelEnabled(@NonNull Context context, @Nullable String channelId) {
        try {
            if (NotificationManagerCompat.from(context).areNotificationsEnabled()) {
                if (Build.VERSION.SDK_INT >= 26) {
                    if (TextUtils.isEmpty(channelId)) {
                        return false;
                    }
                    NotificationChannel notificationChannel = ((NotificationManager) context.getSystemService("notification")).getNotificationChannel(channelId);
                    if (notificationChannel == null) {
                        return true;
                    }
                    Object[] objArr = new Object[2];
                    objArr[0] = channelId;
                    objArr[1] = Integer.valueOf(notificationChannel.getImportance());
                    FileLog.v("NotificationUtils", "Notification channel %s (importance: %s)", objArr);
                    return notificationChannel.getImportance() != 0;
                }
                return true;
            }
            return false;
        } catch (Throwable th) {
            FileLog.e("NotificationUtils", "Failed to check notification availability", th);
            return true;
        }
    }
}
