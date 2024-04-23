package ru.mail.libverify.sms;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import androidx.core.app.JobIntentService;
import ru.mail.libverify.R;
import ru.mail.verify.core.utils.FileLog;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/sms/IncomingCallReceiver.class */
public class IncomingCallReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    @SuppressLint({"UnsafeProtectedBroadcastReceiver"})
    public final void onReceive(Context context, Intent intent) {
        if (intent == null || context == null || !intent.hasExtra("state") || !TextUtils.equals(intent.getStringExtra("state"), TelephonyManager.EXTRA_STATE_RINGING)) {
            return;
        }
        FileLog.v("IncomingCallReceiver", "received call broadcast");
        Context applicationContext = context.getApplicationContext();
        int i = SmsHandlingService.a;
        try {
            JobIntentService.enqueueWork(applicationContext.getApplicationContext(), SmsHandlingService.class, applicationContext.getResources().getInteger(R.integer.libverify_sms_call_job_id), intent);
        } catch (Throwable th) {
            FileLog.e("SmsHandlingService", "failed to start a service", th);
        }
    }
}
