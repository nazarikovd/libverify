package ru.mail.libverify.storage;

import android.content.Context;
import androidx.annotation.NonNull;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import ru.mail.libverify.platform.storage.KeyValueStorage;
import ru.mail.libverify.sms.IncomingSmsReceiver;
import ru.mail.libverify.utils.SystemRestartReceiver;
import ru.mail.verify.core.api.FeaturesBase;
import ru.mail.verify.core.storage.BroadcastManager;
import ru.mail.verify.core.utils.FileLog;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/storage/l.class */
public final class l extends FeaturesBase {
    private static final HashMap<String, Boolean> b;
    private final Context a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public l(@NonNull KeyValueStorage keyValueStorage, @NonNull Context context) {
        super(keyValueStorage);
        this.a = context;
    }

    static {
        HashMap<String, Boolean> hashMap = new HashMap<>();
        b = hashMap;
        Boolean bool = Boolean.TRUE;
        hashMap.put("instance_broadcast_on_demand", bool);
        Boolean bool2 = Boolean.FALSE;
        hashMap.put("instance_intercept_sms", bool2);
        hashMap.put("instance_single_fetcher", bool);
        hashMap.put("instance_safety_net", bool);
        hashMap.put("instance_account_check_sms", bool2);
        hashMap.put("instance_track_package", bool2);
        hashMap.put("instance_send_call_stats", bool);
        hashMap.put("instance_update_alarms", bool);
        hashMap.put("instance_background_verify", bool);
        hashMap.put("instance_write_history", bool);
        hashMap.put("instance_add_shortcut", bool2);
    }

    @Override // ru.mail.verify.core.api.FeaturesBase
    @NonNull
    protected final Map<String, Boolean> getDefault() {
        return b;
    }

    @Override // ru.mail.verify.core.api.FeaturesBase
    protected final void process(@NonNull String str, boolean z) {
        str.getClass();
        boolean z2 = true;
        switch (str.hashCode()) {
            case -1121254165:
                if (str.equals("instance_send_call_stats")) {
                    z2 = false;
                    break;
                }
                break;
            case -873606646:
                if (str.equals("instance_write_history")) {
                    z2 = true;
                    break;
                }
                break;
            case -867566589:
                if (str.equals("instance_broadcast_on_demand")) {
                    z2 = true;
                    break;
                }
                break;
            case -565591910:
                if (str.equals("instance_safety_net")) {
                    z2 = true;
                    break;
                }
                break;
            case 179899982:
                if (str.equals("instance_update_alarms")) {
                    z2 = true;
                    break;
                }
                break;
            case 682306778:
                if (str.equals("instance_single_fetcher")) {
                    z2 = true;
                    break;
                }
                break;
            case 849252136:
                if (str.equals("instance_track_package")) {
                    z2 = true;
                    break;
                }
                break;
            case 1102840704:
                if (str.equals("instance_background_verify")) {
                    z2 = true;
                    break;
                }
                break;
            case 1271208498:
                if (str.equals("instance_intercept_sms")) {
                    z2 = true;
                    break;
                }
                break;
            case 1880219878:
                if (str.equals("instance_account_check_sms")) {
                    z2 = true;
                    break;
                }
                break;
            case 1967155598:
                if (str.equals("instance_add_shortcut")) {
                    z2 = true;
                    break;
                }
                break;
        }
        switch (z2) {
            case false:
            case BroadcastManager.FLAG_NETWORK_RECEIVER /* 1 */:
            case true:
            case true:
            case true:
            case true:
            case true:
            case true:
                return;
            case true:
                if (z) {
                    c.a(this.a, this);
                    return;
                } else {
                    c.a(this.a, this, -1);
                    return;
                }
            case true:
                if (z) {
                    if (isEnabled("instance_broadcast_on_demand")) {
                        c.a(this.a, SystemRestartReceiver.class, 32);
                        return;
                    }
                    return;
                } else if (isEnabled("instance_broadcast_on_demand")) {
                    c.a(this.a, SystemRestartReceiver.class);
                    return;
                } else {
                    return;
                }
            case true:
                if (z) {
                    if (isEnabled("instance_broadcast_on_demand")) {
                        c.a(this.a, IncomingSmsReceiver.class, 4);
                        return;
                    }
                    return;
                } else if (isEnabled("instance_broadcast_on_demand")) {
                    c.a(this.a, IncomingSmsReceiver.class);
                    return;
                } else {
                    return;
                }
            default:
                FileLog.e("VerifyFeatures", String.format(Locale.US, "Illegal feature %s in processing", str));
                throw new IllegalArgumentException("Illegal feature in processing");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void a() {
        HashMap hashMap = new HashMap();
        for (Map.Entry<String, Boolean> entry : b.entrySet()) {
            String key = entry.getKey();
            boolean isEnabled = isEnabled(entry.getKey());
            process(key, isEnabled);
            hashMap.put(key, Boolean.valueOf(isEnabled));
        }
        FileLog.v("VerifyFeatures", "current features:\n %s", hashMap);
    }
}
