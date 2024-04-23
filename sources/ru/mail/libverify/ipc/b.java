package ru.mail.libverify.ipc;

import android.os.Message;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import ru.mail.libverify.api.i;
import ru.mail.verify.core.api.ApiManager;
import ru.mail.verify.core.utils.FileLog;
import ru.mail.verify.core.utils.components.BusMessageType;
import ru.mail.verify.core.utils.components.MessageBusUtils;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/ipc/b.class */
final class b extends a {
    /* JADX INFO: Access modifiers changed from: package-private */
    public b(@NonNull ApiManager apiManager, @NonNull i iVar) {
        super(apiManager, iVar);
    }

    @Override // ru.mail.libverify.ipc.a
    protected final void a(@NonNull Message message) {
        FileLog.v("IpcNotifyHandler", "handleMessage %s", message.toString());
        switch (message.what) {
            case 5:
                try {
                    String string = message.getData().getString("data");
                    long j = message.getData().getLong("timestamp");
                    if (TextUtils.isEmpty(string)) {
                        FileLog.e("IpcNotifyHandler", "serverNotificationId can't be empty");
                    } else {
                        FileLog.v("IpcNotifyHandler", "process cancel message from %s", string);
                        ApiManager apiManager = this.a;
                        BusMessageType busMessageType = BusMessageType.SERVICE_IPC_CANCEL_NOTIFICATION_RECEIVED;
                        Object[] objArr = new Object[2];
                        objArr[0] = string;
                        objArr[1] = Long.valueOf(j);
                        apiManager.post(MessageBusUtils.createMultipleArgs(busMessageType, objArr));
                    }
                    return;
                } catch (Exception e) {
                    FileLog.e("IpcNotifyHandler", "process cancel message failed", e);
                    return;
                }
            case 6:
                try {
                    String string2 = message.getData().getString("data");
                    if (TextUtils.isEmpty(string2)) {
                        FileLog.e("IpcNotifyHandler", "fetcher package name can't be empty");
                    } else {
                        FileLog.v("IpcNotifyHandler", "fetcher started from %s", string2);
                        this.a.post(MessageBusUtils.createOneArg(BusMessageType.SERVICE_IPC_FETCHER_STARTED_RECEIVED, string2));
                    }
                    return;
                } catch (Exception e2) {
                    FileLog.e("IpcNotifyHandler", "process fetcher started message failed", e2);
                    return;
                }
            case 7:
                try {
                    String string3 = message.getData().getString("data");
                    if (TextUtils.isEmpty(string3)) {
                        FileLog.e("IpcNotifyHandler", "fetcher package name can't be empty");
                    } else {
                        FileLog.v("IpcNotifyHandler", "fetcher stopped from %s", string3);
                        this.a.post(MessageBusUtils.createOneArg(BusMessageType.SERVICE_IPC_FETCHER_STOPPED_RECEIVED, string3));
                    }
                    return;
                } catch (Exception e3) {
                    FileLog.e("IpcNotifyHandler", "process fetcher stopped message failed", e3);
                    return;
                }
            default:
                FileLog.e("IpcNotifyHandler", "Can't process message with type " + message.what);
                return;
        }
    }
}
