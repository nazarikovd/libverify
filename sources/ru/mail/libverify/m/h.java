package ru.mail.libverify.m;

import android.content.Context;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.Nullable;
import ru.mail.verify.core.api.NetworkManager;
import ru.mail.verify.core.utils.Utils;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/m/h.class */
final class h extends Lambda implements Function0<Object> {
    final /* synthetic */ Context a;
    final /* synthetic */ NetworkManager b;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public h(Context context, NetworkManager networkManager) {
        super(0);
        this.a = context;
        this.b = networkManager;
    }

    @Nullable
    public final Object invoke() {
        if (Utils.hasSelfPermission(this.a, "android.permission.READ_PHONE_STATE")) {
            return Boolean.valueOf(this.b.hasCellularConnection());
        }
        return null;
    }
}
