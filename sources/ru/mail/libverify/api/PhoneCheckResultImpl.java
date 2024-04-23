package ru.mail.libverify.api;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import ru.mail.libverify.api.VerificationApi;
import ru.mail.libverify.j.c;
import ru.mail.libverify.j.i;
import ru.mail.verify.core.storage.BroadcastManager;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/PhoneCheckResultImpl.class */
public final class PhoneCheckResultImpl implements VerificationApi.PhoneCheckResult {
    private static PhoneCheckResultImpl f;
    private static PhoneCheckResultImpl g;
    private static PhoneCheckResultImpl h;
    private static PhoneCheckResultImpl i;
    private final VerificationApi.FailReason a;
    private final VerificationApi.PhoneCheckResult.State b;
    private final boolean c;
    private final String[] d;
    private final b e;

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/PhoneCheckResultImpl$a.class */
    static /* synthetic */ class a {
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
        }
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/PhoneCheckResultImpl$b.class */
    private static class b implements VerificationApi.PhoneCheckResult.ExtendedInfo {
        private final boolean a;
        private final boolean b;
        private final Integer[] c;
        private final boolean d;
        private final Integer e;
        private final String f;
        private final String g;

        private b(@NonNull i.a aVar) {
            this.a = aVar.e();
            this.b = aVar.d();
            Integer[] a = a(aVar.c());
            this.c = a;
            this.e = a(aVar.c(), a, false);
            this.d = aVar.f();
            this.f = aVar.a();
            this.g = aVar.b();
        }

        private static Integer[] a(Integer[] numArr) {
            if (numArr == null || numArr.length == 0) {
                return null;
            }
            return new Integer[numArr.length];
        }

        @Override // ru.mail.libverify.api.VerificationApi.PhoneCheckResult.ExtendedInfo
        public final boolean isMobile() {
            return this.a;
        }

        @Override // ru.mail.libverify.api.VerificationApi.PhoneCheckResult.ExtendedInfo
        public final boolean isFixedLine() {
            return this.b;
        }

        @Override // ru.mail.libverify.api.VerificationApi.PhoneCheckResult.ExtendedInfo
        public final Integer getRemainingLength() {
            return this.e;
        }

        @Override // ru.mail.libverify.api.VerificationApi.PhoneCheckResult.ExtendedInfo
        public final String getModifiedPhoneNumber() {
            return this.f;
        }

        @Override // ru.mail.libverify.api.VerificationApi.PhoneCheckResult.ExtendedInfo
        public final String getModifiedPrefix() {
            return this.g;
        }

        @NonNull
        public final String toString() {
            return super.toString();
        }

        private b(boolean z, boolean z2, boolean z3, Integer num, Integer[] numArr) {
            this.a = z;
            this.b = z2;
            this.e = num;
            this.c = numArr;
            this.d = z3;
            this.f = null;
            this.g = null;
        }

        private static Integer a(Integer[] numArr, Integer[] numArr2, boolean z) {
            if (numArr == null || numArr2 == null || numArr.length == 0 || numArr.length != numArr2.length) {
                return null;
            }
            int i = Integer.MAX_VALUE;
            Integer num = null;
            for (int i2 = 0; i2 < numArr.length; i2++) {
                Integer valueOf = Integer.valueOf(z ? numArr[i2].intValue() - 1 : numArr[i2].intValue());
                numArr2[i2] = valueOf;
                int abs = Math.abs(valueOf.intValue());
                if (abs < i) {
                    num = numArr2[i2];
                    i = abs;
                }
            }
            return num;
        }
    }

