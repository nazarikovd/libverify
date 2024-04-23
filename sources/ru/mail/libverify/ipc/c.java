package ru.mail.libverify.ipc;

import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.List;
import ru.mail.libverify.api.i;
import ru.mail.verify.core.api.ApiManager;
import ru.mail.verify.core.utils.DebugUtils;
import ru.mail.verify.core.utils.FileLog;
import ru.mail.verify.core.utils.Utils;
import ru.mail.verify.core.utils.components.BusMessageType;
import ru.mail.verify.core.utils.components.MessageBusUtils;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/ipc/c.class */
final class c extends a {
    /* JADX INFO: Access modifiers changed from: package-private */
    public c(@NonNull ApiManager apiManager, @NonNull i iVar) {
        super(apiManager, iVar);
    }

    private Message a(@NonNull List<String> list) {
        Message obtain = Message.obtain(this, 2);
        obtain.replyTo = a();
        Bundle bundle = new Bundle();
        StringBuilder sb = new StringBuilder();
        for (String str : list) {
            if (sb.length() != 0) {
                sb.append(",");
            }
            sb.append(Utils.stringToSHA256(str));
        }
        bundle.putString("data", sb.toString());
        obtain.setData(bundle);
        return obtain;
    }

    @Override // ru.mail.libverify.ipc.a
    protected final void a(@NonNull Message message) {
        FileLog.v("SmsTextServiceHandler", "handleMessage %s", message.toString());
        int i = message.what;
        if (i == 1) {
            FileLog.v("SmsTextServiceHandler", "processGetSessionsMessage");
            ArrayList a = this.b.a();
            if (a.isEmpty()) {
                FileLog.e("SmsTextServiceHandler", "processGetSessionsMessage skipped");
                return;
            }
            try {
                message.replyTo.send(a(a));
            } catch (Exception e) {
                FileLog.e("SmsTextServiceHandler", "processGetSessionsMessage", e);
            }
        } else if (i != 3) {
            DebugUtils.safeThrow("SmsTextServiceHandler", "handleMessage", new IllegalArgumentException("Can't process message with type " + message.what));
        } else {
            try {
                String string = message.getData().getString("data");
                String string2 = message.getData().getString("receiver");
                if (TextUtils.isEmpty(string) || TextUtils.isEmpty(string2)) {
                    FileLog.e("SmsTextServiceHandler", "processPostSmsTextMessage smsText and receiver shouldn't be empty");
                } else {
                    FileLog.v("SmsTextServiceHandler", "processPostSmsTextMessage sms %s for receiver %s", string, string2);
                    this.a.post(MessageBusUtils.createMultipleArgs(BusMessageType.SERVICE_IPC_SMS_MESSAGE_RECEIVED, string2, string));
                    Messenger messenger = message.replyTo;
                    Message obtain = Message.obtain(this, 4);
                    obtain.replyTo = a();
                    obtain.setData(new Bundle());
                    messenger.send(obtain);
                }
            } catch (Exception e2) {
                FileLog.e("SmsTextServiceHandler", "processPostSmsTextMessage", e2);
            }
        }
    }
}
