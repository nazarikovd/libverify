package ru.mail.libverify.storage;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.security.KeyPairGeneratorSpec;
import android.security.keystore.KeyGenParameterSpec;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.ProviderException;
import java.security.UnrecoverableKeyException;
import java.util.GregorianCalendar;
import java.util.Locale;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.security.auth.x500.X500Principal;
import ru.mail.verify.core.storage.Installation;
import ru.mail.verify.core.storage.InstallationHelper;
import ru.mail.verify.core.utils.DebugUtils;
import ru.mail.verify.core.utils.FileLog;
import ru.mail.verify.core.utils.Utils;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/storage/f.class */
final class f extends Installation {
    private static String a;
    private static g b;
    private static volatile String c;
    public static final /* synthetic */ int d = 0;

    f() {
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [java.lang.Throwable, java.lang.Class<ru.mail.libverify.storage.f>] */
    @WorkerThread
    public static String d(@NonNull Context context) {
        Context context2;
        String str;
        if (c == null) {
            synchronized (f.class) {
                if (c == null) {
                    InstallationHelper installationHelper = Installation.idHelper;
                    installationHelper.setIdState(InstallationHelper.IDState.INITIALIZING);
                    File installationFile = Installation.getInstallationFile(context);
                    if (installationFile.exists()) {
                        RandomAccessFile randomAccessFile = null;
                        try {
                            RandomAccessFile randomAccessFile2 = new RandomAccessFile(installationFile, "r");
                            try {
                                byte[] bArr = new byte[(int) randomAccessFile2.length()];
                                randomAccessFile2.readFully(bArr);
                                String a2 = a(context, bArr);
                                randomAccessFile2.close();
                                c = a2;
                                if (TextUtils.isEmpty(c)) {
                                    context2 = context;
                                    e(context2);
                                    c = InstallationHelper.generateId();
                                    str = c;
                                }
                                installationHelper.setIdState(InstallationHelper.IDState.HAS_INSTALLATION);
                            } catch (Throwable th) {
                                th = th;
                                randomAccessFile = randomAccessFile2;
                                if (randomAccessFile != null) {
                                    randomAccessFile.close();
                                }
                                throw th;
                            }
                        } catch (Throwable th2) {
                            th = th2;
                        }
                    } else {
                        context2 = context;
                        c = InstallationHelper.generateId();
                        str = c;
                    }
                    a(context2, str, installationFile);
                    installationHelper.setIdState(InstallationHelper.IDState.HAS_INSTALLATION);
                }
            }
        }
        return c;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1 */
    /* JADX WARN: Type inference failed for: r0v2, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v3, types: [android.content.Context] */
    /* JADX WARN: Type inference failed for: r0v9, types: [android.content.Context] */
    public static synchronized void e(@NonNull Context context) {
        Context context2 = context;
        try {
            InstallationHelper installationHelper = Installation.idHelper;
            installationHelper.setIdState(InstallationHelper.IDState.RESETTING);
            c = null;
            File installationFile = Installation.getInstallationFile(context2);
            if (!installationFile.exists()) {
                installationHelper.setIdState(InstallationHelper.IDState.NO_INSTALLATION);
                a(context).a(context, false);
                return;
            }
            boolean delete = installationFile.delete();
            boolean z = delete;
            if (!delete) {
                File file = new File(Utils.getInstallationDir(context), "VERIFY_INSTALLATION_TMP");
                boolean renameTo = installationFile.renameTo(file);
                z = renameTo;
                if (renameTo) {
                    z = file.delete();
                }
            }
            context2 = context;
            FileLog.d("Installation", "installation file delete result " + z);
            installationHelper.setIdState(InstallationHelper.IDState.NO_INSTALLATION);
            a(context2).a(context, false);
        } catch (Throwable th) {
            try {
                context2 = context;
                FileLog.e("Installation", "failed to reset installation file", th);
                Installation.idHelper.setIdState(InstallationHelper.IDState.NO_INSTALLATION);
                a(context2).a(context, false);
            } catch (Throwable th2) {
                Installation.idHelper.setIdState(InstallationHelper.IDState.NO_INSTALLATION);
                a(th2).a(context, false);
                throw context2;
            }
        }
    }

    private static String c(@NonNull Context context) {
        if (a == null) {
            a = String.format(Locale.US, "%s_libverify_installation_key", context.getPackageName());
        }
        return a;
    }

    @TargetApi(18)
    private static KeyPair b(@NonNull Context context) throws Exception {
        KeyStore.PrivateKeyEntry privateKeyEntry;
        KeyPair generateKeyPair;
        int i = 0;
        Exception e = null;
        while (i < 5) {
            try {
                String c2 = c(context);
                KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
                keyStore.load(null);
                privateKeyEntry = keyStore.getEntry(c2, null);
            } catch (NullPointerException e2) {
                FileLog.e("Installation", "error raised during key search", e2);
                try {
                    FileLog.v("Installation", "try to remove key");
                    String c3 = c(context);
                    KeyStore keyStore2 = KeyStore.getInstance("AndroidKeyStore");
                    keyStore2.load(null);
                    keyStore2.deleteEntry(c3);
                } catch (Throwable th) {
                    FileLog.e("Installation", "failed to clear key", th);
                }
                privateKeyEntry = null;
            } catch (Exception e3) {
                e = e3;
                FileLog.e("Installation", e, "error raised during key search on attempt %s", Integer.valueOf(i));
                i++;
                try {
                    Object[] objArr = new Object[1];
                    objArr[0] = Integer.valueOf(i);
                    FileLog.d("Installation", "failed to get extract key during attempt %s", objArr);
                    synchronized (f.class) {
                        f.class.wait(i * i * 100);
                    }
                } catch (InterruptedException e4) {
                    FileLog.e("Installation", e4, "failed to wait timeout before next attempt", new Object[0]);
                    privateKeyEntry = null;
                }
            }
            if (privateKeyEntry == null) {
                Locale locale = Locale.US;
                GregorianCalendar gregorianCalendar = new GregorianCalendar(locale);
                GregorianCalendar gregorianCalendar2 = new GregorianCalendar(locale);
                gregorianCalendar2.add(1, 5);
                if (Build.VERSION.SDK_INT >= 23) {
                    KeyGenParameterSpec build = new KeyGenParameterSpec.Builder(c(context), 3).setDigests("SHA-256", "SHA-512").setSignaturePaddings("PKCS1").setEncryptionPaddings("PKCS1Padding").setKeyValidityStart(gregorianCalendar.getTime()).setKeyValidityEnd(gregorianCalendar2.getTime()).build();
                    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA", "AndroidKeyStore");
                    keyPairGenerator.initialize(build);
                    generateKeyPair = keyPairGenerator.generateKeyPair();
                } else {
                    KeyPairGeneratorSpec build2 = new KeyPairGeneratorSpec.Builder(context).setAlias(c(context)).setSubject(new X500Principal("CN=" + c(context))).setSerialNumber(BigInteger.valueOf(1337L)).setStartDate(gregorianCalendar.getTime()).setEndDate(gregorianCalendar2.getTime()).build();
                    KeyPairGenerator keyPairGenerator2 = KeyPairGenerator.getInstance("RSA", "AndroidKeyStore");
                    keyPairGenerator2.initialize(build2);
                    generateKeyPair = keyPairGenerator2.generateKeyPair();
                }
                return generateKeyPair;
            }
            KeyStore.PrivateKeyEntry privateKeyEntry2 = privateKeyEntry;
            return new KeyPair(privateKeyEntry2.getCertificate().getPublicKey(), privateKeyEntry2.getPrivateKey());
        }
        throw e;
    }

    private static String a(@NonNull Context context, @NonNull byte[] bArr) throws UnsupportedEncodingException {
        if (Build.VERSION.SDK_INT < 18 || a(context).a()) {
            return new String(bArr, "UTF-8");
        }
        try {
            KeyPair b2 = b(context);
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
            cipher.init(2, b2.getPrivate());
            return new String(cipher.doFinal(bArr), "UTF-8");
        } catch (IllegalArgumentException e) {
            FileLog.e("Installation", "Failed to decrypt installation id (unsupported system locale)", e);
            return null;
        } catch (IllegalStateException e2) {
            FileLog.e("Installation", "Failed to decrypt installation id (credentials storage locked)", e2);
            return null;
        } catch (InvalidKeyException e3) {
            e = e3;
            FileLog.e("Installation", "Failed to decrypt installation id, no such algorithm", e);
            return null;
        } catch (NoSuchAlgorithmException e4) {
            e = e4;
            FileLog.e("Installation", "Failed to decrypt installation id, no such algorithm", e);
            return null;
        } catch (UnrecoverableKeyException e5) {
            FileLog.e("Installation", "Failed to decrypt installation id (private key error)", e5);
            return null;
        } catch (BadPaddingException e6) {
            e = e6;
            FileLog.e("Installation", "Failed to decrypt installation id, bad key", e);
            return null;
        } catch (IllegalBlockSizeException e7) {
            e = e7;
            FileLog.e("Installation", "Failed to decrypt installation id, bad key", e);
            return null;
        } catch (NoSuchPaddingException e8) {
            e = e8;
            FileLog.e("Installation", "Failed to decrypt installation id, no such algorithm", e);
            return null;
        } catch (Throwable th) {
            DebugUtils.safeThrow("Installation", "Failed to decrypt installation id", th);
            return null;
        }
    }

    static {
        new b();
    }

    private static byte[] a(@NonNull Context context, @NonNull String str) throws UnsupportedEncodingException {
        byte[] bytes = str.getBytes("UTF-8");
        if (Build.VERSION.SDK_INT < 18 || a(context).a()) {
            return bytes;
        }
        try {
            KeyPair b2 = b(context);
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
            cipher.init(1, b2.getPublic());
            return cipher.doFinal(bytes);
        } catch (IllegalArgumentException e) {
            e = e;
            FileLog.e("Installation", "Failed to encrypt installation id (unsupported system locale)", e);
            a(context).a(context, true);
            return bytes;
        } catch (IllegalStateException e2) {
            FileLog.e("Installation", "Failed to encrypt installation id (credentials storage locked)", e2);
            a(context).a(context, true);
            return bytes;
        } catch (InvalidKeyException e3) {
            e = e3;
            FileLog.e("Installation", "Failed to encrypt installation id, no such algorithm", e);
            a(context).a(context, true);
            return bytes;
        } catch (NoSuchAlgorithmException e4) {
            e = e4;
            FileLog.e("Installation", "Failed to encrypt installation id, no such algorithm", e);
            a(context).a(context, true);
            return bytes;
        } catch (ProviderException e5) {
            e = e5;
            FileLog.e("Installation", "Failed to encrypt installation id (unsupported system locale)", e);
            a(context).a(context, true);
            return bytes;
        } catch (UnrecoverableKeyException e6) {
            FileLog.e("Installation", "Failed to encrypt installation id (private key error)", e6);
            a(context).a(context, true);
            return bytes;
        } catch (BadPaddingException e7) {
            e = e7;
            FileLog.e("Installation", "Failed to encrypt installation id (use plain text one)", e);
            a(context).a(context, true);
            return bytes;
        } catch (IllegalBlockSizeException e8) {
            e = e8;
            FileLog.e("Installation", "Failed to encrypt installation id (use plain text one)", e);
            a(context).a(context, true);
            return bytes;
        } catch (NoSuchPaddingException e9) {
            e = e9;
            FileLog.e("Installation", "Failed to encrypt installation id, no such algorithm", e);
            a(context).a(context, true);
            return bytes;
        } catch (Exception e10) {
            DebugUtils.safeThrow("Installation", "Failed to encrypt installation id (use plain text one)", e10);
            a(context).a(context, true);
            return bytes;
        }
    }

    private static g a(@NonNull Context context) {
        if (b == null) {
            b = new g(context);
        }
        return b;
    }

    private static void a(@NonNull Context context, @NonNull String str, @NonNull File file) throws IOException {
        FileOutputStream fileOutputStream = null;
        try {
            FileOutputStream fileOutputStream2 = new FileOutputStream(file);
            try {
                fileOutputStream2.write(a(context, str));
                fileOutputStream2.flush();
                fileOutputStream2.close();
            } catch (Throwable th) {
                th = th;
                fileOutputStream = fileOutputStream2;
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }
}
