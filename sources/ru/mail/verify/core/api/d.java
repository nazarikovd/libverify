package ru.mail.verify.core.api;

import android.content.Context;
import android.os.Handler;
import androidx.annotation.NonNull;
import ru.mail.verify.core.storage.SecureSettings;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/api/d.class */
public final class d extends SecureSettings {
    private final Handler f;
    private final Runnable g;

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/api/d$a.class */
    class a implements Runnable {
        a() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            d.super.commit();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public d(@NonNull Handler handler, @NonNull Context context) {
        super(context, "NOTIFY_DEBUG_SETTINGS");
        this.g = new a();
        this.f = handler;
    }

    @Override // ru.mail.verify.core.storage.SecureSettings
    public final synchronized void commit() {
        this.f.removeCallbacks(this.g);
        this.f.postDelayed(this.g, 1000L);
    }

    public final void commitSync() {
        super.commit();
    }
}
