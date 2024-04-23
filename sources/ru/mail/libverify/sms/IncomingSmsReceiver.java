package ru.mail.libverify.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.JobIntentService;
import ru.mail.libverify.R;
import ru.mail.verify.core.utils.FileLog;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/sms/IncomingSmsReceiver.class */
public class IncomingSmsReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        if (context == null || intent == null) {
            return;
        }
        FileLog.v("IncomingSmsReceiver", "received sms broadcast");
        Context applicationContext = context.getApplicationContext();
        int i = SmsHandlingService.a;
        try {
            JobIntentService.enqueueWork(applicationContext.getApplicationContext(), SmsHandlingService.class, applicationContext.getResources().getInteger(R.integer.libverify_sms_call_job_id), intent);
        } catch (Throwable th) {
            FileLog.e("SmsHandlingService", "failed to start a service", th);
        }
    }
}
