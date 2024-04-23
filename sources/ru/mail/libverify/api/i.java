package ru.mail.libverify.api;

import androidx.annotation.NonNull;
import java.util.ArrayList;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/i.class */
public interface i {

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/i$a.class */
    public enum a {
        PACKAGE_UPDATED,
        PACKAGE_REMOVED,
        RESTART,
        TIMER,
        SMS_TEMPLATES_CHECK,
        UNKNOWN
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/i$b.class */
    public static class b {
        public final String a;
        public final String b;
        public final String c;
        public final Boolean d;
        public final String e;
        public final String f;
        public final String g;
        public final String h;
        public final String i;
        public final boolean j;
        public final boolean k;

        /* JADX INFO: Access modifiers changed from: package-private */
        public b(@NonNull String str, @NonNull String str2, @NonNull String str3, @NonNull String str4, @NonNull String str5, String str6, Boolean bool, String str7, String str8, boolean z, boolean z2) {
            this.a = str2;
            this.b = str4;
            this.c = str6;
            this.d = bool;
            this.e = str3;
            this.f = str;
            this.g = str7;
            this.h = str5;
            this.i = str8;
            this.j = z;
            this.k = z2;
        }
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/i$c.class */
    public interface c {
        void a(b bVar);
    }

    ArrayList a();
}
