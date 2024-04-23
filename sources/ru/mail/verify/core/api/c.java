package ru.mail.verify.core.api;

import androidx.annotation.NonNull;
import java.lang.Thread;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import ru.mail.verify.core.utils.FileLog;
import ru.mail.verify.core.utils.components.CustomHandler;
import ru.mail.verify.core.utils.components.MessageHandler;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/api/c.class */
final class c {
    private static final TimeUnit e = TimeUnit.SECONDS;
    private ThreadPoolExecutor a;
    private final ComponentDispatcher b;
    private final Thread.UncaughtExceptionHandler c;
    private final RejectedExecutionHandler d;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/api/c$a.class */
    public class a implements ThreadFactory {
        private final AtomicInteger a = new AtomicInteger(0);

        a() {
        }

        @Override // java.util.concurrent.ThreadFactory
        public final Thread newThread(@NonNull Runnable runnable) {
            Thread thread = new Thread(runnable);
            thread.setName("notify_core_background_worker" + this.a.incrementAndGet());
            thread.setPriority(8);
            thread.setUncaughtExceptionHandler(c.this.c);
            return thread;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public c(@NonNull Thread.UncaughtExceptionHandler uncaughtExceptionHandler, @NonNull RejectedExecutionHandler rejectedExecutionHandler, @NonNull MessageHandler messageHandler) {
        this.c = uncaughtExceptionHandler;
        this.d = rejectedExecutionHandler;
        this.b = new ComponentDispatcher("notify_core_worker", messageHandler, uncaughtExceptionHandler);
    }

    private ThreadPoolExecutor c() {
        if (this.a == null) {
            ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 15, 120L, e, new LinkedBlockingQueue());
            this.a = threadPoolExecutor;
            threadPoolExecutor.allowCoreThreadTimeOut(true);
            this.a.setRejectedExecutionHandler(this.d);
            this.a.setThreadFactory(new a());
        }
        return this.a;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final CustomHandler b() {
        return this.b.getDispatcher();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final ThreadPoolExecutor a() {
        return c();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void e() {
        this.b.stop();
        ThreadPoolExecutor threadPoolExecutor = this.a;
        if (threadPoolExecutor != null) {
            threadPoolExecutor.shutdownNow();
            try {
                if (!this.a.awaitTermination(5000L, TimeUnit.MILLISECONDS)) {
                    FileLog.e("ApiThread", "wait for shutdown failure");
                }
            } catch (InterruptedException unused) {
                FileLog.e("ApiThread", "shutdown failure");
            }
            this.a = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void d() {
        this.b.shutdown();
        ThreadPoolExecutor threadPoolExecutor = this.a;
        if (threadPoolExecutor != null) {
            threadPoolExecutor.shutdownNow();
            try {
                if (!this.a.awaitTermination(5000L, TimeUnit.MILLISECONDS)) {
                    FileLog.e("ApiThread", "wait for shutdown failure");
                }
            } catch (InterruptedException unused) {
                FileLog.e("ApiThread", "shutdown failure");
            }
            this.a = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void a(@NonNull Runnable runnable) {
        c().prestartAllCoreThreads();
        c().submit(runnable);
    }
}
