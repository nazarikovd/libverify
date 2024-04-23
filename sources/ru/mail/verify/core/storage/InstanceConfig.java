package ru.mail.verify.core.storage;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.io.File;
import java.util.Locale;
import ru.mail.verify.core.api.ApiManager;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/storage/InstanceConfig.class */
public interface InstanceConfig {
    public static final String DEVICE_TYPE_TABLET = "tablet";
    public static final String DEVICE_TYPE_PHONE = "phone";

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/storage/InstanceConfig$PropertyType.class */
    public enum PropertyType {
        APP_VERSION,
        ADVERTISING_ID,
        SYSTEM_ID,
        DEVICE_NAME,
        DEVICE_VENDOR,
        TIME_ZONE,
        OS_VERSION,
        LIB_BUILD_NUMBER,
        LIB_VERSION_NUMBER,
        CORE_COUNT,
        RAM_SIZE,
        SCREEN_HEIGHT,
        SCREEN_WIDTH,
        DEVICE_TYPE,
        DEVICE_ID_V2
    }

    @NonNull
    Context getContext();

    @NonNull
    String getId();

    String getStringProperty(PropertyType propertyType);

    @Nullable
    String getStringPropertySync(PropertyType propertyType, ApiManager apiManager);

    Integer getIntProperty(PropertyType propertyType);

    Locale getCurrentLocale();

    @NonNull
    Boolean isDisabledSimDataSend();

    File getCacheFolder();
}
