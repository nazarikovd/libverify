package ru.mail.libverify.v;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Message;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.Executors;
import ru.mail.libverify.R;
import ru.mail.libverify.api.VerificationFactory;
import ru.mail.verify.core.api.ApiComponent;
import ru.mail.verify.core.api.ApiManager;
import ru.mail.verify.core.api.ApplicationModule;
import ru.mail.verify.core.api.DaggerApiComponent;
import ru.mail.verify.core.utils.FileLog;
import ru.mail.verify.core.utils.Utils;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/v/a.class */
public final class a {
    @SuppressLint({"StaticFieldLeak"})
    private static volatile ApplicationModule a;
    private static volatile ApiComponent b;
    private static volatile boolean c = false;
    private static volatile String d;
    private static final LinkedList<Runnable> e = new LinkedList<>();
    public static final /* synthetic */ int f = 0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: ru.mail.libverify.v.a$a  reason: collision with other inner class name */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/v/a$a.class */
    public class RunnableC0023a implements Runnable {
        final /* synthetic */ Context a;

        RunnableC0023a(Context context) {
            this.a = context;
        }

        @Override // java.lang.Runnable
        public final void run() {
            a.b(this.a);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v2, types: [java.lang.Class<ru.mail.libverify.v.a>] */
    /* JADX WARN: Type inference failed for: r0v3, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v5 */
    @NonNull
    public static ApiComponent c(@NonNull Context context) {
        if (b == null) {
            ?? r0 = a.class;
            synchronized (r0) {
                if (b == null) {
                    ApplicationModule a2 = a();
                    a2.setContext(context);
                    b = DaggerApiComponent.builder().applicationModule(a2).build();
                }
                r0 = r0;
            }
        }
        return b;
    }

    public static void a(@NonNull Runnable runnable) {
        e.add(runnable);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v4, types: [java.lang.Class<ru.mail.libverify.v.a>] */
    /* JADX WARN: Type inference failed for: r0v5, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v7 */
    private static ApiManager b(@NonNull Context context) {
        if (!c) {
            ?? r0 = a.class;
            synchronized (r0) {
                if (!c) {
                    Iterator<Runnable> it = e.iterator();
                    while (it.hasNext()) {
                        it.next().run();
                    }
                    c = true;
                }
                r0 = r0;
            }
        }
        return c(context).get();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v2, types: [java.lang.Class<ru.mail.libverify.v.a>] */
    /* JADX WARN: Type inference failed for: r0v3, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v5 */
    public static String d(@NonNull Context context) {
        String str;
        String str2;
        String str3;
        String str4;
        String str5;
        if (d == null) {
            ?? r0 = a.class;
            synchronized (r0) {
                if (d == null) {
                    String string = context.getResources().getString(R.string.libverify_server_id);
                    String str6 = string;
                    if (TextUtils.isEmpty(string)) {
                        String stringFromManifest = Utils.getStringFromManifest(context, VerificationFactory.LIBVERIFY_MANIFEST_SERVER_ID_KEY);
                        str6 = stringFromManifest;
                        boolean isDebugMode = a().provideStartConfig().isDebugMode();
                        if (TextUtils.isEmpty(stringFromManifest)) {
                            String pushSenderId = a().getOrInitPlatformService(context).getPushSenderId();
                            str = pushSenderId;
                            if (TextUtils.isEmpty(pushSenderId)) {
                                d = "empty";
                                if (isDebugMode) {
                                    str4 = "InternalFactory";
                                    str5 = "Libverify server id must be provided ether in an application Manifest or in libverify.xml (see VerificationFactory class javadoc)";
                                    FileLog.e(str4, str5);
                                } else {
                                    str2 = "InternalFactory";
                                    str3 = "Libverify server id must be provided ether in an application Manifest or in libverify.xml (see VerificationFactory class javadoc)";
                                    FileLog.d(str2, str3);
                                }
                            }
                            d = str;
                        } else if (str6.startsWith("server_id:")) {
                            String substring = str6.substring(10);
                            str = substring;
                            if (TextUtils.isEmpty(substring)) {
                                d = "empty";
                                if (isDebugMode) {
                                    str4 = "InternalFactory";
                                    str5 = "Libverify server id provided in Manifest is illegal";
                                    FileLog.e(str4, str5);
                                } else {
                                    str2 = "InternalFactory";
                                    str3 = "Libverify server id provided in Manifest is illegal";
                                    FileLog.d(str2, str3);
                                }
                            }
                            d = str;
                        }
                    }
                    d = str6;
                }
                r0 = r0;
            }
        }
        return d;
    }

    public static void a(@NonNull Context context) {
        Executors.newSingleThreadExecutor().submit(new RunnableC0023a(context));
    }

    public static void b(@NonNull Context context, @NonNull Message message) {
        b(context).postAndWait(message);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v2, types: [java.lang.Class<ru.mail.libverify.v.a>] */
    /* JADX WARN: Type inference failed for: r0v3, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v5 */
    public static ApplicationModule a() {
        if (a == null) {
            ?? r0 = a.class;
            synchronized (r0) {
                if (a == null) {
                    a = new ApplicationModule();
                }
                r0 = r0;
            }
        }
        return a;
    }

    public static void a(@NonNull Context context, @NonNull Message message) {
        b(context).post(message);
    }
}
