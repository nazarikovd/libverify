package ru.mail.libverify.o;

import java.io.IOException;
import java.io.InputStream;
import javax.inject.Inject;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.mail.libverify.m.l;
import ru.mail.libverify.n.a;
import ru.mail.verify.core.utils.FileLog;
import ru.mail.verify.core.utils.Utils;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/o/c.class */
public final class c implements b {
    @NotNull
    private final l a;
    @NotNull
    private final Lazy b;

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/o/c$a.class */
    static final class a extends Lambda implements Function0<ru.mail.libverify.n.a> {
        a() {
            super(0);
        }

        public final Object invoke() {
            ru.mail.libverify.n.a aVar;
            try {
                aVar = ru.mail.libverify.n.a.a(c.this.a.getCacheFolder());
            } catch (IOException e) {
                FileLog.e("DiskCache", "Failed to init disk cache", e);
                aVar = null;
            }
            return aVar;
        }
    }

    @Inject
    public c(@NotNull l lVar) {
        Intrinsics.checkNotNullParameter(lVar, "config");
        this.a = lVar;
        this.b = LazyKt.lazy(new a());
    }

    @Override // ru.mail.libverify.o.b
    @Nullable
    public final d b(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "key");
        ru.mail.libverify.n.a aVar = (ru.mail.libverify.n.a) this.b.getValue();
        if (aVar != null) {
            String stringToMD5 = Utils.stringToMD5(str);
            try {
                a.c a2 = aVar.a(stringToMD5);
                if (a2 == null) {
                    FileLog.e("DiskCache", "Editor is in use for key: %s", str);
                    return null;
                }
                return new d(a2.d(), a2, aVar, str, stringToMD5);
            } catch (IOException e) {
                FileLog.e("DiskCache", e, "Failed to open cache editor for key: %s", str);
                return null;
            }
        }
        return null;
    }

    @Override // ru.mail.libverify.o.b
    @Nullable
    public final InputStream a(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "key");
        ru.mail.libverify.n.a aVar = (ru.mail.libverify.n.a) this.b.getValue();
        if (aVar != null) {
            try {
                a.e b = aVar.b(Utils.stringToMD5(str));
                if (b != null) {
                    FileLog.v("DiskCache", "Cached item found for key: %s", str);
                    return b.a();
                }
                FileLog.d("DiskCache", "Cached item not found for key: %s", str);
                return null;
            } catch (IOException e) {
                FileLog.e("DiskCache", e, "Failed to get cached item for key: %s", str);
                return null;
            }
        }
        return null;
    }
}
