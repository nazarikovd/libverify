package ru.mail.verify.core.utils.components;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.annotation.NonNull;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/utils/components/CustomHandler.class */
public class CustomHandler extends Handler {
    private final MessageHandler messageHandler;

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/utils/components/CustomHandler$a.class */
    class a implements Runnable {
        final /* synthetic */ Message a;

        a(Message message) {
            this.a = message;
        }

        @Override // java.lang.Runnable
        public final void run() {
            CustomHandler.this.messageHandler.handleMessage(this.a);
            this.a.recycle();
        }
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/utils/components/CustomHandler$b.class */
    class b implements Runnable {
        final /* synthetic */ Message a;

        b(Message message) {
            this.a = message;
        }

        @Override // java.lang.Runnable
        public final void run() {
            CustomHandler.this.messageHandler.handleMessage(this.a);
            this.a.recycle();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/utils/components/CustomHandler$c.class */
    public static final class c implements Runnable {
        private final Runnable a;
        private boolean b;

        c(@NonNull Runnable runnable) {
            this.a = runnable;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.lang.Runnable
        public final void run() {
            try {
                this.a.run();
                synchronized (this) {
                    this.b = true;
                    notifyAll();
                }
            } catch (Throwable th) {
                synchronized (th) {
                    th.b = true;
                    th.notifyAll();
                    throw this;
                }
            }
        }
    }

    public CustomHandler(@NonNull Looper looper, @NonNull MessageHandler messageHandler) {
        super(looper);
        this.messageHandler = messageHandler;
    }

    @Override // android.os.Handler
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        this.messageHandler.handleMessage(msg);
    }

    public void postAndWait(@NonNull Message message) {
        postAndWait(new a(message));
    }

    public void post(@NonNull Message message) {
        post(new b(message));
    }

    public boolean isCurrentThread() {
        return getLooper().getThread() == Thread.currentThread();
    }

    public void postAndWait(@NonNull Runnable runnable) {
        c cVar = new c(runnable);
        if (post(cVar)) {
            synchronized (cVar) {
                while (!cVar.b) {
                    try {
                        cVar.wait();
                    } catch (InterruptedException unused) {
                    }
                }
            }
        }
    }
}
