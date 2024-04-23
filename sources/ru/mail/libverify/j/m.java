package ru.mail.libverify.j;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/j/m.class */
public final class m extends c {
    private l sms_info;
    private f fetcher_info;
    private k safety_net_v3;
    private long update_settings_timeout;
    private Integer broadcast_on_demand;
    private Integer sms_check;
    private Integer run_single_fetcher;
    private Integer track_update;
    private Integer sms_find;
    private Integer send_call_stats;
    private Integer update_alarms;
    private Integer background_verify;
    private Integer write_history;
    private Integer add_shortcut;
    private transient boolean d = false;

    @Nullable
    public final l k() {
        return this.sms_info;
    }

    public final boolean m() {
        return this.d;
    }

    public final void p() {
        this.d = true;
    }

    public final long l() {
        return this.update_settings_timeout;
    }

    @Nullable
    public final f j() {
        return this.fetcher_info;
    }

    @Nullable
    public final k i() {
        return this.safety_net_v3;
    }

    @Nullable
    public final Boolean h() {
        Integer num = this.broadcast_on_demand;
        if (num == null) {
            return null;
        }
        return Boolean.valueOf(num.intValue() == 1);
    }

    @Nullable
    public final Boolean n() {
        Integer num = this.sms_check;
        if (num == null) {
            return null;
        }
        return Boolean.valueOf(num.intValue() == 1);
    }

    @Nullable
    public final Boolean q() {
        Integer num = this.run_single_fetcher;
        if (num == null) {
            return null;
        }
        return Boolean.valueOf(num.intValue() == 1);
    }

    @Nullable
    public final Boolean e() {
        Integer num = this.sms_find;
        if (num == null) {
            return null;
        }
        return Boolean.valueOf(num.intValue() == 1);
    }

    @Nullable
    public final Boolean r() {
        Integer num = this.track_update;
        if (num == null) {
            return null;
        }
        return Boolean.valueOf(num.intValue() == 1);
    }

    @Nullable
    public final Boolean o() {
        Integer num = this.send_call_stats;
        if (num == null) {
            return null;
        }
        return Boolean.valueOf(num.intValue() == 1);
    }

    @Nullable
    public final Boolean s() {
        Integer num = this.update_alarms;
        if (num == null) {
            return null;
        }
        return Boolean.valueOf(num.intValue() == 1);
    }

    @Nullable
    public final Boolean g() {
        Integer num = this.background_verify;
        if (num == null) {
            return null;
        }
        return Boolean.valueOf(num.intValue() == 1);
    }

    @Nullable
    public final Boolean t() {
        Integer num = this.write_history;
        if (num == null) {
            return null;
        }
        return Boolean.valueOf(num.intValue() == 1);
    }

    @Nullable
    public final Boolean f() {
        Integer num = this.add_shortcut;
        if (num == null) {
            return null;
        }
        return Boolean.valueOf(num.intValue() == 1);
    }

    @Override // ru.mail.libverify.j.c
    @NonNull
    public final String toString() {
        return super.toString();
    }
}
