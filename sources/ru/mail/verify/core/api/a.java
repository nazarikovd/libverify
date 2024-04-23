package ru.mail.verify.core.api;

import android.content.Context;
import androidx.annotation.NonNull;
import javax.inject.Inject;
import ru.mail.verify.core.api.ApplicationModule;
import ru.mail.verify.core.utils.AlarmReceiver;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/api/a.class */
final class a implements AlarmManager {
    private final Context a;
    private final ApplicationModule.NetworkPolicyConfig b;

    /* JADX INFO: Access modifiers changed from: package-private */
    @Inject
    public a(@NonNull Context context, @NonNull ApplicationModule.NetworkPolicyConfig networkPolicyConfig) {
        this.a = context;
        this.b = networkPolicyConfig;
    }

    @Override // ru.mail.verify.core.api.AlarmManager
    public final AlarmReceiver.AlarmBuilder createBuilder() {
        return AlarmReceiver.create(this.a, this.b.getBackgroundAwakeMode() == BackgroundAwakeMode.DISABLED);
    }

    @Override // ru.mail.verify.core.api.AlarmManager
    public final void cancelAll() {
        AlarmReceiver.create(this.a, false).cancel();
    }
}
