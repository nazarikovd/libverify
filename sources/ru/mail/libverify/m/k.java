package ru.mail.libverify.m;

import android.content.Context;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.Nullable;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/m/k.class */
final class k extends Lambda implements Function0<Object> {
    final /* synthetic */ Context a;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public k(Context context) {
        super(0);
        this.a = context;
    }

    @Nullable
    public final Object invoke() {
        Boolean a = c.a(this.a);
        Boolean bool = a;
        if (a == null) {
            bool = "no_data";
        }
        return bool;
    }
}
