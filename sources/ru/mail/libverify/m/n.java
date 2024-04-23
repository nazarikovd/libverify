package ru.mail.libverify.m;

import android.util.Log;
import ru.mail.libverify.api.CommonContext;
import ru.mail.libverify.platform.storage.KeyValueStorage;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/m/n.class */
public final class n {
    private KeyValueStorage a;

    public n(CommonContext commonContext) {
        try {
            this.a = commonContext.getSettings();
        } catch (Exception e) {
            Log.e("PushTokenStorage", "key value storage obtain error", e);
        }
    }

    public final String b() {
        KeyValueStorage keyValueStorage = this.a;
        if (keyValueStorage != null) {
            return keyValueStorage.getValue("jws_id_storage");
        }
        return null;
    }

    public final void a() {
        KeyValueStorage keyValueStorage = this.a;
        if (keyValueStorage != null) {
            keyValueStorage.removeValue("jws_id_storage").commit();
        }
    }

    public final void a(String str) {
        KeyValueStorage keyValueStorage = this.a;
        if (keyValueStorage != null) {
            keyValueStorage.putValue("jws_id_storage", str).commit();
        }
    }
}
