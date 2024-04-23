package ru.mail.libverify.m;

import android.content.Context;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.Nullable;
import ru.mail.verify.core.utils.Utils;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/m/d.class */
final class d extends Lambda implements Function0<Object> {
    final /* synthetic */ Context a;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public d(Context context) {
        super(0);
        this.a = context;
    }

    @Nullable
    public final Object invoke() {
        return Utils.isVoiceCapable(this.a);
    }
}
