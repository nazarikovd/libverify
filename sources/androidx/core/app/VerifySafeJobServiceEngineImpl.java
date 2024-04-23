package androidx.core.app;

import android.app.job.JobParameters;
import android.app.job.JobServiceEngine;
import android.app.job.JobWorkItem;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.RequiresApi;
import androidx.core.app.JobIntentService;

@RequiresApi(26)
/* loaded from: libverify-2.11.0.aar:classes.jar:androidx/core/app/VerifySafeJobServiceEngineImpl.class */
public class VerifySafeJobServiceEngineImpl extends JobServiceEngine implements JobIntentService.CompatJobEngine {
    private static final String TAG = "VerifySafeJobServiceEngineImpl";
    private static final boolean DEBUG = false;
    private final JobIntentService mService;
    private final Object mLock;
    private JobParameters mParams;

    /* loaded from: libverify-2.11.0.aar:classes.jar:androidx/core/app/VerifySafeJobServiceEngineImpl$a.class */
    final class a implements JobIntentService.GenericWorkItem {
        final JobWorkItem a;

        a(JobWorkItem jobWorkItem) {
            this.a = jobWorkItem;
        }

        public final Intent getIntent() {
            return this.a.getIntent();
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v0, types: [androidx.core.app.VerifySafeJobServiceEngineImpl$a] */
        /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
        /* JADX WARN: Type inference failed for: r0v3, types: [android.app.job.JobParameters] */
        /* JADX WARN: Type inference failed for: r0v5 */
        /* JADX WARN: Type inference failed for: r1v9, types: [java.lang.Throwable, android.app.job.JobWorkItem] */
        public final void complete() {
            ?? r1;
            ?? r0 = this;
            Object obj = VerifySafeJobServiceEngineImpl.this.mLock;
            synchronized (obj) {
                ?? r02 = VerifySafeJobServiceEngineImpl.this.mParams;
                if (r02 != 0) {
                    try {
                        r1 = this.a;
                        r02.completeWork(r1);
                    } catch (IllegalArgumentException unused) {
                        r1.printStackTrace();
                        r0 = obj;
                    } catch (SecurityException unused2) {
                        r1.printStackTrace();
                        r0 = obj;
                    }
                }
                r0 = obj;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public VerifySafeJobServiceEngineImpl(JobIntentService service) {
        super(service);
        this.mLock = new Object();
        this.mService = service;
    }

    public IBinder compatGetBinder() {
        return getBinder();
    }

    @Override // android.app.job.JobServiceEngine
    public boolean onStartJob(JobParameters params) {
        this.mParams = params;
        this.mService.ensureProcessorRunningLocked(false);
        return true;
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [java.lang.Throwable, boolean] */
    @Override // android.app.job.JobServiceEngine
    public boolean onStopJob(JobParameters params) {
        ?? doStopCurrentWork = this.mService.doStopCurrentWork();
        synchronized (this.mLock) {
            this.mParams = null;
        }
        return doStopCurrentWork;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:18:0x002a  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0042 A[RETURN] */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable, android.app.job.JobParameters] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public androidx.core.app.JobIntentService.GenericWorkItem dequeueWork() {
        /*
            r5 = this;
            r0 = r5
            r1 = r0
            r2 = 0
            r6 = r2
            java.lang.Object r1 = r1.mLock
            r2 = r1
            r7 = r2
            monitor-enter(r1)
            android.app.job.JobParameters r0 = r0.mParams     // Catch: java.lang.Throwable -> L44
            r1 = r0
            r8 = r1
            if (r0 != 0) goto L16
            r0 = 0
            r1 = r7
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L44
            return r0
        L16:
            r0 = r8
            android.app.job.JobWorkItem r0 = r0.dequeueWork()     // Catch: java.lang.IllegalArgumentException -> L1e java.lang.SecurityException -> L21 java.lang.Throwable -> L44
            r6 = r0
            goto L24
        L1e:
            goto L21
        L21:
            r1.printStackTrace()     // Catch: java.lang.Throwable -> L44
        L24:
            r0 = r6
            r1 = r7
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L44
            if (r0 == 0) goto L42
            r0 = r6
            android.content.Intent r0 = r0.getIntent()
            r1 = r5
            androidx.core.app.JobIntentService r1 = r1.mService
            java.lang.ClassLoader r1 = r1.getClassLoader()
            r0.setExtrasClassLoader(r1)
            androidx.core.app.VerifySafeJobServiceEngineImpl$a r0 = new androidx.core.app.VerifySafeJobServiceEngineImpl$a
            r1 = r0
            r2 = r5
            r3 = r6
            r1.<init>(r3)
            return r0
        L42:
            r0 = 0
            return r0
        L44:
            r1 = move-exception
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L44
            throw r0     // Catch: java.lang.Throwable -> L44
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.app.VerifySafeJobServiceEngineImpl.dequeueWork():androidx.core.app.JobIntentService$GenericWorkItem");
    }
}
