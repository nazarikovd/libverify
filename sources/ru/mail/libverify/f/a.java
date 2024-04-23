package ru.mail.libverify.f;

import android.os.Handler;
import android.os.Messenger;
import androidx.annotation.NonNull;
import ru.mail.libverify.api.i;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/f/a.class */
abstract class a extends Handler {
    protected final i a;
    protected InterfaceC0007a b;
    protected Messenger c;
    private volatile boolean d = false;
    private Messenger e;

    /* renamed from: ru.mail.libverify.f.a$a  reason: collision with other inner class name */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/f/a$a.class */
    interface InterfaceC0007a {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(@NonNull i iVar) {
        this.a = iVar;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final Messenger b() {
        if (this.e == null) {
            this.e = new Messenger(this);
        }
        return this.e;
    }

    public final void a() {
        this.d = true;
        removeCallbacksAndMessages(null);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean c() {
        return this.d;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract void d();
}
