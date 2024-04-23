package ru.mail.libverify.k;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.mail.libverify.api.CommonContext;
import ru.mail.libverify.k.g;
import ru.mail.libverify.l.b;
import ru.mail.verify.core.storage.InstanceConfig;
import ru.mail.verify.core.utils.FileLog;
import ru.mail.verify.core.utils.Utils;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/k/h.class */
public final class h implements g {
    @NotNull
    private static final HashMap<g.a, b> c = new HashMap<>();
    @NotNull
    private final CommonContext a;
    @NotNull
    private final ru.mail.libverify.l.b b;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/k/h$a.class */
    public static abstract class a {

        /* renamed from: ru.mail.libverify.k.h$a$a  reason: collision with other inner class name */
        /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/k/h$a$a.class */
        public static final class C0012a extends a {
            @NotNull
            public static final C0012a a = new C0012a();

            private C0012a() {
                super(0);
            }
        }

        /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/k/h$a$b.class */
        public static final class b extends a {
            @NotNull
            private final g.c a;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public b(@NotNull g.c cVar) {
                super(0);
                Intrinsics.checkNotNullParameter(cVar, "filter");
                this.a = cVar;
            }

            @NotNull
            public final g.c a() {
                return this.a;
            }

            @NotNull
            public final String toString() {
                return "Outgoing(filter=" + this.a + ')';
            }

            public final int hashCode() {
                return this.a.hashCode();
            }

            public final boolean equals(@Nullable Object obj) {
                if (this == obj) {
                    return true;
                }
                return (obj instanceof b) && Intrinsics.areEqual(this.a, ((b) obj).a);
            }
        }

        private a() {
        }

