package ru.mail.verify.core.ui.notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.service.notification.StatusBarNotification;
import androidx.annotation.RequiresApi;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import javax.inject.Inject;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.mail.libverify.g.b;
import ru.mail.libverify.o.f;
import ru.mail.verify.core.api.ApiManager;
import ru.mail.verify.core.storage.BroadcastManager;
import ru.mail.verify.core.utils.FileLog;
import ru.mail.verify.core.utils.NotificationUtils;
import ru.mail.verify.core.utils.Utils;
import ru.mail.verify.core.utils.components.BusMessageType;
import ru.mail.verify.core.utils.components.MessageBus;
import ru.mail.verify.core.utils.components.MessageBusUtils;

@Metadata(mv = {BroadcastManager.FLAG_NETWORK_RECEIVER, 7, BroadcastManager.FLAG_NETWORK_RECEIVER}, k = BroadcastManager.FLAG_NETWORK_RECEIVER, d1 = {"��h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\u000e\n��\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018�� -2\u00020\u0001:\u0001-B9\b\u0001\u0012\u0006\u0010\u000e\u001a\u00020\r\u0012\u0006\u0010\u0011\u001a\u00020\u0010\u0012\u0006\u0010\u0014\u001a\u00020\u0013\u0012\u0006\u0010\u0017\u001a\u00020\u0016\u0012\u0006\u0010\u001a\u001a\u00020\u0019\u0012\u0006\u0010\u001d\u001a\u00020\u001c¢\u0006\u0004\b+\u0010,J\u0018\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016J\u0010\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u0002H\u0016J\u0010\u0010\t\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0004H\u0016J\u0010\u0010\n\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0004H\u0016J\b\u0010\u000b\u001a\u00020\u0006H\u0016J\b\u0010\f\u001a\u00020\u0006H\u0016R\u0014\u0010\u000e\u001a\u00020\r8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0011\u001a\u00020\u00108\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0014\u001a\u00020\u00138\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0017\u001a\u00020\u00168\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0017\u0010\u0018R\u0014\u0010\u001a\u001a\u00020\u00198\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u001a\u0010\u001bR\u0014\u0010\u001d\u001a\u00020\u001c8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u001d\u0010\u001eR\u001b\u0010$\u001a\u00020\u001f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b \u0010!\u001a\u0004\b\"\u0010#RP\u0010)\u001a>\u0012\u0004\u0012\u00020\u0004\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\u00040&j\b\u0012\u0004\u0012\u00020\u0004`'0%j\u001e\u0012\u0004\u0012\u00020\u0004\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\u00040&j\b\u0012\u0004\u0012\u00020\u0004`'`(8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b)\u0010*¨\u0006."}, d2 = {"Lru/mail/verify/core/ui/notifications/NotificationBarManagerImpl;", "Lru/mail/verify/core/ui/notifications/NotificationBarManager;", "Lru/mail/verify/core/ui/notifications/NotificationBase;", "notification", "", "sessionId", "", "show", "tag", "cancel", "cancelAllBySessionId", "cancelAll", "checkShownNotifications", "Landroid/content/Context;", "context", "Landroid/content/Context;", "Lru/mail/verify/core/utils/components/MessageBus;", "bus", "Lru/mail/verify/core/utils/components/MessageBus;", "Lru/mail/verify/core/api/ApiManager;", "manager", "Lru/mail/verify/core/api/ApiManager;", "Lru/mail/verify/core/ui/notifications/NotificationChannelSettings;", "notificationChannelSettings", "Lru/mail/verify/core/ui/notifications/NotificationChannelSettings;", "Lru/mail/libverify/g/b;", "notificationRepository", "Lru/mail/libverify/g/b;", "Lru/mail/libverify/o/f;", "imageDownloadManager", "Lru/mail/libverify/o/f;", "Landroid/app/NotificationManager;", "systemManager$delegate", "Lkotlin/Lazy;", "getSystemManager", "()Landroid/app/NotificationManager;", "systemManager", "Ljava/util/HashMap;", "Ljava/util/HashSet;", "Lkotlin/collections/HashSet;", "Lkotlin/collections/HashMap;", "notificationsBySessionId", "Ljava/util/HashMap;", "<init>", "(Landroid/content/Context;Lru/mail/verify/core/utils/components/MessageBus;Lru/mail/verify/core/api/ApiManager;Lru/mail/verify/core/ui/notifications/NotificationChannelSettings;Lru/mail/libverify/g/b;Lru/mail/libverify/o/f;)V", "Companion", "libverify_release"})
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/ui/notifications/NotificationBarManagerImpl.class */
public final class NotificationBarManagerImpl implements NotificationBarManager {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private static final String LOG_TAG = "NotificationBarManager";
    @NotNull
    private final Context context;
    @NotNull
    private final MessageBus bus;
    @NotNull
    private final ApiManager manager;
    @NotNull
    private final NotificationChannelSettings notificationChannelSettings;
    @NotNull
    private final b notificationRepository;
    @NotNull
    private final f imageDownloadManager;
    @NotNull
    private final Lazy systemManager$delegate;
    @NotNull
    private final HashMap<String, HashSet<String>> notificationsBySessionId;

