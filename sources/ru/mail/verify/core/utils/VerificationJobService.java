package ru.mail.verify.core.utils;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;
import ru.mail.libverify.R;

@RequiresApi(api = 21)
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/utils/VerificationJobService.class */
public class VerificationJobService extends JobService {
    private static final ConcurrentHashMap<Object, Object> b = new ConcurrentHashMap<>();
    private static final ExecutorService c = Executors.newCachedThreadPool();
    private static final AtomicReference<JobParameters> d = new AtomicReference<>();
    private final long a = System.currentTimeMillis();

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/utils/VerificationJobService$a.class */
    class a implements Runnable {
        a() {
        }

        /* JADX WARN: Type inference failed for: r0v1, types: [java.util.concurrent.ConcurrentHashMap<java.lang.Object, java.lang.Object>, java.lang.Throwable, java.util.concurrent.ConcurrentHashMap, java.lang.Object] */
        /* JADX WARN: Type inference failed for: r3v2, types: [long, java.lang.String] */
        @Override // java.lang.Runnable
        public final void run() {
            ?? r3;
            FileLog.v("VerificationJobService", "wait task started");
            do {
                ?? r0 = VerificationJobService.b;
                if (r0.size() <= 0) {
                    break;
                }
                r3 = "wait task loop " + Integer.toString(r0.size());
                FileLog.v("VerificationJobService", r3);
                try {
                    synchronized (r0) {
                        r0.wait(30000L);
                    }
                    if (System.currentTimeMillis() - VerificationJobService.this.a < 0) {
                        break;
                    }
                } catch (InterruptedException e) {
                    FileLog.e("VerificationJobService", "wait task failed", e);
                }
            } while (r3 <= 300000);
            FileLog.v("VerificationJobService", "wait task for keep alive operation expired");
            FileLog.v("VerificationJobService", "wait task completed");
            VerificationJobService.d.set(null);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean a(@NonNull Context context, @NonNull Object obj) {
        FileLog.v("VerificationJobService", "acquire " + obj);
        ConcurrentHashMap<Object, Object> concurrentHashMap = b;
        if (concurrentHashMap.containsKey(obj)) {
            return true;
        }
        concurrentHashMap.put(obj, obj);
        return b(context);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean b(@NonNull Context context, @NonNull Object obj) {
        ConcurrentHashMap<Object, Object> concurrentHashMap = b;
        if (concurrentHashMap.remove(obj) == null) {
            FileLog.e("VerificationJobService", "no actions to release for owner %s", obj);
            return true;
        }
        FileLog.v("VerificationJobService", "release owner %s", obj);
        if (concurrentHashMap.size() == 0) {
            return a(context);
        }
        return true;
    }

    private static boolean c(@NonNull Context context) {
        boolean z = false;
        try {
            JobScheduler jobScheduler = (JobScheduler) context.getSystemService("jobscheduler");
            if (jobScheduler != null) {
                jobScheduler.cancel(context.getResources().getInteger(R.integer.libverify_verification_job_id));
                z = true;
            }
        } catch (Throwable th) {
            FileLog.e("VerificationJobService", "failed to stop service", th);
        }
        return z;
    }

    @Override // android.app.Service
    public void onDestroy() {
        FileLog.v("VerificationJobService", "service destroyed with count: %d", Integer.valueOf(b.size()));
        a(this);
        super.onDestroy();
    }

    @Override // android.app.job.JobService
    public boolean onStartJob(JobParameters params) {
        if (d.compareAndSet(null, params)) {
            c.submit(new a());
            return true;
        }
        return true;
    }

    @Override // android.app.job.JobService
    public boolean onStopJob(JobParameters params) {
        return d.get() != null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Multi-variable type inference failed */
    public static boolean a(@NonNull Context context) {
        FileLog.v("VerificationJobService", "releaseAll");
        d.set(null);
        ConcurrentHashMap<Object, Object> concurrentHashMap = b;
        concurrentHashMap.clear();
        synchronized (concurrentHashMap) {
            concurrentHashMap.notify();
        }
        return c(context);
    }

    private static boolean b(@NonNull Context context) {
        boolean z = false;
        try {
            JobScheduler jobScheduler = (JobScheduler) context.getSystemService("jobscheduler");
            if (jobScheduler != null) {
                if (jobScheduler.schedule(new JobInfo.Builder(context.getResources().getInteger(R.integer.libverify_verification_job_id), new ComponentName(context, VerificationJobService.class)).setRequiredNetworkType(Build.VERSION.SDK_INT >= 24 ? 3 : 1).build()) == 1) {
                    z = true;
                }
            }
        } catch (Throwable th) {
            FileLog.e("VerificationJobService", "failed to start a service", th);
        }
        return z;
    }
}
