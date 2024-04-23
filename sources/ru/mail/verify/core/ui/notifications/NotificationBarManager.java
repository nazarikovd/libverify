package ru.mail.verify.core.ui.notifications;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import ru.mail.verify.core.storage.BroadcastManager;

@Metadata(mv = {BroadcastManager.FLAG_NETWORK_RECEIVER, 7, BroadcastManager.FLAG_NETWORK_RECEIVER}, k = BroadcastManager.FLAG_NETWORK_RECEIVER, xi = 48, d1 = {"��\u001e\n\u0002\u0018\u0002\n\u0002\u0010��\n��\n\u0002\u0010\u0002\n��\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n��\bf\u0018��2\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\b\u0010\u0006\u001a\u00020\u0003H&J\u0010\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\u0005H&J\b\u0010\t\u001a\u00020\u0003H&J\u0010\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\fH&J\u0018\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\b\u001a\u00020\u0005H&¨\u0006\r"}, d2 = {"Lru/mail/verify/core/ui/notifications/NotificationBarManager;", "", "cancel", "", "tag", "", "cancelAll", "cancelAllBySessionId", "sessionId", "checkShownNotifications", "show", "notification", "Lru/mail/verify/core/ui/notifications/NotificationBase;", "libverify_release"})
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/ui/notifications/NotificationBarManager.class */
public interface NotificationBarManager {
    void show(@NotNull NotificationBase notificationBase);

    void show(@NotNull NotificationBase notificationBase, @NotNull String str);

    void cancel(@NotNull String str);

    void cancelAllBySessionId(@NotNull String str);

    void cancelAll();

    void checkShownNotifications();
}
