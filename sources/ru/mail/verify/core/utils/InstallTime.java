package ru.mail.verify.core.utils;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/utils/InstallTime.class */
public class InstallTime {
    private final Type a;
    private final long b;

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/utils/InstallTime$Type.class */
    public enum Type {
        DEFAULT_TIME,
        FROM_APPLICATION_INFO,
        FROM_APPLICATION_FILE
    }

    public InstallTime(Type installType, long installTimestamp) {
        this.a = installType;
        this.b = installTimestamp;
    }

    public long getInstallTimestamp() {
        return this.b;
    }

    public Type getInstallType() {
        return this.a;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InstallTime installTime = (InstallTime) o;
        return this.b == installTime.b && this.a == installTime.a;
    }

    public int hashCode() {
        Type type = this.a;
        int hashCode = (type != null ? type.hashCode() : 0) * 31;
        return hashCode + ((int) (hashCode ^ (this.b >>> 32)));
    }
}
