package ru.mail.libverify.p;

import androidx.annotation.NonNull;
import java.util.ArrayList;
import ru.mail.libverify.p.a.InterfaceC0019a;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/p/a.class */
final class a<T extends InterfaceC0019a> {
    private final ArrayList<T> a = new ArrayList<>();

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: ru.mail.libverify.p.a$a  reason: collision with other inner class name */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/p/a$a.class */
    public interface InterfaceC0019a {
        long b();

        long a();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final int b(@NonNull T t) {
        int i;
        long b = t.b();
        int i2 = 0;
        int size = this.a.size() - 1;
        while (true) {
            if (i2 > size) {
                i = i2 ^ (-1);
                break;
            }
            i = (i2 + size) >>> 1;
            long b2 = this.a.get(i).b();
            if (b2 >= b) {
                if (b2 <= b) {
                    break;
                }
                size = i - 1;
            } else {
                i2 = i + 1;
            }
        }
        if (i < 0) {
            int i3 = i ^ (-1);
            i = i3;
            if (i3 == this.a.size()) {
                this.a.add(t);
                return i;
            }
        }
        this.a.add(i, t);
        return i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final int a(@NonNull T t) {
        int i;
        long b = t.b();
        int i2 = 0;
        int size = this.a.size() - 1;
        while (true) {
            if (i2 > size) {
                i = i2 ^ (-1);
                break;
            }
            i = (i2 + size) >>> 1;
            long b2 = this.a.get(i).b();
            if (b2 >= b) {
                if (b2 <= b) {
                    break;
                }
                size = i - 1;
            } else {
                i2 = i + 1;
            }
        }
        if (i < 0) {
            return i;
        }
        for (int i3 = i; i3 < this.a.size(); i3++) {
            T t2 = this.a.get(i3);
            if (t2.b() != t.b()) {
                break;
            } else if (t2.a() == t.a()) {
                return i3;
            }
        }
        for (int i4 = i - 1; i4 >= 0; i4--) {
            T t3 = this.a.get(i4);
            if (t3.b() != t.b()) {
                return -1;
            }
            if (t3.a() == t.a()) {
                return i4;
            }
        }
        return -1;
    }

    @NonNull
    public final String toString() {
        return "LongSortedArray{items=" + this.a + '}';
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final T a(int i) {
        return this.a.get(i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void a(@NonNull d dVar) {
        int a = a((a<T>) dVar);
        if (a >= 0) {
            this.a.remove(a);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final int a() {
        return this.a.size();
    }
}
