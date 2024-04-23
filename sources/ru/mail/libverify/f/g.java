package ru.mail.libverify.f;

import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import ru.mail.libverify.api.i;
import ru.mail.libverify.f.f;
import ru.mail.libverify.ipc.IpcMessageService;
import ru.mail.verify.core.utils.DebugUtils;
import ru.mail.verify.core.utils.FileLog;
import ru.mail.verify.core.utils.Utils;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/f/g.class */
final class g extends ru.mail.libverify.f.a {
    private final String f;
    private final String g;
    private String h;

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/f/g$a.class */
    static class a implements c {
        private final i a;
        private final String b;
        private final String c;

        /* JADX INFO: Access modifiers changed from: package-private */
        public a(@NonNull i iVar, @NonNull String str, @NonNull String str2) {
            this.a = iVar;
            this.b = str;
            this.c = str2;
        }

        @Override // ru.mail.libverify.f.c
        public final ru.mail.libverify.f.a b() {
            return new g(this.a, this.b, this.c);
        }

        @Override // ru.mail.libverify.f.c
        public final Class a() {
            return IpcMessageService.class;
        }
    }

    private g(@NonNull i iVar, @NonNull String str, @NonNull String str2) {
        super(iVar);
        this.f = str;
        this.g = str2;
    }

    private boolean a(@NonNull Message message) {
        String string = message.getData().getString("data");
        if (TextUtils.isEmpty(string)) {
            FileLog.e("SmsTextClientHandler", "validateGetSessionsAckMessage can't parse empty ids");
            return false;
        }
        String[] split = string.split(",");
        for (int i = 0; i < split.length && i < 5; i++) {
            if (this.h == null) {
                this.h = Utils.stringToSHA256(this.f);
            }
            if (TextUtils.equals(this.h, split[i])) {
                FileLog.v("SmsTextClientHandler", "validateGetSessionsAckMessage found requested session id %s", this.f);
                return true;
            }
        }
        FileLog.e("SmsTextClientHandler", "validateGetSessionsAckMessage session id %s not found", this.f);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // ru.mail.libverify.f.a
    public final void d() {
        try {
            Messenger messenger = this.c;
            Message obtain = Message.obtain(this, 1);
            obtain.replyTo = b();
            obtain.setData(new Bundle());
            messenger.send(obtain);
        } catch (Exception e) {
            FileLog.e("SmsTextClientHandler", "postDataToService", e);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v14 */
    /* JADX WARN: Type inference failed for: r0v17 */
    /* JADX WARN: Type inference failed for: r0v25 */
    /* JADX WARN: Type inference failed for: r0v6 */
    @Override // android.os.Handler
    public final void handleMessage(Message message) {
        ?? r0;
        if (c()) {
            return;
        }
        super.handleMessage(message);
        if (message == null) {
            return;
        }
        FileLog.v("SmsTextClientHandler", "handleMessage %s", message.toString());
        int i = message.what;
        if (i != 2) {
            if (i == 4) {
                ((f.d.a) this.b).a(true);
                return;
            }
            DebugUtils.safeThrow("SmsTextClientHandler", "handleMessage", new IllegalArgumentException("Can't process message with type " + message.what));
            ((f.d.a) this.b).a(false);
            return;
        }
        g gVar = this;
        FileLog.v("SmsTextClientHandler", "processGetSessionsAckMessage");
        try {
            if (gVar.a(message)) {
                Messenger messenger = this.c;
                Message obtain = Message.obtain(this, 3);
                obtain.replyTo = b();
                Bundle bundle = new Bundle();
                bundle.putString("data", this.g);
                if (this.h == null) {
                    this.h = Utils.stringToSHA256(this.f);
                }
                bundle.putString("receiver", this.h);
                obtain.setData(bundle);
                messenger.send(obtain);
                r0 = 1;
            } else {
                FileLog.e("SmsTextClientHandler", "processGetSessionsAckMessage - received message is not valid");
                r0 = 0;
            }
        } catch (Exception e) {
            FileLog.e("SmsTextClientHandler", "processGetSessionsAckMessage", e);
            r0 = gVar;
        }
        if (r0 == 0) {
            ((f.d.a) this.b).a(false);
        }
    }
}
