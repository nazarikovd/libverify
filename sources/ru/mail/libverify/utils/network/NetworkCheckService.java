package ru.mail.libverify.utils.network;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;
import androidx.core.app.VerifySafeJobIntentService;
import ru.mail.libverify.R;
import ru.mail.verify.core.utils.FileLog;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/utils/network/NetworkCheckService.class */
public class NetworkCheckService extends VerifySafeJobIntentService {
    private final Object b = new Object();
    private final long a = System.nanoTime();

    public static void a(@NonNull Context context) {
        boolean z = false;
        try {
            JobScheduler jobScheduler = (JobScheduler) context.getSystemService("jobscheduler");
            if (jobScheduler != null) {
                if (jobScheduler.schedule(new JobInfo.Builder(context.getResources().getInteger(R.integer.libverify_network_check_job_id), new ComponentName(context, NetworkCheckService.class)).setRequiredNetworkType(1).build()) == 1) {
                    z = true;
                }
            }
        } catch (Throwable th) {
            FileLog.e("NetworkCheckService", "failed to start network check", th);
        }
        if (z) {
            return;
        }
        try {
            JobIntentService.enqueueWork(context.getApplicationContext(), NetworkCheckService.class, context.getResources().getInteger(R.integer.libverify_network_check_job_id), new Intent("check_default"));
        } catch (Throwable th2) {
            FileLog.e("NetworkCheckService", "failed to start a service", th2);
        }
    }

    public final int onStartCommand(Intent intent, int i, int i2) {
        FileLog.v("NetworkCheckService", "onStartCommand");
        return super.onStartCommand(intent, i, i2);
    }

    public final void onDestroy() {
        FileLog.v("NetworkCheckService", "service destroyed");
        super.onDestroy();
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0037, code lost:
        r4 = new java.lang.Object[2];
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x0040, code lost:
        r6 = r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x004a, code lost:
        r4[0] = java.lang.Integer.valueOf(r14);
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0054, code lost:
        r6 = java.lang.Long.valueOf(r6);
        r4[1] = r6;
        ru.mail.verify.core.utils.FileLog.v("NetworkCheckService", "onHandleIntent on iteration = %d remaining time = %d", r4);
        r1 = r13.b;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0065, code lost:
        if (r0 >= 0) goto L30;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0068, code lost:
        r15 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x006d, code lost:
        r0 = r14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x007a, code lost:
        if (((r0 * r0) * 200) <= r15) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0080, code lost:
        r15 = r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0083, code lost:
        r1.wait(r15);
        ru.mail.verify.core.utils.network.NetworkStateReceiver.testNetwork(r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0090, code lost:
        if (ru.mail.verify.core.utils.network.NetworkStateReceiver.isAnything() == false) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0093, code lost:
        ru.mail.verify.core.utils.FileLog.v("NetworkCheckService", "onHandleIntent internet connection detected");
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x009f, code lost:
        monitor-exit(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x00a0, code lost:
        r14 = r14 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x00a3, code lost:
        r15 = r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x00b3, code lost:
        if (((java.lang.System.nanoTime() - r13.a) / 1000000) >= 0) goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x00be, code lost:
        ru.mail.verify.core.utils.FileLog.e("NetworkCheckService", "onHandleIntent wait loop interrupted");
     */
    /* JADX WARN: Code restructure failed: missing block: B:3:0x0019, code lost:
        if (r0 < 0) goto L3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:4:0x001c, code lost:
        r15 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:5:0x0021, code lost:
        r15 = 600000 - r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x002a, code lost:
        if (r15 <= 0) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x002d, code lost:
        r0 = r15;
        r2 = r13.b;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0036, code lost:
        monitor-enter(r2);
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v2, types: [java.lang.Long] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:29:0x00b3 -> B:4:0x001c). Please submit an issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:5:0x0021 -> B:6:0x0027). Please submit an issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected final void onHandleWork(@androidx.annotation.NonNull android.content.Intent r14) {
        /*
            Method dump skipped, instructions count: 216
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: ru.mail.libverify.utils.network.NetworkCheckService.onHandleWork(android.content.Intent):void");
    }
}
