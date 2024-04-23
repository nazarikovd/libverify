package ru.mail.libverify.x;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import dagger.Lazy;
import java.io.File;
import java.util.Locale;
import ru.mail.libverify.R;
import ru.mail.libverify.api.VerificationFactory;
import ru.mail.libverify.platform.core.PlatformCoreService;
import ru.mail.libverify.platform.storage.KeyValueStorage;
import ru.mail.verify.core.api.ApiManager;
import ru.mail.verify.core.storage.BroadcastManager;
import ru.mail.verify.core.storage.InstanceConfig;
import ru.mail.verify.core.storage.InstanceData;
import ru.mail.verify.core.utils.FileLog;
import ru.mail.verify.core.utils.Utils;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/x/a.class */
public abstract class a implements InstanceData {
    private volatile String a;
    private volatile Boolean c;
    private volatile Integer d;
    private volatile String e;
    private volatile String f;
    private volatile String g;
    private volatile Locale h;
    private volatile Boolean i;
    private volatile File l;
    private volatile Boolean m;
    protected volatile Context n;
    protected final Lazy<KeyValueStorage> o;
    private volatile boolean j = false;
    private volatile boolean k = false;
    private final String b = "VERIFY_CACHE";

    /* renamed from: ru.mail.libverify.x.a$a  reason: collision with other inner class name */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/x/a$a.class */
    class RunnableC0025a implements Runnable {
        final /* synthetic */ String[] a;
        final /* synthetic */ InstanceConfig.PropertyType b;

        RunnableC0025a(String[] strArr, InstanceConfig.PropertyType propertyType) {
            this.a = strArr;
            this.b = propertyType;
        }

