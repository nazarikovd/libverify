package ru.mail.libverify.j;

import androidx.annotation.Nullable;
import ru.mail.verify.core.utils.Gsonable;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/j/f.class */
public final class f implements Gsonable {
    private long timeout;
    private String url;
    private long timestamp;
    private long lastModified;
    private String eTag;
    private b status;
    private a detail_status;

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/j/f$a.class */
    public enum a {
        CALL_TEMPLATE_SENT,
        ERROR,
        UNKNOWN
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/j/f$b.class */
    public enum b {
        ENABLED,
        DISABLED,
        UNKNOWN
    }

    public final long e() {
        return this.timestamp;
    }

    public final long b() {
        return this.lastModified;
    }

    @Nullable
    public final String a() {
        return this.eTag;
    }

    public final b c() {
        if (this.status == null) {
            this.status = b.UNKNOWN;
        }
        return this.status;
    }

    public final String f() {
        return this.url;
    }

    public final long d() {
        return this.timeout;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || f.class != obj.getClass()) {
            return false;
        }
        f fVar = (f) obj;
        if (this.timeout != fVar.timeout) {
            return false;
        }
        String str = this.url;
        if (str != null) {
            if (!str.equals(fVar.url)) {
                return false;
            }
        } else if (fVar.url != null) {
            return false;
        }
        return this.status == fVar.status && this.detail_status == fVar.detail_status;
    }

    public final int hashCode() {
        long j = this.timeout;
        int i = ((int) (j ^ (j >>> 32))) * 31;
        String str = this.url;
        int hashCode = (i + (str != null ? str.hashCode() : 0)) * 31;
        b bVar = this.status;
        int hashCode2 = (hashCode + (bVar != null ? bVar.hashCode() : 0)) * 31;
        a aVar = this.detail_status;
        return hashCode2 + (aVar != null ? aVar.hashCode() : 0);
    }

    public final String toString() {
        return super.toString();
    }

    public final void a(@Nullable String str) {
        if (str == null) {
            return;
        }
        this.eTag = str;
    }

    public final void a(@Nullable Long l) {
        if (l == null) {
            return;
        }
        this.lastModified = l.longValue();
    }

    public final void a(long j) {
        this.timestamp = j;
    }
}
