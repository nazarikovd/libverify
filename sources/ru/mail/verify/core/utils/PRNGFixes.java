package ru.mail.verify.core.utils;

import android.os.Build;
import android.os.Process;
import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.SecureRandomSpi;
import java.security.Security;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/utils/PRNGFixes.class */
public final class PRNGFixes {
    private static final byte[] a;

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/utils/PRNGFixes$LinuxPRNGSecureRandom.class */
    public static class LinuxPRNGSecureRandom extends SecureRandomSpi {
        private static final File b = new File("/dev/urandom");
        private static final Object c = new Object();
        private static DataInputStream d;
        private static FileOutputStream e;
        private boolean a;

        private static DataInputStream a() {
            DataInputStream dataInputStream;
            synchronized (c) {
                if (d == null) {
                    try {
                        d = new DataInputStream(new FileInputStream(b));
                    } catch (IOException e2) {
                        throw new SecurityException("Failed to open " + b + " for reading", e2);
                    }
                }
                dataInputStream = d;
            }
            return dataInputStream;
        }

        private static OutputStream b() throws IOException {
            FileOutputStream fileOutputStream;
            synchronized (c) {
                if (e == null) {
                    e = new FileOutputStream(b);
                }
                fileOutputStream = e;
            }
            return fileOutputStream;
        }

        @Override // java.security.SecureRandomSpi
        protected void engineSetSeed(byte[] bytes) {
            OutputStream b2;
            try {
                try {
                    synchronized (c) {
                        b2 = b();
                    }
                    b2.write(bytes);
                    b2.flush();
                } catch (IOException unused) {
                    Log.w("PRNGFixes", "Failed to mix seed into " + b);
                }
            } finally {
                this.a = true;
            }
        }

        @Override // java.security.SecureRandomSpi
        protected void engineNextBytes(byte[] bytes) {
            DataInputStream a;
            if (!this.a) {
                engineSetSeed(PRNGFixes.b());
            }
            try {
                synchronized (c) {
                    a = a();
                }
                synchronized (a) {
                    a.readFully(bytes);
                }
            } catch (IOException e2) {
                throw new SecurityException("Failed to read from " + b, e2);
            }
        }

        @Override // java.security.SecureRandomSpi
        protected byte[] engineGenerateSeed(int size) {
            byte[] bArr = new byte[size];
            engineNextBytes(bArr);
            return bArr;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/utils/PRNGFixes$a.class */
    public static class a extends Provider {
        public a() {
            super("LinuxPRNG", 1.0d, "A Linux-specific random number provider that uses /dev/urandom");
            put("SecureRandom.SHA1PRNG", LinuxPRNGSecureRandom.class.getName());
            put("SecureRandom.SHA1PRNG ImplementedIn", "Software");
        }
    }

    private PRNGFixes() {
    }

    public static void apply() {
        try {
            a();
            c();
        } catch (Exception e) {
            FileLog.e("PRNGFixes", "failed to apply fixes", e);
        }
    }

    private static void a() throws SecurityException {
        int i = Build.VERSION.SDK_INT;
        if (i < 16 || i > 18) {
            return;
        }
        try {
            Class<?> cls = Class.forName("org.apache.harmony.xnet.provider.jsse.NativeCrypto");
            Class<?>[] clsArr = new Class[1];
            clsArr[0] = byte[].class;
            Method method = cls.getMethod("RAND_seed", clsArr);
            Object[] objArr = new Object[1];
            objArr[0] = b();
            method.invoke(null, objArr);
            Class<?> cls2 = Class.forName("org.apache.harmony.xnet.provider.jsse.NativeCrypto");
            Class<?>[] clsArr2 = new Class[2];
            clsArr2[0] = String.class;
            clsArr2[1] = Long.TYPE;
            Method method2 = cls2.getMethod("RAND_load_file", clsArr2);
            Object[] objArr2 = new Object[2];
            objArr2[0] = "/dev/urandom";
            objArr2[1] = 1024;
            int intValue = ((Integer) method2.invoke(null, objArr2)).intValue();
            if (intValue == 1024) {
                return;
            }
            FileLog.e("PRNGFixes", "Unexpected number of bytes read from Linux PRNG: " + intValue);
            throw new IOException("Unexpected number of bytes read from Linux PRNG");
        } catch (Exception e) {
            throw new SecurityException("Failed to seed OpenSSL PRNG", e);
        }
    }

    private static void c() throws SecurityException {
        if (Build.VERSION.SDK_INT > 18) {
            return;
        }
        Provider[] providers = Security.getProviders("SecureRandom.SHA1PRNG");
        if (providers == null || providers.length < 1 || !a.class.equals(providers[0].getClass())) {
            Security.insertProviderAt(new a(), 1);
        }
        SecureRandom secureRandom = new SecureRandom();
        if (!a.class.equals(secureRandom.getProvider().getClass())) {
            throw new SecurityException("new SecureRandom() backed by wrong Provider: " + secureRandom.getProvider().getClass());
        }
        try {
            SecureRandom secureRandom2 = SecureRandom.getInstance("SHA1PRNG");
            if (!a.class.equals(secureRandom2.getProvider().getClass())) {
                throw new SecurityException("SecureRandom.getInstance(\"SHA1PRNG\") backed by wrong Provider: " + secureRandom2.getProvider().getClass());
            }
        } catch (NoSuchAlgorithmException e) {
            throw new SecurityException("SHA1PRNG not available", e);
        }
    }

    private static byte[] b() {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            dataOutputStream.writeLong(System.currentTimeMillis());
            dataOutputStream.writeLong(System.nanoTime());
            dataOutputStream.writeInt(Process.myPid());
            dataOutputStream.writeInt(Process.myUid());
            dataOutputStream.write(a);
            dataOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new SecurityException("Failed to generate seed", e);
        }
    }

    static {
        String str;
        StringBuilder sb = new StringBuilder();
        String str2 = Build.FINGERPRINT;
        if (str2 != null) {
            sb.append(str2);
        }
        try {
            str = (String) Build.class.getField("SERIAL").get(null);
        } catch (Exception unused) {
            str = null;
        }
        if (str != null) {
            sb.append(str);
        }
        try {
            a = sb.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException unused2) {
            throw new RuntimeException("UTF-8 encoding not supported");
        }
    }
}
