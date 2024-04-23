package ru.mail.libverify.notifications;

import android.app.Activity;
import android.preference.Preference;
import android.widget.Toast;
import ru.mail.libverify.R;
import ru.mail.libverify.notifications.SettingsActivity;
import ru.mail.verify.core.utils.components.BusMessageType;
import ru.mail.verify.core.utils.components.MessageBusUtils;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/notifications/h.class */
final class h implements Preference.OnPreferenceClickListener {
    final /* synthetic */ SettingsActivity.a a;

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/notifications/h$a.class */
    class a implements Preference.OnPreferenceChangeListener {
        a() {
        }

        @Override // android.preference.Preference.OnPreferenceChangeListener
        public final boolean onPreferenceChange(Preference preference, Object obj) {
            boolean z;
            SettingsActivity.a aVar = h.this.a;
            Activity activity = aVar.getActivity();
            if (activity == null) {
                z = false;
            } else {
                activity.finish();
                Toast.makeText(activity, aVar.getResources().getString(R.string.setting_saved_toast_text), 1).show();
                z = true;
            }
            if (z) {
                ru.mail.libverify.v.a.a(h.this.a.getActivity(), MessageBusUtils.createMultipleArgs(BusMessageType.UI_NOTIFICATION_SETTINGS_BLOCK, h.this.a.c, Integer.valueOf(Integer.parseInt(obj.toString()))));
                return true;
            }
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public h(SettingsActivity.a aVar) {
        this.a = aVar;
    }

    @Override // android.preference.Preference.OnPreferenceClickListener
    public final boolean onPreferenceClick(Preference preference) {
        preference.setOnPreferenceChangeListener(new a());
        return true;
    }
}
