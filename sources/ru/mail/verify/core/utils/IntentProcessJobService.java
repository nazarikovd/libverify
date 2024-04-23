package ru.mail.verify.core.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;
import androidx.core.app.VerifySafeJobIntentService;
import ru.mail.libverify.R;
import ru.mail.libverify.v.a;
import ru.mail.verify.core.utils.components.BusMessageType;
import ru.mail.verify.core.utils.components.MessageBusUtils;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/utils/IntentProcessJobService.class */
public class IntentProcessJobService extends VerifySafeJobIntentService {
    public IntentProcessJobService() {
        FileLog.v("IntentProcessJobService", "service created");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(@NonNull Context context, @NonNull Intent intent) {
        FileLog.v("IntentProcessJobService", "start %s (extras: %s)", intent, Utils.bundleToString(intent.getExtras()));
        new Intent(intent).setComponent(new ComponentName(context, IntentProcessJobService.class));
        JobIntentService.enqueueWork(context.getApplicationContext(), IntentProcessJobService.class, context.getResources().getInteger(R.integer.libverify_settings_job_id), intent);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        FileLog.v("IntentProcessJobService", "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        FileLog.v("IntentProcessJobService", "service destroyed");
        super.onDestroy();
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected void onHandleWork(@NonNull Intent intent) {
        if (TextUtils.isEmpty(intent.getAction())) {
            return;
        }
        FileLog.v("IntentProcessJobService", "handle %s (extras: %s)", intent, Utils.bundleToString(intent.getExtras()));
        try {
            a.b(this, MessageBusUtils.createOneArg(BusMessageType.valueOf(intent.getAction()), intent.getExtras()));
        } catch (IllegalArgumentException unused) {
            FileLog.e("IntentProcessJobService", "there is no type %s in allowed message types", intent.getAction());
        }
    }
}
