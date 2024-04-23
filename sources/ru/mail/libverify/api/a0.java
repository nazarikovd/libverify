package ru.mail.libverify.api;

import androidx.annotation.NonNull;
import java.util.List;
import ru.mail.libverify.k.g;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/a0.class */
public final class a0 implements g.b {
    final /* synthetic */ x a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public a0(x xVar) {
        this.a = xVar;
    }

    @Override // ru.mail.libverify.k.g.b
    public final long a() {
        return this.a.g.startTimeStamp;
    }

    @Override // ru.mail.libverify.k.g.b
    public final void a(@NonNull List<String> list) {
        if (list.isEmpty()) {
            return;
        }
        for (String str : list) {
            this.a.a(str, false);
        }
    }
}
