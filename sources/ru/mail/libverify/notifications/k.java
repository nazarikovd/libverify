package ru.mail.libverify.notifications;

import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import ru.mail.libverify.R;
import ru.mail.libverify.gcm.ServerNotificationMessage;
import ru.mail.libverify.notifications.e;
import ru.mail.verify.core.ui.notifications.NotificationBase;
import ru.mail.verify.core.ui.notifications.NotificationId;
import ru.mail.verify.core.utils.FileLog;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/notifications/k.class */
public final class k extends e {
    private final ServerNotificationMessage a;
    private final boolean b;

    public k(@NonNull Context context, @NonNull ServerNotificationMessage serverNotificationMessage, boolean z) {
        super(context);
        this.a = serverNotificationMessage;
        this.b = z;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final ServerNotificationMessage a() {
        return this.a;
    }

    @Override // ru.mail.verify.core.ui.notifications.NotificationBase
    public final NotificationId getId() {
        return NotificationId.SMS_CODE;
    }

    @Override // ru.mail.verify.core.ui.notifications.NotificationBase
    public final String getTag() {
        return this.a.getId();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // ru.mail.verify.core.ui.notifications.NotificationBase
    public final NotificationChannel getChannel() {
        return null;
    }

    @Override // ru.mail.verify.core.ui.notifications.NotificationBase
    public final String getChannelId() {
        if (isSilent()) {
            return this.context.getString(R.string.libverify_low_notification_id);
        }
        int i = Build.VERSION.SDK_INT;
        return this.context.getString(R.string.libverify_high_notification_id);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // ru.mail.verify.core.ui.notifications.NotificationBase
    public final NotificationChannelGroup getGroup() {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // ru.mail.verify.core.ui.notifications.NotificationBase
    public final void fillBuilder(NotificationCompat.Builder builder, ru.mail.libverify.o.f fVar) throws IllegalArgumentException {
        super.fillBuilder(builder, fVar);
        ServerNotificationMessage.Message message = this.a.getMessage();
        if (!TextUtils.isEmpty(message.getPublicText())) {
            NotificationCompat.Builder builder2 = new NotificationCompat.Builder(this.context, this.context.getString(isSilent() ? R.string.libverify_low_notification_id : R.string.libverify_high_notification_id));
            builder2.setContentTitle(message.getFrom());
            builder2.setContentText(message.getPublicText());
            builder2.setTicker(message.getPublicText());
            builder2.setWhen(this.a.getLocalTimestamp());
            builder2.setContentIntent(PendingIntent.getActivity(this.context, 0, new Intent(), new ru.mail.libverify.r.a().c().a()));
            builder2.setSmallIcon(R.drawable.libverify_ic_sms_white);
            builder2.setDeleteIntent(new e.a(this.context, "action_delete").putExtra(NotificationBase.NOTIFICATION_ID_EXTRA, this.a.getId()).build());
            builder2.setContentIntent(new e.c(this.context).putExtra(NotificationBase.NOTIFICATION_ID_EXTRA, this.a.getId()).build());
            builder2.setStyle(new NotificationCompat.BigTextStyle().bigText(message.getPublicText()));
            builder.setPublicVersion(builder2.build());
        }
        builder.setContentTitle(message.getFrom());
        builder.setContentText(message.getText());
        builder.setTicker(message.getText());
        builder.setWhen(this.a.getLocalTimestamp());
        builder.setContentIntent(PendingIntent.getActivity(this.context, 0, new Intent(), new ru.mail.libverify.r.a().c().a()));
        builder.setSmallIcon(R.drawable.libverify_ic_sms_white);
        builder.setDeleteIntent(new e.a(this.context, "action_delete").putExtra(NotificationBase.NOTIFICATION_ID_EXTRA, this.a.getId()).build());
        builder.setContentIntent(new e.c(this.context).putExtra(NotificationBase.NOTIFICATION_ID_EXTRA, this.a.getId()).build());
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(message.getText()));
        if (TextUtils.isEmpty(this.a.getSrcApplicationLogo())) {
            return;
        }
        try {
            Bitmap a = fVar.a(this.a.getSrcApplicationLogo());
            if (a != null) {
                builder.setLargeIcon(a);
            } else {
                FileLog.v("SmsCodeNotification", "Not found bitmap to show small image notification");
            }
        } catch (Exception e) {
            FileLog.e("SmsCodeNotification", "Failed to show image small image.", e);
        }
    }

    @Override // ru.mail.verify.core.ui.notifications.NotificationBase
    public final boolean isSilent() {
        return this.b || getShowCount() >= 1;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // ru.mail.verify.core.ui.notifications.NotificationBase
    public final boolean hasVibration() {
        return true;
    }

    @Override // ru.mail.verify.core.ui.notifications.NotificationBase
    protected final boolean hasLedLight() {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // ru.mail.verify.core.ui.notifications.NotificationBase
    public final boolean hasSound() {
        return true;
    }

    @Override // ru.mail.verify.core.ui.notifications.NotificationBase
    protected final int getLedColor() {
        return -1;
    }

    @Override // ru.mail.verify.core.ui.notifications.NotificationBase
    public final boolean isOngoing() {
        boolean z = getOngoingTimeout() != null;
        boolean z2 = z;
        FileLog.v("SmsCodeNotification", "is ongoing result: %s", Boolean.valueOf(z));
        return z2;
    }

    @Override // ru.mail.verify.core.ui.notifications.NotificationBase
    protected final Uri getSoundUri() {
        return RingtoneManager.getDefaultUri(2);
    }

    /* JADX WARN: Not initialized variable reg: 2, insn: ?: MOVE  (r1 I:??) = (r2 I:??), block:B:11:0x0056 */
    @Override // ru.mail.verify.core.ui.notifications.NotificationBase
    @Nullable
    public final Long getOngoingTimeout() {
        long j;
        if (this.a.getHoldTimeout() == null || this.a.getHoldTimeout().longValue() == 0) {
            FileLog.d("SmsCodeNotification", "notification hold timeout %s", this.a.getHoldTimeout());
            return null;
        }
        long abs = Math.abs(System.currentTimeMillis() - this.a.getServerTimestamp());
        if (abs > 1800000) {
            FileLog.d("SmsCodeNotification", "notification %s, outdated by server timeout (%d)", this.a.getId(), Long.valueOf(abs));
            return null;
        } else if (System.currentTimeMillis() - this.a.getLocalTimestamp() < 0) {
            FileLog.d("SmsCodeNotification", "notification %s, outdated by local timeout (%d)", this.a.getId(), Long.valueOf(j));
            return null;
        } else {
            long min = Math.min(this.a.getHoldTimeout().longValue(), 120000L) - j;
            FileLog.v("SmsCodeNotification", "notification %s, local diff %d, server diff %d, ongoing timeout %d", this.a.getId(), Long.valueOf(j), Long.valueOf(abs), Long.valueOf(min));
            if (min > 0) {
                return Long.valueOf(min);
            }
            return null;
        }
    }

    @Override // ru.mail.verify.core.ui.notifications.NotificationBase
    public final boolean shouldReplacePrevious() {
        return false;
    }
}
