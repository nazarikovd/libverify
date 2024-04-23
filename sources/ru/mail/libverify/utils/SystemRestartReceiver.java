package ru.mail.libverify.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import ru.mail.libverify.api.VerificationFactory;
import ru.mail.libverify.api.i;
import ru.mail.verify.core.api.ApiManager;
import ru.mail.verify.core.utils.GCMTokenCheckType;
import ru.mail.verify.core.utils.IntentProcessServiceProcessor;
import ru.mail.verify.core.utils.components.BusMessageType;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/utils/SystemRestartReceiver.class */
public class SystemRestartReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        if (context == null || intent == null || intent.getAction() == null || !"android.intent.action.BOOT_COMPLETED".equals(intent.getAction()) || !VerificationFactory.hasInstallation(context)) {
            return;
        }
        Intent intent2 = new Intent(BusMessageType.GCM_REFRESH_TOKEN.name());
        intent2.putExtra(ApiManager.GCM_TOKEN_CHECK_TYPE, GCMTokenCheckType.ONCE.name());
        IntentProcessServiceProcessor.start(context, intent2);
        Intent intent3 = new Intent(BusMessageType.SERVICE_SETTINGS_CHECK.name());
        intent3.putExtra("settings_action_type", i.a.RESTART.name());
        IntentProcessServiceProcessor.start(context, intent3);
    }
}
