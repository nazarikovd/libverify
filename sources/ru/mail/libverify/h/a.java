package ru.mail.libverify.h;

import android.content.Context;
import android.os.Handler;
import android.widget.BaseAdapter;
import androidx.annotation.NonNull;
import java.text.DateFormat;
import ru.mail.libverify.api.VerificationApi;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/h/a.class */
public abstract class a extends BaseAdapter {
    private static DateFormat e;
    protected final Context b;
    protected final VerificationApi c;
    private boolean d = false;
    protected final Handler a = new Handler();

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(@NonNull Context context, @NonNull VerificationApi verificationApi) {
        this.b = context;
        this.c = verificationApi;
    }

    public void a(int i) {
    }

    public abstract void b();

    public abstract void a();

    protected abstract void g();

    /* JADX INFO: Access modifiers changed from: protected */
    public final void d() {
        this.d = false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void e() {
        this.d = false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void f() {
        if (this.d) {
            return;
        }
        this.d = true;
        g();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final DateFormat c() {
        if (e == null) {
            e = android.text.format.DateFormat.getTimeFormat(this.b);
        }
        return e;
    }
}
