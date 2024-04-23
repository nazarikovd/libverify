package ru.mail.libverify.api;

import java.util.concurrent.Future;
import ru.mail.libverify.api.VerificationApi;
import ru.mail.verify.core.requests.FutureWrapper;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/d0.class */
public final class d0 implements FutureWrapper.FutureListener<ru.mail.libverify.j.n> {
    final /* synthetic */ ru.mail.libverify.i.p a;
    final /* synthetic */ VerificationApi.IvrStateListener b;
    final /* synthetic */ x c;

    /* JADX INFO: Access modifiers changed from: package-private */
    public d0(x xVar, ru.mail.libverify.i.p pVar, VerificationApi.IvrStateListener ivrStateListener) {
        this.c = xVar;
        this.a = pVar;
        this.b = ivrStateListener;
    }

    @Override // ru.mail.verify.core.requests.FutureWrapper.FutureListener
    public final void onComplete(Future<ru.mail.libverify.j.n> future) {
        if (future.isCancelled()) {
            return;
        }
        x xVar = this.c;
        xVar.n = null;
        VerificationApi.VerificationStateDescriptor a = xVar.a(this.a, xVar.h, future);
        VerificationApi.IvrStateListener ivrStateListener = this.b;
        if (ivrStateListener != null) {
            ivrStateListener.onRequestExecuted(a.getReason());
        }
        if (a.getState() == VerificationApi.VerificationState.SUCCEEDED) {
            this.c.a(a);
        }
    }
}
