package ru.mail.verify.core.utils;

import android.app.IntentService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import ru.mail.libverify.v.a;
import ru.mail.verify.core.utils.components.BusMessageType;
import ru.mail.verify.core.utils.components.MessageBusUtils;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/utils/IntentProcessService.class */
public class IntentProcessService extends IntentService {
    public IntentProcessService() {
        super(IntentProcessServiceProcessor.LOG_TAG);
        setIntentRedelivery(true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(@NonNull Context context, @NonNull Intent intent) {
        FileLog.v(IntentProcessServiceProcessor.LOG_TAG, "start %s (extras: %s)", intent, Utils.bundleToString(intent.getExtras()));
        Intent intent2 = new Intent(intent);
        intent2.setComponent(new ComponentName(context, IntentProcessService.class));
        try {
            context.startService(intent2);
        } catch (Throwable th) {
            FileLog.e(IntentProcessServiceProcessor.LOG_TAG, "failed to start settings service", th);
        }
    }

    @Override // android.app.IntentService, android.app.Service
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override // android.app.IntentService, android.app.Service
    public void onDestroy() {
        super.onDestroy();
    }

    @Override // android.app.IntentService
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent == null || TextUtils.isEmpty(intent.getAction())) {
            return;
        }
        FileLog.v(IntentProcessServiceProcessor.LOG_TAG, "handle %s (extras: %s)", intent, Utils.bundleToString(intent.getExtras()));
        try {
            a.b(this, MessageBusUtils.createOneArg(BusMessageType.valueOf(intent.getAction()), intent.getExtras()));
        } catch (IllegalArgumentException unused) {
            FileLog.e(IntentProcessServiceProcessor.LOG_TAG, "there is no type %s in allowed message types", intent.getAction());
        }
    }
}
