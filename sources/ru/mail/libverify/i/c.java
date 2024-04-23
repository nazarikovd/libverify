package ru.mail.libverify.i;

import android.os.Handler;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import ru.mail.libverify.api.VerificationFactory;
import ru.mail.libverify.j.c;
import ru.mail.libverify.platform.core.ServiceType;
import ru.mail.libverify.storage.InstanceConfig;
import ru.mail.verify.core.accounts.SimCardData;
import ru.mail.verify.core.api.ApplicationModule;
import ru.mail.verify.core.requests.ApiRequestParams;
import ru.mail.verify.core.requests.FutureWrapper;
import ru.mail.verify.core.requests.RequestBase;
import ru.mail.verify.core.storage.InstanceConfig;
import ru.mail.verify.core.utils.ClientException;
import ru.mail.verify.core.utils.FileLog;
import ru.mail.verify.core.utils.ServerException;
import ru.mail.verify.core.utils.Utils;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/i/c.class */
public abstract class c<T extends ru.mail.libverify.j.c> extends RequestBase<T> {
    private static final String[] g = "https://clientapi.mail.ru/".split(";");
    private static final String[] h = "clientapi_mail_ru".split(";");
    private static final ApplicationModule.ApplicationStartConfig i = ru.mail.libverify.v.a.a().provideStartConfig();
    protected final InstanceConfig e;
    private final ru.mail.libverify.m.o f;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/i/c$a.class */
    public static /* synthetic */ class a {
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

    /* JADX INFO: Access modifiers changed from: protected */
    public c(@NonNull InstanceConfig instanceConfig) {
        super(instanceConfig.getContext(), instanceConfig.getNetwork(), i);
        this.e = instanceConfig;
        this.f = new ru.mail.libverify.m.o(instanceConfig);
    }

    public static void e() {
        if (g.length == 1) {
            return;
        }
        synchronized (c.class) {
            Object[] objArr = new Object[1];
            objArr[0] = 0;
            FileLog.d("ClientApiRequest", "reset api host to %d", objArr);
        }
    }

    @Override // ru.mail.verify.core.requests.RequestBase
    public final boolean switchToNextApiHost() {
        String[] strArr = g;
        if (strArr.length != 0) {
            if (strArr.length == 1) {
                return false;
            }
            synchronized (c.class) {
                if (strArr.length - 1 != 0) {
                    String str = strArr[0];
                    throw null;
                }
            }
            return false;
        }
        throw new IllegalArgumentException("Wrong api host config");
    }

    @Override // ru.mail.verify.core.requests.RequestBase
    @NonNull
    public final String getApiNameForStatistics() {
        return getMethodName();
    }

    protected String[] d() {
        return null;
    }

    @Override // ru.mail.verify.core.requests.RequestBase
    @NonNull
    protected final String getSignature(@NonNull ApiRequestParams apiRequestParams) throws UnsupportedEncodingException {
        TreeSet treeSet = new TreeSet();
        StringBuilder sb = new StringBuilder(apiRequestParams.getCharLength());
        for (Map.Entry<String, String> entry : apiRequestParams.entrySet()) {
            treeSet.add(entry.getKey() + URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        Iterator it = treeSet.iterator();
        while (it.hasNext()) {
            sb.append((String) it.next());
        }
        return URLEncoder.encode(Utils.stringToMD5(getMethodName() + sb.toString() + Utils.getHexString(this.e.getApplicationKey())), "UTF-8");
    }

    @Override // ru.mail.verify.core.requests.RequestBase
    protected String getApiHost() {
        String str;
        String[] strArr = g;
        if (strArr.length != 0) {
            if (strArr.length == 1) {
                return strArr[0];
            }
            synchronized (c.class) {
                if (strArr.length <= 0) {
                    throw new IllegalArgumentException("Wrong api host config");
                }
                str = strArr[0];
            }
            return str;
        }
        throw new IllegalArgumentException("Wrong api host config");
    }

    @Override // ru.mail.verify.core.requests.RequestBase
    protected final String getApiCertificate() {
        String str;
        String[] strArr = h;
        if (strArr.length != 0) {
            if (strArr.length == 1) {
                return strArr[0];
            }
            synchronized (c.class) {
                if (strArr.length <= 0) {
                    throw new IllegalArgumentException("Wrong api certificate config");
                }
                str = strArr[0];
            }
            return str;
        }
        throw new IllegalArgumentException("Wrong api certificate config");
    }

    @Override // ru.mail.verify.core.requests.RequestBase
    protected boolean isSignatureRequired() {
        return !(this instanceof i);
    }

    @Override // ru.mail.verify.core.requests.RequestBase
    @NonNull
    protected String getApiPath() {
        return String.format(Locale.US, "%s/%s", "fcgi-bin", getMethodName());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // ru.mail.verify.core.requests.RequestBase
    public ApiRequestParams getMethodParams() {
        ApiRequestParams apiRequestParams = new ApiRequestParams();
        if (b() && !this.e.isDisabledSimDataSend().booleanValue()) {
            SimCardData simCardData = this.e.getSimCardData();
            String simIsoCountryCode = simCardData.getSimIsoCountryCode();
            String hashedImsi = simCardData.getHashedImsi();
            String hashedImei = simCardData.getHashedImei();
            Boolean roaming = simCardData.getRoaming();
            Boolean roamingNetAllowed = simCardData.getRoamingNetAllowed();
            String simState = simCardData.getSimState();
            String simOperator = simCardData.getSimOperator();
            String networkOperator = simCardData.getNetworkOperator();
            if (!TextUtils.isEmpty(hashedImei)) {
                apiRequestParams.put("imei", hashedImei);
            }
            if (!TextUtils.isEmpty(hashedImsi)) {
                apiRequestParams.put("imsi", hashedImsi);
            }
            if (!TextUtils.isEmpty(simIsoCountryCode)) {
                apiRequestParams.put("iso_country_code", simIsoCountryCode);
            }
            if (!TextUtils.isEmpty(simState)) {
                apiRequestParams.put("sim_state", simState);
            }
            if (!TextUtils.isEmpty(simOperator)) {
                apiRequestParams.put("sim_operator", simOperator);
            }
            if (!TextUtils.isEmpty(networkOperator)) {
                apiRequestParams.put("network_operator", networkOperator);
            }
            if (roaming != null && roaming.booleanValue()) {
                apiRequestParams.put("roaming", "1");
            }
            if (roamingNetAllowed != null && roamingNetAllowed.booleanValue()) {
                apiRequestParams.put("roaming_net_allowed", "1");
            }
        }
        String str = "gps";
        int i2 = a.a[VerificationFactory.getPlatformService(this.e.getContext()).getServiceType().ordinal()];
        if (i2 == 1) {
            str = "hms";
        } else if (i2 == 2) {
            str = "gps";
        }
        apiRequestParams.put("env", str);
        apiRequestParams.put("version", this.e.getStringProperty(InstanceConfig.PropertyType.APP_VERSION));
        apiRequestParams.put("application", this.e.getApplicationName());
        apiRequestParams.put("platform", "android");
        apiRequestParams.put("application_id", this.e.getId());
        apiRequestParams.put("os_version", this.e.getStringProperty(InstanceConfig.PropertyType.OS_VERSION));
        apiRequestParams.put("libverify_version", this.e.getStringProperty(InstanceConfig.PropertyType.LIB_VERSION_NUMBER));
        apiRequestParams.put("libverify_build", this.e.getStringProperty(InstanceConfig.PropertyType.LIB_BUILD_NUMBER));
        String str2 = "call_number_fragment,call_session_hash,background_verify,ping_v2,request_id,safety_net_v3,mow,route_info,mobileid_redirects";
        String[] d = d();
        if (d != null && d.length > 0) {
            StringBuilder sb = new StringBuilder("call_number_fragment,call_session_hash,background_verify,ping_v2,request_id,safety_net_v3,mow,route_info,mobileid_redirects");
            StringBuilder sb2 = new StringBuilder();
            for (String str3 : d) {
                sb2.append(",");
                sb2.append(str3);
            }
            str2 = sb.append(sb2.toString()).toString();
        }
        apiRequestParams.put("capabilities", str2);
        String b = this.f.b();
        if (!TextUtils.isEmpty(b)) {
            apiRequestParams.put("push_token_id", b);
        }
        String stringProperty = this.e.getStringProperty(InstanceConfig.PropertyType.ADVERTISING_ID);
        if (!TextUtils.isEmpty(stringProperty)) {
            apiRequestParams.put("device_id", stringProperty);
        }
        String stringProperty2 = this.e.getStringProperty(InstanceConfig.PropertyType.DEVICE_ID_V2);
        if (!TextUtils.isEmpty(stringProperty2)) {
            apiRequestParams.put("device_id2", stringProperty2);
        }
        String stringProperty3 = this.e.getStringProperty(InstanceConfig.PropertyType.SYSTEM_ID);
        if (!TextUtils.isEmpty(stringProperty3)) {
            apiRequestParams.put("system_id", stringProperty3);
        }
        return apiRequestParams;
    }

    protected abstract boolean b();

    @Override // ru.mail.verify.core.requests.RequestBase
    @NonNull
    /* renamed from: c */
    public final T execute() throws NoSuchAlgorithmException, IllegalArgumentException, ClientException, ServerException, IOException {
        T t = (T) super.execute();
        this.e.getTimeProvider().setServerTime(t.c(), t.getSentTimestamp(), t.getReceiveTimestamp());
        return t;
    }

    @Override // ru.mail.verify.core.requests.RequestBase
    @NonNull
    public final Future<T> executeAsync(@NonNull ExecutorService executorService, @NonNull Handler handler, @Nullable FutureWrapper.FutureListener<T> futureListener) {
        return new FutureWrapper(executorService, handler, this::execute, null, futureListener).execute();
    }
}
