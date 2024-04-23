package ru.mail.libverify.g;

import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.mail.libverify.notifications.k;
import ru.mail.verify.core.ui.notifications.NotificationBase;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/g/b.class */
public interface b {
    @Nullable
    k a(@NotNull NotificationBase notificationBase, @NotNull String str);

    @Nullable
    k a(@NotNull String str);

    @Nullable
    k remove(@NotNull String str);

    void clear();

    @NotNull
    Map<String, NotificationBase> a();
}