    private PhoneCheckResultImpl(VerificationApi.FailReason failReason, String[] strArr, VerificationApi.PhoneCheckResult.ExtendedInfo extendedInfo, VerificationApi.PhoneCheckResult.State state, boolean z) {
        this.a = failReason;
        this.b = state;
        this.c = z;
        this.d = strArr;
        this.e = (b) extendedInfo;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static VerificationApi.PhoneCheckResult c() {
        if (g == null) {
            g = new PhoneCheckResultImpl(j.a(), null, null, VerificationApi.PhoneCheckResult.State.INVALID, false);
        }
        return g;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static VerificationApi.PhoneCheckResult b() {
        if (h == null) {
            h = new PhoneCheckResultImpl(j.b(), null, null, VerificationApi.PhoneCheckResult.State.INVALID, false);
        }
        return h;
    }

    public static VerificationApi.PhoneCheckResult getIncorrectPhoneResult() {
        if (i == null) {
            i = new PhoneCheckResultImpl(VerificationApi.FailReason.INCORRECT_PHONE_NUMBER, null, null, VerificationApi.PhoneCheckResult.State.INVALID, true);
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static PhoneCheckResultImpl a(@NonNull VerificationApi.PhoneCheckResult phoneCheckResult) {
        b bVar;
        b bVar2;
        VerificationApi.PhoneCheckResult.ExtendedInfo extendedInfo = phoneCheckResult.getExtendedInfo();
        if (phoneCheckResult.isValid()) {
            return new PhoneCheckResultImpl(VerificationApi.FailReason.OK, null, extendedInfo, phoneCheckResult.getState(), true);
        }
        if (extendedInfo != null) {
            boolean z = extendedInfo instanceof b;
            if (z) {
                b bVar3 = (b) extendedInfo;
                Integer[] a2 = b.a(bVar3.c);
                Integer a3 = b.a(bVar3.c, a2, true);
                bVar = new b(bVar3.a, bVar3.b, (a3 == null || a3.intValue() != 0) && bVar3.d, a3, a2);
            } else {
                bVar = null;
            }
            Integer num = bVar.e;
            VerificationApi.PhoneCheckResult.State state = num != null && num.intValue() == 0 && (extendedInfo.isMobile() || extendedInfo.isFixedLine()) ? VerificationApi.PhoneCheckResult.State.VALID : phoneCheckResult.getState();
            VerificationApi.FailReason failReason = VerificationApi.FailReason.OK;
            if (z) {
                b bVar4 = (b) extendedInfo;
                Integer[] a4 = b.a(bVar4.c);
                Integer a5 = b.a(bVar4.c, a4, true);
                bVar2 = new b(bVar4.a, bVar4.b, (a5 == null || a5.intValue() != 0) && bVar4.d, a5, a4);
            } else {
                bVar2 = null;
            }
            return new PhoneCheckResultImpl(failReason, null, bVar2, state, true);
        }
        return null;
    }

    @Override // ru.mail.libverify.api.VerificationApi.PhoneCheckResult
    public VerificationApi.FailReason getReason() {
        return this.a;
    }

    @Override // ru.mail.libverify.api.VerificationApi.PhoneCheckResult
    public VerificationApi.PhoneCheckResult.State getState() {
        return this.b;
    }

    @Override // ru.mail.libverify.api.VerificationApi.PhoneCheckResult
    public boolean isValid() {
        return this.b == VerificationApi.PhoneCheckResult.State.VALID;
    }

    @Override // ru.mail.libverify.api.VerificationApi.PhoneCheckResult
    public boolean isUnknown() {
        return this.b == VerificationApi.PhoneCheckResult.State.UNKNOWN;
    }

    @Override // ru.mail.libverify.api.VerificationApi.PhoneCheckResult
    public boolean isInvalid() {
        return this.b == VerificationApi.PhoneCheckResult.State.INVALID;
    }

    @Override // ru.mail.libverify.api.VerificationApi.PhoneCheckResult
    public boolean isWarning() {
        b bVar;
        VerificationApi.FailReason failReason;
        return this.b == VerificationApi.PhoneCheckResult.State.INVALID && ((bVar = this.e) == null || bVar.d || (failReason = this.a) == VerificationApi.FailReason.INCORRECT_PHONE_NUMBER || failReason == VerificationApi.FailReason.UNSUPPORTED_NUMBER);
    }

    @Override // ru.mail.libverify.api.VerificationApi.PhoneCheckResult
    public String[] getPrintableText() {
        String[] strArr = this.d;
        String[] strArr2 = strArr;
        if (strArr == null || strArr2.length == 0) {
            strArr2 = null;
        }
        return strArr2;
    }

    @Override // ru.mail.libverify.api.VerificationApi.PhoneCheckResult
    @Nullable
    public VerificationApi.PhoneCheckResult.ExtendedInfo getExtendedInfo() {
        return this.e;
    }

    @Override // ru.mail.libverify.api.VerificationApi.PhoneCheckResult
    public boolean isApproximate() {
        return this.c;
    }

    public String toString() {
        return "PhoneCheckResult{isApproximate=" + this.c + ", state=" + this.b + ", reason=" + this.a + ", extendedInfo=" + this.e + '}';
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static PhoneCheckResultImpl a(@NonNull ru.mail.libverify.j.i iVar) {
        VerificationApi.PhoneCheckResult.State state;
        switch (a.a[iVar.d().ordinal()]) {
            case BroadcastManager.FLAG_NETWORK_RECEIVER /* 1 */:
                state = VerificationApi.PhoneCheckResult.State.VALID;
                break;
            case 2:
            case 3:
            case 4:
            case 5:
                state = VerificationApi.PhoneCheckResult.State.INVALID;
                break;
            case 6:
                state = VerificationApi.PhoneCheckResult.State.UNKNOWN;
                break;
            default:
                throw new IllegalArgumentException();
        }
        VerificationApi.FailReason failReason = VerificationApi.FailReason.OK;
        String[] e = iVar.e();
        i.a f2 = iVar.f();
        return new PhoneCheckResultImpl(failReason, e, f2 == null ? null : new b(f2), state, false);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static VerificationApi.PhoneCheckResult a() {
        if (f == null) {
            f = new PhoneCheckResultImpl(j.a(), null, null, VerificationApi.PhoneCheckResult.State.INVALID, false);
        }
        return f;
    }
}
