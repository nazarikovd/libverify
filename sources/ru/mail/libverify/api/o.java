package ru.mail.libverify.api;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import ru.mail.libverify.api.VerificationApi;
import ru.mail.libverify.j.c;
import ru.mail.verify.core.utils.FileLog;
import ru.mail.verify.core.utils.Gsonable;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/o.class */
final class o implements Gsonable {
    private ru.mail.libverify.d.d verifyRouteCommand;
    private transient VerificationApi.VerificationState a;
    private transient int b;
    private final Map<String, String> defaultSmsCodeTemplates;
    private transient long c;
    private boolean verifiedOnce;
    @Nullable
    private ru.mail.libverify.j.n verifyApiResponse;
    @Nullable
    private ru.mail.libverify.j.n prevVerifyApiResponse;
    final String verificationService;
    @Nullable
    final String srcApplication;
    @Nullable
    final String userProvidedPhoneNumber;
    final String userId;
    final String id;
    @Nullable
    final ru.mail.libverify.i.q verifySessionSettings;
    final long startTimeStamp;
    @NonNull
    final Set<String> rawSmsTexts;
    @Nullable
    Long waitForRoutesTimestamp;
    @Nullable
    String smsCode;
    @Nullable
    transient ru.mail.libverify.d.b d;
    @Nullable
    transient ru.mail.libverify.d.b e;
    @Nullable
    String[] callFragmentTemplate;
    VerificationApi.VerificationSource smsCodeSource;
    VerificationApi.VerificationState state;
    VerificationApi.FailReason reason;
    @NonNull
    final ArrayList<ru.mail.libverify.c.b> mobileIdRoutes;
    @NonNull
    final Set<String> callInNumbers;

    private o() {
        this.b = 1;
        this.c = 0L;
        this.prevVerifyApiResponse = null;
        this.rawSmsTexts = new HashSet();
        this.smsCodeSource = VerificationApi.VerificationSource.UNKNOWN;
        this.state = VerificationApi.VerificationState.INITIAL;
        this.reason = VerificationApi.FailReason.OK;
        this.srcApplication = null;
        this.verificationService = null;
        this.defaultSmsCodeTemplates = null;
        this.userProvidedPhoneNumber = null;
        this.userId = null;
        this.id = null;
        this.startTimeStamp = 0L;
        this.c = 0L;
        this.verifySessionSettings = null;
        this.verifyRouteCommand = null;
        this.d = null;
        this.e = null;
        this.mobileIdRoutes = new ArrayList<>();
        this.callInNumbers = new HashSet();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean a() {
        long currentTimeMillis = System.currentTimeMillis() - this.startTimeStamp;
        Object[] objArr = new Object[3];
        objArr[0] = Long.valueOf(currentTimeMillis);
        objArr[1] = this.state;
        objArr[2] = Boolean.valueOf(this.verifyApiResponse != null);
        FileLog.v("SessionData", "Trace time from start = %d, state = %s, hasResponse = %s", objArr);
        if (currentTimeMillis < 0) {
            return false;
        }
        if (this.state == VerificationApi.VerificationState.SUSPENDED) {
            if (this.verifyApiResponse != null || currentTimeMillis <= c()) {
                return this.verifyApiResponse == null || currentTimeMillis <= 3600000;
            }
            return false;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final int c() {
        return this.verifiedOnce ? 1800000 : 45000;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final long f() {
        if (this.state == VerificationApi.VerificationState.SUSPENDED) {
            return Math.min(this.b * 1000, 3000);
        }
        return 0L;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean b() {
        VerificationApi.VerificationState verificationState = this.state;
        if (!(verificationState == VerificationApi.VerificationState.FINAL || verificationState == VerificationApi.VerificationState.SUCCEEDED)) {
            return verificationState == VerificationApi.VerificationState.FAILED;
        }
        ru.mail.libverify.j.n nVar = this.verifyApiResponse;
        return (nVar == null || TextUtils.isEmpty(nVar.t())) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    public final ru.mail.libverify.j.n e() {
        return this.verifyApiResponse;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final ru.mail.libverify.j.n d() {
        return this.prevVerifyApiResponse;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean h() {
        return this.verifiedOnce;
    }

    @NonNull
    public final ru.mail.libverify.d.d g() {
        ru.mail.libverify.d.d dVar = this.verifyRouteCommand;
        return dVar == null ? ru.mail.libverify.d.d.a() : dVar;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public o(String str, ru.mail.libverify.d.d dVar, @Nullable String str2, String str3, String str4, Map<String, String> map, @Nullable String str5, @Nullable ru.mail.libverify.i.q qVar) {
        this.b = 1;
        this.c = 0L;
        this.prevVerifyApiResponse = null;
        this.rawSmsTexts = new HashSet();
        this.smsCodeSource = VerificationApi.VerificationSource.UNKNOWN;
        this.state = VerificationApi.VerificationState.INITIAL;
        this.reason = VerificationApi.FailReason.OK;
        this.verificationService = str;
        this.userProvidedPhoneNumber = str2;
        this.srcApplication = str5;
        this.userId = str3;
        this.id = str4;
        this.startTimeStamp = System.currentTimeMillis();
        this.defaultSmsCodeTemplates = map;
        this.verifyRouteCommand = dVar;
        this.verifySessionSettings = qVar;
        this.mobileIdRoutes = new ArrayList<>();
        this.callInNumbers = new HashSet();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final String a(String str) {
        Map<String, String> map = this.defaultSmsCodeTemplates;
        if (map == null) {
            return null;
        }
        return map.get(str);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean a(long j) {
        if (this.state == VerificationApi.VerificationState.SUSPENDED) {
            long j2 = this.c;
            if (j2 != 0 && j - j2 > 3600000) {
                FileLog.v("SessionData", "Attempt request time expired");
                return false;
            }
            return true;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void a(@Nullable ru.mail.libverify.j.n nVar) {
        if (nVar == null) {
            ru.mail.libverify.j.n nVar2 = this.verifyApiResponse;
            if (nVar2 != null) {
                this.prevVerifyApiResponse = nVar2;
            }
        } else {
            this.prevVerifyApiResponse = null;
        }
        if (!this.verifiedOnce && nVar != null) {
            ru.mail.libverify.i.q qVar = this.verifySessionSettings;
            if (qVar == null || qVar.h()) {
                this.verifiedOnce = true;
            } else {
                this.verifiedOnce = nVar.isOk() || nVar.d() == c.b.VERIFIED;
            }
        }
        this.verifyApiResponse = nVar;
    }

    public final void a(ru.mail.libverify.d.d dVar) {
        this.verifyRouteCommand = dVar;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void a(VerificationApi.VerificationState verificationState, VerificationApi.FailReason failReason, long j) {
        if (this.a != verificationState) {
            this.b = 0;
            this.c = 0L;
        } else if (this.state == VerificationApi.VerificationState.SUSPENDED) {
            int i = this.b;
            if (i == 0) {
                this.c = j;
            }
            this.b = i + 1;
        }
        VerificationApi.VerificationState verificationState2 = this.state;
        this.a = verificationState2;
        this.state = verificationState;
        this.reason = failReason;
        FileLog.v("SessionData", "Change session = %s state %s->%s (count %d) reason %s", this.id, verificationState2, verificationState, Integer.valueOf(this.b), failReason);
    }
}
