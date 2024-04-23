package ru.mail.verify.core.utils.network;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.jvm.internal.Intrinsics;
import ru.mail.libverify.s.a;
import ru.mail.libverify.v.a;
import ru.mail.verify.core.storage.Installation;
import ru.mail.verify.core.storage.UnsafeInstallation;
import ru.mail.verify.core.utils.FileLog;
import ru.mail.verify.core.utils.components.BusMessageType;
import ru.mail.verify.core.utils.components.MessageBusUtils;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/utils/network/NetworkStateReceiver.class */
public class NetworkStateReceiver extends BroadcastReceiver {
    private static final String LOG_TAG = "NetworkStateReceiver";
    private static final AtomicReference<NetworkStateDescriptor> sNetworkState = new AtomicReference<>(NetworkStateDescriptor.getNoneDescriptor());
    private static NetworkStateReceiver registeredInstance = null;

    public static NetworkStateDescriptor getNetwork(@NonNull Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null) {
            FileLog.e(LOG_TAG, "no available network found (ConnectivityManager is null)");
            return NetworkStateDescriptor.getDescriptor(context, NetworkState.NONE);
        }
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            return activeNetworkInfo.getType() == 1 ? NetworkStateDescriptor.getDescriptor(context, NetworkState.WIFI) : activeNetworkInfo.isRoaming() ? NetworkStateDescriptor.getDescriptor(context, NetworkState.ROAMING) : NetworkStateDescriptor.getDescriptor(context, NetworkState.CELLULAR);
        } else if (activeNetworkInfo == null || !activeNetworkInfo.isConnectedOrConnecting()) {
            FileLog.d(LOG_TAG, "no available network found (%s)", activeNetworkInfo);
            return NetworkStateDescriptor.getDescriptor(context, NetworkState.NONE);
        } else {
            return NetworkStateDescriptor.getDescriptor(context, NetworkState.CONNECTING);
        }
    }

    private static void a(@NonNull Context context, boolean z) {
        NetworkStateDescriptor network = getNetwork(context);
        AtomicReference<NetworkStateDescriptor> atomicReference = sNetworkState;
        FileLog.d(LOG_TAG, "check network: %s (current: %s, fire event: %s)", network, atomicReference.get(), Boolean.valueOf(z));
        if (atomicReference.getAndSet(network) != network) {
            int i = a.f;
            if (UnsafeInstallation.hasInstallation(context) || Installation.hasInstallation(context)) {
                FileLog.v(LOG_TAG, "state changed to %s on %s", network.state, network.name);
                if (z) {
                    try {
                        a.a(context, MessageBusUtils.createOneArg(BusMessageType.NETWORK_STATE_CHANGED, Boolean.valueOf(hasNetwork(context))));
                    } catch (Throwable th) {
                        FileLog.e(LOG_TAG, "failed to process network state change", th);
                    }
                }
            }
        }
    }

    public static boolean isMetered(@NonNull Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null) {
            return false;
        }
        return connectivityManager.isActiveNetworkMetered();
    }

    public static boolean checkCurrentIsWiFi(@NonNull Context context) {
        a(context, false);
        return sNetworkState.get().state == NetworkState.WIFI;
    }

    public static boolean checkCurrentIsCellular(@NonNull Context context) {
        a(context, false);
        NetworkState networkState = sNetworkState.get().state;
        return networkState == NetworkState.CELLULAR || networkState == NetworkState.ROAMING;
    }

    public static Boolean checkCurrentIsRoaming(@NonNull Context context) {
        a(context, false);
        return Boolean.valueOf(sNetworkState.get().state == NetworkState.ROAMING);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [int] */
    /* JADX WARN: Type inference failed for: r0v2, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v5, types: [boolean] */
    public static boolean hasCellularConnection(Context context) {
        boolean z = false;
        ?? checkSelfPermission = ActivityCompat.checkSelfPermission(context, "android.permission.ACCESS_NETWORK_STATE");
        if (checkSelfPermission == 0) {
            try {
                Intrinsics.checkNotNullParameter(context, "context");
                checkSelfPermission = a.C0020a.a(context, null).n();
                z = checkSelfPermission;
            } catch (Exception unused) {
                checkSelfPermission.printStackTrace();
            }
        }
        return z;
    }

    public static boolean hasNetwork(@NonNull Context context) {
        a(context, false);
        return isAnything();
    }

    public static void testNetwork(@NonNull Context context) {
        a(context, true);
    }

    public static boolean isAnything() {
        return sNetworkState.get().state != NetworkState.NONE;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v5, types: [java.lang.Class<ru.mail.verify.core.utils.network.NetworkStateReceiver>] */
    /* JADX WARN: Type inference failed for: r0v6, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v8 */
    public static void enable(@NonNull Context context) {
        try {
            context.getPackageManager().setComponentEnabledSetting(new ComponentName(context, NetworkStateReceiver.class), 1, 1);
            int i = Build.VERSION.SDK_INT;
            if (i >= 24) {
                ?? r0 = NetworkStateReceiver.class;
                synchronized (r0) {
                    if (registeredInstance == null) {
                        a(context, false);
                        registeredInstance = new NetworkStateReceiver();
                        IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
                        if (i >= 33) {
                            context.registerReceiver(registeredInstance, intentFilter, 4);
                        } else {
                            context.registerReceiver(registeredInstance, intentFilter);
                        }
                    }
                    r0 = r0;
                }
            }
            FileLog.v(LOG_TAG, "enabled");
        } catch (Throwable th) {
            FileLog.e(LOG_TAG, "failed to enable", th);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v5, types: [java.lang.Class<ru.mail.verify.core.utils.network.NetworkStateReceiver>] */
    /* JADX WARN: Type inference failed for: r0v6, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v8 */
    public static void disable(@NonNull Context context) {
        try {
            context.getPackageManager().setComponentEnabledSetting(new ComponentName(context, NetworkStateReceiver.class), 2, 1);
            if (Build.VERSION.SDK_INT >= 24) {
                ?? r0 = NetworkStateReceiver.class;
                synchronized (r0) {
                    NetworkStateReceiver networkStateReceiver = registeredInstance;
                    if (networkStateReceiver != null) {
                        context.unregisterReceiver(networkStateReceiver);
                        registeredInstance = null;
                    }
                    r0 = r0;
                }
            }
            FileLog.v(LOG_TAG, "disabled");
        } catch (Throwable th) {
            FileLog.e(LOG_TAG, "failed to disable", th);
        }
    }

    public static boolean hasVpnConnection(Context context) {
        NetworkInfo[] allNetworkInfo;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null) {
            FileLog.e(LOG_TAG, "no available network found (ConnectivityManager is null)");
            return false;
        } else if (Build.VERSION.SDK_INT >= 23) {
            NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
            if (networkCapabilities == null) {
                return false;
            }
            return networkCapabilities.hasTransport(4);
        } else {
            for (NetworkInfo networkInfo : connectivityManager.getAllNetworkInfo()) {
                if (networkInfo.isConnected() && networkInfo.getType() == 17) {
                    return true;
                }
            }
            return false;
        }
    }

    public static Boolean hasNetworkRoaming(@NonNull Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null) {
            return null;
        }
        boolean z = false;
        for (NetworkInfo networkInfo : connectivityManager.getAllNetworkInfo()) {
            z = z || networkInfo.isRoaming();
        }
        return Boolean.valueOf(z);
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (context == null || intent == null || !"android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
            return;
        }
        a(context, true);
    }
}
