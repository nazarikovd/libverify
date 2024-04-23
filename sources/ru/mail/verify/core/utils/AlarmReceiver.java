package ru.mail.verify.core.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import java.util.Locale;
import java.util.Random;
import ru.mail.verify.core.storage.Installation;
import ru.mail.verify.core.storage.UnsafeInstallation;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/utils/AlarmReceiver.class */
public class AlarmReceiver extends BroadcastReceiver {
    public static final /* synthetic */ int a = 0;

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/utils/AlarmReceiver$AlarmBuilder.class */
    public static class AlarmBuilder {
        private final Intent a;
        private final Context b;
        private final boolean c;
        private long d = 0;
        private boolean e = false;
        private boolean f = true;
        private boolean g = true;

        private AlarmBuilder(@NonNull Context context, boolean z) {
            this.a = new Intent(context, AlarmReceiver.class);
            this.b = context;
            this.c = z;
        }

        private a a() {
            Intent intent = this.a;
            FileLog.v("AlarmBuilder", "build %s (extras: %s, shift: %s, repeat: %s, update: %s)", intent, Utils.bundleToString(intent.getExtras()), Boolean.valueOf(this.f), Boolean.valueOf(this.e), Boolean.valueOf(this.g));
            return new a(PendingIntent.getBroadcast(this.b, 0, this.a, this.g ? new ru.mail.libverify.r.a().d().c().a() : new ru.mail.libverify.r.a().c().a()), this.a.getAction());
        }

        public AlarmBuilder setAction(@NonNull String action) {
            this.a.setAction(action);
            return this;
        }

        public AlarmBuilder putExtra(@NonNull String name, @NonNull String value) {
            this.a.putExtra(name, value);
            this.a.addCategory(String.format(Locale.US, "%s:%s", name, value));
            return this;
        }

        public AlarmBuilder setTimeout(long timeout) {
            if (timeout > 0) {
                this.d = timeout;
                return this;
            }
            throw new IllegalArgumentException("timeout must be > 0");
        }

        public AlarmBuilder setRepeating(boolean repeating) {
            this.e = repeating;
            return this;
        }

        public AlarmBuilder disableRandomShift() {
            this.f = false;
            return this;
        }

        public AlarmBuilder disableUpdateExisting() {
            this.g = false;
            return this;
        }

        public void setUp() {
            if (!this.c) {
                AlarmReceiver.a(this.b, a(), this.d, this.f, this.e);
                return;
            }
            Context context = this.b;
            a a = a();
            int i = AlarmReceiver.a;
            AlarmManager alarmManager = (AlarmManager) context.getSystemService("alarm");
            if (alarmManager == null) {
                return;
            }
            alarmManager.cancel(a.a);
            FileLog.v("AlarmReceiver", "canceled alarm: %s", a.b);
        }

        public void cancel() {
            Context context = this.b;
            a a = a();
            int i = AlarmReceiver.a;
            AlarmManager alarmManager = (AlarmManager) context.getSystemService("alarm");
            if (alarmManager == null) {
                return;
            }
            alarmManager.cancel(a.a);
            FileLog.v("AlarmReceiver", "canceled alarm: %s", a.b);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/utils/AlarmReceiver$a.class */
    public static class a {
        private final PendingIntent a;
        private final String b;

        private a(PendingIntent pendingIntent, String str) {
            this.a = pendingIntent;
            this.b = str;
        }
    }

    public static AlarmBuilder create(@NonNull Context context, boolean disableAlarms) {
        return new AlarmBuilder(context, disableAlarms);
    }

    private static void a(@NonNull Context context, @NonNull a aVar, long j, boolean z, boolean z2) {
        try {
            if (j <= 0) {
                throw new IllegalArgumentException("timeout must be > 0");
            }
            Object[] objArr = new Object[4];
            objArr[0] = aVar.b;
            objArr[1] = Long.valueOf(j);
            objArr[2] = Boolean.valueOf(z);
            objArr[3] = Boolean.valueOf(z2);
            FileLog.v("AlarmReceiver", "set up alarm %s : timeout = %d, shift = %s, repeating = %s", objArr);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService("alarm");
            if (alarmManager == null) {
                return;
            }
            alarmManager.cancel(aVar.a);
            long currentTimeMillis = System.currentTimeMillis();
            if (z2) {
                alarmManager.setInexactRepeating(1, (!z || j >= 2147483647L) ? currentTimeMillis + j : new Random().nextInt((int) j) + (j / 2) + currentTimeMillis, j, aVar.a);
            } else {
                alarmManager.set(1, currentTimeMillis + j, aVar.a);
            }
        } catch (Throwable th) {
            DebugUtils.safeThrow("AlarmReceiver", "error in setup an alarm logic", th);
        }
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (intent == null) {
            return;
        }
        int i = ru.mail.libverify.v.a.f;
        if (!(UnsafeInstallation.hasInstallation(context) || Installation.hasInstallation(context))) {
            FileLog.d("AlarmReceiver", "An alarm received, but no libverify installation found. Next initialize will be disabled.");
            new AlarmBuilder(context, false).cancel();
            return;
        }
        FileLog.v("AlarmReceiver", "handle %s (extras: %s)", intent, Utils.bundleToString(intent.getExtras()));
        if (intent.getCategories() != null && !intent.getCategories().isEmpty()) {
            Intent intent2 = new Intent(intent);
            for (String str : intent.getCategories()) {
                String[] split = str.split(":");
                if (split.length == 2) {
                    intent2.putExtra(split[0], split[1]);
                }
            }
            intent = intent2;
        }
        IntentProcessServiceProcessor.start(context, intent);
    }
}
