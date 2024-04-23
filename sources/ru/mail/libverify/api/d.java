package ru.mail.libverify.api;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Log;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/d.class */
public class d {
    public static String a(Context context, String key) {
        try {
            Signature[] signatureArr = context.getPackageManager().getPackageInfo(context.getPackageName(), 64).signatures;
            if (signatureArr.length > 0) {
                Signature signature = signatureArr[0];
                MessageDigest messageDigest = MessageDigest.getInstance(key);
                messageDigest.update(signature.toByteArray());
                byte[] digest = messageDigest.digest();
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < digest.length; i++) {
                    if (i != 0) {
                        sb.append(":");
                    }
                    String hexString = Integer.toHexString(digest[i] & 255);
                    if (hexString.length() == 1) {
                        sb.append("0");
                    }
                    sb.append(hexString);
                }
                return sb.toString();
            }
            return null;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("d", "name not found " + e.toString());
            return null;
        } catch (NoSuchAlgorithmException e2) {
            Log.e("d", "no such an algorithm " + e2.toString());
            return null;
        } catch (Exception e3) {
            Log.e("d", "exception " + e3.toString());
            return null;
        }
    }
}
