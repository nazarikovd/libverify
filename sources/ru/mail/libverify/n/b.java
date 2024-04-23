package ru.mail.libverify.n;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/n/b.class */
public final class b implements Closeable {
    private final InputStream a;
    private final Charset b;
    private byte[] c;
    private int d;
    private int e;

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/n/b$a.class */
    class a extends ByteArrayOutputStream {
        a(int i) {
            super(i);
        }

        @Override // java.io.ByteArrayOutputStream
        public final String toString() {
            int i = ((ByteArrayOutputStream) this).count;
            int i2 = i;
            if (i > 0 && ((ByteArrayOutputStream) this).buf[i2 - 1] == 13) {
                i2--;
            }
            try {
                return new String(((ByteArrayOutputStream) this).buf, 0, i2, b.this.b.name());
            } catch (UnsupportedEncodingException e) {
                throw new AssertionError(e);
            }
        }
    }

    public b(FileInputStream fileInputStream, Charset charset) {
        if (charset == null) {
            throw null;
        }
        if (!charset.equals(c.a)) {
            throw new IllegalArgumentException("Unsupported encoding");
        }
        this.a = fileInputStream;
        this.b = charset;
        this.c = new byte[8192];
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [ru.mail.libverify.n.b] */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v3 */
    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() throws IOException {
        ?? r0 = this;
        InputStream inputStream = r0.a;
        synchronized (inputStream) {
            if (r0.c != null) {
                this.c = null;
                this.a.close();
            }
            r0 = inputStream;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x0075, code lost:
        if (r0[r1] == 13) goto L31;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String b() throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 345
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: ru.mail.libverify.n.b.b():java.lang.String");
    }

    public final boolean a() {
        return this.e == -1;
    }
}
