package ru.mail.libverify.i;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.Locale;
import ru.mail.libverify.api.VerificationApi;
import ru.mail.verify.core.requests.RequestPersistentId;
import ru.mail.verify.core.utils.Gsonable;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/i/b.class */
final class b implements Gsonable, RequestPersistentId {
    final String verificationUrl;
    final String code;
    final String applicationId;
    @Nullable
    final VerificationApi.VerificationSource codeSource;

    private b() {
        this.verificationUrl = null;
        this.code = null;
        this.applicationId = null;
        this.codeSource = null;
    }

    @Override // ru.mail.verify.core.requests.RequestPersistentId
    public final String getId() {
        return String.format(Locale.US, "attempt_%s_%s_%s_%s", this.verificationUrl, this.code, this.codeSource, this.applicationId);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public b(@NonNull String str, @NonNull String str2, @Nullable VerificationApi.VerificationSource verificationSource, String str3) {
        this.verificationUrl = str;
        this.code = str2;
        this.applicationId = str3;
        this.codeSource = verificationSource;
    }
}
