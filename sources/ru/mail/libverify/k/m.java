package ru.mail.libverify.k;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.util.SparseArray;
import androidx.annotation.NonNull;
import androidx.collection.LongSparseArray;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import ru.mail.libverify.api.CommonContext;
import ru.mail.libverify.k.l;
import ru.mail.verify.core.utils.FileLog;
import ru.mail.verify.core.utils.Utils;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/k/m.class */
public final class m implements l {
    static final Pattern h = Pattern.compile(".*", 32);
    private static final Pattern i = Pattern.compile("content://sms/[0-9]+");
    private final ContentResolver b;
    private final CommonContext c;
    private long d;
    private LinkedHashMap a = new LinkedHashMap();
    private String[] e = {"_id", "type", "address", "body"};
    private String f = "_id ASC";
    private LongSparseArray<c> g = new b();

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/k/m$a.class */
    class a implements ru.mail.libverify.k.e {
        final /* synthetic */ d a;
        final /* synthetic */ l.e b;

        a(d dVar, l.e eVar) {
            this.a = dVar;
            this.b = eVar;
        }

        @Override // ru.mail.libverify.k.e
        public final void a() {
            synchronized (m.this) {
                List list = (List) m.this.a.get(this.a);
                list.remove(this.b);
                if (list.isEmpty()) {
                    m.this.a.remove(this.a);
                }
            }
        }
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/k/m$b.class */
    private static class b<T> extends LongSparseArray<T> {
        private final int a = 128;

        public final void put(long j, T t) {
            if (size() == this.a && get(j) == null) {
                removeAt(0);
            }
            super.put(j, t);
        }

        @NonNull
        public final String toString() {
            StringBuilder sb = new StringBuilder("{");
            for (int i = 0; i < size(); i++) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(keyAt(i) + ":" + valueAt(i));
            }
            sb.append('}');
            return sb.toString();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/k/m$c.class */
    public static class c {
        public static final c INBOX;
        public static final c SENT;
        public static final c DRAFT;
        public static final c OUTBOX;
        public static final c FAILED;
        public static final c QUEUED;
        public static final c UNKNOWN;
        private static final SparseArray<c> values;
        private static final /* synthetic */ c[] $VALUES;
        private final int mCode;

        /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/k/m$c$a.class */
        enum a extends c {
            private a() {
                super("INBOX", 0, 1);
            }

            @Override // ru.mail.libverify.k.m.c
            public final void a(m mVar, ru.mail.libverify.k.b bVar) {
                FileLog.v("SmsManager", ">>> onReceived(%s)", bVar);
                Iterator it = mVar.a(bVar).iterator();
                while (it.hasNext()) {
                    ((l.e) it.next()).b(bVar);
                }
            }
        }

        /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/k/m$c$b.class */
        enum b extends c {
            private b() {
                super("SENT", 1, 2);
            }

            @Override // ru.mail.libverify.k.m.c
            public final void a(m mVar, ru.mail.libverify.k.b bVar) {
                FileLog.v("SmsManager", ">>> onSent(%s)", bVar);
                Iterator it = mVar.a(bVar).iterator();
                while (it.hasNext()) {
                    ((l.e) it.next()).a(bVar);
                }
            }
        }

        /* renamed from: ru.mail.libverify.k.m$c$c  reason: collision with other inner class name */
        /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/k/m$c$c.class */
        enum C0013c extends c {
            private C0013c() {
                super("OUTBOX", 3, 4);
            }

            @Override // ru.mail.libverify.k.m.c
            public final void a(m mVar, ru.mail.libverify.k.b bVar) {
                FileLog.v("SmsManager", ">>> onSending(%s)", bVar);
                Iterator it = mVar.a(bVar).iterator();
                while (it.hasNext()) {
                    ((l.e) it.next()).d(bVar);
                }
            }
        }

        /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/k/m$c$d.class */
        enum d extends c {
            private d() {
                super("FAILED", 4, 5);
            }

            @Override // ru.mail.libverify.k.m.c
            public final void a(m mVar, ru.mail.libverify.k.b bVar) {
                FileLog.v("SmsManager", ">>> onSendingFailed(%s)", bVar);
                Iterator it = mVar.a(bVar).iterator();
                while (it.hasNext()) {
                    ((l.e) it.next()).c(bVar);
                }
            }
        }

        /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/k/m$c$e.class */
        class e extends SparseArray<c> {
            e() {
                c[] values;
                for (c cVar : c.values()) {
                    put(cVar.mCode, cVar);
                }
            }
        }

        public static c[] values() {
            return (c[]) $VALUES.clone();
        }

        public static c valueOf(String str) {
            return (c) Enum.valueOf(c.class, str);
        }

        private c(String str, int i, int i2) {
            this.mCode = i2;
        }

        public static c a(int i) {
            c cVar = values.get(i);
            c cVar2 = cVar;
            if (cVar == null) {
                cVar2 = UNKNOWN;
            }
            return cVar2;
        }

