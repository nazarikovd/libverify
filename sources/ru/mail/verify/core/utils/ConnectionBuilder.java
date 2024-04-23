package ru.mail.verify.core.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.io.IOException;
import javax.net.ssl.SSLSocketFactory;
import ru.mail.verify.core.utils.HttpConnection;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/utils/ConnectionBuilder.class */
public interface ConnectionBuilder {
    ConnectionBuilder setMethod(HttpConnection.Method method) throws IOException, ClientException;

    ConnectionBuilder setKeepAlive(boolean z) throws IOException, ClientException;

    ConnectionBuilder allowRedirects(boolean z) throws IOException, ClientException;

    ConnectionBuilder setConnectTimeout(int i) throws IOException, ClientException;

    ConnectionBuilder setReadTimeout(int i) throws IOException, ClientException;

    ConnectionBuilder addHeader(String str, String str2) throws IOException, ClientException;

    ConnectionBuilder setCheckResponseEncoding(boolean z);

    ConnectionBuilder acceptGzip(boolean z) throws IOException, ClientException;

    ConnectionBuilder setPostUrlData(@NonNull String str, boolean z) throws IOException, ClientException;

    ConnectionBuilder setPostJsonData(@NonNull byte[] bArr, boolean z) throws IOException, ClientException;

    ConnectionBuilder setSocketFactory(@Nullable SSLSocketFactory sSLSocketFactory) throws IOException, ClientException;

    ConnectionBuilder setProxyHost(String str);

    ConnectionBuilder setProxyPort(int i);

    ConnectionBuilder setDebugMode();

    HttpConnection build() throws IOException, ClientException;
}
