package ru.mail.libverify.storage;

import android.content.Context;
import androidx.annotation.Nullable;
import ru.mail.libverify.api.CommonContext;
import ru.mail.verify.core.api.ApiManager;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/storage/m.class */
public class m {
    static {
        System.loadLibrary("verify");
    }

    public native String a(Context context, CommonContext commonContext, ApiManager apiManager, String str, @Nullable String str2);
}
