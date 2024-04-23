package ru.mail.libverify.notifications;

import android.content.Context;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.inject.Inject;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.mail.libverify.gcm.ServerNotificationMessage;
import ru.mail.libverify.platform.storage.KeyValueStorage;
import ru.mail.verify.core.ui.notifications.NotificationBase;
import ru.mail.verify.core.utils.json.JsonParser;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/notifications/f.class */
public final class f implements ru.mail.libverify.g.b {
    @NotNull
    private final Context a;
    @NotNull
    private final KeyValueStorage b;

    @Inject
    public f(@NotNull Context context, @NotNull ru.mail.libverify.m.l lVar) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(lVar, "instanceData");
        this.a = context;
        KeyValueStorage settings = lVar.getSettings();
        Intrinsics.checkNotNullExpressionValue(settings, "instanceData.settings");
        this.b = settings;
    }

    /* JADX WARN: Not initialized variable reg: 0, insn: 0x0086: INVOKE  (r0 I:java.lang.Throwable) type: VIRTUAL call: java.lang.Throwable.printStackTrace():void, block:B:20:0x0086 */
    private final Map<String, k> b() {
        Throwable printStackTrace;
        try {
            String value = this.b.getValue("server_notification_message_data");
            if (value == null) {
                return new LinkedHashMap();
            }
            HashMap mapFromJson = JsonParser.mapFromJson(value, ServerNotificationMessage.class);
            Intrinsics.checkNotNullExpressionValue(mapFromJson, "mapFromJson");
            LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt.mapCapacity(mapFromJson.size()));
            for (Object obj : mapFromJson.entrySet()) {
                linkedHashMap.put(((Map.Entry) obj).getKey(), new k(this.a, (ServerNotificationMessage) ((Map.Entry) obj).getValue(), true));
            }
            return MapsKt.toMutableMap(linkedHashMap);
        } catch (Throwable unused) {
            printStackTrace.printStackTrace();
            return new LinkedHashMap();
        }
    }

    @Override // ru.mail.libverify.g.b
    @Nullable
    public final k remove(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "key");
        Map<String, k> b = b();
        k remove = b.remove(str);
        LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt.mapCapacity(b.size()));
        Iterator<T> it = b.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            linkedHashMap.put(entry.getKey(), ((k) entry.getValue()).a());
        }
        KeyValueStorage keyValueStorage = this.b;
        String json = JsonParser.toJson(linkedHashMap);
        Intrinsics.checkNotNullExpressionValue(json, "toJson(mapValues)");
        keyValueStorage.putValue("server_notification_message_data", json).commitSync();
        return remove;
    }

    @Override // ru.mail.libverify.g.b
    public final void clear() {
        this.b.removeValue("server_notification_message_data").commitSync();
    }

    @Override // ru.mail.libverify.g.b
    public final k a(String str) {
        Intrinsics.checkNotNullParameter(str, "key");
        return b().get(str);
    }

    @Override // ru.mail.libverify.g.b
    @NotNull
    public final Map<String, k> a() {
        return b();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // ru.mail.libverify.g.b
    @Nullable
    public final k a(@NotNull NotificationBase notificationBase, @NotNull String str) {
        k kVar;
        Intrinsics.checkNotNullParameter(str, "key");
        Intrinsics.checkNotNullParameter(notificationBase, "value");
        if (notificationBase instanceof k) {
            Map<String, k> b = b();
            kVar = b.put(str, notificationBase);
            LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt.mapCapacity(b.size()));
            Iterator<T> it = b.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                linkedHashMap.put(entry.getKey(), ((k) entry.getValue()).a());
            }
            KeyValueStorage keyValueStorage = this.b;
            String json = JsonParser.toJson(linkedHashMap);
            Intrinsics.checkNotNullExpressionValue(json, "toJson(mapValues)");
            keyValueStorage.putValue("server_notification_message_data", json).commitSync();
        } else {
            kVar = null;
        }
        return kVar;
    }
}
