package ru.mail.libverify.d;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.mail.libverify.i.p;
import ru.mail.verify.core.storage.InstanceConfig;
import ru.mail.verify.core.utils.Gsonable;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/d/b.class */
public abstract class b implements Gsonable {
    @NotNull
    public static final c Companion = new c(0);
    @NotNull
    private final p.b route;

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/d/b$a.class */
    public static final class a extends b {
        @NotNull
        public static final C0003a Companion = new C0003a(0);
        private final int fallbackTimeout;
        private final int totalFallbackTimeout;
        @NotNull
        private final String phone;
        private final boolean isDisableDirectCall;
        private final boolean isIvr;

        /* renamed from: ru.mail.libverify.d.b$a$a  reason: collision with other inner class name */
        /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/d/b$a$a.class */
        public static final class C0003a {
            private C0003a() {
            }

            public /* synthetic */ C0003a(int i) {
                this();
            }
        }

        public a() {
            this(0, 0, null, false, false, 31, null);
        }

        public final int a() {
            return this.fallbackTimeout;
        }

        public final int c() {
            return this.totalFallbackTimeout;
        }

        @NotNull
        public final String b() {
            return this.phone;
        }

        public final boolean d() {
            return this.isDisableDirectCall;
        }

        public final boolean e() {
            return this.isIvr;
        }

        @NotNull
        public final String toString() {
            return "CallInInfo(fallbackTimeout=" + this.fallbackTimeout + ", totalFallbackTimeout=" + this.totalFallbackTimeout + ", phone=" + this.phone + ", isDisableDirectCall=" + this.isDisableDirectCall + ", isIvr=" + this.isIvr + ')';
        }

        public final int hashCode() {
            int hashCode = (this.phone.hashCode() + ((Integer.hashCode(this.totalFallbackTimeout) + (Integer.hashCode(this.fallbackTimeout) * 31)) * 31)) * 31;
            boolean z = this.isDisableDirectCall;
            int i = z ? 1 : 0;
            if (z) {
                i = 1;
            }
            int i2 = (hashCode + i) * 31;
            boolean z2 = this.isIvr;
            int i3 = z2 ? 1 : 0;
            if (z2) {
                i3 = 1;
            }
            return i2 + i3;
        }

        public final boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof a) {
                a aVar = (a) obj;
                return this.fallbackTimeout == aVar.fallbackTimeout && this.totalFallbackTimeout == aVar.totalFallbackTimeout && Intrinsics.areEqual(this.phone, aVar.phone) && this.isDisableDirectCall == aVar.isDisableDirectCall && this.isIvr == aVar.isIvr;
            }
            return false;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public a(int i, int i2, @NotNull String str, boolean z, boolean z2) {
            super(p.b.CALLIN, null);
            Intrinsics.checkNotNullParameter(str, InstanceConfig.DEVICE_TYPE_PHONE);
            this.fallbackTimeout = i;
            this.totalFallbackTimeout = i2;
            this.phone = str;
            this.isDisableDirectCall = z;
            this.isIvr = z2;
        }

