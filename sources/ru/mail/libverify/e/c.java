package ru.mail.libverify.e;

import android.os.Message;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import ru.mail.libverify.api.CommonContext;
import ru.mail.libverify.gcm.ServerInfo;
import ru.mail.libverify.j.f;
import ru.mail.libverify.storage.DecryptionError;
import ru.mail.verify.core.api.ApiPlugin;
import ru.mail.verify.core.platform.TimeProvider;
import ru.mail.verify.core.storage.BroadcastManager;
import ru.mail.verify.core.utils.DebugUtils;
import ru.mail.verify.core.utils.FileLog;
import ru.mail.verify.core.utils.components.BusMessageType;
import ru.mail.verify.core.utils.components.MessageBus;
import ru.mail.verify.core.utils.components.MessageBusUtils;
import ru.mail.verify.core.utils.components.MessageHandler;
import ru.mail.verify.core.utils.json.JsonParseException;
import ru.mail.verify.core.utils.json.JsonParser;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/e/c.class */
public final class c implements MessageHandler, ApiPlugin {
    private final CommonContext a;
    private final d b;
    private final ru.mail.libverify.e.a c;
    private final MessageBus d;
    private final TimeProvider e;
    @Nullable
    private f g;
    private EnumC0006c f = EnumC0006c.NOT_ACTIVE;
    private final AtomicInteger h = new AtomicInteger(0);

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/e/c$a.class */
    public static /* synthetic */ class a {
        static final /* synthetic */ int[] a;
        static final /* synthetic */ int[] b;

        static {
            int[] iArr = new int[BusMessageType.values().length];
            b = iArr;
            try {
                try {
                    try {
                        iArr[BusMessageType.FETCHER_EXECUTOR_MESSAGE_RECEIVED.ordinal()] = 1;
                    } catch (NoSuchFieldError unused) {
                    }
                } catch (NoSuchFieldError unused2) {
                }
            } catch (NoSuchFieldError unused3) {
            }
            try {
                try {
                    try {
                        try {
                            b[BusMessageType.FETCHER_EXECUTOR_SERVER_INFO_RECEIVED.ordinal()] = 2;
                        } catch (NoSuchFieldError unused4) {
                        }
                    } catch (NoSuchFieldError unused5) {
                    }
                } catch (NoSuchFieldError unused6) {
                }
            } catch (NoSuchFieldError unused7) {
            }
            try {
                try {
                    try {
                        try {
                            b[BusMessageType.FETCHER_EXECUTOR_FETCHER_STOPPED.ordinal()] = 3;
                        } catch (NoSuchFieldError unused8) {
                        }
                    } catch (NoSuchFieldError unused9) {
                    }
                } catch (NoSuchFieldError unused10) {
                }
            } catch (NoSuchFieldError unused11) {
            }
            try {
                try {
                    try {
                        try {
                            b[BusMessageType.FETCHER_EXECUTOR_FETCHER_STARTED.ordinal()] = 4;
                        } catch (NoSuchFieldError unused12) {
                        }
                    } catch (NoSuchFieldError unused13) {
                    }
                } catch (NoSuchFieldError unused14) {
                }
            } catch (NoSuchFieldError unused15) {
            }
            try {
                try {
                    try {
                        try {
                            b[BusMessageType.FETCHER_EXECUTOR_UPDATE_CACHE_HEADERS.ordinal()] = 5;
                        } catch (NoSuchFieldError unused16) {
                        }
                    } catch (NoSuchFieldError unused17) {
                    }
                } catch (NoSuchFieldError unused18) {
                }
            } catch (NoSuchFieldError unused19) {
            }
            try {
                try {
                    try {
                        try {
                            b[BusMessageType.FETCHER_MANAGER_UPDATE_FETCHER_INFO_INTERNAL.ordinal()] = 6;
                        } catch (NoSuchFieldError unused20) {
                        }
                    } catch (NoSuchFieldError unused21) {
                    }
                } catch (NoSuchFieldError unused22) {
                }
            } catch (NoSuchFieldError unused23) {
            }
            try {
                try {
                    try {
                        try {
                            b[BusMessageType.FETCHER_EXECUTOR_UPDATE_FETCHER_INFO.ordinal()] = 7;
                        } catch (NoSuchFieldError unused24) {
                        }
                    } catch (NoSuchFieldError unused25) {
                    }
                } catch (NoSuchFieldError unused26) {
                }
            } catch (NoSuchFieldError unused27) {
            }
            try {
                try {
                    try {
                        try {
                            b[BusMessageType.API_RESET.ordinal()] = 8;
                        } catch (NoSuchFieldError unused28) {
                        }
                    } catch (NoSuchFieldError unused29) {
                    }
                } catch (NoSuchFieldError unused30) {
                }
            } catch (NoSuchFieldError unused31) {
            }
            try {
                try {
                    try {
                        try {
                            b[BusMessageType.VERIFY_API_RESET.ordinal()] = 9;
                        } catch (NoSuchFieldError unused32) {
                        }
                    } catch (NoSuchFieldError unused33) {
                    }
                } catch (NoSuchFieldError unused34) {
                }
            } catch (NoSuchFieldError unused35) {
            }
            int[] iArr2 = new int[EnumC0006c.values().length];
            a = iArr2;
            try {
                try {
                    try {
                        iArr2[EnumC0006c.NOT_ACTIVE.ordinal()] = 1;
                    } catch (NoSuchFieldError unused36) {
                    }
                } catch (NoSuchFieldError unused37) {
                }
            } catch (NoSuchFieldError unused38) {
            }
            try {
                try {
                    try {
                        try {
                            a[EnumC0006c.SUSPENDED_TEMPORARY.ordinal()] = 2;
                        } catch (NoSuchFieldError unused39) {
                        }
                    } catch (NoSuchFieldError unused40) {
                    }
                } catch (NoSuchFieldError unused41) {
                }
            } catch (NoSuchFieldError unused42) {
            }
            try {
                try {
                    try {
                        try {
                            a[EnumC0006c.SUSPENDED_OTHER_SERVICE.ordinal()] = 3;
                        } catch (NoSuchFieldError unused43) {
                        }
                    } catch (NoSuchFieldError unused44) {
                    }
                } catch (NoSuchFieldError unused45) {
                }
            } catch (NoSuchFieldError unused46) {
            }
            try {
                try {
                    try {
                        try {
                            a[EnumC0006c.ACTIVE.ordinal()] = 4;
                        } catch (NoSuchFieldError unused47) {
                        }
                    } catch (NoSuchFieldError unused48) {
                    }
                } catch (NoSuchFieldError unused49) {
                }
            } catch (NoSuchFieldError unused50) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/e/c$b.class */
    public class b implements ru.mail.libverify.e.b {
        private b() {
        }

        @NonNull
        public final d a() {
            return c.this.b;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v0, types: [ru.mail.libverify.e.c$b] */
        /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
        /* JADX WARN: Type inference failed for: r0v5, types: [boolean] */
        public final boolean f() {
            ?? r0 = this;
            synchronized (c.this) {
                r0 = c.this.d() && c.this.f != EnumC0006c.SUSPENDED_OTHER_SERVICE;
            }
            return r0;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final long b() {
            synchronized (c.this) {
                c.this.e();
                if (c.this.d()) {
                    long localTime = c.this.e.getLocalTime() - c.this.g.e();
                    if (localTime > c.this.g.d()) {
                        return 0L;
                    }
                    return c.this.g.d() - localTime;
                }
                return 0L;
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v0, types: [ru.mail.libverify.e.c$b] */
        /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
        /* JADX WARN: Type inference failed for: r0v5, types: [java.lang.String] */
        @Nullable
        public final String c() {
            ?? r0 = this;
            synchronized (c.this) {
                c.this.e();
                r0 = c.this.d() ? c.this.g.f() : null;
            }
            return r0;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final long e() {
            synchronized (c.this) {
                c.this.e();
                if (!c.this.d()) {
                    FileLog.d("FetcherManager", "no valid fetcher info to get timestamp");
                    return 0L;
                }
                f fVar = c.this.g;
                if (fVar != null && fVar.b() != 0) {
                    return c.this.g.b();
                }
                FileLog.d("FetcherManager", "no last modified timestamp, use current time");
                return c.this.g.e();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Nullable
        public final String d() {
            synchronized (c.this) {
                c.this.e();
                if (c.this.d()) {
                    return c.this.g.a();
                }
                FileLog.d("FetcherManager", "no valid fetcher info to get ETag");
                return null;
            }
        }

        /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable, ru.mail.libverify.e.c] */
        public final void a(@Nullable String str) {
            synchronized (c.this) {
                c.this.g.a(str);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v0, types: [ru.mail.libverify.e.c$b] */
        /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
        /* JADX WARN: Type inference failed for: r0v4 */
        public final void a(Long l) {
            ?? r0 = this;
            c cVar = c.this;
            synchronized (cVar) {
                f fVar = c.this.g;
                if (fVar != null) {
                    fVar.a(l);
                }
                r0 = cVar;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: ru.mail.libverify.e.c$c  reason: collision with other inner class name */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/e/c$c.class */
    public enum EnumC0006c {
        NOT_ACTIVE,
        SUSPENDED_TEMPORARY,
        SUSPENDED_OTHER_SERVICE,
        ACTIVE
    }

    public c(@NonNull CommonContext commonContext, @NonNull d dVar) {
        this.a = commonContext;
        this.b = dVar;
        this.d = commonContext.getBus();
        this.c = new ru.mail.libverify.e.a(commonContext.getConfig(), new b(), commonContext);
        this.e = commonContext.getConfig().getTimeProvider();
    }

    private synchronized boolean d() {
        e();
        f fVar = this.g;
        return fVar != null && fVar.c() == f.b.ENABLED && !TextUtils.isEmpty(this.g.f()) && this.g.d() >= 0;
    }

    private void a(@Nullable String str, boolean z) {
        EnumC0006c enumC0006c = EnumC0006c.ACTIVE;
        boolean a2 = a(enumC0006c, str, z);
        FileLog.v("FetcherManager", "activate fetcher, publish = %s, package = %s", Boolean.valueOf(a2), str);
        if (this.f == enumC0006c) {
            this.d.post(MessageBusUtils.createOneArg(BusMessageType.FETCHER_MANAGER_FETCHER_STARTED, Boolean.valueOf(a2)));
        } else {
            this.d.post(MessageBusUtils.createOneArg(BusMessageType.FETCHER_MANAGER_FETCHER_STOPPED, Boolean.valueOf(a2)));
        }
    }

    private void e() {
        if (this.g != null) {
            return;
        }
        String value = this.a.getSettings().getValue("fetcher_manager_info");
        if (TextUtils.isEmpty(value)) {
            return;
        }
        String value2 = this.a.getSettings().getValue("fetcher_state");
        if (!TextUtils.isEmpty(value2)) {
            this.f = EnumC0006c.valueOf(value2);
        }
        try {
            f fVar = (f) JsonParser.fromJson(value, f.class);
            this.g = fVar;
            Object[] objArr = new Object[2];
            objArr[0] = fVar;
            objArr[1] = this.f;
            FileLog.v("FetcherManager", "fetcher info loaded %s state %s", objArr);
        } catch (JsonParseException e) {
            this.f = EnumC0006c.NOT_ACTIVE;
            this.a.getSettings().removeValue("fetcher_manager_info").removeValue("fetcher_state").commit();
            DebugUtils.safeThrow("FetcherManager", "failed to load fetcher state", e);
        }
    }

    @Override // ru.mail.verify.core.api.ApiPlugin
    public final void initialize() {
        this.d.register(Arrays.asList(BusMessageType.FETCHER_EXECUTOR_MESSAGE_RECEIVED, BusMessageType.FETCHER_EXECUTOR_SERVER_INFO_RECEIVED, BusMessageType.FETCHER_EXECUTOR_FETCHER_STOPPED, BusMessageType.FETCHER_EXECUTOR_FETCHER_STARTED, BusMessageType.FETCHER_EXECUTOR_UPDATE_CACHE_HEADERS, BusMessageType.FETCHER_MANAGER_UPDATE_FETCHER_INFO_INTERNAL, BusMessageType.FETCHER_EXECUTOR_UPDATE_FETCHER_INFO, BusMessageType.API_RESET, BusMessageType.VERIFY_API_RESET), this);
        h();
    }

    public final void g() {
        FileLog.v("FetcherManager", "reset and stop fetcher");
        c((f) null);
        a(null, false);
    }

    public final synchronized void h() {
        FileLog.v("FetcherManager", "run fetcher with check");
        e();
        c(this.g);
        a(null, false);
    }

    public final void f() {
        FileLog.v("FetcherManager", "pause fetcher");
        this.d.post(MessageBusUtils.createOneArg(BusMessageType.FETCHER_MANAGER_FETCHER_STOPPED, Boolean.valueOf(a(EnumC0006c.SUSPENDED_TEMPORARY))));
    }

    @Nullable
    public final Long c() {
        f fVar = this.g;
        if (fVar == null) {
            return null;
        }
        return Long.valueOf(fVar.b());
    }

    @Nullable
    public final String b() {
        f fVar = this.g;
        if (fVar == null) {
            return null;
        }
        return fVar.a();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v33, types: [java.lang.Long] */
    /* JADX WARN: Type inference failed for: r0v34, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v39, types: [java.lang.Object[]] */
    /* JADX WARN: Type inference failed for: r0v42, types: [ru.mail.libverify.j.f] */
    /* JADX WARN: Type inference failed for: r0v46 */
    /* JADX WARN: Type inference failed for: r0v62 */
    /* JADX WARN: Type inference failed for: r0v63 */
    @Override // ru.mail.verify.core.utils.components.MessageHandler
    public final boolean handleMessage(@NonNull Message message) {
        switch (a.b[MessageBusUtils.getType(message, "FetcherManager").ordinal()]) {
            case BroadcastManager.FLAG_NETWORK_RECEIVER /* 1 */:
                String str = (String) MessageBusUtils.getArg(message, String.class, 0);
                String str2 = (String) MessageBusUtils.getArg(message, String.class, 1);
                try {
                    FileLog.v("FetcherManager", "message received from fetcher");
                    this.d.post(MessageBusUtils.createOneArg(BusMessageType.FETCHER_MANAGER_MESSAGE_RECEIVED, this.a.getConfig().decryptServerMessage(str, str2)));
                    return true;
                } catch (DecryptionError e) {
                    DebugUtils.safeThrow("FetcherManager", "fetcher message decryption error", e);
                    g();
                    return true;
                } catch (Exception e2) {
                    DebugUtils.safeThrow("FetcherManager", "unexpected error during fetcher message decryption", e2);
                    g();
                    return true;
                }
            case 2:
                FileLog.v("FetcherManager", "server info received from fetcher");
                this.d.post(MessageBusUtils.createOneArg(BusMessageType.FETCHER_MANAGER_SERVER_INFO_RECEIVED, MessageBusUtils.getArg(message, ServerInfo.class)));
                return true;
            case 3:
                this.d.post(MessageBusUtils.createOneArg(BusMessageType.FETCHER_MANAGER_FETCHER_STOPPED, Boolean.valueOf(a(EnumC0006c.SUSPENDED_TEMPORARY))));
                return true;
            case 4:
                this.d.post(MessageBusUtils.createOneArg(BusMessageType.FETCHER_MANAGER_FETCHER_STARTED, Boolean.valueOf(a(EnumC0006c.ACTIVE))));
                return true;
            case 5:
                ?? r0 = (Long) MessageBusUtils.getArg(message, Long.class, 0);
                synchronized (this) {
                    if (r0 != 0) {
                        e();
                        if (this.g == null) {
                            FileLog.e("FetcherManager", "failed to update last modified time (there is no saved info)");
                            r0 = "FetcherManager";
                        } else {
                            ?? r02 = new Object[1];
                            r02[0] = r0;
                            FileLog.d("FetcherManager", "update fetcher info last modified %d", (Object[]) r02);
                            this.g.a(r0);
                            c cVar = this;
                            cVar.a(this.g);
                            r0 = cVar;
                        }
                    }
                }
                d((String) MessageBusUtils.getArg(message, String.class, 1));
                return true;
            case 6:
                d((f) MessageBusUtils.getNullableArg(message, f.class));
                return true;
            case 7:
                f fVar = (f) MessageBusUtils.getNullableArg(message, f.class);
                if (fVar == null) {
                    FileLog.d("FetcherManager", "empty fetcher info has been skipped");
                    return true;
                }
                c(fVar);
                return true;
            case 8:
            case 9:
                g();
                return true;
            default:
                return false;
        }
    }

    private synchronized void d(@Nullable String str) {
        if (str == null) {
            return;
        }
        e();
        if (this.g == null) {
            FileLog.e("FetcherManager", "failed to update last eTag (there is no saved info)");
            return;
        }
        FileLog.d("FetcherManager", "update fetcher info eTag %s", str);
        this.g.a(str);
        a(this.g);
    }

    public final synchronized void a() {
        FileLog.v("FetcherManager", "check and activate fetcher");
        a(null, true);
    }

    public final void c(@Nullable String str) {
        f fVar = this.g;
        if (fVar != null) {
            fVar.a(str);
        }
    }

    public final void b(@NonNull String str) {
        if (TextUtils.equals(str, this.a.getConfig().getContext().getPackageName())) {
            FileLog.e("FetcherManager", "package name %s matches with local", str);
            return;
        }
        FileLog.v("FetcherManager", "remote fetcher from %s stopped", str);
        a(str, false);
    }

    private synchronized void d(@Nullable f fVar) {
        FileLog.v("FetcherManager", "update fetcher info started");
        if (c(fVar)) {
            boolean a2 = a(EnumC0006c.NOT_ACTIVE);
            FileLog.v("FetcherManager", "deactivate fetcher, publish = %s", Boolean.valueOf(a2));
            this.d.post(MessageBusUtils.createOneArg(BusMessageType.FETCHER_MANAGER_FETCHER_STOPPED, Boolean.valueOf(a2)));
        }
        a(null, true);
        FileLog.v("FetcherManager", "update fetcher info completed");
    }

    private synchronized boolean c(@Nullable f fVar) {
        e();
        f fVar2 = this.g;
        if (fVar2 != null && fVar != null) {
            if (fVar.b() == 0) {
                fVar.a(Long.valueOf(fVar2.b()));
            }
            if (fVar.a() == null) {
                fVar.a(fVar2.a());
            }
        }
        this.g = fVar;
        a(fVar);
        FileLog.v("FetcherManager", "fetcher info updated %s -> %s", fVar2, this.g);
        return (fVar2 == null || fVar2.equals(this.g)) ? false : true;
    }

    public final void a(@NonNull String str) {
        if (TextUtils.equals(str, this.a.getConfig().getContext().getPackageName())) {
            FileLog.e("FetcherManager", "package name %s matches with local", str);
            return;
        }
        FileLog.v("FetcherManager", "remote fetcher from %s started", str);
        boolean a2 = a(EnumC0006c.SUSPENDED_OTHER_SERVICE, str, false);
        FileLog.v("FetcherManager", "activate fetcher, publish = %s, package = %s", Boolean.valueOf(a2), str);
        this.d.post(MessageBusUtils.createOneArg(BusMessageType.FETCHER_MANAGER_FETCHER_STOPPED, Boolean.valueOf(a2)));
    }

    public final void b(@Nullable f fVar) {
        if (fVar == null) {
            FileLog.d("FetcherManager", "empty fetcher info has been skipped");
        } else {
            this.a.getDispatcher().sendMessage(MessageBusUtils.createOneArg(BusMessageType.FETCHER_MANAGER_UPDATE_FETCHER_INFO_INTERNAL, fVar));
        }
    }

    private synchronized boolean a(EnumC0006c enumC0006c) {
        return a(enumC0006c, null, false);
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0074, code lost:
        if (r0 != ru.mail.libverify.e.c.EnumC0006c.NOT_ACTIVE) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x00cd, code lost:
        if (r8.h.get() > 0) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00d0, code lost:
        r8.h.getAndDecrement();
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0115, code lost:
        if (r11 == false) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0174, code lost:
        if (r8.h.get() > 0) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x01af, code lost:
        if (r0 != ru.mail.libverify.e.c.EnumC0006c.NOT_ACTIVE) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x01e4, code lost:
        if (r8.h.get() > 0) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x021f, code lost:
        if (r0 != ru.mail.libverify.e.c.EnumC0006c.NOT_ACTIVE) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x025a, code lost:
        if (r0 != ru.mail.libverify.e.c.EnumC0006c.NOT_ACTIVE) goto L19;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private synchronized boolean a(ru.mail.libverify.e.c.EnumC0006c r9, @androidx.annotation.Nullable java.lang.String r10, boolean r11) {
        /*
            Method dump skipped, instructions count: 663
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: ru.mail.libverify.e.c.a(ru.mail.libverify.e.c$c, java.lang.String, boolean):boolean");
    }

    /*  JADX ERROR: JadxRuntimeException in pass: BlockProcessor
        jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:27:0x0060
        	at jadx.core.dex.visitors.blocks.BlockProcessor.checkForUnreachableBlocks(BlockProcessor.java:81)
        	at jadx.core.dex.visitors.blocks.BlockProcessor.processBlocksTree(BlockProcessor.java:47)
        	at jadx.core.dex.visitors.blocks.BlockProcessor.visit(BlockProcessor.java:39)
        */
    private void a(@androidx.annotation.Nullable ru.mail.libverify.j.f r5) {
        /*
            r4 = this;
            r0 = r5
            if (r0 != 0) goto L23
            r0 = r4
            ru.mail.libverify.api.CommonContext r0 = r0.a
            ru.mail.libverify.platform.storage.KeyValueStorage r0 = r0.getSettings()
            java.lang.String r1 = "fetcher_manager_info"
            ru.mail.libverify.platform.storage.KeyValueStorage r0 = r0.removeValue(r1)
            java.lang.String r1 = "fetcher_state"
            ru.mail.libverify.platform.storage.KeyValueStorage r0 = r0.removeValue(r1)
            r0.commit()
            goto L7e
        L23:
            r0 = r4
            ru.mail.libverify.api.CommonContext r0 = r0.a     // Catch: ru.mail.verify.core.utils.json.JsonParseException -> L74
            ru.mail.libverify.platform.storage.KeyValueStorage r0 = r0.getSettings()     // Catch: ru.mail.verify.core.utils.json.JsonParseException -> L70 ru.mail.verify.core.utils.json.JsonParseException -> L74
            r1 = r5
            java.lang.String r2 = "fetcher_manager_info"
            r5 = r2
            java.lang.String r1 = ru.mail.verify.core.utils.json.JsonParser.toJson(r1)     // Catch: ru.mail.verify.core.utils.json.JsonParseException -> L68
            r2 = r5
            r3 = r1; r1 = r2; r2 = r3;      // Catch: ru.mail.verify.core.utils.json.JsonParseException -> L64
            ru.mail.libverify.platform.storage.KeyValueStorage r0 = r0.putValue(r1, r2)     // Catch: ru.mail.verify.core.utils.json.JsonParseException -> L64
            java.lang.String r1 = "fetcher_state"
            r2 = r4
            ru.mail.libverify.e.c$c r2 = r2.f     // Catch: ru.mail.verify.core.utils.json.JsonParseException -> L5c
            java.lang.String r2 = r2.toString()     // Catch: ru.mail.verify.core.utils.json.JsonParseException -> L58 ru.mail.verify.core.utils.json.JsonParseException -> L5c
            ru.mail.libverify.platform.storage.KeyValueStorage r0 = r0.putValue(r1, r2)     // Catch: ru.mail.verify.core.utils.json.JsonParseException -> L54 ru.mail.verify.core.utils.json.JsonParseException -> L58
            r0.commit()     // Catch: ru.mail.verify.core.utils.json.JsonParseException -> L50 ru.mail.verify.core.utils.json.JsonParseException -> L54
            goto L7e
        L50:
            r4 = move-exception
            goto L75
        L54:
            r4 = move-exception
            goto L75
        L58:
            r4 = move-exception
            goto L75
        L5c:
            r4 = move-exception
            goto L75
        L60:
            r4 = move-exception
            goto L75
        L64:
            r4 = move-exception
            goto L75
        L68:
            r4 = move-exception
            goto L75
        L6c:
            r4 = move-exception
            goto L75
        L70:
            r4 = move-exception
            goto L75
        L74:
            r4 = move-exception
        L75:
            java.lang.String r0 = "FetcherManager"
            java.lang.String r1 = "failed to save fetcher info"
            r2 = r4
            ru.mail.verify.core.utils.DebugUtils.safeThrow(r0, r1, r2)
        L7e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: ru.mail.libverify.e.c.a(ru.mail.libverify.j.f):void");
    }

    public final void a(@Nullable Long l) {
        f fVar = this.g;
        if (fVar != null) {
            fVar.a(l);
        }
    }
}
