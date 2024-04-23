package ru.mail.verify.core.requests;

import android.content.Context;
import android.net.Network;
import android.os.Handler;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import javax.net.ssl.SSLException;
import ru.mail.verify.core.api.ApplicationModule;
import ru.mail.verify.core.api.NetworkManager;
import ru.mail.verify.core.requests.FutureWrapper;
import ru.mail.verify.core.requests.response.ResponseBase;
import ru.mail.verify.core.utils.ClientException;
import ru.mail.verify.core.utils.ConnectionBuilder;
import ru.mail.verify.core.utils.DebugUtils;
import ru.mail.verify.core.utils.FileLog;
import ru.mail.verify.core.utils.HttpConnection;
import ru.mail.verify.core.utils.ServerException;
import ru.mail.verify.core.utils.json.JsonParseException;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/requests/RequestBase.class */
public abstract class RequestBase<T extends ResponseBase> {
    protected static final String SIGNATURE_PARAM = "signature";
    private static SimpleDateFormat d;
    @Nullable
    protected Network customNetwork = null;
    private String a = null;
    private Long b;
    private String c;
    protected final Context context;
    protected final NetworkManager network;
    protected final ApplicationModule.ApplicationStartConfig appConfig;

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/requests/RequestBase$a.class */
    class a implements Callable<T> {
        a() {
        }

        @Override // java.util.concurrent.Callable
        public final Object call() throws Exception {
            return RequestBase.this.execute();
        }
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/requests/RequestBase$b.class */
    class b implements Callable<T> {
        final /* synthetic */ HttpConnection a;

        b(HttpConnection httpConnection) {
            this.a = httpConnection;
        }

        @Override // java.util.concurrent.Callable
        public final Object call() throws Exception {
            return RequestBase.this.a(this.a);
        }
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/requests/RequestBase$c.class */
    class c implements FutureWrapper.c {
        final /* synthetic */ HttpConnection a;

