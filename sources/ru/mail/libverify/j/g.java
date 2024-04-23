package ru.mail.libverify.j;

import androidx.annotation.NonNull;
import java.util.Objects;
import ru.mail.verify.core.utils.Gsonable;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/j/g.class */
public final class g implements Gsonable {
    private String device_id;
    private String system_id;

    @NonNull
    public final String toString() {
        return super.toString();
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof g) {
            g gVar = (g) obj;
            return Objects.equals(this.device_id, gVar.device_id) && Objects.equals(this.system_id, gVar.system_id);
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hash(this.device_id, this.system_id);
    }
}
