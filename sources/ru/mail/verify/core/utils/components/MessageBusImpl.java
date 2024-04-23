package ru.mail.verify.core.utils.components;

import android.os.Message;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import dagger.Lazy;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import javax.inject.Inject;
import ru.mail.verify.core.api.ApiManager;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/utils/components/MessageBusImpl.class */
public final class MessageBusImpl implements MessageBus {
    private final LinkedHashSet<?>[] consumers = new LinkedHashSet[MessageBusUtils.values.length];
    private final Lazy<ApiManager> manager;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/utils/components/MessageBusImpl$a.class */
    public class a implements Runnable {
        final /* synthetic */ Message a;

        a(Message message) {
            this.a = message;
        }

        @Override // java.lang.Runnable
        public final void run() {
            MessageBusImpl.this.post(this.a);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Inject
    public MessageBusImpl(@NonNull Lazy<ApiManager> manager) {
        this.manager = manager;
    }

    @VisibleForTesting
    public static MessageBusImpl create(@NonNull Lazy<ApiManager> manager) {
        return null;
    }

    @Override // ru.mail.verify.core.utils.components.MessageBus
    public void post(@NonNull Message msg) {
        if (!((ApiManager) this.manager.get()).getDispatcher().isCurrentThread()) {
            ((ApiManager) this.manager.get()).getDispatcher().post(new a(msg));
            return;
        }
        int i = msg.what;
        if (i < 0 || i >= MessageBusUtils.values.length) {
            throw new IllegalArgumentException("Illegal message type");
        }
        LinkedHashSet<?> linkedHashSet = this.consumers[i];
        if (linkedHashSet == null) {
            return;
        }
        Iterator<?> it = linkedHashSet.iterator();
        while (it.hasNext()) {
            ((MessageHandler) it.next()).handleMessage(msg);
        }
    }

    @Override // ru.mail.verify.core.utils.components.MessageBus
    public void register(@NonNull Collection<BusMessageType> types, @NonNull MessageHandler handler) {
        if (!((ApiManager) this.manager.get()).getDispatcher().isCurrentThread()) {
            throw new IllegalArgumentException("Components must be registered in the dispatcher thread");
        }
        for (BusMessageType busMessageType : types) {
            LinkedHashSet<?> linkedHashSet = this.consumers[busMessageType.ordinal()];
            LinkedHashSet<?> linkedHashSet2 = linkedHashSet;
            if (linkedHashSet == null) {
                linkedHashSet2 = r1;
                LinkedHashSet<?> linkedHashSet3 = new LinkedHashSet<>();
                this.consumers[busMessageType.ordinal()] = linkedHashSet2;
            }
            linkedHashSet2.add(handler);
        }
    }
}
