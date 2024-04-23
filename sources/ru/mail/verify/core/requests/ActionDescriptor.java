package ru.mail.verify.core.requests;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import ru.mail.verify.core.utils.Gsonable;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/requests/ActionDescriptor.class */
public final class ActionDescriptor implements Gsonable {
    private final String requestJson;
    private transient RequestSerializedData a;
    @Nullable
    public final Type type;
    public int actionTimeout;
    public final long createdTimestamp;
    public long lastAttemptTimestamp;
    public int attemptCount;

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/requests/ActionDescriptor$Type.class */
    public enum Type {
        UPDATE_SETTINGS(0),
        PUSH_STATUS(1),
        ATTEMPT(2),
        INSTANCE(3),
        EVENTS(4),
        CONTENT(5),
        CALLBACK(6),
        NOTIFY_PUSH_STATUS(7),
        NOTIFY_INAPP_UPDATE(8);
        
        private final int value;

        Type(int i) {
            this.value = i;
        }

        public int getValue() {
            return this.value;
        }
    }

    private ActionDescriptor() {
        this.actionTimeout = 0;
        this.lastAttemptTimestamp = 0L;
        this.attemptCount = 0;
        this.type = null;
        this.requestJson = null;
        this.createdTimestamp = 0L;
    }

    public RequestSerializedData getData() {
        if (this.a == null) {
            this.a = new RequestSerializedData(this.requestJson);
        }
        return this.a;
    }

    public ActionDescriptor(@Nullable Type type, @NonNull RequestSerializedData data, long createdTimestamp) {
        this.actionTimeout = 0;
        this.lastAttemptTimestamp = 0L;
        this.attemptCount = 0;
        this.type = type;
        this.requestJson = data.json;
        this.a = data;
        this.createdTimestamp = createdTimestamp;
    }
}
