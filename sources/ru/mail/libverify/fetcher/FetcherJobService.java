package ru.mail.libverify.fetcher;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import ru.mail.libverify.R;
import ru.mail.verify.core.utils.FileLog;

@RequiresApi(api = 21)
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/fetcher/FetcherJobService.class */
public class FetcherJobService extends JobService {
    private static volatile JobParameters a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean a(@NonNull Context context) {
        FileLog.d("FetcherJobService", "fetcher start requested");
        boolean z = false;
        try {
            JobScheduler jobScheduler = (JobScheduler) context.getSystemService("jobscheduler");
            if (jobScheduler != null) {
                if (jobScheduler.schedule(new JobInfo.Builder(context.getResources().getInteger(R.integer.libverify_fetcher_job_id), new ComponentName(context, FetcherJobService.class)).setRequiredNetworkType(Build.VERSION.SDK_INT >= 24 ? 3 : 1).build()) == 1) {
                    z = true;
                }
            }
        } catch (Throwable th) {
            FileLog.e("FetcherJobService", "failed to start service", th);
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean b(@NonNull Context context) {
        FileLog.d("FetcherJobService", "fetcher stop requested");
        a = null;
        boolean z = false;
        try {
            JobScheduler jobScheduler = (JobScheduler) context.getSystemService("jobscheduler");
            if (jobScheduler != null) {
                jobScheduler.cancel(context.getResources().getInteger(R.integer.libverify_fetcher_job_id));
                z = true;
            }
        } catch (Throwable th) {
            FileLog.e("FetcherJobService", "failed to stop service", th);
        }
        return z;
    }

    @Override // android.app.job.JobService
    public final boolean onStartJob(JobParameters jobParameters) {
        a = jobParameters;
        return true;
    }

    @Override // android.app.job.JobService
    public final boolean onStopJob(JobParameters jobParameters) {
        return a == null;
    }
}