        public /* synthetic */ a(int i) {
            this();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/k/h$b.class */
    public final class b extends BroadcastReceiver {
        @NotNull
        private final a a;
        @Nullable
        private g.a b;
        final /* synthetic */ h c;

        public b(h hVar, @NotNull a aVar) {
            Intrinsics.checkNotNullParameter(aVar, "callType");
            this.c = hVar;
            this.a = aVar;
        }

        private static final void a(String str, String str2, b bVar, h hVar) {
            Intrinsics.checkNotNullParameter(str, "$state");
            Intrinsics.checkNotNullParameter(str2, "$incomingNumber");
            Intrinsics.checkNotNullParameter(bVar, "this$0");
            Intrinsics.checkNotNullParameter(hVar, "this$1");
            FileLog.v("PhoneCallInterceptor", "onCallStateChanged state %s number %s", str, str2);
            if (bVar.b == null || TextUtils.isEmpty(str2)) {
                return;
            }
            g.a aVar = bVar.b;
            Intrinsics.checkNotNull(aVar);
            if (aVar.a(str2)) {
                h.b(hVar);
            }
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(@NotNull Context context, @NotNull Intent intent) {
            String string;
            Bundle extras;
            String string2;
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(intent, "intent");
            Bundle extras2 = intent.getExtras();
            if (extras2 == null || (string = extras2.getString("state")) == null) {
                return;
            }
            a aVar = this.a;
            if (Intrinsics.areEqual(aVar, a.C0012a.a)) {
                if (!Intrinsics.areEqual(string, TelephonyManager.EXTRA_STATE_RINGING) || (extras = intent.getExtras()) == null || (string2 = extras.getString("incoming_number")) == null) {
                    return;
                }
                Handler dispatcher = this.c.a.getDispatcher();
                h hVar = this.c;
                dispatcher.post(() -> {
                    a(r1, r2, r3, r4);
                });
            } else if ((aVar instanceof a.b) && Intrinsics.areEqual(string, TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                Bundle extras3 = intent.getExtras();
                String string3 = extras3 != null ? extras3.getString("incoming_number") : null;
                FileLog.v("PhoneCallInterceptor", "onCallStateChanged state " + string + " number " + string3);
                if (((a.b) this.a).a().a(string3)) {
                    String str = string3;
                    this.c.a.getDispatcher().post(() -> {
                        a(r1, r2);
                    });
                }
            }
        }

        private static final void a(b bVar, String str) {
            Intrinsics.checkNotNullParameter(bVar, "this$0");
            g.a aVar = bVar.b;
            if (aVar != null) {
                if (str == null) {
                    str = "";
                }
                aVar.a(str);
            }
        }

        public final void a(@Nullable g.a aVar) {
            this.b = aVar;
        }
    }

    public h(@NotNull CommonContext commonContext, @NotNull ru.mail.libverify.l.b bVar) {
        Intrinsics.checkNotNullParameter(commonContext, "commonContext");
        Intrinsics.checkNotNullParameter(bVar, "eventLogger");
        this.a = commonContext;
        this.b = bVar;
    }

    public static final void b(h hVar) {
        Object systemService = hVar.a.getConfig().getContext().getSystemService(InstanceConfig.DEVICE_TYPE_PHONE);
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.telephony.TelephonyManager");
        TelephonyManager telephonyManager = (TelephonyManager) systemService;
        try {
            if (Utils.hasSelfPermission(hVar.a.getConfig().getContext(), "android.permission.CALL_PHONE")) {
                Method declaredMethod = TelephonyManager.class.getDeclaredMethod("getITelephony", new Class[0]);
                declaredMethod.setAccessible(true);
                Object invoke = declaredMethod.invoke(telephonyManager, new Object[0]);
                if (invoke != null) {
                    Class<?> cls = Class.forName("com.android.internal.telephony.ITelephony");
                    Intrinsics.checkNotNullExpressionValue(cls, "telephonyClazz");
                    cls.getDeclaredMethod("endCall", new Class[0]).invoke(invoke, new Object[0]);
                    cls.getDeclaredMethod("silenceRinger", new Class[0]).invoke(invoke, new Object[0]);
                    cls.getDeclaredMethod("cancelMissedCallsNotification", new Class[0]).invoke(invoke, new Object[0]);
                }
            } else {
                Object[] objArr = new Object[1];
                objArr[0] = "android.permission.CALL_PHONE";
                FileLog.d("PhoneCallInterceptor", "can't reject call without %s permission", objArr);
            }
        } catch (Throwable th) {
            FileLog.d("PhoneCallInterceptor", "can't reject call", th);
        }
    }

    public final void c(@NotNull g.a aVar) {
        Intrinsics.checkNotNullParameter(aVar, "callback");
        FileLog.v("PhoneCallInterceptor", "callback unregistered");
        b remove = c.remove(aVar);
        if (remove == null) {
            return;
        }
        remove.a(null);
        this.a.getConfig().getContext().unregisterReceiver(remove);
    }

    public final boolean a(@Nullable String str) {
        if (str == null) {
            return false;
        }
        return b(str);
    }

    public final void a(@NotNull g.b bVar) {
        Intrinsics.checkNotNullParameter(bVar, "processor");
        this.a.getBackgroundWorker().submit(() -> {
            a(r1, r2);
        });
    }

    private static final void a(h hVar, g.b bVar) {
        Intrinsics.checkNotNullParameter(hVar, "this$0");
        Intrinsics.checkNotNullParameter(bVar, "$processor");
        FileLog.v("PhoneCallInterceptor", "last calls search started");
        Cursor cursor = null;
        ArrayList arrayList = new ArrayList();
        try {
            if (!Utils.hasSelfPermission(hVar.a.getConfig().getContext(), "android.permission.READ_CALL_LOG")) {
                Object[] objArr = new Object[1];
                objArr[0] = "android.permission.READ_CALL_LOG";
                FileLog.d("PhoneCallInterceptor", "can't read calls without %s permission", objArr);
                String arrays = Arrays.toString(arrayList.toArray(new String[0]));
                Intrinsics.checkNotNullExpressionValue(arrays, "toString(this)");
                FileLog.d("PhoneCallInterceptor", "found %s calls", arrays);
                hVar.a.getDispatcher().post(() -> {
                    a(r1, r2);
                });
                return;
            }
            bVar.getClass();
            Cursor a2 = hVar.a();
            if (a2 == null) {
                String arrays2 = Arrays.toString(arrayList.toArray(new String[0]));
                Intrinsics.checkNotNullExpressionValue(arrays2, "toString(this)");
                FileLog.d("PhoneCallInterceptor", "found %s calls", arrays2);
                hVar.a.getDispatcher().post(() -> {
                    a(r1, r2);
                });
                return;
            }
            long a3 = bVar.a();
            int columnIndex = a2.getColumnIndex("number");
            int columnIndex2 = a2.getColumnIndex("date");
            if (!a2.moveToFirst()) {
                a2.close();
                String arrays3 = Arrays.toString(arrayList.toArray(new String[0]));
                Intrinsics.checkNotNullExpressionValue(arrays3, "toString(this)");
                FileLog.d("PhoneCallInterceptor", "found %s calls", arrays3);
                hVar.a.getDispatcher().post(() -> {
                    a(r1, r2);
                });
            }
            do {
                long j = a2.getLong(columnIndex2) - a3;
                if (j >= 0) {
                    if (j > 30000) {
                        a2.close();
                        String arrays4 = Arrays.toString(arrayList.toArray(new String[0]));
                        Intrinsics.checkNotNullExpressionValue(arrays4, "toString(this)");
                        FileLog.d("PhoneCallInterceptor", "found %s calls", arrays4);
                        hVar.a.getDispatcher().post(() -> {
                            a(r1, r2);
                        });
                        return;
                    }
                    String string = a2.getString(columnIndex);
                    Intrinsics.checkNotNullExpressionValue(string, "phoneNumber");
                    arrayList.add(string);
                    if (arrayList.size() > 5) {
                        a2.close();
                        String arrays5 = Arrays.toString(arrayList.toArray(new String[0]));
                        Intrinsics.checkNotNullExpressionValue(arrays5, "toString(this)");
                        FileLog.d("PhoneCallInterceptor", "found %s calls", arrays5);
                        hVar.a.getDispatcher().post(() -> {
                            a(r1, r2);
                        });
                        return;
                    }
                }
            } while (a2.moveToNext());
            a2.close();
            String arrays32 = Arrays.toString(arrayList.toArray(new String[0]));
            Intrinsics.checkNotNullExpressionValue(arrays32, "toString(this)");
            FileLog.d("PhoneCallInterceptor", "found %s calls", arrays32);
            hVar.a.getDispatcher().post(() -> {
                a(r1, r2);
            });
        } catch (Throwable th) {
            try {
                FileLog.e("PhoneCallInterceptor", "can't read calls", th);
                if (0 != 0) {
                    cursor.close();
                }
                String arrays6 = Arrays.toString(arrayList.toArray(new String[0]));
                Intrinsics.checkNotNullExpressionValue(arrays6, "toString(this)");
                FileLog.d("PhoneCallInterceptor", "found %s calls", arrays6);
                hVar.a.getDispatcher().post(() -> {
                    a(r1, r2);
                });
            } catch (Throwable th2) {
                if (0 != 0) {
                    cursor.close();
                }
                String arrays7 = Arrays.toString(arrayList.toArray(new String[0]));
                Intrinsics.checkNotNullExpressionValue(arrays7, "toString(this)");
                FileLog.d("PhoneCallInterceptor", "found %s calls", arrays7);
                hVar.a.getDispatcher().post(() -> {
                    a(r2, r3);
                });
                throw th2;
            }
        }
    }

    private static final void a(g.b bVar, List list) {
        Intrinsics.checkNotNullParameter(bVar, "$processor");
        Intrinsics.checkNotNullParameter(list, "$result");
        bVar.a(list);
    }

    private final Cursor a() {
        if (Build.VERSION.SDK_INT >= 26) {
            Bundle bundle = new Bundle();
            bundle.putInt("android:query-arg-limit", 5);
            bundle.putInt("android:query-arg-sort-direction", 1);
            bundle.putStringArray("android:query-arg-sort-columns", new String[]{"date"});
            return this.a.getConfig().getContext().getContentResolver().query(ru.mail.libverify.k.a.c, null, bundle, null);
        }
        return this.a.getConfig().getContext().getContentResolver().query(ru.mail.libverify.k.a.c, null, null, null, "date DESC LIMIT 5");
    }

    public final void a(@NotNull g.a aVar) {
        Intrinsics.checkNotNullParameter(aVar, "callback");
        if (!Utils.hasSelfPermission(this.a.getConfig().getContext(), "android.permission.READ_PHONE_STATE")) {
            FileLog.d("PhoneCallInterceptor", "can't register call listener without %s permission", "android.permission.READ_PHONE_STATE");
            return;
        }
        try {
            HashMap<g.a, b> hashMap = c;
            if (hashMap.containsKey(aVar)) {
                FileLog.e("PhoneCallInterceptor", "callback has been already registered");
                return;
            }
            FileLog.v("PhoneCallInterceptor", "callback registered");
            b bVar = new b(this, a.C0012a.a);
            bVar.a(aVar);
            hashMap.put(aVar, bVar);
            Context context = this.a.getConfig().getContext();
            Intrinsics.checkNotNullExpressionValue(context, "commonContext.config.context");
            IntentFilter intentFilter = new IntentFilter("android.intent.action.PHONE_STATE");
            if (Build.VERSION.SDK_INT >= 33) {
                context.registerReceiver(bVar, intentFilter, 4);
            } else {
                context.registerReceiver(bVar, intentFilter);
            }
        } catch (Throwable th) {
            FileLog.e("PhoneCallInterceptor", "failed to subscribe for a call state", th);
        }
    }

    public final void a(@NotNull g.c cVar, @NotNull g.a aVar) {
        Intrinsics.checkNotNullParameter(cVar, "phoneNumberFilter");
        Intrinsics.checkNotNullParameter(aVar, "callback");
        if (b("")) {
            HashMap<g.a, b> hashMap = c;
            if (hashMap.containsKey(aVar)) {
                FileLog.e("PhoneCallInterceptor", "callback has been already registered");
                return;
            }
            b bVar = new b(this, new a.b(cVar));
            bVar.a(aVar);
            hashMap.put(aVar, bVar);
            Context context = this.a.getConfig().getContext();
            Intrinsics.checkNotNullExpressionValue(context, "commonContext.config.context");
            IntentFilter intentFilter = new IntentFilter("android.intent.action.PHONE_STATE");
            if (Build.VERSION.SDK_INT >= 33) {
                context.registerReceiver(bVar, intentFilter, 4);
            } else {
                context.registerReceiver(bVar, intentFilter);
            }
            FileLog.d("PhoneCallInterceptor", "Outgoing call callback registered!");
        }
    }

    private final boolean b(String str) {
        FileLog.v("PhoneCallInterceptor", "check phone is able to intercept calls");
        boolean z = Build.VERSION.SDK_INT < 28;
        boolean hasSelfPermission = Utils.hasSelfPermission(this.a.getConfig().getContext(), "android.permission.READ_CALL_LOG");
        if (!Utils.hasSelfPermission(this.a.getConfig().getContext(), "android.permission.READ_PHONE_STATE") || (!z && !hasSelfPermission)) {
            FileLog.d("PhoneCallInterceptor", "can't intercept calls to %s (%s)", str, "no permission");
            this.b.a(b.a.NO_CALL_PERMISSION);
            return false;
        } else if (this.a.getConfig().getSimCardData().hasAtLeastOneReadySim()) {
            return true;
        } else {
            FileLog.d("PhoneCallInterceptor", "can't intercept calls to %s (%s)", str, "no ready sim");
            this.b.a(b.a.NO_READY_SIM);
            return false;
        }
    }

    public final void b(@NotNull g.a aVar) {
        Intrinsics.checkNotNullParameter(aVar, "callback");
        if (!Utils.hasSelfPermission(this.a.getConfig().getContext(), "android.permission.READ_PHONE_STATE")) {
            FileLog.d("PhoneCallInterceptor", "can't register call listener without %s permission", "android.permission.READ_PHONE_STATE");
            return;
        }
        try {
            FileLog.v("PhoneCallInterceptor", "callback unregistered");
            b remove = c.remove(aVar);
            if (remove == null) {
                return;
            }
            remove.a(null);
            this.a.getConfig().getContext().unregisterReceiver(remove);
        } catch (Throwable th) {
            FileLog.e("PhoneCallInterceptor", "failed to subscribe for a call state", th);
        }
    }
}
