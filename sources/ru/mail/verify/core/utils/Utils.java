package ru.mail.verify.core.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.util.AtomicFile;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.Locale;
import java.util.TimeZone;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import ru.mail.libverify.R;
import ru.mail.verify.core.storage.InstanceConfig;
import ru.mail.verify.core.utils.InstallTime;
import ru.mail.verify.core.utils.bouncycastle.SHA256Digest;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/utils/Utils.class */
public class Utils {
    public static void safeClose(Cursor obj) {
        if (obj == null || obj.isClosed()) {
            return;
        }
        obj.close();
    }

    public static String stringToSHA256(@NonNull String str) {
        byte[] bArr;
        String bytesToHexString;
        try {
            byte[] bytes = str.getBytes("UTF-8");
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                messageDigest.reset();
                messageDigest.update(bytes);
                bytesToHexString = bytesToHexString(messageDigest.digest());
            } catch (NoSuchAlgorithmException e) {
                FileLog.e("Utils", "stringToSHA256", e);
                bytesToHexString = bytesToHexString(SHA256Digest.getDigest(bArr));
            }
            return bytesToHexString;
        } catch (UnsupportedEncodingException unused) {
            return str;
        }
    }

    public static boolean checkFileMD5(@NonNull File file, @NonNull String hash) {
        FileInputStream fileInputStream = null;
        try {
            try {
                FileInputStream fileInputStream2 = new FileInputStream(file);
                try {
                    int length = (int) file.length();
                    byte[] bArr = new byte[length];
                    if (fileInputStream2.read(bArr) != file.length()) {
                        try {
                            fileInputStream2.close();
                        } catch (IOException unused) {
                        }
                        return false;
                    }
                    try {
                        fileInputStream2.close();
                    } catch (IOException unused2) {
                    }
                    try {
                        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                        messageDigest.reset();
                        messageDigest.update(bArr);
                        return TextUtils.equals(bytesToHexString(messageDigest.digest()), hash);
                    } catch (NoSuchAlgorithmException e) {
                        FileLog.e("Utils", "checkFileMD5", e);
                        return TextUtils.equals(bytesToHexString(MD5.digest(bArr, length)), hash);
                    }
                } catch (IOException e2) {
                    e = e2;
                    fileInputStream = fileInputStream2;
                    FileInputStream fileInputStream3 = fileInputStream;
                    FileLog.e("Utils", "checkFileMD5", e);
                    if (fileInputStream3 != null) {
                        try {
                            fileInputStream.close();
                        } catch (IOException unused3) {
                        }
                    }
                    return false;
                } catch (Throwable th) {
                    th = th;
                    fileInputStream = fileInputStream2;
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (IOException unused4) {
                        }
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException e3) {
            e = e3;
        }
    }

    public static String stringToMD5(@NonNull String str) {
        String digestHex;
        try {
            byte[] bytes = str.getBytes("UTF-8");
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                messageDigest.reset();
                messageDigest.update(bytes);
                digestHex = bytesToHexString(messageDigest.digest());
            } catch (NoSuchAlgorithmException e) {
                FileLog.e("Utils", "stringToMD5", e);
                try {
                    digestHex = MD5.digestHex(str);
                } catch (UnsupportedEncodingException unused) {
                    return str;
                }
            }
            return digestHex;
        } catch (UnsupportedEncodingException unused2) {
            return str;
        }
    }

    public static String bytesToHexString(byte[] source) {
        StringBuilder sb = new StringBuilder();
        for (byte b : source) {
            int i = b & 255;
            if (i < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(i));
        }
        return sb.toString();
    }

    public static String getHexString(String source) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < source.length(); i++) {
            sb.append(Integer.toHexString(source.charAt(i)));
        }
        return sb.toString();
    }

    public static SecretKey generateKey() throws NoSuchAlgorithmException {
        PRNGFixes.apply();
        SecureRandom secureRandom = new SecureRandom();
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256, secureRandom);
        return keyGenerator.generateKey();
    }

    public static String bundleToString(Bundle bundle) {
        if (bundle == null) {
            return "[null]";
        }
        StringBuilder sb = new StringBuilder();
        for (String str : bundle.keySet()) {
            Object obj = bundle.get(str);
            if (str != null && obj != null) {
                if (sb.length() != 0) {
                    sb.append(", ");
                }
                sb.append(str).append("='").append(obj).append("'");
            }
        }
        String sb2 = sb.toString();
        String str2 = sb2;
        if (TextUtils.isEmpty(sb2)) {
            str2 = "[empty]";
        }
        return str2;
    }

    public static int getAppVersion(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            FileLog.e("Utils", "Could not get package name: " + e);
            throw new RuntimeException("Could not get package name");
        }
    }

    public static boolean isNumeric(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    public static int countEqualPhoneDigitsFromEnd(@NonNull String phoneNumberLeft, @NonNull String phoneNumberRight) {
        char c;
        char c2;
        int length = phoneNumberLeft.length() - 1;
        int length2 = phoneNumberRight.length() - 1;
        int i = 0;
        while (length >= 0 && length2 >= 0) {
            char charAt = phoneNumberLeft.charAt(length);
            while (true) {
                c = charAt;
                if (length <= 0 || Character.isDigit(c)) {
                    break;
                }
                int i2 = length - 1;
                length = i2;
                charAt = phoneNumberLeft.charAt(i2);
            }
            length--;
            char charAt2 = phoneNumberRight.charAt(length2);
            while (true) {
                c2 = charAt2;
                if (length2 <= 0 || Character.isDigit(c2)) {
                    break;
                }
                int i3 = length2 - 1;
                length2 = i3;
                charAt2 = phoneNumberRight.charAt(i3);
            }
            length2--;
            if (c != c2 || !Character.isDigit(c)) {
                break;
            }
            i++;
        }
        int i4 = i;
        FileLog.v("Utils", "equal digits from end %s %s = %d", phoneNumberLeft, phoneNumberRight, Integer.valueOf(i));
        return i4;
    }

    public static String normalizePhoneDigits(@NonNull String phoneNumber) {
        char[] charArray;
        StringBuilder sb = new StringBuilder(phoneNumber.length());
        for (char c : phoneNumber.toCharArray()) {
            if (Character.isDigit(c)) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String getBase64String(@NonNull String input) {
        try {
            return Base64.encodeToString(input.getBytes("UTF-8"), 2);
        } catch (UnsupportedEncodingException unused) {
            return input;
        }
    }

    public static byte[] decodeBase64FromString(@NonNull String input) {
        try {
            return Base64.decode(input.getBytes("UTF-8"), 2);
        } catch (UnsupportedEncodingException unused) {
            return null;
        }
    }

    public static Locale getCurrentLocale() {
        return Locale.getDefault();
    }

    public static String getCurrentLanguage() {
        return Locale.getDefault().getLanguage();
    }

    public static String getCurrentLocaleUnixId() {
        return getLocaleUnixId(Locale.getDefault());
    }

    public static String getLocaleUnixId(@NonNull Locale locale) {
        String language = locale.getLanguage();
        String country = locale.getCountry();
        return TextUtils.isEmpty(language) ? "en_US" : TextUtils.isEmpty(country) ? language : language + '_' + country;
    }

    public static Locale getLocaleFromUnixId(@NonNull String localUnixId) {
        if (TextUtils.isEmpty(localUnixId)) {
            return Locale.getDefault();
        }
        String str = "";
        int indexOf = localUnixId.indexOf(95);
        if (indexOf >= 0) {
            localUnixId = localUnixId.substring(0, indexOf);
            str = localUnixId.substring(indexOf + 1);
        }
        return new Locale(localUnixId.length() == 2 ? localUnixId.toLowerCase(Locale.US) : "", str.length() == 2 ? str.toUpperCase(Locale.US) : "");
    }

    public static String getFullDeviceName(@NonNull String manufacturer, @NonNull String model) {
        return model.startsWith(manufacturer) ? a(model) : manufacturer.equalsIgnoreCase("HTC") ? "HTC ".concat(model) : a(manufacturer) + " " + model;
    }

    private static String a(String str) {
        char[] charArray;
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        boolean z = true;
        String str2 = "";
        for (char c : str.toCharArray()) {
            if (z && Character.isLetter(c)) {
                str2 = str2 + Character.toUpperCase(c);
                z = false;
            } else {
                if (Character.isWhitespace(c)) {
                    z = true;
                }
                str2 = str2 + c;
            }
        }
        return str2;
    }

    public static File getInstallationDir(@NonNull Context context) {
        return context.getNoBackupFilesDir();
    }

    public static String getHiddenPhoneNumber(@NonNull String phone) {
        if (phone.length() <= 4) {
            return phone;
        }
        StringBuilder sb = new StringBuilder(phone.length());
        int length = phone.length() - 4;
        for (int i = 0; i < length; i++) {
            sb.append('*');
        }
        sb.append(phone.substring(length));
        return sb.toString();
    }

    public static boolean hasSelfPermission(@NonNull Context context, @NonNull String permission) {
        try {
            return ContextCompat.checkSelfPermission(context, permission) == 0;
        } catch (Exception unused) {
            FileLog.e("Utils", "Failed to check self permission %s", permission);
            return false;
        }
    }

    public static boolean hasProxy(@NonNull Context context) {
        try {
            String property = System.getProperty("http.proxyHost");
            FileLog.d("Utils", "proxy host %s", property);
            return !TextUtils.isEmpty(property);
        } catch (Throwable th) {
            FileLog.e("Utils", "Failed to check proxy settings", th);
            return false;
        }
    }

    public static String getSystemId(@NonNull Context context) {
        String string = Settings.Secure.getString(context.getContentResolver(), "android_id");
        return TextUtils.isEmpty(string) ? System.getProperty("ro.serialno") : string;
    }

    public static int compareInt(int lhs, int rhs) {
        if (lhs < rhs) {
            return -1;
        }
        return lhs == rhs ? 0 : 1;
    }

    public static int compareLong(long lhs, long rhs) {
        if (lhs < rhs) {
            return -1;
        }
        return lhs == rhs ? 0 : 1;
    }

    public static String readFile(@NonNull File file) throws IOException {
        RandomAccessFile randomAccessFile;
        RandomAccessFile randomAccessFile2 = null;
        try {
            randomAccessFile = new RandomAccessFile(file, "r");
        } catch (Throwable th) {
            th = th;
        }
        try {
            byte[] bArr = new byte[(int) randomAccessFile.length()];
            randomAccessFile.readFully(bArr);
            String str = new String(bArr, "UTF-8");
            try {
                randomAccessFile.close();
            } catch (IOException e) {
                FileLog.e("Utils", "failed to close file", e);
            }
            return str;
        } catch (Throwable th2) {
            th = th2;
            randomAccessFile2 = randomAccessFile;
            if (randomAccessFile2 != null) {
                try {
                    randomAccessFile2.close();
                } catch (IOException e2) {
                    FileLog.e("Utils", "failed to close file", e2);
                }
            }
            throw th;
        }
    }

    public static void writeFile(@NonNull File file, @NonNull String data) throws IOException {
        FileOutputStream fileOutputStream = null;
        try {
            FileOutputStream fileOutputStream2 = new FileOutputStream(file);
            try {
                fileOutputStream2.write(data.getBytes("UTF-8"));
                fileOutputStream2.flush();
                try {
                    fileOutputStream2.close();
                } catch (IOException e) {
                    FileLog.e("Utils", "failed to close file", e);
                }
            } catch (Throwable th) {
                th = th;
                fileOutputStream = fileOutputStream2;
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e2) {
                        FileLog.e("Utils", "failed to close file", e2);
                    }
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static String readAtomicTextFile(@NonNull File sourceFile) throws IOException {
        return new String(new AtomicFile(sourceFile).readFully(), "UTF-8");
    }

    public static void writeAtomicTextFile(@NonNull String data, @NonNull File sourceFile) throws IOException {
        AtomicFile atomicFile = new AtomicFile(sourceFile);
        FileOutputStream fileOutputStream = null;
        try {
            FileOutputStream startWrite = atomicFile.startWrite();
            fileOutputStream = startWrite;
            startWrite.write(data.getBytes("UTF-8"));
            atomicFile.finishWrite(startWrite);
        } catch (IOException e) {
            if (fileOutputStream != null) {
                atomicFile.failWrite(fileOutputStream);
            }
            throw e;
        }
    }

    public static SSLContext getSslContextFromResource(@NonNull Context context, @NonNull String resource) throws IOException {
        try {
            return a(context, resource);
        } catch (Throwable th) {
            throw new IOException(th);
        }
    }

    @Nullable
    public static Boolean isVoiceCapable(@NonNull Context context) {
        try {
            if (Build.VERSION.SDK_INT >= 22) {
                return Boolean.valueOf(((TelephonyManager) context.getSystemService(InstanceConfig.DEVICE_TYPE_PHONE)).isVoiceCapable());
            }
            int identifier = Resources.getSystem().getIdentifier("config_voice_capable", "bool", "android");
            if (identifier == 0) {
                return null;
            }
            return Boolean.valueOf(context.getResources().getBoolean(identifier));
        } catch (Throwable unused) {
            FileLog.e("Utils", "failed to get voice capable property");
            return null;
        }
    }

    @Nullable
    public static Boolean isRoaming(@NonNull Context context) {
        try {
            return Boolean.valueOf(((TelephonyManager) context.getSystemService(InstanceConfig.DEVICE_TYPE_PHONE)).isNetworkRoaming());
        } catch (Throwable unused) {
            FileLog.e("Utils", "failed to detect roaming");
            return null;
        }
    }

    @Nullable
    public static Integer getBatteryLevel(@NonNull Context context) {
        try {
            return Integer.valueOf(((BatteryManager) context.getSystemService("batterymanager")).getIntProperty(4));
        } catch (Throwable unused) {
            FileLog.e("Utils", "failed to get battery status");
            return null;
        }
    }

    public static void unzip(File zipFile, File targetDirectory) throws IOException {
        unzip(new FileInputStream(zipFile), targetDirectory);
    }

    @NonNull
    public static byte[] zipData(@NonNull byte[] data) throws IOException {
        if (data.length == 0) {
            return new byte[0];
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        GZIPOutputStream gZIPOutputStream = null;
        try {
            GZIPOutputStream gZIPOutputStream2 = new GZIPOutputStream(byteArrayOutputStream);
            try {
                gZIPOutputStream2.write(data);
                gZIPOutputStream2.close();
                byteArrayOutputStream.close();
                return byteArrayOutputStream.toByteArray();
            } catch (IOException e) {
                e = e;
                gZIPOutputStream = gZIPOutputStream2;
                if (gZIPOutputStream != null) {
                    gZIPOutputStream.close();
                }
                IOException iOException = e;
                byteArrayOutputStream.close();
                throw iOException;
            }
        } catch (IOException e2) {
            e = e2;
        }
    }

    @NonNull
    public static String unzipData(@NonNull byte[] data) throws IOException {
        if (data.length == 0) {
            return "";
        }
        if (!isCompressed(data)) {
            return new String(data, "UTF-8");
        }
        StringBuilder sb = new StringBuilder(data.length);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
        GZIPInputStream gZIPInputStream = null;
        BufferedReader bufferedReader = null;
        try {
            GZIPInputStream gZIPInputStream2 = new GZIPInputStream(byteArrayInputStream);
            try {
                BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(gZIPInputStream2, "UTF-8"));
                while (true) {
                    try {
                        String readLine = bufferedReader2.readLine();
                        if (readLine == null) {
                            return sb.toString();
                        }
                        sb.append(readLine);
                    } catch (IOException e) {
                        e = e;
                        gZIPInputStream = gZIPInputStream2;
                        bufferedReader = bufferedReader2;
                        if (bufferedReader != null) {
                            bufferedReader.close();
                        }
                        if (gZIPInputStream != null) {
                            gZIPInputStream.close();
                        }
                        throw e;
                    }
                }
            } catch (IOException e2) {
                e = e2;
                gZIPInputStream = gZIPInputStream2;
            }
        } catch (IOException e3) {
            e = e3;
        }
    }

    public static boolean isCompressed(byte[] compressed) {
        return compressed[0] == 31 && compressed[1] == -117;
    }

    public static String getTimeZone() {
        return TimeZone.getDefault().getID();
    }

    public static boolean isTablet(@NonNull Context context) {
        return context.getResources().getBoolean(R.bool.libverify_is_tablet);
    }

    @Nullable
    public static String getStringFromManifest(@NonNull Context context, @NonNull String key) {
        if (TextUtils.isEmpty(key)) {
            throw new IllegalArgumentException("Manifest key must be not empty");
        }
        try {
            return context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData.getString(key);
        } catch (Throwable th) {
            FileLog.e("Utils", "Failed to load meta-data", th);
            return null;
        }
    }

    @NonNull
    public static InstallTime getInstallTime(Context context, long defaultTime) {
        try {
            return a(context, defaultTime);
        } catch (Throwable th) {
            FileLog.e("Utils", "Exception on install time", th);
            return new InstallTime(InstallTime.Type.DEFAULT_TIME, defaultTime);
        }
    }

    public static Integer getResourceColor(@NonNull Context context, @Nullable Integer color, int defaultResId) {
        if (color == null) {
            String string = context.getString(defaultResId);
            if (!TextUtils.isEmpty(string)) {
                int identifier = context.getResources().getIdentifier(string, null, context.getPackageName());
                if (identifier > 0) {
                    color = Integer.valueOf(ContextCompat.getColor(context, identifier));
                } else {
                    FileLog.e("Utils", "Color id %d not found for color name (from resources) %s", Integer.valueOf(identifier), string);
                }
            }
        }
        return color;
    }

    public static String getBase64String(byte[] input) {
        return Base64.encodeToString(input, 2);
    }

    public static boolean hasSelfPermission(@NonNull Context context, @NonNull String[] permissions) {
        try {
            for (String str : permissions) {
                if (ContextCompat.checkSelfPermission(context, str) != 0) {
                    return false;
                }
            }
            return true;
        } catch (Exception unused) {
            FileLog.e("Utils", "Failed to check self permission %s", Arrays.toString(permissions));
            return false;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x006f, code lost:
        ru.mail.verify.core.utils.FileLog.e("Utils", "Failed to ensure directory: " + r13.getAbsolutePath());
        r0 = new java.io.FileNotFoundException("Failed to ensure directory");
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0096, code lost:
        throw r0;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.util.zip.ZipInputStream, java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r0v1 */
    /* JADX WARN: Type inference failed for: r0v2, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v25, types: [java.lang.Throwable, java.io.FileNotFoundException] */
    /* JADX WARN: Type inference failed for: r0v3, types: [java.util.zip.ZipInputStream] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void unzip(java.io.InputStream r8, java.io.File r9) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 234
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: ru.mail.verify.core.utils.Utils.unzip(java.io.InputStream, java.io.File):void");
    }

    @NonNull
    private static InstallTime a(Context context, long j) {
        long currentTimeMillis = System.currentTimeMillis();
        PackageManager packageManager = context.getPackageManager();
        Context applicationContext = context.getApplicationContext();
        if (applicationContext == null) {
            FileLog.e("Utils", "Not found application context");
            return new InstallTime(InstallTime.Type.DEFAULT_TIME, j);
        }
        String packageName = applicationContext.getPackageName();
        if (packageManager == null) {
            FileLog.e("Utils", "Packagemanager can't be null. Return current time as install time");
            return new InstallTime(InstallTime.Type.DEFAULT_TIME, j);
        }
        long j2 = 0;
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            if (packageInfo == null) {
                FileLog.e("Utils", "Packageinfo can't be null. Return current time as install time");
            } else {
                long j3 = packageInfo.firstInstallTime;
                if (j3 <= 1222113600000L || j3 >= currentTimeMillis) {
                    Object[] objArr = new Object[1];
                    objArr[0] = Long.valueOf(j3);
                    FileLog.e("Utils", "Install time from package info invalid %d", objArr);
                } else {
                    FileLog.v("Utils", "Use install time from file time creation");
                    j2 = j3;
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            FileLog.e("Utils", "Not found package info about first install time", e);
        }
        if (j2 > 0) {
            FileLog.v("Utils", "Use install time from package");
            return new InstallTime(InstallTime.Type.FROM_APPLICATION_INFO, j2);
        }
        FileLog.v("Utils", "Not found install time in package info. Get install time from apk file created");
        long j4 = 0;
        long j5 = 0;
        try {
            File file = new File(packageManager.getApplicationInfo(packageName, 0).sourceDir);
            if (file.exists()) {
                j5 = file.lastModified();
            } else {
                FileLog.e("Utils", "Application installation file not found");
            }
            if (j5 <= 1222113600000L || j5 >= currentTimeMillis) {
                FileLog.e("Utils", "Install time from last modified file invalid");
            } else {
                j4 = j5;
            }
        } catch (Exception e2) {
            FileLog.e("Utils", "Not found application info", e2);
        }
        if (j4 > 0) {
            FileLog.v("Utils", "Use install time from file time creation");
            return new InstallTime(InstallTime.Type.FROM_APPLICATION_FILE, j4);
        }
        return new InstallTime(InstallTime.Type.DEFAULT_TIME, j);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v2, types: [java.lang.Throwable, java.io.BufferedInputStream, java.io.InputStream] */
    private static SSLContext a(@NonNull Context context, @NonNull String str) throws CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        ?? bufferedInputStream = new BufferedInputStream(context.getResources().openRawResource(context.getResources().getIdentifier(str, "raw", context.getPackageName())));
        try {
            Certificate generateCertificate = certificateFactory.generateCertificate(bufferedInputStream);
            bufferedInputStream.close();
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", generateCertificate);
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            SSLContext sSLContext = SSLContext.getInstance("TLS");
            sSLContext.init(null, trustManagerFactory.getTrustManagers(), null);
            return sSLContext;
        } catch (Throwable th) {
            th.close();
            throw bufferedInputStream;
        }
    }
}
