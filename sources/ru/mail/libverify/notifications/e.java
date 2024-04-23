package ru.mail.libverify.notifications;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import ru.mail.verify.core.ui.notifications.NotificationBase;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/notifications/e.class */
abstract class e extends NotificationBase {

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/notifications/e$a.class */
    public static class a extends NotificationBase.IntentBuilder {
        /* JADX INFO: Access modifiers changed from: package-private */
        public a(@NonNull Context context, @NonNull String str) {
            super(context, new Intent(context, NotificationService.class), str);
        }

        @Override // ru.mail.verify.core.ui.notifications.NotificationBase.IntentBuilder
        public final PendingIntent build() {
            return PendingIntent.getService(this.context, NotificationBase.IntentBuilder.random.nextInt(), this.intent, new ru.mail.libverify.r.a().b().c().a());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/notifications/e$b.class */
    public static class b extends NotificationBase.IntentBuilder {
        /* JADX INFO: Access modifiers changed from: package-private */
        public b(@NonNull Context context) {
            super(context, new Intent(context, SettingsActivity.class), null);
            this.intent.setFlags(335544320);
        }

        @Override // ru.mail.verify.core.ui.notifications.NotificationBase.IntentBuilder
        public final PendingIntent build() {
            return PendingIntent.getActivity(this.context, NotificationBase.IntentBuilder.random.nextInt(), this.intent, new ru.mail.libverify.r.a().b().c().a());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/notifications/e$c.class */
    public static class c extends NotificationBase.IntentBuilder {
        /* JADX INFO: Access modifiers changed from: package-private */
        public c(@NonNull Context context) {
            super(context, new Intent(context, SmsCodeNotificationActivity.class), null);
            this.intent.setFlags(335544320);
        }

        @Override // ru.mail.verify.core.ui.notifications.NotificationBase.IntentBuilder
        public final PendingIntent build() {
            return PendingIntent.getActivity(this.context, NotificationBase.IntentBuilder.random.nextInt(), this.intent, new ru.mail.libverify.r.a().b().c().a());
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public e(@NonNull Context context) {
        super(context);
    }
}
