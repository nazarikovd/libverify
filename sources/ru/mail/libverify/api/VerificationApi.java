package ru.mail.libverify.api;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;
import androidx.annotation.WorkerThread;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import ru.mail.libverify.d.b;
import ru.mail.libverify.i.p;
import ru.mail.libverify.j.c;
import ru.mail.libverify.j.n;
import ru.mail.verify.core.utils.Gsonable;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/VerificationApi.class */
public interface VerificationApi {

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/VerificationApi$AccountCheckListener.class */
    public interface AccountCheckListener {
        @WorkerThread
        void onComplete(AccountCheckResult accountCheckResult);
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/VerificationApi$AccountCheckResult.class */
    public enum AccountCheckResult {
        OK,
        NO_SMS_PERMISSION,
        NO_SMS_FOUND,
        NO_SMS_FOUND_HAS_SOURCE_MATCH,
        NO_SMS_FOUND_HAS_CODE,
        NO_SMS_INFO,
        EMPTY_ACCOUNT_DATA,
        GENERAL_ERROR
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/VerificationApi$CallInDescriptor.class */
    public static class CallInDescriptor {
        @NonNull
        private final String a;
        private final int b;
        private final int c;
        private final boolean d;
        private final boolean e;

        public CallInDescriptor(@NonNull String phoneNumber, int timeout, int fallbackTimeout, boolean isDisableDirectCall, boolean isIvr) {
            this.a = phoneNumber;
            this.b = timeout;
            this.c = fallbackTimeout;
            this.d = isDisableDirectCall;
            this.e = isIvr;
        }

        @NonNull
        public String getPhoneNumber() {
            return this.a;
        }

        public int getNumberTimeout() {
            return this.b;
        }

        public int getTotalTimeout() {
            return this.c;
        }

        public boolean isDisableDirectCall() {
            return this.d;
        }

        public boolean isIvr() {
            return this.e;
        }
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/VerificationApi$CallUIDescriptor.class */
    public static class CallUIDescriptor {
        @Nullable
        private final String a;
        @Nullable
        private final String b;
        @Nullable
        private final String c;
        private final int d;

        public CallUIDescriptor(@Nullable String preferredDescription, @Nullable String preferredDescriptionOptional, @Nullable String callUiPhoneFragmentStart, int codeLength) {
            this.a = callUiPhoneFragmentStart;
            this.b = preferredDescription;
            this.c = preferredDescriptionOptional;
            this.d = codeLength;
        }

        @Nullable
        public String getCallUiPhoneFragmentStart() {
            return this.a;
        }

        @Nullable
        public String getPreferredDescription() {
            return this.b;
        }

        @Nullable
        public String getPreferredOptionalDescription() {
            return this.c;
        }

        public int getCodeLength() {
            return this.d;
        }
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/VerificationApi$CancelReason.class */
    public enum CancelReason {
        TOKEN_SWAP_GENERAL_ERROR,
        TOKEN_SWAP_NETWORK_ERROR,
        CANCELLED_BY_USER,
        PHONE_EDIT_BY_USER,
        GENERAL_ERROR,
        OK
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/VerificationApi$FailReason.class */
    public enum FailReason {
        OK,
        GENERAL_ERROR,
        UNSUPPORTED_NUMBER,
        INCORRECT_PHONE_NUMBER,
        INCORRECT_SMS_CODE,
        RATELIMIT,
        NETWORK_ERROR,
        NO_NETWORK,
        NO_MORE_ROUTES;
        
        private String description;

