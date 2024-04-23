package ru.mail.verify.core.api;

import android.net.Network;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.io.IOException;
import ru.mail.verify.core.utils.ClientException;
import ru.mail.verify.core.utils.ConnectionBuilder;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/api/NetworkManager.class */
public interface NetworkManager extends ApiPlugin {
    @NonNull
    ConnectionBuilder getConnectionBuilder(@NonNull String str, @Nullable Network network) throws IOException, ClientException;

    boolean hasNetwork();

    boolean hasProxy();

    void testNetwork();

    boolean hasWifiConnection();

    boolean hasCellularConnection();

    boolean hasVpnConnection();

    @Nullable
    Boolean isRoaming();
}
