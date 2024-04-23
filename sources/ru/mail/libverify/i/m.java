package ru.mail.libverify.i;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Set;
import ru.mail.verify.core.requests.RequestPersistentId;
import ru.mail.verify.core.utils.Gsonable;
import ru.mail.verify.core.utils.Utils;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/i/m.class */
public final class m implements Gsonable, RequestPersistentId {
    public final String action;
    public final String policy;
    public final String from;
    public final int blockTimeoutSec;
    public final String checkParams;
    public final String smsParams;
    public final String appCheckParams;
    public final String pushToken;
    @Nullable
    public final ArrayList<ru.mail.libverify.c.b> mobileIdRoutes;
    public final String sessionId;
    @Nullable
    public String phone;
    @Nullable
    public Set<ru.mail.libverify.q.a> startTimings;
    public final String sign;

    @NonNull
    public static m a(@Nullable String str) {
        return new m(null, null, str, null);
    }

    @NonNull
    public static m d(@NonNull String str, @Nullable String str2) {
        return new m("report_signout", str, str2, null);
    }

    @NonNull
    public static m c(@NonNull String str, @Nullable String str2, @Nullable String str3, @NonNull String str4) {
        return new m("check", null, null, 0, str, str2, null, str3, null, str4);
    }

    @NonNull
    public static m b(@NonNull String str, @Nullable String str2) {
        return new m(null, null, null, 0, null, null, str, str2, null, null);
    }

    private m() {
        this.phone = null;
        this.startTimings = null;
        this.action = null;
        this.policy = null;
        this.from = null;
        this.blockTimeoutSec = 0;
        this.checkParams = null;
        this.smsParams = null;
        this.appCheckParams = null;
        this.pushToken = null;
        this.mobileIdRoutes = null;
        this.sessionId = null;
        this.sign = null;
    }

    @Override // ru.mail.verify.core.requests.RequestPersistentId
    @NonNull
    public final String getId() {
        Locale locale = Locale.US;
        Object[] objArr = new Object[10];
        objArr[0] = this.action;
        objArr[1] = this.from;
        objArr[2] = Integer.valueOf(this.blockTimeoutSec);
        objArr[3] = this.checkParams;
        objArr[4] = this.smsParams;
        objArr[5] = this.policy;
        String str = this.appCheckParams;
        objArr[6] = str == null ? null : Utils.stringToMD5(str);
        objArr[7] = this.pushToken;
        ArrayList<ru.mail.libverify.c.b> arrayList = this.mobileIdRoutes;
        objArr[8] = arrayList == null ? null : Integer.valueOf(arrayList.size());
        objArr[9] = this.sessionId;
        return String.format(locale, "update_%s_%s_%d_%s_%s_%s_%s_%s_%s_%s", objArr);
    }

    @NonNull
    public static m a(@NonNull String str, @Nullable String str2) {
        return new m(str, null, str2, null);
    }

    @NonNull
    public static m c(@NonNull String str, @Nullable String str2) {
        return new m("report_spam", null, str, 0, null, null, null, str2, null, null);
    }

    @NonNull
    public static m b(@NonNull String str, @NonNull String str2, @Nullable String str3, @Nullable String str4) {
        return new m(str, str2, str3, "callin_click", str4);
    }

    private m(@NonNull String str, @NonNull String str2, String str3, int i, String str4, String str5, String str6, @Nullable String str7, @Nullable String str8, @Nullable String str9) {
        this.phone = null;
        this.startTimings = null;
        this.action = str;
        this.policy = str2;
        this.from = str3;
        this.blockTimeoutSec = i;
        this.checkParams = str4;
        this.smsParams = str5;
        this.appCheckParams = str6;
        this.pushToken = str7;
        this.mobileIdRoutes = null;
        this.sessionId = str8;
        this.sign = str9;
    }

    @NonNull
    public static m a(int i, @NonNull String str, @Nullable String str2) {
        return new m("block", null, str, i, null, null, null, str2, null, null);
    }

    private m(@NonNull String str, @NonNull String str2, @Nullable String str3, @Nullable String str4) {
        this.phone = null;
        this.startTimings = null;
        this.action = str;
        this.policy = str2;
        this.from = null;
        this.blockTimeoutSec = 0;
        this.checkParams = null;
        this.smsParams = null;
        this.appCheckParams = null;
        this.pushToken = str3;
        this.mobileIdRoutes = null;
        this.sessionId = null;
        this.sign = str4;
    }

    @NonNull
    public static m a(@NonNull String str, @NonNull String str2, @Nullable String str3, @Nullable String str4) {
        return new m(str, str2, str3, "callin_call", str4);
    }

    private m(@NonNull String str, @NonNull String str2, @Nullable String str3, @Nullable String str4, @Nullable String str5) {
        this.startTimings = null;
        this.action = str4;
        this.policy = null;
        this.from = null;
        this.blockTimeoutSec = 0;
        this.checkParams = null;
        this.smsParams = null;
        this.appCheckParams = null;
        this.pushToken = str3;
        this.mobileIdRoutes = null;
        this.sessionId = str2;
        this.sign = str5;
        this.phone = str;
    }

    @NonNull
    public static m a(@NonNull String str, @Nullable String str2, @NonNull String str3) {
        return new m("check", null, null, 0, str, null, null, str2, null, str3);
    }

    private m(@NonNull ArrayList<ru.mail.libverify.c.b> arrayList, @NonNull String str, @Nullable String str2, @Nullable String str3, @Nullable String str4) {
        this.phone = null;
        this.startTimings = null;
        this.action = str3;
        this.policy = null;
        this.from = null;
        this.blockTimeoutSec = 0;
        this.checkParams = null;
        this.smsParams = null;
        this.appCheckParams = null;
        this.pushToken = str2;
        this.mobileIdRoutes = arrayList;
        this.sessionId = str;
        this.sign = str4;
    }

    @NonNull
    public static m a(@NonNull m mVar, @NonNull ArrayList arrayList) {
        ru.mail.libverify.c.b bVar = (ru.mail.libverify.c.b) arrayList.get(arrayList.size() - 1);
        if (bVar != null) {
            arrayList.add(new ru.mail.libverify.c.b(bVar.a(), -2));
        }
        return new m(arrayList, mVar.sessionId, mVar.pushToken, "mobileid", mVar.sign);
    }

    private m(@NonNull Set<ru.mail.libverify.q.a> set) {
        this.action = null;
        this.policy = null;
        this.from = null;
        this.blockTimeoutSec = 0;
        this.checkParams = null;
        this.smsParams = null;
        this.appCheckParams = null;
        this.pushToken = null;
        this.mobileIdRoutes = null;
        this.sessionId = null;
        this.sign = null;
        this.phone = null;
        this.startTimings = set;
    }

    @NonNull
    public static m a(@NonNull ArrayList<ru.mail.libverify.c.b> arrayList, @NonNull String str, @Nullable String str2, @Nullable String str3) {
        return new m(arrayList, str, str2, "mobileid", str3);
    }

    @NonNull
    public static m a(@NonNull Set<ru.mail.libverify.q.a> set) {
        return new m(set);
    }
}