        public String getDescription() {
            return this.description;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public final FailReason a(String str) {
            this.description = str;
            return this;
        }
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/VerificationApi$GcmTokenListener.class */
    public interface GcmTokenListener {
        @WorkerThread
        void onReceived(@Nullable String str);
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/VerificationApi$IvrStateListener.class */
    public interface IvrStateListener {
        @WorkerThread
        void onRequestExecuted(FailReason failReason);
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/VerificationApi$MobileIdDescriptor.class */
    public static class MobileIdDescriptor {
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/VerificationApi$PhoneAccountSearchItem.class */
    public static class PhoneAccountSearchItem {
        public final String phone;
        public final String source;

        /* JADX INFO: Access modifiers changed from: package-private */
        public PhoneAccountSearchItem(String str, String str2) {
            this.phone = str;
            this.source = str2;
        }
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/VerificationApi$PhoneAccountSearchListener.class */
    public interface PhoneAccountSearchListener {
        @WorkerThread
        void onComplete(@NonNull List<PhoneAccountSearchItem> list);
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/VerificationApi$PhoneCheckListener.class */
    public interface PhoneCheckListener {
        @WorkerThread
        void onCompleted(@NonNull String str, @NonNull PhoneCheckResult phoneCheckResult);
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/VerificationApi$PhoneCheckResult.class */
    public interface PhoneCheckResult {

        /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/VerificationApi$PhoneCheckResult$ExtendedInfo.class */
        public interface ExtendedInfo {
            boolean isMobile();

            boolean isFixedLine();

            Integer getRemainingLength();

            String getModifiedPhoneNumber();

            String getModifiedPrefix();
        }

        /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/VerificationApi$PhoneCheckResult$State.class */
        public enum State {
            VALID,
            INVALID,
            UNKNOWN
        }

        FailReason getReason();

        State getState();

        boolean isValid();

        boolean isUnknown();

        boolean isInvalid();

        boolean isWarning();

        String[] getPrintableText();

        @Nullable
        ExtendedInfo getExtendedInfo();

        boolean isApproximate();
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/VerificationApi$PhoneNumberCheckSession.class */
    public static class PhoneNumberCheckSession {
        private static final Random c = new Random();
        private final String a;
        private final VerificationApi b;

        public PhoneNumberCheckSession(@NonNull VerificationApi verificationApi) {
            this.b = verificationApi;
            this.a = Integer.toString(c.nextInt());
        }

        public String getId() {
            return this.a;
        }

        public void checkPhoneNumber(@NonNull String service, @NonNull String phoneNumber, boolean extendedInfo, @NonNull PhoneCheckListener listener) {
            this.b.checkPhoneNumber(this.a, service, phoneNumber, extendedInfo, listener);
        }

        public PhoneNumberCheckSession(@NonNull VerificationApi verificationApi, @NonNull String id) {
            this.b = verificationApi;
            this.a = id;
        }
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/VerificationApi$RateLimitType.class */
    public enum RateLimitType {
        UNKNOWN,
        VERIFY,
        ATTEMPT
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/VerificationApi$SmsCodeNotificationListener.class */
    public interface SmsCodeNotificationListener {
        @WorkerThread
        void onNotification(@NonNull String str);
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/VerificationApi$SmsDialogChangedListener.class */
    public interface SmsDialogChangedListener {
        @WorkerThread
        void onChanged(@Nullable SmsDialogItem smsDialogItem);
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/VerificationApi$SmsDialogItem.class */
    public interface SmsDialogItem extends Comparable<SmsDialogItem> {
        long getId();

        String getFrom();

        long getLastTimestamp();

        String getLastText();

        boolean hasUnread();
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/VerificationApi$SmsDialogsListener.class */
    public interface SmsDialogsListener {
        @WorkerThread
        void onCompleted(@NonNull List<SmsDialogItem> list);

        @WorkerThread
        void onError();
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/VerificationApi$SmsItem.class */
    public interface SmsItem {
        long getId();

        String getText();

        long getTimestamp();

        String getFrom();
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/VerificationApi$SmsListener.class */
    public interface SmsListener {
        @WorkerThread
        void onCompleted(@NonNull List<SmsItem> list);

        @WorkerThread
        void onError();
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/VerificationApi$VerificationSource.class */
    public enum VerificationSource implements Gsonable {
        UNKNOWN,
        SMS,
        CALL,
        SMS_RETRIEVER,
        USER_INPUT,
        ALREADY_VERIFIED,
        APPLICATION_LOCAL,
        APPLICATION_EXTERNAL,
        MOBILEID_OK,
        CALLIN
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/VerificationApi$VerificationState.class */
    public enum VerificationState {
        INITIAL,
        VERIFYING_PHONE_NUMBER,
        WAITING_FOR_SMS_CODE,
        VERIFYING_SMS_CODE,
        SUCCEEDED,
        FAILED,
        SUSPENDED,
        FINAL
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/VerificationApi$VerificationStateChangedListener.class */
    public interface VerificationStateChangedListener {
        @WorkerThread
        void onStateChanged(@NonNull String str, @Nullable VerificationStateDescriptor verificationStateDescriptor);
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/VerificationApi$VerificationStateDescriptor.class */
    public static class VerificationStateDescriptor {
        private VerificationState a;
        @Nullable
        private boolean b;
        private VerificationSource c;
        private FailReason d;
        private boolean e;
        @Nullable
        private String f;
        @Nullable
        private String g;
        private String h;
        @Nullable
        private c.a i;
        private int j;
        private SmsCodeInfo k;
        private IvrInfo l;
        private Map<String, String> m;
        @Nullable
        private RateLimitType n;
        @Nullable
        private final CallUIDescriptor o;
        @Nullable
        private final CallInDescriptor p;
        @Nullable
        private final MobileIdDescriptor q;
        @Nullable
        private p.b[] r;

        /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/VerificationApi$VerificationStateDescriptor$IvrInfo.class */
        public class IvrInfo {
            public final Set<String> supportedIvrLanguages;
            public final int ivrTimeoutSec;
            public final boolean defaultIvrTimeoutApplied;

            IvrInfo(Set set, int i, boolean z) {
                this.supportedIvrLanguages = set;
                this.ivrTimeoutSec = i;
                this.defaultIvrTimeoutApplied = z;
            }

            @NonNull
            public String toString() {
                return "IvrInfo{supportedIvrLanguages=" + this.supportedIvrLanguages + ", ivrTimeoutSec=" + this.ivrTimeoutSec + ", defaultIvrTimeoutApplied=" + this.defaultIvrTimeoutApplied + '}';
            }
        }

        /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/VerificationApi$VerificationStateDescriptor$SmsCodeInfo.class */
        public class SmsCodeInfo {
            public final int smsCodeLength;
            public final boolean isNumericSmsCode;
            public final String receivedSmsCode;

            SmsCodeInfo(int i, boolean z, String str) {
                this.smsCodeLength = i;
                this.isNumericSmsCode = z;
                this.receivedSmsCode = str;
            }

            @NonNull
            public String toString() {
                return "SmsCodeInfo{smsCodeLength=" + this.smsCodeLength + ", isNumericSmsCode=" + this.isNumericSmsCode + '}';
            }
        }

        @Nullable
        private static CallInDescriptor a(@Nullable ru.mail.libverify.d.b bVar) {
            if (bVar instanceof b.a) {
                b.a aVar = (b.a) bVar;
                return new CallInDescriptor(aVar.b(), aVar.a(), aVar.c(), aVar.d(), aVar.e());
            }
            return null;
        }

        @Nullable
        private static MobileIdDescriptor b(@Nullable ru.mail.libverify.d.b bVar) {
            if (bVar instanceof b.d) {
                b.d dVar = (b.d) bVar;
                return new MobileIdDescriptor();
            }
            return null;
        }

        public VerificationStateDescriptor(VerificationState state, FailReason reason, boolean verifiedOnce) {
            this.a = VerificationState.INITIAL;
            this.c = VerificationSource.UNKNOWN;
            FailReason failReason = FailReason.OK;
            this.e = verifiedOnce;
            this.a = state;
            this.d = reason;
            this.o = null;
            this.p = null;
            this.q = null;
        }

        @Nullable
        public c.a getErrorDetailStatus() {
            return this.i;
        }

        @Nullable
        public RateLimitType getRateLimitType() {
            return this.n;
        }

        public VerificationState getState() {
            return this.a;
        }

        public FailReason getReason() {
            return this.d;
        }

        @Nullable
        public String getModifiedPhoneNumber() {
            return this.f;
        }

        @Nullable
        public String getUserId() {
            return this.g;
        }

        public String getToken() {
            return this.h;
        }

        public int getTokenExpirationTimeoutSec() {
            return this.j;
        }

        public Map<String, String> getAppEndpoints() {
            return this.m;
        }

        public VerificationSource getSource() {
            return this.c;
        }

        public SmsCodeInfo getSmsCodeInfo() {
            return this.k;
        }

        public IvrInfo getIvrInfo() {
            return this.l;
        }

        @Nullable
        public p.b[] getRoute() {
            return this.r;
        }

        public boolean completedSuccessfully() {
            VerificationState verificationState = this.a;
            return (verificationState == VerificationState.FINAL || verificationState == VerificationState.SUCCEEDED) && this.d == FailReason.OK && !TextUtils.isEmpty(this.h);
        }

        public boolean isVerifiedOnce() {
            return this.e;
        }

        @NonNull
        public String toString() {
            return "VerificationStateDescriptor{state='" + this.a + "', source='" + this.c + "', reason='" + this.d + "', modifiedPhoneNumber='" + this.f + "', token='" + this.h + "', smsCodeInfo='" + this.k + "', ivrInfo='" + this.l + "', appEndpoints='" + this.m + "'}";
        }

        public boolean isVKCLogin() {
            return this.b;
        }

        @Nullable
        public CallUIDescriptor getCallUIDescriptor() {
            return this.o;
        }

        @Nullable
        public CallInDescriptor getCallInDescriptor() {
            return this.p;
        }

        @Nullable
        public MobileIdDescriptor getMobileIdDescriptor() {
            return this.q;
        }

        @Nullable
        private static CallUIDescriptor a(@Nullable ru.mail.libverify.d.b bVar, int i) {
            if (bVar instanceof b.C0004b) {
                b.C0004b c0004b = (b.C0004b) bVar;
                return new CallUIDescriptor(c0004b.a(), c0004b.b(), c0004b.d(), i);
            }
            return null;
        }

        public VerificationStateDescriptor(VerificationState state, FailReason reason, boolean verifiedOnce, @Nullable RateLimitType rateLimitType) {
            this.a = VerificationState.INITIAL;
            this.c = VerificationSource.UNKNOWN;
            FailReason failReason = FailReason.OK;
            this.e = verifiedOnce;
            this.a = state;
            this.d = reason;
            this.n = rateLimitType;
            this.o = null;
            this.p = null;
            this.q = null;
        }

        public VerificationStateDescriptor(VerificationState verificationState, FailReason failReason, boolean z, @Nullable ru.mail.libverify.j.n nVar) {
            this(verificationState, failReason, z);
            if (nVar != null) {
                this.k = new SmsCodeInfo(nVar.i(), nVar.j() == n.a.NUMERIC, null);
            }
        }

        public VerificationStateDescriptor(VerificationState verificationState, VerificationSource verificationSource, FailReason failReason, boolean z, @Nullable String str, @Nullable String str2, String str3, int i, int i2, boolean z2, String str4, Set<String> set, int i3, Map<String, String> map, boolean z3, c.a aVar, @Nullable p.b[] bVarArr, boolean z4, @Nullable ru.mail.libverify.d.b bVar) {
            this.a = VerificationState.INITIAL;
            VerificationSource verificationSource2 = VerificationSource.UNKNOWN;
            this.f = str;
            this.c = verificationSource;
            this.d = failReason;
            this.h = str3;
            this.e = z;
            this.j = i;
            this.a = verificationState;
            this.b = z4;
            this.k = new SmsCodeInfo(i2, z2, str4);
            this.l = new IvrInfo(set, i3, z3);
            this.m = map;
            this.g = str2;
            this.i = aVar;
            this.o = a(bVar, i2);
            this.p = a(bVar);
            this.q = b(bVar);
            this.r = bVarArr;
        }

        public VerificationStateDescriptor(VerificationState state, boolean verifiedOnce) {
            this.a = VerificationState.INITIAL;
            this.c = VerificationSource.UNKNOWN;
            this.d = FailReason.OK;
            this.e = verifiedOnce;
            this.a = state;
            this.o = null;
            this.p = null;
            this.q = null;
        }

        public VerificationStateDescriptor(VerificationState verificationState, boolean z, @Nullable ru.mail.libverify.j.n nVar) {
            this(verificationState, z);
            if (nVar != null) {
                this.k = new SmsCodeInfo(nVar.i(), nVar.j() == n.a.NUMERIC, null);
            }
        }
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/VerificationApi$VerificationStatesHandler.class */
    public interface VerificationStatesHandler {
        void onExistingVerificationsFound(List<String> list);
    }

    @NonNull
    String startVerification(@NonNull String str, @NonNull String str2, @Nullable String str3, @Nullable Map<String, String> map, @Nullable VerificationParameters verificationParameters) throws IllegalStateException;

    @NonNull
    String startVerification(@NonNull String str, @Nullable String str2, @Nullable String str3, @Nullable Map<String, String> map, @Nullable VerifyRoute verifyRoute, @Nullable VerificationParameters verificationParameters) throws IllegalStateException;

    @NonNull
    String loggedInWithVKConnect(@NonNull String str, @Nullable String str2) throws IllegalStateException;

    @NonNull
    String startVerificationWithVKConnect(@NonNull String str, @Nullable String str2, @Nullable String str3, @Nullable Map<String, String> map, @Nullable String str4, @Nullable VerificationParameters verificationParameters) throws IllegalStateException;

    void cancelVerification(@NonNull String str);

    void cancelVerification(@NonNull String str, CancelReason cancelReason);

    void requestGcmToken(@NonNull GcmTokenListener gcmTokenListener);

    void completeVerification(@NonNull String str);

    void verifySmsCode(@NonNull String str, @NonNull String str2);

    void reportNetworkStateChange(boolean z);

    void addVerificationStateChangedListener(@NonNull VerificationStateChangedListener verificationStateChangedListener);

    void removeVerificationStateChangedListener(@NonNull VerificationStateChangedListener verificationStateChangedListener);

    void addSmsCodeNotificationListener(@NonNull SmsCodeNotificationListener smsCodeNotificationListener);

    void removeSmsCodeNotificationListener(@NonNull SmsCodeNotificationListener smsCodeNotificationListener);

    void setApiEndpoint(@Nullable String str);

    void removeApiEndpoint();

    void requestVerificationStates(@NonNull VerificationStatesHandler verificationStatesHandler);

    void requestVerificationState(@NonNull String str, @NonNull VerificationStateChangedListener verificationStateChangedListener);

    void resetVerificationCodeError(@NonNull String str);

    void reset();

    void signOut(boolean z);

    void signOut(boolean z, @Nullable SignOutCallback signOutCallback);

    void softSignOut();

    void softSignOut(SignOutCallback signOutCallback);

    void prepare2StepAuthCheck();

    void checkAccountVerification(@NonNull String str);

    void checkAccountVerificationBySms(@NonNull String str, @Nullable AccountCheckListener accountCheckListener);

    void requestIvrPhoneCall(@NonNull String str, IvrStateListener ivrStateListener);

    void checkPhoneNumber(@NonNull String str, @NonNull String str2, @NonNull String str3, boolean z, @NonNull PhoneCheckListener phoneCheckListener);

    void setCustomLocale(@NonNull Locale locale);

    void setSimDataSendDisabled(boolean z);

    void requestNewSmsCode(@NonNull String str);

    @RequiresPermission(allOf = {"android.permission.READ_PHONE_STATE", "android.permission.READ_PHONE_NUMBERS"})
    @Deprecated
    void searchPhoneAccounts(@NonNull PhoneAccountSearchListener phoneAccountSearchListener, boolean z);

    @RequiresPermission(allOf = {"android.permission.READ_PHONE_STATE", "android.permission.READ_PHONE_NUMBERS"})
    void searchPhoneAccounts(@NonNull PhoneAccountSearchListener phoneAccountSearchListener);

    void setAllowedPermissions(@NonNull String[] strArr);

    void setApiEndpoints(@NonNull Map<String, String> map);

    void querySmsDialogs(@NonNull SmsDialogsListener smsDialogsListener);

    void querySms(@Nullable String str, @Nullable Long l, @Nullable Long l2, @Nullable Integer num, @NonNull SmsListener smsListener);

    void removeSmsDialog(@Nullable String str, @Nullable Long l);

    void removeSms(@NonNull String str, @Nullable Long l, long j);

    void clearSmsDialogs();

    void addSmsDialogChangedListener(@NonNull SmsDialogChangedListener smsDialogChangedListener);

    void removeSmsDialogChangedListener(@NonNull SmsDialogChangedListener smsDialogChangedListener);

    void sendCallInClickStats(@NonNull String str);
}
