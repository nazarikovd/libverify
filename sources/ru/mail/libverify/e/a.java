package ru.mail.libverify.e;

import androidx.annotation.NonNull;
import java.util.concurrent.Future;
import ru.mail.libverify.api.CommonContext;
import ru.mail.libverify.e.c;
import ru.mail.libverify.storage.InstanceConfig;
import ru.mail.verify.core.utils.FileLog;
import ru.mail.verify.core.utils.components.MessageBus;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/e/a.class */
public final class a {
    private final InstanceConfig a;
    private final b b;
    private final Object c = new Object();
    private final Runnable d = new RunnableC0005a();
    private final MessageBus e;
    private Future f;

    /* renamed from: ru.mail.libverify.e.a$a  reason: collision with other inner class name */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/e/a$a.class */
    class RunnableC0005a implements Runnable {
        RunnableC0005a() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            a.this.a();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(@NonNull InstanceConfig instanceConfig, @NonNull b bVar, @NonNull CommonContext commonContext) {
        this.a = instanceConfig;
        this.b = bVar;
        this.e = commonContext.getBus();
    }

    private boolean a(int i) throws InterruptedException {
        boolean z;
        if (!((c.b) this.b).f()) {
            FileLog.d("FetcherExecutor", "fetcher start not allowed");
            return false;
        } else if (i >= 20) {
            FileLog.d("FetcherExecutor", "fetcher request max attempts reached");
            return false;
        } else {
            boolean b = ((c.b) this.b).a().b();
            if (!b) {
                long b2 = ((c.b) this.b).b() + (i * 500 * i);
                FileLog.d("FetcherExecutor", "wait timeout %d on attempt (%d)", Long.valueOf(b2), Integer.valueOf(i));
                if (b2 < 0) {
                    z = false;
                } else {
                    if (b2 != 0) {
                        synchronized (this.c) {
                            try {
                                this.c.wait(b2);
                            } catch (InterruptedException e) {
                                FileLog.e("FetcherExecutor", "fetcher thread was interrupted");
                                throw e;
                            }
                        }
                    }
                    z = true;
                }
                if (!z) {
                    FileLog.e("FetcherExecutor", "fetcher timeout check failed");
                    return false;
                }
            }
            if (!((c.b) this.b).f()) {
                FileLog.d("FetcherExecutor", "fetcher start blocked");
                return false;
            } else if (!this.a.getNetwork().hasNetwork()) {
                FileLog.d("FetcherExecutor", "fetcher start blocked, no network");
                return false;
            } else if (b || !this.a.isLowBattery()) {
                return true;
            } else {
                FileLog.d("FetcherExecutor", "fetcher start blocked, low battery");
                return false;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void c() {
        FileLog.d("FetcherExecutor", "fetcher thread start requested, future %s", this.f);
        if (this.f == null) {
            this.f = ((c.b) this.b).a().a().submit(this.d);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void d() {
        FileLog.d("FetcherExecutor", "fetcher thread stop requested, future %s", this.f);
        Future future = this.f;
        if (future != null) {
            future.cancel(true);
            try {
                this.f.get();
            } catch (Exception unused) {
                FileLog.d("FetcherExecutor", "fetcher thread stopped");
            }
            this.f = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void b() {
        Future future = this.f;
        if (future != null) {
            future.cancel(true);
            try {
                this.f.get();
            } catch (Exception unused) {
                FileLog.d("FetcherExecutor", "fetcher thread stopped");
            }
            this.f = null;
        }
        c();
    }

    /* JADX WARN: Code restructure failed: missing block: B:107:0x02c7, code lost:
        if (0 != 0) goto L124;
     */
    /* JADX WARN: Code restructure failed: missing block: B:131:0x0377, code lost:
        if (0 != 0) goto L124;
     */
    /* JADX WARN: Code restructure failed: missing block: B:132:0x037a, code lost:
        r15.cancel(true);
     */
    /* JADX WARN: Code restructure failed: missing block: B:136:0x038c, code lost:
        if (r10 == false) goto L89;
     */
    /* JADX WARN: Code restructure failed: missing block: B:149:0x03b7, code lost:
        if (0 == 0) goto L89;
     */
    /* JADX WARN: Code restructure failed: missing block: B:150:0x03ba, code lost:
        r9.e.post(ru.mail.verify.core.utils.components.MessageBusUtils.createOneArg(ru.mail.verify.core.utils.components.BusMessageType.FETCHER_EXECUTOR_FETCHER_STOPPED, (java.lang.Object) null));
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x0265, code lost:
        if (0 == 0) goto L85;
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x027d, code lost:
        if (0 != 0) goto L124;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x028f, code lost:
        if (0 == 0) goto L85;
     */
    /* JADX WARN: Removed duplicated region for block: B:171:0x0360 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:175:0x003a A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void a() {
        /*
            Method dump skipped, instructions count: 1024
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: ru.mail.libverify.e.a.a():void");
    }
}
