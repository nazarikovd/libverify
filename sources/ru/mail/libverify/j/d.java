package ru.mail.libverify.j;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.mail.verify.core.requests.response.ResponseBase;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/j/d.class */
public final class d extends ResponseBase<ru.mail.libverify.i.d> {
    @Nullable
    private InputStream content;
    @NotNull
    private String eTag = "";
    private boolean isLoadedFromCache;

    @Nullable
    public final InputStream a() {
        return this.content;
    }

    public final void b() {
        this.isLoadedFromCache = true;
    }

    @Override // ru.mail.verify.core.requests.response.ResponseBase
    public final boolean isOk() {
        return this.content != null;
    }

    public final void a(@Nullable ByteArrayInputStream byteArrayInputStream) {
        this.content = byteArrayInputStream;
    }

    public final void a(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.eTag = str;
    }
}
