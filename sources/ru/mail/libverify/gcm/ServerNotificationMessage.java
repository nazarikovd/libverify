package ru.mail.libverify.gcm;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.Set;
import ru.mail.libverify.i.j;
import ru.mail.verify.core.utils.Gsonable;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/gcm/ServerNotificationMessage.class */
public class ServerNotificationMessage implements Gsonable {
    private Message message;
    private String sender;
    private Long hold_timeout;
    private long server_timestamp = 0;
    private long timestamp = 0;
    private j.c deliveryMethod = j.c.UNKNOWN;

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/gcm/ServerNotificationMessage$Message.class */
    public static class Message implements Gsonable {
        private String device_id;
        private String system_id;
        private String push_token_id;
        private String text;
        private String public_text;
        private String verify_code;
        private String from;
        private Set<NotificationFlags> delivery_flags;
        private NotificationType type;
        private String imsi;
        private String imei;
        private String session_id;
        private String application_id;
        private String phone;
        private Controls controls;
        private String verification_url;
        private String requester_package_name;
        private String shortcut_name;
        private long requester_id;
        private String src_application_logo;

        /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/gcm/ServerNotificationMessage$Message$Controls.class */
        public static class Controls implements Gsonable {
            private Confirm confirm;
            private Description description;

            /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/gcm/ServerNotificationMessage$Message$Controls$Confirm.class */
            public static class Confirm implements Gsonable {
                private String text;
                private int enableTimeoutSec;

                public String getText() {
                    return this.text;
                }

                public int getEnableTimeoutSec() {
                    return this.enableTimeoutSec;
                }

                public boolean equals(Object o) {
                    if (this == o) {
                        return true;
                    }
                    if (o == null || getClass() != o.getClass()) {
                        return false;
                    }
                    Confirm confirm = (Confirm) o;
                    if (this.enableTimeoutSec != confirm.enableTimeoutSec) {
                        return false;
                    }
                    String str = this.text;
                    return str != null ? str.equals(confirm.text) : confirm.text == null;
                }

                public int hashCode() {
                    String str = this.text;
                    return ((str != null ? str.hashCode() : 0) * 31) + this.enableTimeoutSec;
                }
            }

            /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/gcm/ServerNotificationMessage$Message$Controls$Description.class */
            public static class Description implements Gsonable {
                private String text;

                public String getText() {
                    return this.text;
                }

                public boolean equals(Object o) {
                    if (this == o) {
                        return true;
                    }
                    if (o == null || getClass() != o.getClass()) {
                        return false;
                    }
                    Description description = (Description) o;
                    String str = this.text;
                    return str != null ? str.equals(description.text) : description.text == null;
                }

                public int hashCode() {
                    String str = this.text;
                    if (str != null) {
                        return str.hashCode();
                    }
                    return 0;
                }
            }

            public Description getDescription() {
                return this.description;
            }

            public Confirm getConfirm() {
                return this.confirm;
            }

            public boolean equals(Object o) {
                if (this == o) {
                    return true;
                }
                if (o == null || getClass() != o.getClass()) {
                    return false;
                }
                Controls controls = (Controls) o;
                Confirm confirm = this.confirm;
                if (confirm != null) {
                    if (!confirm.equals(controls.confirm)) {
                        return false;
                    }
                } else if (controls.confirm != null) {
                    return false;
                }
                Description description = this.description;
                return description != null ? description.equals(controls.description) : controls.description == null;
            }

            public int hashCode() {
                Confirm confirm = this.confirm;
                int hashCode = (confirm != null ? confirm.hashCode() : 0) * 31;
                Description description = this.description;
                return hashCode + (description != null ? description.hashCode() : 0);
            }
        }

        /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/gcm/ServerNotificationMessage$Message$NotificationFlags.class */
        public enum NotificationFlags {
            POPUP,
            IPC,
            SMS,
            UNKNOWN
        }

