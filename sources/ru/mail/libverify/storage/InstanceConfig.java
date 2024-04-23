package ru.mail.libverify.storage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil;
import java.util.Map;
import ru.mail.libverify.utils.ScreenState;
import ru.mail.verify.core.accounts.SimCardData;
import ru.mail.verify.core.api.NetworkManager;
import ru.mail.verify.core.gcm.GcmRegistrar;
import ru.mail.verify.core.platform.TimeProvider;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/storage/InstanceConfig.class */
public interface InstanceConfig extends ru.mail.verify.core.storage.InstanceConfig {
    String getApplicationName();

    String getApplicationKey();

    String getHashedId();

    @NonNull
    ScreenState getScreenState();

    NetworkManager getNetwork();

    GcmRegistrar getRegistrar();

    boolean isLowBattery();

    @NonNull
    SimCardData getSimCardData();

    @NonNull
    ru.mail.libverify.k.c getKnownSmsFinder();

    String getServerKey();

    boolean isCallUiFeatureEnable();

    String getExtendedPhoneInfo();

    @NonNull
    Map<String, String> getApiEndpoints();

    @Nullable
    String getApiProxyDomain();

    String decryptServerMessage(@NonNull String str, @NonNull String str2) throws DecryptionError;

    boolean checkInstanceHasNewerVersion(@Nullable String str);

    @NonNull
    TimeProvider getTimeProvider();

    @NonNull
    PhoneNumberUtil getPhoneNumberUtil();
}
