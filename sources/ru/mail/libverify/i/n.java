package ru.mail.libverify.i;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.net.MalformedURLException;
import javax.inject.Inject;
import ru.mail.verify.core.requests.ActionDescriptor;
import ru.mail.verify.core.requests.ActionFactory;
import ru.mail.verify.core.requests.RequestBase;
import ru.mail.verify.core.utils.FileLog;
import ru.mail.verify.core.utils.json.JsonParseException;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/i/n.class */
public final class n implements ActionFactory {
    private final ru.mail.libverify.m.l a;

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/i/n$a.class */
    static /* synthetic */ class a {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[ActionDescriptor.Type.values().length];
            a = iArr;
            try {
                iArr[ActionDescriptor.Type.UPDATE_SETTINGS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[ActionDescriptor.Type.PUSH_STATUS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[ActionDescriptor.Type.ATTEMPT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Inject
    public n(@NonNull ru.mail.libverify.m.l lVar) {
        this.a = lVar;
    }

    @Override // ru.mail.verify.core.requests.ActionFactory
    @Nullable
    public final RequestBase createRequest(@NonNull ActionDescriptor actionDescriptor) throws MalformedURLException, JsonParseException {
        ActionDescriptor.Type type = actionDescriptor.type;
        if (type != null) {
            int i = a.a[type.ordinal()];
            if (i != 1) {
                if (i != 2) {
                    if (i == 3) {
                        return new ru.mail.libverify.i.a(this.a, actionDescriptor.getData());
                    }
                    throw new IllegalArgumentException(actionDescriptor.type + " type is not supported");
                }
                return new j(this.a, actionDescriptor.getData());
            }
            return new l(this.a, actionDescriptor.getData());
        }
        throw new IllegalArgumentException("Null type is not supported");
    }

    @Override // ru.mail.verify.core.requests.ActionFactory
    @Nullable
    public final ActionDescriptor createDescriptor(@NonNull RequestBase requestBase) throws JsonParseException {
        if (requestBase instanceof l) {
            return new ActionDescriptor(ActionDescriptor.Type.UPDATE_SETTINGS, requestBase.getSerializedData(), this.a.getTimeProvider().getLocalTime());
        }
        if (requestBase instanceof j) {
            return new ActionDescriptor(ActionDescriptor.Type.PUSH_STATUS, requestBase.getSerializedData(), this.a.getTimeProvider().getLocalTime());
        }
        if (requestBase instanceof ru.mail.libverify.i.a) {
            return new ActionDescriptor(ActionDescriptor.Type.ATTEMPT, requestBase.getSerializedData(), this.a.getTimeProvider().getLocalTime());
        }
        if (requestBase instanceof d) {
            return new ActionDescriptor(ActionDescriptor.Type.CONTENT, requestBase.getSerializedData(), this.a.getTimeProvider().getLocalTime());
        }
        FileLog.e("VerifyActionFactoryImpl", requestBase.getClass().getName().concat(" type is not supported"));
        throw new IllegalArgumentException("Request type is not supported");
    }
}
