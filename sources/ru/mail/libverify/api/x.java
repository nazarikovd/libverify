package ru.mail.libverify.api;

import android.net.Network;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.michaelrocks.libphonenumber.android.NumberParseException;
import io.michaelrocks.libphonenumber.android.Phonenumber;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import ru.mail.libverify.api.VerificationApi;
import ru.mail.libverify.api.g;
import ru.mail.libverify.api.q;
import ru.mail.libverify.d.b;
import ru.mail.libverify.gcm.ServerInfo;
import ru.mail.libverify.i.p;
import ru.mail.libverify.j.c;
import ru.mail.libverify.j.n;
import ru.mail.libverify.k.g;
import ru.mail.libverify.platform.sms.SmsRetrieverManager;
import ru.mail.libverify.storage.DecryptionError;
import ru.mail.libverify.storage.InstanceConfig;
import ru.mail.verify.core.api.ApplicationModule;
import ru.mail.verify.core.platform.TimeProvider;
import ru.mail.verify.core.requests.ConnectivityHelper;
import ru.mail.verify.core.requests.FutureWrapper;
import ru.mail.verify.core.storage.BroadcastManager;
import ru.mail.verify.core.timer.TimerManager;
import ru.mail.verify.core.utils.DebugUtils;
import ru.mail.verify.core.utils.FileLog;
import ru.mail.verify.core.utils.ServerException;
import ru.mail.verify.core.utils.SessionIdGeneratorImpl;
import ru.mail.verify.core.utils.Utils;
import ru.mail.verify.core.utils.components.BusMessageType;
import ru.mail.verify.core.utils.components.MessageBus;
import ru.mail.verify.core.utils.components.MessageBusUtils;
import ru.mail.verify.core.utils.json.JsonParseException;
import ru.mail.verify.core.utils.json.JsonParser;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/x.class */
public final class x {
    private static final SessionIdGeneratorImpl B = new SessionIdGeneratorImpl();
    private final ru.mail.libverify.k.l a;
    private final ru.mail.libverify.k.g b;
    private final SmsRetrieverManager c;
    private final MessageBus d;
    private TimeProvider e;
    private final CommonContext f;
    private final o g;
    private final ru.mail.libverify.m.n h;
    private ru.mail.libverify.k.e i;
    private g.a j;
    private g.a k;
    private SmsRetrieverManager.SmsRetrieverSmsCallback l;
    private Future m;
    private Future n;
    private ru.mail.libverify.m.r o;
    private final TimerManager r;
    @Nullable
    private ru.mail.libverify.l.b s;
    private Runnable p = new b();
    private Runnable q = new c();
    private boolean t = false;
    private boolean u = false;
    @Nullable
    private String v = null;
    @Nullable
    private String w = null;
    @Nullable
    private String x = null;
    @Nullable
    private String y = null;
    @Nullable
    private String z = null;
    private String A = null;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/x$a.class */
    public static /* synthetic */ class a {
        static final /* synthetic */ int[] a;
        static final /* synthetic */ int[] b;
        static final /* synthetic */ int[] c;
        static final /* synthetic */ int[] d;
        static final /* synthetic */ int[] e;

        static {
            int[] iArr = new int[VerificationApi.VerificationSource.values().length];
            e = iArr;
            try {
                iArr[VerificationApi.VerificationSource.APPLICATION_LOCAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                e[VerificationApi.VerificationSource.SMS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                e[VerificationApi.VerificationSource.CALL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                e[VerificationApi.VerificationSource.APPLICATION_EXTERNAL.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                e[VerificationApi.VerificationSource.USER_INPUT.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                e[VerificationApi.VerificationSource.ALREADY_VERIFIED.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                e[VerificationApi.VerificationSource.UNKNOWN.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            int[] iArr2 = new int[VerificationApi.VerificationState.values().length];
            d = iArr2;
            try {
                iArr2[VerificationApi.VerificationState.INITIAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                d[VerificationApi.VerificationState.VERIFYING_PHONE_NUMBER.ordinal()] = 2;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                d[VerificationApi.VerificationState.SUSPENDED.ordinal()] = 3;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                d[VerificationApi.VerificationState.WAITING_FOR_SMS_CODE.ordinal()] = 4;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                d[VerificationApi.VerificationState.VERIFYING_SMS_CODE.ordinal()] = 5;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                d[VerificationApi.VerificationState.SUCCEEDED.ordinal()] = 6;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                d[VerificationApi.VerificationState.FAILED.ordinal()] = 7;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                d[VerificationApi.VerificationState.FINAL.ordinal()] = 8;
            } catch (NoSuchFieldError unused15) {
            }
            int[] iArr3 = new int[c.a.values().length];
            c = iArr3;
            try {
                iArr3[c.a.NO_CALL.ordinal()] = 1;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                c[c.a.INCORRECT_CODE.ordinal()] = 2;
            } catch (NoSuchFieldError unused17) {
            }
            int[] iArr4 = new int[c.b.values().length];
            b = iArr4;
            try {
                iArr4[c.b.OK.ordinal()] = 1;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                b[c.b.VERIFIED.ordinal()] = 2;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                b[c.b.UNKNOWN.ordinal()] = 3;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                b[c.b.NOT_ENOUGH_DATA.ordinal()] = 4;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                b[c.b.ERROR.ordinal()] = 5;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                b[c.b.PHONE_NUMBER_IN_BLACK_LIST.ordinal()] = 6;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                b[c.b.INCORRECT_PHONE_NUMBER.ordinal()] = 7;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                b[c.b.PHONE_NUMBER_TYPE_NOT_ALLOWED.ordinal()] = 8;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                b[c.b.UNSUPPORTED_NUMBER.ordinal()] = 9;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                b[c.b.RATELIMIT.ordinal()] = 10;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                b[c.b.ATTEMPTLIMIT.ordinal()] = 11;
            } catch (NoSuchFieldError unused28) {
            }
            int[] iArr5 = new int[p.b.values().length];
            a = iArr5;
            try {
                iArr5[p.b.CALLIN.ordinal()] = 1;
            } catch (NoSuchFieldError unused29) {
            }
            try {
                a[p.b.CALLUI.ordinal()] = 2;
            } catch (NoSuchFieldError unused30) {
            }
            try {
                a[p.b.SMS.ordinal()] = 3;
            } catch (NoSuchFieldError unused31) {
            }
        }
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/x$b.class */
    class b implements Runnable {
        b() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            x.this.B();
        }
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/x$c.class */
    class c implements Runnable {
        c() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            if (x.this.g.e() == null) {
                FileLog.e("VerificationSession", "wait for verify answer timeout expired");
                x xVar = x.this;
                xVar.getClass();
                xVar.a(xVar.a(VerificationApi.VerificationState.FAILED, VerificationApi.VerificationSource.UNKNOWN, j.c(), xVar.g.e()));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/x$d.class */
    public class d implements FutureWrapper.FutureListener<ru.mail.libverify.j.a> {
        final /* synthetic */ ru.mail.libverify.i.a a;

        d(ru.mail.libverify.i.a aVar) {
            this.a = aVar;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v19, types: [ru.mail.libverify.api.VerificationApi$VerificationStateDescriptor] */
        /* JADX WARN: Type inference failed for: r0v4, types: [ru.mail.libverify.api.c0] */
        /* JADX WARN: Type inference failed for: r0v7, types: [java.lang.Throwable] */
        @Override // ru.mail.verify.core.requests.FutureWrapper.FutureListener
        public final void onComplete(Future<ru.mail.libverify.j.a> future) {
            VerificationApi.VerificationStateDescriptor d;
            if (future.isCancelled()) {
                return;
            }
            x xVar = x.this;
            xVar.m = null;
            ru.mail.libverify.i.a aVar = this.a;
            ?? c0Var = new c0(xVar, future);
            try {
                c0Var = c0Var.a();
                d = c0Var;
            } catch (InterruptedException e) {
                e = e;
                FileLog.e("VerificationSession", "apiMethodToNextState", e);
                d = xVar.d();
            } catch (CancellationException e2) {
                e = e2;
                FileLog.e("VerificationSession", "apiMethodToNextState", e);
                d = xVar.d();
            } catch (ExecutionException e3) {
                th = e3;
                Throwable cause = c0Var.getCause();
                if (cause != null) {
                    FileLog.d("VerificationSession", "apiMethodToNextState", cause);
                    xVar.d.post(MessageBusUtils.createMultipleArgs(BusMessageType.VERIFY_API_HANDLE_SERVER_FAILURE, aVar, cause));
                    d = ((cause instanceof ServerException) || (cause instanceof IOException)) ? xVar.a(VerificationApi.VerificationState.SUSPENDED, VerificationApi.VerificationSource.UNKNOWN, j.b(), xVar.g.e()) : xVar.d();
                }
                DebugUtils.safeThrow("VerificationSession", "apiMethodToNextState", th);
                d = xVar.d();
            } catch (Throwable th) {
                th = th;
                DebugUtils.safeThrow("VerificationSession", "apiMethodToNextState", th);
                d = xVar.d();
            }
            xVar.a(d);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public x(@NonNull ru.mail.libverify.k.l lVar, @NonNull ru.mail.libverify.k.g gVar, @NonNull SmsRetrieverManager smsRetrieverManager, @NonNull CommonContext commonContext, @NonNull ru.mail.libverify.m.r rVar, @NonNull String str, @NonNull ru.mail.libverify.d.d dVar, @Nullable String str2, @Nullable String str3, @NonNull TimeProvider timeProvider, @Nullable Map<String, String> map, @Nullable String str4, @Nullable ru.mail.libverify.i.q qVar, @NonNull TimerManager timerManager, @Nullable ru.mail.libverify.l.b bVar) {
        this.a = lVar;
        this.b = gVar;
        this.c = smsRetrieverManager;
        this.f = commonContext;
        this.o = rVar;
        q.f fVar = (q.f) commonContext;
        this.d = fVar.getBus();
        this.e = timeProvider;
        this.g = new o(str, dVar, str2, str3, B.generateId(), map, str4, qVar);
        this.h = new ru.mail.libverify.m.n(fVar);
        this.r = timerManager;
        this.s = bVar;
    }

    private void C() {
        o oVar = this.g;
        this.d.post(MessageBusUtils.createMultipleArgs(BusMessageType.VERIFICATION_SESSION_STATE_CHANGED, this.g.id, a(oVar.state, oVar.smsCodeSource, oVar.reason, oVar.e())));
    }

    private void y() {
        if (this.j == null) {
            g.a aVar = str -> {
                return a(str, true);
            };
            this.j = aVar;
            ((ru.mail.libverify.k.h) this.b).a(aVar);
        }
        if (this.k == null) {
            this.k = this::d;
            ((ru.mail.libverify.k.h) this.b).a(this::c, this.k);
        }
    }

    private g.a b() {
        g.a aVar = new g.a();
        aVar.c = n.a.NUMERIC;
        if (this.g.e() == null || this.g.e().g() == null || this.g.e().g().length == 0) {
            String value = this.f.getSettings().getValue("verification_session_last_saved_call_template");
            if (TextUtils.isEmpty(value)) {
                FileLog.e("VerificationSession", "getCallParseData - can't parse incoming call without pattern");
                return null;
            }
            try {
                aVar.a = (String[]) JsonParser.fromJson(value, String[].class);
            } catch (JsonParseException e) {
                DebugUtils.safeThrow("VerificationSession", "filed to read saved templates", e);
            }
            String[] strArr = aVar.a;
            if (strArr == null || strArr.length == 0) {
                FileLog.e("VerificationSession", "getCallParseData - wrong saved pattern detected");
                return null;
            }
            String a2 = a("verification_session_last_saved_code_length", this.f.getConfig().getCurrentLocale().getLanguage());
            if (!TextUtils.isEmpty(a2)) {
                try {
                    aVar.b = Integer.parseInt(a2);
                } catch (NumberFormatException unused) {
                }
            }
        } else {
            aVar.a = this.g.e().g();
            aVar.b = this.g.e().i();
        }
        return aVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public g.a j() {
        g.a aVar = new g.a();
        HashSet hashSet = new HashSet();
        if (this.g.e() != null) {
            if (!TextUtils.isEmpty(this.g.e().r())) {
                hashSet.add(this.g.e().r());
            }
            if (!TextUtils.isEmpty(this.g.e().o())) {
                hashSet.add(this.g.e().o());
            }
        }
        int size = hashSet.size();
        String[] strArr = new String[size];
        hashSet.toArray(strArr);
        if (this.g.e() == null || size <= 0) {
            String language = this.f.getConfig().getCurrentLocale().getLanguage();
            String a2 = a("verification_session_last_saved_sms_template", language);
            String str = a2;
            if (TextUtils.isEmpty(a2)) {
                FileLog.d("VerificationSession", "getSmsCodeParseData - there is no saved pattern. try to get default one.");
                String a3 = this.g.a(language);
                str = a3;
                if (TextUtils.isEmpty(a3)) {
                    FileLog.e("VerificationSession", "getSmsCodeParseData - can't parse incoming sms without pattern");
                    return null;
                }
            }
            aVar.a = new String[]{str};
            String a4 = a("verification_session_last_saved_code_type", language);
            if (!TextUtils.isEmpty(a4)) {
                aVar.c = n.a.valueOf(a4);
            }
            String a5 = a("verification_session_last_saved_code_length", language);
            if (!TextUtils.isEmpty(a5)) {
                try {
                    aVar.b = Integer.parseInt(a5);
                } catch (NumberFormatException unused) {
                }
            }
        } else {
            aVar.a = strArr;
            aVar.b = this.g.e().i();
            aVar.c = this.g.e().j();
        }
        return aVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public VerificationApi.VerificationStateDescriptor d() {
        return new VerificationApi.VerificationStateDescriptor(VerificationApi.VerificationState.FAILED, j.a(), this.g.h());
    }

    private void B() {
        boolean z;
        VerificationApi.VerificationStateDescriptor verificationStateDescriptor = null;
        switch (a.d[this.g.state.ordinal()]) {
            case BroadcastManager.FLAG_NETWORK_RECEIVER /* 1 */:
                verificationStateDescriptor = r0;
                VerificationApi.VerificationStateDescriptor verificationStateDescriptor2 = new VerificationApi.VerificationStateDescriptor(VerificationApi.VerificationState.VERIFYING_PHONE_NUMBER, this.g.h());
                this.f.getDispatcher().postDelayed(this.q, this.g.c());
                break;
            case 2:
                if (this.A == null) {
                    this.o.b(this.g.id, TextUtils.isEmpty(this.g.userProvidedPhoneNumber) ? this.g.userId : this.g.userProvidedPhoneNumber);
                    return;
                }
                if (this.i == null) {
                    this.i = ((ru.mail.libverify.k.m) this.a).b().a(new b0(this)).a();
                }
                y();
                if (this.l == null) {
                    z zVar = new z(this);
                    this.l = zVar;
                    this.c.register(zVar);
                }
                verificationStateDescriptor = n();
                break;
            case 3:
            case 4:
                if (this.i == null) {
                    this.i = ((ru.mail.libverify.k.m) this.a).b().a(new b0(this)).a();
                }
                y();
                if (this.l == null) {
                    z zVar2 = new z(this);
                    this.l = zVar2;
                    this.c.register(zVar2);
                }
                if (this.g.e() == null) {
                    verificationStateDescriptor = r0;
                    VerificationApi.VerificationStateDescriptor verificationStateDescriptor3 = new VerificationApi.VerificationStateDescriptor(VerificationApi.VerificationState.VERIFYING_PHONE_NUMBER, this.g.h(), this.g.d());
                } else if (!TextUtils.isEmpty(this.g.e().t())) {
                    VerificationApi.VerificationState verificationState = VerificationApi.VerificationState.SUCCEEDED;
                    o oVar = this.g;
                    verificationStateDescriptor = a(verificationState, oVar.smsCodeSource, VerificationApi.FailReason.OK, oVar.e());
                } else if (!TextUtils.isEmpty(this.g.smsCode)) {
                    VerificationApi.VerificationState verificationState2 = VerificationApi.VerificationState.VERIFYING_SMS_CODE;
                    o oVar2 = this.g;
                    verificationStateDescriptor = a(verificationState2, oVar2.smsCodeSource, VerificationApi.FailReason.OK, oVar2.e());
                } else if (!this.g.rawSmsTexts.isEmpty()) {
                    if (!this.g.rawSmsTexts.isEmpty()) {
                        FileLog.d("VerificationSession", "verifyRawText %s", Arrays.toString(this.g.rawSmsTexts.toArray()));
                        Iterator<String> it = this.g.rawSmsTexts.iterator();
                        while (true) {
                            if (it.hasNext()) {
                                String next = it.next();
                                int i = a.e[this.g.smsCodeSource.ordinal()];
                                if (i == 1 || i == 2) {
                                    next = g.a(ru.mail.libverify.v.a.a().provideStartConfig().isSimpleCodeParser(), next, j());
                                } else if (i != 3) {
                                    FileLog.e("VerificationSession", "Illegal state %s for a sms code restoring from a raw text", this.g.smsCodeSource);
                                    next = null;
                                } else {
                                    String[] strArr = null;
                                    ru.mail.libverify.d.b bVar = this.g.e;
                                    if (!(bVar instanceof b.C0004b) || TextUtils.isEmpty(((b.C0004b) bVar).d())) {
                                        String[] strArr2 = this.g.callFragmentTemplate;
                                        if (strArr2 != null && strArr2.length != 0) {
                                            strArr = strArr2;
                                        }
                                    } else {
                                        strArr = r0;
                                        String[] strArr3 = {((b.C0004b) this.g.e).d()};
                                    }
                                    if (TextUtils.isEmpty(next) || strArr == null || strArr.length == 0) {
                                        FileLog.v("CodeParser", String.format("containsFragment return false. messageText: %s parseData: %s", next, strArr));
                                        z = false;
                                    } else {
                                        int length = strArr.length;
                                        int i2 = 0;
                                        while (true) {
                                            if (i2 >= length) {
                                                z = false;
                                            } else if (next.contains(strArr[i2])) {
                                                z = true;
                                            } else {
                                                i2++;
                                            }
                                        }
                                    }
                                    if (!z) {
                                        next = g.a(ru.mail.libverify.v.a.a().provideStartConfig().isSimpleCodeParser(), next, b());
                                    }
                                }
                                if (!TextUtils.isEmpty(next)) {
                                    a(next, (String) null, this.g.smsCodeSource);
                                }
                            }
                        }
                    }
                } else {
                    o oVar3 = this.g;
                    if (oVar3.callFragmentTemplate != null) {
                        if (((ru.mail.libverify.k.h) this.b).a(oVar3.userProvidedPhoneNumber)) {
                            FileLog.d("VerificationSession", "start checking last calls");
                            ((ru.mail.libverify.k.h) this.b).a(new a0(this));
                        } else {
                            FileLog.d("VerificationSession", "skip checking last calls (call is not possible)");
                        }
                    }
                }
                if (this.g.state == VerificationApi.VerificationState.WAITING_FOR_SMS_CODE) {
                    this.f.getDispatcher().removeCallbacks(this.q);
                    break;
                }
                break;
            case 5:
                try {
                    verificationStateDescriptor = m();
                    break;
                } catch (MalformedURLException e) {
                    FileLog.e("VerificationSession", "On verifying sms code handle exception", e);
                    verificationStateDescriptor = d();
                    break;
                }
            case 6:
            case 7:
                VerificationApi.VerificationState verificationState3 = VerificationApi.VerificationState.FINAL;
                o oVar4 = this.g;
                verificationStateDescriptor = a(verificationState3, oVar4.smsCodeSource, oVar4.reason, oVar4.e());
                break;
            case 8:
                if (this.m != null) {
                    FileLog.v("VerificationSession", "cancel main request");
                    this.m.cancel(true);
                    this.m = null;
                }
                this.f.getDispatcher().removeCallbacks(this.q);
                ru.mail.libverify.k.e eVar = this.i;
                if (eVar != null) {
                    eVar.a();
                    this.i = null;
                }
                g.a aVar = this.j;
                if (aVar != null) {
                    ((ru.mail.libverify.k.h) this.b).b(aVar);
                    this.j = null;
                }
                g.a aVar2 = this.k;
                if (aVar2 != null) {
                    ((ru.mail.libverify.k.h) this.b).c(aVar2);
                    this.k = null;
                }
                SmsRetrieverManager.SmsRetrieverSmsCallback smsRetrieverSmsCallback = this.l;
                if (smsRetrieverSmsCallback != null) {
                    this.c.unregister(smsRetrieverSmsCallback);
                    this.l = null;
                    break;
                }
                break;
            default:
                throw new IllegalArgumentException("Undefined state for current session");
        }
        a(verificationStateDescriptor);
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0080 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0082  */
    @androidx.annotation.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private ru.mail.libverify.api.VerificationApi.VerificationStateDescriptor m() throws java.net.MalformedURLException {
        /*
            Method dump skipped, instructions count: 341
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: ru.mail.libverify.api.x.m():ru.mail.libverify.api.VerificationApi$VerificationStateDescriptor");
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x00c7 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00c9  */
    @androidx.annotation.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private ru.mail.libverify.api.VerificationApi.VerificationStateDescriptor n() {
        /*
            Method dump skipped, instructions count: 523
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: ru.mail.libverify.api.x.n():ru.mail.libverify.api.VerificationApi$VerificationStateDescriptor");
    }

    @NonNull
    public final String e() {
        return this.g.id;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @NonNull
    public final String h() throws JsonParseException {
        return JsonParser.toJson(this.g);
    }

    @NonNull
    public final VerificationApi.VerificationStateDescriptor k() {
        o oVar = this.g;
        return a(oVar.state, oVar.smsCodeSource, oVar.reason, oVar.e());
    }

    @NonNull
    final o c() {
        return this.g;
    }

    public final void D() {
        B();
    }

    public final void a() {
        FileLog.v("VerificationSession", "cancel session");
        if (this.z != null) {
            FileLog.d("VerificationSession", "End waiting callin time");
            this.r.cancelTimer(this.z);
        }
        if (this.y != null) {
            FileLog.d("VerificationSession", "End waiting callui time");
            this.r.cancelTimer(this.y);
        }
        if (this.x != null) {
            FileLog.d("VerificationSession", "End waiting sms time");
            this.r.cancelTimer(this.x);
        }
        if (this.w != null) {
            FileLog.d("VerificationSession", "End waiting mobile_id time");
            this.r.cancelTimer(this.w);
        }
        a((String) null);
        if (this.m != null) {
            FileLog.v("VerificationSession", "cancel main request");
            this.m.cancel(true);
            this.m = null;
        }
        if (this.n != null) {
            FileLog.v("VerificationSession", "cancel ivr request");
            this.n.cancel(true);
            this.n = null;
        }
        this.f.getDispatcher().removeCallbacks(this.q);
        this.g.a(VerificationApi.VerificationState.FINAL, VerificationApi.FailReason.OK, this.e.getLocalTime());
        B();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void z() {
        boolean z;
        boolean z2;
        FileLog.v("VerificationSession", "session %s new sms code requested", this.g.id);
        if (this.g.b()) {
            FileLog.e("VerificationSession", "failed to modify session state after completion");
            z = true;
        } else {
            z = false;
        }
        if (z) {
            return;
        }
        ru.mail.libverify.d.b bVar = this.g.e;
        if (bVar instanceof b.C0004b) {
            FileLog.e("VerificationSession", "Route %s not permit resend!", bVar.getClass().getName());
            z2 = false;
        } else {
            z2 = true;
        }
        if (z2) {
            this.g.a((ru.mail.libverify.j.n) null);
            o oVar = this.g;
            oVar.callFragmentTemplate = null;
            oVar.smsCodeSource = VerificationApi.VerificationSource.UNKNOWN;
            oVar.smsCode = null;
            oVar.a(ru.mail.libverify.d.d.a(p.b.SMS));
            o oVar2 = this.g;
            oVar2.e = null;
            oVar2.d = null;
            oVar2.rawSmsTexts.clear();
            FileLog.v("VerificationSession", "session %s reset verification error", this.g.id);
            o oVar3 = this.g;
            if (oVar3.state == VerificationApi.VerificationState.WAITING_FOR_SMS_CODE && oVar3.reason == VerificationApi.FailReason.INCORRECT_SMS_CODE) {
                oVar3.reason = VerificationApi.FailReason.OK;
            }
            C();
            B();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void A() {
        boolean z;
        boolean z2;
        if (this.g.b()) {
            FileLog.e("VerificationSession", "failed to modify session state after completion");
            z = true;
        } else {
            z = false;
        }
        if (z) {
            return;
        }
        FileLog.v("VerificationSession", "session %s reset verification error", this.g.id);
        o oVar = this.g;
        if (oVar.state == VerificationApi.VerificationState.WAITING_FOR_SMS_CODE && oVar.reason == VerificationApi.FailReason.INCORRECT_SMS_CODE) {
            oVar.reason = VerificationApi.FailReason.OK;
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2) {
            C();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void x() {
        boolean z;
        FileLog.v("VerificationSession", "session %s verified from other instance", this.g.id);
        if (this.g.b()) {
            FileLog.e("VerificationSession", "failed to modify session state after completion");
            z = true;
        } else {
            z = false;
        }
        if (z) {
            return;
        }
        this.g.a((ru.mail.libverify.j.n) null);
        o oVar = this.g;
        oVar.callFragmentTemplate = null;
        oVar.smsCodeSource = VerificationApi.VerificationSource.APPLICATION_EXTERNAL;
        C();
        B();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void w() {
        if (this.m != null) {
            FileLog.v("VerificationSession", "cancel main request");
            this.m.cancel(true);
            this.m = null;
        }
        FileLog.v("VerificationSession", "onNetworkBecameAvailable");
        B();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    public final String f() {
        return this.A;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @NonNull
    public final ArrayList<ru.mail.libverify.c.b> g() {
        return this.g.mobileIdRoutes;
    }

    public final String toString() {
        return super.toString();
    }

    public final long l() {
        return this.g.startTimeStamp;
    }

    @NonNull
    public final String i() {
        return this.g.verificationService;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public x(@NonNull ru.mail.libverify.k.l lVar, @NonNull ru.mail.libverify.k.g gVar, @NonNull SmsRetrieverManager smsRetrieverManager, @NonNull TimeProvider timeProvider, @NonNull CommonContext commonContext, @NonNull ru.mail.libverify.m.r rVar, @NonNull String str, @NonNull TimerManager timerManager, @Nullable ru.mail.libverify.l.b bVar) throws JsonParseException, IllegalStateException {
        this.a = lVar;
        this.b = gVar;
        this.c = smsRetrieverManager;
        this.f = commonContext;
        this.o = rVar;
        this.e = timeProvider;
        q.f fVar = (q.f) commonContext;
        this.d = fVar.getBus();
        o oVar = (o) JsonParser.fromJson(str, o.class);
        this.g = oVar;
        if (oVar == null) {
            throw new IllegalStateException("data field must be initialized");
        }
        this.h = new ru.mail.libverify.m.n(fVar);
        this.r = timerManager;
        this.s = bVar;
    }

    private boolean d(@Nullable String str) {
        if (str == null) {
            return false;
        }
        this.d.post(MessageBusUtils.createMultipleArgs(BusMessageType.VERIFICATION_SESSION_CALL_IN_EXECUTED, this.g.id, str));
        return true;
    }

    private boolean c(String str) {
        if (str == null) {
            return false;
        }
        Set<String> set = this.g.callInNumbers;
        try {
            Phonenumber.PhoneNumber parse = this.f.getConfig().getPhoneNumberUtil().parse(str, Locale.getDefault().getCountry());
            str = String.valueOf(parse.getCountryCode()) + parse.getNationalNumber();
        } catch (NumberParseException e) {
            FileLog.d("VerificationSession", "Failed to parse number", (Throwable) e);
        }
        return set.contains(str);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void g(@NonNull String str) {
        a(str, (String) null, VerificationApi.VerificationSource.USER_INPUT);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void f(@NonNull String str) {
        a(str, true);
    }

    public final void e(@NonNull String str) {
        this.A = str;
    }

    private void b(@Nullable ru.mail.libverify.d.b bVar) {
        o oVar = this.g;
        oVar.e = bVar;
        oVar.d = null;
        if (bVar != null) {
            oVar.waitForRoutesTimestamp = null;
            if (oVar.state != VerificationApi.VerificationState.VERIFYING_PHONE_NUMBER || oVar.e() == null) {
                return;
            }
            a(a(VerificationApi.VerificationState.WAITING_FOR_SMS_CODE, VerificationApi.VerificationSource.UNKNOWN, VerificationApi.FailReason.OK, this.g.e()));
        }
    }

    private void c(@Nullable Integer num) {
        if (num != null) {
            if (this.w != null) {
                FileLog.d("VerificationSession", "End waiting mobile_id time");
                this.r.cancelTimer(this.w);
            }
            this.w = this.r.createTimer(this.f.getDispatcher(), num.intValue(), () -> {
                o oVar = this.g;
                if (oVar.state == VerificationApi.VerificationState.WAITING_FOR_SMS_CODE && (oVar.e instanceof b.d)) {
                    FileLog.d("VerificationSession", "MobileID fallback timeout expired! In session %s clear route", this);
                    a(p.b.MOBILEID, () -> {
                        b((ru.mail.libverify.d.b) null);
                        C();
                        return null;
                    });
                }
            });
        }
    }

    private void d(@Nullable Integer num) {
        if (num != null) {
            if (this.x != null) {
                FileLog.d("VerificationSession", "End waiting sms time");
                this.r.cancelTimer(this.x);
            }
            this.x = this.r.createTimer(this.f.getDispatcher(), num.intValue(), () -> {
                o oVar = this.g;
                if (oVar.state == VerificationApi.VerificationState.WAITING_FOR_SMS_CODE && (oVar.e instanceof b.e)) {
                    FileLog.d("VerificationSession", "Fallback timeout expired! In session %s clear route", this);
                    b((ru.mail.libverify.d.b) null);
                    C();
                    B();
                }
            });
        }
    }

    private void b(Integer num) {
        if (num != null) {
            if (this.y != null) {
                FileLog.d("VerificationSession", "End waiting callui time");
                this.r.cancelTimer(this.y);
            }
            this.y = this.r.createTimer(this.f.getDispatcher(), num.intValue(), () -> {
                o oVar = this.g;
                if (oVar.state == VerificationApi.VerificationState.WAITING_FOR_SMS_CODE && (oVar.e instanceof b.C0004b)) {
                    FileLog.d("VerificationSession", "Fallback timeout expired! In session %s clear route", this);
                    a(p.b.CALLUI, () -> {
                        b((ru.mail.libverify.d.b) null);
                        C();
                        B();
                        return null;
                    });
                }
            });
        }
    }

    @NonNull
    private VerificationApi.VerificationStateDescriptor b(@NonNull ru.mail.libverify.j.n nVar) {
        FileLog.v("VerificationSession", "session with id = %s received VerifyApiResponse response = %s", this.g.id, nVar.toString());
        this.d.post(MessageBusUtils.createOneArg(BusMessageType.VERIFICATION_SESSION_FETCHER_INFO_RECEIVED, nVar.k()));
        this.g.a(nVar);
        if (nVar.d() == c.b.VERIFIED) {
            o oVar = this.g;
            if (oVar.smsCodeSource == VerificationApi.VerificationSource.UNKNOWN) {
                oVar.smsCodeSource = VerificationApi.VerificationSource.ALREADY_VERIFIED;
            }
        }
        if (this.g.e().f() != null) {
            o oVar2 = this.g;
            oVar2.callFragmentTemplate = oVar2.e().f();
        }
        boolean z = false;
        String localeUnixId = Utils.getLocaleUnixId(this.f.getConfig().getCurrentLocale());
        if (nVar.g() != null && nVar.g().length != 0) {
            try {
                a("verification_session_last_saved_call_template", JsonParser.toJson(nVar.g()), localeUnixId);
            } catch (JsonParseException e) {
                DebugUtils.safeThrow("VerificationSession", "failed to save call templates", e);
            }
            z = true;
        }
        if (!TextUtils.isEmpty(nVar.r())) {
            a("verification_session_last_saved_sms_template", nVar.r(), localeUnixId);
            z = true;
        }
        if (nVar.j() != null) {
            a("verification_session_last_saved_code_type", nVar.j().toString(), localeUnixId);
            z = true;
        }
        if (nVar.i() != 0) {
            a("verification_session_last_saved_code_length", Integer.toString(nVar.i()), localeUnixId);
            z = true;
        }
        if (z) {
            this.f.getSettings().commit();
        }
        C();
        ru.mail.libverify.j.k p = nVar.p();
        if (p != null) {
            try {
                this.d.post(MessageBusUtils.createOneArg(BusMessageType.SAFETY_NET_RESPONE_RECEIVED, this.f.getConfig().decryptServerMessage(p.b(), p.a())));
            } catch (DecryptionError e2) {
                FileLog.e("VerificationSession", "decrypt SafetyNet error", e2);
            }
        }
        int i = a.b[nVar.d().ordinal()];
        if (i != 1) {
            if (i == 2) {
                return TextUtils.isEmpty(nVar.t()) ? d() : a(VerificationApi.VerificationState.WAITING_FOR_SMS_CODE, this.g.smsCodeSource, VerificationApi.FailReason.OK, nVar);
            }
            this.d.post(MessageBusUtils.createOneArg(BusMessageType.VERIFY_API_HANDLE_REQUEST_FAILURE, nVar));
            return a((ru.mail.libverify.j.c) nVar);
        }
        Integer w = nVar.w();
        if (w != null && w.intValue() > 0) {
            a(w.intValue());
            return a(VerificationApi.VerificationState.VERIFYING_PHONE_NUMBER, VerificationApi.VerificationSource.UNKNOWN, VerificationApi.FailReason.OK, nVar);
        }
        return a(VerificationApi.VerificationState.WAITING_FOR_SMS_CODE, VerificationApi.VerificationSource.UNKNOWN, VerificationApi.FailReason.OK, nVar);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void b(@NonNull String str, boolean z) {
        if (TextUtils.isEmpty(str)) {
            FileLog.e("VerificationSession", "verifySmsText smsText can't be empty");
        } else {
            a(g.a(ru.mail.libverify.v.a.a().provideStartConfig().isSimpleCodeParser(), str, j()), str, z ? VerificationApi.VerificationSource.APPLICATION_LOCAL : VerificationApi.VerificationSource.SMS);
        }
    }

    private void a(@Nullable String str) {
        if (this.v != null) {
            if (str != null) {
                FileLog.d("VerificationSession", "End waiting route time because route %s income", str);
            } else {
                FileLog.d("VerificationSession", "End waiting route time");
            }
            this.r.cancelTimer(this.v);
        }
        this.g.waitForRoutesTimestamp = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0056  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0073  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x007d  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00c8  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00ce  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x010b  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0111  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0139  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0148  */
    @androidx.annotation.NonNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public ru.mail.libverify.api.VerificationApi.VerificationStateDescriptor a(ru.mail.libverify.api.VerificationApi.VerificationState r23, ru.mail.libverify.api.VerificationApi.VerificationSource r24, ru.mail.libverify.api.VerificationApi.FailReason r25, @androidx.annotation.Nullable ru.mail.libverify.j.n r26) {
        /*
            Method dump skipped, instructions count: 380
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: ru.mail.libverify.api.x.a(ru.mail.libverify.api.VerificationApi$VerificationState, ru.mail.libverify.api.VerificationApi$VerificationSource, ru.mail.libverify.api.VerificationApi$FailReason, ru.mail.libverify.j.n):ru.mail.libverify.api.VerificationApi$VerificationStateDescriptor");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public VerificationApi.VerificationStateDescriptor a(@NonNull ru.mail.libverify.j.c cVar) {
        switch (a.b[cVar.d().ordinal()]) {
            case 3:
            case 4:
            case 5:
                return new VerificationApi.VerificationStateDescriptor(VerificationApi.VerificationState.FAILED, TextUtils.isEmpty(cVar.a()) ? j.a() : VerificationApi.FailReason.GENERAL_ERROR.a(cVar.a()), this.g.h());
            case 6:
            case 7:
                return new VerificationApi.VerificationStateDescriptor(VerificationApi.VerificationState.FAILED, VerificationApi.FailReason.INCORRECT_PHONE_NUMBER.a(cVar.a()), this.g.h());
            case 8:
            case 9:
                return new VerificationApi.VerificationStateDescriptor(VerificationApi.VerificationState.FAILED, VerificationApi.FailReason.UNSUPPORTED_NUMBER.a(cVar.a()), this.g.h());
            case 10:
            case 11:
                VerificationApi.RateLimitType rateLimitType = VerificationApi.RateLimitType.UNKNOWN;
                if (cVar instanceof ru.mail.libverify.j.n) {
                    rateLimitType = VerificationApi.RateLimitType.VERIFY;
                } else if (cVar instanceof ru.mail.libverify.j.a) {
                    rateLimitType = VerificationApi.RateLimitType.ATTEMPT;
                }
                return new VerificationApi.VerificationStateDescriptor(VerificationApi.VerificationState.FAILED, VerificationApi.FailReason.RATELIMIT.a(cVar.a()), this.g.h(), rateLimitType);
            default:
                throw new IllegalArgumentException("Undefined response status");
        }
    }

    private String a(String str, String str2) {
        return this.f.getSettings().getValue(String.format(Locale.US, "%s_%s_%s", str, this.g.verificationService, str2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    @NonNull
    public VerificationApi.VerificationStateDescriptor a(@NonNull ru.mail.libverify.i.c cVar, @NonNull ru.mail.libverify.m.n nVar, @NonNull Future<ru.mail.libverify.j.n> future) {
        VerificationApi.VerificationStateDescriptor d2;
        try {
            ru.mail.libverify.j.n nVar2 = future.get();
            nVar.a();
            d2 = b(nVar2);
        } catch (InterruptedException e) {
            e = e;
            FileLog.e("VerificationSession", "apiMethodToNextState", e);
            d2 = this.d();
        } catch (CancellationException e2) {
            e = e2;
            FileLog.e("VerificationSession", "apiMethodToNextState", e);
            d2 = this.d();
        } catch (ExecutionException e3) {
            Throwable cause = e3.getCause();
            if (cause == null) {
                DebugUtils.safeThrow("VerificationSession", "apiMethodToNextState", e3);
                d2 = this.d();
            } else {
                FileLog.d("VerificationSession", "apiMethodToNextState", cause);
                this.d.post(MessageBusUtils.createMultipleArgs(BusMessageType.VERIFY_API_HANDLE_SERVER_FAILURE, cVar, cause));
                d2 = ((cause instanceof ServerException) || (cause instanceof IOException)) ? this.a(VerificationApi.VerificationState.SUSPENDED, VerificationApi.VerificationSource.UNKNOWN, j.b(), this.g.e()) : this.d();
            }
        } catch (Throwable th) {
            DebugUtils.safeThrow("VerificationSession", "apiMethodToNextState", th);
            d2 = this.d();
        }
        return d2;
    }

    public final void a(@NonNull ru.mail.libverify.j.j jVar) {
        p.b bVar;
        ru.mail.libverify.d.b bVar2;
        String f = jVar.f();
        p.b[] values = p.b.values();
        int length = values.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                bVar = null;
                break;
            }
            p.b bVar3 = values[i];
            bVar = bVar3;
            if (bVar3.value.equalsIgnoreCase(f)) {
                break;
            }
            i++;
        }
        p.b bVar4 = bVar;
        System.out.println("nextRoute: " + bVar);
        if (bVar4 != null) {
            int i2 = a.a[bVar.ordinal()];
            if (i2 == 1) {
                ru.mail.libverify.d.b bVar5 = this.g.d;
                if (bVar5 != null && (bVar5 instanceof b.a)) {
                    b.a aVar = (b.a) bVar5;
                    a(bVar.value);
                    if (this.y != null) {
                        FileLog.d("VerificationSession", "End waiting callui time");
                        this.r.cancelTimer(this.y);
                    }
                    if (this.x != null) {
                        FileLog.d("VerificationSession", "End waiting sms time");
                        this.r.cancelTimer(this.x);
                    }
                    if (this.w != null) {
                        FileLog.d("VerificationSession", "End waiting mobile_id time");
                        this.r.cancelTimer(this.w);
                    }
                    if (!a(Integer.valueOf(aVar.a()))) {
                        FileLog.d("VerificationSession", "Fallback timeout for last push expired! Push ignored!");
                        b((ru.mail.libverify.d.b) null);
                        InstanceConfig config = this.f.getConfig();
                        new ru.mail.libverify.i.l(config, ru.mail.libverify.i.m.a("callin_expired", config.getRegistrar().getRegistrationId())).executeAsync(this.f.getBackgroundWorker(), this.f.getDispatcher(), null);
                        return;
                    }
                    b(aVar);
                }
            } else if (i2 == 2) {
                ru.mail.libverify.d.b bVar6 = this.g.d;
                if (bVar6 != null && (bVar6 instanceof b.C0004b)) {
                    b.C0004b c0004b = (b.C0004b) bVar6;
                    a(bVar.value);
                    if (this.z != null) {
                        FileLog.d("VerificationSession", "End waiting callin time");
                        this.r.cancelTimer(this.z);
                    }
                    if (this.x != null) {
                        FileLog.d("VerificationSession", "End waiting sms time");
                        this.r.cancelTimer(this.x);
                    }
                    if (this.w != null) {
                        FileLog.d("VerificationSession", "End waiting mobile_id time");
                        this.r.cancelTimer(this.w);
                    }
                    b(c0004b);
                    b(Integer.valueOf(c0004b.c()));
                }
            } else if (i2 == 3 && (bVar2 = this.g.d) != null && (bVar2 instanceof b.e)) {
                b.e eVar = (b.e) bVar2;
                a(bVar.value);
                if (this.y != null) {
                    FileLog.d("VerificationSession", "End waiting callui time");
                    this.r.cancelTimer(this.y);
                }
                if (this.z != null) {
                    FileLog.d("VerificationSession", "End waiting callin time");
                    this.r.cancelTimer(this.z);
                }
                if (this.w != null) {
                    FileLog.d("VerificationSession", "End waiting mobile_id time");
                    this.r.cancelTimer(this.w);
                }
                b(eVar);
                d(eVar.a());
            }
        } else {
            this.g.d = null;
        }
        C();
        B();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void a(@NonNull ru.mail.libverify.j.b bVar) {
        if (Arrays.equals(this.g.callFragmentTemplate, bVar.a())) {
            FileLog.d("VerificationSession", "call info update %s discarded (%s)", bVar, "equal to current");
            return;
        }
        this.g.callFragmentTemplate = bVar.a();
        FileLog.d("VerificationSession", "call info updated %s", bVar);
        C();
        B();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void a(@NonNull ru.mail.libverify.j.n nVar) {
        boolean z;
        FileLog.v("VerificationSession", "session %s verify delayed response: %s", this.g.id, nVar);
        if (this.g.b()) {
            FileLog.e("VerificationSession", "failed to modify session state after completion");
            z = true;
        } else {
            z = false;
        }
        if (z) {
            return;
        }
        if (TextUtils.equals(nVar.q(), this.g.id) || TextUtils.equals(nVar.l(), Utils.stringToSHA256(this.g.id))) {
            a(b(nVar));
        } else {
            FileLog.e("VerificationSession", "session %s didn't matched delayed verify response", this.g.id);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void a(@NonNull ServerInfo.DoAttempt doAttempt) {
        if (this.u) {
            return;
        }
        this.u = true;
        a(doAttempt.getCode(), (String) null, doAttempt.getCodeSource() == null ? VerificationApi.VerificationSource.MOBILEID_OK : doAttempt.getCodeSource());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void a(@NonNull ServerInfo.MobileId mobileId, Boolean bool) {
        if (this.t) {
            return;
        }
        this.t = true;
        if (!bool.booleanValue()) {
            this.g.e = new b.d(mobileId.getUrl(), mobileId.getMaxRedirects(), mobileId.getFallbackTimeout());
            C();
            c(Integer.valueOf(mobileId.getFallbackTimeout()));
        }
        ru.mail.libverify.l.b bVar = this.s;
        if (bVar != null) {
            bVar.a(mobileId.getUrl(), this);
        }
        ApplicationModule.ApplicationStartConfig provideStartConfig = ru.mail.libverify.v.a.a().provideStartConfig();
        InstanceConfig config = this.f.getConfig();
        Network network = ConnectivityHelper.getNetwork().get();
        Object[] objArr = new Object[1];
        objArr[0] = network != null ? network.toString() : null;
        FileLog.d("ConnectivityHelper", "Result of cellular request: %s", objArr);
        ru.mail.libverify.i.h hVar = new ru.mail.libverify.i.h(config.getContext(), config.getNetwork(), provideStartConfig, mobileId.getUrl(), network);
        if (network == null) {
            this.g.mobileIdRoutes.add(new ru.mail.libverify.c.b(mobileId.getUrl(), -3));
            this.d.post(MessageBusUtils.createMultipleArgs(BusMessageType.VERIFICATION_SESSION_MOBILEID_RESULTS_RECEIVED, this.g.id, -3));
            return;
        }
        hVar.executeAsync(this.f.getBackgroundWorker(), this.f.getDispatcher(), new y(this, mobileId));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void a(@NonNull ru.mail.libverify.d.b bVar) {
        this.g.d = bVar;
        FileLog.d("VerificationSession", "unconfirmedRouteInfo info updated %s", bVar);
        if (bVar instanceof b.a) {
            Set<String> set = this.g.callInNumbers;
            String b2 = ((b.a) bVar).b();
            try {
                Phonenumber.PhoneNumber parse = this.f.getConfig().getPhoneNumberUtil().parse(b2, Locale.getDefault().getCountry());
                b2 = String.valueOf(parse.getCountryCode()) + parse.getNationalNumber();
            } catch (NumberParseException e) {
                FileLog.d("VerificationSession", "Failed to parse number", (Throwable) e);
            }
            set.add(b2);
        }
        if ((bVar instanceof b.C0004b) && (this.g.e instanceof b.C0004b)) {
            b(bVar);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(@Nullable VerificationApi.VerificationStateDescriptor verificationStateDescriptor) {
        if (verificationStateDescriptor == null) {
            return;
        }
        this.d.post(MessageBusUtils.createMultipleArgs(BusMessageType.VERIFICATION_SESSION_STATE_CHANGED, this.g.id, verificationStateDescriptor));
        if (this.g.state != verificationStateDescriptor.getState()) {
            this.g.a(verificationStateDescriptor.getState(), verificationStateDescriptor.getReason(), this.e.getLocalTime());
            if (!this.g.a()) {
                FileLog.v("VerificationSession", "Mark session = %s data as failed and run control loop", this.g.id);
                o oVar = this.g;
                oVar.state = VerificationApi.VerificationState.FAILED;
                oVar.reason = j.b();
            }
            if (!this.g.a(this.e.getLocalTime())) {
                FileLog.v("VerificationSession", "Stop control loop for session = %s", this.g.id);
                o oVar2 = this.g;
                oVar2.state = VerificationApi.VerificationState.FAILED;
                oVar2.reason = j.b();
            }
            long f = this.g.f();
            FileLog.v("VerificationSession", "Schedule control loop for session = %s delay %d", this.g.id, Long.valueOf(f));
            this.f.getDispatcher().postDelayed(this.p, f);
        }
    }

    private void a(@NonNull String str, @NonNull String str2, @NonNull String str3) {
        this.f.getSettings().putValue(String.format(Locale.US, "%s_%s_%s", str, this.g.verificationService, str3), str2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void a(VerificationApi.IvrStateListener ivrStateListener) {
        FileLog.v("VerificationSession", "session %s ivr call requested", this.g.id);
        o oVar = this.g;
        if (oVar.state != VerificationApi.VerificationState.WAITING_FOR_SMS_CODE) {
            FileLog.e("VerificationSession", "session %s wrong state for ivr call detected", oVar.id);
            if (ivrStateListener != null) {
                ivrStateListener.onRequestExecuted(j.a());
            }
        } else if (!this.f.getConfig().getNetwork().hasNetwork()) {
            FileLog.d("VerificationSession", "ivr request start disallowed");
            if (ivrStateListener != null) {
                ivrStateListener.onRequestExecuted(j.c());
            }
        } else {
            if (this.n != null) {
                FileLog.v("VerificationSession", "cancel ivr request");
                this.n.cancel(true);
                this.n = null;
            }
            FileLog.d("VerificationSession", "start ivr request");
            p.b[] bVarArr = {p.b.IVR};
            InstanceConfig config = this.f.getConfig();
            o oVar2 = this.g;
            String str = oVar2.id;
            String str2 = oVar2.verificationService;
            String str3 = oVar2.userProvidedPhoneNumber;
            String str4 = oVar2.userId;
            this.c.canUseSmsRetriever();
            p.a e = this.g.g().e();
            String str5 = this.g.srcApplication;
            String b2 = this.h.b();
            ru.mail.libverify.i.q qVar = this.g.verifySessionSettings;
            ru.mail.libverify.i.p pVar = new ru.mail.libverify.i.p(config, str, str2, str3, str4, bVarArr, e, str5, false, b2, qVar == null ? null : qVar.d(), this.A, false);
            this.n = pVar.executeAsync(this.f.getBackgroundWorker(), this.f.getDispatcher(), new d0(this, pVar, ivrStateListener));
        }
    }

    private void a(p.b bVar, Function0<Void> function0) {
        p.b[] h;
        Intrinsics.checkNotNullParameter(this, "<this>");
        ru.mail.libverify.j.n e = c().e();
        if (((e == null || (h = e.h()) == null) ? null : (p.b) ArraysKt.last(h)) == bVar) {
            a(new VerificationApi.VerificationStateDescriptor(VerificationApi.VerificationState.FAILED, VerificationApi.FailReason.NO_MORE_ROUTES, this.g.h()));
        } else {
            function0.invoke();
        }
    }

    private boolean a(@NonNull Integer num) {
        String str = this.z;
        if ((str == null || this.r.hasTimerWithId(str)) ? false : true) {
            return false;
        }
        if (this.z != null) {
            FileLog.d("VerificationSession", "End waiting callin time");
            this.r.cancelTimer(this.z);
        }
        this.z = this.r.createTimer(this.f.getDispatcher(), num.intValue(), () -> {
            o oVar = this.g;
            if (oVar.state == VerificationApi.VerificationState.WAITING_FOR_SMS_CODE && (oVar.e instanceof b.a)) {
                FileLog.d("VerificationSession", "Fallback timeout expired! In session %s clear route", this);
                a(p.b.CALLIN, () -> {
                    b((ru.mail.libverify.d.b) null);
                    C();
                    B();
                    return null;
                });
            }
        });
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void a(int i) {
        a((String) null);
        o oVar = this.g;
        this.e.getLocalTime();
        long j = i;
        oVar.waitForRoutesTimestamp = Long.valueOf(this + j);
        FileLog.d("VerificationSession", "Start waiting route %s ms", Integer.valueOf(i));
        this.v = this.r.createTimer(this.f.getDispatcher(), j, () -> {
            FileLog.d("VerificationSession", "End waiting route time because timeout is expired");
            o oVar2 = this.g;
            oVar2.waitForRoutesTimestamp = null;
            if (oVar2.state != VerificationApi.VerificationState.VERIFYING_PHONE_NUMBER || oVar2.e() == null) {
                return;
            }
            a(a(VerificationApi.VerificationState.WAITING_FOR_SMS_CODE, VerificationApi.VerificationSource.UNKNOWN, VerificationApi.FailReason.OK, this.g.e()));
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(@NonNull String str, boolean z) {
        boolean z2;
        FileLog.v("VerificationSession", "try to verify phone %s", str);
        String[] strArr = null;
        ru.mail.libverify.d.b bVar = this.g.e;
        if (!(bVar instanceof b.C0004b) || TextUtils.isEmpty(((b.C0004b) bVar).d())) {
            String[] strArr2 = this.g.callFragmentTemplate;
            if (strArr2 != null && strArr2.length != 0) {
                strArr = strArr2;
            }
        } else {
            strArr = r0;
            String[] strArr3 = {((b.C0004b) this.g.e).d()};
        }
        if (!TextUtils.isEmpty(str) && strArr != null && strArr.length != 0) {
            int length = strArr.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    z2 = false;
                    break;
                } else if (str.contains(strArr[i])) {
                    z2 = true;
                    break;
                } else {
                    i++;
                }
            }
        } else {
            FileLog.v("CodeParser", String.format("containsFragment return false. messageText: %s parseData: %s", str, strArr));
            z2 = false;
        }
        if (z2) {
            a(str, str, VerificationApi.VerificationSource.CALL);
            return true;
        }
        String a2 = g.a(ru.mail.libverify.v.a.a().provideStartConfig().isSimpleCodeParser(), str, b());
        boolean z3 = !TextUtils.isEmpty(a2);
        if (z3 || z) {
            a(a2, str, VerificationApi.VerificationSource.CALL);
        }
        return z3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(@Nullable String str, @Nullable String str2, VerificationApi.VerificationSource verificationSource) {
        boolean z;
        if (TextUtils.isEmpty(str)) {
            FileLog.d("VerificationSession", "empty sms code received");
            if (TextUtils.isEmpty(str2) || this.g.rawSmsTexts.contains(str2)) {
                return;
            }
            o oVar = this.g;
            oVar.smsCodeSource = verificationSource;
            oVar.rawSmsTexts.add(str2);
            FileLog.d("VerificationSession", "save raw sms text %s for further processing", Arrays.toString(this.g.rawSmsTexts.toArray()));
            C();
        } else if (TextUtils.equals(this.g.smsCode, str)) {
            FileLog.d("VerificationSession", "provided sms code: %s is equal to the last stored one", str);
        } else {
            if (this.g.b()) {
                FileLog.e("VerificationSession", "failed to modify session state after completion");
                z = true;
            } else {
                z = false;
            }
            if (z) {
                return;
            }
            FileLog.v("VerificationSession", "received code: %s", str);
            o oVar2 = this.g;
            oVar2.reason = VerificationApi.FailReason.OK;
            oVar2.smsCode = str;
            oVar2.smsCodeSource = verificationSource;
            oVar2.rawSmsTexts.clear();
            C();
            if (this.m != null) {
                FileLog.v("VerificationSession", "cancel main request");
                this.m.cancel(true);
                this.m = null;
            }
            B();
        }
    }
}
