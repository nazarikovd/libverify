package ru.mail.libverify.m;

import android.content.Context;
import ru.mail.libverify.api.CommonContext;
import ru.mail.verify.core.api.ApiManager;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/m/m.class */
public final class m implements Runnable {
    private final Context a;
    private final CommonContext b;
    private final ApiManager c;
    private final String d;
    private final String e;
    private final q f;
    private final ru.mail.libverify.storage.m g = new ru.mail.libverify.storage.m();

    /* JADX INFO: Access modifiers changed from: package-private */
    public m(Context context, CommonContext commonContext, ApiManager apiManager, String str, String str2, q qVar) {
        this.a = context;
        this.b = commonContext;
        this.c = apiManager;
        this.d = str;
        this.e = str2;
        this.f = qVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        String str;
        try {
            str = this.g.a(this.a, this.b, this.c, this.d, this.e);
        } catch (Exception unused) {
            str = null;
        }
        if (str == null) {
            str = "";
        }
        this.f.a(str);
    }
}
