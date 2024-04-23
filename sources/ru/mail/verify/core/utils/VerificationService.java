package ru.mail.verify.core.utils;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import androidx.annotation.NonNull;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/utils/VerificationService.class */
public class VerificationService extends IntentService {
    private static final ConcurrentHashMap<Object, Boolean> b = new ConcurrentHashMap<>();
    private static volatile long c = 0;
    private static PowerManager.WakeLock d;
    private final long a;

    public VerificationService() {
        super("VerificationService");
        setIntentRedelivery(true);
        this.a = System.nanoTime();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v20 */
    /* JADX WARN: Type inference failed for: r0v24 */
    /* JADX WARN: Type inference failed for: r0v25 */
    /* JADX WARN: Type inference failed for: r0v26 */
    /* JADX WARN: Type inference failed for: r0v6, types: [java.lang.Class<ru.mail.verify.core.utils.VerificationService>] */
    /* JADX WARN: Type inference failed for: r0v7 */
    /* JADX WARN: Type inference failed for: r0v8, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v9, types: [java.lang.String] */
    public static void a(@NonNull Context context, @NonNull Object obj, boolean z) {
        if (b.putIfAbsent(obj, Boolean.valueOf(z)) == null) {
            FileLog.v("VerificationService", "acquire " + obj);
            if (z) {
                ?? r0 = VerificationService.class;
                synchronized (r0) {
                    try {
                        PowerManager.WakeLock wakeLock = d;
                        if (wakeLock == null || !wakeLock.isHeld()) {
                            PowerManager powerManager = (PowerManager) context.getSystemService("power");
                            r0 = powerManager;
                            if (powerManager != null) {
                                PowerManager.WakeLock newWakeLock = powerManager.newWakeLock(1, "ru.mail.verify.core.utils:VerificationService");
                                d = newWakeLock;
                                newWakeLock.acquire(60000L);
                                d.setReferenceCounted(false);
                                c = System.nanoTime();
                                String str = "VerificationService";
                                Object[] objArr = new Object[1];
                                objArr[0] = Long.valueOf(c);
                                FileLog.d("VerificationService", "wake lock acquired (timestamp: %d)", objArr);
                                r0 = str;
                            }
                        } else {
                            FileLog.d("VerificationService", "wake lock has been already acquired");
                            r0 = "VerificationService";
                        }
                    } finally {
                    }
                }
            }
            try {
                context.startService(new Intent(context, VerificationService.class));
            } catch (Throwable th) {
                FileLog.e("VerificationService", "failed to start verification service", th);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void b() {
        ConcurrentHashMap<Object, Boolean> concurrentHashMap = b;
        FileLog.v("VerificationService", "releaseAll count: %d", Integer.valueOf(concurrentHashMap.size()));
        concurrentHashMap.clear();
        c();
        synchronized (concurrentHashMap) {
            concurrentHashMap.notify();
        }
    }

    private static synchronized void c() {
        try {
            PowerManager.WakeLock wakeLock = d;
            if (wakeLock != null) {
                if (wakeLock.isHeld()) {
                    d.release();
                    d.release();
                }
                Object[] objArr = new Object[1];
                objArr[0] = Long.valueOf((System.nanoTime() - c) / 1000000);
                FileLog.d("VerificationService", "wake lock released (held for time: %d)", objArr);
                d = null;
                c = 0L;
            }
        } catch (Throwable th) {
            FileLog.e("VerificationService", "failed to release wake lock", th);
        }
    }

    @Override // android.app.IntentService, android.app.Service
    public int onStartCommand(Intent intent, int flags, int startId) {
        int size = b.size();
        if (size > 0) {
            FileLog.v("VerificationService", "onStartCommand started with count: %d", Integer.valueOf(size));
            return super.onStartCommand(intent, flags, startId);
        }
        stopSelf();
        return 2;
    }

    @Override // android.app.IntentService, android.app.Service
    public void onDestroy() {
        ConcurrentHashMap<Object, Boolean> concurrentHashMap = b;
        FileLog.v("VerificationService", "service destroyed with count: %d", Integer.valueOf(concurrentHashMap.size()));
        concurrentHashMap.clear();
        c();
        super.onDestroy();
    }

    /* JADX WARN: Type inference failed for: r3v2, types: [long, java.lang.Object[]] */
    @Override // android.app.IntentService
    protected void onHandleIntent(Intent intent) {
        ConcurrentHashMap<Object, Boolean> concurrentHashMap;
        ?? r3;
        FileLog.v("VerificationService", "onHandleIntent before loop with count: %d", Integer.valueOf(b.size()));
        while (true) {
            concurrentHashMap = b;
            if (concurrentHashMap.size() <= 0) {
                return;
            }
            r3 = new Object[]{Integer.valueOf(concurrentHashMap.size())};
            FileLog.v("VerificationService", "onHandleIntent loop with count: %d", (Object[]) r3);
            try {
                synchronized (concurrentHashMap) {
                    concurrentHashMap.wait(30000L);
                }
                if ((System.nanoTime() - this.a) / 1000000 < 0 || r3 > 300000) {
                    break;
                }
                a();
                FileLog.v("VerificationService", "onHandleIntent loop end, uptime: %d", Long.valueOf((long) r3));
            } catch (InterruptedException e) {
                FileLog.e("VerificationService", "onHandleIntent wait failed", e);
                b.clear();
                c();
                return;
            }
        }
        FileLog.v("VerificationService", "onHandleIntent wait for keep alive operation expired, uptime: %d", Long.valueOf((long) r3));
        concurrentHashMap.clear();
        c();
    }

    private static synchronized void a() {
        try {
            PowerManager.WakeLock wakeLock = d;
            if (wakeLock == null || !wakeLock.isHeld()) {
                return;
            }
            long nanoTime = (System.nanoTime() - c) / 1000000;
            if (nanoTime < 0 || nanoTime > 60000) {
                c();
            }
        } catch (Throwable th) {
            FileLog.e("VerificationService", "failed to check wake lock expiration", th);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Type inference failed for: r0v5, types: [java.lang.Throwable, java.util.concurrent.ConcurrentHashMap<java.lang.Object, java.lang.Boolean>, java.util.concurrent.ConcurrentHashMap, java.lang.Object] */
    public static void a(@NonNull Object obj) {
        ConcurrentHashMap<Object, Boolean> concurrentHashMap = b;
        Boolean remove = concurrentHashMap.remove(obj);
        if (remove != null) {
            FileLog.v("VerificationService", "release owner: %s with wakeLock: %s", obj, remove);
            if (remove.booleanValue()) {
                boolean z = false;
                Iterator<Map.Entry<Object, Boolean>> it = concurrentHashMap.entrySet().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    Map.Entry<Object, Boolean> next = it.next();
                    if (next.getValue() != null && next.getValue().booleanValue()) {
                        z = true;
                        break;
                    }
                }
                if (!z) {
                    FileLog.d("VerificationService", "no more wakelock owners detected");
                    c();
                }
            }
            ?? r0 = b;
            if (r0.size() == 0) {
                synchronized (r0) {
                    r0.notify();
                }
            }
        }
    }
}
