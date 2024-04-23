package ru.mail.verify.core.platform;

import kotlin.Metadata;
import org.jetbrains.annotations.Nullable;
import ru.mail.verify.core.storage.BroadcastManager;

@Metadata(mv = {BroadcastManager.FLAG_NETWORK_RECEIVER, 7, BroadcastManager.FLAG_NETWORK_RECEIVER}, k = BroadcastManager.FLAG_NETWORK_RECEIVER, xi = 48, d1 = {"�� \n\u0002\u0018\u0002\n\u0002\u0010��\n��\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0010\u0002\n��\n\u0002\u0010\u000b\n\u0002\b\u0005\b`\u0018��2\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003H&J\b\u0010\u0005\u001a\u00020\u0003H&J\b\u0010\u0006\u001a\u00020\u0003H&J\b\u0010\u0007\u001a\u00020\bH&J'\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\u00032\u0006\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u0003H&¢\u0006\u0002\u0010\u000e¨\u0006\u000f"}, d2 = {"Lru/mail/verify/core/platform/TimeProvider;", "", "convertToServerTimeStamp", "", "localTimeStamp", "getLocalTime", "getServerTimestamp", "reset", "", "setServerTime", "", "serverTimestamp", "sentTimestamp", "receiveTimestamp", "(Ljava/lang/Long;JJ)Z", "libverify_release"})
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/platform/TimeProvider.class */
public interface TimeProvider {
    long getLocalTime();

    long convertToServerTimeStamp(long j);

    long getServerTimestamp();

    boolean setServerTime(@Nullable Long l, long j, long j2);

    void reset();
}
