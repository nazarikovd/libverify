package ru.mail.verify.core.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Locale;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/utils/DebugUtils.class */
public class DebugUtils {
    private static volatile ErrorListener a;

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/utils/DebugUtils$ErrorListener.class */
    public interface ErrorListener {
        void onSilentException(@Nullable String str, @NonNull Throwable th);

        void onUnhandledException(@NonNull Throwable th);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/utils/DebugUtils$a.class */
    public class a implements X509TrustManager {
        a() {
        }

        @Override // javax.net.ssl.X509TrustManager
        public final X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        @Override // javax.net.ssl.X509TrustManager
        public final void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) {
        }

        @Override // javax.net.ssl.X509TrustManager
        public final void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) {
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/utils/DebugUtils$b.class */
    public class b implements HostnameVerifier {
        b() {
        }

        @Override // javax.net.ssl.HostnameVerifier
        public final boolean verify(String str, SSLSession sSLSession) {
            return true;
        }
    }

    public static void setListener(@Nullable ErrorListener listener) {
        a = listener;
    }

    public static void safeThrow(@NonNull String tag, @Nullable String message, @NonNull Throwable error) {
        FileLog.e(tag, message, error);
        ErrorListener errorListener = a;
        SmartException smartException = new SmartException(message, error);
        if (errorListener != null) {
            errorListener.onSilentException(message, smartException);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v13, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r0v5, types: [java.lang.Throwable] */
    public static String exceptionStacktraceToString(@NonNull Throwable error, @Nullable Thread thread, @Nullable Integer maxLength) {
        ?? r0 = thread;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            PrintStream printStream = new PrintStream(byteArrayOutputStream, false, "UTF-8");
            if (r0 != 0) {
                try {
                    printStream.append((CharSequence) thread.getName()).append("\n");
                } catch (Exception unused) {
                    printStream.close();
                    return null;
                } catch (Throwable th) {
                    th.close();
                    throw r0;
                }
            }
            error.printStackTrace(printStream);
            String byteArrayOutputStream2 = byteArrayOutputStream.toString("UTF-8");
            if (maxLength == null || byteArrayOutputStream2.length() <= maxLength.intValue()) {
                printStream.close();
                return byteArrayOutputStream2;
            }
            r0 = byteArrayOutputStream2.substring(0, maxLength.intValue());
            printStream.close();
            return r0;
        } catch (UnsupportedEncodingException unused2) {
            return null;
        }
    }

    public static void crashIfDebug(@NonNull Throwable error) {
        ErrorListener errorListener = a;
        if (errorListener != null) {
            errorListener.onUnhandledException(error);
        }
    }

    public static boolean isTrustAllCertificatesEnabled() {
        return false;
    }

    public static void trustAllCertificates() {
        if (isTrustAllCertificatesEnabled()) {
            try {
                TrustManager[] trustManagerArr = new TrustManager[1];
                trustManagerArr[0] = new a();
                SSLContext sSLContext = SSLContext.getInstance("SSL");
                PRNGFixes.apply();
                sSLContext.init(null, trustManagerArr, new SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sSLContext.getSocketFactory());
                HttpsURLConnection.setDefaultHostnameVerifier(new b());
            } catch (Exception e) {
                FileLog.v("DebugUtils", "setTrustAllCertificates", e.toString());
            }
        }
    }

    public static void safeThrow(@NonNull String tag, @NonNull Throwable error, @NonNull String messageFormat, Object... arguments) {
        FileLog.e(tag, error, messageFormat, arguments);
        ErrorListener errorListener = a;
        String format = String.format(Locale.US, messageFormat, arguments);
        SmartException smartException = new SmartException(format, error);
        if (errorListener != null) {
            errorListener.onSilentException(format, smartException);
        }
    }
}
