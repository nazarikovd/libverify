package ru.mail.verify.core.requests;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkRequest;
import android.net.TelephonyNetworkSpecifier;
import android.os.Build;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import androidx.core.content.ContextCompat;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.mail.verify.core.storage.BroadcastManager;
import ru.mail.verify.core.utils.DebugUtils;
import ru.mail.verify.core.utils.FileLog;

@Metadata(mv = {BroadcastManager.FLAG_NETWORK_RECEIVER, 7, BroadcastManager.FLAG_NETWORK_RECEIVER}, k = BroadcastManager.FLAG_NETWORK_RECEIVER, d1 = {"��0\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\u0010\u000e\n��\n\u0002\u0010\u0002\n��\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0007\bÀ\u0002\u0018��2\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0018\u0010\u0010J\u0010\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0007J\u0018\u0010\b\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u0002H\u0007R(\u0010\u0011\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\t8\u0006X\u0087\u0004¢\u0006\u0012\n\u0004\b\u000b\u0010\f\u0012\u0004\b\u000f\u0010\u0010\u001a\u0004\b\r\u0010\u000eR \u0010\u0015\u001a\u00020\u00128\u0006X\u0087\u0004¢\u0006\u0012\n\u0004\b\u0013\u0010\u0014\u0012\u0004\b\u0017\u0010\u0010\u001a\u0004\b\u0015\u0010\u0016¨\u0006\u0019"}, d2 = {"Lru/mail/verify/core/requests/ConnectivityHelper;", "", "", "key", "", "unregisterCallback", "Landroid/content/Context;", "context", "registerCallback", "Ljava/util/concurrent/atomic/AtomicReference;", "Landroid/net/Network;", "c", "Ljava/util/concurrent/atomic/AtomicReference;", "getNetwork", "()Ljava/util/concurrent/atomic/AtomicReference;", "getNetwork$annotations", "()V", "network", "Ljava/util/concurrent/atomic/AtomicBoolean;", "d", "Ljava/util/concurrent/atomic/AtomicBoolean;", "isCallbackRegistered", "()Ljava/util/concurrent/atomic/AtomicBoolean;", "isCallbackRegistered$annotations", "<init>", "libverify_release"})
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/requests/ConnectivityHelper.class */
public final class ConnectivityHelper {
    @Nullable
    private static ConnectivityManager a;
    @NotNull
    public static final ConnectivityHelper INSTANCE = new ConnectivityHelper();
    private static final Set<String> b = Collections.synchronizedSet(new HashSet());
    @NotNull
    private static final AtomicReference<Network> c = new AtomicReference<>(null);
    @NotNull
    private static final AtomicBoolean d = new AtomicBoolean(false);
    @NotNull
    private static final ConnectivityHelper$callback$1 e = new ConnectivityManager.NetworkCallback() { // from class: ru.mail.verify.core.requests.ConnectivityHelper$callback$1
        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onLinkPropertiesChanged(@NotNull Network pNetwork, @NotNull LinkProperties linkProperties) {
            Intrinsics.checkNotNullParameter(pNetwork, "pNetwork");
            Intrinsics.checkNotNullParameter(linkProperties, "linkProperties");
            super.onLinkPropertiesChanged(pNetwork, linkProperties);
            FileLog.d("ConnectivityHelper", "Receive onLinkPropertiesChanged");
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onLost(@NotNull Network pNetwork) {
            Intrinsics.checkNotNullParameter(pNetwork, "pNetwork");
            super.onLost(pNetwork);
            FileLog.d("ConnectivityHelper", "Network lost " + pNetwork);
            ConnectivityHelper.getNetwork().set(null);
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onAvailable(@NotNull Network pNetwork) {
            Intrinsics.checkNotNullParameter(pNetwork, "pNetwork");
            super.onAvailable(pNetwork);
            ConnectivityHelper.getNetwork().set(pNetwork);
            FileLog.d("ConnectivityHelper", "Network available " + pNetwork);
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onUnavailable() {
            super.onUnavailable();
            ConnectivityHelper.getNetwork().set(null);
            FileLog.d("ConnectivityHelper", "Network unavailable");
        }
    };

    private ConnectivityHelper() {
    }

    @NotNull
    public static final AtomicReference<Network> getNetwork() {
        return c;
    }

    @JvmStatic
    public static /* synthetic */ void getNetwork$annotations() {
    }

    @NotNull
    public static final AtomicBoolean isCallbackRegistered() {
        return d;
    }

    @JvmStatic
    public static /* synthetic */ void isCallbackRegistered$annotations() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.util.Set, java.util.Set<java.lang.String>, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r0v2, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v4 */
    @JvmStatic
    public static final void unregisterCallback(@NotNull String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        ?? r0 = b;
        Intrinsics.checkNotNullExpressionValue((Object) r0, "sessions");
        synchronized (r0) {
            r0.remove(key);
            if (r0.isEmpty()) {
                ConnectivityManager connectivityManager = a;
                if (connectivityManager != null) {
                    connectivityManager.unregisterNetworkCallback(e);
                    d.set(false);
                    c.set(null);
                    FileLog.d("ConnectivityHelper", "Network listener has been removed");
                }
            }
            r0 = r0;
            Unit unit = Unit.INSTANCE;
        }
    }

    @JvmStatic
    public static final void registerCallback(@NotNull Context context, @NotNull String key) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(key, "key");
        try {
            Set<String> set = b;
            Intrinsics.checkNotNullExpressionValue(set, "sessions");
            synchronized (set) {
                if (set.contains(key)) {
                    return;
                }
                set.add(key);
                AtomicBoolean atomicBoolean = d;
                if (atomicBoolean.get()) {
                    return;
                }
                ConnectivityHelper connectivityHelper = INSTANCE;
                connectivityHelper.getClass();
                a = (ConnectivityManager) context.getSystemService("connectivity");
                NetworkRequest.Builder builder = new NetworkRequest.Builder();
                builder.addCapability(12);
                builder.addTransportType(0);
                if (Build.VERSION.SDK_INT >= 30 && ContextCompat.checkSelfPermission(context, "android.permission.READ_PHONE_STATE") == 0) {
                    connectivityHelper.getClass();
                    SubscriptionManager subscriptionManager = (SubscriptionManager) context.getSystemService("telephony_subscription_service");
                    if (subscriptionManager != null) {
                        int defaultDataSubscriptionId = SubscriptionManager.getDefaultDataSubscriptionId();
                        if (defaultDataSubscriptionId != -1) {
                            SubscriptionInfo activeSubscriptionInfo = subscriptionManager.getActiveSubscriptionInfo(defaultDataSubscriptionId);
                            if (activeSubscriptionInfo != null) {
                                builder.setNetworkSpecifier(new TelephonyNetworkSpecifier.Builder().setSubscriptionId(activeSubscriptionInfo.getSubscriptionId()).build());
                            }
                        }
                    }
                }
                NetworkRequest build = builder.build();
                ConnectivityManager connectivityManager = a;
                if (connectivityManager != null) {
                    connectivityManager.requestNetwork(build, e);
                    atomicBoolean.set(true);
                    FileLog.d("ConnectivityHelper", "Network listener registered");
                }
            }
        } catch (Throwable th) {
            DebugUtils.safeThrow("ConnectivityHelper", th, "Failed to register network listener.", new Object[0]);
        }
    }
}
