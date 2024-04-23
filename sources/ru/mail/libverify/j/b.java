package ru.mail.libverify.j;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.Arrays;
import ru.mail.verify.core.utils.Gsonable;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/j/b.class */
public final class b implements Gsonable {
    private String[] call_fragment_template;

    @Nullable
    public final String[] a() {
        return this.call_fragment_template;
    }

    @NonNull
    public final String toString() {
        return super.toString();
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof b) {
            return Arrays.equals(this.call_fragment_template, ((b) obj).call_fragment_template);
        }
        return false;
    }

    public final int hashCode() {
        return Arrays.hashCode(this.call_fragment_template);
    }
}
