package ru.mail.libverify.k;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.regex.Pattern;
import ru.mail.libverify.k.l;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/k/i.class */
public final class i {
    private final j a;
    private l.a b;
    private Pattern c;
    private Pattern d;

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/k/i$a.class */
    class a implements l.e {
        final /* synthetic */ l.a a;

        a(l.a aVar) {
            this.a = aVar;
        }

        @Override // ru.mail.libverify.k.l.a
        public final void b(b bVar) {
            l.a aVar = this.a;
            if (aVar != null) {
                aVar.b(bVar);
            }
        }

        @Override // ru.mail.libverify.k.l.b
        public final void c(b bVar) {
        }

        @Override // ru.mail.libverify.k.l.c
        public final void d(b bVar) {
        }

        @Override // ru.mail.libverify.k.l.d
        public final void a(b bVar) {
        }
    }

    public i(@NonNull j jVar) {
        Pattern pattern = m.h;
        this.c = pattern;
        this.d = pattern;
        this.a = jVar;
    }

    @NonNull
    public final e a() {
        return this.a.a(this.c, this.d, new a(this.b));
    }

    @NonNull
    public final i a(@Nullable l.a aVar) {
        this.b = aVar;
        return this;
    }
}
