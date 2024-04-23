package ru.mail.libverify.n;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/n/a.class */
public final class a implements Closeable {
    static final Pattern o = Pattern.compile("[a-z0-9_-]{1,120}");
    private static final OutputStream p = new b();
    private final File a;
    private final File b;
    private final File c;
    private final File d;
    private BufferedWriter i;
    private int k;
    private long h = 0;
    private final LinkedHashMap<String, d> j = new LinkedHashMap<>(0, 0.75f, true);
    private long l = 0;
    final ThreadPoolExecutor m = new ThreadPoolExecutor(0, 1, 60, TimeUnit.SECONDS, new LinkedBlockingQueue());
    private final Callable<Void> n = new CallableC0016a();
    private final int e = 1;
    private final int g = 1;
    private long f = 10311680;

    /* renamed from: ru.mail.libverify.n.a$a  reason: collision with other inner class name */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/n/a$a.class */
    class CallableC0016a implements Callable<Void> {
        CallableC0016a() {
        }

        @Override // java.util.concurrent.Callable
        public final Void call() throws Exception {
            synchronized (a.this) {
                a aVar = a.this;
                if (aVar.i != null) {
                    while (aVar.h > aVar.f) {
                        aVar.d(aVar.j.entrySet().iterator().next().getKey());
                    }
                    a aVar2 = a.this;
                    int i = aVar2.k;
                    if (i >= 2000 && i >= aVar2.j.size()) {
                        a.this.d();
                        a.this.k = 0;
                    }
                }
            }
            return null;
        }
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/n/a$b.class */
    class b extends OutputStream {
        b() {
        }

        @Override // java.io.OutputStream
        public final void write(int i) throws IOException {
        }
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/n/a$c.class */
    public final class c {
        private final d a;
        private final boolean[] b;
        private boolean c;
        private boolean d;

        /* renamed from: ru.mail.libverify.n.a$c$a  reason: collision with other inner class name */
        /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/n/a$c$a.class */
        private class C0017a extends FilterOutputStream {
            private C0017a(FileOutputStream fileOutputStream) {
                super(fileOutputStream);
            }

            @Override // java.io.FilterOutputStream, java.io.OutputStream
            public final void write(int i) {
                try {
                    ((FilterOutputStream) this).out.write(i);
                } catch (IOException unused) {
                    c.this.c = true;
                }
            }

            @Override // java.io.FilterOutputStream, java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
            public final void close() {
                try {
                    ((FilterOutputStream) this).out.close();
                } catch (IOException unused) {
                    c.this.c = true;
                }
            }

            @Override // java.io.FilterOutputStream, java.io.OutputStream, java.io.Flushable
            public final void flush() {
                try {
                    ((FilterOutputStream) this).out.flush();
                } catch (IOException unused) {
                    c.this.c = true;
                }
            }

            @Override // java.io.FilterOutputStream, java.io.OutputStream
            public final void write(byte[] bArr, int i, int i2) {
                try {
                    ((FilterOutputStream) this).out.write(bArr, i, i2);
                } catch (IOException unused) {
                    c.this.c = true;
                }
            }
        }

        private c(d dVar) {
            this.a = dVar;
            this.b = dVar.c ? null : new boolean[a.this.g];
        }

        public final OutputStream d() throws IOException {
            FileOutputStream fileOutputStream;
            FileOutputStream fileOutputStream2;
            C0017a c0017a;
            FileOutputStream fileOutputStream3;
            a aVar = a.this;
            if (aVar.g > 0) {
                synchronized (aVar) {
                    d dVar = this.a;
                    if (dVar.d != this) {
                        throw new IllegalStateException();
                    }
                    if (!dVar.c) {
                        this.b[0] = true;
                    }
                    File b = dVar.b(0);
                    try {
                        fileOutputStream2 = fileOutputStream3;
                        fileOutputStream3 = new FileOutputStream(b);
                    } catch (FileNotFoundException unused) {
                        a.this.a.mkdirs();
                        try {
                            fileOutputStream2 = fileOutputStream;
                            fileOutputStream = new FileOutputStream(b);
                        } catch (FileNotFoundException unused2) {
                            return a.p;
                        }
                    }
                    c0017a = new C0017a(fileOutputStream2);
                }
                return c0017a;
            }
            throw new IllegalArgumentException("Expected index 0 to be greater than 0 and less than the maximum value count of " + a.this.g);
        }

        public final void c() throws IOException {
            if (this.c) {
                a.this.a(this, false);
                a.this.d(this.a.a);
            } else {
                a.this.a(this, true);
            }
            this.d = true;
        }

        public final void a() throws IOException {
            a.this.a(this, false);
        }

