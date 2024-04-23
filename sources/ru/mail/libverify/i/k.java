package ru.mail.libverify.i;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;
import java.util.Locale;
import ru.mail.libverify.i.j;
import ru.mail.libverify.i.p;
import ru.mail.verify.core.requests.RequestPersistentId;
import ru.mail.verify.core.utils.Gsonable;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/i/k.class */
final class k implements Gsonable, RequestPersistentId {
    final String pushSessionId;
    final List<j.d> statusData;
    final String pushApplicationId;
    final long statusTimestamp;
    final j.c deliveryMethod;
    final j.b confirmAction;
    final p.b routeType;

    private k() {
        this.pushSessionId = null;
        this.statusData = null;
        this.pushApplicationId = null;
        this.statusTimestamp = 0L;
        this.deliveryMethod = null;
        this.confirmAction = null;
        this.routeType = null;
    }

    @Override // ru.mail.verify.core.requests.RequestPersistentId
    public final String getId() {
        return String.format(Locale.US, "push_status_%s_%s_%s_%s_%s", this.pushSessionId, this.statusData, this.pushApplicationId, this.deliveryMethod, this.confirmAction);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public k(@NonNull List<j.d> list, @NonNull String str, @Nullable String str2, long j, @NonNull j.c cVar, @NonNull j.b bVar, @Nullable p.b bVar2) {
        this.pushSessionId = str;
        this.statusData = list;
        this.pushApplicationId = str2;
        this.statusTimestamp = j;
        this.deliveryMethod = cVar;
        this.confirmAction = bVar;
        this.routeType = bVar2;
    }
}