        c(HttpConnection httpConnection) {
            this.a = httpConnection;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public RequestBase(@NonNull Context context, @NonNull NetworkManager network, @NonNull ApplicationModule.ApplicationStartConfig appConfig) {
        this.context = context;
        this.network = network;
        this.appConfig = appConfig;
    }

    private T a(@NonNull HttpConnection httpConnection) throws ClientException, ServerException, IOException {
        try {
            c(httpConnection);
            b(httpConnection);
            T readResponse = readResponse(httpConnection);
            if (readResponse != null) {
                readResponse.setSentTimestamp(httpConnection.getSentTimestamp());
                readResponse.setReceiveTimestamp(httpConnection.getReceiveTimestamp());
                Object[] objArr = new Object[2];
                objArr[0] = Long.valueOf(readResponse.getSentTimestamp());
                objArr[1] = Long.valueOf(readResponse.getReceiveTimestamp());
                FileLog.d("ApiRequest", "Response received; Sent timestamp: %d; Receive timestamp: %d", objArr);
                readResponse.setOwner(this);
                return readResponse;
            }
            throw new JsonParseException("Response can't be null");
        } catch (SecurityException e) {
            if (ru.mail.verify.core.utils.Utils.hasSelfPermission(this.context, "android.permission.INTERNET")) {
                throw e;
            }
            throw new ClientException(e);
        } catch (SSLException e2) {
            if (useCertificatePinning()) {
                FileLog.e("ApiRequest", "failed to validate pinned certificate", e2);
                throw new ClientException(e2);
            }
            throw e2;
        }
    }

    private void c(@NonNull HttpConnection httpConnection) throws ClientException, IOException, ServerException {
        if (isLastModifiedNeeded()) {
            String headerField = httpConnection.getHeaderField("Last-Modified");
            if (TextUtils.isEmpty(headerField)) {
                return;
            }
            try {
                if (d == null) {
                    synchronized (RequestBase.class) {
                        if (d == null) {
                            d = new SimpleDateFormat("EEE, dd MMM y HH:mm:ss 'GMT'", Locale.US);
                            d.setTimeZone(TimeZone.getTimeZone("GMT"));
                        }
                    }
                }
                Long valueOf = Long.valueOf(d.parse(headerField).getTime());
                this.b = valueOf;
                Object[] objArr = new Object[3];
                objArr[0] = "Last-Modified";
                objArr[1] = headerField;
                objArr[2] = valueOf;
                FileLog.v("ApiRequest", "header %s value %s (%d)", objArr);
            } catch (ParseException e) {
                DebugUtils.safeThrow("ApiRequest", "failed to parse last modified timestamp from the response", e);
            }
        }
    }

    private void b(@NonNull HttpConnection httpConnection) throws ClientException, IOException, ServerException {
        if (isETagNeeded()) {
            String headerField = httpConnection.getHeaderField("ETag");
            if (TextUtils.isEmpty(headerField)) {
                return;
            }
            this.c = headerField;
            FileLog.v("ApiRequest", "header %s value %s", "ETag", headerField);
        }
    }

    @NonNull
    public String getId() {
        RequestPersistentId requestData = getRequestData();
        if (requestData == null || TextUtils.isEmpty(requestData.getId())) {
            throw new IllegalArgumentException("Request id can't be empty");
        }
        return String.format(Locale.US, "%s_%s", getMethodName(), requestData.getId());
    }

    @NonNull
    public T execute() throws NoSuchAlgorithmException, IllegalArgumentException, ClientException, ServerException, IOException {
        return a(a());
    }

    @NonNull
    public Future<T> executeAsync(@NonNull ExecutorService executorService, @NonNull Handler handler, @Nullable FutureWrapper.FutureListener<T> listener) {
        return new FutureWrapper(executorService, handler, new a(), null, listener).execute();
    }

    @NonNull
    public Future<T> executeCancellableAsync(@NonNull ExecutorService executorService) throws ClientException, NoSuchAlgorithmException, IOException {
        HttpConnection a2 = a();
        return new FutureWrapper(executorService, null, new b(a2), new c(a2), null).execute();
    }

    @Nullable
    public Long getLastModified() {
        if (isLastModifiedNeeded()) {
            return this.b;
        }
        return null;
    }

    @Nullable
    public String getETag() {
        if (isETagNeeded()) {
            return this.c;
        }
        return null;
    }

    public boolean keepSystemLock() {
        return false;
    }

    @NonNull
    public String getApiNameForStatistics() {
        return getMethodName();
    }

    public abstract RequestSerializedData getSerializedData() throws JsonParseException;

    public boolean switchToNextApiHost() {
        return false;
    }

    public boolean canRunOffline() {
        return false;
    }

    protected String getRequestUrl() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String str = this.a;
        if (str == null || !str.contains(getApiHost())) {
            if (isSignatureRequired()) {
                FileLog.v("ApiRequest", "buildRequestUrlSigned start");
                ApiRequestParams methodParams = getMethodParams();
                StringBuilder sb = new StringBuilder(methodParams.getCharLength());
                HashSet hashSet = new HashSet();
                for (Map.Entry<String, String> entry : methodParams.entrySet()) {
                    if (entry.getValue() == null || entry.getValue().length() >= 196) {
                        hashSet.add(entry);
                    } else {
                        addUrlParam(sb, entry);
                    }
                }
                Iterator it = hashSet.iterator();
                while (it.hasNext()) {
                    addUrlParam(sb, (Map.Entry) it.next());
                }
                String format = String.format(Locale.US, "%s%s?%s&signature=%s", getApiHost(), getApiPath(), sb.toString(), getSignature(methodParams));
                sb.setLength(0);
                FileLog.v("ApiRequest", "buildRequestUrlSigned end");
                this.a = format;
            } else {
                this.a = buildRequestUrl();
            }
        }
        return this.a;
    }

    @Nullable
    protected abstract String getApiHost();

    @NonNull
    protected String getApiPath() {
        return "";
    }

    protected ApiRequestParams getMethodParams() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return new ApiRequestParams();
    }

    @NonNull
    protected String buildRequestUrl() throws UnsupportedEncodingException, NoSuchAlgorithmException, IllegalArgumentException {
        FileLog.v("ApiRequest", "buildRequestUrl start");
        ApiRequestParams methodParams = getMethodParams();
        if (methodParams.isEmpty()) {
            FileLog.v("ApiRequest", "buildRequestUrl end without params");
            return String.format(Locale.US, "%s%s", getApiHost(), getApiPath());
        }
        StringBuilder sb = new StringBuilder(methodParams.getCharLength());
        for (Map.Entry<String, String> entry : methodParams.entrySet()) {
            addUrlParam(sb, entry);
        }
        String format = String.format(Locale.US, "%s%s?%s", getApiHost(), getApiPath(), sb);
        sb.setLength(0);
        FileLog.v("ApiRequest", "buildRequestUrl end");
        return format;
    }

    @NonNull
    protected String getSignature(@NonNull ApiRequestParams params) throws UnsupportedEncodingException {
        return "";
    }

