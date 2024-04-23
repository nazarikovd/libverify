package ru.mail.libverify.api;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/u.class */
final class u implements Runnable {
    final /* synthetic */ q a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public u(q qVar) {
        this.a = qVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.a.a.getSimCardData();
    }
}
