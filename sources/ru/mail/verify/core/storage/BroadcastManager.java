package ru.mail.verify.core.storage;

import android.content.Context;
import androidx.annotation.NonNull;
import java.util.HashSet;
import ru.mail.verify.core.utils.network.NetworkStateReceiver;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/storage/BroadcastManager.class */
public class BroadcastManager {
    private static final HashSet<Object> a = new HashSet<>();
    public static final int FLAG_NETWORK_RECEIVER = 1;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v10 */
    /* JADX WARN: Type inference failed for: r0v2 */
    /* JADX WARN: Type inference failed for: r0v9, types: [java.lang.Throwable] */
    public static void a(@NonNull Context context, @NonNull Object obj, int i) {
        if (i == 0) {
            return;
        }
        synchronized (BroadcastManager.class) {
            ?? r0 = i & 1;
            if (r0 == 1) {
                HashSet<Object> hashSet = a;
                if (hashSet.add(obj) && hashSet.size() == 1) {
                    NetworkStateReceiver.enable(context);
                }
            }
            r0 = BroadcastManager.class;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(@NonNull Context context, @NonNull Object obj) {
        synchronized (BroadcastManager.class) {
            Throwable th = null;
            HashSet<Object> hashSet = a;
            if (hashSet.remove(obj) && hashSet.isEmpty()) {
                NetworkStateReceiver.disable(context);
            }
            th = BroadcastManager.class;
        }
    }
}
