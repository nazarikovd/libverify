package ru.mail.verify.core.gcm;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import java.util.Map;
import ru.mail.verify.core.utils.FileLog;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/gcm/a.class */
final class a {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean a(@NonNull Context context, @NonNull String str, @NonNull Bundle bundle) {
        boolean z;
        if (bundle == null) {
            FileLog.e("GcmMessageProcessor", "wrong message received ('data' is empty)");
            z = false;
        } else if (TextUtils.isEmpty(str)) {
            FileLog.e("GcmMessageProcessor", "wrong message received (either 'from' is empty)");
            z = false;
        } else if (TextUtils.equals(str, ru.mail.libverify.v.a.d(context))) {
            z = true;
        } else {
            FileLog.d("GcmMessageProcessor", "skip message from unknown server", str);
            z = false;
        }
        if (z && bundle != null) {
            return !TextUtils.isEmpty(bundle.getString("data"));
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean a(@NonNull Context context, @NonNull String str, @NonNull Map<String, String> map) {
        boolean z;
        if (map == null) {
            FileLog.e("GcmMessageProcessor", "wrong message received ('data' is empty)");
            z = false;
        } else if (TextUtils.isEmpty(str)) {
            FileLog.e("GcmMessageProcessor", "wrong message received (either 'from' is empty)");
            z = false;
        } else if (TextUtils.equals(str, ru.mail.libverify.v.a.d(context))) {
            z = true;
        } else {
            FileLog.d("GcmMessageProcessor", "skip message from unknown server", str);
            z = false;
        }
        if (z && map != null) {
            return !TextUtils.isEmpty(map.get("data"));
        }
        return false;
    }
}
