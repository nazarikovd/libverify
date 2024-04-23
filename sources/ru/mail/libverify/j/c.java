package ru.mail.libverify.j;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import ru.mail.libverify.i.c;
import ru.mail.verify.core.requests.response.ResponseBase;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/j/c.class */
public class c<T extends ru.mail.libverify.i.c> extends ResponseBase<T> {
    private b status;
    private String description;
    private a detail_status;
    @Nullable
    private Long server_timestamp;

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/j/c$a.class */
    public enum a {
        UNKNOWN_LIBVERIFY,
        UNDEFINED_PHONE,
        NO_CHECKPARAMS,
        CONFIRM_NOT_ALLOWED,
        EMPTY_APPLICATION_ID,
        EMPTY_CODE,
        EMPTY_PHONE,
        EMPTY_SESSION,
        EMPTY_STATUS,
        EMPTY_STORED_CODE,
        EXPIRED_SESSION,
        INCORRECT_CODE,
        NOT_ENOUGH_DATA,
        NO_VERIFICATION_SMS,
        SMSACTION_NOT_AVAILABLE,
        SMSGATE_NOT_AVAILABLE,
        TOO_SHORT_CODE,
        UNKNOWN_PHONE,
        UNKNOWN_PRODUCT,
        UNKNOWN_SERVICE,
        UNSUPPORTED_PHONE,
        NO_CALL,
        INCORRECT_SIGNATURE,
        UNKNOWN_APPLICATION,
        INVALID_CODE_SOURCE,
        UNSUPPORTED_ROUTES,
        UNKNOWN
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/j/c$b.class */
    public enum b {
        OK,
        ERROR,
        VERIFIED,
        UNSUPPORTED_NUMBER,
        INCORRECT_PHONE_NUMBER,
        PHONE_NUMBER_IN_BLACK_LIST,
        PHONE_NUMBER_TYPE_NOT_ALLOWED,
        RATELIMIT,
        ATTEMPTLIMIT,
        NOT_ENOUGH_DATA,
        UNKNOWN
    }

    @NonNull
    public b d() {
        if (this.status == null) {
            this.status = b.UNKNOWN;
        }
        return (this.detail_status == a.UNSUPPORTED_ROUTES && this.status == b.ERROR) ? b.UNSUPPORTED_NUMBER : this.status;
    }

    @NonNull
    public final a b() {
        if (this.detail_status == null) {
            this.detail_status = a.UNKNOWN;
        }
        a aVar = this.detail_status;
        return (aVar == a.UNSUPPORTED_ROUTES && this.status == b.ERROR) ? a.UNKNOWN : aVar;
    }

    @Nullable
    public final Long c() {
        return this.server_timestamp;
    }

    @Override // ru.mail.verify.core.requests.response.ResponseBase
    public final boolean isOk() {
        return d() == b.OK;
    }

    public final String a() {
        return this.description;
    }

    @NonNull
    public String toString() {
        return "ClientApiResponseBase{status=" + this.status + ", description='" + this.description + "', detail_status='" + this.detail_status + "'}";
    }
}
