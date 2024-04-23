package ru.mail.libverify.p;

import androidx.annotation.NonNull;
import ru.mail.libverify.api.VerificationApi;
import ru.mail.libverify.p.a;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/p/d.class */
final class d implements VerificationApi.SmsItem, a.InterfaceC0019a {
    private final String a;
    private final String b;
    private final long c;
    private final long d;
    private final long e;
    private boolean f = false;

    /* JADX INFO: Access modifiers changed from: package-private */
    public d(@NonNull String str, @NonNull String str2, long j, long j2, long j3) {
        this.a = str;
        this.b = str2;
        this.e = j;
        this.c = j2;
        this.d = j3;
    }

    @Override // ru.mail.libverify.api.VerificationApi.SmsItem
    public final long getId() {
        return this.e;
    }

    @Override // ru.mail.libverify.api.VerificationApi.SmsItem
    public final String getText() {
        return this.b;
    }

    @Override // ru.mail.libverify.api.VerificationApi.SmsItem
    public final long getTimestamp() {
        return this.c;
    }

    @Override // ru.mail.libverify.api.VerificationApi.SmsItem
    public final String getFrom() {
        return this.a;
    }

    @Override // ru.mail.libverify.p.a.InterfaceC0019a
    public final long b() {
        return this.d;
    }

    @Override // ru.mail.libverify.p.a.InterfaceC0019a
    public final long a() {
        return this.e;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void d() {
        this.f = true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean c() {
        return this.f;
    }

    @NonNull
    public final String toString() {
        return "SmsItemImpl{from='" + this.a + "', text='" + this.b + "', timestamp=" + this.c + ", serverTimestamp=" + this.d + ", id=" + this.e + '}';
    }
}
