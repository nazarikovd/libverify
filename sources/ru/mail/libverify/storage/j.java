package ru.mail.libverify.storage;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import ru.mail.libverify.platform.storage.KeyValueStorage;
import ru.mail.verify.core.utils.DebugUtils;
import ru.mail.verify.core.utils.FileLog;
import ru.mail.verify.core.utils.PRNGFixes;
import ru.mail.verify.core.utils.Utils;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/storage/j.class */
final class j {
    private final KeyValueStorage a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public j(KeyValueStorage keyValueStorage) {
        this.a = keyValueStorage;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final synchronized String a(@NonNull String str, @NonNull String str2) throws DecryptionError {
        FileLog.v("ServerKeyManager", "decrypt message started");
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            throw new IllegalArgumentException();
        }
        String value = this.a.getValue("server_key_manager_private_data");
        try {
            if (TextUtils.isEmpty(value)) {
                throw new DecryptionError("No private key found");
            }
            try {
                PrivateKey a = a(value);
                Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
                cipher.init(2, a);
                byte[] decodeBase64FromString = Utils.decodeBase64FromString(str2);
                if (decodeBase64FromString != null) {
                    byte[] doFinal = cipher.doFinal(decodeBase64FromString);
                    byte[] decodeBase64FromString2 = Utils.decodeBase64FromString(str);
                    if (decodeBase64FromString2 != null) {
                        Cipher cipher2 = Cipher.getInstance("AES/CBC/PKCS5PADDING");
                        cipher2.init(2, new SecretKeySpec(doFinal, "AES"), new IvParameterSpec(decodeBase64FromString2, 0, 16));
                        String str3 = new String(cipher2.doFinal(decodeBase64FromString2, 16, decodeBase64FromString2.length - 16), "UTF-8");
                        FileLog.v("ServerKeyManager", "decrypt message completed");
                        return str3;
                    }
                    throw new DecryptionError("Wrong base64 message text");
                }
                throw new DecryptionError("Wrong base64 key text");
            } catch (UnsupportedEncodingException e) {
                e = e;
                FileLog.e("ServerKeyManager", "failed to decrypt server message", e);
                this.a.removeValue("server_key_manager_private_data").removeValue("server_key_manager_public_data").commit();
                throw new DecryptionError(e);
            } catch (InvalidKeyException e2) {
                e = e2;
                FileLog.e("ServerKeyManager", "failed to decrypt server message", e);
                this.a.removeValue("server_key_manager_private_data").removeValue("server_key_manager_public_data").commit();
                throw new DecryptionError(e);
            } catch (NoSuchAlgorithmException e3) {
                e = e3;
                FileLog.e("ServerKeyManager", "failed to decrypt server message", e);
                this.a.removeValue("server_key_manager_private_data").removeValue("server_key_manager_public_data").commit();
                throw new DecryptionError(e);
            } catch (InvalidKeySpecException e4) {
                e = e4;
                FileLog.e("ServerKeyManager", "failed to decrypt server message", e);
                this.a.removeValue("server_key_manager_private_data").removeValue("server_key_manager_public_data").commit();
                throw new DecryptionError(e);
            } catch (BadPaddingException e5) {
                e = e5;
                FileLog.e("ServerKeyManager", "failed to decrypt server message", e);
                this.a.removeValue("server_key_manager_private_data").removeValue("server_key_manager_public_data").commit();
                throw new DecryptionError(e);
            } catch (IllegalBlockSizeException e6) {
                e = e6;
                FileLog.e("ServerKeyManager", "failed to decrypt server message", e);
                this.a.removeValue("server_key_manager_private_data").removeValue("server_key_manager_public_data").commit();
                throw new DecryptionError(e);
            } catch (NoSuchPaddingException e7) {
                e = e7;
                FileLog.e("ServerKeyManager", "failed to decrypt server message", e);
                this.a.removeValue("server_key_manager_private_data").removeValue("server_key_manager_public_data").commit();
                throw new DecryptionError(e);
            } catch (Exception e8) {
                DebugUtils.safeThrow("ServerKeyManager", "failed to decrypt server message", e8);
                this.a.removeValue("server_key_manager_private_data").removeValue("server_key_manager_public_data").commit();
                throw new DecryptionError(e8);
            }
        } catch (Throwable th) {
            FileLog.v("ServerKeyManager", "decrypt message completed");
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final synchronized void b() {
        this.a.removeValue("server_key_manager_private_data").removeValue("server_key_manager_public_data").commit();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final synchronized String a() {
        String value = this.a.getValue("server_key_manager_private_data");
        String value2 = this.a.getValue("server_key_manager_public_data");
        String str = value2;
        if (TextUtils.isEmpty(value2) || TextUtils.isEmpty(value)) {
            try {
                FileLog.v("ServerKeyManager", "key generation started");
                PRNGFixes.apply();
                KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
                keyPairGenerator.initialize(2048);
                KeyPair generateKeyPair = keyPairGenerator.generateKeyPair();
                FileLog.v("ServerKeyManager", "key generation completed");
                String base64String = Utils.getBase64String(generateKeyPair.getPrivate().getEncoded());
                str = Utils.getBase64String(generateKeyPair.getPublic().getEncoded());
                this.a.putValue("server_key_manager_private_data", base64String).putValue("server_key_manager_public_data", str).commit();
            } catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException e) {
                FileLog.e("ServerKeyManager", "failed to generate key pair", e);
                return null;
            }
        }
        return str;
    }

    private static PrivateKey a(@NonNull String str) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] decodeBase64FromString = Utils.decodeBase64FromString(str);
        if (decodeBase64FromString != null) {
            return KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decodeBase64FromString));
        }
        throw new IllegalStateException("Failed to extract encoded key");
    }
}
