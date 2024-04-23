package ru.mail.libverify.p;

import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;
import ru.mail.libverify.api.VerificationApi;
import ru.mail.verify.core.utils.FileLog;
import ru.mail.verify.core.utils.Utils;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/p/c.class */
final class c implements VerificationApi.SmsDialogItem {
    private final String a;
    private final long b;
    private volatile long c;
    private volatile String d;
    private volatile boolean e;
    private volatile boolean f;
    private final a<d> g = new a<>();
    private final TreeMap<Long, d> h = new TreeMap<>();

    /* JADX INFO: Access modifiers changed from: package-private */
    public c(@NonNull String str, long j) {
        this.a = str;
        this.b = j;
    }

    @Override // ru.mail.libverify.api.VerificationApi.SmsDialogItem
    public final long getId() {
        return this.b;
    }

    @Override // ru.mail.libverify.api.VerificationApi.SmsDialogItem
    public final String getFrom() {
        return this.a;
    }

    @Override // ru.mail.libverify.api.VerificationApi.SmsDialogItem
    public final long getLastTimestamp() {
        return this.c;
    }

    @Override // ru.mail.libverify.api.VerificationApi.SmsDialogItem
    public final String getLastText() {
        return this.d;
    }

    @Override // ru.mail.libverify.api.VerificationApi.SmsDialogItem
    public final boolean hasUnread() {
        return this.e;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void a(@NonNull d dVar) {
        d put = this.h.put(Long.valueOf(dVar.getId()), dVar);
        if (put != null) {
            this.g.a(put);
            this.g.b(dVar);
            return;
        }
        int b = this.g.b(dVar);
        this.f = false;
        FileLog.v("SmsDialogItem", "%s added into %s at index %d", dVar, this.a, Integer.valueOf(b));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void c() {
        this.f = true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean b() {
        return this.f;
    }

    @Override // java.lang.Comparable
    public final int compareTo(@NonNull VerificationApi.SmsDialogItem smsDialogItem) {
        return Utils.compareLong(smsDialogItem.getLastTimestamp(), this.c);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void b(long j) {
        d remove = this.h.remove(Long.valueOf(j));
        if (remove != null) {
            this.g.a(remove);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final ArrayList a() {
        ArrayList arrayList = new ArrayList(this.g.a());
        for (int a = this.g.a() - 1; a >= 0; a--) {
            d a2 = this.g.a(a);
            if (!a2.c()) {
                break;
            }
            arrayList.add(a2);
        }
        FileLog.v("SmsDialogItem", "all %d sms:\n%s", Integer.valueOf(this.g.a()), arrayList);
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void c(long j) {
        this.c = j;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final ArrayList a(int i) {
        ArrayList arrayList = new ArrayList(i);
        if (this.g.a() != 0) {
            int a = this.g.a() - 1;
            for (int i2 = 0; i2 < i && a >= 0; i2++) {
                d a2 = this.g.a(a);
                if (!a2.c()) {
                    break;
                }
                arrayList.add(a2);
                a--;
            }
        }
        FileLog.v("SmsDialogItem", "first %d sms:\n%s", Integer.valueOf(i), arrayList);
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void b(boolean z) {
        this.e = z;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final VerificationApi.SmsItem a(boolean z) {
        if (this.g.a() != 0) {
            a<d> aVar = this.g;
            d a = aVar.a(aVar.a() - 1);
            if (a.c() || !z) {
                return a;
            }
            return null;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final List a(long j, int i) {
        d dVar = this.h.get(Long.valueOf(j));
        return dVar == null ? Collections.emptyList() : a(dVar, i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final List a(@NonNull d dVar, int i) {
        int a;
        if (dVar.c() && (a = this.g.a((a<d>) dVar)) >= 0) {
            ArrayList arrayList = new ArrayList(i);
            for (int i2 = a - 1; arrayList.size() < i && i2 >= 0; i2--) {
                d a2 = this.g.a(i2);
                if (!a2.c()) {
                    break;
                }
                arrayList.add(a2);
            }
            FileLog.v("SmsDialogItem", "%d sms starting from %d:\n%s", Integer.valueOf(i), Long.valueOf(this.b), arrayList);
            return arrayList;
        }
        return Collections.emptyList();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final VerificationApi.SmsItem a(long j) {
        return this.h.get(Long.valueOf(j));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void a(String str) {
        this.d = str;
    }
}
