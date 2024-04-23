package ru.mail.libverify.api;

import java.net.URI;
import java.util.concurrent.Future;
import ru.mail.libverify.gcm.ServerInfo;
import ru.mail.verify.core.requests.FutureWrapper;
import ru.mail.verify.core.utils.FileLog;
import ru.mail.verify.core.utils.components.BusMessageType;
import ru.mail.verify.core.utils.components.MessageBusUtils;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/y.class */
public final class y implements FutureWrapper.FutureListener<ru.mail.libverify.j.h> {
    final /* synthetic */ ServerInfo.MobileId a;
    final /* synthetic */ x b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public y(x xVar, ServerInfo.MobileId mobileId) {
        this.b = xVar;
        this.a = mobileId;
    }

    @Override // ru.mail.verify.core.requests.FutureWrapper.FutureListener
    public final void onComplete(Future<ru.mail.libverify.j.h> future) {
        int i;
        String str;
        this.b.m = null;
        try {
            ru.mail.libverify.j.h hVar = future.get();
            i = hVar.a();
            str = hVar.b();
        } catch (Exception e) {
            e.printStackTrace();
            FileLog.e("VerificationSession", e, "ModileId response error", new Object[0]);
            i = -1;
            str = null;
        }
        if (i >= 300 && i < 400) {
            if (str == null) {
                FileLog.e("VerificationSession", "Failed to parse MobileID headers");
                x xVar = this.b;
                ru.mail.libverify.l.b bVar = xVar.s;
                if (bVar != null) {
                    bVar.c(xVar);
                }
                i = (-1000) - i;
            } else if (this.a.getMaxRedirects() > 0) {
                x xVar2 = this.b;
                String url = this.a.getUrl();
                xVar2.getClass();
                String uri = URI.create(url).resolve(str).toString();
                FileLog.d("VerificationSession", "MobileID request redirected to %s", uri);
                x xVar3 = this.b;
                ru.mail.libverify.l.b bVar2 = xVar3.s;
                if (bVar2 != null) {
                    bVar2.a(xVar3, uri, this.a.getMaxRedirects());
                }
                this.b.g.mobileIdRoutes.add(new ru.mail.libverify.c.b(this.a.getUrl(), i));
                this.a.setUrl(uri);
                ServerInfo.MobileId mobileId = this.a;
                mobileId.setMaxRedirects(mobileId.getMaxRedirects() - 1);
                x xVar4 = this.b;
                xVar4.t = false;
                xVar4.a(this.a, Boolean.TRUE);
                return;
            } else {
                FileLog.e("VerificationSession", "MobileID redirects limit is reached");
                x xVar5 = this.b;
                ru.mail.libverify.l.b bVar3 = xVar5.s;
                if (bVar3 != null) {
                    bVar3.d(xVar5);
                }
                i = (-2000) - i;
            }
        }
        this.b.g.mobileIdRoutes.add(new ru.mail.libverify.c.b(this.a.getUrl(), i));
        x xVar6 = this.b;
        xVar6.d.post(MessageBusUtils.createMultipleArgs(BusMessageType.VERIFICATION_SESSION_MOBILEID_RESULTS_RECEIVED, xVar6.g.id, Integer.valueOf(i)));
    }
}
