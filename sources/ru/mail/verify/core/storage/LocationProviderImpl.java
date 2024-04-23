package ru.mail.verify.core.storage;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/storage/LocationProviderImpl.class */
public final class LocationProviderImpl implements LocationProvider {
    private final Context a;
    @Nullable
    private final List<String> b;
    @Nullable
    private final LocationManager c;
    private int d = -1;
    private long e;
    @Nullable
    private Location f;

    /* JADX INFO: Access modifiers changed from: package-private */
    @Inject
    public LocationProviderImpl(@NonNull Context context) {
        this.a = context;
        LocationManager locationManager = (LocationManager) context.getSystemService("location");
        this.c = locationManager;
        Criteria criteria = new Criteria();
        criteria.setCostAllowed(false);
        this.b = locationManager == null ? null : locationManager.getProviders(criteria, false);
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x00ec, code lost:
        if (r0 == null) goto L55;
     */
    @Override // ru.mail.verify.core.storage.LocationProvider
    @androidx.annotation.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized android.location.Location getLastLocation() {
        /*
            Method dump skipped, instructions count: 268
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: ru.mail.verify.core.storage.LocationProviderImpl.getLastLocation():android.location.Location");
    }
}
