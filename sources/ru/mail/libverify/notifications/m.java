package ru.mail.libverify.notifications;

import android.content.Context;
import android.content.DialogInterface;
import ru.mail.libverify.api.VerificationFactory;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/notifications/m.class */
public final class m implements DialogInterface.OnClickListener {
    final /* synthetic */ Context a;
    final /* synthetic */ String b;
    final /* synthetic */ long c;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Multi-variable type inference failed */
    public m(SmsMessagesActivity smsMessagesActivity, String str, long j) {
        this.a = smsMessagesActivity;
        this.b = str;
        this.c = j;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        VerificationFactory.getInstance(this.a).removeSms(this.b, null, this.c);
    }
}
