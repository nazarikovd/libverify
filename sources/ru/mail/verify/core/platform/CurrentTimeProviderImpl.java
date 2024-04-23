package ru.mail.verify.core.platform;

import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import ru.mail.verify.core.storage.BroadcastManager;

@Metadata(mv = {BroadcastManager.FLAG_NETWORK_RECEIVER, 7, BroadcastManager.FLAG_NETWORK_RECEIVER}, k = BroadcastManager.FLAG_NETWORK_RECEIVER, xi = 48, d1 = {"��\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\b��\u0018�� \u00052\u00020\u0001:\u0001\u0005B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016¨\u0006\u0006"}, d2 = {"Lru/mail/verify/core/platform/CurrentTimeProviderImpl;", "Lru/mail/verify/core/platform/CurrentTimeProvider;", "()V", "getCurrentTime", "", "Companion", "libverify_release"})
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/platform/CurrentTimeProviderImpl.class */
public final class CurrentTimeProviderImpl implements CurrentTimeProvider {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private static CurrentTimeProviderImpl a;

    @Metadata(mv = {BroadcastManager.FLAG_NETWORK_RECEIVER, 7, BroadcastManager.FLAG_NETWORK_RECEIVER}, k = BroadcastManager.FLAG_NETWORK_RECEIVER, xi = 48, d1 = {"��\u0014\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018��2\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0005\u001a\u00020\u0004H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n��¨\u0006\u0006"}, d2 = {"Lru/mail/verify/core/platform/CurrentTimeProviderImpl$Companion;", "", "()V", "_instance", "Lru/mail/verify/core/platform/CurrentTimeProviderImpl;", "getInstance", "libverify_release"})
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/platform/CurrentTimeProviderImpl$Companion.class */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @JvmStatic
        @NotNull
        public final CurrentTimeProviderImpl getInstance() {
            if (CurrentTimeProviderImpl.a == null) {
                CurrentTimeProviderImpl.a = new CurrentTimeProviderImpl();
            }
            CurrentTimeProviderImpl currentTimeProviderImpl = CurrentTimeProviderImpl.a;
            CurrentTimeProviderImpl currentTimeProviderImpl2 = currentTimeProviderImpl;
            if (currentTimeProviderImpl == null) {
                Intrinsics.throwUninitializedPropertyAccessException("_instance");
                currentTimeProviderImpl2 = null;
            }
            return currentTimeProviderImpl2;
        }
    }

    @JvmStatic
    @NotNull
    public static final CurrentTimeProviderImpl getInstance() {
        return Companion.getInstance();
    }

    @Override // ru.mail.verify.core.platform.CurrentTimeProvider
    public long getCurrentTime() {
        return System.currentTimeMillis();
    }
}
