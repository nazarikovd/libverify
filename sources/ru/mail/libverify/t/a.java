package ru.mail.libverify.t;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import java.util.HashMap;
import kotlin.Unit;
import ru.mail.libverify.api.CommonContext;
import ru.mail.libverify.api.VerificationFactory;
import ru.mail.libverify.platform.core.IPlatformUtils;
import ru.mail.libverify.platform.core.PlatformCoreService;
import ru.mail.libverify.platform.sms.SmsRetrieverManager;
import ru.mail.libverify.platform.sms.SmsRetrieverPlatformManager;
import ru.mail.verify.core.utils.FileLog;
import ru.mail.verify.core.utils.Utils;
import ru.mail.verify.core.utils.components.BusMessageType;
import ru.mail.verify.core.utils.components.MessageBusUtils;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/t/a.class */
public final class a implements SmsRetrieverManager {
    private final CommonContext b;
    private final HashMap a = new HashMap();
    private Boolean c = null;
    private boolean d = false;

    /* renamed from: ru.mail.libverify.t.a$a  reason: collision with other inner class name */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/t/a$a.class */
    class RunnableC0021a implements Runnable {
        RunnableC0021a() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            FileLog.d("SmsRetrieverManager", "SmsRetrieverClient successfully subscribed");
            a aVar = a.this;
            aVar.d = false;
            aVar.b.getBus().post(MessageBusUtils.createOneArg(BusMessageType.SMS_RETRIEVER_MANAGER_SUBSCRIBE_SUCCEEDED, (Object) null));
        }
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/t/a$b.class */
    class b implements Runnable {
        final /* synthetic */ Exception a;

        b(Exception exc) {
            this.a = exc;
        }

        @Override // java.lang.Runnable
        public final void run() {
            FileLog.e("SmsRetrieverManager", "SmsRetrieverClient failed to subscribe", this.a);
            a aVar = a.this;
            aVar.d = true;
            aVar.b.getBus().post(MessageBusUtils.createOneArg(BusMessageType.SMS_RETRIEVER_MANAGER_SUBSCRIBE_FAILED, (Object) null));
        }
    }

    public a(@NonNull CommonContext commonContext) {
        this.b = commonContext;
    }

    private void a() {
        PlatformCoreService platformService = VerificationFactory.getPlatformService(this.b.getConfig().getContext());
        if (platformService == null) {
            return;
        }
        platformService.getSmsRetrieverPlatformManager().checkSmsRetrieverTask(this.b.getConfig().getContext(), () -> {
            this.b.getDispatcher().post(new RunnableC0021a());
        }, exc -> {
            this.b.getDispatcher().post(new b(exc));
            return Unit.INSTANCE;
        });
    }

    public final void register(@NonNull SmsRetrieverManager.SmsRetrieverSmsCallback smsRetrieverSmsCallback) {
        if (canUseSmsRetriever()) {
            if (this.a.containsKey(smsRetrieverSmsCallback)) {
                FileLog.e("SmsRetrieverManager", "callback has been already registered");
                return;
            }
            this.a.put(smsRetrieverSmsCallback, smsRetrieverSmsCallback);
            a();
        }
    }

    public final void unregister(@NonNull SmsRetrieverManager.SmsRetrieverSmsCallback smsRetrieverSmsCallback) {
        this.a.remove(smsRetrieverSmsCallback);
    }

    public final void onSmsRetrieverSmsReceived(int i, @NonNull String str) {
        PlatformCoreService platformService = VerificationFactory.getPlatformService(this.b.getConfig().getContext());
        if (platformService == null) {
            return;
        }
        SmsRetrieverPlatformManager smsRetrieverPlatformManager = platformService.getSmsRetrieverPlatformManager();
        if (!this.a.isEmpty()) {
            a();
        }
        smsRetrieverPlatformManager.onSmsRetrieverSmsReceived(i, str, () -> {
            if (TextUtils.isEmpty(str)) {
                return;
            }
            for (SmsRetrieverManager.SmsRetrieverSmsCallback smsRetrieverSmsCallback : this.a.values()) {
                smsRetrieverSmsCallback.onIncomingSms(str);
            }
        }, () -> {
            this.b.getBus().post(MessageBusUtils.createOneArg(BusMessageType.SMS_RETRIEVER_MANAGER_WAIT_TIMEOUT, (Object) null));
        });
    }

    public final boolean canUseSmsRetriever() {
        PlatformCoreService platformService = VerificationFactory.getPlatformService(this.b.getConfig().getContext());
        if (platformService == null) {
            return false;
        }
        IPlatformUtils utils = platformService.getUtils();
        if (Utils.hasSelfPermission(this.b.getConfig().getContext(), "android.permission.RECEIVE_SMS") || Utils.hasSelfPermission(this.b.getConfig().getContext(), "android.permission.READ_SMS")) {
            FileLog.d("SmsRetrieverManager", "no reason to use sms retriever (has permissions)");
            return false;
        } else if (this.d) {
            FileLog.d("SmsRetrieverManager", "there were an error in sms retriever api");
            return false;
        } else {
            if (this.c == null) {
                this.c = Boolean.valueOf(utils.checkGooglePlayServicesNewer(this.b.getConfig().getContext()));
            }
            return this.c.booleanValue();
        }
    }
}
