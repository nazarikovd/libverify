package ru.mail.libverify.storage;

import android.content.Context;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/storage/e.class */
final class e implements Runnable {
    final /* synthetic */ String[] a;
    final /* synthetic */ Context b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public e(Context context, String[] strArr) {
        this.a = strArr;
        this.b = context;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.a[0] = f.d(this.b);
    }
}
