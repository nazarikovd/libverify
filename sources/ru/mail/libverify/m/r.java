package ru.mail.libverify.m;

import android.content.Context;
import androidx.annotation.NonNull;
import java.util.concurrent.Executors;
import ru.mail.libverify.api.CommonContext;
import ru.mail.verify.core.api.ApiManager;
import ru.mail.verify.core.utils.components.BusMessageType;
import ru.mail.verify.core.utils.components.MessageBusUtils;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/m/r.class */
public final class r {
    private Context a;
    private ApiManager b;
    private CommonContext c;

    public r(Context context, ApiManager apiManager, CommonContext commonContext) {
        this.a = context;
        this.b = apiManager;
        this.c = commonContext;
    }

    public final void b(@NonNull String str, @NonNull String str2) {
        Executors.newCachedThreadPool().submit(new m(this.a, this.c, this.b, str, str2, str3 -> {
            this.c.getBus().post(MessageBusUtils.createMultipleArgs(BusMessageType.VERIFY_API_SESSION_SIGNATURE_GENERATED, str, str3));
        }));
    }
}
