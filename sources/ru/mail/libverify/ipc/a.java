package ru.mail.libverify.ipc;

import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import androidx.annotation.NonNull;
import ru.mail.libverify.api.i;
import ru.mail.verify.core.api.ApiManager;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/ipc/a.class */
abstract class a extends Handler {
    final ApiManager a;
    final i b;
    private Messenger c;

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(@NonNull ApiManager apiManager, @NonNull i iVar) {
        this.a = apiManager;
        this.b = iVar;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final Messenger a() {
        if (this.c == null) {
            this.c = new Messenger(this);
        }
        return this.c;
    }

    @Override // android.os.Handler
    public final void handleMessage(Message message) {
        super.handleMessage(message);
        if (message == null) {
            return;
        }
        a(message);
    }

    protected abstract void a(@NonNull Message message);
}
