package ru.mail.libverify.api;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import ru.mail.libverify.api.q;
import ru.mail.libverify.gcm.ServerNotificationMessage;
import ru.mail.verify.core.utils.DebugUtils;
import ru.mail.verify.core.utils.components.BusMessageType;
import ru.mail.verify.core.utils.components.MessageBus;
import ru.mail.verify.core.utils.components.MessageBusUtils;
import ru.mail.verify.core.utils.json.JsonParser;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/m.class */
public final class m {
    private final CommonContext b;
    private final MessageBus c;
    private HashMap<String, ServerNotificationMessage> a = null;
    private final LinkedList<ServerNotificationMessage> d = new LinkedList<>();
    private Runnable e = new a();

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/m$a.class */
    class a implements Runnable {
        a() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            m.this.d();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public m(@NonNull CommonContext commonContext) {
        this.b = commonContext;
        this.c = ((q.f) commonContext).getBus();
    }

    private void d() {
        HashMap<String, ServerNotificationMessage> hashMap = this.a;
        if (hashMap == null) {
            return;
        }
        try {
            (hashMap.isEmpty() ? this.b.getSettings().removeValue("api_popup_notifications_data") : this.b.getSettings().putValue("api_popup_notifications_data", JsonParser.toJson(this.a))).commit();
        } catch (Exception e) {
            DebugUtils.safeThrow("PopupMessageContainer", "Failed to save popup notifications", e);
        }
        Iterator<ServerNotificationMessage> it = this.d.iterator();
        while (it.hasNext()) {
            this.c.post(MessageBusUtils.createOneArg(BusMessageType.POPUP_CONTAINER_NOTIFICATION_REMOVED, it.next()));
        }
        this.d.clear();
    }

    private void c() {
        if (this.a != null) {
            return;
        }
        this.a = new HashMap<>();
        String value = this.b.getSettings().getValue("api_popup_notifications_data");
        if (TextUtils.isEmpty(value)) {
            return;
        }
        try {
            HashMap mapFromJson = JsonParser.mapFromJson(value, ServerNotificationMessage.class);
            if (mapFromJson == null) {
                return;
            }
            for (Map.Entry entry : mapFromJson.entrySet()) {
                long localTime = this.b.getConfig().getTimeProvider().getLocalTime() - ((ServerNotificationMessage) entry.getValue()).getLocalTimestamp();
                if (!(localTime < 0 || localTime > 43200000)) {
                    this.a.put((String) entry.getKey(), (ServerNotificationMessage) entry.getValue());
                    this.c.post(MessageBusUtils.createOneArg(BusMessageType.POPUP_CONTAINER_NOTIFICATION_ADDED, entry.getValue()));
                }
            }
        } catch (Throwable th) {
            a();
            DebugUtils.safeThrow("PopupMessageContainer", "Failed to read popup notifications", th);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean b() {
        c();
        return this.a.isEmpty();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final Collection<ServerNotificationMessage> e() {
        c();
        return this.a.values();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void a() {
        if (this.a == null) {
            return;
        }
        this.d.clear();
        this.d.addAll(this.a.values());
        this.a.clear();
        d();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final ServerNotificationMessage b(@NonNull String str) {
        c();
        ServerNotificationMessage remove = this.a.remove(str);
        if (remove != null) {
            this.d.add(remove);
            this.b.getDispatcher().removeCallbacks(this.e);
            this.b.getDispatcher().postDelayed(this.e, 200L);
        }
        return remove;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final ServerNotificationMessage a(@NonNull String str) {
        c();
        return this.a.get(str);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void a(@NonNull String str, @NonNull ServerNotificationMessage serverNotificationMessage) {
        c();
        ServerNotificationMessage put = this.a.put(str, serverNotificationMessage);
        if (put == null) {
            this.c.post(MessageBusUtils.createOneArg(BusMessageType.POPUP_CONTAINER_NOTIFICATION_ADDED, serverNotificationMessage));
        } else {
            this.c.post(MessageBusUtils.createOneArg(BusMessageType.POPUP_CONTAINER_NOTIFICATION_ADDED, serverNotificationMessage));
            this.c.post(MessageBusUtils.createOneArg(BusMessageType.POPUP_CONTAINER_NOTIFICATION_REMOVED, put));
        }
        this.b.getDispatcher().removeCallbacks(this.e);
        this.b.getDispatcher().postDelayed(this.e, 200L);
    }
}
