package ru.mail.verify.core.utils;

import androidx.annotation.NonNull;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/utils/HttpConnection.class */
public interface HttpConnection {

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/utils/HttpConnection$Method.class */
    public enum Method {
        GET,
        POST,
        HEAD,
        PUT
    }

    int getResponseCode() throws IOException, ClientException;

    String getResponseAsString() throws IOException, ServerException, ClientException;

    String getHeaderField(String str) throws ClientException, ServerException, IOException;

    String getHeaderField(String str, boolean z) throws ClientException, ServerException, IOException;

    void downloadToFile(@NonNull File file) throws IOException, ServerException, ClientException;

    void downloadToStream(@NonNull OutputStream outputStream) throws IOException, ServerException, ClientException;

    void disconnect();

    long getSentTimestamp();

    long getReceiveTimestamp();
}
