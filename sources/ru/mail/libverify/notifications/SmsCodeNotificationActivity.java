package ru.mail.libverify.notifications;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import java.util.Arrays;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.StringCompanionObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.mail.libverify.R;
import ru.mail.libverify.api.i;
import ru.mail.libverify.notifications.e;
import ru.mail.verify.core.storage.BroadcastManager;
import ru.mail.verify.core.ui.notifications.NotificationBarManagerImpl;
import ru.mail.verify.core.ui.notifications.NotificationBase;
import ru.mail.verify.core.utils.FileLog;
import ru.mail.verify.core.utils.Utils;
import ru.mail.verify.core.utils.components.BusMessageType;
import ru.mail.verify.core.utils.components.MessageBusUtils;

@Metadata(mv = {BroadcastManager.FLAG_NETWORK_RECEIVER, 7, BroadcastManager.FLAG_NETWORK_RECEIVER}, k = BroadcastManager.FLAG_NETWORK_RECEIVER, d1 = {"��\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018��2\u00020\u00012\u00020\u0002B\u0007¢\u0006\u0004\b\u0003\u0010\u0004¨\u0006\u0005"}, d2 = {"Lru/mail/libverify/notifications/SmsCodeNotificationActivity;", "Lru/mail/libverify/g/a;", "Lru/mail/libverify/notifications/d;", "<init>", "()V", "libverify_release"})
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/notifications/SmsCodeNotificationActivity.class */
public final class SmsCodeNotificationActivity extends ru.mail.libverify.g.a implements d {
    private String a;
    @Nullable
    private String b;
    @Nullable
    private AlertDialog c;
    private boolean d;
    @NotNull
    private final Lazy e = LazyKt.lazy(new a());

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/notifications/SmsCodeNotificationActivity$a.class */
    static final class a extends Lambda implements Function0<Drawable> {
        a() {
            super(0);
        }

