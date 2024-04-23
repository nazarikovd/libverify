package ru.mail.libverify.api;

import ru.mail.libverify.f.f;
import ru.mail.verify.core.utils.FileLog;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/s.class */
final class s implements f.b {
    final /* synthetic */ boolean a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public s(boolean z) {
        this.a = z;
    }

    @Override // ru.mail.libverify.f.f.b
    public final void a(f.c cVar) {
        FileLog.v("VerificationApi", "post fetcher started result %s with started %s", cVar, Boolean.valueOf(this.a));
    }
}
