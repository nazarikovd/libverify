package ru.mail.libverify.g;

import ru.mail.libverify.R;
import ru.mail.verify.core.ui.notifications.NotificationChannelSettings;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/g/d.class */
public final class d extends NotificationChannelSettings {
    @Override // ru.mail.verify.core.ui.notifications.NotificationChannelSettings
    protected final int getLowNotificationIdRes() {
        return R.string.libverify_low_notification_id;
    }

    @Override // ru.mail.verify.core.ui.notifications.NotificationChannelSettings
    protected final int getLowNotificationNameRes() {
        return R.string.libverify_low_notification_name;
    }

    @Override // ru.mail.verify.core.ui.notifications.NotificationChannelSettings
    protected final int getLowNotificationDescriptionRes() {
        return R.string.libverify_low_notification_description;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // ru.mail.verify.core.ui.notifications.NotificationChannelSettings
    public final int getHighNotificationIdRes() {
        return R.string.libverify_high_notification_id;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // ru.mail.verify.core.ui.notifications.NotificationChannelSettings
    public final int getHighNotificationNameRes() {
        return R.string.libverify_high_notification_name;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // ru.mail.verify.core.ui.notifications.NotificationChannelSettings
    public final int getHighNotificationDescriptionRes() {
        return R.string.libverify_high_notification_description;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // ru.mail.verify.core.ui.notifications.NotificationChannelSettings
    public final int getLedColorRes() {
        return R.string.libverify_resource_led_color_id;
    }
}
