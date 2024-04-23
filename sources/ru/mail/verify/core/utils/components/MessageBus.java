package ru.mail.verify.core.utils.components;

import android.os.Message;
import androidx.annotation.NonNull;
import java.util.Collection;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/utils/components/MessageBus.class */
public interface MessageBus {
    void post(@NonNull Message message);

    void register(@NonNull Collection<BusMessageType> collection, @NonNull MessageHandler messageHandler);
}