        public final void b() {
            if (this.d) {
                return;
            }
            try {
                a();
            } catch (IOException unused) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/n/a$d.class */
    public final class d {
        private final String a;
        private final long[] b;
        private boolean c;
        private c d;

        private d(String str) {
            this.a = str;
            this.b = new long[a.this.g];
        }

        public final File a(int i) {
            return new File(a.this.a, this.a + "." + i);
        }

        public final File b(int i) {
            return new File(a.this.a, this.a + "." + i + ".tmp");
        }
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/n/a$e.class */
    public final class e implements Closeable {
        private final InputStream[] a;

        private e(InputStream[] inputStreamArr) {
            this.a = inputStreamArr;
        }

        public final InputStream a() {
            return this.a[0];
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v1, types: [java.io.InputStream[]] */
        /* JADX WARN: Type inference failed for: r0v6 */
        /* JADX WARN: Type inference failed for: r0v7, types: [java.lang.Throwable] */
        /* JADX WARN: Type inference failed for: r0v8, types: [java.io.Closeable] */
        @Override // java.io.Closeable, java.lang.AutoCloseable
        public final void close() {
            ?? r0;
            for (?? r02 : this.a) {
                Charset charset = ru.mail.libverify.n.c.a;
                if (r02 != 0) {
                    try {
                        r02 = r02;
                        r02.close();
                    } catch (RuntimeException unused) {
                        throw r02;
                    } catch (Exception unused2) {
                    }
                }
            }
        }
    }

    private a(File file) {
        this.a = file;
        this.b = new File(file, "journal");
        this.c = new File(file, "journal.tmp");
        this.d = new File(file, "journal.bkp");
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable, ru.mail.libverify.n.b] */
    private void c() throws IOException {
        ?? bVar = new ru.mail.libverify.n.b(new FileInputStream(this.b), ru.mail.libverify.n.c.a);
        try {
            String b2 = bVar.b();
            String b3 = bVar.b();
            String b4 = bVar.b();
            String b5 = bVar.b();
            String b6 = bVar.b();
            if (!"libcore.io.DiskLruCache".equals(b2) || !"1".equals(b3) || !Integer.toString(this.e).equals(b4) || !Integer.toString(this.g).equals(b5) || !"".equals(b6)) {
                throw new IOException("unexpected journal header: [" + b2 + ", " + b3 + ", " + b5 + ", " + b6 + "]");
            }
            int i = 0;
            while (true) {
                try {
                    c(bVar.b());
                    i++;
                } catch (EOFException unused) {
                    this.k = i - this.j.size();
                    if (bVar.a()) {
                        d();
                    } else {
                        this.i = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.b, true), ru.mail.libverify.n.c.a));
                    }
                    try {
                        bVar.close();
                        return;
                    } catch (RuntimeException unused2) {
                        throw bVar;
                    } catch (Exception unused3) {
                        return;
                    }
                }
            }
        } catch (Throwable th) {
            try {
                bVar.close();
            } catch (RuntimeException unused4) {
                throw bVar;
            } catch (Exception unused5) {
            }
            throw th;
        }
    }

