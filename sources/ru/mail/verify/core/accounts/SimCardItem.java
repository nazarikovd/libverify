package ru.mail.verify.core.accounts;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/accounts/SimCardItem.class */
public class SimCardItem {
    String a;
    String b;
    String c;
    String d;
    String e;
    boolean f;
    boolean g;
    boolean h = false;
    String i;
    String j;
    String k;
    String l;
    String m;
    Integer n;

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/accounts/SimCardItem$Builder.class */
    public static class Builder {
        private String a;
        private String b;
        private String c;
        private String d;
        private String e;
        private boolean f;
        private boolean g;
        private boolean h = false;
        private String i;
        private String j;
        private String k;
        private String l;
        private String m;
        private Integer n;

        @NonNull
        public SimCardItem build() {
            SimCardItem simCardItem = new SimCardItem();
            simCardItem.a = this.a;
            simCardItem.b = this.b;
            simCardItem.c = this.c;
            simCardItem.d = this.d;
            simCardItem.e = this.e;
            simCardItem.f = this.f;
            simCardItem.g = this.g;
            simCardItem.h = this.h;
            simCardItem.i = this.i;
            simCardItem.j = this.j;
            simCardItem.k = this.k;
            simCardItem.l = this.l;
            simCardItem.m = this.m;
            simCardItem.n = this.n;
            return simCardItem;
        }

        @NonNull
        public Builder setSubscriberId(@Nullable String subscriberId) {
            return this;
        }

        @NonNull
        public Builder setImsi(@Nullable String imsi) {
            this.a = imsi;
            return this;
        }

        @NonNull
        public Builder setImei(@Nullable String imei) {
            this.b = imei;
            return this;
        }

        @NonNull
        public Builder setSimCountryIso(@Nullable String simCountryIso) {
            this.c = simCountryIso;
            return this;
        }

        @NonNull
        public Builder setSimPhoneNumber(@Nullable String simPhoneNumber) {
            this.d = simPhoneNumber;
            return this;
        }

        @NonNull
        public Builder setSimState(@Nullable String simState) {
            this.e = simState;
            return this;
        }

        @NonNull
        public Builder setReady(boolean ready) {
            this.f = ready;
            return this;
        }

        @NonNull
        public Builder setNetworkRoaming(boolean networkRoaming) {
            this.g = networkRoaming;
            return this;
        }

        @NonNull
        public Builder setRoamingDataAllowed(boolean roamingDataAllowed) {
            this.h = roamingDataAllowed;
            return this;
        }

        @NonNull
        public Builder setOperatorName(@Nullable String operatorName) {
            this.i = operatorName;
            return this;
        }

        @NonNull
        public Builder setOperator(@Nullable String operator) {
            this.j = operator;
            return this;
        }

        @NonNull
        public Builder setNetworkOperatorName(@Nullable String networkOperatorName) {
            this.k = networkOperatorName;
            return this;
        }

        @NonNull
        public Builder setNetworkOperator(@Nullable String networkOperator) {
            this.l = networkOperator;
            return this;
        }

        @NonNull
        public Builder setNetworkCountryIso(@Nullable String networkCountryIso) {
            this.m = networkCountryIso;
            return this;
        }

        @NonNull
        public Builder setSlotIndex(@Nullable Integer slotIndex) {
            this.n = slotIndex;
            return this;
        }
    }

    @Nullable
    public String getSimPhoneNumber() {
        return this.d;
    }

    @Nullable
    public String getSimCountryIso() {
        return this.c;
    }

    @Nullable
    public String getOperatorName() {
        return this.i;
    }

    @Nullable
    public String getOperatorMCC() {
        String str = this.j;
        return (TextUtils.isEmpty(str) || str.length() < 3) ? null : str.substring(0, 3);
    }

    @Nullable
    public String getOperatorMNC() {
        String str = this.j;
        return (TextUtils.isEmpty(str) || str.length() < 5) ? null : str.substring(3);
    }

    @Nullable
    public String getNetworkOperatorName() {
        return this.k;
    }

    @Nullable
    public Integer getSlotIndex() {
        return this.n;
    }

    @Nullable
    public String getNetworkMCC() {
        String str = this.l;
        return (TextUtils.isEmpty(str) || str.length() < 3) ? null : str.substring(0, 3);
    }

    @Nullable
    public String getNetworkMNC() {
        String str = this.l;
        return (TextUtils.isEmpty(str) || str.length() < 5) ? null : str.substring(3);
    }

    @Nullable
    public String getNetworkCountryIso() {
        return this.m;
    }

    @Nullable
    public String getImsi() {
        return this.a;
    }

    @Nullable
    public String getImei() {
        return this.b;
    }

    @Nullable
    public String getSimState() {
        return this.e;
    }

    public boolean isNetworkRoaming() {
        return this.g;
    }

    public String toString() {
        return "SimCardItem{simCountryIso='" + this.c + "', simPhoneNumber='" + this.d + "', simState='" + this.e + "', isNetworkRoaming=" + this.g + ", isRoamingDataAllowed=" + this.h + ", operatorName='" + this.i + "', operator='" + this.j + "', networkOperatorName='" + this.k + "', networkOperator='" + this.l + "', networkCountryIso='" + this.m + "'}";
    }

    @Nullable
    public String getNetworkOperator() {
        return this.l;
    }

    @Nullable
    public String getOperator() {
        return this.j;
    }
}
