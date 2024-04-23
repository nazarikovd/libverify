package ru.mail.verify.core.storage;

import android.content.Context;
import androidx.annotation.NonNull;
import ru.mail.verify.core.api.ApiManager;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/storage/AsyncSettings.class */
public abstract class AsyncSettings extends SecureSettings {
    private final ApiManager f;
    private final int g;
    private final Runnable h;

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/storage/AsyncSettings$a.class */
    class a implements Runnable {
        a() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            AsyncSettings.super.commit();
        }
    }

    public AsyncSettings(@NonNull ApiManager manager, @NonNull Context context, @NonNull String fileName, int commitDelay) {
        super(context, fileName);
        this.h = new a();
        this.f = manager;
        this.g = commitDelay;
    }

    @Override // ru.mail.verify.core.storage.SecureSettings
    public synchronized void commit() {
        this.f.getDispatcher().removeCallbacks(this.h);
        this.f.getDispatcher().postDelayed(this.h, this.g);
    }

    public void commitSync() {
        super.commit();
    }
}
