package ru.mail.libverify.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.PowerManager;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import java.util.concurrent.atomic.AtomicReference;
import ru.mail.verify.core.utils.FileLog;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/utils/ScreenStateReceiver.class */
public class ScreenStateReceiver extends BroadcastReceiver {
    private static final AtomicReference<a> a = new AtomicReference<>();
    private static volatile PowerManager b;

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/utils/ScreenStateReceiver$a.class */
    private static final class a {
        final boolean a;
        final long b;

        private a(boolean z, long j) {
            this.a = z;
            this.b = j;
        }

        public final String toString() {
            return "ScreenStateInfo{isActive=" + this.a + ", timestamp=" + this.b + '}';
        }
    }

    public static void b(@NonNull Context context) {
        IntentFilter intentFilter = new IntentFilter("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        ScreenStateReceiver screenStateReceiver = new ScreenStateReceiver();
        try {
            if (Build.VERSION.SDK_INT >= 33) {
                context.registerReceiver(screenStateReceiver, intentFilter, 4);
            } else {
                context.registerReceiver(screenStateReceiver, intentFilter);
            }
        } catch (Throwable th) {
            FileLog.e("ScreenStateReceiver", "failed to register receiver", th);
        }
    }

    @NonNull
    public static ScreenState a(@NonNull Context context) {
        ScreenState screenState;
        if (b == null) {
            synchronized (ScreenStateReceiver.class) {
                if (b == null) {
                    b = (PowerManager) context.getSystemService("power");
                }
            }
        }
        PowerManager powerManager = b;
        if (powerManager == null ? true : powerManager.isInteractive()) {
            screenState = r0;
            ScreenState screenState2 = new ScreenState(true, null);
        } else {
            a aVar = a.get();
            if (aVar == null || aVar.a) {
                screenState = r0;
                ScreenState screenState3 = new ScreenState(false, null);
            } else {
                long currentTimeMillis = System.currentTimeMillis() - aVar.b;
                long j = currentTimeMillis;
                if (currentTimeMillis < 0) {
                    j = 0;
                }
                screenState = new ScreenState(false, Long.valueOf(j));
            }
        }
        ScreenState screenState4 = screenState;
        FileLog.d("ScreenStateReceiver", "current state %s", screenState);
        return screenState4;
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        a aVar;
        if (intent == null || TextUtils.isEmpty(intent.getAction())) {
            aVar = null;
        } else {
            aVar = r0;
            a aVar2 = new a(intent.getAction().equals("android.intent.action.SCREEN_ON"), System.currentTimeMillis());
        }
        a.set(aVar);
        FileLog.d("ScreenStateReceiver", "received state %s", aVar);
    }
}
