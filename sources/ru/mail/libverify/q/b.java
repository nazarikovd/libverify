package ru.mail.libverify.q;

import android.os.Build;
import android.os.Process;
import android.os.SystemClock;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Locale;
import javax.inject.Inject;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import ru.mail.libverify.m.l;
import ru.mail.verify.core.platform.TimeProvider;
import ru.mail.verify.core.storage.InstanceConfig;
import ru.mail.verify.core.utils.FileLog;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/q/b.class */
public final class b {
    @NotNull
    private final TimeProvider a;
    @NotNull
    private final c b;
    private final String c;

    @Inject
    public b(@NotNull l lVar, @NotNull c cVar, @NotNull TimeProvider timeProvider) {
        Intrinsics.checkNotNullParameter(lVar, "instanceData");
        Intrinsics.checkNotNullParameter(timeProvider, "timeProvider");
        Intrinsics.checkNotNullParameter(cVar, "repository");
        this.a = timeProvider;
        this.b = cVar;
        this.c = lVar.getStringProperty(InstanceConfig.PropertyType.APP_VERSION);
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [long, ru.mail.libverify.q.b] */
    public final void a() {
        Locale locale;
        Object obj;
        if (Build.VERSION.SDK_INT >= 24) {
            ?? localTime = this.a.getLocalTime();
            long uptimeMillis = SystemClock.uptimeMillis();
            long startUptimeMillis = Process.getStartUptimeMillis();
            FileLog.d(b.class.getName(), "uptime: " + uptimeMillis + "ms");
            FileLog.d(b.class.getName(), new StringBuilder("bootTime: ").append(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US).format(Long.valueOf((long) localTime))).toString());
            FileLog.d(b.class.getName(), "appStartTime: " + startUptimeMillis + "ms");
            long j = localTime - ((localTime - localTime) + localTime);
            FileLog.d(b.class.getName(), "appRealStartTime: " + new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", locale).format(Long.valueOf((long) localTime)));
            FileLog.d(b.class.getName(), "currentTime: " + new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", locale).format(Long.valueOf((long) localTime)));
            FileLog.d(b.class.getName(), "startTime: " + j + "ms");
            Iterator it = localTime.b.get().iterator();
            while (true) {
                if (!it.hasNext()) {
                    obj = null;
                    break;
                }
                Object next = it.next();
                obj = next;
                if (Intrinsics.areEqual(((a) next).a(), this.c)) {
                    break;
                }
            }
            a aVar = (a) obj;
            a aVar2 = aVar;
            if (aVar == null) {
                aVar2 = r1;
                String str = this.c;
                Intrinsics.checkNotNullExpressionValue(str, "appVersion");
                a aVar3 = new a(str);
            }
            aVar2.a(j);
            this.b.a(aVar2);
        }
    }
}
