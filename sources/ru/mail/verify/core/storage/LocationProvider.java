package ru.mail.verify.core.storage;

import android.location.Location;
import androidx.annotation.Nullable;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/storage/LocationProvider.class */
public interface LocationProvider {
    @Nullable
    Location getLastLocation();
}
