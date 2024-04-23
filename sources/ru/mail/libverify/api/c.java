package ru.mail.libverify.api;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import ru.mail.libverify.api.a;
import ru.mail.libverify.api.g;
import ru.mail.libverify.k.c;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/c.class */
final class c implements c.a {
    final /* synthetic */ g.a a;
    final /* synthetic */ a.c b;
    final /* synthetic */ ru.mail.libverify.j.l c;

    /* JADX INFO: Access modifiers changed from: package-private */
    public c(a.c cVar, g.a aVar, ru.mail.libverify.j.l lVar) {
        this.a = aVar;
        this.b = cVar;
        this.c = lVar;
    }

    @Override // ru.mail.libverify.k.c.a
    @Nullable
    public final String a(@NonNull String str) throws InterruptedException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        String a = g.a(str, this.a);
        if (!this.b.b && TextUtils.isEmpty(a) && !TextUtils.isEmpty(str)) {
            this.b.b = a.j.matcher(str).matches();
        }
        return a;
    }

    @Override // ru.mail.libverify.k.c.a
    public final boolean b(@Nullable String str) throws InterruptedException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        boolean contains = this.c.d().contains(str);
        a.c cVar = this.b;
        if (!cVar.a) {
            cVar.a = contains;
        }
        return contains;
    }

    @Override // ru.mail.libverify.k.c.a
    public final long c() {
        return this.c.e();
    }

    @Override // ru.mail.libverify.k.c.a
    public final long d() {
        return this.c.f();
    }

    @Override // ru.mail.libverify.k.c.a
    public final int a() {
        return this.c.a();
    }

    @Override // ru.mail.libverify.k.c.a
    public final int b() {
        return this.c.b();
    }
}
