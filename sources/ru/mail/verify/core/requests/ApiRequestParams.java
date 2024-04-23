package ru.mail.verify.core.requests;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.Locale;
import java.util.TreeMap;
import ru.mail.verify.core.utils.FileLog;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/requests/ApiRequestParams.class */
public class ApiRequestParams extends TreeMap<String, String> {
    private int a = 0;

    public ApiRequestParams() {
    }

    public int getCharLength() {
        return this.a;
    }

    public ApiRequestParams(String query) {
        for (String str : query.split("&")) {
            String[] split = str.split("=");
            put(split[0], split[1]);
        }
    }

    @Override // java.util.TreeMap, java.util.AbstractMap, java.util.Map
    @Nullable
    public String put(String key, String value) {
        if (key == null || value == null) {
            FileLog.e("ApiRequestParams", String.format(Locale.US, "Wrong request params key = %s, value = %s", key, value));
            throw new IllegalArgumentException("Wrong request params");
        }
        this.a = value.length() + key.length() + 2 + this.a;
        return (String) super.put((ApiRequestParams) key, value);
    }

    @Nullable
    public String put(@NonNull String key, boolean value) {
        return put(key, value ? "1" : "0");
    }
}
