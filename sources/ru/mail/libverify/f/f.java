package ru.mail.libverify.f;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.IBinder;
import android.os.Messenger;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import ru.mail.libverify.api.i;
import ru.mail.libverify.f.a;
import ru.mail.libverify.f.b;
import ru.mail.libverify.f.d;
import ru.mail.libverify.f.g;
import ru.mail.verify.core.utils.FileLog;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/f/f.class */
public final class f {
    private final Context a;
    private final i b;
    private final b c;
    private final HashMap<d, a> d = new HashMap<>();
    private Timer e = null;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/f/f$a.class */
    public enum a {
        Initial,
        Connected,
        Completed,
        Failed
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/f/f$b.class */
    public interface b {
        void a(c cVar);
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/f/f$c.class */
    public enum c {
        OK,
        READY_SERVICE_FOUND,
        FAILED_TO_FIND_READY_SERVICE,
        FAILED_TO_FIND_TARGET_SESSION,
        CONNECTION_TIMEOUT_EXPIRED,
        GENERAL_FAILURE
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/f/f$d.class */
    public class d implements ServiceConnection {
        private ru.mail.libverify.f.a a;
        private final ResolveInfo b;

        /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/f/f$d$a.class */
        class a implements a.InterfaceC0007a {
            a() {
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r0v0 */
            /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
            /* JADX WARN: Type inference failed for: r0v4 */
            public final void a(boolean z) {
                ?? r0 = z;
                FileLog.v("IpcMessageClient", "onServiceConnected postDataToService result = %b", Boolean.valueOf(z));
                d.this.b();
                f fVar = f.this;
                synchronized (fVar) {
                    d dVar = d.this;
                    HashMap<d, a> hashMap = f.this.d;
                    a aVar = r0 != 0 ? a.Completed : a.Failed;
                    r0 = fVar;
                    hashMap.put(dVar, aVar);
                    f.this.a(false);
                }
            }
        }

        private d(ResolveInfo resolveInfo, @NonNull ru.mail.libverify.f.a aVar) {
            this.b = resolveInfo;
            this.a = aVar;
        }

        private synchronized void a() {
            ru.mail.libverify.f.a aVar = this.a;
            if (aVar != null) {
                aVar.a();
                this.a = null;
            }
        }

        public final synchronized void b() {
            FileLog.v("IpcMessageClient", "unbind service %s", this.b.toString());
            try {
                f.this.a.unbindService(this);
            } catch (Throwable th) {
                FileLog.e("IpcMessageClient", "failed to unbind service", th);
            }
            a();
        }

        /* JADX WARN: Type inference failed for: r0v7, types: [java.lang.Throwable, ru.mail.libverify.f.a] */
        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            if (this.a == null || iBinder == null) {
                FileLog.e("IpcMessageClient", "onServiceConnected connected %s, but connection had been already stopped or binder is null", this.b.toString());
                return;
            }
            FileLog.v("IpcMessageClient", "onServiceConnected connected %s", this.b.toString());
            ?? r0 = this.a;
            Messenger messenger = new Messenger(iBinder);
            a aVar = new a();
            synchronized (r0) {
                if (r0.c != null) {
                    throw new IllegalStateException("can't call postDataToService twice");
                }
                r0.c = messenger;
                r0.b = aVar;
                r0.d();
            }
        }

        /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable, ru.mail.libverify.f.f] */
        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            FileLog.v("IpcMessageClient", "onServiceDisconnected disconnected %s", this.b.toString());
            a();
            synchronized (f.this) {
                f.this.d.put(this, a.Failed);
                f.this.a(false);
            }
        }
    }

