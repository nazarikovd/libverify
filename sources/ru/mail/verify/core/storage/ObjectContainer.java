package ru.mail.verify.core.storage;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import ru.mail.libverify.platform.storage.KeyValueStorage;
import ru.mail.verify.core.storage.PersistableObject;
import ru.mail.verify.core.utils.DebugUtils;
import ru.mail.verify.core.utils.FileLog;
import ru.mail.verify.core.utils.json.JsonParser;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/storage/ObjectContainer.class */
public abstract class ObjectContainer<T extends PersistableObject> {
    private HashMap<String, T> a = null;
    private final KeyValueStorage b;

    public ObjectContainer(@NonNull KeyValueStorage storage) {
        this.b = storage;
    }

    private void b() {
        KeyValueStorage putValue;
        HashMap<String, T> hashMap = this.a;
        if (hashMap == null) {
            return;
        }
        try {
            if (hashMap.isEmpty()) {
                putValue = this.b.removeValue(getStorageVersionKey()).removeValue(getStorageKey());
            } else {
                putValue = this.b.putValue(getStorageKey(), JsonParser.toJson(this.a)).putValue(getStorageVersionKey(), getStorageVersion());
            }
            putValue.commit();
        } catch (Exception e) {
            DebugUtils.safeThrow("ObjectContainer", "Failed to save objects", e);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void a() {
        if (this.a != null) {
            return;
        }
        this.a = new HashMap<>();
        String value = this.b.getValue(getStorageKey());
        if (TextUtils.isEmpty(value)) {
            return;
        }
        Integer integerValue = this.b.getIntegerValue(getStorageVersionKey(), (Integer) null);
        if (integerValue == null || !integerValue.equals(Integer.valueOf(getStorageVersion()))) {
            clear();
            return;
        }
        try {
            HashMap mapFromJson = JsonParser.mapFromJson(value, getObjectClass());
            if (mapFromJson == null) {
                return;
            }
            for (Map.Entry entry : mapFromJson.entrySet()) {
                long currentTimeMillis = System.currentTimeMillis() - ((PersistableObject) entry.getValue()).getTimestamp();
                if (currentTimeMillis < 0 || currentTimeMillis > getMaxTTL()) {
                    Object[] objArr = new Object[1];
                    objArr[0] = entry.getValue();
                    FileLog.d("ObjectContainer", "Skip expired object %s", objArr);
                } else {
                    this.a.put((String) entry.getKey(), (PersistableObject) entry.getValue());
                }
            }
        } catch (Throwable th) {
            clear();
            DebugUtils.safeThrow("ObjectContainer", "Failed to read objects", th);
        }
    }

    public boolean isEmpty() {
        a();
        return this.a.isEmpty();
    }

    public T put(@NonNull String key, @NonNull T value) {
        a();
        T put = this.a.put(key, value);
        b();
        return put;
    }

    public boolean contains(@NonNull String key) {
        a();
        return this.a.containsKey(key);
    }

    public T remove(@NonNull String key) {
        a();
        T remove = this.a.remove(key);
        if (remove != null) {
            b();
        }
        return remove;
    }

    public T get(@NonNull String key) {
        a();
        return this.a.get(key);
    }

    public int size() {
        a();
        return this.a.size();
    }

    public Collection<T> values() {
        a();
        return this.a.values();
    }

    public void clear() {
        HashMap<String, T> hashMap = this.a;
        if (hashMap == null) {
            return;
        }
        hashMap.clear();
        b();
    }

    protected abstract String getStorageKey();

    protected abstract String getStorageVersionKey();

    protected abstract int getStorageVersion();

    protected abstract Class<T> getObjectClass();

    protected abstract long getMaxTTL();
}
