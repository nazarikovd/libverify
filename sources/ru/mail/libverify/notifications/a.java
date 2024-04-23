package ru.mail.libverify.notifications;

import android.graphics.drawable.ColorDrawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/notifications/a.class */
final class a {
    private final ActionBar a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(@NonNull ActionBar actionBar) {
        this.a = actionBar;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void a(@Nullable ColorDrawable colorDrawable) {
        this.a.setBackgroundDrawable(colorDrawable);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void a() {
        this.a.setDisplayHomeAsUpEnabled(true);
    }
}
