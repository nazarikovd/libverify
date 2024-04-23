package ru.mail.libverify.sms;

import android.content.Intent;
import android.os.Build;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.core.app.VerifySafeJobIntentService;
import ru.mail.libverify.v.a;
import ru.mail.verify.core.utils.DebugUtils;
import ru.mail.verify.core.utils.FileLog;
import ru.mail.verify.core.utils.Utils;
import ru.mail.verify.core.utils.components.BusMessageType;
import ru.mail.verify.core.utils.components.MessageBusUtils;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/sms/SmsHandlingService.class */
public class SmsHandlingService extends VerifySafeJobIntentService {
    public static final /* synthetic */ int a = 0;

    /* JADX WARN: Multi-variable type inference failed */
    private void b(@NonNull Intent intent) {
        try {
            SmsMessage[] a2 = a(intent);
            if (a2.length <= 0) {
                FileLog.e("SmsHandlingService", "received message is empty");
                return;
            }
            String displayOriginatingAddress = a2[0].getDisplayOriginatingAddress();
            StringBuilder sb = new StringBuilder(160);
            for (SmsMessage smsMessage : a2) {
                sb.append(smsMessage.getMessageBody());
            }
            String sb2 = sb.toString();
            if (TextUtils.isEmpty(displayOriginatingAddress) || TextUtils.isEmpty(sb2)) {
                FileLog.e("SmsHandlingService", "received ether message or phoneNumber is empty");
                return;
            }
            FileLog.v("SmsHandlingService", "received message");
            a.b(this, MessageBusUtils.createMultipleArgs(BusMessageType.SERVICE_SMS_RECEIVED, displayOriginatingAddress, sb2));
        } catch (Throwable th) {
            FileLog.e("SmsHandlingService", "can't parse sms message %s", th.getMessage());
            intent.removeExtra("pdus");
            DebugUtils.safeThrow("SmsHandlingService", "handleSmsMessages", new RuntimeException(new RuntimeException("intent : " + Utils.bundleToString(intent.getExtras()), th)));
        }
    }

    private static SmsMessage[] a(Intent intent) {
        Object[] objArr = (Object[]) intent.getSerializableExtra("pdus");
        String stringExtra = intent.getStringExtra("format");
        int length = objArr.length;
        SmsMessage[] smsMessageArr = new SmsMessage[length];
        for (int i = 0; i < length; i++) {
            byte[] bArr = (byte[]) objArr[i];
            if (Build.VERSION.SDK_INT < 23 || TextUtils.isEmpty(stringExtra)) {
                smsMessageArr[i] = SmsMessage.createFromPdu(bArr);
            } else {
                smsMessageArr[i] = SmsMessage.createFromPdu(bArr, stringExtra);
            }
        }
        return smsMessageArr;
    }

    public final void onDestroy() {
        super.onDestroy();
        FileLog.v("SmsHandlingService", "service destroyed");
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected final void onHandleWork(@NonNull Intent intent) {
        if (intent.getExtras() == null) {
            FileLog.e("SmsHandlingService", "Incoming intent extras is null");
            return;
        }
        try {
            if (intent.hasExtra("pdus")) {
                Object[] objArr = new Object[1];
                objArr[0] = Utils.bundleToString(intent.getExtras());
                FileLog.v("SmsHandlingService", "Incoming sms dump %s", objArr);
                b(intent);
            } else if (intent.hasExtra("state") && TextUtils.equals(intent.getStringExtra("state"), TelephonyManager.EXTRA_STATE_RINGING)) {
                FileLog.v("SmsHandlingService", "received call");
                if (intent.getExtras() != null) {
                    String string = intent.getExtras().getString("incoming_number");
                    if (!TextUtils.isEmpty(string)) {
                        a.b(this, MessageBusUtils.createOneArg(BusMessageType.SERVICE_CALL_RECEIVED, string));
                    }
                }
            } else {
                FileLog.v("SmsHandlingService", "empty intent");
            }
        } catch (Throwable th) {
            FileLog.e("SmsHandlingService", "failed to process incoming sms %s", th.getMessage());
        }
    }
}
