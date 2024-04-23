package ru.mail.verify.core.gcm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;
import androidx.core.app.VerifySafeJobIntentService;
import java.util.Map;
import ru.mail.libverify.R;
import ru.mail.verify.core.api.ApiManager;
import ru.mail.verify.core.utils.FileLog;
import ru.mail.verify.core.utils.GCMTokenCheckType;
import ru.mail.verify.core.utils.components.BusMessageType;
import ru.mail.verify.core.utils.components.MessageBusUtils;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/gcm/GcmProcessService.class */
public class GcmProcessService extends VerifySafeJobIntentService {
    public static void processMessage(@NonNull Context context, @NonNull String from, @NonNull Bundle data) {
        Intent intent = new Intent(context, GcmProcessService.class);
        intent.setAction("process_message");
        intent.putExtra("from", from);
        intent.putExtra("data", data);
        try {
            JobIntentService.enqueueWork(context.getApplicationContext(), GcmProcessService.class, context.getResources().getInteger(R.integer.libverify_gcm_process_job_id), intent);
        } catch (Throwable th) {
            FileLog.e("GcmProcessService", "failed to start a service", th);
        }
    }

    public static boolean checkLibverifyMessage(@NonNull Context context, @NonNull String from, @NonNull Bundle data) {
        return a.a(context, from, data);
    }

    public static void refreshToken(@NonNull Context context) {
        Intent intent = new Intent(context, GcmProcessService.class);
        intent.setAction("refresh_token");
        try {
            JobIntentService.enqueueWork(context.getApplicationContext(), GcmProcessService.class, context.getResources().getInteger(R.integer.libverify_gcm_process_job_id), intent);
        } catch (Throwable th) {
            FileLog.e("GcmProcessService", "failed to start a service", th);
        }
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        FileLog.v("GcmProcessService", "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        FileLog.v("GcmProcessService", "service destroyed");
        super.onDestroy();
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected void onHandleWork(@NonNull Intent intent) {
        if ("refresh_token".equals(intent.getAction())) {
            Bundle bundle = new Bundle();
            bundle.putString(ApiManager.GCM_TOKEN_CHECK_TYPE, GCMTokenCheckType.ONCE.name());
            ru.mail.libverify.v.a.b(this, MessageBusUtils.createOneArg(BusMessageType.GCM_REFRESH_TOKEN, bundle));
            return;
        }
        String stringExtra = intent.getStringExtra("from");
        Bundle bundleExtra = intent.getBundleExtra("data");
        if (TextUtils.isEmpty(stringExtra) || bundleExtra == null) {
            FileLog.e("GcmMessageProcessor", "wrong message received (either 'from' or 'data' is empty)");
        } else if (!TextUtils.equals(stringExtra, ru.mail.libverify.v.a.d(this))) {
            FileLog.d("GcmMessageProcessor", "skip message from unknown server", stringExtra);
        } else {
            String string = bundleExtra.getString("server_info");
            if (!TextUtils.isEmpty(string)) {
                ru.mail.libverify.v.a.b(this, MessageBusUtils.createMultipleArgs(BusMessageType.GCM_SERVER_INFO_RECEIVED, stringExtra, string));
            }
            String string2 = bundleExtra.getString("fetcher_info");
            if (!TextUtils.isEmpty(string2)) {
                ru.mail.libverify.v.a.b(this, MessageBusUtils.createMultipleArgs(BusMessageType.GCM_FETCHER_INFO_RECEIVED, stringExtra, string2));
            }
            String string3 = bundleExtra.getString("data");
            String str = string3;
            if (TextUtils.isEmpty(string3)) {
                str = bundleExtra.getString("libverify_data");
            }
            if (TextUtils.isEmpty(str)) {
                FileLog.e("GcmMessageProcessor", "wrong message received (message data is empty)");
                return;
            }
            FileLog.v("GcmMessageProcessor", "message received from %s with text %s", stringExtra, str);
            ru.mail.libverify.v.a.b(this, MessageBusUtils.createMultipleArgs(BusMessageType.GCM_MESSAGE_RECEIVED, stringExtra, str, bundleExtra.getString("key")));
        }
    }

    public static void processMessage(@NonNull Context context, @NonNull String from, @NonNull Map<String, String> data) {
        Intent intent = new Intent(context, GcmProcessService.class);
        intent.setAction("process_message");
        intent.putExtra("from", from);
        Bundle bundle = new Bundle();
        for (Map.Entry<String, String> entry : data.entrySet()) {
            bundle.putString(entry.getKey(), entry.getValue());
        }
        intent.putExtra("data", bundle);
        try {
            JobIntentService.enqueueWork(context.getApplicationContext(), GcmProcessService.class, context.getResources().getInteger(R.integer.libverify_gcm_process_job_id), intent);
        } catch (Throwable th) {
            FileLog.e("GcmProcessService", "failed to start a service", th);
        }
    }

    public static boolean checkLibverifyMessage(@NonNull Context context, @NonNull String from, @NonNull Map<String, String> dataMap) {
        return a.a(context, from, dataMap);
    }
}
