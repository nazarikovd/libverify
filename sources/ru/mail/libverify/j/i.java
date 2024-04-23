package ru.mail.libverify.j;

import android.text.TextUtils;
import androidx.annotation.Nullable;
import ru.mail.verify.core.utils.Gsonable;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/j/i.class */
public final class i extends c {
    private String[] printable;
    private a typing_check;

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/j/i$a.class */
    public static class a implements Gsonable {
        private int show_warning;
        private String modified_phone_number;
        private Integer[] remaining_lengths;
        private String[] prefix_state;
        private String modified_prefix;

        public final boolean d() {
            String[] strArr = this.prefix_state;
            if (strArr == null) {
                return false;
            }
            for (String str : strArr) {
                if (!TextUtils.isEmpty(str) && str.equalsIgnoreCase("fixed-line")) {
                    return true;
                }
            }
            return false;
        }

        public final boolean e() {
            String[] strArr = this.prefix_state;
            if (strArr == null) {
                return false;
            }
            for (String str : strArr) {
                if (!TextUtils.isEmpty(str) && str.equalsIgnoreCase("mobile")) {
                    return true;
                }
            }
            return false;
        }

        @Nullable
        public final Integer[] c() {
            Integer[] numArr = this.remaining_lengths;
            if (numArr == null || numArr.length == 0) {
                return null;
            }
            return numArr;
        }

        public final boolean f() {
            return this.show_warning == 1;
        }

        public final String b() {
            return this.modified_prefix;
        }

        public final String a() {
            return this.modified_phone_number;
        }
    }

    @Nullable
    public final a f() {
        return this.typing_check;
    }

    @Nullable
    public final String[] e() {
        return this.printable;
    }

    @Override // ru.mail.libverify.j.c
    public final String toString() {
        return super.toString();
    }
}
