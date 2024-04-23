package ru.mail.libverify.i;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;
import ru.mail.libverify.api.VerificationFactory;
import ru.mail.libverify.i.p;
import ru.mail.libverify.platform.core.ServiceType;
import ru.mail.libverify.utils.ScreenState;
import ru.mail.verify.core.requests.ApiRequestParams;
import ru.mail.verify.core.requests.RequestPersistentId;
import ru.mail.verify.core.requests.RequestSerializedData;
import ru.mail.verify.core.requests.response.ResponseBase;
import ru.mail.verify.core.utils.json.JsonParseException;
import ru.mail.verify.core.utils.json.JsonParser;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/i/j.class */
public final class j extends ru.mail.libverify.i.c<ru.mail.libverify.j.j> {
    private final k j;
    private final Long k;

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/i/j$a.class */
    static /* synthetic */ class a {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[ServiceType.values().length];
            a = iArr;
            try {
                iArr[ServiceType.Huawei.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[ServiceType.Firebase.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/i/j$b.class */
    public enum b {
        SMS_CODE,
        VERIFIED,
        SERVER_INFO,
        ROUTE_INFO,
        MOBILEID,
        DO_ATTEMPT
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/i/j$c.class */
    public enum c {
        GCM,
        FETCHER,
        UNKNOWN
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/i/j$d.class */
    public enum d {
        DELIVERED,
        SMS_ACCESS_ERROR,
        IMSI_NOT_MATCH,
        IMEI_NOT_MATCH,
        APPLICATION_ID_NOT_MATCH,
        IPC_ACCESS_ERROR,
        UNABLE_TO_SHOW,
        NO_RECEIVER,
        TTL_EXPIRED
    }

    public j() {
        throw null;
    }

    public j(@NonNull ru.mail.libverify.m.l lVar, @NonNull List list, @NonNull String str, @NonNull c cVar, @NonNull b bVar, @Nullable p.b bVar2, @Nullable Long l, @Nullable String str2, long j) {
        super(lVar);
        if (list.isEmpty()) {
            throw new IllegalArgumentException("statusData can't be empty");
        }
        this.k = l;
        this.j = new k(list, str, str2, j, cVar, bVar, bVar2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // ru.mail.libverify.i.c, ru.mail.verify.core.requests.RequestBase
    public final ApiRequestParams getMethodParams() {
        ApiRequestParams methodParams = super.getMethodParams();
        String str = "";
        for (d dVar : this.j.statusData) {
            if (!TextUtils.isEmpty(str)) {
                str = str + ",";
            }
            str = str + dVar.toString();
        }
        methodParams.put("status", str);
        if (!TextUtils.isEmpty(this.j.pushSessionId)) {
            methodParams.put("session_id", this.j.pushSessionId);
        }
        if (!TextUtils.isEmpty(this.j.pushApplicationId)) {
            methodParams.put("application_id_old", this.j.pushApplicationId);
        }
        ScreenState screenState = this.e.getScreenState();
        if (screenState.isScreenActive()) {
            methodParams.put("device_screen_active", "1");
        } else {
            methodParams.put("device_screen_active", "0");
            if (screenState.isInactiveTimeAvailable()) {
                methodParams.put("device_inactive_time", Long.toString(screenState.getInactiveTime() / 1000));
            }
        }
        p.b bVar = this.j.routeType;
        if (bVar != null) {
            methodParams.put("route_type", bVar.toString().toLowerCase());
        }
        Long l = this.k;
        if (l != null) {
            methodParams.put("push_id", l.toString());
        }
        c cVar = this.j.deliveryMethod;
        if (cVar != null) {
            if (cVar == c.GCM) {
                String str2 = "GCM";
                int i = a.a[VerificationFactory.getPlatformService(this.e.getContext()).getServiceType().ordinal()];
                if (i == 1) {
                    str2 = "HCM";
                } else if (i == 2) {
                    str2 = "GCM";
                }
                methodParams.put("delivery_method", str2);
            } else {
                methodParams.put("delivery_method", cVar.toString());
            }
        }
        b bVar2 = this.j.confirmAction;
        if (bVar2 != null) {
            methodParams.put("confirm_action", bVar2.toString());
        }
        return methodParams;
    }

    @Override // ru.mail.libverify.i.c
    protected final boolean b() {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // ru.mail.verify.core.requests.RequestBase
    public final String getMethodName() {
        return "pushstatus";
    }

    @Override // ru.mail.verify.core.requests.RequestBase
    protected final RequestPersistentId getRequestData() {
        return this.j;
    }

    @Override // ru.mail.verify.core.requests.RequestBase
    public final RequestSerializedData getSerializedData() throws JsonParseException {
        return new RequestSerializedData(JsonParser.toJson(this.j));
    }

    @Override // ru.mail.verify.core.requests.RequestBase
    public final boolean keepSystemLock() {
        return true;
    }

    public final long g() {
        return this.j.statusTimestamp;
    }

    @Nullable
    public final String f() {
        return this.j.pushSessionId;
    }

    @Override // ru.mail.verify.core.requests.RequestBase
    protected final ResponseBase parseJsonAnswer(String str) throws JsonParseException {
        return (ru.mail.libverify.j.j) JsonParser.fromJson(str, ru.mail.libverify.j.j.class);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public j(@NonNull ru.mail.libverify.m.l lVar, @NonNull RequestSerializedData requestSerializedData) throws JsonParseException {
        super(lVar);
        this.j = (k) JsonParser.fromJson(requestSerializedData.json, k.class);
        this.k = null;
    }
}
