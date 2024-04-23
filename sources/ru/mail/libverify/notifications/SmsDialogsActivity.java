package ru.mail.libverify.notifications;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import ru.mail.libverify.R;
import ru.mail.libverify.api.VerificationApi;
import ru.mail.libverify.api.VerificationFactory;
import ru.mail.verify.core.utils.components.BusMessageType;
import ru.mail.verify.core.utils.components.MessageBusUtils;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/notifications/SmsDialogsActivity.class */
public class SmsDialogsActivity extends j {
    public static final /* synthetic */ int e = 0;

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/notifications/SmsDialogsActivity$a.class */
    class a implements AdapterView.OnItemClickListener {
        a() {
        }

        @Override // android.widget.AdapterView.OnItemClickListener
        public final void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
            VerificationApi.SmsDialogItem smsDialogItem = (VerificationApi.SmsDialogItem) adapterView.getItemAtPosition(i);
            Intent intent = new Intent((Context) SmsDialogsActivity.this, (Class<?>) SmsMessagesActivity.class);
            int i2 = SmsDialogsActivity.e;
            intent.putExtra("dialog_id", smsDialogItem.getId());
            intent.putExtra("dialog_name", smsDialogItem.getFrom());
            SmsDialogsActivity.this.startActivity(intent);
        }
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/notifications/SmsDialogsActivity$b.class */
    class b implements AdapterView.OnItemLongClickListener {
        b() {
        }

        @Override // android.widget.AdapterView.OnItemLongClickListener
        public final boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long j) {
            l.a((Context) SmsDialogsActivity.this, ((VerificationApi.SmsDialogItem) adapterView.getItemAtPosition(i)).getFrom()).show();
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // ru.mail.libverify.notifications.j
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_sms_dialogs);
        ListView listView = (ListView) findViewById(R.id.dialogs);
        if (listView == null) {
            finish();
            return;
        }
        listView.setOnItemClickListener(new a());
        listView.setOnItemLongClickListener(new b());
        a(listView, new ru.mail.libverify.h.b(getBaseContext(), VerificationFactory.getInstance(this), R.layout.sms_dialog_item));
        l.a(this, R.drawable.libverify_ic_sms_white, getResources().getString(R.string.notification_history_text), false, false);
        ru.mail.libverify.v.a.a(this, MessageBusUtils.createOneArg(BusMessageType.UI_NOTIFICATION_HISTORY_OPENED, (Object) null));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.history_clear) {
            l.a((Context) this).show();
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
