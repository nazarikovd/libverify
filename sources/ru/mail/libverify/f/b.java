package ru.mail.libverify.f;

import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import androidx.annotation.NonNull;
import ru.mail.libverify.api.i;
import ru.mail.libverify.f.f;
import ru.mail.libverify.ipc.IpcNotificationService;
import ru.mail.verify.core.utils.FileLog;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/f/b.class */
final class b extends ru.mail.libverify.f.a {
    private final String f;
    private final long g;

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/f/b$a.class */
    static class a implements c {
        private final i a;
        private final long b;
        private final String c;

        /* JADX INFO: Access modifiers changed from: package-private */
        public a(@NonNull i iVar, @NonNull String str, long j) {
            this.a = iVar;
            this.c = str;
            this.b = j;
        }

        @Override // ru.mail.libverify.f.c
        public final ru.mail.libverify.f.a b() {
            return new b(this.a, this.c, this.b);
        }

        @Override // ru.mail.libverify.f.c
        public final Class a() {
            return IpcNotificationService.class;
        }
    }

    private b(@NonNull i iVar, @NonNull String str, long j) {
        super(iVar);
        this.f = str;
        this.g = j;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // ru.mail.libverify.f.a
    public final void d() {
        try {
            Messenger messenger = this.c;
            String str = this.f;
            Message obtain = Message.obtain(this, 5);
            obtain.replyTo = b();
            Bundle bundle = new Bundle();
            bundle.putString("data", str);
            bundle.putLong("timestamp", this.g);
            obtain.setData(bundle);
            messenger.send(obtain);
            ((f.d.a) this.b).a(true);
        } catch (Exception e) {
            FileLog.e("CancelNotification", "postDataToService", e);
        }
    }
}
