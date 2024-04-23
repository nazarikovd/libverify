package ru.mail.verify.core.platform;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.mail.libverify.platform.storage.KeyValueStorage;
import ru.mail.verify.core.storage.BroadcastManager;
import ru.mail.verify.core.utils.FileLog;

@Metadata(mv = {BroadcastManager.FLAG_NETWORK_RECEIVER, 7, BroadcastManager.FLAG_NETWORK_RECEIVER}, k = BroadcastManager.FLAG_NETWORK_RECEIVER, d1 = {"��&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0005\b��\u0018�� \u00132\u00020\u0001:\u0001\u0013B\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u000f¢\u0006\u0004\b\u0011\u0010\u0012J\b\u0010\u0003\u001a\u00020\u0002H\u0016J\u0010\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u0002H\u0016J\b\u0010\u0006\u001a\u00020\u0002H\u0016J)\u0010\u000b\u001a\u00020\n2\b\u0010\u0007\u001a\u0004\u0018\u00010\u00022\u0006\u0010\b\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020\u0002H\u0016¢\u0006\u0004\b\u000b\u0010\fJ\b\u0010\u000e\u001a\u00020\rH\u0016¨\u0006\u0014"}, d2 = {"Lru/mail/verify/core/platform/TimeProviderImpl;", "Lru/mail/verify/core/platform/TimeProvider;", "", "getLocalTime", "localTimeStamp", "convertToServerTimeStamp", "getServerTimestamp", "serverTimestamp", "sentTimestamp", "receiveTimestamp", "", "setServerTime", "(Ljava/lang/Long;JJ)Z", "", "reset", "Lru/mail/libverify/platform/storage/KeyValueStorage;", "settings", "<init>", "(Lru/mail/libverify/platform/storage/KeyValueStorage;)V", "Companion", "libverify_release"})
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/platform/TimeProviderImpl.class */
public final class TimeProviderImpl implements TimeProvider {
    @NotNull
    public static final String API_SERVER_TIMESTAMP_DIFF = "api_server_diff";
    @NotNull
    private final KeyValueStorage a;
    @NotNull
    public static final Companion Companion = new Companion(null);
    private static final String b = TimeProviderImpl.class.getName();

    @Metadata(mv = {BroadcastManager.FLAG_NETWORK_RECEIVER, 7, BroadcastManager.FLAG_NETWORK_RECEIVER}, k = BroadcastManager.FLAG_NETWORK_RECEIVER, xi = 48, d1 = {"��\u0014\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n��R\u0016\u0010\u0005\u001a\n \u0006*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004¢\u0006\u0002\n��¨\u0006\u0007"}, d2 = {"Lru/mail/verify/core/platform/TimeProviderImpl$Companion;", "", "()V", "API_SERVER_TIMESTAMP_DIFF", "", "LOG_TAG", "kotlin.jvm.PlatformType", "libverify_release"})
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/platform/TimeProviderImpl$Companion.class */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    public TimeProviderImpl(@NotNull KeyValueStorage settings) {
        Intrinsics.checkNotNullParameter(settings, "settings");
        this.a = settings;
    }

    @Override // ru.mail.verify.core.platform.TimeProvider
    public long getLocalTime() {
        return System.currentTimeMillis();
    }

    @Override // ru.mail.verify.core.platform.TimeProvider
    public long convertToServerTimeStamp(long localTimeStamp) {
        Long longValue = this.a.getLongValue(API_SERVER_TIMESTAMP_DIFF, (Long) null);
        if (longValue != null) {
            localTimeStamp = longValue.longValue() + localTimeStamp;
        }
        return localTimeStamp;
    }

    @Override // ru.mail.verify.core.platform.TimeProvider
    public long getServerTimestamp() {
        return convertToServerTimeStamp(getLocalTime());
    }

    @Override // ru.mail.verify.core.platform.TimeProvider
    public boolean setServerTime(@Nullable Long serverTimestamp, long sentTimestamp, long receiveTimestamp) {
        if (serverTimestamp == null || serverTimestamp.longValue() <= 0 || sentTimestamp <= 0 || receiveTimestamp <= 0 || receiveTimestamp <= sentTimestamp) {
            return false;
        }
        long longValue = serverTimestamp.longValue() - (((receiveTimestamp - sentTimestamp) / 2) + sentTimestamp);
        this.a.putValue(API_SERVER_TIMESTAMP_DIFF, longValue).commit();
        FileLog.d(b, "Difference with server time: %dms", Long.valueOf(longValue));
        return true;
    }

    @Override // ru.mail.verify.core.platform.TimeProvider
    public void reset() {
        this.a.removeValue(API_SERVER_TIMESTAMP_DIFF).commit();
    }
}