        /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/gcm/ServerNotificationMessage$Message$NotificationType.class */
        public enum NotificationType {
            MESSAGE,
            PING,
            PING_V2,
            VERIFIED,
            UNKNOWN
        }

        public String getRequesterPackageName() {
            return this.requester_package_name;
        }

        public String getVerifyCode() {
            return this.verify_code;
        }

        public String getVerificationUrl() {
            return this.verification_url;
        }

        public String getImsi() {
            return this.imsi;
        }

        public String getImei() {
            return this.imei;
        }

        public String getText() {
            return this.text;
        }

        public String getPublicText() {
            return this.public_text;
        }

        public String getShortcutName() {
            return this.shortcut_name;
        }

        public String getFrom() {
            return this.from;
        }

        public Set<NotificationFlags> getFlags() {
            return this.delivery_flags;
        }

        public NotificationType getType() {
            return this.type;
        }

        public String getSessionId() {
            return this.session_id;
        }

        public Controls getControls() {
            return this.controls;
        }

        public String getApplicationId() {
            return this.application_id;
        }

        public String getPhone() {
            return this.phone;
        }

        public String getDescription() {
            Controls.Description description;
            Controls controls = this.controls;
            if (controls == null || (description = controls.description) == null || TextUtils.isEmpty(description.text)) {
                return null;
            }
            return this.controls.description.text;
        }

        public boolean hasConfirmation() {
            Controls.Confirm confirm;
            Controls controls = this.controls;
            return (controls == null || (confirm = controls.confirm) == null || TextUtils.isEmpty(confirm.getText())) ? false : true;
        }

        public String getConfirmationText() {
            if (hasConfirmation()) {
                return this.controls.confirm.text;
            }
            return null;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Message message = (Message) o;
            if (this.requester_id != message.requester_id) {
                return false;
            }
            String str = this.text;
            if (str != null) {
                if (!str.equals(message.text)) {
                    return false;
                }
            } else if (message.text != null) {
                return false;
            }
            String str2 = this.verify_code;
            if (str2 != null) {
                if (!str2.equals(message.verify_code)) {
                    return false;
                }
            } else if (message.verify_code != null) {
                return false;
            }
            String str3 = this.from;
            if (str3 != null) {
                if (!str3.equals(message.from)) {
                    return false;
                }
            } else if (message.from != null) {
                return false;
            }
            Set<NotificationFlags> set = this.delivery_flags;
            if (set != null) {
                if (!set.equals(message.delivery_flags)) {
                    return false;
                }
            } else if (message.delivery_flags != null) {
                return false;
            }
            if (this.type != message.type) {
                return false;
            }
            String str4 = this.imsi;
            if (str4 != null) {
                if (!str4.equals(message.imsi)) {
                    return false;
                }
            } else if (message.imsi != null) {
                return false;
            }
            String str5 = this.imei;
            if (str5 != null) {
                if (!str5.equals(message.imei)) {
                    return false;
                }
            } else if (message.imei != null) {
                return false;
            }
            String str6 = this.session_id;
            if (str6 != null) {
                if (!str6.equals(message.session_id)) {
                    return false;
                }
            } else if (message.session_id != null) {
                return false;
            }
            String str7 = this.application_id;
            if (str7 != null) {
                if (!str7.equals(message.application_id)) {
                    return false;
                }
            } else if (message.application_id != null) {
                return false;
            }
            String str8 = this.phone;
            if (str8 != null) {
                if (!str8.equals(message.phone)) {
                    return false;
                }
            } else if (message.phone != null) {
                return false;
            }
            Controls controls = this.controls;
            if (controls != null) {
                if (!controls.equals(message.controls)) {
                    return false;
                }
            } else if (message.controls != null) {
                return false;
            }
            String str9 = this.verification_url;
            if (str9 != null) {
                if (!str9.equals(message.verification_url)) {
                    return false;
                }
            } else if (message.verification_url != null) {
                return false;
            }
            String str10 = this.requester_package_name;
            return str10 != null ? str10.equals(message.requester_package_name) : message.requester_package_name == null;
        }

