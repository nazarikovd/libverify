package ru.mail.libverify.storage;

import android.content.Context;
import androidx.annotation.NonNull;
import ru.mail.verify.core.api.ApiManager;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/storage/a.class */
public class a {
    public static String b(@NonNull Context context, ApiManager manager) {
        int i = f.d;
        String[] strArr = new String[1];
        manager.getDispatcher().postAndWait(new e(context, strArr));
        return strArr[0];
    }
}
