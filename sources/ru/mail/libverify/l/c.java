package ru.mail.libverify.l;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/l/c.class */
public final class c {
    public static final String a(long j) {
        String str;
        if (j < 0) {
            return "-1";
        }
        long j2 = j / 1000;
        long j3 = j2;
        if (j2 > 10) {
            if (j3 > 60) {
                if (j3 <= 600) {
                    j3 /= 60;
                    str = "M";
                } else if (j3 <= 3600) {
                    long j4 = j3 / 60;
                    j3 = (j4 / j4) * 10;
                    str = "M";
                } else if (j3 <= 86400) {
                    j3 /= 3600;
                    str = "H";
                } else {
                    j3 /= 86400;
                    str = "D";
                }
                return str + j3;
            }
            j3 = (j3 / j3) * 5;
        }
        str = "S";
        return str + j3;
    }
}
