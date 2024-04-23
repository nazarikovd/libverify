package ru.mail.libverify.f;

import java.util.Timer;
import java.util.TimerTask;
import ru.mail.libverify.f.f;
import ru.mail.verify.core.utils.FileLog;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/f/e.class */
final class e extends TimerTask {
    final /* synthetic */ f a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public e(f fVar) {
        this.a = fVar;
    }

    @Override // java.util.TimerTask, java.lang.Runnable
    public final void run() {
        synchronized (this.a) {
            FileLog.v("IpcMessageClient", "startTimer connection timeout expired");
            f fVar = this.a;
            Object[] objArr = new Object[1];
            objArr[0] = Integer.valueOf(fVar.d.size());
            FileLog.d("IpcMessageClient", "unbind %d connections", objArr);
            for (f.d dVar : fVar.d.keySet()) {
                dVar.b();
            }
            this.a.a(true);
            f fVar2 = this.a;
            Timer timer = fVar2.e;
            if (timer != null) {
                timer.cancel();
                fVar2.e = null;
            }
        }
    }
}
