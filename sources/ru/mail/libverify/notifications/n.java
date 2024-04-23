package ru.mail.libverify.notifications;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import ru.mail.libverify.R;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/notifications/n.class */
final class n implements DialogInterface.OnShowListener {
    final /* synthetic */ AlertDialog a;
    final /* synthetic */ Context b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public n(AlertDialog alertDialog, Context context) {
        this.a = alertDialog;
        this.b = context;
    }

    @Override // android.content.DialogInterface.OnShowListener
    public final void onShow(DialogInterface dialogInterface) {
        this.a.getButton(-1).setTextColor(this.b.getResources().getColor(R.color.libverify_settings_color));
        this.a.getButton(-2).setTextColor(this.b.getResources().getColor(R.color.libverify_settings_color));
    }
}
