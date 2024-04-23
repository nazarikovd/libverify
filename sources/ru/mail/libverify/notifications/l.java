package ru.mail.libverify.notifications;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.Window;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import ru.mail.libverify.R;
import ru.mail.libverify.api.VerificationFactory;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/notifications/l.class */
public final class l {
    private static final int[] a = {Color.parseColor("#F44336"), Color.parseColor("#E91E63"), Color.parseColor("#9C27B0"), Color.parseColor("#673AB7"), Color.parseColor("#3F51B5"), Color.parseColor("#2196F3"), Color.parseColor("#03A9F4"), Color.parseColor("#03A9F4"), Color.parseColor("#00BCD4"), Color.parseColor("#009688"), Color.parseColor("#4CAF50"), Color.parseColor("#8BC34A"), Color.parseColor("#CDDC39"), Color.parseColor("#43A047"), Color.parseColor("#5C6BC0"), Color.parseColor("#00695C"), Color.parseColor("#C2185B"), Color.parseColor("#795548"), Color.parseColor("#9E9E9E"), Color.parseColor("#607D8B")};
    public static final /* synthetic */ int b = 0;

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/notifications/l$a.class */
    class a implements DialogInterface.OnClickListener {
        final /* synthetic */ Context a;
        final /* synthetic */ String b;

        a(Context context, String str) {
            this.a = context;
            this.b = str;
        }

        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            VerificationFactory.getInstance(this.a).removeSmsDialog(this.b, null);
        }
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/notifications/l$b.class */
    class b implements DialogInterface.OnClickListener {
        final /* synthetic */ Context a;

        b(Context context) {
            this.a = context;
        }

        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            VerificationFactory.getInstance(this.a).clearSmsDialogs();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static AlertDialog a(@NonNull Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(R.string.notification_history_delete_all_confirm));
        builder.setPositiveButton(context.getString(R.string.notification_history_delete), new b(context));
        builder.setNegativeButton(context.getString(R.string.notification_event_cancel), (DialogInterface.OnClickListener) null);
        AlertDialog create = builder.create();
        create.setOnShowListener(new n(create, context));
        return create;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static AlertDialog a(@NonNull Context context, @NonNull String str) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(String.format(context.getResources().getString(R.string.notification_history_delete_confirm), str));
        builder.setPositiveButton(context.getString(R.string.notification_history_delete), new a(context, str));
        builder.setNegativeButton(context.getString(R.string.notification_event_cancel), (DialogInterface.OnClickListener) null);
        AlertDialog create = builder.create();
        create.setOnShowListener(new n(create, context));
        return create;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Multi-variable type inference failed */
    public static AlertDialog a(@NonNull SmsMessagesActivity smsMessagesActivity, @NonNull String str, long j) {
        AlertDialog.Builder builder = new AlertDialog.Builder(smsMessagesActivity);
        builder.setTitle(String.format(smsMessagesActivity.getResources().getString(R.string.notification_history_delete_sms_confirm), str));
        builder.setPositiveButton(smsMessagesActivity.getString(R.string.notification_history_delete), new m(smsMessagesActivity, str, j));
        builder.setNegativeButton(smsMessagesActivity.getString(R.string.notification_event_cancel), (DialogInterface.OnClickListener) null);
        AlertDialog create = builder.create();
        create.setOnShowListener(new n(create, smsMessagesActivity));
        return create;
    }

    public static int a(@NonNull String str) {
        char charAt = str.charAt(0);
        int[] iArr = a;
        return iArr[charAt % iArr.length];
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Multi-variable type inference failed */
    @TargetApi(21)
    public static void a(@NonNull ru.mail.libverify.g.a aVar, int i, String str, boolean z, boolean z2) {
        int a2;
        aVar.setTitle(str);
        if (z) {
            char charAt = str.charAt(0);
            int[] iArr = a;
            a2 = iArr[charAt % iArr.length];
        } else {
            a2 = ru.mail.libverify.notifications.b.a(aVar);
        }
        ActionBar supportActionBar = aVar.getSupportActionBar();
        ru.mail.libverify.notifications.a aVar2 = supportActionBar == null ? null : new ru.mail.libverify.notifications.a(supportActionBar);
        if (aVar2 != null) {
            aVar2.a(new ColorDrawable(a2));
            if (z2) {
                aVar2.a();
            }
        }
        Window window = aVar.getWindow();
        window.addFlags(Integer.MIN_VALUE);
        window.setStatusBarColor(a2);
        Bitmap decodeResource = BitmapFactory.decodeResource(aVar.getResources(), i);
        aVar.setTaskDescription(new ActivityManager.TaskDescription(str, decodeResource, a2));
        decodeResource.recycle();
    }

    public static void a(RelativeLayout relativeLayout, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= 16) {
            relativeLayout.setBackground(drawable);
        } else {
            relativeLayout.setBackgroundDrawable(drawable);
        }
    }
}
