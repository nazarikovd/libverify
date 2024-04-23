package ru.mail.verify.core.utils.network;

import android.util.LruCache;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/utils/network/NetworkStateDescriptor.class */
public class NetworkStateDescriptor {
    private static final String LOG_TAG = "NetworkStateDescriptor";
    private static final String UNKNOWN_WIFI_NETWORK_NAME = "Unknown Wi-Fi network";
    private static final String CELLULAR_NETWORK_NAME = "Cellular network";
    private static final String ROAMING_NETWORK_NAME = "In roaming";
    private static final String NO_NETWORK_NAME = "No network";
    private static final String CONNECTING_NAME = "Connecting";
    public final NetworkState state;
    public final String name;
    private static final NetworkStateDescriptor NONE = new NetworkStateDescriptor(NetworkState.NONE);
    private static final NetworkStateDescriptor CELLULAR = new NetworkStateDescriptor(NetworkState.CELLULAR);
    private static final NetworkStateDescriptor ROAMING = new NetworkStateDescriptor(NetworkState.ROAMING);
    private static final NetworkStateDescriptor CONNECTING = new NetworkStateDescriptor(NetworkState.CONNECTING);
    private static final int MAX_WIFI_NETWORKS_COUNT = 256;
    private static final LruCache<String, NetworkStateDescriptor> wifiNetworks = new LruCache<>(MAX_WIFI_NETWORKS_COUNT);

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/utils/network/NetworkStateDescriptor$a.class */
    public static /* synthetic */ class a {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[NetworkState.values().length];
            a = iArr;
            try {
                iArr[NetworkState.NONE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[NetworkState.WIFI.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[NetworkState.CELLULAR.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                a[NetworkState.ROAMING.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                a[NetworkState.CONNECTING.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x00d4, code lost:
        if (android.text.TextUtils.isEmpty(r0) != false) goto L41;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v22, types: [android.util.LruCache<java.lang.String, ru.mail.verify.core.utils.network.NetworkStateDescriptor>, android.util.LruCache] */
    /* JADX WARN: Type inference failed for: r0v23, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v26, types: [ru.mail.verify.core.utils.network.NetworkStateDescriptor] */
    @androidx.annotation.NonNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static ru.mail.verify.core.utils.network.NetworkStateDescriptor getDescriptor(@androidx.annotation.NonNull android.content.Context r6, ru.mail.verify.core.utils.network.NetworkState r7) {
        /*
            Method dump skipped, instructions count: 277
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: ru.mail.verify.core.utils.network.NetworkStateDescriptor.getDescriptor(android.content.Context, ru.mail.verify.core.utils.network.NetworkState):ru.mail.verify.core.utils.network.NetworkStateDescriptor");
    }

    public static NetworkStateDescriptor getNoneDescriptor() {
        return NONE;
    }

    private NetworkStateDescriptor(@NonNull NetworkState state) {
        this.state = state;
        this.name = null;
    }

    @NonNull
    public String toString() {
        return "NetworkStateDescriptor{state=" + this.state + ", name='" + this.name + "'}";
    }

    private NetworkStateDescriptor(@NonNull NetworkState state, @Nullable String networkName) {
        this.state = state;
        this.name = networkName;
    }
}
