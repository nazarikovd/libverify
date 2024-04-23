package ru.mail.libverify.m;

import android.content.Context;
import androidx.annotation.NonNull;
import javax.inject.Inject;
import ru.mail.verify.core.api.ApiManager;
import ru.mail.verify.core.storage.AsyncSettings;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/m/s.class */
public final class s extends AsyncSettings {
    /* JADX INFO: Access modifiers changed from: package-private */
    @Inject
    public s(@NonNull Context context, @NonNull ApiManager apiManager) {
        super(apiManager, context, "VERIFY_SETTINGS", 500);
    }
}
