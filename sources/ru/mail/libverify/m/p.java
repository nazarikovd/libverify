package ru.mail.libverify.m;

import android.content.Context;
import androidx.annotation.NonNull;
import java.util.concurrent.Executors;
import ru.mail.libverify.api.CommonContext;
import ru.mail.verify.core.api.ApiManager;
import ru.mail.verify.core.utils.Utils;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/m/p.class */
public final class p {
    private Context a;
    private ApiManager b;
    private CommonContext c;

    public p(Context context, ApiManager apiManager, CommonContext commonContext) {
        this.a = context;
        this.b = apiManager;
        this.c = commonContext;
    }

    public final void a(@NonNull String str, String str2, q qVar) {
        Executors.newCachedThreadPool().submit(new m(this.a, this.c, this.b, str, Utils.getBase64String(str2), qVar));
    }
}
