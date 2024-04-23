package ru.mail.libverify.api;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/t.class */
final class t implements Runnable {
    final /* synthetic */ x a;
    final /* synthetic */ q b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public t(q qVar, x xVar) {
        this.b = qVar;
        this.a = xVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.a.D();
        this.b.f.j(this.a);
    }
}
