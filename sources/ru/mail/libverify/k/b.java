package ru.mail.libverify.k;

import androidx.annotation.NonNull;
import ru.mail.libverify.k.m;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/k/b.class */
public final class b {
    private final long a;
    private final m.c b;
    private final String c;
    private final String d;

    /* JADX INFO: Access modifiers changed from: package-private */
    public b(long j, m.c cVar, String str, String str2) {
        this.a = j;
        this.b = cVar;
        this.c = str;
        this.d = str2;
    }

    public final long a() {
        return this.a;
    }

    public final String b() {
        return this.c;
    }

    public final String c() {
        return this.d;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final m.c d() {
        return this.b;
    }

    @NonNull
    public final String toString() {
        StringBuilder append = new StringBuilder("{").append(this.b).append(":");
        String str = this.c;
        StringBuilder append2 = append.append(str == null ? "null" : "[" + str.length() + "]").append(":");
        String str2 = this.d;
        return append2.append(str2 == null ? "null" : "[" + str2.length() + "]").append("}").toString();
    }
}
