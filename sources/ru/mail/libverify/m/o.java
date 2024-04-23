package ru.mail.libverify.m;

import android.util.Log;
import ru.mail.libverify.platform.storage.KeyValueStorage;
import ru.mail.libverify.storage.InstanceConfig;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/m/o.class */
public final class o {
    private KeyValueStorage a;

    public o(InstanceConfig instanceConfig) {
        try {
            this.a = ((l) instanceConfig).getSettings();
        } catch (Exception e) {
            Log.e("PushTokenStorage", "key value storage obtain error", e);
        }
    }

    public final String b() {
        KeyValueStorage keyValueStorage = this.a;
        if (keyValueStorage != null) {
            return keyValueStorage.getValue("push_token_id_storage");
        }
        return null;
    }

    public final void a() {
        KeyValueStorage keyValueStorage = this.a;
        if (keyValueStorage != null) {
            keyValueStorage.removeValue("push_token_id_storage").commit();
        }
    }

    public final void a(String str) {
        KeyValueStorage keyValueStorage = this.a;
        if (keyValueStorage != null) {
            keyValueStorage.putValue("push_token_id_storage", str).commit();
        }
    }
}
