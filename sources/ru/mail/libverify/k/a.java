package ru.mail.libverify.k;

import android.net.Uri;
import android.provider.CallLog;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/k/a.class */
public final class a {
    @JvmField
    @NotNull
    public static final Uri a;
    @JvmField
    @NotNull
    public static final Uri b;
    @JvmField
    @NotNull
    public static final Uri c;

    static {
        Uri parse = Uri.parse("content://sms");
        Intrinsics.checkNotNullExpressionValue(parse, "parse(\"content://sms\")");
        a = parse;
        Intrinsics.checkNotNullExpressionValue(Uri.parse("content://sms/sent"), "parse(\"content://sms/sent\")");
        Uri parse2 = Uri.parse("content://sms/inbox");
        Intrinsics.checkNotNullExpressionValue(parse2, "parse(\"content://sms/inbox\")");
        b = parse2;
        Uri uri = CallLog.Calls.CONTENT_URI;
        Intrinsics.checkNotNullExpressionValue(uri, "CONTENT_URI");
        c = uri;
    }
}
