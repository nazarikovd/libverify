package ru.mail.libverify.o;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.jetbrains.annotations.Nullable;
import ru.mail.libverify.n.a;
import ru.mail.verify.core.utils.FileLog;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/o/d.class */
public final class d {
    private final OutputStream a;
    final /* synthetic */ OutputStream b;
    final /* synthetic */ a.c c;
    final /* synthetic */ ru.mail.libverify.n.a d;
    final /* synthetic */ String e;
    final /* synthetic */ String f;

    /* JADX INFO: Access modifiers changed from: package-private */
    public d(OutputStream outputStream, a.c cVar, ru.mail.libverify.n.a aVar, String str, String str2) {
        this.b = outputStream;
        this.c = cVar;
        this.d = aVar;
        this.e = str;
        this.f = str2;
        this.a = outputStream;
    }

    public final OutputStream c() {
        return this.a;
    }

    @Nullable
    public final InputStream b() {
        try {
            this.b.close();
            this.c.c();
            this.d.a();
            Object[] objArr = new Object[1];
            objArr[0] = this.e;
            FileLog.v("DiskCache", "Item cached for key: %s", objArr);
            a.e b = this.d.b(this.f);
            if (b != null) {
                return b.a();
            }
            return null;
        } catch (IOException e) {
            FileLog.e("DiskCache", e, "Failed to commit cache item for key: %s", this.e);
            a();
            return null;
        }
    }

    public final void a() {
        this.c.b();
        try {
            this.b.close();
            Object[] objArr = new Object[2];
            objArr[0] = Boolean.valueOf(this.d.d(this.e));
            objArr[1] = this.e;
            FileLog.v("DiskCache", "Drop cache item result: %s for key: %s", objArr);
        } catch (IOException e) {
            FileLog.e("DiskCache", e, "Failed to close cache item stream for key: %s", this.e);
        }
    }
}