        public final Object invoke() {
            Drawable drawable = ResourcesCompat.getDrawable(SmsCodeNotificationActivity.this.getResources(), R.drawable.libverify_ic_sms_white, SmsCodeNotificationActivity.this.getTheme());
            Intrinsics.checkNotNull(drawable);
            Drawable wrap = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(wrap, ResourcesCompat.getColor(SmsCodeNotificationActivity.this.getResources(), R.color.libverify_secondary_icon_color, SmsCodeNotificationActivity.this.getTheme()));
            return wrap;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final AlertDialog a(String str, String str2, String str3, String str4, boolean z) {
        FileLog.v("SmsCodeActivity", "build dialog for notification %s", str);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(str);
        Object value = this.e.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "<get-dialogDrawable>(...)");
        builder.setIcon((Drawable) value);
        if (!TextUtils.isEmpty(str4)) {
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            String format = String.format("%s\n%s", Arrays.copyOf(new Object[]{str2, str4}, 2));
            str2 = format;
            Intrinsics.checkNotNullExpressionValue(format, "format(format, *args)");
        }
        if (TextUtils.isEmpty(str3)) {
            str3 = getString(R.string.notification_event_confirm);
        }
        builder.setMessage(str2);
        if (z) {
            builder.setPositiveButton(str3, (v1, v2) -> {
                a(r1, v1, v2);
            });
        }
        builder.setNegativeButton(getString(R.string.notification_event_close), (v1, v2) -> {
            b(r5, v1, v2);
        });
        builder.setNeutralButton(getString(R.string.notification_settings), (v1, v2) -> {
            c(r2, v1, v2);
        });
        AlertDialog create = builder.create();
        create.setOnDismissListener((v1) -> {
            a(r3, v1);
        });
        Intrinsics.checkNotNullExpressionValue(create, "dialog");
        return create;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final void b(SmsCodeNotificationActivity smsCodeNotificationActivity, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(smsCodeNotificationActivity, "this$0");
        try {
            String str = smsCodeNotificationActivity.a;
            String str2 = str;
            if (str == null) {
                Intrinsics.throwUninitializedPropertyAccessException("notificationId");
                str2 = null;
            }
            new e.a(smsCodeNotificationActivity, "action_cancel").putExtra(NotificationBase.NOTIFICATION_ID_EXTRA, str2).build().send();
        } catch (PendingIntent.CanceledException e) {
            FileLog.e("SmsCodeActivity", "failed to confirm notification", e);
        }
        smsCodeNotificationActivity.finish();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final void c(SmsCodeNotificationActivity smsCodeNotificationActivity, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(smsCodeNotificationActivity, "this$0");
        try {
            String str = smsCodeNotificationActivity.a;
            String str2 = str;
            if (str == null) {
                Intrinsics.throwUninitializedPropertyAccessException("notificationId");
                str2 = null;
            }
            new e.b(smsCodeNotificationActivity).putExtra(NotificationBase.NOTIFICATION_ID_EXTRA, str2).build().send();
        } catch (PendingIntent.CanceledException e) {
            FileLog.e("SmsCodeActivity", "failed to open settings", e);
        }
        smsCodeNotificationActivity.finish();
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected final void onCreate(@Nullable Bundle bundle) {
        super/*androidx.fragment.app.FragmentActivity*/.onCreate(bundle);
        setContentView(R.layout.activity_sms_code_notification);
        if (getIntent() == null) {
            finish();
            return;
        }
        FileLog.v("SmsCodeActivity", "create with %s", Utils.bundleToString(getIntent().getExtras()));
        String stringExtra = getIntent().getStringExtra(NotificationBase.NOTIFICATION_ID_EXTRA);
        if (stringExtra == null) {
            finish();
            return;
        }
        this.a = stringExtra;
        ru.mail.libverify.v.a.a(this, MessageBusUtils.createOneArg(BusMessageType.UI_NOTIFICATION_OPENED, stringExtra));
        BusMessageType busMessageType = BusMessageType.UI_NOTIFICATION_GET_INFO;
        Object[] objArr = new Object[2];
        String str = this.a;
        String str2 = str;
        if (str == null) {
            Intrinsics.throwUninitializedPropertyAccessException("notificationId");
            str2 = null;
        }
        objArr[0] = str2;
        objArr[1] = new c(this);
        ru.mail.libverify.v.a.a(this, MessageBusUtils.createMultipleArgs(busMessageType, objArr));
    }

    protected final void onPause() {
        l.a(this, R.drawable.libverify_ic_sms_white, this.b, false, false);
        super/*androidx.fragment.app.FragmentActivity*/.onPause();
    }

    protected final void onStop() {
        super.onStop();
        this.d = true;
        AlertDialog alertDialog = this.c;
        if (alertDialog != null) {
            Intrinsics.checkNotNull(alertDialog);
            alertDialog.dismiss();
        }
    }

    public final boolean onCreateOptionsMenu(@NotNull Menu menu) {
        Intrinsics.checkNotNullParameter(menu, "menu");
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final void a(SmsCodeNotificationActivity smsCodeNotificationActivity, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(smsCodeNotificationActivity, "this$0");
        try {
            String str = smsCodeNotificationActivity.a;
            String str2 = str;
            if (str == null) {
                Intrinsics.throwUninitializedPropertyAccessException("notificationId");
                str2 = null;
            }
            new e.a(smsCodeNotificationActivity, "action_confirm").putExtra(NotificationBase.NOTIFICATION_ID_EXTRA, str2).build().send();
        } catch (PendingIntent.CanceledException e) {
            FileLog.e("SmsCodeActivity", "failed to confirm notification", e);
        }
        smsCodeNotificationActivity.finish();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final void a(SmsCodeNotificationActivity smsCodeNotificationActivity, DialogInterface dialogInterface) {
        Intrinsics.checkNotNullParameter(smsCodeNotificationActivity, "this$0");
        smsCodeNotificationActivity.finish();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v14, types: [ru.mail.libverify.notifications.SmsCodeNotificationActivity] */
    /* JADX WARN: Type inference failed for: r0v15, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v54, types: [android.app.Dialog] */
    /* JADX WARN: Type inference failed for: r9v0, types: [android.content.Context, ru.mail.libverify.notifications.SmsCodeNotificationActivity, android.app.Activity] */
    @Override // ru.mail.libverify.notifications.d
    public final void a(@Nullable i.b bVar) {
        boolean z;
        if (bVar == null) {
            if (Build.VERSION.SDK_INT >= 23) {
                NotificationBarManagerImpl.Companion companion = NotificationBarManagerImpl.Companion;
                String str = this.a;
                String str2 = str;
                if (str == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("notificationId");
                    str2 = null;
                }
                Notification activeNotification$libverify_release = companion.getActiveNotification$libverify_release(str2, this);
                if (activeNotification$libverify_release == null) {
                    finish();
                    return;
                }
                String string = activeNotification$libverify_release.extras.getString("android.title");
                Intrinsics.checkNotNull(string);
                AlertDialog a2 = a(string, activeNotification$libverify_release.tickerText.toString(), "", "", false);
                this.c = a2;
                Intrinsics.checkNotNull(a2);
                a2.show();
                AlertDialog alertDialog = this.c;
                Intrinsics.checkNotNull(alertDialog);
                Linkify.addLinks((TextView) alertDialog.findViewById(16908299), 3);
                return;
            }
            return;
        }
        String str3 = bVar.f;
        String str4 = this.a;
        String str5 = str4;
        if (str4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("notificationId");
            str5 = null;
        }
        if (!TextUtils.equals(str3, str5)) {
            Object[] objArr = new Object[1];
            String str6 = this.a;
            String str7 = str6;
            if (str6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("notificationId");
                str7 = null;
            }
            objArr[0] = str7;
            FileLog.e("SmsCodeActivity", "no such notification with id %s", objArr);
            finish();
        } else if (this.d) {
            Object[] objArr2 = new Object[1];
            String str8 = this.a;
            String str9 = str8;
            if (str8 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("notificationId");
                str9 = null;
            }
            objArr2[0] = str9;
            FileLog.d("SmsCodeActivity", "activity with id %s has been already deactivated", objArr2);
        } else {
            String str10 = bVar.b;
            this.b = str10;
            Intrinsics.checkNotNullExpressionValue(str10, "info.from");
            String str11 = bVar.a;
            Intrinsics.checkNotNullExpressionValue(str11, "info.message");
            String str12 = bVar.c;
            String str13 = bVar.g;
            Intrinsics.checkNotNullExpressionValue(bVar.h, "info.deliveryMethod");
            Boolean bool = bVar.d;
            Intrinsics.checkNotNullExpressionValue(bool, "info.confirmEnabled");
            AlertDialog a3 = a(str10, str11, str12, str13, bool.booleanValue());
            ?? r0 = this;
            r0.c = a3;
            try {
                if (!isFinishing()) {
                    AlertDialog alertDialog2 = this.c;
                    if (alertDialog2 != null) {
                        r0 = alertDialog2;
                        r0.show();
                    }
                }
            } catch (WindowManager.BadTokenException unused) {
                r0.printStackTrace();
            }
            if (bVar.k) {
                String string2 = TextUtils.isEmpty(bVar.i) ? getResources().getString(R.string.notification_history_shortcut_name) : bVar.i;
                int i = l.b;
                if (Utils.hasSelfPermission((Context) this, "com.android.launcher.permission.INSTALL_SHORTCUT") && Utils.hasSelfPermission((Context) this, "com.android.launcher.permission.UNINSTALL_SHORTCUT")) {
                    Intent intent = new Intent(getApplicationContext(), SmsDialogsActivity.class);
                    intent.addFlags(268435456);
                    intent.addFlags(67108864);
                    Intent intent2 = new Intent();
                    intent2.putExtra("android.intent.extra.shortcut.INTENT", intent);
                    intent2.putExtra("android.intent.extra.shortcut.NAME", string2);
                    intent2.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", Intent.ShortcutIconResource.fromContext(getApplicationContext(), R.drawable.libverify_ic_sms_white));
                    intent2.putExtra("duplicate", false);
                    intent2.setAction("com.android.launcher.action.UNINSTALL_SHORTCUT");
                    getApplicationContext().sendBroadcast(intent2);
                    intent2.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
                    getApplicationContext().sendBroadcast(intent2);
                    z = true;
                } else {
                    z = false;
                }
                ru.mail.libverify.v.a.a(this, MessageBusUtils.createOneArg(BusMessageType.UI_NOTIFICATION_HISTORY_SHORTCUT_CREATED, Boolean.valueOf(z)));
            }
            AlertDialog alertDialog3 = this.c;
            Intrinsics.checkNotNull(alertDialog3);
            Linkify.addLinks((TextView) alertDialog3.findViewById(16908299), 3);
        }
    }
}
