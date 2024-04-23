package ru.mail.verify.core.storage;

import androidx.annotation.NonNull;
import java.io.File;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import ru.mail.verify.core.utils.FileLog;
import ru.mail.verify.core.utils.PRNGFixes;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/storage/InstallationHelper.class */
public final class InstallationHelper {
    protected static final String LOG_TAG = "InstallationHelper";
    private final AtomicReference<IDState> a = new AtomicReference<>(IDState.UNKNOWN);

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/storage/InstallationHelper$IDState.class */
    public enum IDState {
        UNKNOWN,
        NO_INSTALLATION,
        INITIALIZING,
        RESETTING,
        HAS_INSTALLATION
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/storage/InstallationHelper$a.class */
    static /* synthetic */ class a {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[IDState.values().length];
            a = iArr;
            try {
                iArr[IDState.UNKNOWN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[IDState.HAS_INSTALLATION.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[IDState.INITIALIZING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                a[IDState.NO_INSTALLATION.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                a[IDState.RESETTING.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public static String generateId() {
        PRNGFixes.apply();
        return UUID.randomUUID().toString();
    }

    public boolean hasInstallation(@NonNull File installation) {
        FileLog.v(LOG_TAG, "state %s", this.a);
        switch (a.a[this.a.get().ordinal()]) {
            case BroadcastManager.FLAG_NETWORK_RECEIVER /* 1 */:
                try {
                    if (installation.exists()) {
                        this.a.compareAndSet(IDState.UNKNOWN, IDState.HAS_INSTALLATION);
                        return true;
                    }
                    this.a.compareAndSet(IDState.UNKNOWN, IDState.NO_INSTALLATION);
                    return false;
                } catch (Throwable th) {
                    FileLog.e(LOG_TAG, "failed to check installation file", th);
                    return false;
                }
            case 2:
            case 3:
                return true;
            case 4:
            case 5:
                return false;
            default:
                throw new IllegalStateException("Undefined state");
        }
    }

    public void setIdState(IDState state) {
        this.a.set(state);
    }
}
