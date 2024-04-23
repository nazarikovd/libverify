package ru.mail.platform.libverify.sms;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;
import androidx.core.app.VerifySafeJobIntentService;
import java.util.concurrent.Executors;
import ru.mail.libverify.AppStateModel;
import ru.mail.libverify.R;
import ru.mail.libverify.api.VerificationFactory;
import ru.mail.libverify.k.o;
import ru.mail.libverify.platform.core.PlatformCoreService;
import ru.mail.libverify.platform.core.SmsRetrieverResult;
import ru.mail.verify.core.utils.FileLog;
import ru.mail.verify.core.utils.components.BusMessageType;
import ru.mail.verify.core.utils.components.MessageBusUtils;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/platform/libverify/sms/SmsRetrieverService.class */
public class SmsRetrieverService extends VerifySafeJobIntentService {
    public static final int SMS_SAVE_STATE_TIMEOUT = 300000;
    static o a;

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/platform/libverify/sms/SmsRetrieverService$a.class */
    class a implements Runnable {
        final /* synthetic */ o a;

        a(o oVar) {
            this.a = oVar;
        }

        @Override // java.lang.Runnable
        public final void run() {
            ru.mail.libverify.v.a.b(this.a.c(), MessageBusUtils.createMultipleArgs(BusMessageType.SERVICE_SMS_RETRIEVER_SMS_RECEIVED, Integer.valueOf(this.a.b()), this.a.a()));
            SmsRetrieverService.a = null;
        }
    }

    public static void resendState() {
        o oVar = a;
        if (oVar == null) {
            return;
        }
        if (System.currentTimeMillis() - oVar.d() > 300000) {
            a = null;
        } else {
            Executors.newFixedThreadPool(4).submit(new a(oVar));
        }
    }

    public static void enqueueWork(@NonNull Context context, @NonNull Intent intent) {
        try {
            JobIntentService.enqueueWork(context.getApplicationContext(), SmsRetrieverService.class, context.getResources().getInteger(R.integer.libverify_sms_retriever_job_id), intent);
        } catch (Throwable th) {
            FileLog.e("SmsRetrieverService", "failed to start a service", th);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected final void onHandleWork(@NonNull Intent intent) {
        PlatformCoreService platformService;
        SmsRetrieverResult smsRetrieverService;
        Bundle extras = intent.getExtras();
        if (extras == null || (platformService = VerificationFactory.getPlatformService(getApplicationContext())) == null || (smsRetrieverService = platformService.smsRetrieverService(extras)) == null) {
            return;
        }
        if (AppStateModel.getState() != AppStateModel.b.INACTIVE) {
            ru.mail.libverify.v.a.b(this, MessageBusUtils.createMultipleArgs(BusMessageType.SERVICE_SMS_RETRIEVER_SMS_RECEIVED, Integer.valueOf(smsRetrieverService.getResultStatus()), smsRetrieverService.getResultMessage()));
        } else {
            a = new o(this, smsRetrieverService.getResultStatus(), smsRetrieverService.getResultMessage(), System.currentTimeMillis());
        }
    }
}
