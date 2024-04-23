package ru.mail.libverify.m;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.Nullable;
import ru.mail.verify.core.accounts.SimCardData;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/m/j.class */
final class j extends Lambda implements Function0<Object> {
    final /* synthetic */ SimCardData a;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public j(SimCardData simCardData) {
        super(0);
        this.a = simCardData;
    }

    @Nullable
    public final Object invoke() {
        return this.a.getSlotCount();
    }
}
