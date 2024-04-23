package ru.mail.libverify.b;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/b/b.class */
public final class b extends ContextWrapper {
    public b(Context context) {
        super(context);
    }

    public final ArrayList<String> a() {
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            String packageName = getPackageName();
            for (Signature signature : getPackageManager().getPackageInfo(packageName, 64).signatures) {
                String a = a(packageName, signature.toCharsString());
                if (a != null) {
                    arrayList.add(String.format("%s", a));
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("b", "Unable to find package to obtain hash.", e);
        }
        return arrayList;
    }

    private static String a(String str, String str2) {
        String str3 = str + " " + str2;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str3.getBytes(StandardCharsets.UTF_8));
            String substring = Base64.encodeToString(Arrays.copyOfRange(messageDigest.digest(), 0, 9), 3).substring(0, 11);
            String.format("pkg: %s -- hash: %s", str, substring);
            return substring;
        } catch (NoSuchAlgorithmException e) {
            Log.e("b", "hash:NoSuchAlgorithm", e);
            return null;
        }
    }
}
