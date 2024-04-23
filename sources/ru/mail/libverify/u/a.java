package ru.mail.libverify.u;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import javax.inject.Inject;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.mail.libverify.l.d;
import ru.mail.verify.core.utils.DebugUtils;
import ru.mail.verify.core.utils.FileLog;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/u/a.class */
public final class a implements d {
    @Inject
    public a(@NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
    }

    @Override // ru.mail.libverify.l.d
    public final void a(@Nullable Thread thread, @NotNull Throwable th) {
        Intrinsics.checkNotNullParameter(th, "error");
        try {
            Bundle bundle = new Bundle();
            bundle.putInt("libverify_version", 2011000);
            if (thread != null) {
                bundle.putString("libverify_thread", thread.getName());
            }
            bundle.putString("libverify_exception", th.toString());
            String exceptionStacktraceToString = DebugUtils.exceptionStacktraceToString(th, thread, 100);
            if (TextUtils.isEmpty(exceptionStacktraceToString)) {
                return;
            }
            bundle.putString("libverify_trace", exceptionStacktraceToString);
        } catch (Throwable th2) {
            FileLog.e("FirebaseEventSender", "sendError", th2);
        }
    }

    @Override // ru.mail.libverify.l.d
    public final void a(@NotNull ru.mail.libverify.l.a aVar, @NotNull Bundle bundle) {
        Intrinsics.checkNotNullParameter(aVar, "id");
        Intrinsics.checkNotNullParameter(bundle, "parameters");
        try {
            bundle.putInt("libverify_version", 2011000);
        } catch (Throwable th) {
            FileLog.e("FirebaseEventSender", "sendLog", th);
        }
    }
}
