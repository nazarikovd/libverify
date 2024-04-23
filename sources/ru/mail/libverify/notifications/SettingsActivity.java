package ru.mail.libverify.notifications;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.core.graphics.drawable.DrawableCompat;
import ru.mail.libverify.R;
import ru.mail.libverify.api.i;
import ru.mail.verify.core.ui.notifications.NotificationBase;
import ru.mail.verify.core.utils.FileLog;
import ru.mail.verify.core.utils.Utils;
import ru.mail.verify.core.utils.components.BusMessageType;
import ru.mail.verify.core.utils.components.MessageBusUtils;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/notifications/SettingsActivity.class */
public class SettingsActivity extends ru.mail.libverify.g.a {

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/notifications/SettingsActivity$a.class */
    public static class a extends PreferenceFragment implements d {
        private String a;
        private String b;
        private String c;

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: ru.mail.libverify.notifications.SettingsActivity$a$a  reason: collision with other inner class name */
        /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/notifications/SettingsActivity$a$a.class */
        public class DialogInterface$OnClickListenerC0018a implements DialogInterface.OnClickListener {
            DialogInterface$OnClickListenerC0018a() {
            }

            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                boolean z;
                a aVar = a.this;
                Activity activity = aVar.getActivity();
                if (activity == null) {
                    z = false;
                } else {
                    activity.finish();
                    Toast.makeText(activity, aVar.getResources().getString(R.string.setting_saved_toast_text), 1).show();
                    z = true;
                }
                if (z) {
                    ru.mail.libverify.v.a.a(a.this.getActivity(), MessageBusUtils.createOneArg(BusMessageType.UI_NOTIFICATION_SETTINGS_REPORT_REUSE, a.this.c));
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public AlertDialog a() {
            Activity activity = getActivity();
            if (activity == null) {
                return null;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle(this.b);
            Drawable wrap = DrawableCompat.wrap(getResources().getDrawable(R.drawable.libverify_ic_sms_white));
            DrawableCompat.setTint(wrap, getResources().getColor(R.color.libverify_secondary_icon_color));
            builder.setIcon(wrap);
            builder.setMessage(String.format(getResources().getString(R.string.report_reuse_text_confirmation), this.a));
            builder.setPositiveButton(getString(R.string.notification_event_confirm), new DialogInterface$OnClickListenerC0018a());
            builder.setNegativeButton(getString(R.string.notification_event_close), (DialogInterface.OnClickListener) null);
            return builder.create();
        }

        @Override // android.app.Fragment
        public final void setArguments(Bundle bundle) {
            super.setArguments(bundle);
            if (this.c == null) {
                String string = getArguments().getString(NotificationBase.NOTIFICATION_ID_EXTRA);
                this.c = string;
                if (TextUtils.isEmpty(string)) {
                    getActivity().finish();
                } else {
                    ru.mail.libverify.v.a.a(getActivity(), MessageBusUtils.createMultipleArgs(BusMessageType.UI_NOTIFICATION_GET_INFO, this.c, new c(this)));
                }
            }
        }

        @Override // android.preference.PreferenceFragment, android.app.Fragment
        public final void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            if (this.c == null) {
                String string = getArguments().getString(NotificationBase.NOTIFICATION_ID_EXTRA);
                this.c = string;
                if (TextUtils.isEmpty(string)) {
                    getActivity().finish();
                } else {
                    ru.mail.libverify.v.a.a(getActivity(), MessageBusUtils.createMultipleArgs(BusMessageType.UI_NOTIFICATION_GET_INFO, this.c, new c(this)));
                }
            }
        }

        @Override // ru.mail.libverify.notifications.d
        public final void a(i.b bVar) {
            if (bVar == null || getActivity() == null || !TextUtils.equals(bVar.f, this.c)) {
                FileLog.e("SettingsActivity", "no such notification with id %s or activity has been finished", this.c);
                if (getActivity() != null) {
                    getActivity().finish();
                    return;
                }
                return;
            }
            ru.mail.libverify.v.a.a(getActivity(), MessageBusUtils.createOneArg(BusMessageType.UI_NOTIFICATION_SETTINGS_SHOWN, this.c));
            this.a = Utils.getHiddenPhoneNumber(bVar.e);
            this.b = bVar.b;
            boolean z = bVar.j;
            addPreferencesFromResource(R.xml.notification_settings);
            Preference findPreference = findPreference("preference_report_reuse");
            findPreference.setTitle(String.format(getResources().getString(R.string.report_reuse_text), this.a));
            findPreference.setOnPreferenceClickListener(new g(this));
            findPreference("preference_block_notifications").setOnPreferenceClickListener(new h(this));
            Preference findPreference2 = findPreference("preference_show_history");
            if (z) {
                findPreference2.setOnPreferenceClickListener(new i(this));
            } else {
                getPreferenceScreen().removeAll();
                getPreferenceScreen().addPreference(findPreference);
                getPreferenceScreen().addPreference(findPreference2);
            }
            getActivity().setTitle(String.format("%s (%s)", getResources().getString(R.string.title_activity_settings), this.b));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected final void onCreate(Bundle bundle) {
        super/*androidx.fragment.app.FragmentActivity*/.onCreate(bundle);
        Intent intent = getIntent();
        if (intent == null) {
            finish();
            return;
        }
        FileLog.v("SettingsActivity", "create with %s", Utils.bundleToString(intent.getExtras()));
        a aVar = new a();
        aVar.setArguments(getIntent().getExtras());
        getFragmentManager().beginTransaction().replace(16908290, aVar).commit();
        l.a(this, R.drawable.libverify_ic_sms_white, getResources().getString(R.string.title_activity_settings), false, false);
    }

    public final boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        return super.onOptionsItemSelected(menuItem);
    }
}
