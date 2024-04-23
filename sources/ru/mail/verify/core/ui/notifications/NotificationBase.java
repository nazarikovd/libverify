package ru.mail.verify.core.ui.notifications;

import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import java.util.Random;
import ru.mail.libverify.o.f;
import ru.mail.libverify.r.a;
import ru.mail.verify.core.utils.IntentProcessService;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/ui/notifications/NotificationBase.class */
public abstract class NotificationBase {
    public static final String NOTIFICATION_ID_EXTRA = "notification_id";
    public static final String NOTIFICATION_ACTIVITY_ID_EXTRA = "activity_id";
    public static final String NOTIFICATION_BUTTON_INDEX = "button_index";
    private static final int LED_COLOR_ON_OFF_DURATION = 1500;
    private int showCount = 0;
    protected final Context context;

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/ui/notifications/NotificationBase$IntentBuilder.class */
    public static abstract class IntentBuilder {
        protected static final Random random = new Random();
        protected final Intent intent;
        protected final Context context;

        public IntentBuilder(@NonNull Context context, @NonNull Intent intent, @NonNull String action) {
            this.intent = intent;
            this.context = context;
            intent.setAction(action);
        }

        public IntentBuilder putExtra(@NonNull String type, Integer value) {
            this.intent.putExtra(type, value);
            return this;
        }

        public abstract PendingIntent build();

        public IntentBuilder putExtra(@NonNull String type, String value) {
            this.intent.putExtra(type, value);
            return this;
        }
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/ui/notifications/NotificationBase$ServiceIntentBuilder.class */
    public static class ServiceIntentBuilder extends IntentBuilder {
        public ServiceIntentBuilder(@NonNull Context context, @NonNull String action) {
            super(context, new Intent(context, IntentProcessService.class), action);
        }

        @Override // ru.mail.verify.core.ui.notifications.NotificationBase.IntentBuilder
        public PendingIntent build() {
            return PendingIntent.getService(this.context, IntentBuilder.random.nextInt(), this.intent, new a().b().c().a());
        }
    }

    public NotificationBase(@NonNull Context context) {
        this.context = context;
    }

    public abstract NotificationId getId();

    public abstract String getTag();

    public NotificationCompat.Builder getBuilder(f fVar) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this.context, getChannelId());
        fillBuilder(builder, fVar);
        return builder;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void fillBuilder(NotificationCompat.Builder builder, f fVar) throws IllegalArgumentException {
        boolean isOngoing = isOngoing();
        if (isOngoing || !isSilent()) {
            builder.setPriority(2);
        } else {
            builder.setPriority(0);
        }
        builder.setOngoing(isOngoing);
        builder.setDefaults(0);
        builder.setLocalOnly(true);
        builder.setCategory("msg");
        if (isSilent()) {
            builder.setSound((Uri) null);
            builder.setLights(getLedColor(), 0, 0);
            return;
        }
        if (hasSound()) {
            builder.setSound(getSoundUri());
        }
        if (hasLedLight()) {
            builder.setLights(getLedColor(), (int) LED_COLOR_ON_OFF_DURATION, (int) LED_COLOR_ON_OFF_DURATION);
        }
    }

    public int getShowCount() {
        return this.showCount;
    }

    public final void onShown() {
        this.showCount++;
    }

    public abstract boolean isSilent();

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract NotificationChannel getChannel();

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract NotificationChannelGroup getGroup();

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract boolean hasVibration();

    protected abstract boolean hasLedLight();

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract boolean hasSound();

    protected abstract int getLedColor();

    protected abstract Uri getSoundUri();

    public abstract boolean isOngoing();

    public abstract String getChannelId();

    @Nullable
    public abstract Long getOngoingTimeout();

    public abstract boolean shouldReplacePrevious();
}
