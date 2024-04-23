package ru.mail.libverify.b;

import android.content.Context;
import android.net.Network;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import javax.inject.Inject;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import ru.mail.libverify.m.l;
import ru.mail.verify.core.api.ApplicationModule;
import ru.mail.verify.core.api.NetworkManagerImpl;
import ru.mail.verify.core.utils.ClientException;
import ru.mail.verify.core.utils.ConnectionBuilder;
import ru.mail.verify.core.utils.FileLog;
import ru.mail.verify.core.utils.HttpConnectionImpl;
import ru.mail.verify.core.utils.SocketFactoryProvider;
import ru.mail.verify.core.utils.components.MessageBus;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/b/g.class */
public final class g extends NetworkManagerImpl {
    private final l e;

    /* JADX INFO: Access modifiers changed from: package-private */
    @Inject
    public g(@NonNull Context context, @NonNull l lVar, @NonNull MessageBus messageBus, @NonNull ApplicationModule.NetworkPolicyConfig networkPolicyConfig, @Nullable SocketFactoryProvider socketFactoryProvider) {
        super(context, messageBus, networkPolicyConfig, socketFactoryProvider);
        this.e = lVar;
    }

    private String b(String str) {
        int i;
        String apiProxyDomain = this.e.getApiProxyDomain();
        String str2 = apiProxyDomain;
        if (TextUtils.isEmpty(apiProxyDomain)) {
            return str;
        }
        if (str != null && str2 != null) {
            try {
                URL url = new URL(str);
                if (StringsKt.indexOf$default(str2, ":", 0, false, 6, (Object) null) != -1) {
                    URL url2 = null;
                    try {
                        url2 = new URL("http://".concat(str2));
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    URL url3 = url2;
                    Intrinsics.checkNotNull(url3);
                    str2 = url3.getHost();
                    i = url3.getPort();
                } else {
                    i = -1;
                }
                int i2 = i;
                boolean areEqual = Intrinsics.areEqual(url.getProtocol(), "https");
                if ((i2 == 443 && areEqual) || (i == 80 && !areEqual)) {
                    i = -1;
                }
                str = new URL(url.getProtocol(), str2, i, url.getFile()).toString();
            } catch (MalformedURLException unused) {
                throw new RuntimeException("Couldn't replace host in url, originalUrl=" + str + ", newHost=" + str2);
            }
        }
        return str;
    }

    private String a(@NonNull String str) {
        Map<String, String> apiEndpoints = this.e.getApiEndpoints();
        if (!apiEndpoints.isEmpty()) {
            for (Map.Entry<String, String> entry : apiEndpoints.entrySet()) {
                if (!TextUtils.isEmpty(entry.getKey()) && !TextUtils.isEmpty(entry.getValue())) {
                    str = str.replace(entry.getKey(), entry.getValue());
                }
            }
        }
        return str;
    }

    @Override // ru.mail.verify.core.api.NetworkManagerImpl, ru.mail.verify.core.api.NetworkManager
    @NonNull
    public final ConnectionBuilder getConnectionBuilder(@NonNull String str, @Nullable Network network) throws IOException, ClientException {
        try {
            str = b(a(str));
        } catch (Exception e) {
            FileLog.e("VerifyNetworkManager", e, "failed to replace token in url %s", str);
        }
        return HttpConnectionImpl.getBuilder(str, this.provider, createNetworkInterceptor(), network);
    }
}
