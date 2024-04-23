package ru.mail.libverify.api;

import java.util.concurrent.Future;
import ru.mail.libverify.api.VerificationApi;
import ru.mail.libverify.api.l;
import ru.mail.verify.core.requests.FutureWrapper;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/k.class */
final class k implements FutureWrapper.FutureListener<ru.mail.libverify.j.i> {
    final /* synthetic */ ru.mail.libverify.i.i a;
    final /* synthetic */ l.b b;
    final /* synthetic */ String c;
    final /* synthetic */ l d;

    /* JADX INFO: Access modifiers changed from: package-private */
    public k(l lVar, ru.mail.libverify.i.i iVar, l.b bVar, String str) {
        this.d = lVar;
        this.a = iVar;
        this.b = bVar;
        this.c = str;
    }

    @Override // ru.mail.verify.core.requests.FutureWrapper.FutureListener
    public final void onComplete(Future<ru.mail.libverify.j.i> future) {
        if (future.isCancelled()) {
            return;
        }
        VerificationApi.PhoneCheckResult a = this.d.a(this.a, future, this.b);
        this.d.a.remove(this.c);
        l lVar = this.d;
        l.b bVar = this.b;
        lVar.getClass();
        l.a(bVar, a);
    }
}
