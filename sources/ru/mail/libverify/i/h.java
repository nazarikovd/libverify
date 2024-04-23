package ru.mail.libverify.i;

import android.content.Context;
import android.net.Network;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.io.IOException;
import java.net.UnknownHostException;
import ru.mail.verify.core.api.ApplicationModule;
import ru.mail.verify.core.api.NetworkManager;
import ru.mail.verify.core.requests.ConstantRequestData;
import ru.mail.verify.core.requests.RequestPersistentId;
import ru.mail.verify.core.requests.RequestSerializedData;
import ru.mail.verify.core.requests.response.ResponseBase;
import ru.mail.verify.core.utils.ClientException;
import ru.mail.verify.core.utils.FileLog;
import ru.mail.verify.core.utils.HttpConnection;
import ru.mail.verify.core.utils.ServerException;
import ru.mail.verify.core.utils.json.JsonParseException;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/i/h.class */
public final class h extends f<ru.mail.libverify.j.h> {
    private String f;

    public h(@NonNull Context context, @NonNull NetworkManager networkManager, @NonNull ApplicationModule.ApplicationStartConfig applicationStartConfig, @NonNull String str, @Nullable Network network) {
        super(context, networkManager, applicationStartConfig, new ConstantRequestData(str, ""));
        this.f = str;
        this.customNetwork = network;
    }

    @Override // ru.mail.verify.core.requests.RequestBase
    @NonNull
    public final String getId() {
        return this.f;
    }

    @Override // ru.mail.libverify.i.f, ru.mail.verify.core.requests.RequestBase
    public final RequestSerializedData getSerializedData() throws JsonParseException {
        return null;
    }

    @Override // ru.mail.libverify.i.f, ru.mail.verify.core.requests.RequestBase
    @Nullable
    protected final String getApiHost() {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // ru.mail.libverify.i.f, ru.mail.verify.core.requests.RequestBase
    public final String getMethodName() {
        return null;
    }

    @Override // ru.mail.libverify.i.f, ru.mail.verify.core.requests.RequestBase
    protected final RequestPersistentId getRequestData() {
        return null;
    }

    @Override // ru.mail.verify.core.requests.RequestBase
    protected final ResponseBase readResponse(@NonNull HttpConnection httpConnection) throws ClientException, ServerException, IOException {
        ru.mail.libverify.j.h hVar;
        ru.mail.libverify.j.h hVar2;
        try {
            long j = 0;
            try {
                j = Long.parseLong(httpConnection.getHeaderField("Content-Length", true));
            } catch (Exception unused) {
            }
            hVar = hVar2;
            hVar2 = new ru.mail.libverify.j.h(httpConnection.getResponseCode(), httpConnection.getHeaderField("location", true), j);
        } catch (Exception e) {
            e.printStackTrace();
            FileLog.e("MobileIdRequest", "mobileId response error:", e);
            if (e instanceof UnknownHostException) {
                hVar = r0;
                ru.mail.libverify.j.h hVar3 = new ru.mail.libverify.j.h(-4, null, 0L);
            } else {
                hVar = r0;
                ru.mail.libverify.j.h hVar4 = new ru.mail.libverify.j.h(-1, null, 0L);
            }
        }
        return hVar;
    }

    @Override // ru.mail.verify.core.requests.RequestBase
    protected final /* bridge */ /* synthetic */ ResponseBase parseJsonAnswer(String str) throws JsonParseException {
        return null;
    }
}