        static {
            a aVar = new a();
            INBOX = aVar;
            b bVar = new b();
            SENT = bVar;
            c cVar = new c("DRAFT", 2, 3);
            DRAFT = cVar;
            C0013c c0013c = new C0013c();
            OUTBOX = c0013c;
            d dVar = new d();
            FAILED = dVar;
            c cVar2 = new c("QUEUED", 5, 6);
            QUEUED = cVar2;
            c cVar3 = new c("UNKNOWN", 6, -1);
            UNKNOWN = cVar3;
            $VALUES = new c[]{aVar, bVar, cVar, c0013c, dVar, cVar2, cVar3};
            values = new e();
        }

        public void a(m mVar, ru.mail.libverify.k.b bVar) {
            FileLog.v("SmsManager", ">>> Unprocessable message type: %s", bVar.d());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/k/m$d.class */
    public static class d {
        final Pattern a;
        final Pattern b;
        final String c;
        final String d;

        private d(Pattern pattern, Pattern pattern2) {
            this.b = pattern;
            this.a = pattern2;
            this.c = pattern2.pattern();
            this.d = pattern.pattern();
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || d.class != obj.getClass()) {
                return false;
            }
            d dVar = (d) obj;
            return this.d.equals(dVar.d) && this.c.equals(dVar.c);
        }

        public final int hashCode() {
            return this.d.hashCode() + (this.c.hashCode() * 31);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/k/m$e.class */
    public class e extends ContentObserver {
        public e(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            onChange(z, null);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            m mVar = m.this;
            mVar.getClass();
            FileLog.v("SmsManager", "Got some message folder change: uri=%s", uri);
            mVar.c.getDispatcher().post(new n(mVar, uri));
        }
    }

    public m(@NonNull CommonContext commonContext) {
        this.c = commonContext;
        this.b = commonContext.getConfig().getContext().getContentResolver();
        if (a()) {
            return;
        }
        c();
        d();
    }

    private void d() {
        try {
            this.b.registerContentObserver(ru.mail.libverify.k.a.a, true, new e(this.c.getDispatcher()));
        } catch (Exception e2) {
            FileLog.e("SmsManager", "start error", e2);
        }
    }

    private boolean a() {
        if (Utils.hasSelfPermission(this.c.getConfig().getContext(), "android.permission.READ_SMS")) {
            return false;
        }
        FileLog.e("SmsManager", "can't init SmsManager without %s", "android.permission.READ_SMS");
        return true;
    }

    private void c() {
        e();
        Cursor cursor = null;
        try {
            try {
                Cursor query = this.b.query(ru.mail.libverify.k.a.a, this.e, null, null, "_id DESC LIMIT 128");
                cursor = query;
                if (query != null) {
                    f fVar = new f(cursor);
                    while (fVar.c()) {
                        ru.mail.libverify.k.b a2 = fVar.a();
                        this.g.put(a2.a(), a2.d());
                    }
                } else if (cursor == null) {
                    return;
                }
            } catch (Exception e2) {
                Cursor cursor2 = cursor;
                FileLog.e("SmsManager", "prefillKnownMessages error", e2);
                if (cursor2 == null) {
                    return;
                }
            }
            cursor.close();
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e() {
        long j;
        Cursor query;
        try {
            ContentResolver contentResolver = this.b;
            Uri uri = ru.mail.libverify.k.a.a;
            String[] strArr = new String[1];
            strArr[0] = "_id";
            query = contentResolver.query(uri, strArr, null, null, "_id DESC LIMIT 1");
        } catch (Exception e2) {
            FileLog.e("SmsManager", "obtainLastSmsId error", e2);
        }
        if (query == null || !query.moveToFirst()) {
            if (query != null) {
                query.close();
            }
            j = -1;
            this.d = j;
        }
        j = query.getLong(query.getColumnIndex("_id"));
        query.close();
        this.d = j;
    }

    @NonNull
    public final i b() {
        return new i(this::a);
    }

    private ru.mail.libverify.k.e a(@NonNull Pattern pattern, @NonNull Pattern pattern2, @NonNull l.e eVar) {
        ArrayList arrayList;
        d dVar = new d(pattern, pattern2);
        synchronized (this) {
            List list = (List) this.a.get(dVar);
            ArrayList arrayList2 = list;
            if (list == null) {
                arrayList2 = arrayList;
                arrayList = new ArrayList();
                this.a.put(dVar, arrayList2);
            }
            arrayList2.add(eVar);
        }
        return new a(dVar, eVar);
    }

    private ArrayList a(ru.mail.libverify.k.b bVar) {
        ArrayList arrayList = new ArrayList();
        if (!TextUtils.isEmpty(bVar.b()) && !TextUtils.isEmpty(bVar.c())) {
            synchronized (this) {
                for (Map.Entry entry : this.a.entrySet()) {
                    d dVar = (d) entry.getKey();
                    if (dVar.b.matcher(bVar.b()).matches() && dVar.a.matcher(bVar.c()).matches()) {
                        arrayList.addAll((Collection) entry.getValue());
                    }
                }
            }
        }
        return arrayList;
    }
}
