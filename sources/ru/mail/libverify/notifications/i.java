package ru.mail.libverify.notifications;

import android.content.Intent;
import android.preference.Preference;
import ru.mail.libverify.notifications.SettingsActivity;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/notifications/i.class */
final class i implements Preference.OnPreferenceClickListener {
    final /* synthetic */ SettingsActivity.a a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public i(SettingsActivity.a aVar) {
        this.a = aVar;
    }

    @Override // android.preference.Preference.OnPreferenceClickListener
    public final boolean onPreferenceClick(Preference preference) {
        if (this.a.getActivity() == null) {
            return false;
        }
        this.a.getActivity().startActivity(new Intent(this.a.getActivity(), SmsDialogsActivity.class));
        return true;
    }
}
