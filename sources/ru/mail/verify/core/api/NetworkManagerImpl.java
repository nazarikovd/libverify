package ru.mail.verify.core.api;

import android.content.Context;
import android.net.Network;
import android.os.Message;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;
import javax.inject.Inject;
import ru.mail.verify.core.api.ApplicationModule;
import ru.mail.verify.core.api.NetworkSyncInterceptor;
import ru.mail.verify.core.storage.BroadcastManager;
import ru.mail.verify.core.utils.ClientException;
import ru.mail.verify.core.utils.ConnectionBuilder;
import ru.mail.verify.core.utils.FileLog;
import ru.mail.verify.core.utils.HttpConnectionImpl;
import ru.mail.verify.core.utils.NetworkInterceptor;
import ru.mail.verify.core.utils.SocketFactoryProvider;
import ru.mail.verify.core.utils.Utils;
import ru.mail.verify.core.utils.components.BusMessageType;
import ru.mail.verify.core.utils.components.MessageBus;
import ru.mail.verify.core.utils.components.MessageBusUtils;
import ru.mail.verify.core.utils.components.MessageHandler;
import ru.mail.verify.core.utils.network.NetworkStateReceiver;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/api/NetworkManagerImpl.class */
public class NetworkManagerImpl implements NetworkManager, MessageHandler {
    private final Context a;
    private final MessageBus b;
    private final ApplicationModule.NetworkPolicyConfig c;
    protected final SocketFactoryProvider provider;
    private NetworkSyncMode d = NetworkSyncMode.DEFAULT;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/api/NetworkManagerImpl$a.class */
    public static /* synthetic */ class a {
        static final /* synthetic */ int[] a;
        static final /* synthetic */ int[] b;

        static {
            int[] iArr = new int[NetworkInterceptor.NetworkAction.values().length];
            b = iArr;
            try {
                iArr[NetworkInterceptor.NetworkAction.BEFORE_DOWNLOAD.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                b[NetworkInterceptor.NetworkAction.BEFORE_UPLOAD.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                b[NetworkInterceptor.NetworkAction.AFTER_DOWNLOAD.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                b[NetworkInterceptor.NetworkAction.AFTER_UPLOAD.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            int[] iArr2 = new int[NetworkSyncMode.values().length];
            a = iArr2;
            try {
                iArr2[NetworkSyncMode.DEFAULT.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                a[NetworkSyncMode.WIFI_ONLY.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                a[NetworkSyncMode.CELLULAR_IF_NOT_METERED.ordinal()] = 3;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                a[NetworkSyncMode.DISABLED.ordinal()] = 4;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/api/NetworkManagerImpl$b.class */
    public static class b implements NetworkInterceptor {
        private static final AtomicInteger c = new AtomicInteger(0);
        private final NetworkSyncInterceptor a;
        private final int b = c.getAndIncrement();

        private b(@NonNull NetworkSyncInterceptor networkSyncInterceptor) {
            this.a = networkSyncInterceptor;
        }

        @Override // ru.mail.verify.core.utils.NetworkInterceptor
        public final void check(@NonNull String str, NetworkInterceptor.NetworkAction networkAction, int i) throws ClientException {
            NetworkSyncInterceptor networkSyncInterceptor;
            int i2;
            NetworkSyncInterceptor.DataRequestAction dataRequestAction;
            try {
                Object[] objArr = new Object[4];
                objArr[0] = str;
                objArr[1] = Integer.valueOf(this.b);
                objArr[2] = networkAction;
                objArr[3] = Integer.valueOf(i);
                FileLog.v("NetworkManager", "Check policy for %s (%d): %s, %d (bytes)", objArr);
                switch (a.b[networkAction.ordinal()]) {
                    case BroadcastManager.FLAG_NETWORK_RECEIVER /* 1 */:
                        if (this.a.onBeforeRequest(this.b, NetworkSyncInterceptor.DataRequestAction.DOWNLOAD, i) != NetworkSyncInterceptor.DataExchangeResolution.ENABLED) {
                            throw new ClientException("Application policy", ClientException.ClientReason.REJECTED_BY_POLICY);
                        }
                        return;
                    case 2:
                        if (this.a.onBeforeRequest(this.b, NetworkSyncInterceptor.DataRequestAction.UPLOAD, i) != NetworkSyncInterceptor.DataExchangeResolution.ENABLED) {
                            throw new ClientException("Application policy", ClientException.ClientReason.REJECTED_BY_POLICY);
                        }
                        return;
                    case 3:
                        networkSyncInterceptor = this.a;
                        i2 = this.b;
                        dataRequestAction = NetworkSyncInterceptor.DataRequestAction.DOWNLOAD;
                        break;
                    case 4:
                        networkSyncInterceptor = this.a;
                        i2 = this.b;
                        dataRequestAction = NetworkSyncInterceptor.DataRequestAction.UPLOAD;
                        break;
                    default:
                        FileLog.e("NetworkManager", "Illegal action name: " + networkAction.name());
                        throw new IllegalArgumentException("Illegal action name");
                }
                networkSyncInterceptor.onRequestCompleted(i2, dataRequestAction, i);
            } catch (ClientException unused) {
                throw "Illegal action name: ";
            } catch (Throwable th) {
                FileLog.e("NetworkManager", "Failed to call an application interceptor", th);
                throw new ClientException("Application policy", ClientException.ClientReason.REJECTED_BY_INTERCEPTOR_ERROR);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Inject
    public NetworkManagerImpl(@NonNull Context context, @NonNull MessageBus bus, @NonNull ApplicationModule.NetworkPolicyConfig config, @Nullable SocketFactoryProvider provider) {
        this.a = context;
        this.b = bus;
        this.c = config;
        this.provider = provider;
    }

    private boolean a(NetworkSyncMode networkSyncMode) {
        switch (a.a[networkSyncMode.ordinal()]) {
            case BroadcastManager.FLAG_NETWORK_RECEIVER /* 1 */:
                return NetworkStateReceiver.hasNetwork(this.a);
            case 2:
                return NetworkStateReceiver.hasNetwork(this.a) && NetworkStateReceiver.checkCurrentIsWiFi(this.a);
            case 3:
                return (!NetworkStateReceiver.hasNetwork(this.a) || NetworkStateReceiver.checkCurrentIsRoaming(this.a).booleanValue() || NetworkStateReceiver.isMetered(this.a)) ? false : true;
            case 4:
                return false;
            default:
                FileLog.e("NetworkManager", "Illegal mode: " + networkSyncMode.name());
                throw new IllegalArgumentException("Illegal mode");
        }
    }

    @Override // ru.mail.verify.core.api.NetworkManager
    @NonNull
    public ConnectionBuilder getConnectionBuilder(@NonNull String url, @Nullable Network customNetwork) throws IOException, ClientException {
        return HttpConnectionImpl.getBuilder(url, this.provider, createNetworkInterceptor(), customNetwork);
    }

    @Override // ru.mail.verify.core.api.NetworkManager
    public boolean hasNetwork() {
        NetworkSyncMode networkSyncMode = this.c.getNetworkSyncMode();
        this.d = networkSyncMode;
        return a(networkSyncMode);
    }

    @Override // ru.mail.verify.core.api.NetworkManager
    public boolean hasProxy() {
        return Utils.hasProxy(this.a);
    }

    @Override // ru.mail.verify.core.api.NetworkManager
    public void testNetwork() {
        NetworkStateReceiver.testNetwork(this.a);
    }

    @Override // ru.mail.verify.core.api.NetworkManager
    public boolean hasWifiConnection() {
        return NetworkStateReceiver.checkCurrentIsWiFi(this.a);
    }

    @Override // ru.mail.verify.core.api.NetworkManager
    public boolean hasCellularConnection() {
        return NetworkStateReceiver.hasCellularConnection(this.a);
    }

    @Override // ru.mail.verify.core.api.NetworkManager
    public boolean hasVpnConnection() {
        return NetworkStateReceiver.hasVpnConnection(this.a);
    }

    @Override // ru.mail.verify.core.api.NetworkManager
    @Nullable
    public Boolean isRoaming() {
        return NetworkStateReceiver.hasNetworkRoaming(this.a);
    }

    @Override // ru.mail.verify.core.api.ApiPlugin
    public void initialize() {
        this.b.register(Collections.singletonList(BusMessageType.API_APPLICATION_START_CONFIG_CHANGED), this);
    }

    @Override // ru.mail.verify.core.utils.components.MessageHandler
    public boolean handleMessage(@NonNull Message msg) {
        if (MessageBusUtils.getType(msg, "NetworkManager") == BusMessageType.API_APPLICATION_START_CONFIG_CHANGED) {
            NetworkSyncMode networkSyncMode = this.c.getNetworkSyncMode();
            if (networkSyncMode != this.d) {
                boolean a2 = a(networkSyncMode);
                this.b.post(MessageBusUtils.createOneArg(BusMessageType.NETWORK_STATE_CHANGED, Boolean.valueOf(a2)));
                FileLog.v("NetworkManager", "Network sync mode changed from %s to %s (online = %s)", this.d, networkSyncMode, Boolean.valueOf(a2));
                this.d = networkSyncMode;
                return true;
            }
            return true;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public NetworkInterceptor createNetworkInterceptor() {
        NetworkSyncInterceptor networkInterceptor = this.c.getNetworkInterceptor();
        if (networkInterceptor == null) {
            return null;
        }
        return new b(networkInterceptor);
    }
}
