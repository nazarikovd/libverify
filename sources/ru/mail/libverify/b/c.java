package ru.mail.libverify.b;

import android.util.Base64;
import java.nio.charset.Charset;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.Nullable;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/b/c.class */
public final class c {
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v6, types: [java.lang.String] */
    @Nullable
    public static String a() {
        ?? r0 = "LNZrigBD/02VR1dPpu0n/Q==";
        Intrinsics.checkNotNullParameter("LNZrigBD/02VR1dPpu0n/Q==", "code");
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(2, new SecretKeySpec(Base64.decode("R1DhaJqD2Xvr9WnaYRSluj/E5Wp5uhOLA9fgFMQCQRw=", 2), "AES"));
            byte[] doFinal = cipher.doFinal(Base64.decode("LNZrigBD/02VR1dPpu0n/Q==", 2));
            Intrinsics.checkNotNullExpressionValue(doFinal, "cipherText");
            Charset forName = Charset.forName("UTF-8");
            Intrinsics.checkNotNullExpressionValue(forName, "forName(charsetName)");
            r0 = new String(doFinal, forName);
            return r0;
        } catch (Exception unused) {
            r0.printStackTrace();
            return null;
        }
    }
}
