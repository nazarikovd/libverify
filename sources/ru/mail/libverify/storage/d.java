package ru.mail.libverify.storage;

import android.content.Context;
import androidx.annotation.NonNull;
import dagger.Lazy;
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil;
import java.util.Locale;
import java.util.Map;
import ru.mail.libverify.platform.storage.KeyValueStorage;
import ru.mail.verify.core.accounts.SimCardReader;
import ru.mail.verify.core.api.AlarmManager;
import ru.mail.verify.core.api.NetworkManager;
import ru.mail.verify.core.api.ResourceParamsBase;
import ru.mail.verify.core.gcm.GcmRegistrar;
import ru.mail.verify.core.storage.LocationProvider;
import ru.mail.verify.core.utils.Utils;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/storage/d.class */
final class d extends h {
    private final String B;
    private final Locale C;
    private final String D;
    private final Map<String, String> E;

    /* JADX INFO: Access modifiers changed from: package-private */
    public d(@NonNull ru.mail.libverify.m.l lVar, @NonNull Context context, @NonNull ResourceParamsBase resourceParamsBase, @NonNull Lazy<AlarmManager> lazy, @NonNull Lazy<GcmRegistrar> lazy2, @NonNull Lazy<LocationProvider> lazy3, @NonNull Lazy<NetworkManager> lazy4, @NonNull Lazy<KeyValueStorage> lazy5, @NonNull Lazy<SimCardReader> lazy6, @NonNull Lazy<PhoneNumberUtil> lazy7) {
        super(context, resourceParamsBase, lazy, lazy2, lazy3, lazy4, lazy5, lazy6, lazy7);
        this.B = lVar.getId();
        this.C = lVar.getCurrentLocale();
        this.D = lVar.getServerKey();
        this.E = lVar.getApiEndpoints();
    }

    @Override // ru.mail.libverify.storage.h, ru.mail.verify.core.storage.InstanceConfig
    @NonNull
    public final String getId() {
        return this.B;
    }

    @Override // ru.mail.libverify.storage.h, ru.mail.libverify.storage.InstanceConfig
    public final String getHashedId() {
        return Utils.stringToSHA256(this.B);
    }

    @Override // ru.mail.libverify.x.a, ru.mail.verify.core.storage.InstanceConfig
    public final Locale getCurrentLocale() {
        return this.C;
    }

    @Override // ru.mail.libverify.storage.h, ru.mail.libverify.storage.InstanceConfig
    public final String getServerKey() {
        return this.D;
    }

    @Override // ru.mail.libverify.storage.h, ru.mail.libverify.storage.InstanceConfig
    @NonNull
    public final Map<String, String> getApiEndpoints() {
        return this.E;
    }
}
