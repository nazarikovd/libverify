package ru.mail.verify.core.storage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.Locale;
import java.util.Map;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/storage/InstanceData.class */
public interface InstanceData extends InstanceConfig {
    void resetId();

    void prepare();

    void setCustomLocale(@NonNull Locale locale);

    void setSimDataSendDisabled(boolean z);

    boolean sendApplicationBroadcast(@NonNull String str, @Nullable Map<String, String> map);
}
