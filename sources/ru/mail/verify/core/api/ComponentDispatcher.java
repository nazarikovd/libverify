package ru.mail.verify.core.api;

import android.os.Build;
import android.os.HandlerThread;
import androidx.annotation.NonNull;
import java.lang.Thread;
import ru.mail.verify.core.utils.FileLog;
import ru.mail.verify.core.utils.components.CustomHandler;
import ru.mail.verify.core.utils.components.MessageHandler;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/api/ComponentDispatcher.class */
public final class ComponentDispatcher implements Thread.UncaughtExceptionHandler {
    private final MessageHandler a;
    private final Thread.UncaughtExceptionHandler b;
    private final String c;
    private volatile HandlerThread d;
    private volatile CustomHandler e;
    private int f = 0;

    public ComponentDispatcher(@NonNull String name, @NonNull MessageHandler messageHandler, @NonNull Thread.UncaughtExceptionHandler exceptionHandler) {
        this.c = name;
        this.a = messageHandler;
        this.b = exceptionHandler;
    }

    public void shutdown() {
        HandlerThread handlerThread = this.d;
        if (handlerThread != null) {
            if (Build.VERSION.SDK_INT < 18) {
                handlerThread.quit();
            } else {
                handlerThread.quitSafely();
            }
        }
    }

    public void stop() {
        CustomHandler customHandler = this.e;
        if (customHandler != null) {
            customHandler.removeCallbacksAndMessages(null);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v4, types: [ru.mail.verify.core.api.ComponentDispatcher] */
    /* JADX WARN: Type inference failed for: r0v5, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v7 */
    public CustomHandler getDispatcher() {
        if (this.e == null) {
            ?? r0 = this;
            synchronized (r0) {
                if (r0.e == null) {
                    this.d = new HandlerThread(this.c);
                    this.d.setUncaughtExceptionHandler(this);
                    this.d.start();
                    this.e = new CustomHandler(this.d.getLooper(), this.a);
                }
                r0 = this;
            }
        }
        return this.e;
    }

    public boolean isCurrentThread() {
        return getDispatcher().isCurrentThread();
    }

    public String toString() {
        return "ComponentDispatcher{name='" + this.c + "'}";
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(Thread thread, Throwable ex) {
        FileLog.e("ComponentDispatcher", "Crashed thread %s(%d) for dispatcher %s with error: %s", thread, Long.valueOf(thread.getId()), this.e, ex);
        synchronized (this) {
            if (this.f < 10) {
                shutdown();
                this.e = null;
                this.d = null;
                getDispatcher();
                Object[] objArr = new Object[4];
                objArr[0] = this.d;
                objArr[1] = Long.valueOf(this.d.getId());
                objArr[2] = this.e;
                objArr[3] = Integer.valueOf(this.f);
                FileLog.v("ComponentDispatcher", "Restored thread %s(%d) for dispatcher %s (restart count: %d)", objArr);
                this.f++;
            }
        }
        this.b.uncaughtException(thread, ex);
    }
}
