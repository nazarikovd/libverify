package ru.mail.libverify.api;

import ru.mail.libverify.api.VerificationApi;
import ru.mail.verify.core.utils.FileLog;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/v.class */
final class v implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ VerificationApi.CancelReason b;
    final /* synthetic */ q c;

    /* JADX INFO: Access modifiers changed from: package-private */
    public v(q qVar, String str, VerificationApi.CancelReason cancelReason) {
        this.c = qVar;
        this.a = str;
        this.b = cancelReason;
    }

    @Override // java.lang.Runnable
    public final void run() {
        FileLog.v("VerificationApi", "cancel verification for session %s by reason %s", this.a, this.b);
        x d = this.c.b.d(this.a);
        this.c.J.a(this.a);
        if (d != null) {
            this.c.f.a(d, this.b);
            d.a();
        }
    }
}
