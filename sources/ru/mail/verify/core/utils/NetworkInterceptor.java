package ru.mail.verify.core.utils;

import androidx.annotation.NonNull;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/utils/NetworkInterceptor.class */
public interface NetworkInterceptor {

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/utils/NetworkInterceptor$NetworkAction.class */
    public enum NetworkAction {
        BEFORE_DOWNLOAD,
        BEFORE_UPLOAD,
        AFTER_DOWNLOAD,
        AFTER_UPLOAD
    }

    void check(@NonNull String str, NetworkAction networkAction, int i) throws ClientException;
}
