package ru.mail.libverify.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import ru.mail.libverify.api.VerificationFactory;
import ru.mail.libverify.api.i;
import ru.mail.verify.core.api.ApiManager;
import ru.mail.verify.core.storage.BroadcastManager;
import ru.mail.verify.core.utils.GCMTokenCheckType;
import ru.mail.verify.core.utils.IntentProcessServiceProcessor;
import ru.mail.verify.core.utils.components.BusMessageType;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/utils/PackageStateReceiver.class */
public class PackageStateReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        if (context == null || intent == null || TextUtils.isEmpty(intent.getAction())) {
            return;
        }
        String.format("received %s %s", intent.getAction(), intent.getData());
        String action = intent.getAction();
        action.getClass();
        boolean z = true;
        switch (action.hashCode()) {
            case -810471698:
                if (action.equals("android.intent.action.PACKAGE_REPLACED")) {
                    z = false;
                    break;
                }
                break;
            case 525384130:
                if (action.equals("android.intent.action.PACKAGE_REMOVED")) {
                    z = true;
                    break;
                }
                break;
            case 1737074039:
                if (action.equals("android.intent.action.MY_PACKAGE_REPLACED")) {
                    z = true;
                    break;
                }
                break;
        }
        switch (z) {
            case false:
                if (TextUtils.equals(intent.getData() == null ? null : intent.getData().toString(), "package:" + context.getPackageName()) && VerificationFactory.hasInstallation(context)) {
                    Intent intent2 = new Intent(BusMessageType.GCM_REFRESH_TOKEN.name());
                    intent2.putExtra(ApiManager.GCM_TOKEN_CHECK_TYPE, GCMTokenCheckType.ONCE.name());
                    IntentProcessServiceProcessor.start(context, intent2);
                    ru.mail.verify.core.utils.AlarmReceiver.create(context, false).setAction(BusMessageType.SERVICE_SETTINGS_CHECK.name()).setTimeout(3600000L).putExtra("settings_action_type", i.a.PACKAGE_UPDATED.name()).setUp();
                    return;
                }
                return;
            case BroadcastManager.FLAG_NETWORK_RECEIVER /* 1 */:
                if (VerificationFactory.hasInstallation(context)) {
                    ru.mail.verify.core.utils.AlarmReceiver.create(context, false).setAction(BusMessageType.SERVICE_SETTINGS_CHECK.name()).setTimeout(3600000L).putExtra("settings_action_type", i.a.PACKAGE_REMOVED.name()).setUp();
                    return;
                }
                return;
            case true:
                if (VerificationFactory.hasInstallation(context)) {
                    Intent intent3 = new Intent(BusMessageType.GCM_REFRESH_TOKEN.name());
                    intent3.putExtra(ApiManager.GCM_TOKEN_CHECK_TYPE, GCMTokenCheckType.ONCE.name());
                    IntentProcessServiceProcessor.start(context, intent3);
                    ru.mail.verify.core.utils.AlarmReceiver.create(context, false).setAction(BusMessageType.SERVICE_SETTINGS_CHECK.name()).setTimeout(3600000L).putExtra("settings_action_type", i.a.PACKAGE_UPDATED.name()).setUp();
                    return;
                }
                return;
            default:
                return;
        }
    }
}
