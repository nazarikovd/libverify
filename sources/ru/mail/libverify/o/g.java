package ru.mail.libverify.o;

import android.graphics.Bitmap;
import dagger.Lazy;
import javax.inject.Inject;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.mail.libverify.m.l;
import ru.mail.verify.core.utils.DebugUtils;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/o/g.class */
public final class g implements f {
    @NotNull
    private final Lazy<b> a;
    @NotNull
    private final l b;

    @Inject
    public g(@NotNull Lazy<b> lazy, @NotNull l lVar) {
        Intrinsics.checkNotNullParameter(lazy, "cache");
        Intrinsics.checkNotNullParameter(lVar, "data");
        this.a = lazy;
        this.b = lVar;
    }

    @Override // ru.mail.libverify.o.f
    @Nullable
    public final Bitmap a(@Nullable String str) {
        Bitmap bitmap;
        if (str == null) {
            return null;
        }
        try {
            bitmap = new i(str, this.a).a(this.b);
        } catch (Exception e) {
            DebugUtils.safeThrow("SmsCodeNotification", e, "Failed init download %s", str);
            bitmap = null;
        }
        return bitmap;
    }
}
