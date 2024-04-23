package ru.mail.verify.core.api;

import androidx.annotation.WorkerThread;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/api/NetworkSyncInterceptor.class */
public interface NetworkSyncInterceptor {

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/api/NetworkSyncInterceptor$DataExchangeResolution.class */
    public enum DataExchangeResolution {
        ENABLED,
        DISABLED
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/api/NetworkSyncInterceptor$DataRequestAction.class */
    public enum DataRequestAction {
        UPLOAD,
        DOWNLOAD
    }

    @WorkerThread
    DataExchangeResolution onBeforeRequest(int i, DataRequestAction dataRequestAction, int i2);

    @WorkerThread
    void onRequestCompleted(int i, DataRequestAction dataRequestAction, int i2);
}
