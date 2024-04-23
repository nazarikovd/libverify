package ru.mail.libverify.storage;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import dagger.Lazy;
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import ru.mail.libverify.R;
import ru.mail.libverify.fetcher.FetcherService;
import ru.mail.libverify.platform.storage.KeyValueStorage;
import ru.mail.libverify.utils.BatteryLevelReceiver;
import ru.mail.libverify.utils.ScreenState;
import ru.mail.libverify.utils.ScreenStateReceiver;
import ru.mail.libverify.utils.network.NetworkCheckService;
import ru.mail.verify.core.accounts.SimCardData;
import ru.mail.verify.core.accounts.SimCardReader;
import ru.mail.verify.core.api.AlarmManager;
import ru.mail.verify.core.api.NetworkManager;
import ru.mail.verify.core.api.ResourceParamsBase;
import ru.mail.verify.core.gcm.GcmRegistrar;
import ru.mail.verify.core.platform.TimeProvider;
import ru.mail.verify.core.platform.TimeProviderImpl;
import ru.mail.verify.core.storage.InstanceConfig;
import ru.mail.verify.core.storage.LocationProvider;
import ru.mail.verify.core.utils.FileLog;
import ru.mail.verify.core.utils.Utils;
import ru.mail.verify.core.utils.VerificationServiceProcessor;
import ru.mail.verify.core.utils.json.JsonParseException;
import ru.mail.verify.core.utils.json.JsonParser;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/storage/h.class */
public class h extends ru.mail.libverify.x.a implements ru.mail.libverify.m.l {
    private final Lazy<NetworkManager> p;
    private final Lazy<LocationProvider> q;
    private final Lazy<GcmRegistrar> r;
    private final Lazy<AlarmManager> s;
    private final Lazy<SimCardReader> t;
    private final Lazy<PhoneNumberUtil> u;
    private final ResourceParamsBase v;
    private volatile String w;
    private volatile j x;
    private volatile ru.mail.libverify.k.d y;
    private volatile l z;
    private volatile HashMap A;

