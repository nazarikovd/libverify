package ru.mail.verify.core.requests;

import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import ru.mail.verify.core.requests.RequestBase;
import ru.mail.verify.core.utils.FileLog;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/requests/FutureWrapper.class */
public class FutureWrapper<TW> {
    private final ExecutorService a;
    private final Handler b;
    private final Callable<TW> c;
    private final c d;
    private final FutureListener<TW> e;
    private volatile Future<TW> f;

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/requests/FutureWrapper$FutureListener.class */
    public interface FutureListener<T> {
        void onComplete(Future<T> future);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/requests/FutureWrapper$a.class */
    public class a implements Callable<TW> {

        /* renamed from: ru.mail.verify.core.requests.FutureWrapper$a$a  reason: collision with other inner class name */
        /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/requests/FutureWrapper$a$a.class */
        class RunnableC0027a implements Runnable {
            RunnableC0027a() {
            }

            @Override // java.lang.Runnable
            public final void run() {
                FutureWrapper futureWrapper = FutureWrapper.this;
                futureWrapper.e.onComplete(futureWrapper.f);
            }
        }

        a() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.concurrent.Callable
        public final TW call() throws Exception {
            Handler handler;
            Handler handler2;
            try {
                try {
                    TW call = FutureWrapper.this.c.call();
                    FutureWrapper futureWrapper = FutureWrapper.this;
                    if (futureWrapper.e != null && (handler2 = futureWrapper.b) != null) {
                        handler2.post(new RunnableC0027a());
                    }
                    return call;
                } catch (Exception unused) {
                    throw this;
                }
            } catch (Throwable th) {
                FutureWrapper futureWrapper2 = FutureWrapper.this;
                if (futureWrapper2.e != null && (handler = futureWrapper2.b) != null) {
                    handler.post(new RunnableC0027a());
                }
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/requests/FutureWrapper$b.class */
    public class b implements Future<TW> {
        final /* synthetic */ Future a;

        b(Future future) {
            this.a = future;
        }

        @Override // java.util.concurrent.Future
        public final boolean cancel(boolean z) {
            boolean cancel = this.a.cancel(true);
            c cVar = FutureWrapper.this.d;
            if (cVar != null) {
                RequestBase.c cVar2 = (RequestBase.c) cVar;
                cVar2.getClass();
                try {
                    FileLog.v("ApiRequest", "try to disconnect");
                    cVar2.a.disconnect();
                    FileLog.v("ApiRequest", "disconnected");
                } catch (Exception e) {
                    FileLog.v("ApiRequest", "failed to disconnect", e);
                }
            }
            return cancel;
        }

        @Override // java.util.concurrent.Future
        public final boolean isCancelled() {
            return this.a.isCancelled();
        }

        @Override // java.util.concurrent.Future
        public final boolean isDone() {
            return this.a.isDone();
        }

        @Override // java.util.concurrent.Future
        public final TW get() throws InterruptedException, ExecutionException {
            return (TW) this.a.get();
        }

        @Override // java.util.concurrent.Future
        public final TW get(long j, @NonNull TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
            return (TW) this.a.get(j, timeUnit);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/requests/FutureWrapper$c.class */
    public interface c {
    }

    public FutureWrapper(@NonNull ExecutorService executorService, @Nullable Handler handler, @NonNull Callable<TW> task, @Nullable c cancelListener, @Nullable FutureListener<TW> futureListener) {
        this.b = handler;
        this.a = executorService;
        this.c = task;
        this.d = cancelListener;
        this.e = futureListener;
    }

    public Future<TW> execute() {
        this.f = new b(this.a.submit(new a()));
        return this.f;
    }
}
