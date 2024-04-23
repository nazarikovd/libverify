package ru.mail.libverify.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import ru.mail.libverify.api.VerificationFactory;
import ru.mail.verify.core.utils.FileLog;
import ru.mail.verify.core.utils.IntentProcessServiceProcessor;
import ru.mail.verify.core.utils.components.BusMessageType;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/utils/BatteryLevelReceiver.class */
public class BatteryLevelReceiver extends BroadcastReceiver {
    private static volatile boolean a = false;

    public static boolean a() {
        return a;
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        if (context == null || intent == null || TextUtils.isEmpty(intent.getAction()) || !VerificationFactory.hasInstallation(context)) {
            return;
        }
        FileLog.d("BatteryLevelReceiver", "onReceive %s", intent.getAction());
        Intent intent2 = null;
        String action = intent.getAction();
        boolean z = true;
        int hashCode = action.hashCode();
        if (hashCode != -1980154005) {
            if (hashCode != -1886648615) {
                if (hashCode != 490310653) {
                    if (hashCode == 1019184907 && action.equals("android.intent.action.ACTION_POWER_CONNECTED")) {
                        z = true;
                    }
                } else if (action.equals("android.intent.action.BATTERY_LOW")) {
                    z = false;
                }
            } else if (action.equals("android.intent.action.ACTION_POWER_DISCONNECTED")) {
                z = true;
            }
        } else if (action.equals("android.intent.action.BATTERY_OKAY")) {
            z = true;
        }
        if (!z) {
            a = true;
            intent2 = r0;
            Intent intent3 = new Intent(BusMessageType.SERVICE_SETTINGS_BATTERY_STATE_CHANGED.name());
            intent3.putExtra("battery_level_low", true);
        } else if (z || z) {
            a = false;
            intent2 = r0;
            Intent intent4 = new Intent(BusMessageType.SERVICE_SETTINGS_BATTERY_STATE_CHANGED.name());
            intent4.putExtra("battery_level_low", false);
        }
        if (intent2 != null) {
            IntentProcessServiceProcessor.start(context, intent2);
        }
    }
}