    public f(@NonNull Context context, @NonNull i iVar, @NonNull b bVar) {
        this.a = context;
        this.b = iVar;
        this.c = bVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(boolean z) {
        if (this.d.isEmpty()) {
            return;
        }
        boolean z2 = false;
        boolean z3 = true;
        Iterator<a> it = this.d.values().iterator();
        while (it.hasNext()) {
            a next = it.next();
            z3 &= next != a.Initial;
            z2 |= next == a.Completed;
        }
        if (z) {
            this.c.a(z2 ? c.OK : c.CONNECTION_TIMEOUT_EXPIRED);
            this.d.clear();
        } else if (z3) {
            if (z2) {
                this.c.a(c.OK);
            } else {
                this.c.a(c.FAILED_TO_FIND_TARGET_SESSION);
            }
            this.d.clear();
        }
    }

    public final void b() {
        a(new d.b(this.b, this.a, d.c.STOPPED), (String) null);
    }

    private synchronized void a(@NonNull ru.mail.libverify.f.c cVar, String str) {
        String packageName;
        boolean z;
        List<ResolveInfo> queryIntentServices;
        FileLog.v("IpcMessageClient", "connectAndPostData target package = %s", str);
        this.d.clear();
        try {
            Intent intent = new Intent(cVar.a().getName());
            packageName = this.a.getPackageName();
            z = !TextUtils.isEmpty(str);
            queryIntentServices = this.a.getPackageManager().queryIntentServices(intent, 0);
            Collections.sort(queryIntentServices, resolveInfo, resolveInfo2 -> {
                return Long.compare(new File(resolveInfo2.serviceInfo.applicationInfo.sourceDir).lastModified(), new File(resolveInfo.serviceInfo.applicationInfo.sourceDir).lastModified());
            });
            Object[] objArr = new Object[1];
            objArr[0] = Integer.valueOf(queryIntentServices.size());
            FileLog.d("IpcMessageClient", "connectAndPostData found services count %d", objArr);
        } catch (Exception e) {
            FileLog.e("IpcMessageClient", "connectAndPostData", e);
            Timer timer = this.e;
            if (timer != null) {
                timer.cancel();
                this.e = null;
            }
            FileLog.d("IpcMessageClient", "unbind %d connections", Integer.valueOf(this.d.size()));
            for (d dVar : this.d.keySet()) {
                dVar.b();
            }
            this.c.a(c.GENERAL_FAILURE);
        }
        if (queryIntentServices.size() == 0) {
            this.c.a(c.FAILED_TO_FIND_READY_SERVICE);
            return;
        }
        for (ResolveInfo resolveInfo3 : queryIntentServices) {
            boolean z2 = !TextUtils.equals(packageName, resolveInfo3.serviceInfo.packageName);
            boolean z3 = !z || TextUtils.equals(str, resolveInfo3.serviceInfo.packageName);
            if (z2 && z3) {
                Timer timer2 = this.e;
                if (timer2 != null) {
                    timer2.cancel();
                    this.e = null;
                }
                if (this.e == null) {
                    this.e = new Timer("IpcMessageClient.connectionTimer");
                }
                this.e.schedule(new e(this), 1000L);
                a(resolveInfo3, cVar);
            }
        }
        if (this.d.isEmpty()) {
            this.c.a(c.FAILED_TO_FIND_READY_SERVICE);
        } else {
            this.c.a(c.READY_SERVICE_FOUND);
        }
    }

    private void a(@NonNull ResolveInfo resolveInfo, @NonNull ru.mail.libverify.f.c cVar) {
        FileLog.v("IpcMessageClient", "connectToService try binding to %s", resolveInfo.toString());
        try {
            Intent intent = new Intent();
            ServiceInfo serviceInfo = resolveInfo.serviceInfo;
            intent.setClassName(serviceInfo.packageName, serviceInfo.name);
            d dVar = new d(resolveInfo, cVar.b());
            if (this.a.bindService(intent, dVar, 1)) {
                this.d.put(dVar, a.Initial);
                Object[] objArr = new Object[1];
                objArr[0] = resolveInfo.toString();
                FileLog.v("IpcMessageClient", "connectToService bound to %s", objArr);
            } else {
                Object[] objArr2 = new Object[1];
                objArr2[0] = resolveInfo.toString();
                FileLog.e("IpcMessageClient", "connectToService failed to bind to %s", objArr2);
            }
        } catch (SecurityException e) {
            FileLog.e("IpcMessageClient", e, "connectToService failed to bind to %s", resolveInfo.toString());
        }
    }

    public final void a(@NonNull String str, long j) {
        a(new b.a(this.b, str, j), (String) null);
    }

    public final void a() {
        a(new d.b(this.b, this.a, d.c.STARTED), (String) null);
    }

    public final void a(@NonNull String str, @NonNull String str2, String str3) {
        a(new g.a(this.b, str, str2), str3);
    }
}
