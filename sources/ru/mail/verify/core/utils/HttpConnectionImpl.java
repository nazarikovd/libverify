package ru.mail.verify.core.utils;

import android.net.Network;
import android.os.Build;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import ru.mail.verify.core.storage.BroadcastManager;
import ru.mail.verify.core.utils.ClientException;
import ru.mail.verify.core.utils.HttpConnection;
import ru.mail.verify.core.utils.NetworkInterceptor;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/utils/HttpConnectionImpl.class */
public class HttpConnectionImpl implements HttpConnection {
    private final String a;
    private final byte[] b;
    private final HttpURLConnection c;
    private final boolean d;
    private final String e;
    private final boolean f;
    private final NetworkInterceptor g;
    private boolean h;
    private boolean i;
    private boolean j;
    private Long k = null;
    private Long l = null;

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/utils/HttpConnectionImpl$a.class */
    static /* synthetic */ class a {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[HttpConnection.Method.values().length];
            a = iArr;
            try {
                iArr[HttpConnection.Method.GET.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[HttpConnection.Method.POST.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[HttpConnection.Method.HEAD.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                a[HttpConnection.Method.PUT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/utils/HttpConnectionImpl$b.class */
    private static class b implements ConnectionBuilder {
        @Nullable
        private HttpURLConnection a;
        private final String b;
        private final SocketFactoryProvider c;
        private final NetworkInterceptor d;
        @Nullable
        private final Network e;
        private byte[] f;
        private String g;
        private boolean h;
        private int i;
        private String j;
        private boolean k;

        private b(@NonNull String str, @Nullable SocketFactoryProvider socketFactoryProvider, @Nullable NetworkInterceptor networkInterceptor, @Nullable Network network) {
            this.b = str;
            this.c = socketFactoryProvider;
            this.d = networkInterceptor;
            this.e = network;
        }

        private HttpURLConnection a() throws ClientException, IOException {
            HttpURLConnection httpURLConnection;
            if (this.a == null) {
                SocketFactoryProvider socketFactoryProvider = this.c;
                SSLSocketFactory sSLFactory = socketFactoryProvider == null ? null : socketFactoryProvider.getSSLFactory(null);
                try {
                    if (TextUtils.isEmpty(this.j) || this.i <= 0) {
                        Network network = this.e;
                        httpURLConnection = network != null ? (HttpURLConnection) network.openConnection(new URL(this.b)) : (HttpURLConnection) new URL(this.b).openConnection();
                    } else {
                        Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(this.j, this.i));
                        if (Build.VERSION.SDK_INT >= 23) {
                            Network network2 = this.e;
                            if (network2 != null) {
                                httpURLConnection = (HttpURLConnection) network2.openConnection(new URL(this.b), proxy);
                            }
                        }
                        httpURLConnection = (HttpURLConnection) new URL(this.b).openConnection(proxy);
                    }
                    this.a = httpURLConnection;
                    if (sSLFactory != null) {
                        ((HttpsURLConnection) this.a).setSSLSocketFactory(sSLFactory);
                    }
                    a().setConnectTimeout(30000);
                    a().setReadTimeout(30000);
                    a().setInstanceFollowRedirects(false);
                } catch (MalformedURLException e) {
                    throw new ClientException(e);
                }
            }
            return this.a;
        }

        @Override // ru.mail.verify.core.utils.ConnectionBuilder
        public final ConnectionBuilder setMethod(HttpConnection.Method method) throws IOException, ClientException {
            HttpURLConnection a = a();
            switch (a.a[method.ordinal()]) {
                case BroadcastManager.FLAG_NETWORK_RECEIVER /* 1 */:
                    a.setRequestMethod("GET");
                    a.setDoInput(true);
                    a.setDoOutput(false);
                    break;
                case 2:
                    a.setRequestMethod("POST");
                    a.setDoInput(true);
                    a.setDoOutput(true);
                    break;
                case 3:
                    a.setRequestMethod("HEAD");
                    a.setDoInput(false);
                    a.setDoOutput(false);
                    break;
                case 4:
                    a.setRequestMethod("PUT");
                    a.setDoInput(true);
                    a.setDoOutput(true);
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported http method");
            }
            return this;
        }

        @Override // ru.mail.verify.core.utils.ConnectionBuilder
        public final ConnectionBuilder setDebugMode() {
            this.k = true;
            return this;
        }

        @Override // ru.mail.verify.core.utils.ConnectionBuilder
        public final ConnectionBuilder setKeepAlive(boolean z) throws IOException, ClientException {
            HttpURLConnection a = a();
            if (z) {
                a.addRequestProperty("Connection", "Keep-Alive");
            } else {
                a.addRequestProperty("Connection", "Close");
            }
            return this;
        }

        @Override // ru.mail.verify.core.utils.ConnectionBuilder
        public final ConnectionBuilder allowRedirects(boolean z) throws IOException, ClientException {
            a().setInstanceFollowRedirects(z);
            return this;
        }

        @Override // ru.mail.verify.core.utils.ConnectionBuilder
        public final ConnectionBuilder setConnectTimeout(int i) throws IOException, ClientException {
            a().setConnectTimeout(i);
            return this;
        }

        @Override // ru.mail.verify.core.utils.ConnectionBuilder
        public final ConnectionBuilder setReadTimeout(int i) throws IOException, ClientException {
            a().setReadTimeout(i);
            return this;
        }

        @Override // ru.mail.verify.core.utils.ConnectionBuilder
        public final ConnectionBuilder addHeader(@NonNull String str, @NonNull String str2) throws IOException, ClientException {
            a().addRequestProperty(str, str2);
            return this;
        }

        @Override // ru.mail.verify.core.utils.ConnectionBuilder
        public final ConnectionBuilder setCheckResponseEncoding(boolean z) {
            this.h = z;
            return this;
        }

        @Override // ru.mail.verify.core.utils.ConnectionBuilder
        public final ConnectionBuilder acceptGzip(boolean z) throws IOException, ClientException {
            if (z) {
                a().addRequestProperty("Accept-Encoding", "gzip");
            }
            return this;
        }

        @Override // ru.mail.verify.core.utils.ConnectionBuilder
        public final ConnectionBuilder setPostUrlData(@NonNull String str, boolean z) throws IOException, ClientException {
            if (!TextUtils.isEmpty(str)) {
                this.f = str.getBytes(StandardCharsets.UTF_8);
                if (this.k) {
                    this.g = str;
                }
                HttpURLConnection a = a();
                a.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                a.setRequestProperty("Charset", "utf-8");
                if (z) {
                    a.addRequestProperty("Content-Encoding", "gzip");
                    this.f = Utils.zipData(this.f);
                }
                NetworkInterceptor networkInterceptor = this.d;
                if (networkInterceptor != null) {
                    networkInterceptor.check(this.b, NetworkInterceptor.NetworkAction.BEFORE_UPLOAD, this.f.length);
                }
                a.setRequestProperty("Content-Length", Integer.toString(this.f.length));
            }
            return this;
        }

        @Override // ru.mail.verify.core.utils.ConnectionBuilder
        public final ConnectionBuilder setPostJsonData(@NonNull byte[] bArr, boolean z) throws IOException, ClientException {
            if (bArr.length != 0) {
                this.f = bArr;
                if (this.k) {
                    this.g = new String(bArr, StandardCharsets.UTF_8);
                }
                HttpURLConnection a = a();
                a.addRequestProperty("Content-Type", "application/json");
                a.setRequestProperty("Charset", "utf-8");
                if (z) {
                    a.addRequestProperty("Content-Encoding", "gzip");
                    this.f = Utils.zipData(this.f);
                }
                NetworkInterceptor networkInterceptor = this.d;
                if (networkInterceptor != null) {
                    networkInterceptor.check(this.b, NetworkInterceptor.NetworkAction.BEFORE_UPLOAD, this.f.length);
                }
                a.setRequestProperty("Content-Length", Integer.toString(this.f.length));
            }
            return this;
        }

        @Override // ru.mail.verify.core.utils.ConnectionBuilder
        public final ConnectionBuilder setSocketFactory(@Nullable SSLSocketFactory sSLSocketFactory) throws IOException, ClientException {
            HttpURLConnection a = a();
            if (a instanceof HttpsURLConnection) {
                SocketFactoryProvider socketFactoryProvider = this.c;
                if (socketFactoryProvider != null) {
                    sSLSocketFactory = socketFactoryProvider.getSSLFactory(sSLSocketFactory);
                }
                ((HttpsURLConnection) a).setSSLSocketFactory(sSLSocketFactory);
            }
            return this;
        }

        @Override // ru.mail.verify.core.utils.ConnectionBuilder
        public final ConnectionBuilder setProxyHost(String str) {
            this.j = str;
            return this;
        }

        @Override // ru.mail.verify.core.utils.ConnectionBuilder
        public final ConnectionBuilder setProxyPort(int i) {
            this.i = i;
            return this;
        }

        @Override // ru.mail.verify.core.utils.ConnectionBuilder
        public final HttpConnection build() throws IOException, ClientException {
            return new HttpConnectionImpl(this.b, this.f, this.g, a(), this.d, this.h, this.k);
        }
    }

    private HttpConnectionImpl(@NonNull String str, @Nullable byte[] bArr, @Nullable String str2, @NonNull HttpURLConnection httpURLConnection, @Nullable NetworkInterceptor networkInterceptor, boolean z, boolean z2) {
        this.a = str;
        this.e = str2;
        this.f = z2;
        this.b = bArr;
        this.c = httpURLConnection;
        this.g = networkInterceptor;
        this.d = z;
    }

    public static ConnectionBuilder getBuilder(@NonNull String url, @Nullable SocketFactoryProvider provider, @Nullable NetworkInterceptor interceptor, @Nullable Network customNetwork) throws IOException {
        return new b(url, provider, interceptor, customNetwork);
    }

    private void b() {
        c();
        try {
            a(this.c.getInputStream());
        } catch (IOException e) {
            FileLog.e("HttpConnection", "emptyAndClose", e);
        }
        try {
            a(this.c.getErrorStream());
        } catch (IOException e2) {
            FileLog.e("HttpConnection", "emptyAndClose", e2);
        }
        d();
        disconnect();
    }

    private void c() {
        String str;
        if (!this.f || this.h) {
            return;
        }
        this.h = true;
        try {
            str = this.c.getRequestMethod();
        } catch (Exception unused) {
            str = null;
        }
        try {
            Object[] objArr = new Object[2];
            objArr[0] = this.a;
            objArr[1] = str;
            FileLog.v("HttpConnection", String.format("\r\nURL: %s\r\nMethod:%s", objArr));
            StringBuilder sb = new StringBuilder();
            for (String str2 : this.c.getRequestProperties().keySet()) {
                sb.append(str2).append(" : ").append(this.c.getRequestProperty(str2)).append('\n');
            }
            FileLog.v("HttpConnection", sb.toString());
        } catch (Exception unused2) {
        }
    }

    private void d() {
        if (!this.f || this.i) {
            return;
        }
        this.i = true;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("contentLength : ").append(this.c.getContentLength()).append('\n');
            for (String str : this.c.getHeaderFields().keySet()) {
                sb.append(str).append(" : ").append(this.c.getHeaderField(str)).append('\n');
            }
            FileLog.v("HttpConnection", sb.toString());
        } catch (Exception unused) {
        }
    }

    private void a() throws ClientException {
        NetworkInterceptor networkInterceptor = this.g;
        if (networkInterceptor == null || this.j) {
            return;
        }
        networkInterceptor.check(this.a, NetworkInterceptor.NetworkAction.BEFORE_DOWNLOAD, 0);
        this.j = true;
    }

    @Override // ru.mail.verify.core.utils.HttpConnection
    public int getResponseCode() throws IOException, ClientException {
        c();
        a();
        if (Thread.interrupted()) {
            throw new ClientException("The thread has been cancelled before the request start", ClientException.ClientReason.CANCELLED);
        }
        try {
            this.k = Long.valueOf(System.currentTimeMillis());
            int responseCode = this.c.getResponseCode();
            this.l = Long.valueOf(System.currentTimeMillis());
            d();
            return responseCode;
        } catch (IOException unused) {
            this.k = Long.valueOf(System.currentTimeMillis());
            int responseCode2 = this.c.getResponseCode();
            this.l = Long.valueOf(System.currentTimeMillis());
            d();
            return responseCode2;
        } catch (NullPointerException e) {
            throw new ClientException(new IOException(e));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v74, types: [java.io.OutputStream, java.lang.Throwable, java.io.DataOutputStream] */
    @Override // ru.mail.verify.core.utils.HttpConnection
    public String getResponseAsString() throws IOException, ServerException, ClientException {
        InputStream inputStream;
        c();
        a();
        try {
            if (this.b != null) {
                if (Thread.interrupted()) {
                    b();
                    throw new ClientException("The thread has been cancelled before post data", ClientException.ClientReason.CANCELLED);
                }
                FileLog.v("HttpConnection", "post data started");
                if (this.f) {
                    FileLog.v("HttpConnection", this.e);
                }
                try {
                    ?? dataOutputStream = new DataOutputStream(this.c.getOutputStream());
                    try {
                        dataOutputStream.write(this.b);
                        dataOutputStream.flush();
                        FileLog.v("HttpConnection", "post data completed");
                        dataOutputStream.close();
                    } catch (Throwable th) {
                        try {
                            dataOutputStream.close();
                        } catch (Throwable th2) {
                            th2.addSuppressed(dataOutputStream);
                        }
                        throw th;
                    }
                } catch (Exception unused) {
                    Objects.toString(this.c.getURL());
                }
            }
            int responseCode = getResponseCode();
            if (Thread.interrupted()) {
                b();
                throw new ClientException("The thread has been cancelled after connection start", ClientException.ClientReason.CANCELLED);
            } else if (responseCode != 200 && responseCode != 202) {
                c();
                b();
                throw new ServerException(responseCode);
            } else {
                c();
                try {
                    inputStream = this.c.getInputStream();
                    try {
                        a(this.c.getErrorStream());
                    } catch (IOException e) {
                        FileLog.v("HttpConnection", "getInputStream", e);
                    }
                } catch (FileNotFoundException e2) {
                    InputStream errorStream = this.c.getErrorStream();
                    FileLog.v("HttpConnection", "getInputStream", e2);
                    if (errorStream == null) {
                        throw new IOException("errorStream is null");
                    }
                    inputStream = errorStream;
                }
                d();
                String str = "UTF-8";
                if (this.d) {
                    String headerField = getHeaderField("Content-Type");
                    if (headerField != null) {
                        String[] split = headerField.replace(" ", "").split(";");
                        int length = split.length;
                        int i = 0;
                        while (true) {
                            if (i >= length) {
                                break;
                            }
                            String str2 = split[i];
                            if (str2.startsWith("charset=")) {
                                str = str2.split("=", 2)[1];
                                break;
                            }
                            i++;
                        }
                    }
                }
                StringBuilder sb = new StringBuilder(1024);
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, str);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    sb.append(readLine);
                }
                bufferedReader.close();
                inputStreamReader.close();
                String sb2 = sb.toString();
                d();
                FileLog.v("HttpConnection", sb2);
                NetworkInterceptor networkInterceptor = this.g;
                if (networkInterceptor != null) {
                    networkInterceptor.check(this.a, NetworkInterceptor.NetworkAction.AFTER_DOWNLOAD, sb2.length());
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                return sb2;
            }
        } finally {
            disconnect();
        }
    }

    @Override // ru.mail.verify.core.utils.HttpConnection
    @Nullable
    public String getHeaderField(@NonNull String s) throws ClientException, ServerException, IOException {
        return getHeaderField(s, false);
    }

    @Override // ru.mail.verify.core.utils.HttpConnection
    public void disconnect() {
        c();
        this.c.disconnect();
    }

    @Override // ru.mail.verify.core.utils.HttpConnection
    public long getSentTimestamp() {
        try {
            String headerField = getHeaderField("X-Android-Sent-Millis");
            if (headerField != null) {
                return Long.parseLong(headerField);
            }
        } catch (Throwable unused) {
        }
        Long l = this.k;
        if (l == null) {
            return 0L;
        }
        return l.longValue();
    }

    @Override // ru.mail.verify.core.utils.HttpConnection
    public long getReceiveTimestamp() {
        try {
            String headerField = getHeaderField("X-Android-Received-Millis");
            if (headerField != null) {
                return Long.parseLong(headerField);
            }
        } catch (Throwable unused) {
        }
        Long l = this.l;
        if (l == null) {
            return 0L;
        }
        return l.longValue();
    }

    @Override // ru.mail.verify.core.utils.HttpConnection
    public void downloadToFile(@NonNull File file) throws IOException, ServerException, ClientException {
        c();
        a();
        downloadToStream(new FileOutputStream(file));
    }

    /* JADX WARN: Finally extract failed */
    @Override // ru.mail.verify.core.utils.HttpConnection
    public void downloadToStream(@NonNull OutputStream outputStream) throws IOException, ServerException, ClientException {
        InputStream inputStream;
        int intValue;
        c();
        a();
        try {
            int responseCode = getResponseCode();
            if (Thread.interrupted()) {
                b();
                throw new ClientException("The thread has been cancelled after connection start", ClientException.ClientReason.CANCELLED);
            } else if (responseCode == 200) {
                String headerField = getHeaderField("Content-Length");
                Integer num = null;
                if (!TextUtils.isEmpty(headerField)) {
                    try {
                        num = Integer.valueOf(Integer.parseInt(headerField));
                    } catch (NumberFormatException unused) {
                    }
                }
                c();
                try {
                    inputStream = this.c.getInputStream();
                    try {
                        a(this.c.getErrorStream());
                    } catch (IOException e) {
                        FileLog.v("HttpConnection", "getInputStream", e);
                    }
                } catch (FileNotFoundException e2) {
                    InputStream errorStream = this.c.getErrorStream();
                    FileLog.v("HttpConnection", "getInputStream", e2);
                    if (errorStream == null) {
                        throw new IOException("errorStream is null");
                    }
                    inputStream = errorStream;
                }
                d();
                long nanoTime = System.nanoTime();
                int i = 0;
                int i2 = 0;
                try {
                    byte[] bArr = new byte[51200];
                    while (true) {
                        int read = inputStream.read(bArr);
                        if (read <= 0) {
                            break;
                        }
                        Integer num2 = num;
                        int i3 = i;
                        outputStream.write(bArr, 0, read);
                        i = i3 + read;
                        if (num2 != null && (intValue = (int) ((i / num.intValue()) * 100.0d)) != i2 && intValue % 10 == 0) {
                            Object[] objArr = new Object[1];
                            objArr[0] = Integer.valueOf(intValue);
                            FileLog.v("HttpConnection", "File download progress %d", objArr);
                            i2 = intValue;
                        }
                    }
                    Integer num3 = num;
                    outputStream.flush();
                    outputStream.close();
                    Object[] objArr2 = new Object[1];
                    objArr2[0] = Long.valueOf(TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - nanoTime));
                    FileLog.v("HttpConnection", "File download took %d ms", objArr2);
                    if (num3 != null && num.intValue() != i) {
                        throw new ClientException("Content size is not equal to the promoted one", ClientException.ClientReason.DEFAULT);
                    }
                    Object[] objArr3 = new Object[1];
                    objArr3[0] = Integer.valueOf(i);
                    FileLog.v("HttpConnection", "File download completed (%d written)", objArr3);
                    NetworkInterceptor networkInterceptor = this.g;
                    if (networkInterceptor != null) {
                        networkInterceptor.check(this.a, NetworkInterceptor.NetworkAction.AFTER_DOWNLOAD, i);
                    }
                    inputStream.close();
                } catch (Throwable th) {
                    outputStream.flush();
                    outputStream.close();
                    throw th;
                }
            } else {
                b();
                throw new ServerException(responseCode);
            }
        } finally {
            disconnect();
        }
    }

    private static void a(@Nullable InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return;
        }
        try {
            do {
            } while (inputStream.read(new byte[1024]) >= 0);
            try {
                inputStream.close();
            } catch (IOException e) {
                FileLog.e("HttpConnection", "emptyAndClose", e);
            }
        } catch (Throwable th) {
            try {
                inputStream.close();
            } catch (IOException e2) {
                FileLog.e("HttpConnection", "emptyAndClose", e2);
            }
            throw th;
        }
    }

    @Override // ru.mail.verify.core.utils.HttpConnection
    public String getHeaderField(@NonNull String s, boolean allowRedirects) throws ClientException, ServerException, IOException {
        c();
        a();
        int responseCode = getResponseCode();
        if (Thread.interrupted()) {
            b();
            throw new ClientException("The thread has been cancelled after connection start", ClientException.ClientReason.CANCELLED);
        }
        boolean z = allowRedirects && responseCode >= 400;
        boolean z2 = (allowRedirects || responseCode == 200) ? false : true;
        if (z || z2) {
            b();
            throw new ServerException(responseCode);
        }
        d();
        return this.c.getHeaderField(s);
    }
}
