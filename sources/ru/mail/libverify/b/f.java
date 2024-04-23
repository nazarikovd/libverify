package ru.mail.libverify.b;

import android.net.Network;
import java.util.TreeSet;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.mail.libverify.api.VerifyRoute;
import ru.mail.libverify.i.p;
import ru.mail.libverify.i.q;
import ru.mail.libverify.storage.InstanceConfig;
import ru.mail.verify.core.requests.ConnectivityHelper;
import ru.mail.verify.core.utils.FileLog;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/b/f.class */
public final class f {
    @Nullable
    private final ru.mail.libverify.d.d a;
    @Nullable
    private final q b;
    @NotNull
    private final TreeSet c = d();

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/b/f$a.class */
    public /* synthetic */ class a {
        public static final /* synthetic */ int[] a;
        public static final /* synthetic */ int[] b;

        static {
            int[] iArr = new int[ru.mail.libverify.d.a.values().length];
            try {
                iArr[ru.mail.libverify.d.a.MANUAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ru.mail.libverify.d.a.RESEND.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ru.mail.libverify.d.a.VKLOGIN.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            a = iArr;
            int[] iArr2 = new int[VerifyRoute.values().length];
            try {
                iArr2[VerifyRoute.SMS.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr2[VerifyRoute.PUSH.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr2[VerifyRoute.CALL.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr2[VerifyRoute.CALLUI.ordinal()] = 4;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr2[VerifyRoute.CALLIN.ordinal()] = 5;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr2[VerifyRoute.VKCLogin.ordinal()] = 6;
            } catch (NoSuchFieldError unused9) {
            }
            b = iArr2;
        }
    }

    public f(@Nullable ru.mail.libverify.d.d dVar, @Nullable q qVar) {
        this.a = dVar;
        this.b = qVar;
    }

    private final TreeSet d() {
        if (this.b == null) {
            return SetsKt.sortedSetOf(new p.b[]{p.b.SMS, p.b.PUSH});
        }
        TreeSet sortedSetOf = SetsKt.sortedSetOf(new p.b[0]);
        if (this.b.g()) {
            sortedSetOf.add(p.b.SMS);
        }
        if (this.b.f()) {
            sortedSetOf.add(p.b.PUSH);
        }
        return sortedSetOf;
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x0153  */
    @org.jetbrains.annotations.NotNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final ru.mail.libverify.i.p.b[] c() {
        /*
            Method dump skipped, instructions count: 371
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: ru.mail.libverify.b.f.c():ru.mail.libverify.i.p$b[]");
    }

    @NotNull
    public final f a(@NotNull ru.mail.libverify.k.g gVar, @Nullable String str) {
        Intrinsics.checkNotNullParameter(gVar, "phoneCallInterceptor");
        q qVar = this.b;
        if ((qVar == null || qVar.a()) ? false : true) {
            return this;
        }
        if (((ru.mail.libverify.k.h) gVar).a(str)) {
            this.c.add(p.b.CALL);
        }
        return this;
    }

    @NotNull
    public final f b() {
        q qVar = this.b;
        if ((qVar == null || qVar.e()) ? false : true) {
            return this;
        }
        Network network = ConnectivityHelper.getNetwork().get();
        FileLog.d("VerifyChecksBuilder", "Result of cellular request: %s", network);
        if (network != null) {
            this.c.add(p.b.MOBILEID);
        }
        return this;
    }

    @NotNull
    public final f a() {
        q qVar = this.b;
        if (qVar != null && qVar.b()) {
            this.c.add(p.b.CALLIN);
        }
        return this;
    }

    @NotNull
    public final f a(@NotNull InstanceConfig instanceConfig) {
        Boolean c;
        Intrinsics.checkNotNullParameter(instanceConfig, "instanceConfig");
        if (instanceConfig.isCallUiFeatureEnable()) {
            this.c.add(p.b.CALLUI);
        }
        q qVar = this.b;
        if (qVar != null && (c = qVar.c()) != null) {
            if (c.booleanValue()) {
                this.c.add(p.b.CALLUI);
            } else {
                this.c.remove(p.b.CALLUI);
            }
        }
        return this;
    }
}
