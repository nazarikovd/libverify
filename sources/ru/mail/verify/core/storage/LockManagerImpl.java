package ru.mail.verify.core.storage;

import android.content.Context;
import androidx.annotation.NonNull;
import javax.inject.Inject;
import javax.inject.Singleton;
import ru.mail.verify.core.utils.VerificationServiceProcessor;

@Singleton
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/storage/LockManagerImpl.class */
public class LockManagerImpl implements LockManager {
    private final Context a;

    /* JADX INFO: Access modifiers changed from: package-private */
    @Inject
    public LockManagerImpl(@NonNull Context context) {
        this.a = context;
    }

    @Override // ru.mail.verify.core.storage.LockManager
    public void acquireLock(@NonNull Object owner, boolean wakeLock, int flags) {
        VerificationServiceProcessor.acquire(this.a, owner, wakeLock);
        BroadcastManager.a(this.a, owner, flags);
    }

    @Override // ru.mail.verify.core.storage.LockManager
    public void releaseLock(@NonNull Object owner) {
        VerificationServiceProcessor.release(this.a, owner);
        BroadcastManager.a(this.a, owner);
    }

    @Override // ru.mail.verify.core.storage.LockManager
    public void releaseAllLocks() {
        VerificationServiceProcessor.releaseAll(this.a);
    }
}
