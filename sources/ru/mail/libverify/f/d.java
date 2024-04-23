package ru.mail.libverify.f;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import androidx.annotation.NonNull;
import ru.mail.libverify.api.i;
import ru.mail.libverify.f.f;
import ru.mail.libverify.ipc.IpcNotificationService;
import ru.mail.verify.core.utils.FileLog;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/f/d.class */
final class d extends ru.mail.libverify.f.a {
    private final c f;
    private final String g;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/f/d$a.class */
    public static /* synthetic */ class a {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[c.values().length];
            a = iArr;
            try {
                try {
                    try {
                        iArr[c.STARTED.ordinal()] = 1;
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
                            a[c.STOPPED.ordinal()] = 2;
                        } catch (NoSuchFieldError unused4) {
                        }
                    } catch (NoSuchFieldError unused5) {
                    }
                } catch (NoSuchFieldError unused6) {
                }
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/f/d$b.class */
    static class b implements ru.mail.libverify.f.c {
        private final i a;
        private final Context b;
        private final c c;

        /* JADX INFO: Access modifiers changed from: package-private */
        public b(@NonNull i iVar, @NonNull Context context, c cVar) {
            this.a = iVar;
            this.b = context;
            this.c = cVar;
        }

        @Override // ru.mail.libverify.f.c
        public final ru.mail.libverify.f.a b() {
            return new d(this.a, this.c, this.b.getPackageName());
        }

        @Override // ru.mail.libverify.f.c
        public final Class a() {
            return IpcNotificationService.class;
        }
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/f/d$c.class */
    enum c {
        STARTED,
        STOPPED
    }

    private d(@NonNull i iVar, c cVar, String str) {
        super(iVar);
        this.f = cVar;
        this.g = str;
    }

    private Message a(c cVar) {
        Message obtain;
        int i = a.a[cVar.ordinal()];
        if (i == 1) {
            obtain = Message.obtain(this, 6);
        } else if (i != 2) {
            throw new IllegalArgumentException("unknown fetcher state");
        } else {
            obtain = Message.obtain(this, 7);
        }
        Message message = obtain;
        message.replyTo = b();
        Bundle bundle = new Bundle();
        bundle.putString("data", this.g);
        message.setData(bundle);
        return obtain;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // ru.mail.libverify.f.a
    public final void d() {
        try {
            this.c.send(a(this.f));
            ((f.d.a) this.b).a(true);
        } catch (Throwable th) {
            FileLog.e("FetcherState", "postDataToService", th);
        }
    }
}
