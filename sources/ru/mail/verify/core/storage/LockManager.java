package ru.mail.verify.core.storage;

import androidx.annotation.NonNull;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/storage/LockManager.class */
public interface LockManager {
    void acquireLock(@NonNull Object obj, boolean z, int i);

    void releaseLock(@NonNull Object obj);

    void releaseAllLocks();
}
