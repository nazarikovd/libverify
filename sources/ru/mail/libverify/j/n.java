package ru.mail.libverify.j;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import ru.mail.libverify.i.p;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/j/n.class */
public final class n extends c {
    private String verification_url;
    private String[] call_template;
    private String[] call_fragment_template;
    private String ivr_timeout_sec;
    private String modified_phone_number;
    private String session_id;
    private String session_id_hash;
    private String sms_template;
    private String push_template;
    private Map<String, String> app_endpoints;
    private Set<String> supported_ivr_languages;
    private String verify_code;
    private int token_expiration_time;
    private int code_length;
    private a code_type;
    private String token;
    private Integer wait_for_route;
    private p.b[] checks;
    private f fetcher_info;
    private k safety_net_v3;

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/j/n$a.class */
    public enum a {
        NUMERIC,
        ALPHANUMERIC
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof n) {
            n nVar = (n) obj;
            return this.token_expiration_time == nVar.token_expiration_time && this.code_length == nVar.code_length && Objects.equals(this.verification_url, nVar.verification_url) && Arrays.equals(this.call_template, nVar.call_template) && Arrays.equals(this.call_fragment_template, nVar.call_fragment_template) && Objects.equals(this.ivr_timeout_sec, nVar.ivr_timeout_sec) && Objects.equals(this.modified_phone_number, nVar.modified_phone_number) && Objects.equals(this.session_id, nVar.session_id) && Objects.equals(this.session_id_hash, nVar.session_id_hash) && Objects.equals(this.sms_template, nVar.sms_template) && Objects.equals(this.app_endpoints, nVar.app_endpoints) && Objects.equals(this.supported_ivr_languages, nVar.supported_ivr_languages) && Objects.equals(this.verify_code, nVar.verify_code) && this.code_type == nVar.code_type && Objects.equals(this.token, nVar.token) && Objects.equals(this.wait_for_route, nVar.wait_for_route) && Objects.equals(this.checks, nVar.checks) && Objects.equals(this.fetcher_info, nVar.fetcher_info);
        }
        return false;
    }

    public final int hashCode() {
        return (((Objects.hash(this.verification_url, this.ivr_timeout_sec, this.modified_phone_number, this.session_id, this.session_id_hash, this.sms_template, this.app_endpoints, this.supported_ivr_languages, this.verify_code, Integer.valueOf(this.token_expiration_time), Integer.valueOf(this.code_length), this.code_type, this.token, this.wait_for_route, this.checks, this.fetcher_info) * 31) + Arrays.hashCode(this.call_template)) * 31) + Arrays.hashCode(this.call_fragment_template);
    }

    public final k p() {
        return this.safety_net_v3;
    }

    public final Map<String, String> e() {
        return this.app_endpoints;
    }

    public final void a(@Nullable Map<String, String> map) {
        this.app_endpoints = map;
    }

    public final a j() {
        return this.code_type;
    }

    public final int i() {
        return this.code_length;
    }

    public final int u() {
        return this.token_expiration_time;
    }

    public final String v() {
        return this.verification_url;
    }

    @Nullable
    public final Integer w() {
        return this.wait_for_route;
    }

    @Nullable
    public final String[] g() {
        return this.call_template;
    }

    @Nullable
    public final String[] f() {
        return this.call_fragment_template;
    }

    public final String m() {
        return this.ivr_timeout_sec;
    }

    public final String n() {
        return this.modified_phone_number;
    }

    public final String q() {
        return this.session_id;
    }

    @Nullable
    public final String r() {
        return this.sms_template;
    }

    @Nullable
    public final String o() {
        return this.push_template;
    }

    @Nullable
    public final String l() {
        return this.session_id_hash;
    }

    @Nullable
    public final Set<String> s() {
        return this.supported_ivr_languages;
    }

    public final String t() {
        return this.token;
    }

    @Nullable
    public final p.b[] h() {
        return this.checks;
    }

    @Nullable
    public final f k() {
        return this.fetcher_info;
    }

    @Override // ru.mail.libverify.j.c
    @NonNull
    public final String toString() {
        return super.toString();
    }

    public final void a(String str) {
        this.token = str;
    }

    public final void a(int i) {
        this.token_expiration_time = i;
    }
}
