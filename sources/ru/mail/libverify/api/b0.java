package ru.mail.libverify.api;

import ru.mail.libverify.api.VerificationApi;
import ru.mail.libverify.k.l;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/b0.class */
public final class b0 implements l.a {
    final /* synthetic */ x a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public b0(x xVar) {
        this.a = xVar;
    }

    @Override // ru.mail.libverify.k.l.a
    public final void b(ru.mail.libverify.k.b bVar) {
        this.a.a(g.a(ru.mail.libverify.v.a.a().provideStartConfig().isSimpleCodeParser(), bVar.c(), this.a.j()), bVar.c(), VerificationApi.VerificationSource.SMS);
    }
}
