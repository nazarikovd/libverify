package ru.mail.verify.core.timer;

import android.os.Handler;
import java.util.Set;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.mail.verify.core.storage.BroadcastManager;

@Metadata(mv = {BroadcastManager.FLAG_NETWORK_RECEIVER, 7, BroadcastManager.FLAG_NETWORK_RECEIVER}, k = BroadcastManager.FLAG_NETWORK_RECEIVER, xi = 48, d1 = {"��:\n\u0002\u0018\u0002\n\u0002\u0010��\n��\n\u0002\u0010\u0002\n��\n\u0002\u0010\u000b\n��\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n��\n\u0002\u0010\t\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\b\u0002\b`\u0018��2\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&J$\u0010\b\u001a\u00020\u00072\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH&J,\u0010\b\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\u00072\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH&J\u000e\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00070\u0011H&J\u0010\u0010\u0012\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&¨\u0006\u0013"}, d2 = {"Lru/mail/verify/core/timer/TimerManager;", "", "cancelAll", "", "cancelTimer", "", "id", "", "createTimer", "dispatcher", "Landroid/os/Handler;", "delay", "", "action", "Ljava/lang/Runnable;", "name", "getAllTimersIds", "", "hasTimerWithId", "libverify_release"})
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/timer/TimerManager.class */
public interface TimerManager {

    @Metadata(mv = {BroadcastManager.FLAG_NETWORK_RECEIVER, 7, BroadcastManager.FLAG_NETWORK_RECEIVER}, k = 3, xi = 48)
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/timer/TimerManager$DefaultImpls.class */
    public static final class DefaultImpls {
        public static /* synthetic */ String createTimer$default(TimerManager timerManager, Handler handler, long j, Runnable runnable, int i, Object obj) {
            if (obj == null) {
                if ((i & 1) != 0) {
                    handler = null;
                }
                return timerManager.createTimer(handler, j, runnable);
            }
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: createTimer");
        }

        public static /* synthetic */ String createTimer$default(TimerManager timerManager, String str, Handler handler, long j, Runnable runnable, int i, Object obj) {
            if (obj == null) {
                if ((i & 2) != 0) {
                    handler = null;
                }
                return timerManager.createTimer(str, handler, j, runnable);
            }
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: createTimer");
        }
    }

    @NotNull
    String createTimer(@NotNull String str, @Nullable Handler handler, long j, @NotNull Runnable runnable);

    @NotNull
    String createTimer(@Nullable Handler handler, long j, @NotNull Runnable runnable);

    boolean cancelTimer(@NotNull String str);

    boolean hasTimerWithId(@NotNull String str);

    @NotNull
    Set<String> getAllTimersIds();

    void cancelAll();
}