    public String getUrl() {
        try {
            return getRequestUrl();
        } catch (Throwable unused) {
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract String getMethodName();

    protected abstract T parseJsonAnswer(String str) throws JsonParseException;

    protected T readResponse(@NonNull HttpConnection connection) throws ClientException, ServerException, IOException {
        return parseJsonAnswer(connection.getResponseAsString());
    }

    protected Integer getReadTimeout() {
        return null;
    }

    protected Integer getConnectTimeout() {
        return null;
    }

    protected Long getLastResponseTimestamp() {
        return null;
    }

    @Nullable
    protected String getLastETag() {
        return null;
    }

    protected boolean isLastModifiedNeeded() {
        return false;
    }

    protected boolean isETagNeeded() {
        return false;
    }

    protected boolean isSignatureRequired() {
        return false;
    }

    protected abstract RequestPersistentId getRequestData();

    protected boolean postUrlData() {
        return false;
    }

    protected boolean postJsonData() {
        return false;
    }

    protected boolean postGzipData() {
        return false;
    }

    protected HttpConnection.Method getHttpMethod() {
        return postJsonData() || postUrlData() || postGzipData() ? HttpConnection.Method.POST : HttpConnection.Method.GET;
    }

    @Nullable
    protected byte[] getPostData() throws ClientException {
        return null;
    }

    protected String getApiCertificate() {
        return null;
    }

    protected boolean useCertificatePinning() {
        return false;
    }

    protected void addUrlParam(StringBuilder query, Map.Entry<String, String> param) throws UnsupportedEncodingException {
        if (TextUtils.isEmpty(param.getValue())) {
            FileLog.e("ApiRequest", String.format(Locale.US, "Url argument %s can't be empty", param.getKey()));
            throw new IllegalArgumentException("Url argument can't be empty");
        }
        if (!TextUtils.isEmpty(query)) {
            query.append("&");
        }
        query.append(param.getKey()).append("=").append(URLEncoder.encode(param.getValue(), "UTF-8"));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Nullable
    public String getIfNoneMatchHeader() {
        return null;
    }

    private HttpConnection a() throws ClientException, IOException, NoSuchAlgorithmException {
        if (DebugUtils.isTrustAllCertificatesEnabled()) {
            DebugUtils.trustAllCertificates();
        }
        String requestUrl = getRequestUrl();
        String str = null;
        if (postUrlData()) {
            String[] split = requestUrl.split("\\?");
            if (split.length == 2) {
                requestUrl = split[0];
                str = split[1];
            }
        }
        ConnectionBuilder allowRedirects = this.network.getConnectionBuilder(requestUrl, this.customNetwork).allowRedirects(false);
        if (getIfNoneMatchHeader() != null) {
            allowRedirects.addHeader("If-None-Match", getIfNoneMatchHeader());
        }
        if (useCertificatePinning()) {
            allowRedirects.setSocketFactory(ru.mail.verify.core.utils.Utils.getSslContextFromResource(this.context, getApiCertificate()).getSocketFactory());
        }
        if (this.network.hasProxy()) {
            FileLog.v("ApiRequest", "keep-alive disabled because of proxy config");
            allowRedirects.setKeepAlive(false);
        } else {
            allowRedirects.setKeepAlive(true);
        }
        if (this.appConfig.isDebugMode()) {
            allowRedirects.setDebugMode();
        }
        allowRedirects.setMethod(getHttpMethod());
        if (postJsonData() || postUrlData() || postGzipData()) {
            if (postUrlData()) {
                if (TextUtils.isEmpty(str)) {
                    throw new ClientException("Post url data must be provided", ClientException.ClientReason.DEFAULT);
                }
                allowRedirects.setPostUrlData(str, postGzipData());
            } else if (!postJsonData()) {
                throw new IllegalStateException("Request must declare any available post data method");
            } else {
                byte[] postData = getPostData();
                if (postData != null && postData.length != 0) {
                    allowRedirects.setPostJsonData(postData, postGzipData());
                }
            }
        }
        if (getReadTimeout() != null) {
            allowRedirects.setReadTimeout(getReadTimeout().intValue());
        }
        if (getConnectTimeout() != null) {
            allowRedirects.setConnectTimeout(getConnectTimeout().intValue());
        }
        if (getLastResponseTimestamp() != null) {
            if (d == null) {
                synchronized (RequestBase.class) {
                    if (d == null) {
                        d = new SimpleDateFormat("EEE, dd MMM y HH:mm:ss 'GMT'", Locale.US);
                        d.setTimeZone(TimeZone.getTimeZone("GMT"));
                    }
                }
            }
            allowRedirects.addHeader("If-Modified-Since", d.format(new Date(getLastResponseTimestamp().longValue())));
        }
        if (getLastETag() != null) {
            allowRedirects.addHeader("If-None-Match", getLastETag());
        }
        return allowRedirects.build();
    }
}
