package ru.mail.libverify.o;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import dagger.Lazy;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.mail.libverify.m.l;
import ru.mail.verify.core.utils.FileLog;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/o/i.class */
public final class i {
    @NotNull
    private final String a;
    @NotNull
    private final Lazy<b> b;
    @Nullable
    private final String c;
    private boolean d;

    public i(@NotNull String str, @NotNull Lazy lazy) {
        Intrinsics.checkNotNullParameter(str, "contentUrl");
        Intrinsics.checkNotNullParameter(lazy, "cache");
        this.a = str;
        this.b = lazy;
        this.c = "SmsCodeNotification";
    }

    private final Bitmap a(InputStream inputStream) throws IOException {
        FileLog.v("ImageDownloadTask", "Decode from stream: %s for url: %s", inputStream, this.a);
        if (inputStream.available() == 0) {
            if (inputStream.markSupported()) {
                inputStream.reset();
            } else if (inputStream instanceof FileInputStream) {
                inputStream.close();
                InputStream a = ((b) this.b.get()).a(this.a);
                inputStream = a;
                Intrinsics.checkNotNull(a);
            }
        }
        Bitmap decodeStream = BitmapFactory.decodeStream(inputStream);
        if (decodeStream != null) {
            return decodeStream;
        }
        throw new IOException("Can't decode an image from url: " + this.a);
    }

    public final boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (Intrinsics.areEqual(i.class, obj != null ? obj.getClass() : null)) {
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type ru.mail.libverify.storage.images.ImageDownloadTask");
            return Intrinsics.areEqual(this.a, ((i) obj).a);
        }
        return false;
    }

    public final int hashCode() {
        return this.a.hashCode();
    }

    @Nullable
    public final Bitmap a(@NotNull l lVar) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Bitmap bitmap;
        InputStream a;
        Intrinsics.checkNotNullParameter(lVar, "data");
        if (this.d) {
            return null;
        }
        this.d = true;
        if (!(!StringsKt.isBlank(this.a))) {
            this.d = false;
            throw new IllegalArgumentException("Content url empty".toString());
        }
        try {
            Lazy<b> lazy = this.b;
            String str = this.a;
            String str2 = this.c;
            String str3 = str2;
            if (str2 == null) {
                str3 = "ImageDownloadTask";
            }
            ru.mail.libverify.j.d execute = new ru.mail.libverify.i.d(lVar, lazy, str, str3).execute();
            Intrinsics.checkNotNullExpressionValue(execute, "createContentApiRequest(…0\n            ).execute()");
            a = execute.a();
        } catch (Throwable th) {
            th.printStackTrace();
            FileLog.e("ImageDownloadTask", th, "Failed execute request for %s", this.a);
            this.d = false;
            bitmap = null;
        }
        if (a != null) {
            this.d = false;
            bitmap = a(a);
            return bitmap;
        }
        throw new Throwable("Failed to read response.");
    }
}
