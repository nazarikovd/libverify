package ru.mail.libverify.notifications;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import ru.mail.libverify.R;
import ru.mail.libverify.api.VerificationApi;
import ru.mail.libverify.api.VerificationFactory;
import ru.mail.verify.core.utils.components.BusMessageType;
import ru.mail.verify.core.utils.components.MessageBusUtils;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/notifications/SmsMessagesActivity.class */
public class SmsMessagesActivity extends j {
    private String e;

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/notifications/SmsMessagesActivity$a.class */
    class a implements AdapterView.OnItemLongClickListener {
        a() {
        }

        @Override // android.widget.AdapterView.OnItemLongClickListener
        public final boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long j) {
            SmsMessagesActivity smsMessagesActivity = SmsMessagesActivity.this;
            l.a(smsMessagesActivity, smsMessagesActivity.e, ((VerificationApi.SmsItem) adapterView.getItemAtPosition(i)).getId()).show();
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // ru.mail.libverify.notifications.j
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = getIntent();
        long longExtra = intent.getLongExtra("dialog_id", -1L);
        if (longExtra < 0) {
            finish();
            return;
        }
        String stringExtra = intent.getStringExtra("dialog_name");
        this.e = stringExtra;
        if (TextUtils.isEmpty(stringExtra)) {
            finish();
            return;
        }
        setTheme(R.style.libverify_custom_color_theme);
        l.a(this, R.drawable.libverify_ic_sms_white, this.e, true, true);
        setContentView(R.layout.activity_sms_messages);
        ListView listView = (ListView) findViewById(R.id.messages);
        if (listView == null) {
            finish();
            return;
        }
        ru.mail.libverify.h.c cVar = new ru.mail.libverify.h.c(this, VerificationFactory.getInstance(this), longExtra, R.layout.sms_message_item);
        listView.setOnItemLongClickListener(new a());
        a(listView, cVar);
        ru.mail.libverify.v.a.a(this, MessageBusUtils.createOneArg(BusMessageType.UI_NOTIFICATION_HISTORY_OPENED, this.e));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.history_clear) {
            l.a((Context) this, this.e).show();
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