        public /* synthetic */ a(int i, int i2, String str, boolean z, boolean z2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
            this((i3 & 1) != 0 ? 0 : i, (i3 & 2) != 0 ? 0 : i2, (i3 & 4) != 0 ? "" : str, (i3 & 8) != 0 ? false : z, (i3 & 16) != 0 ? false : z2);
        }
    }

    /* renamed from: ru.mail.libverify.d.b$b  reason: collision with other inner class name */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/d/b$b.class */
    public static final class C0004b extends b {
        @NotNull
        public static final a Companion = new a(0);
        private final int fallbackTimeout;
        @Nullable
        private final String fragmentStart;
        @Nullable
        private final String description;
        @Nullable
        private final String descriptionOptional;

        /* renamed from: ru.mail.libverify.d.b$b$a */
        /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/d/b$b$a.class */
        public static final class a {
            private a() {
            }

            public /* synthetic */ a(int i) {
                this();
            }
        }

        public C0004b() {
            this(0, null, null, null, 15, null);
        }

        public final int c() {
            return this.fallbackTimeout;
        }

        @Nullable
        public final String d() {
            return this.fragmentStart;
        }

        @Nullable
        public final String a() {
            return this.description;
        }

        @Nullable
        public final String b() {
            return this.descriptionOptional;
        }

        @NotNull
        public final String toString() {
            return "CallUiInfo(fallbackTimeout=" + this.fallbackTimeout + ", fragmentStart=" + this.fragmentStart + ", description=" + this.description + ", descriptionOptional=" + this.descriptionOptional + ')';
        }

        public final int hashCode() {
            int hashCode = Integer.hashCode(this.fallbackTimeout) * 31;
            String str = this.fragmentStart;
            int hashCode2 = (hashCode + (str == null ? 0 : str.hashCode())) * 31;
            String str2 = this.description;
            int hashCode3 = (hashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
            String str3 = this.descriptionOptional;
            return hashCode3 + (str3 == null ? 0 : str3.hashCode());
        }

        public final boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof C0004b) {
                C0004b c0004b = (C0004b) obj;
                return this.fallbackTimeout == c0004b.fallbackTimeout && Intrinsics.areEqual(this.fragmentStart, c0004b.fragmentStart) && Intrinsics.areEqual(this.description, c0004b.description) && Intrinsics.areEqual(this.descriptionOptional, c0004b.descriptionOptional);
            }
            return false;
        }

        public C0004b(int i, @Nullable String str, @Nullable String str2, @Nullable String str3) {
            super(p.b.CALLUI, null);
            this.fallbackTimeout = i;
            this.fragmentStart = str;
            this.description = str2;
            this.descriptionOptional = str3;
        }

        public /* synthetic */ C0004b(int i, String str, String str2, String str3, int i2, DefaultConstructorMarker defaultConstructorMarker) {
            this((i2 & 1) != 0 ? 0 : i, (i2 & 2) != 0 ? null : str, (i2 & 4) != 0 ? null : str2, (i2 & 8) != 0 ? null : str3);
        }
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/d/b$c.class */
    public static final class c {

        /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/d/b$c$a.class */
        public /* synthetic */ class a {
            public static final /* synthetic */ int[] a;

            static {
                int[] iArr = new int[p.b.values().length];
                try {
                    iArr[p.b.SMS.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[p.b.CALLUI.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr[p.b.CALLIN.ordinal()] = 3;
                } catch (NoSuchFieldError unused3) {
                }
                a = iArr;
            }
        }

        private c() {
        }

        public /* synthetic */ c(int i) {
            this();
        }
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/d/b$d.class */
    public static final class d extends b {
        @NotNull
        private String url;
        private int maxRedirects;
        private final int fallbackTimeout;

        public d() {
            this(null, 0, 0, 7, null);
        }

        public final boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (Intrinsics.areEqual(d.class, obj != null ? obj.getClass() : null)) {
                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type ru.mail.libverify.api.model.RouteInfo.MobileIdInfo");
                return Intrinsics.areEqual(this.url, ((d) obj).url);
            }
            return false;
        }

        public final int hashCode() {
            return this.url.hashCode();
        }

        @NotNull
        public final String toString() {
            return "MobileIdInfo(url=" + this.url + ", maxRedirects=" + this.maxRedirects + ", fallbackTimeout=" + this.fallbackTimeout + ')';
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public d(@NotNull String str, int i, int i2) {
            super(p.b.MOBILEID, null);
            Intrinsics.checkNotNullParameter(str, "url");
            this.url = str;
            this.maxRedirects = i;
            this.fallbackTimeout = i2;
        }

        public /* synthetic */ d(String str, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
            this((i3 & 1) != 0 ? "" : str, (i3 & 2) != 0 ? 0 : i, (i3 & 4) != 0 ? 5000 : i2);
        }
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/d/b$e.class */
    public static final class e extends b {
        @NotNull
        public static final a Companion = new a(0);
        @Nullable
        private final Integer fallbackTimeout;

        /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/d/b$e$a.class */
        public static final class a {
            private a() {
            }

            public /* synthetic */ a(int i) {
                this();
            }
        }

        public e() {
            this(null, 1, null);
        }

        @Nullable
        public final Integer a() {
            return this.fallbackTimeout;
        }

        @NotNull
        public final String toString() {
            return "SmsInfo(fallbackTimeout=" + this.fallbackTimeout + ')';
        }

        public final int hashCode() {
            Integer num = this.fallbackTimeout;
            if (num == null) {
                return 0;
            }
            return num.hashCode();
        }

        public final boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof e) && Intrinsics.areEqual(this.fallbackTimeout, ((e) obj).fallbackTimeout);
        }

        public e(@Nullable Integer num) {
            super(p.b.SMS, null);
            this.fallbackTimeout = num;
        }

        public /* synthetic */ e(Integer num, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? null : num);
        }
    }

    private b(p.b bVar) {
        this.route = bVar;
    }

    public /* synthetic */ b(p.b bVar, DefaultConstructorMarker defaultConstructorMarker) {
        this(bVar);
    }
}