    @Metadata(mv = {BroadcastManager.FLAG_NETWORK_RECEIVER, 7, BroadcastManager.FLAG_NETWORK_RECEIVER}, k = BroadcastManager.FLAG_NETWORK_RECEIVER, xi = 48, d1 = {"��\"\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0010\u000e\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J!\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u00042\u0006\u0010\b\u001a\u00020\tH\u0001¢\u0006\u0002\b\nR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n��¨\u0006\u000b"}, d2 = {"Lru/mail/verify/core/ui/notifications/NotificationBarManagerImpl$Companion;", "", "()V", "LOG_TAG", "", "getActiveNotification", "Landroid/app/Notification;", "notificationId", "context", "Landroid/content/Context;", "getActiveNotification$libverify_release", "libverify_release"})
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/ui/notifications/NotificationBarManagerImpl$Companion.class */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @JvmStatic
        @RequiresApi(23)
        @Nullable
        public final Notification getActiveNotification$libverify_release(@Nullable String notificationId, @NotNull Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            Object systemService = context.getSystemService("notification");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.NotificationManager");
            StatusBarNotification[] activeNotifications = ((NotificationManager) systemService).getActiveNotifications();
            Intrinsics.checkNotNullExpressionValue(activeNotifications, "barNotifications");
            for (StatusBarNotification statusBarNotification : activeNotifications) {
                if (Intrinsics.areEqual(statusBarNotification.getTag(), notificationId)) {
                    return statusBarNotification.getNotification();
                }
            }
            return null;
        }
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/ui/notifications/NotificationBarManagerImpl$a.class */
    static final class a extends Lambda implements Function0<NotificationManager> {
        a() {
            super(0);
        }

        public final Object invoke() {
            Object systemService = NotificationBarManagerImpl.this.context.getSystemService("notification");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.NotificationManager");
            return (NotificationManager) systemService;
        }
    }

