package androidx.core.app;

import android.os.Build;
import androidx.core.app.JobIntentService;

/* loaded from: libverify-2.11.0.aar:classes.jar:androidx/core/app/VerifySafeJobIntentService.class */
public abstract class VerifySafeJobIntentService extends JobIntentService {
    final JobIntentService.GenericWorkItem dequeueWork() {
        JobIntentService.GenericWorkItem dequeueWork;
        try {
            dequeueWork = super.dequeueWork();
            return dequeueWork;
        } catch (SecurityException unused) {
            dequeueWork.printStackTrace();
            return null;
        }
    }

    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= 26) {
            ((JobIntentService) this).mJobImpl = new VerifySafeJobServiceEngineImpl(this);
        } else {
            ((JobIntentService) this).mJobImpl = null;
        }
    }
}
