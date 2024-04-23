package ru.mail.libverify.api;

import ru.mail.libverify.f.f;
import ru.mail.libverify.gcm.ServerNotificationMessage;
import ru.mail.verify.core.utils.components.BusMessageType;
import ru.mail.verify.core.utils.components.MessageBusUtils;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/r.class */
public final class r implements f.b {
    final /* synthetic */ ServerNotificationMessage a;
    final /* synthetic */ boolean b;
    final /* synthetic */ boolean c;
    final /* synthetic */ q d;

    /* JADX INFO: Access modifiers changed from: package-private */
    public r(q qVar, ServerNotificationMessage serverNotificationMessage, boolean z, boolean z2) {
        this.d = qVar;
        this.a = serverNotificationMessage;
        this.b = z;
        this.c = z2;
    }

    @Override // ru.mail.libverify.f.f.b
    public final void a(f.c cVar) {
        this.d.p.post(MessageBusUtils.createMultipleArgs(BusMessageType.VERIFY_API_IPC_CONNECT_RESULT_RECEIVED, this.a, Boolean.valueOf(this.b), Boolean.valueOf(this.c), cVar));
    }
}
