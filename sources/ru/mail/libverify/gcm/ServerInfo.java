package ru.mail.libverify.gcm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.Objects;
import ru.mail.libverify.api.VerificationApi;
import ru.mail.libverify.i.p;
import ru.mail.libverify.j.b;
import ru.mail.libverify.j.g;
import ru.mail.libverify.j.n;
import ru.mail.verify.core.utils.Gsonable;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/gcm/ServerInfo.class */
public class ServerInfo implements Gsonable {
    private b call_info;
    private NotificationInfo notification_info;
    private n delayed_verify_response;
    private int confirm_required;
    private Long push_id;
    private Long valid_until;
    private p.b route_type;
    private String session_id;
    private CallUiInfo callui_info;
    private MobileId mobileid;
    private DoAttempt do_attempt;
    private g message;
    private NotificationType type;
    private CallInInfo callin_info;
    private SmsInfo sms_info;

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/gcm/ServerInfo$CallInInfo.class */
    public static final class CallInInfo implements Gsonable {
        private Integer fallback_timeout;
        private Integer total_fallback_timeout;
        private String phone;
        private int disable_direct_call = 0;
        private int ivr = 0;

        @NonNull
        public Integer getFallbackTimeout() {
            return this.fallback_timeout;
        }

        @Nullable
        public Integer getTotalFallbackTimeout() {
            return this.total_fallback_timeout;
        }

        @NonNull
        public String getPhone() {
            return this.phone;
        }

        public boolean isDisableDirectCall() {
            return this.disable_direct_call == 1;
        }

        public boolean isIvr() {
            return this.ivr == 1;
        }
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/gcm/ServerInfo$CallUiInfo.class */
    public static final class CallUiInfo implements Gsonable {
        private Integer fallback_timeout;
        private String fragment_start;
        private String description;
        private String description_optional;

        @Nullable
        public String getFragmentStart() {
            return this.fragment_start;
        }

        @Nullable
        public Integer getFallbackTimeout() {
            return this.fallback_timeout;
        }

        @Nullable
        public String getDescription() {
            return this.description;
        }

        @Nullable
        public String getDescriptionOptional() {
            return this.description_optional;
        }

        @NonNull
        public String toString() {
            return super.toString();
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o instanceof CallUiInfo) {
                CallUiInfo callUiInfo = (CallUiInfo) o;
                return Objects.equals(this.fallback_timeout, callUiInfo.fallback_timeout) && Objects.equals(this.fragment_start, callUiInfo.fragment_start) && Objects.equals(this.description, callUiInfo.description) && Objects.equals(this.description_optional, callUiInfo.description_optional);
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(this.fallback_timeout, this.fragment_start, this.description, this.description_optional);
        }
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/gcm/ServerInfo$DoAttempt.class */
    public static final class DoAttempt implements Gsonable {
        private String code;
        @Nullable
        private VerificationApi.VerificationSource code_source;

        @Nullable
        public VerificationApi.VerificationSource getCodeSource() {
            return this.code_source;
        }

        public String getCode() {
            return this.code;
        }

        @NonNull
        public String toString() {
            return "DoAttempt{code='" + this.code + "', code_source='" + this.code_source + "'}";
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o instanceof DoAttempt) {
                return Objects.equals(this.code, ((DoAttempt) o).code);
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(this.code);
        }
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/gcm/ServerInfo$MobileId.class */
    public static final class MobileId implements Gsonable {
        private static final int DEFAULT_FALLBACK_TIMEOUT = 5000;
        private String url;
        private int max_redirects;
        private Integer fallback_timeout = null;

        public String getUrl() {
            return this.url;
        }

        public int getMaxRedirects() {
            return this.max_redirects;
        }

