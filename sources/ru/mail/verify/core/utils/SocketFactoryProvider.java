package ru.mail.verify.core.utils;

import androidx.annotation.Nullable;
import javax.net.ssl.SSLSocketFactory;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/utils/SocketFactoryProvider.class */
public interface SocketFactoryProvider {
    @Nullable
    SSLSocketFactory getSSLFactory(@Nullable SSLSocketFactory sSLSocketFactory);
}
