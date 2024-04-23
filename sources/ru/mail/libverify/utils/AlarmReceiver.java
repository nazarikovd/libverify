package ru.mail.libverify.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import ru.mail.libverify.api.VerificationFactory;
import ru.mail.libverify.api.i;
import ru.mail.libverify.r.a;
import ru.mail.verify.core.api.ApiManager;
import ru.mail.verify.core.storage.BroadcastManager;
import ru.mail.verify.core.ui.notifications.NotificationBase;
import ru.mail.verify.core.utils.DebugUtils;
import ru.mail.verify.core.utils.GCMTokenCheckType;
import ru.mail.verify.core.utils.IntentProcessServiceProcessor;
import ru.mail.verify.core.utils.components.BusMessageType;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/utils/AlarmReceiver.class */
public class AlarmReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        AlarmManager alarmManager;
        if (context == null || intent == null || intent.getAction() == null || (alarmManager = (AlarmManager) context.getSystemService("alarm")) == null) {
            return;
        }
        alarmManager.cancel(PendingIntent.getBroadcast(context, 0, intent, new a().c().a()));
        if (VerificationFactory.hasInstallation(context)) {
            intent.getAction();
            Intent intent2 = null;
            String action = intent.getAction();
            action.getClass();
            boolean z = true;
            switch (action.hashCode()) {
                case 529905078:
                    if (action.equals("unblock_notification")) {
                        z = false;
                        break;
                    }
                    break;
                case 1043572956:
                    if (action.equals("check_sms_templates")) {
                        z = true;
                        break;
                    }
                    break;
                case 1144303715:
                    if (action.equals("app_started")) {
                        z = true;
                        break;
                    }
                    break;
                case 1153603515:
                    if (action.equals("package_changed")) {
                        z = true;
                        break;
                    }
                    break;
                case 1242322408:
                    if (action.equals("refresh_push_token_once")) {
                        z = true;
                        break;
                    }
                    break;
                case 1361129850:
                    if (action.equals("check_settings")) {
                        z = true;
                        break;
                    }
                    break;
                case 1442811000:
                    if (action.equals("refresh_push_token")) {
                        z = true;
                        break;
                    }
                    break;
            }
            switch (z) {
                case false:
                    String stringExtra = intent.getStringExtra(NotificationBase.NOTIFICATION_ID_EXTRA);
                    Intent intent3 = new Intent(BusMessageType.SERVICE_SETTINGS_NOTIFICATION_UNBLOCK.name());
                    intent3.putExtra(NotificationBase.NOTIFICATION_ID_EXTRA, stringExtra);
                    intent2 = intent3;
                    break;
                case BroadcastManager.FLAG_NETWORK_RECEIVER /* 1 */:
                    intent2 = r0;
                    Intent intent4 = new Intent(BusMessageType.SERVICE_SETTINGS_CHECK.name());
                    intent4.putExtra("settings_action_type", i.a.SMS_TEMPLATES_CHECK.name());
                    break;
                case true:
                    intent2 = r0;
                    Intent intent5 = new Intent(BusMessageType.SERVICE_SETTINGS_CHECK.name());
                    intent5.putExtra("settings_action_type", i.a.RESTART.name());
                    break;
                case true:
                    String stringExtra2 = intent.getStringExtra("package_change_type");
                    stringExtra2.getClass();
                    if (!stringExtra2.equals("package_change_updated")) {
                        if (!stringExtra2.equals("package_change_removed")) {
                            DebugUtils.safeThrow("OldAlarmReceiver", "failed to process broadcast", new IllegalArgumentException("unknown change type"));
                            break;
                        } else {
                            intent2 = r0;
                            Intent intent6 = new Intent(BusMessageType.SERVICE_SETTINGS_CHECK.name());
                            intent6.putExtra("settings_action_type", i.a.PACKAGE_REMOVED.name());
                            break;
                        }
                    } else {
                        intent2 = r0;
                        Intent intent7 = new Intent(BusMessageType.SERVICE_SETTINGS_CHECK.name());
                        intent7.putExtra("settings_action_type", i.a.PACKAGE_UPDATED.name());
                        break;
                    }
                case true:
                    intent2 = r0;
                    Intent intent8 = new Intent(BusMessageType.GCM_REFRESH_TOKEN.name());
                    intent8.putExtra(ApiManager.GCM_TOKEN_CHECK_TYPE, GCMTokenCheckType.ONCE.name());
                    break;
                case true:
                    intent2 = r0;
                    Intent intent9 = new Intent(BusMessageType.SERVICE_SETTINGS_CHECK.name());
                    intent9.putExtra("settings_action_type", i.a.TIMER.name());
                    break;
                case true:
                    intent2 = r0;
                    Intent intent10 = new Intent(BusMessageType.GCM_REFRESH_TOKEN.name());
                    intent10.putExtra(ApiManager.GCM_TOKEN_CHECK_TYPE, GCMTokenCheckType.PERIODIC.name());
                    break;
                default:
                    DebugUtils.safeThrow("OldAlarmReceiver", "failed to process broadcast", new IllegalArgumentException("unknown action"));
                    break;
            }
            if (intent2 != null) {
                IntentProcessServiceProcessor.start(context, intent2);
            }
        }
    }
}