    /* JADX INFO: Access modifiers changed from: package-private */
    @Inject
    public h(@NonNull Context context, @NonNull ResourceParamsBase resourceParamsBase, @NonNull Lazy<AlarmManager> lazy, @NonNull Lazy<GcmRegistrar> lazy2, @NonNull Lazy<LocationProvider> lazy3, @NonNull Lazy<NetworkManager> lazy4, @NonNull Lazy<KeyValueStorage> lazy5, @NonNull Lazy<SimCardReader> lazy6, @NonNull Lazy<PhoneNumberUtil> lazy7) {
        super(context, lazy5);
        this.p = lazy4;
        this.v = resourceParamsBase;
        this.q = lazy3;
        this.r = lazy2;
        this.s = lazy;
        this.t = lazy6;
        this.u = lazy7;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v4, types: [ru.mail.libverify.storage.h] */
    /* JADX WARN: Type inference failed for: r0v5, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v7 */
    private j k() {
        if (this.x == null) {
            ?? r0 = this;
            synchronized (r0) {
                if (r0.x == null) {
                    this.x = new j(getSettings());
                }
                r0 = this;
            }
        }
        return this.x;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v4, types: [ru.mail.libverify.storage.h] */
    /* JADX WARN: Type inference failed for: r0v5, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v7 */
    private l j() {
        if (this.z == null) {
            ?? r0 = this;
            synchronized (r0) {
                if (r0.z == null) {
                    this.z = new l((KeyValueStorage) this.o.get(), this.n);
                }
                r0 = this;
            }
        }
        return this.z;
    }

    @Override // ru.mail.verify.core.storage.InstanceConfig
    @NonNull
    public String getId() {
        return f.d(this.n);
    }

    @Override // ru.mail.libverify.storage.InstanceConfig
    public final String getApplicationName() {
        return this.v.getName();
    }

    @Override // ru.mail.libverify.storage.InstanceConfig
    public final String getApplicationKey() {
        return this.v.getKey();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v4, types: [ru.mail.libverify.storage.h] */
    /* JADX WARN: Type inference failed for: r0v5, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v7 */
    public String getHashedId() {
        if (this.w == null) {
            ?? r0 = this;
            synchronized (r0) {
                if (r0.w == null) {
                    this.w = Utils.stringToSHA256(getId());
                }
                r0 = this;
            }
        }
        return this.w;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // ru.mail.verify.core.storage.InstanceData, ru.mail.libverify.m.l
    public final void resetId() {
        synchronized (this) {
            this.w = null;
        }
        f.e(this.n);
        k().b();
    }

    @Override // ru.mail.libverify.m.l
    @NonNull
    public final KeyValueStorage getSettings() {
        return (KeyValueStorage) this.o.get();
    }

    @Override // ru.mail.libverify.storage.InstanceConfig
    @NonNull
    public final ScreenState getScreenState() {
        return ScreenStateReceiver.a(this.n);
    }

    @Override // ru.mail.libverify.storage.InstanceConfig
    public final NetworkManager getNetwork() {
        return (NetworkManager) this.p.get();
    }

    @Override // ru.mail.libverify.storage.InstanceConfig
    public final GcmRegistrar getRegistrar() {
        return (GcmRegistrar) this.r.get();
    }

    @Override // ru.mail.libverify.storage.InstanceConfig
    public final boolean isLowBattery() {
        return BatteryLevelReceiver.a();
    }

    @Override // ru.mail.libverify.storage.InstanceConfig
    public final boolean isCallUiFeatureEnable() {
        return Boolean.parseBoolean(this.n.getString(R.string.libverify_support_feature_callui));
    }

    @Override // ru.mail.libverify.storage.InstanceConfig
    @NonNull
    public final SimCardData getSimCardData() {
        return ((SimCardReader) this.t.get()).getSimCardData();
    }

    @Override // ru.mail.libverify.storage.InstanceConfig
    @NonNull
    public final ru.mail.libverify.k.c getKnownSmsFinder() {
        if (this.y == null) {
            synchronized (this) {
                if (this.y == null) {
                    this.y = new ru.mail.libverify.k.d(this.n);
                }
            }
        }
        return this.y;
    }

    public String getServerKey() {
        return k().a();
    }

    @Override // ru.mail.libverify.storage.InstanceConfig
    public final String getExtendedPhoneInfo() {
        if (j().isEnabled("instance_send_call_stats")) {
            return ru.mail.libverify.m.c.a(this, (NetworkManager) this.p.get());
        }
        return null;
    }

    @Override // ru.mail.libverify.storage.InstanceConfig
    public final String decryptServerMessage(@NonNull String str, @NonNull String str2) throws DecryptionError {
        return k().a(str, str2);
    }

    @Override // ru.mail.verify.core.storage.InstanceData, ru.mail.libverify.m.l
    public final void prepare() {
        FileLog.v("InstanceData", "prepare internal members");
        ScreenStateReceiver.b(this.n);
        getId();
        getStringProperty(InstanceConfig.PropertyType.ADVERTISING_ID);
        getStringProperty(InstanceConfig.PropertyType.SYSTEM_ID);
        getServerKey();
        j().a();
    }

    @Override // ru.mail.libverify.m.l
    public final boolean setApiEndpoints(@NonNull Map<String, String> map) {
        try {
            HashMap hashMap = new HashMap();
            String[] split = "https://clientapi.mail.ru/".split(";");
            if (split.length != 0) {
                for (String str : split) {
                    String host = Uri.parse(str).getHost();
                    if (TextUtils.isEmpty(host)) {
                        throw new IllegalArgumentException("Host name must be non empty");
                    }
                    String str2 = map.get(host);
                    if (!TextUtils.isEmpty(str2) && !TextUtils.equals(host, str2)) {
                        hashMap.put(host, str2);
                    }
                }
                if (hashMap.isEmpty()) {
                    FileLog.d("InstanceData", "reset api endpoints");
                    this.A = new HashMap();
                    getSettings().removeValue("instance_api_endpoints").commit();
                    return false;
                }
                FileLog.d("InstanceData", "set api endpoints %s", hashMap);
                this.A = hashMap;
                getSettings().putValue("instance_api_endpoints", JsonParser.toJson(hashMap)).commit();
                return true;
            }
            throw new IllegalArgumentException("At least one api host must be provided");
        } catch (Exception e) {
            FileLog.e("InstanceData", "failed to set api endpoints", e);
            return false;
        }
    }

    @Override // ru.mail.libverify.m.l
    @NonNull
    public final InstanceConfig e() {
        FileLog.v("InstanceData", "create new immutable config");
        return new d(this, this.n, this.v, this.s, this.r, this.q, this.p, this.o, this.t, this.u);
    }

    @Override // ru.mail.libverify.m.l
    public final void acquireLock(@NonNull Object obj, boolean z, int i) {
        VerificationServiceProcessor.acquire(this.n, obj, z);
        if (j().isEnabled("instance_broadcast_on_demand")) {
            c.a(this.n, obj, i);
        }
    }

    @Override // ru.mail.libverify.m.l
    public final void releaseLock(@NonNull Object obj) {
        VerificationServiceProcessor.release(this.n, obj);
        if (j().isEnabled("instance_broadcast_on_demand")) {
            c.a(this.n, obj);
        }
    }

    @Override // ru.mail.libverify.m.l
    public final void releaseAllLocks() {
        VerificationServiceProcessor.releaseAll(this.n);
    }

    @Override // ru.mail.libverify.m.l
    public final void f() {
        NetworkCheckService.a(this.n);
    }

    @Override // ru.mail.libverify.m.l
    public final boolean c(@NonNull String str) {
        return j().isEnabled(str);
    }

    @Override // ru.mail.libverify.m.l
    @Nullable
    public final ru.mail.libverify.j.l b() {
        return k.a(this.n);
    }

    @Override // ru.mail.libverify.storage.InstanceConfig
    public final boolean checkInstanceHasNewerVersion(@Nullable String str) {
        return ru.mail.libverify.r.b.a(this.n, str);
    }

    @Override // ru.mail.libverify.storage.InstanceConfig
    @NonNull
    public final TimeProvider getTimeProvider() {
        return new TimeProviderImpl((KeyValueStorage) this.o.get());
    }

    @Override // ru.mail.libverify.storage.InstanceConfig
    @NonNull
    public final PhoneNumberUtil getPhoneNumberUtil() {
        return (PhoneNumberUtil) this.u.get();
    }

    @NonNull
    public Map<String, String> getApiEndpoints() {
        HashMap hashMap = this.A;
        if (hashMap != null) {
            return hashMap;
        }
        String value = getSettings().getValue("instance_api_endpoints");
        if (this.A == null) {
            synchronized (this) {
                if (this.A == null) {
                    try {
                        if (TextUtils.isEmpty(value)) {
                            this.A = new HashMap();
                        } else {
                            this.A = JsonParser.mapFromJson(value, String.class);
                        }
                    } catch (JsonParseException e) {
                        FileLog.e("InstanceData", "failed to restore api endpoints", e);
                        getSettings().removeValue("instance_api_endpoints").commit();
                        this.A = new HashMap();
                    }
                }
            }
        }
        return this.A;
    }

    @Override // ru.mail.libverify.storage.InstanceConfig
    @Nullable
    public final String getApiProxyDomain() {
        return getSettings().getValue("instance_api_proxy_domain");
    }

    @Override // ru.mail.verify.core.storage.InstanceData, ru.mail.libverify.m.l
    public final boolean sendApplicationBroadcast(@NonNull String str, @Nullable Map<String, String> map) {
        Intent intent = new Intent(str);
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                intent.putExtra(entry.getKey(), entry.getValue());
            }
        }
        return LocalBroadcastManager.getInstance(this.n).sendBroadcast(intent);
    }

    @Override // ru.mail.libverify.m.l
    public final boolean a(@NonNull String str) {
        ru.mail.libverify.j.l a;
        if (TextUtils.isEmpty(str) || (a = k.a(this.n)) == null || a.d() == null) {
            return false;
        }
        return a.d().contains(str);
    }

    @Override // ru.mail.libverify.m.l
    public final void d() {
        if (j().isEnabled("instance_broadcast_on_demand")) {
            c.a(this.n, FetcherService.class, 56);
        }
        ru.mail.libverify.fetcher.a.a(this.n);
    }

    @Override // ru.mail.libverify.m.l
    public final boolean b(@NonNull String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        getSettings().putValue("instance_api_proxy_domain", str).commit();
        return true;
    }

    @Override // ru.mail.libverify.m.l
    public final void c() {
        getSettings().removeValue("instance_api_proxy_domain").commit();
    }

    @Override // ru.mail.libverify.m.l
    public final boolean a() {
        return k.b(this.n);
    }

    @Override // ru.mail.libverify.m.l
    public final void a(@NonNull String str, @Nullable Boolean bool) {
        j().set(str, bool);
    }

    @Override // ru.mail.libverify.m.l
    public final void a(@Nullable ru.mail.libverify.j.l lVar) {
        k.a(this.n, lVar);
    }

    @Override // ru.mail.libverify.m.l
    public final void a(boolean z) {
        if (j().isEnabled("instance_broadcast_on_demand") && z) {
            c.a(this.n, FetcherService.class);
        }
        ru.mail.libverify.fetcher.a.b(this.n);
    }
}