    @Inject
    public NotificationBarManagerImpl(@NotNull Context context, @NotNull MessageBus messageBus, @NotNull ApiManager apiManager, @NotNull NotificationChannelSettings notificationChannelSettings, @NotNull b bVar, @NotNull f fVar) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(messageBus, "bus");
        Intrinsics.checkNotNullParameter(apiManager, "manager");
        Intrinsics.checkNotNullParameter(notificationChannelSettings, "notificationChannelSettings");
        Intrinsics.checkNotNullParameter(bVar, "notificationRepository");
        Intrinsics.checkNotNullParameter(fVar, "imageDownloadManager");
        this.context = context;
        this.bus = messageBus;
        this.manager = apiManager;
        this.notificationChannelSettings = notificationChannelSettings;
        this.notificationRepository = bVar;
        this.imageDownloadManager = fVar;
        this.systemManager$delegate = LazyKt.lazy(new a());
        this.notificationsBySessionId = new HashMap<>();
    }

    private final void a(NotificationId notificationId, String str) {
        try {
            Object[] objArr = new Object[2];
            objArr[0] = str;
            objArr[1] = Integer.valueOf(notificationId.ordinal());
            FileLog.d(LOG_TAG, "cancel tag %s id %d", objArr);
            ((NotificationManager) this.systemManager$delegate.getValue()).cancel(str, notificationId.ordinal());
        } catch (NullPointerException | SecurityException e) {
            FileLog.e(LOG_TAG, "cancel", e);
        }
    }

    @Override // ru.mail.verify.core.ui.notifications.NotificationBarManager
    public void show(@NotNull NotificationBase notification) {
        Long ongoingTimeout;
        Intrinsics.checkNotNullParameter(notification, "notification");
        FileLog.v(LOG_TAG, "show notification %s", notification.getTag());
        b bVar = this.notificationRepository;
        String tag = notification.getTag();
        Intrinsics.checkNotNullExpressionValue(tag, "notification.tag");
        bVar.a(notification, tag);
        a(notification);
        if (!notification.isOngoing() || (ongoingTimeout = notification.getOngoingTimeout()) == null) {
            return;
        }
        long longValue = ongoingTimeout.longValue();
        FileLog.v(LOG_TAG, "notification %s ongoing timeout %d", notification.getTag(), Long.valueOf(longValue));
        this.bus.post(MessageBusUtils.createMultipleArgs(BusMessageType.NOTIFICATION_BAR_MANAGER_ONGOING_NOTIFICATION_SHOWN, notification.getTag(), Long.valueOf(longValue)));
        this.manager.getDispatcher().postDelayed(() -> {
            a(r1, r2);
        }, longValue);
    }

    @Override // ru.mail.verify.core.ui.notifications.NotificationBarManager
    public void cancel(@NotNull String tag) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        this.notificationRepository.remove(tag);
        a(NotificationId.CONTENT, tag);
        a(NotificationId.SMS_CODE, tag);
    }

    @Override // ru.mail.verify.core.ui.notifications.NotificationBarManager
    public void cancelAllBySessionId(@NotNull String sessionId) {
        Intrinsics.checkNotNullParameter(sessionId, "sessionId");
        HashSet<String> hashSet = this.notificationsBySessionId.get(sessionId);
        if (hashSet == null) {
            return;
        }
        Iterator<String> it = hashSet.iterator();
        while (it.hasNext()) {
            String next = it.next();
            Intrinsics.checkNotNullExpressionValue(next, "tag");
            cancel(next);
            HashSet<String> hashSet2 = this.notificationsBySessionId.get(sessionId);
            if (hashSet2 != null) {
                hashSet2.remove(next);
            }
        }
    }

    @Override // ru.mail.verify.core.ui.notifications.NotificationBarManager
    public void cancelAll() {
        this.notificationRepository.clear();
        try {
            FileLog.d(LOG_TAG, "cancel all");
            ((NotificationManager) this.systemManager$delegate.getValue()).cancelAll();
        } catch (NullPointerException | SecurityException e) {
            FileLog.e(LOG_TAG, "cancel all", e);
        }
    }

    @Override // ru.mail.verify.core.ui.notifications.NotificationBarManager
    public void checkShownNotifications() {
        if (Build.VERSION.SDK_INT < 23) {
            this.notificationRepository.clear();
            return;
        }
        for (Map.Entry<String, NotificationBase> entry : this.notificationRepository.a().entrySet()) {
            NotificationBase value = entry.getValue();
            if (Companion.getActiveNotification$libverify_release(value.getTag(), this.context) != null) {
                show(value);
            } else {
                String tag = value.getTag();
                Intrinsics.checkNotNullExpressionValue(tag, "notification.tag");
                cancel(tag);
            }
        }
    }

    @Override // ru.mail.verify.core.ui.notifications.NotificationBarManager
    public void show(@NotNull NotificationBase notification, @NotNull String sessionId) {
        Intrinsics.checkNotNullParameter(notification, "notification");
        Intrinsics.checkNotNullParameter(sessionId, "sessionId");
        HashMap<String, HashSet<String>> hashMap = this.notificationsBySessionId;
        HashSet<String> hashSet = hashMap.get(sessionId);
        HashSet<String> hashSet2 = hashSet;
        if (hashSet == null) {
            hashSet2 = r1;
            HashSet<String> hashSet3 = new HashSet<>();
            hashMap.put(sessionId, hashSet2);
        }
        hashSet2.add(notification.getTag());
        show(notification);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final boolean a(String str, NotificationId notificationId, Notification notification) {
        int ordinal = notificationId.ordinal();
        try {
            Object[] objArr = new Object[2];
            objArr[0] = str;
            objArr[1] = Integer.valueOf(ordinal);
            FileLog.d(LOG_TAG, "safeNotify tag %s id %d", objArr);
            ((NotificationManager) this.systemManager$delegate.getValue()).notify(str, ordinal, notification);
            return true;
        } catch (SecurityException e) {
            FileLog.e(LOG_TAG, "safeNotify error", e);
            return this;
        }
    }

    private static final void a(NotificationBarManagerImpl notificationBarManagerImpl, NotificationBase notificationBase) {
        Intrinsics.checkNotNullParameter(notificationBarManagerImpl, "this$0");
        Intrinsics.checkNotNullParameter(notificationBase, "$notification");
        notificationBarManagerImpl.a(notificationBase);
        FileLog.v(LOG_TAG, "ongoing timeout for %s expired, silent = %s, ongoing = %s", notificationBase.getTag(), Boolean.valueOf(notificationBase.isSilent()), Boolean.valueOf(notificationBase.isOngoing()));
    }

    private final void a(NotificationBase notificationBase) {
        NotificationChannel notificationChannel;
        int i = Build.VERSION.SDK_INT;
        if (i >= 26) {
            ArrayList arrayList = new ArrayList(3);
            NotificationChannelSettings notificationChannelSettings = this.notificationChannelSettings;
            Context context = this.context;
            notificationChannelSettings.getClass();
            if (i < 26) {
                notificationChannel = null;
            } else {
                NotificationChannel notificationChannel2 = new NotificationChannel(context.getString(notificationChannelSettings.getHighNotificationIdRes()), context.getString(notificationChannelSettings.getHighNotificationNameRes()), 4);
                notificationChannel2.setDescription(context.getString(notificationChannelSettings.getHighNotificationDescriptionRes()));
                Integer resourceColor = Utils.getResourceColor(context, null, notificationChannelSettings.getLedColorRes());
                notificationChannel2.setLightColor(resourceColor != null ? resourceColor.intValue() : -1);
                notificationChannel2.enableLights(true);
                notificationChannel2.enableVibration(true);
                notificationChannel2.setVibrationPattern(new long[]{500, 500});
                notificationChannel = notificationChannel2;
            }
            NotificationChannel notificationChannel3 = notificationChannel;
            Intrinsics.checkNotNullExpressionValue(notificationChannel3, "notificationChannelSetti…ificationChannel(context)");
            arrayList.add(notificationChannel3);
            NotificationChannel lowNotificationChannel = this.notificationChannelSettings.getLowNotificationChannel(this.context);
            Intrinsics.checkNotNullExpressionValue(lowNotificationChannel, "notificationChannelSetti…ificationChannel(context)");
            arrayList.add(lowNotificationChannel);
            NotificationChannel channel = notificationBase.getChannel();
            if (channel != null) {
                arrayList.add(channel);
            }
            NotificationChannelGroup group = notificationBase.getGroup();
            if (group != null) {
                ((NotificationManager) this.systemManager$delegate.getValue()).createNotificationChannelGroup(group);
            }
            ((NotificationManager) this.systemManager$delegate.getValue()).createNotificationChannels(arrayList);
        }
        Notification build = notificationBase.getBuilder(this.imageDownloadManager).build();
        Intrinsics.checkNotNullExpressionValue(build, "builder.build()");
        if (notificationBase.isSilent()) {
            build.defaults = build.defaults & (-3) & (-2);
        } else {
            if (notificationBase.hasVibration()) {
                build.vibrate = new long[]{500, 500};
            } else {
                build.defaults &= -3;
            }
            if (!notificationBase.hasSound()) {
                build.defaults &= -2;
            }
        }
        if (notificationBase.shouldReplacePrevious()) {
            NotificationId id = notificationBase.getId();
            Intrinsics.checkNotNullExpressionValue(id, "notification.id");
            String tag = notificationBase.getTag();
            Intrinsics.checkNotNullExpressionValue(tag, "notification.tag");
            a(id, tag);
        }
        b bVar = this.notificationRepository;
        String tag2 = notificationBase.getTag();
        Intrinsics.checkNotNullExpressionValue(tag2, "notification.tag");
        if (bVar.a(tag2) == null) {
            return;
        }
        String tag3 = notificationBase.getTag();
        Intrinsics.checkNotNullExpressionValue(tag3, "notification.tag");
        NotificationId id2 = notificationBase.getId();
        Intrinsics.checkNotNullExpressionValue(id2, "notification.id");
        boolean a2 = a(tag3, id2, build);
        if (NotificationUtils.isNotificationChannelEnabled(this.context, notificationBase.getChannelId()) && a2) {
            notificationBase.onShown();
            return;
        }
        FileLog.e(LOG_TAG, "Failed to show notification %s", notificationBase.getTag());
        b bVar2 = this.notificationRepository;
        String tag4 = notificationBase.getTag();
        Intrinsics.checkNotNullExpressionValue(tag4, "notification.tag");
        bVar2.remove(tag4);
        this.bus.post(MessageBusUtils.createOneArg(BusMessageType.NOTIFICATION_BAR_MANAGER_BLOCKED_BY_USER, notificationBase.getTag()));
    }
}