    private synchronized void d() throws IOException {
        StringBuilder append;
        BufferedWriter bufferedWriter = this.i;
        if (bufferedWriter != null) {
            bufferedWriter.close();
        }
        BufferedWriter bufferedWriter2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.c), ru.mail.libverify.n.c.a));
        try {
            bufferedWriter2.write("libcore.io.DiskLruCache");
            bufferedWriter2.write("\n");
            bufferedWriter2.write("1");
            bufferedWriter2.write("\n");
            bufferedWriter2.write(Integer.toString(this.e));
            bufferedWriter2.write("\n");
            bufferedWriter2.write(Integer.toString(this.g));
            bufferedWriter2.write("\n");
            bufferedWriter2.write("\n");
            for (d dVar : this.j.values()) {
                if (dVar.d != null) {
                    append = new StringBuilder().append("DIRTY ").append(dVar.a).append('\n');
                } else {
                    StringBuilder append2 = new StringBuilder().append("CLEAN ").append(dVar.a);
                    StringBuilder sb = new StringBuilder();
                    for (long j : dVar.b) {
                        sb.append(' ').append(j);
                    }
                    append = append2.append(sb.toString()).append('\n');
                }
                bufferedWriter2.write(append.toString());
            }
            bufferedWriter2.close();
            if (this.b.exists()) {
                File file = this.b;
                File file2 = this.d;
                if (file2.exists() && !file2.delete()) {
                    throw new IOException();
                }
                if (!file.renameTo(file2)) {
                    throw new IOException();
                }
            }
            if (this.c.renameTo(this.b)) {
                this.d.delete();
                this.i = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.b, true), ru.mail.libverify.n.c.a));
                return;
            }
            throw new IOException();
        } catch (Throwable th) {
            bufferedWriter2.close();
            throw th;
        }
    }

    private synchronized void a(c cVar, boolean z) throws IOException {
        d dVar = cVar.a;
        if (dVar.d != cVar) {
            throw new IllegalStateException();
        }
        if (z && !dVar.c) {
            for (int i = 0; i < this.g; i++) {
                if (!cVar.b[i]) {
                    cVar.a();
                    throw new IllegalStateException("Newly created entry didn't create value for index " + i);
                } else if (!dVar.b(i).exists()) {
                    cVar.a();
                    return;
                }
            }
        }
        for (int i2 = 0; i2 < this.g; i2++) {
            File b2 = dVar.b(i2);
            if (z) {
                if (b2.exists()) {
                    File a = dVar.a(i2);
                    b2.renameTo(a);
                    long j = dVar.b[i2];
                    long length = a.length();
                    dVar.b[i2] = length;
                    this.h = (this.h - j) + length;
                }
            } else if (b2.exists() && !b2.delete()) {
                throw new IOException();
            }
        }
        this.k++;
        dVar.d = null;
        if (dVar.c || z) {
            dVar.c = true;
            BufferedWriter bufferedWriter = this.i;
            StringBuilder append = new StringBuilder("CLEAN ").append(dVar.a);
            StringBuilder sb = new StringBuilder();
            for (long j2 : dVar.b) {
                sb.append(' ').append(j2);
            }
            bufferedWriter.write(append.append(sb.toString()).append('\n').toString());
            if (z) {
                this.l++;
                dVar.getClass();
            }
        } else {
            this.j.remove(dVar.a);
            this.i.write("REMOVE " + dVar.a + '\n');
        }
        this.i.flush();
        if (this.h <= this.f) {
            int i3 = this.k;
            if (!(i3 >= 2000 && i3 >= this.j.size())) {
                return;
            }
        }
        this.m.submit(this.n);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v21, types: [java.lang.Throwable, java.io.Closeable] */
    public final synchronized e b(String str) throws IOException {
        ?? r0;
        if (this.i != null) {
            if (o.matcher(str).matches()) {
                d dVar = this.j.get(str);
                if (dVar != null && dVar.c) {
                    InputStream[] inputStreamArr = new InputStream[this.g];
                    for (int i = 0; i < this.g; i++) {
                        try {
                            inputStreamArr[i] = new FileInputStream(dVar.a(i));
                        } catch (FileNotFoundException unused) {
                            for (int i2 = 0; i2 < this.g && (r0 = inputStreamArr[i2]) != 0; i2++) {
                                Charset charset = ru.mail.libverify.n.c.a;
                                try {
                                    r0.close();
                                } catch (RuntimeException unused2) {
                                    throw r0;
                                } catch (Exception unused3) {
                                }
                            }
                            return null;
                        }
                    }
                    this.k++;
                    this.i.append((CharSequence) ("READ " + str + '\n'));
                    int i3 = this.k;
                    if (i3 >= 2000 && i3 >= this.j.size()) {
                        this.m.submit(this.n);
                    }
                    return new e(inputStreamArr);
                }
                return null;
            }
            throw new IllegalArgumentException("keys must match regex [a-z0-9_-]{1,120}: \"" + str + "\"");
        }
        throw new IllegalStateException("cache is closed");
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final synchronized void close() throws IOException {
        if (this.i == null) {
            return;
        }
        Iterator it = new ArrayList(this.j.values()).iterator();
        while (it.hasNext()) {
            c cVar = ((d) it.next()).d;
            if (cVar != null) {
                cVar.a();
            }
        }
        while (this.h > this.f) {
            d(this.j.entrySet().iterator().next().getKey());
        }
        this.i.close();
        this.i = null;
    }

    public final c a(String str) throws IOException {
        c cVar;
        d dVar;
        synchronized (this) {
            if (this.i != null) {
                if (o.matcher(str).matches()) {
                    d dVar2 = this.j.get(str);
                    d dVar3 = dVar2;
                    if (dVar2 == null) {
                        dVar3 = dVar;
                        dVar = new d(str);
                        this.j.put(str, dVar3);
                    } else if (dVar3.d != null) {
                        cVar = null;
                    }
                    d dVar4 = dVar3;
                    c cVar2 = new c(dVar3);
                    dVar4.d = cVar2;
                    this.i.write("DIRTY " + str + '\n');
                    this.i.flush();
                    cVar = cVar2;
                } else {
                    throw new IllegalArgumentException("keys must match regex [a-z0-9_-]{1,120}: \"" + str + "\"");
                }
            } else {
                throw new IllegalStateException("cache is closed");
            }
        }
        return cVar;
    }

    private void c(String str) throws IOException {
        String substring;
        int indexOf = str.indexOf(32);
        if (indexOf == -1) {
            throw new IOException("unexpected journal line: ".concat(str));
        }
        int i = indexOf + 1;
        int indexOf2 = str.indexOf(32, i);
        if (indexOf2 == -1) {
            substring = str.substring(i);
            if (indexOf == 6 && str.startsWith("REMOVE")) {
                this.j.remove(substring);
                return;
            }
        } else {
            substring = str.substring(i, indexOf2);
        }
        d dVar = this.j.get(substring);
        d dVar2 = dVar;
        if (dVar == null) {
            dVar2 = r1;
            d dVar3 = new d(substring);
            this.j.put(substring, dVar2);
        }
        if (indexOf2 != -1 && indexOf == 5 && str.startsWith("CLEAN")) {
            d dVar4 = dVar2;
            String[] split = str.substring(indexOf2 + 1).split(" ");
            dVar4.c = true;
            dVar4.d = null;
            if (split.length != a.this.g) {
                throw new IOException("unexpected journal line: " + Arrays.toString(split));
            }
            for (int i2 = 0; i2 < split.length; i2++) {
                try {
                    dVar2.b[i2] = Long.parseLong(split[i2]);
                } catch (NumberFormatException unused) {
                    throw new IOException("unexpected journal line: " + Arrays.toString(split));
                }
            }
        } else if (indexOf2 == -1 && indexOf == 5 && str.startsWith("DIRTY")) {
            dVar2.d = new c(dVar2);
        } else if (indexOf2 != -1 || indexOf != 4 || !str.startsWith("READ")) {
            throw new IOException("unexpected journal line: ".concat(str));
        }
    }

    private void b() throws IOException {
        File file = this.c;
        if (file.exists() && !file.delete()) {
            throw new IOException();
        }
        Iterator<d> it = this.j.values().iterator();
        while (it.hasNext()) {
            d next = it.next();
            if (next.d == null) {
                for (int i = 0; i < this.g; i++) {
                    this.h += next.b[i];
                }
            } else {
                next.d = null;
                for (int i2 = 0; i2 < this.g; i2++) {
                    File a = next.a(i2);
                    if (a.exists() && !a.delete()) {
                        throw new IOException();
                    }
                    File b2 = next.b(i2);
                    if (b2.exists() && !b2.delete()) {
                        throw new IOException();
                    }
                }
                it.remove();
            }
        }
    }

    public final synchronized boolean d(String str) throws IOException {
        if (this.i != null) {
            if (o.matcher(str).matches()) {
                d dVar = this.j.get(str);
                if (dVar == null || dVar.d != null) {
                    return false;
                }
                for (int i = 0; i < this.g; i++) {
                    File a = dVar.a(i);
                    if (a.exists() && !a.delete()) {
                        throw new IOException("failed to delete " + a);
                    }
                    long j = this.h;
                    long[] jArr = dVar.b;
                    this.h = j - jArr[i];
                    jArr[i] = 0;
                }
                this.k++;
                this.i.append((CharSequence) ("REMOVE " + str + '\n'));
                this.j.remove(str);
                int i2 = this.k;
                if (i2 >= 2000 && i2 >= this.j.size()) {
                    this.m.submit(this.n);
                    return true;
                }
                return true;
            }
            throw new IllegalArgumentException("keys must match regex [a-z0-9_-]{1,120}: \"" + str + "\"");
        }
        throw new IllegalStateException("cache is closed");
    }

    public final synchronized void a() throws IOException {
        if (this.i != null) {
            while (this.h > this.f) {
                d(this.j.entrySet().iterator().next().getKey());
            }
            this.i.flush();
            return;
        }
        throw new IllegalStateException("cache is closed");
    }

    public static a a(File file) throws IOException {
        File file2 = new File(file, "journal.bkp");
        if (file2.exists()) {
            File file3 = new File(file, "journal");
            if (file3.exists()) {
                file2.delete();
            } else if (!file2.renameTo(file3)) {
                throw new IOException();
            }
        }
        a aVar = new a(file);
        if (aVar.b.exists()) {
            try {
                aVar.c();
                aVar.b();
                return aVar;
            } catch (IOException e2) {
                System.out.println("DiskLruCache " + file + " is corrupt: " + e2.getMessage() + ", removing");
                aVar.close();
                ru.mail.libverify.n.c.a(aVar.a);
            }
        }
        file.mkdirs();
        a aVar2 = new a(file);
        aVar2.d();
        return aVar2;
    }
}
