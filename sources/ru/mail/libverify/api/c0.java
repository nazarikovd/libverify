package ru.mail.libverify.api;

import android.text.TextUtils;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import ru.mail.libverify.api.VerificationApi;
import ru.mail.libverify.api.x;
import ru.mail.libverify.j.c;
import ru.mail.verify.core.utils.FileLog;
import ru.mail.verify.core.utils.components.BusMessageType;
import ru.mail.verify.core.utils.components.MessageBusUtils;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/c0.class */
final class c0 {
    final /* synthetic */ Future a;
    final /* synthetic */ x b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public c0(x xVar, Future future) {
        this.b = xVar;
        this.a = future;
    }

    public final VerificationApi.VerificationStateDescriptor a() throws ExecutionException, InterruptedException {
        VerificationApi.VerificationStateDescriptor a;
        x xVar = this.b;
        ru.mail.libverify.j.a aVar = (ru.mail.libverify.j.a) this.a.get();
        FileLog.v("VerificationSession", "session with id = %s received AttemptApiResponse response = %s", xVar.g.id, aVar.toString());
        xVar.d.post(MessageBusUtils.createOneArg(BusMessageType.VERIFICATION_SESSION_FETCHER_INFO_RECEIVED, aVar.f()));
        if (aVar.d() != c.b.OK) {
            o oVar = xVar.g;
            oVar.smsCode = null;
            oVar.rawSmsTexts.clear();
        } else {
            if (xVar.g.e() == null) {
                xVar.g.a(new ru.mail.libverify.j.n());
            }
            xVar.g.e().a(aVar.g());
            xVar.g.e().a(aVar.e());
            xVar.g.e().a(aVar.h());
        }
        int i = x.a.b[aVar.d().ordinal()];
        if (i == 1 || i == 2) {
            if (!TextUtils.isEmpty(aVar.g())) {
                VerificationApi.VerificationState verificationState = VerificationApi.VerificationState.SUCCEEDED;
                o oVar2 = xVar.g;
                a = xVar.a(verificationState, oVar2.smsCodeSource, VerificationApi.FailReason.OK, oVar2.e());
            }
            a = xVar.d();
        } else if (i != 5) {
            xVar.d.post(MessageBusUtils.createOneArg(BusMessageType.VERIFY_API_HANDLE_REQUEST_FAILURE, aVar));
            a = xVar.a(aVar);
        } else {
            int i2 = x.a.c[aVar.b().ordinal()];
            if (i2 == 1 || i2 == 2) {
                o oVar3 = xVar.g;
                VerificationApi.VerificationSource verificationSource = oVar3.smsCodeSource;
                a = verificationSource == VerificationApi.VerificationSource.USER_INPUT ? xVar.a(VerificationApi.VerificationState.WAITING_FOR_SMS_CODE, verificationSource, VerificationApi.FailReason.INCORRECT_SMS_CODE.a(aVar.a()), xVar.g.e()) : xVar.a(VerificationApi.VerificationState.WAITING_FOR_SMS_CODE, verificationSource, VerificationApi.FailReason.OK, oVar3.e());
            }
            a = xVar.d();
        }
        return a;
    }
}
