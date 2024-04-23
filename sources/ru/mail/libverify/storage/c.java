package ru.mail.libverify.storage;

import android.content.Context;
import androidx.annotation.NonNull;
import java.util.HashSet;
import ru.mail.libverify.api.VerificationFactory;
import ru.mail.libverify.platform.core.IPlatformUtils;
import ru.mail.libverify.platform.core.PlatformCoreService;
import ru.mail.libverify.sms.IncomingCallReceiver;
import ru.mail.libverify.sms.IncomingSmsReceiver;
import ru.mail.libverify.utils.BatteryLevelReceiver;
import ru.mail.libverify.utils.SystemRestartReceiver;
import ru.mail.verify.core.utils.network.NetworkStateReceiver;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/storage/c.class */
public final class c {
    private static final HashSet<Object> a = new HashSet<>();
    private static final HashSet<Object> b = new HashSet<>();
    private static final HashSet<Object> c = new HashSet<>();
    private static final HashSet<Object> d = new HashSet<>();
    private static final HashSet<Object> e = new HashSet<>();

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v13, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v22 */
    /* JADX WARN: Type inference failed for: r0v6 */
    public static void a(@NonNull Context context, @NonNull Object obj, int i) {
        IPlatformUtils utils;
        PlatformCoreService platformService = VerificationFactory.getPlatformService(context);
        if (platformService == null || (utils = platformService.getUtils()) == null || i == 0) {
            return;
        }
        synchronized (c.class) {
            ?? r0 = i & 1;
            if (r0 == 1) {
                HashSet<Object> hashSet = b;
                if (hashSet.add(obj) && hashSet.size() == 1) {
                    utils.enableReceiver(context, IncomingCallReceiver.class);
                }
            }
            if ((i & 4) == 4) {
                HashSet<Object> hashSet2 = c;
                if (hashSet2.add(obj) && hashSet2.size() == 1) {
                    utils.enableReceiver(context, IncomingSmsReceiver.class);
                }
            }
            if ((i & 8) == 8) {
                HashSet<Object> hashSet3 = a;
                if (hashSet3.add(obj) && hashSet3.size() == 1) {
                    NetworkStateReceiver.enable(context);
                }
            }
            if ((i & 16) == 16) {
                HashSet<Object> hashSet4 = d;
                if (hashSet4.add(obj) && hashSet4.size() == 1) {
                    utils.enableReceiver(context, BatteryLevelReceiver.class);
                }
            }
            if ((i & 32) == 32) {
                HashSet<Object> hashSet5 = e;
                if (hashSet5.add(obj) && hashSet5.size() == 1) {
                    utils.enableReceiver(context, SystemRestartReceiver.class);
                }
            }
            r0 = c.class;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Multi-variable type inference failed */
    public static void a(@NonNull Context context, @NonNull Object obj) {
        synchronized (c.class) {
            PlatformCoreService platformService = VerificationFactory.getPlatformService(context);
            if (platformService == null) {
                return;
            }
            IPlatformUtils utils = platformService.getUtils();
            if (utils == null) {
                return;
            }
            HashSet<Object> hashSet = b;
            if (hashSet.remove(obj) && hashSet.isEmpty()) {
                utils.disableReceiver(context, IncomingCallReceiver.class);
            }
            HashSet<Object> hashSet2 = c;
            if (hashSet2.remove(obj) && hashSet2.isEmpty()) {
                utils.disableReceiver(context, IncomingSmsReceiver.class);
            }
            HashSet<Object> hashSet3 = d;
            if (hashSet3.remove(obj) && hashSet3.isEmpty()) {
                utils.disableReceiver(context, BatteryLevelReceiver.class);
            }
            HashSet<Object> hashSet4 = a;
            if (hashSet4.remove(obj) && hashSet4.isEmpty()) {
                NetworkStateReceiver.disable(context);
            }
            HashSet<Object> hashSet5 = e;
            if (hashSet5.remove(obj) && hashSet5.isEmpty()) {
                utils.disableReceiver(context, SystemRestartReceiver.class);
            }
        }
    }
}
