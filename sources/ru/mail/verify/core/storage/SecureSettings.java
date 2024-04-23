package ru.mail.verify.core.storage;

import android.content.Context;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import ru.mail.libverify.platform.storage.KeyValueStorage;
import ru.mail.verify.core.utils.DebugUtils;
import ru.mail.verify.core.utils.FileLog;
import ru.mail.verify.core.utils.Utils;
import ru.mail.verify.core.utils.json.JsonParseException;
import ru.mail.verify.core.utils.json.JsonParser;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/storage/SecureSettings.class */
public abstract class SecureSettings implements KeyValueStorage {
    private final File a;
    private final String b;
    private volatile ConcurrentHashMap<String, String> d;
    private final ConcurrentHashMap<String, Object> c = new ConcurrentHashMap<>();
    private boolean e = false;

    public SecureSettings(@NonNull Context context, @NonNull String fileName) {
        this.b = fileName;
        this.a = a(context);
    }

    private void b() throws IOException {
        FileLog.v("SecureSettings", "initialize file read");
        String readAtomicTextFile = Utils.readAtomicTextFile(this.a);
        if (TextUtils.isEmpty(readAtomicTextFile)) {
            return;
        }
        this.d = new ConcurrentHashMap<>(JsonParser.mapFromJson(readAtomicTextFile, String.class));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v14, types: [ru.mail.verify.core.storage.SecureSettings] */
    /* JADX WARN: Type inference failed for: r0v2, types: [ru.mail.verify.core.storage.SecureSettings] */
    /* JADX WARN: Type inference failed for: r0v3, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v5 */
    /* JADX WARN: Type inference failed for: r0v8, types: [boolean] */
    private void a() {
        SecureSettings secureSettings;
        ConcurrentHashMap<String, String> concurrentHashMap;
        if (this.d == null) {
            ?? r0 = this;
            synchronized (r0) {
                if (r0.d == null) {
                    r0 = this.a.exists();
                    if (r0 != 0) {
                        try {
                            b();
                            if (this.d == null) {
                                r0 = this;
                                r0.d = new ConcurrentHashMap<>();
                            }
                        } catch (IOException e) {
                            e = e;
                            secureSettings = this;
                            FileLog.e("SecureSettings", "Failed to read settings file", e);
                            concurrentHashMap = new ConcurrentHashMap<>();
                            secureSettings.d = concurrentHashMap;
                            r0 = this;
                        } catch (JsonParseException e2) {
                            e = e2;
                            secureSettings = this;
                            FileLog.e("SecureSettings", "Failed to read settings file", e);
                            concurrentHashMap = new ConcurrentHashMap<>();
                            secureSettings.d = concurrentHashMap;
                            r0 = this;
                        } catch (Exception e3) {
                            secureSettings = this;
                            DebugUtils.safeThrow("SecureSettings", "Failed to read settings file", e3);
                            concurrentHashMap = new ConcurrentHashMap<>();
                            secureSettings.d = concurrentHashMap;
                            r0 = this;
                        }
                    } else {
                        this.d = new ConcurrentHashMap<>();
                    }
                }
                r0 = this;
            }
        }
    }

    @Nullable
    public String getValue(@NonNull String key) {
        a();
        return this.d.get(key);
    }

    @Nullable
    public Long getLongValue(@NonNull String key, @Nullable Long defaultValue) {
        Object obj = this.c.get(key);
        if (obj != null) {
            return (Long) obj;
        }
        a();
        String str = this.d.get(key);
        if (str == null) {
            return defaultValue;
        }
        try {
            long parseLong = Long.parseLong(str);
            this.c.put(key, Long.valueOf(parseLong));
            return Long.valueOf(parseLong);
        } catch (NumberFormatException unused) {
            return defaultValue;
        }
    }

    @Nullable
    public Integer getIntegerValue(@NonNull String key, @Nullable Integer defaultValue) {
        Object obj = this.c.get(key);
        if (obj != null) {
            return (Integer) obj;
        }
        a();
        String str = this.d.get(key);
        if (str == null) {
            return defaultValue;
        }
        try {
            int parseInt = Integer.parseInt(str);
            this.c.put(key, Integer.valueOf(parseInt));
            return Integer.valueOf(parseInt);
        } catch (NumberFormatException unused) {
            return defaultValue;
        }
    }

    public synchronized KeyValueStorage putValue(@NonNull String key, int value) {
        this.c.put(key, Integer.valueOf(value));
        return putValue(key, Integer.toString(value));
    }

    public synchronized KeyValueStorage removeValue(@NonNull String key) {
        a();
        this.c.remove(key);
        this.e |= this.d.remove(key) != null;
        return this;
    }

    public synchronized void commit() {
        FileLog.v("SecureSettings", "commit (%s)", Boolean.valueOf(this.e));
        if (this.e) {
            try {
                FileLog.v("SecureSettings", "initialize file write");
                long nanoTime = System.nanoTime();
                Utils.writeAtomicTextFile(JsonParser.toJson(this.d), this.a);
                Object[] objArr = new Object[1];
                objArr[0] = Long.valueOf((System.nanoTime() - nanoTime) / 1000000);
                FileLog.v("SecureSettings", "file write competed (%d ms)", objArr);
            } catch (IOException e) {
                e = e;
                FileLog.e("SecureSettings", "Failed to write settings file", e);
            } catch (JsonParseException e2) {
                e = e2;
                FileLog.e("SecureSettings", "Failed to write settings file", e);
            } catch (Exception e3) {
                DebugUtils.safeThrow("SecureSettings", "Failed to write settings file", e3);
                this.d = null;
            }
            this.e = false;
        }
    }

    public synchronized KeyValueStorage clear() {
        a();
        this.c.clear();
        this.d.clear();
        this.e = true;
        return this;
    }

    private File a(@NonNull Context context) {
        return new File(Utils.getInstallationDir(context), this.b);
    }

    public synchronized KeyValueStorage putValue(@NonNull String key, long value) {
        this.c.put(key, Long.valueOf(value));
        return putValue(key, Long.toString(value));
    }

    public synchronized KeyValueStorage putValue(@NonNull String key, @NonNull String value) {
        if (value == null) {
            String format = String.format(Locale.US, "Null value is not allowed[key = %s]", key);
            DebugUtils.safeThrow("SecureSettings", format, new IllegalArgumentException(format));
            value = "";
        }
        a();
        this.e |= !TextUtils.equals(value, this.d.put(key, value));
        return this;
    }
}
