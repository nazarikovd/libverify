package ru.mail.libverify.api;

import android.text.TextUtils;
import android.util.LruCache;
import androidx.annotation.NonNull;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.regex.Pattern;
import ru.mail.libverify.api.VerificationApi;
import ru.mail.libverify.api.q;
import ru.mail.libverify.j.c;
import ru.mail.verify.core.storage.BroadcastManager;
import ru.mail.verify.core.utils.DebugUtils;
import ru.mail.verify.core.utils.FileLog;
import ru.mail.verify.core.utils.ServerException;
import ru.mail.verify.core.utils.components.BusMessageType;
import ru.mail.verify.core.utils.components.MessageBus;
import ru.mail.verify.core.utils.components.MessageBusUtils;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/l.class */
public final class l {
    private static Pattern e;
    private final HashMap<String, c> a = new HashMap<>();
    private final LruCache<String, VerificationApi.PhoneCheckResult> b = new LruCache<>(50);
    private final CommonContext c;
    private final MessageBus d;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/l$a.class */
    public static /* synthetic */ class a {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[c.b.values().length];
            a = iArr;
            try {
                iArr[c.b.OK.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[c.b.UNSUPPORTED_NUMBER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[c.b.INCORRECT_PHONE_NUMBER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                a[c.b.PHONE_NUMBER_IN_BLACK_LIST.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                a[c.b.PHONE_NUMBER_TYPE_NOT_ALLOWED.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                a[c.b.NOT_ENOUGH_DATA.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                a[c.b.RATELIMIT.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/l$b.class */
    public static class b {
        static final HashSet<String> h = new HashSet<>();
        final String a;
        final String b;
        final HashMap<VerificationApi.PhoneCheckListener, String> c;
        final String d;
        final String e;
        final boolean f;
        final String g;

        b(@NonNull String str, @NonNull String str2, @NonNull String str3, boolean z, @NonNull VerificationApi.PhoneCheckListener phoneCheckListener) {
            HashMap<VerificationApi.PhoneCheckListener, String> hashMap = new HashMap<>();
            this.c = hashMap;
            this.a = str;
            this.b = str2;
            this.f = z;
            hashMap.put(phoneCheckListener, str3);
            this.d = str3;
            String a = l.a(str3);
            this.e = a;
            this.g = l.a(str2, a);
            h.add(str);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || b.class != obj.getClass()) {
                return false;
            }
            return this.g.equals(((b) obj).g);
        }

        public final int hashCode() {
            return this.g.hashCode();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/l$c.class */
    public static class c {
        final Future a;
        final b b;

        c(@NonNull b bVar, @NonNull Future future) {
            this.b = bVar;
            this.a = future;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public l(@NonNull CommonContext commonContext) {
        this.c = commonContext;
        this.d = ((q.f) commonContext).getBus();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void a(@NonNull String str, @NonNull String str2, @NonNull String str3, boolean z, VerificationApi.PhoneCheckListener phoneCheckListener) {
        FileLog.v("PhoneNumberChecker", "Check %s %s %s", str, str2, str3);
        if (TextUtils.isEmpty(str3) || TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            VerificationApi.PhoneCheckResult a2 = PhoneCheckResultImpl.a();
            if (phoneCheckListener != null) {
                if (str3 == null) {
                    str3 = "";
                }
                phoneCheckListener.onCompleted(str3, a2);
                return;
            }
            return;
        }
        if (!b.h.contains(str)) {
            this.d.post(MessageBusUtils.createOneArg(BusMessageType.PHONE_NUMBER_CHECKER_NEW_CHECK_STARTED, (Object) null));
        }
        b bVar = new b(str, str2, str3, z, phoneCheckListener);
        String str4 = bVar.e;
        int length = (TextUtils.isEmpty(str4) || str4.charAt(0) != '+') ? str4.length() : str4.length() - 1;
        if (length < 4) {
            a(bVar, PhoneCheckResultImpl.getIncorrectPhoneResult());
        } else if (length > 20) {
            a(bVar, PhoneCheckResultImpl.getIncorrectPhoneResult());
        } else {
            VerificationApi.PhoneCheckResult phoneCheckResult = this.b.get(bVar.g);
            if (phoneCheckResult == null || phoneCheckResult.getReason() != VerificationApi.FailReason.OK) {
                FileLog.v("PhoneNumberChecker", "Check %s not found in the cache", str3);
                a(bVar, a(str2, bVar));
                return;
            }
            FileLog.v("PhoneNumberChecker", "Check %s found in the cache", str3);
            a(bVar, phoneCheckResult);
            if (phoneCheckResult.isApproximate()) {
                a(bVar, true);
            }
        }
    }

    private boolean a(@NonNull String str, b bVar) {
        String str2 = bVar.e;
        boolean z = false;
        while (true) {
            if (str2.length() <= 5) {
                break;
            }
            String str3 = str2;
            str2 = str3.substring(0, str3.length() - 1);
            VerificationApi.PhoneCheckResult phoneCheckResult = this.b.get(a(str, str2));
            if (phoneCheckResult != null) {
                PhoneCheckResultImpl a2 = PhoneCheckResultImpl.a(phoneCheckResult);
                if (a2 != null) {
                    FileLog.v("PhoneNumberChecker", "Check %s found reduced number in the cache", str2);
                    this.b.put(bVar.g, a2);
                    a(bVar, a2);
                    z = true;
                }
            }
        }
        return z;
    }

    private static String a(@NonNull String str, @NonNull String str2) {
        return String.format(Locale.US, "%s_%s", str2, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public VerificationApi.PhoneCheckResult a(@NonNull ru.mail.libverify.i.i iVar, Future<ru.mail.libverify.j.i> future, @NonNull b bVar) {
        PhoneCheckResultImpl a2;
        VerificationApi.PhoneCheckResult phoneCheckResult;
        try {
            ru.mail.libverify.j.i iVar2 = future.get();
            switch (a.a[iVar2.d().ordinal()]) {
                case BroadcastManager.FLAG_NETWORK_RECEIVER /* 1 */:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                    PhoneCheckResultImpl a3 = PhoneCheckResultImpl.a(iVar2);
                    a2 = a3;
                    this.b.put(bVar.g, a2);
                    VerificationApi.PhoneCheckResult.ExtendedInfo extendedInfo = a3.getExtendedInfo();
                    if (extendedInfo != null && !TextUtils.isEmpty(extendedInfo.getModifiedPhoneNumber())) {
                        String a4 = a(bVar.b, a(extendedInfo.getModifiedPhoneNumber()));
                        if (!TextUtils.equals(a4, bVar.g)) {
                            this.b.put(a4, a2);
                            Object[] objArr = new Object[2];
                            objArr[0] = extendedInfo.getModifiedPhoneNumber();
                            objArr[1] = bVar.d;
                            FileLog.v("PhoneNumberChecker", "Modified phone %s from check %s added to cache", objArr);
                            break;
                        }
                    }
                    break;
                default:
                    this.d.post(MessageBusUtils.createMultipleArgs(BusMessageType.VERIFY_API_HANDLE_REQUEST_FAILURE, iVar2));
                case 7:
                    a2 = PhoneCheckResultImpl.a();
                    break;
            }
            Object[] objArr2 = new Object[1];
            objArr2[0] = bVar.d;
            FileLog.v("PhoneNumberChecker", "Check %s completed", objArr2);
        } catch (ExecutionException e2) {
            Throwable cause = e2.getCause();
            if (cause == null) {
                phoneCheckResult = PhoneCheckResultImpl.a();
            } else {
                VerificationApi.PhoneCheckResult b2 = cause instanceof ServerException ? PhoneCheckResultImpl.b() : cause instanceof IOException ? PhoneCheckResultImpl.c() : PhoneCheckResultImpl.a();
                this.d.post(MessageBusUtils.createMultipleArgs(BusMessageType.VERIFY_API_HANDLE_SERVER_FAILURE, iVar, cause));
                phoneCheckResult = b2;
            }
            FileLog.e("PhoneNumberChecker", e2, "Check %s failed", bVar.d);
            a2 = phoneCheckResult;
        } catch (Throwable th) {
            a2 = PhoneCheckResultImpl.a();
            FileLog.e("PhoneNumberChecker", th, "Check %s failed", bVar.d);
            DebugUtils.safeThrow("PhoneNumberChecker", th, "Check failed", new Object[0]);
        }
        return a2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(@NonNull b bVar, VerificationApi.PhoneCheckResult phoneCheckResult) {
        if (bVar.c.isEmpty()) {
            return;
        }
        for (Map.Entry<VerificationApi.PhoneCheckListener, String> entry : bVar.c.entrySet()) {
            entry.getKey().onCompleted(entry.getValue(), phoneCheckResult);
        }
    }

    private void a(@NonNull b bVar, boolean z) {
        if (!this.c.getConfig().getNetwork().hasNetwork()) {
            if (z) {
                return;
            }
            a(bVar, PhoneCheckResultImpl.c());
            return;
        }
        String str = bVar.b;
        String format = String.format(Locale.US, "%s_%s", bVar.a, str);
        c cVar = this.a.get(format);
        if (cVar != null) {
            if (!cVar.a.isCancelled() && cVar.b.equals(bVar)) {
                FileLog.v("PhoneNumberChecker", "Check %s requests joined", bVar.d);
                cVar.b.c.putAll(bVar.c);
                return;
            }
            FileLog.v("PhoneNumberChecker", "Check %s cancel previous request", bVar.d);
            cVar.a.cancel(true);
            cVar.b.c.clear();
            this.a.remove(format);
        }
        ru.mail.libverify.i.i iVar = new ru.mail.libverify.i.i(this.c.getConfig(), bVar.e, bVar.b, bVar.a, bVar.f);
        FileLog.d("PhoneNumberChecker", "Check %s start request", bVar.d);
        this.a.put(format, new c(bVar, iVar.executeAsync(this.c.getBackgroundWorker(), this.c.getDispatcher(), new k(this, iVar, bVar, format))));
    }

    private static String a(@NonNull String str) {
        if (e == null) {
            e = Pattern.compile("[^\\+0-9]");
        }
        return e.matcher(str).replaceAll("");
    }
}
