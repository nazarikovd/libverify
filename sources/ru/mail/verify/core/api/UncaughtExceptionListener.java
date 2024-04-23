package ru.mail.verify.core.api;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/api/UncaughtExceptionListener.class */
public interface UncaughtExceptionListener {
    @WorkerThread
    void uncaughtException(@Nullable Thread thread, @NonNull Throwable th);
}
