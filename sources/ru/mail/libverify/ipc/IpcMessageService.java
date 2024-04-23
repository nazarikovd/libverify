package ru.mail.libverify.ipc;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import ru.mail.libverify.api.VerificationFactory;
import ru.mail.libverify.api.i;
import ru.mail.verify.core.utils.FileLog;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/ipc/IpcMessageService.class */
public class IpcMessageService extends Service {
    private c a;

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        FileLog.v("IpcMessageService", "onBind from initiator %s", intent.getStringExtra("bind_initiator"));
        if (!VerificationFactory.hasInstallation(this)) {
            Log.e("IpcMessageService", "libverify isn't initialized for this application");
            return null;
        }
        if (this.a == null) {
            this.a = new c(ru.mail.libverify.v.a.c(this).get(), (i) VerificationFactory.get(this));
        }
        return this.a.a().getBinder();
    }
}