        public int hashCode() {
            String str = this.text;
            int hashCode = (str != null ? str.hashCode() : 0) * 31;
            String str2 = this.verify_code;
            int hashCode2 = (hashCode + (str2 != null ? str2.hashCode() : 0)) * 31;
            String str3 = this.from;
            int hashCode3 = (hashCode2 + (str3 != null ? str3.hashCode() : 0)) * 31;
            Set<NotificationFlags> set = this.delivery_flags;
            int hashCode4 = (hashCode3 + (set != null ? set.hashCode() : 0)) * 31;
            NotificationType notificationType = this.type;
            int hashCode5 = (hashCode4 + (notificationType != null ? notificationType.hashCode() : 0)) * 31;
            String str4 = this.imsi;
            int hashCode6 = (hashCode5 + (str4 != null ? str4.hashCode() : 0)) * 31;
            String str5 = this.imei;
            int hashCode7 = (hashCode6 + (str5 != null ? str5.hashCode() : 0)) * 31;
            String str6 = this.session_id;
            int hashCode8 = (hashCode7 + (str6 != null ? str6.hashCode() : 0)) * 31;
            String str7 = this.application_id;
            int hashCode9 = (hashCode8 + (str7 != null ? str7.hashCode() : 0)) * 31;
            String str8 = this.phone;
            int hashCode10 = (hashCode9 + (str8 != null ? str8.hashCode() : 0)) * 31;
            Controls controls = this.controls;
            int hashCode11 = (hashCode10 + (controls != null ? controls.hashCode() : 0)) * 31;
            String str9 = this.verification_url;
            int hashCode12 = (hashCode11 + (str9 != null ? str9.hashCode() : 0)) * 31;
            String str10 = this.requester_package_name;
            int hashCode13 = (hashCode12 + (str10 != null ? str10.hashCode() : 0)) * 31;
            return hashCode13 + ((int) (hashCode13 ^ (this.requester_id >>> 32)));
        }

        @NonNull
        public String toString() {
            return "Message{text='" + this.text + "', from='" + this.from + "', session_id='" + this.session_id + "', requester_package_name=" + this.requester_package_name + ", requester_id='" + this.requester_id + "'}";
        }

        @Nullable
        public String getSrcApplicationLogo() {
            return this.src_application_logo;
        }

        public String getPushTokenId() {
            return this.push_token_id;
        }

        public String getDeviceId() {
            return this.device_id;
        }

        public String getSystemId() {
            return this.system_id;
        }
    }

    public Message getMessage() {
        return this.message;
    }

    public String getSender() {
        return this.sender;
    }

    public String getId() {
        return this.message.from + Long.toString(this.message.requester_id);
    }

    public void setLocalTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getLocalTimestamp() {
        return this.timestamp;
    }

    public long getServerTimestamp() {
        return this.server_timestamp;
    }

    @Nullable
    public Long getHoldTimeout() {
        return this.hold_timeout;
    }

    public j.c getDeliveryMethod() {
        return this.deliveryMethod;
    }

    public void setDeliveryMethod(j.c cVar) {
        this.deliveryMethod = cVar;
    }

    @Nullable
    public String getSrcApplicationLogo() {
        Message message = this.message;
        if (message == null) {
            return null;
        }
        return message.src_application_logo;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ServerNotificationMessage serverNotificationMessage = (ServerNotificationMessage) o;
        Message message = this.message;
        return message != null ? message.equals(serverNotificationMessage.message) : serverNotificationMessage.message == null;
    }

    public int hashCode() {
        Message message = this.message;
        if (message != null) {
            return message.hashCode();
        }
        return 0;
    }

    @NonNull
    public String toString() {
        return "ServerNotificationMessage{message=" + this.message + ", sender='" + this.sender + "', timestamp=" + this.timestamp + ", hold_timeout=" + this.hold_timeout + ", deliveryMethod=" + this.deliveryMethod + '}';
    }
}