        @Override // java.lang.Runnable
        public final void run() {
            this.a[0] = a.this.getStringProperty(this.b);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/x/a$b.class */
    public static /* synthetic */ class b {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[InstanceConfig.PropertyType.values().length];
            a = iArr;
            try {
                iArr[InstanceConfig.PropertyType.APP_VERSION.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[InstanceConfig.PropertyType.ADVERTISING_ID.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[InstanceConfig.PropertyType.DEVICE_ID_V2.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                a[InstanceConfig.PropertyType.SYSTEM_ID.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                a[InstanceConfig.PropertyType.DEVICE_NAME.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                a[InstanceConfig.PropertyType.DEVICE_VENDOR.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                a[InstanceConfig.PropertyType.TIME_ZONE.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                a[InstanceConfig.PropertyType.OS_VERSION.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                a[InstanceConfig.PropertyType.LIB_BUILD_NUMBER.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                a[InstanceConfig.PropertyType.LIB_VERSION_NUMBER.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                a[InstanceConfig.PropertyType.CORE_COUNT.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                a[InstanceConfig.PropertyType.RAM_SIZE.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                a[InstanceConfig.PropertyType.SCREEN_HEIGHT.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                a[InstanceConfig.PropertyType.SCREEN_WIDTH.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                a[InstanceConfig.PropertyType.DEVICE_TYPE.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
        }
    }

    public a(@NonNull Context context, @NonNull Lazy lazy) {
        this.n = context;
        this.o = lazy;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private Integer h() {
        if (this.d == null) {
            synchronized (this) {
                if (this.d == null) {
                    ActivityManager activityManager = (ActivityManager) this.n.getSystemService("activity");
                    if (activityManager == null) {
                        return null;
                    }
                    ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
                    activityManager.getMemoryInfo(memoryInfo);
                    this.d = Integer.valueOf((int) (memoryInfo.totalMem / 1000000));
                }
            }
        }
        return this.d;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v10 */
    /* JADX WARN: Type inference failed for: r0v7, types: [ru.mail.libverify.x.a] */
    /* JADX WARN: Type inference failed for: r0v8, types: [java.lang.Throwable] */
    @Nullable
    private String g() {
        PlatformCoreService platformService = VerificationFactory.getPlatformService(this.n);
        if (platformService == null) {
            return null;
        }
        if (this.f == null) {
            ?? r0 = this;
            synchronized (r0) {
                if (r0.f == null) {
                    this.f = platformService.getIDv2ProviderService(this.n).get();
                }
                r0 = this;
            }
        }
        return this.f;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v4, types: [ru.mail.libverify.x.a] */
    /* JADX WARN: Type inference failed for: r0v5, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v7 */
    private String i() {
        if (this.g == null) {
            ?? r0 = this;
            synchronized (r0) {
                if (r0.g == null) {
                    this.g = Utils.getSystemId(this.n);
                }
                r0 = this;
            }
        }
        return this.g;
    }

    @Override // ru.mail.verify.core.storage.InstanceConfig
    @NonNull
    public final Context getContext() {
        return this.n;
    }

    @Override // ru.mail.verify.core.storage.InstanceConfig
    public final String getStringProperty(InstanceConfig.PropertyType propertyType) {
        switch (b.a[propertyType.ordinal()]) {
            case BroadcastManager.FLAG_NETWORK_RECEIVER /* 1 */:
                if (this.a == null) {
                    this.a = Integer.toString(Utils.getAppVersion(this.n));
                }
                return this.a;
            case 2:
                KeyValueStorage keyValueStorage = (KeyValueStorage) this.o.get();
                PlatformCoreService platformService = VerificationFactory.getPlatformService(this.n);
                if (platformService == null) {
                    return "";
                }
                if (this.e == null) {
                    synchronized (this) {
                        if (this.e == null) {
                            this.e = platformService.obtainAdvertisingId(this.n, keyValueStorage);
                        }
                    }
                }
                return this.e;
            case 3:
                return g();
            case 4:
                return i();
            case 5:
                return Build.MODEL;
            case 6:
                return Build.MANUFACTURER;
            case 7:
                return Utils.getTimeZone();
            case 8:
                return Build.VERSION.RELEASE;
            case 9:
                return "264";
            case 10:
                return "2.11.0";
            case 11:
                Integer valueOf = Build.VERSION.SDK_INT >= 17 ? Integer.valueOf(Runtime.getRuntime().availableProcessors()) : null;
                if (valueOf == null) {
                    return null;
                }
                return Integer.toString(valueOf.intValue());
            case 12:
                Integer h = h();
                if (h == null) {
                    return null;
                }
                return Integer.toString(h.intValue());
            case 13:
                return Integer.toString(Resources.getSystem().getDisplayMetrics().heightPixels);
            case 14:
                return Integer.toString(Resources.getSystem().getDisplayMetrics().widthPixels);
            case 15:
                if (this.c == null) {
                    this.c = Boolean.valueOf(Utils.isTablet(this.n));
                }
                return this.c.booleanValue() ? InstanceConfig.DEVICE_TYPE_TABLET : InstanceConfig.DEVICE_TYPE_PHONE;
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override // ru.mail.verify.core.storage.InstanceConfig
    public final Integer getIntProperty(InstanceConfig.PropertyType propertyType) {
        switch (b.a[propertyType.ordinal()]) {
            case BroadcastManager.FLAG_NETWORK_RECEIVER /* 1 */:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 11:
                Integer valueOf = Build.VERSION.SDK_INT >= 17 ? Integer.valueOf(Runtime.getRuntime().availableProcessors()) : null;
                if (valueOf == null) {
                    valueOf = null;
                }
                return valueOf;
            case 9:
            case 10:
            default:
                throw new IllegalArgumentException();
            case 12:
                Integer h = h();
                Integer num = h;
                if (h == null) {
                    num = null;
                }
                return num;
            case 13:
                return Integer.valueOf(Resources.getSystem().getDisplayMetrics().heightPixels);
            case 14:
                return Integer.valueOf(Resources.getSystem().getDisplayMetrics().widthPixels);
            case 15:
                if (this.c == null) {
                    this.c = Boolean.valueOf(Utils.isTablet(this.n));
                }
                return Integer.valueOf(this.c.booleanValue() ? 1 : 0);
        }
    }

    @Override // ru.mail.verify.core.storage.InstanceConfig
    public final String getStringPropertySync(InstanceConfig.PropertyType propertyType, ApiManager apiManager) {
        String[] strArr = new String[1];
        apiManager.getDispatcher().postAndWait(new RunnableC0025a(strArr, propertyType));
        return strArr[0];
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v10, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v12, types: [ru.mail.libverify.x.a] */
    /* JADX WARN: Type inference failed for: r0v9, types: [ru.mail.libverify.x.a] */
    @Override // ru.mail.verify.core.storage.InstanceConfig
    public Locale getCurrentLocale() {
        if (this.h == null && !this.k) {
            ?? r0 = this;
            synchronized (r0) {
                if (r0.h == null) {
                    String value = ((KeyValueStorage) this.o.get()).getValue("instance_custom_locale");
                    if (!TextUtils.isEmpty(value)) {
                        this.h = Utils.getLocaleFromUnixId(value);
                    }
                }
                r0 = this;
                r0.k = true;
            }
        }
        return this.h == null ? Utils.getCurrentLocale() : this.h;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v13, types: [ru.mail.libverify.x.a] */
    /* JADX WARN: Type inference failed for: r0v14, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v16, types: [ru.mail.libverify.x.a] */
    @Override // ru.mail.verify.core.storage.InstanceConfig
    @NonNull
    public final Boolean isDisabledSimDataSend() {
        if (this.i == null && !this.j) {
            ?? r0 = this;
            synchronized (r0) {
                if (r0.i == null) {
                    Integer integerValue = ((KeyValueStorage) this.o.get()).getIntegerValue("instance_disable_sim_data_send", (Integer) null);
                    if (integerValue != null) {
                        this.i = Boolean.valueOf(integerValue.intValue() > 0);
                    }
                }
                r0 = this;
                r0.j = true;
            }
        }
        if (this.i != null) {
            return this.i;
        }
        if (this.m == null) {
            this.m = Boolean.valueOf(this.n.getString(R.string.libverify_default_disable_sim_data_send));
        }
        return this.m;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v4, types: [ru.mail.libverify.x.a] */
    /* JADX WARN: Type inference failed for: r0v5, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v7 */
    @Override // ru.mail.verify.core.storage.InstanceConfig
    public final File getCacheFolder() {
        if (this.l == null) {
            ?? r0 = this;
            synchronized (r0) {
                if (r0.l == null) {
                    File file = new File(this.n.getCacheDir().getAbsolutePath() + "/" + this.b);
                    if (!file.exists() && !file.mkdirs()) {
                        FileLog.e("InstanceData", "Failed to create cache folder");
                    }
                    this.l = file;
                }
                r0 = this;
            }
        }
        return this.l;
    }

    @Override // ru.mail.verify.core.storage.InstanceData
    public final void setCustomLocale(@NonNull Locale locale) {
        this.h = locale;
        ((KeyValueStorage) this.o.get()).putValue("instance_custom_locale", Utils.getLocaleUnixId(locale)).commitSync();
    }

    @Override // ru.mail.verify.core.storage.InstanceData
    public final void setSimDataSendDisabled(boolean z) {
        this.i = Boolean.valueOf(z);
        ((KeyValueStorage) this.o.get()).putValue("instance_disable_sim_data_send", z ? 1 : 0).commitSync();
    }
}
