package ru.mail.libverify.m;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.Nullable;
import ru.mail.verify.core.api.NetworkManager;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/m/g.class */
final class g extends Lambda implements Function0<Object> {
    final /* synthetic */ NetworkManager a;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public g(NetworkManager networkManager) {
        super(0);
        this.a = networkManager;
    }

    @Nullable
    public final Object invoke() {
        return Boolean.valueOf(this.a.hasVpnConnection());
    }
}
