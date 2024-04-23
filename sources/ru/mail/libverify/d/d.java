package ru.mail.libverify.d;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import ru.mail.libverify.api.VerifyRoute;
import ru.mail.libverify.i.p;
import ru.mail.verify.core.utils.Gsonable;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/d/d.class */
public final class d implements Gsonable {
    @NonNull
    final a authMethod;
    @NonNull
    final p.a authType;
    @Nullable
    final VerifyRoute verifyRoute;
    final p.b singleCheck;

    private d() {
        this.authMethod = a.DEFAULT;
        this.authType = p.a.EMPTY;
        this.verifyRoute = null;
        this.singleCheck = null;
    }

    public static d a() {
        return new d(a.DEFAULT, p.a.EMPTY, null, null);
    }

    public static d c() {
        return new d(a.VKCONNECT, p.a.VKCONNECT, null, null);
    }

    public static d b() {
        return new d(a.VKLOGIN, p.a.VKCONNECT, VerifyRoute.VKCLogin, null);
    }

    public final VerifyRoute f() {
        return this.verifyRoute;
    }

    public final p.b g() {
        return this.singleCheck;
    }

    @NonNull
    public final p.a e() {
        return this.authType;
    }

    @NonNull
    public final a d() {
        return this.authMethod;
    }

    private d(@NonNull a aVar, @NonNull p.a aVar2, @Nullable VerifyRoute verifyRoute, @Nullable p.b bVar) {
        this.authMethod = aVar;
        this.authType = aVar2;
        this.verifyRoute = verifyRoute;
        this.singleCheck = bVar;
    }

    public static d a(@NonNull VerifyRoute verifyRoute) {
        return new d(a.MANUAL, p.a.EMPTY, verifyRoute, null);
    }

    public static d a(@NonNull p.b bVar) {
        return new d(a.RESEND, p.a.EMPTY, null, bVar);
    }
}
