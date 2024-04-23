package ru.mail.libverify.notifications;

import android.app.AlertDialog;
import android.preference.Preference;
import ru.mail.libverify.notifications.SettingsActivity;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/notifications/g.class */
final class g implements Preference.OnPreferenceClickListener {
    final /* synthetic */ SettingsActivity.a a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public g(SettingsActivity.a aVar) {
        this.a = aVar;
    }

    @Override // android.preference.Preference.OnPreferenceClickListener
    public final boolean onPreferenceClick(Preference preference) {
        AlertDialog a = this.a.a();
        if (a == null) {
            return false;
        }
        a.show();
        return true;
    }
}
