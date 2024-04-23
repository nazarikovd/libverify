package ru.mail.libverify.api;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import ru.mail.libverify.api.VerificationApi;
import ru.mail.libverify.platform.sms.SmsRetrieverManager;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/z.class */
public final class z implements SmsRetrieverManager.SmsRetrieverSmsCallback {
    final /* synthetic */ x a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public z(x xVar) {
        this.a = xVar;
    }

    public final void onIncomingSms(@NonNull String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        boolean isSimpleCodeParser = ru.mail.libverify.v.a.a().provideStartConfig().isSimpleCodeParser();
        x xVar = this.a;
        xVar.a(g.a(isSimpleCodeParser, str, xVar.j()), str, VerificationApi.VerificationSource.SMS_RETRIEVER);
    }
}
