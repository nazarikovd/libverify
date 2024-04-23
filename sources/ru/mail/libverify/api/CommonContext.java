package ru.mail.libverify.api;

import android.os.Handler;
import java.util.concurrent.ExecutorService;
import ru.mail.libverify.platform.storage.KeyValueStorage;
import ru.mail.libverify.storage.InstanceConfig;
import ru.mail.verify.core.utils.components.MessageBus;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/CommonContext.class */
public interface CommonContext {
    MessageBus getBus();

    InstanceConfig getConfig();

    Handler getDispatcher();

    KeyValueStorage getSettings();

    ExecutorService getBackgroundWorker();
}
