package ru.mail.verify.core.timer;

import android.os.Handler;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.mail.verify.core.storage.BroadcastManager;

@Metadata(mv = {BroadcastManager.FLAG_NETWORK_RECEIVER, 7, BroadcastManager.FLAG_NETWORK_RECEIVER}, k = BroadcastManager.FLAG_NETWORK_RECEIVER, d1 = {"��8\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010\t\n��\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010#\n��\n\u0002\u0010\u0002\n\u0002\b\u0005\b��\u0018�� \u00152\u00020\u0001:\u0001\u0015B\t\b\u0002¢\u0006\u0004\b\u0013\u0010\u0014J*\u0010\n\u001a\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00022\b\u0010\u0005\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\bH\u0016J\"\u0010\n\u001a\u00020\u00022\b\u0010\u0005\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\bH\u0016J\u0010\u0010\r\u001a\u00020\f2\u0006\u0010\u000b\u001a\u00020\u0002H\u0016J\u0010\u0010\u000e\u001a\u00020\f2\u0006\u0010\u000b\u001a\u00020\u0002H\u0016J\u000e\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00020\u000fH\u0016J\b\u0010\u0012\u001a\u00020\u0011H\u0016¨\u0006\u0016"}, d2 = {"Lru/mail/verify/core/timer/TimerManagerImpl;", "Lru/mail/verify/core/timer/TimerManager;", "", "name", "Landroid/os/Handler;", "dispatcher", "", "delay", "Ljava/lang/Runnable;", "action", "createTimer", "id", "", "cancelTimer", "hasTimerWithId", "", "getAllTimersIds", "", "cancelAll", "<init>", "()V", "Companion", "libverify_release"})
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/timer/TimerManagerImpl.class */
public final class TimerManagerImpl implements TimerManager {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @Nullable
    private static TimerManager b;
    @NotNull
    private final HashMap<String, Timer> a;

    @Metadata(mv = {BroadcastManager.FLAG_NETWORK_RECEIVER, 7, BroadcastManager.FLAG_NETWORK_RECEIVER}, k = BroadcastManager.FLAG_NETWORK_RECEIVER, xi = 48, d1 = {"��\u0014\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0005\u001a\u00020\u0004H\u0007R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n��¨\u0006\u0006"}, d2 = {"Lru/mail/verify/core/timer/TimerManagerImpl$Companion;", "", "()V", "instance", "Lru/mail/verify/core/timer/TimerManager;", "getInstance", "libverify_release"})
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/timer/TimerManagerImpl$Companion.class */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @JvmStatic
        @NotNull
        public final TimerManager getInstance() {
            TimerManager timerManager;
            if (TimerManagerImpl.b != null) {
                TimerManager timerManager2 = TimerManagerImpl.b;
                timerManager = timerManager2;
                Intrinsics.checkNotNull(timerManager2);
            } else {
                TimerManagerImpl.b = new TimerManagerImpl(null);
                TimerManager timerManager3 = TimerManagerImpl.b;
                timerManager = timerManager3;
                Intrinsics.checkNotNull(timerManager3);
            }
            return timerManager;
        }
    }

    private TimerManagerImpl() {
        this.a = new HashMap<>();
    }

    @JvmStatic
    @NotNull
    public static final TimerManager getInstance() {
        return Companion.getInstance();
    }

    @Override // ru.mail.verify.core.timer.TimerManager
    @NotNull
    public String createTimer(@Nullable Handler dispatcher, long delay, @NotNull Runnable action) {
        UUID randomUUID;
        Intrinsics.checkNotNullParameter(action, "action");
        do {
            randomUUID = UUID.randomUUID();
            Intrinsics.checkNotNullExpressionValue(randomUUID, "randomUUID()");
        } while (this.a.containsKey(randomUUID.toString()));
        String uuid = randomUUID.toString();
        Intrinsics.checkNotNullExpressionValue(uuid, "generateId().toString()");
        return createTimer(uuid, dispatcher, delay, action);
    }

    @Override // ru.mail.verify.core.timer.TimerManager
    public boolean cancelTimer(@NotNull String id) {
        Intrinsics.checkNotNullParameter(id, "id");
        Timer timer = this.a.get(id);
        if (timer == null) {
            return false;
        }
        timer.cancel();
        this.a.remove(id);
        return true;
    }

    @Override // ru.mail.verify.core.timer.TimerManager
    public boolean hasTimerWithId(@NotNull String id) {
        Intrinsics.checkNotNullParameter(id, "id");
        return this.a.containsKey(id);
    }

    @Override // ru.mail.verify.core.timer.TimerManager
    @NotNull
    public Set<String> getAllTimersIds() {
        Set<String> keySet = this.a.keySet();
        Intrinsics.checkNotNullExpressionValue(keySet, "timers.keys");
        return keySet;
    }

    @Override // ru.mail.verify.core.timer.TimerManager
    public void cancelAll() {
        for (Map.Entry<String, Timer> entry : this.a.entrySet()) {
            entry.getValue().cancel();
        }
        this.a.clear();
    }

    public /* synthetic */ TimerManagerImpl(DefaultConstructorMarker $constructor_marker) {
        this();
    }

    @Override // ru.mail.verify.core.timer.TimerManager
    @NotNull
    public String createTimer(@NotNull final String name, @Nullable final Handler dispatcher, long delay, @NotNull final Runnable action) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(action, "action");
        if (this.a.containsKey(name)) {
            cancelTimer(name);
        }
        Timer timer = new Timer(name, false);
        timer.schedule(new TimerTask() { // from class: ru.mail.verify.core.timer.TimerManagerImpl$createTimer$1
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                HashMap hashMap;
                hashMap = TimerManagerImpl.this.a;
                hashMap.remove(name);
                Handler handler = dispatcher;
                if (handler != null) {
                    handler.post(action);
                } else {
                    action.run();
                }
            }
        }, delay);
        this.a.put(name, timer);
        return name;
    }
}
