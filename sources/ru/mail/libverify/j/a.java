package ru.mail.libverify.j;

import androidx.annotation.Nullable;
import java.util.Map;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/j/a.class */
public final class a extends c {
    private String token;
    private int token_expiration_time;
    private f fetcher_info;
    private Map<String, String> app_endpoints;

    public final String g() {
        return this.token;
    }

    public final int h() {
        return this.token_expiration_time;
    }

    @Nullable
    public final f f() {
        return this.fetcher_info;
    }

    public final Map<String, String> e() {
        return this.app_endpoints;
    }

    @Override // ru.mail.libverify.j.c
    public final String toString() {
        return super.toString();
    }
}
