package ru.mail.libverify.notifications;

import android.app.IntentService;
import android.content.Intent;
import android.text.TextUtils;
import java.util.Locale;
import ru.mail.verify.core.storage.BroadcastManager;
import ru.mail.verify.core.ui.notifications.NotificationBase;
import ru.mail.verify.core.utils.DebugUtils;
import ru.mail.verify.core.utils.FileLog;
import ru.mail.verify.core.utils.components.BusMessageType;
import ru.mail.verify.core.utils.components.MessageBusUtils;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/notifications/NotificationService.class */
public class NotificationService extends IntentService {
    public NotificationService() {
        super("NotificationService");
    }

    @Override // android.app.IntentService
    protected final void onHandleIntent(Intent intent) {
        if (intent == null) {
            return;
        }
        String action = intent.getAction();
        if (TextUtils.isEmpty(action)) {
            FileLog.e("NotificationService", "Wrong action type detected");
            return;
        }
        String stringExtra = intent.getStringExtra(NotificationBase.NOTIFICATION_ID_EXTRA);
        FileLog.v("NotificationService", "received extra %s from notification %s", action, stringExtra);
        action.getClass();
        boolean z = true;
        switch (action.hashCode()) {
            case -964594249:
                if (action.equals("action_confirm")) {
                    z = false;
                    break;
                }
                break;
            case 1064330403:
                if (action.equals("action_cancel")) {
                    z = true;
                    break;
                }
                break;
            case 1096596436:
                if (action.equals("action_delete")) {
                    z = true;
                    break;
                }
                break;
        }
        switch (z) {
            case false:
                ru.mail.libverify.v.a.b(this, MessageBusUtils.createOneArg(BusMessageType.SERVICE_NOTIFICATION_CONFIRM, stringExtra));
                return;
            case BroadcastManager.FLAG_NETWORK_RECEIVER /* 1 */:
            case true:
                ru.mail.libverify.v.a.b(this, MessageBusUtils.createOneArg(BusMessageType.SERVICE_NOTIFICATION_CANCEL, stringExtra));
                return;
            default:
                DebugUtils.safeThrow("NotificationService", "wrong action type", new IllegalArgumentException(String.format(Locale.US, "Wrong action type %s for NotificationService detected", action)));
                return;
        }
    }
}
