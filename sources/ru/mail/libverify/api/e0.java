package ru.mail.libverify.api;

import android.content.Context;
import androidx.annotation.NonNull;
import javax.inject.Inject;
import ru.mail.libverify.R;
import ru.mail.verify.core.api.ResourceParamsBase;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/e0.class */
final class e0 extends ResourceParamsBase {
    /* JADX INFO: Access modifiers changed from: package-private */
    @Inject
    public e0(@NonNull Context context) {
        super(context);
    }

    @Override // ru.mail.verify.core.api.ResourceParamsBase
    protected final void loadFromResources() {
        this.name = getFromResources(R.string.libverify_application_name, "libverify", VerificationFactory.LIBVERIFY_RESOURCE_APPLICATION_NAME_KEY, VerificationFactory.LIBVERIFY_MANIFEST_APPLICATION_NAME_KEY, true);
        this.key = getFromResources(R.string.libverify_application_key, "libverify", VerificationFactory.LIBVERIFY_RESOURCE_APPLICATION_KEY_KEY, VerificationFactory.LIBVERIFY_MANIFEST_APPLICATION_KEY_KEY, true);
        this.serverId = ru.mail.libverify.v.a.d(this.context);
    }
}
