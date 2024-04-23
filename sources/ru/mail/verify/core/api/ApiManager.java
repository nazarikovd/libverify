package ru.mail.verify.core.api;

import android.os.Message;
import androidx.annotation.NonNull;
import java.util.concurrent.ExecutorService;
import ru.mail.verify.core.utils.components.CustomHandler;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/api/ApiManager.class */
public interface ApiManager {
    public static final String GCM_TOKEN_CHECK_TYPE = "gcm_token_check_type";

    void addApiGroup(@NonNull ApiGroup apiGroup);

    void post(@NonNull Message message);

    void postAndWait(@NonNull Message message);

    CustomHandler getDispatcher();

    ExecutorService getBackgroundWorker();

    void shutdown();

    void stop();

    void reset();

    void prepare(@NonNull Runnable runnable);

    void onApplicationStartConfigChanged();
}
