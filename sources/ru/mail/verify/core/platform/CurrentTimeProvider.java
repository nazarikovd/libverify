package ru.mail.verify.core.platform;

import kotlin.Metadata;
import ru.mail.verify.core.storage.BroadcastManager;

@Metadata(mv = {BroadcastManager.FLAG_NETWORK_RECEIVER, 7, BroadcastManager.FLAG_NETWORK_RECEIVER}, k = BroadcastManager.FLAG_NETWORK_RECEIVER, xi = 48, d1 = {"��\u0010\n\u0002\u0018\u0002\n\u0002\u0010��\n��\n\u0002\u0010\t\n��\bà\u0080\u0001\u0018��2\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&¨\u0006\u0004"}, d2 = {"Lru/mail/verify/core/platform/CurrentTimeProvider;", "", "getCurrentTime", "", "libverify_release"})
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/platform/CurrentTimeProvider.class */
public interface CurrentTimeProvider {
    long getCurrentTime();
}
