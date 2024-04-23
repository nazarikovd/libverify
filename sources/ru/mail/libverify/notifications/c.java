package ru.mail.libverify.notifications;

import android.os.Handler;
import android.os.Looper;
import java.lang.ref.WeakReference;
import ru.mail.libverify.api.i;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/notifications/c.class */
final class c implements i.c {
    private static Handler b = new Handler(Looper.getMainLooper());
    private final WeakReference<d> a;

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/notifications/c$a.class */
    class a implements Runnable {
        final /* synthetic */ d a;
        final /* synthetic */ i.b b;

        a(d dVar, i.b bVar) {
            this.a = dVar;
            this.b = bVar;
        }

        @Override // java.lang.Runnable
        public final void run() {
            this.a.a(this.b);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public c(d dVar) {
        this.a = new WeakReference<>(dVar);
    }

    @Override // ru.mail.libverify.api.i.c
    public final void a(i.b bVar) {
        d dVar = this.a.get();
        if (dVar == null) {
            return;
        }
        b.post(new a(dVar, bVar));
    }
}
