package ru.mail.libverify.fetcher;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import ru.mail.verify.core.utils.DebugUtils;
import ru.mail.verify.core.utils.FileLog;
import ru.mail.verify.core.utils.Utils;
import ru.mail.verify.core.utils.components.BusMessageType;
import ru.mail.verify.core.utils.components.MessageBusUtils;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/fetcher/FetcherService.class */
public class FetcherService extends Service {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(@NonNull Context context) {
        FileLog.d("FetcherService", "fetcher start requested");
        Intent intent = new Intent(context, FetcherService.class);
        intent.setAction("fetcher_start");
        try {
            context.startService(intent);
        } catch (Throwable th) {
            FileLog.e("FetcherService", "failed to start fetcher service", th);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void b(@NonNull Context context) {
        FileLog.d("FetcherService", "fetcher stop requested");
        Intent intent = new Intent(context, FetcherService.class);
        intent.setAction("fetcher_stop");
        try {
            context.startService(intent);
        } catch (Throwable th) {
            FileLog.e("FetcherService", "failed to stop fetcher service", th);
        }
    }

    @Override // android.app.Service
    public final int onStartCommand(Intent intent, int i, int i2) {
        super.onStartCommand(intent, i, i2);
        Object[] objArr = new Object[2];
        objArr[0] = intent == null ? null : intent.getAction();
        objArr[1] = intent == null ? null : Utils.bundleToString(intent.getExtras());
        FileLog.v("FetcherService", "onStartCommand with action %s, extra %s", objArr);
        if (intent == null || TextUtils.isEmpty(intent.getAction())) {
            try {
                ru.mail.libverify.v.a.a(this, MessageBusUtils.createOneArg(BusMessageType.SERVICE_FETCHER_START_WITH_CHECK, (Object) null));
                return 1;
            } catch (Throwable th) {
                DebugUtils.safeThrow("FetcherService", "failed to process fetcher start", th);
                return 1;
            }
        }
        String action = intent.getAction();
        action.getClass();
        if (action.equals("fetcher_stop")) {
            FileLog.d("FetcherService", "fetcher service has been stopped from an application");
            stopSelf();
            return 1;
        } else if (action.equals("fetcher_start")) {
            FileLog.d("FetcherService", "fetcher service has been started from an application");
            return 1;
        } else {
            DebugUtils.safeThrow("FetcherService", "illegal fetcher service action", new IllegalAccessException("illegal fetcher service action"));
            stopSelf();
            return 1;
        }
    }

    @Override // android.app.Service
    public final void onDestroy() {
        FileLog.v("FetcherService", "service destroyed");
        super.onDestroy();
    }

    @Override // android.app.Service
    @Nullable
    public final IBinder onBind(Intent intent) {
        return null;
    }
}