        public void setMaxRedirects(int max_redirects) {
            this.max_redirects = max_redirects;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getFallbackTimeout() {
            Integer num = this.fallback_timeout;
            return num == null ? DEFAULT_FALLBACK_TIMEOUT : num.intValue();
        }

        public String toString() {
            return "MobileId{url='" + this.url + "'}";
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o instanceof MobileId) {
                return Objects.equals(this.url, ((MobileId) o).url);
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(this.url);
        }
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/gcm/ServerInfo$NotificationInfo.class */
    public static final class NotificationInfo implements Gsonable {
        private Action action = Action.UNKNOWN;
        private String session_id;

        /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/gcm/ServerInfo$NotificationInfo$Action.class */
        public enum Action {
            COMPLETED,
            UNKNOWN
        }

        public Action getAction() {
            return this.action;
        }

        public String getSession_id() {
            return this.session_id;
        }

        @NonNull
        public String toString() {
            return "NotificationInfo{, action=" + this.action + '}';
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            return (o instanceof NotificationInfo) && this.action == ((NotificationInfo) o).action;
        }

        public int hashCode() {
            return Objects.hash(this.action);
        }
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/gcm/ServerInfo$NotificationType.class */
    public enum NotificationType {
        MOBILEID,
        DO_ATTEMPT,
        ROUTE_INFO,
        UNKNOWN
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/gcm/ServerInfo$SmsInfo.class */
    public static final class SmsInfo implements Gsonable {
        private Integer fallback_timeout;

        @Nullable
        public Integer getFallbackTimeout() {
            return this.fallback_timeout;
        }
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof ServerInfo) {
            ServerInfo serverInfo = (ServerInfo) o;
            return this.confirm_required == serverInfo.confirm_required && Objects.equals(this.call_info, serverInfo.call_info) && Objects.equals(this.notification_info, serverInfo.notification_info) && Objects.equals(this.delayed_verify_response, serverInfo.delayed_verify_response) && this.route_type == serverInfo.route_type && Objects.equals(this.session_id, serverInfo.session_id) && Objects.equals(this.callui_info, serverInfo.callui_info) && Objects.equals(this.mobileid, serverInfo.mobileid) && Objects.equals(this.do_attempt, serverInfo.do_attempt) && Objects.equals(this.message, serverInfo.message) && this.type == serverInfo.type;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.call_info, this.notification_info, this.delayed_verify_response, Integer.valueOf(this.confirm_required), this.route_type, this.session_id, this.callui_info, this.mobileid, this.do_attempt, this.message, this.type);
    }

    @Nullable
    public Long getPushId() {
        return this.push_id;
    }

    @Nullable
    public Long getValidUntil() {
        return this.valid_until;
    }

    @Nullable
    public CallInInfo getCallInInfo() {
        return this.callin_info;
    }

    @Nullable
    public SmsInfo getSmsInfo() {
        return this.sms_info;
    }

    public NotificationType getType() {
        return this.type;
    }

    public boolean isConfirmRequired() {
        return this.confirm_required == 1;
    }

    @Nullable
    public b getCallInfo() {
        return this.call_info;
    }

    @Nullable
    public MobileId getMobileId() {
        return this.mobileid;
    }

    @Nullable
    public DoAttempt getDoAttempt() {
        return this.do_attempt;
    }

    @Nullable
    public NotificationInfo getNotificationInfo() {
        return this.notification_info;
    }

    @Nullable
    public n getDelayedVerifyResponse() {
        return this.delayed_verify_response;
    }

    @Nullable
    public p.b getRoute() {
        return this.route_type;
    }

    @Nullable
    public CallUiInfo getCallUiInfo() {
        return this.callui_info;
    }

    public String getHashedSessionId() {
        return this.session_id;
    }

    @NonNull
    public String toString() {
        return "ServerInfo{, type=" + this.type + ", call_info=" + this.call_info + ", notification_info=" + this.notification_info + ", confirm_required=" + this.confirm_required + ", delayed_verify_response=" + this.delayed_verify_response + ", session_id=" + this.session_id + ", route_type=" + this.route_type + '}';
    }

    public g getMessageInfo() {
        return this.message;
    }
}
