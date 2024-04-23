package ru.mail.libverify.utils.permissions;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.LongSparseArray;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import ru.mail.verify.core.utils.FileLog;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/utils/permissions/a.class */
public final class a {
    private static final AtomicInteger a = new AtomicInteger(0);
    private static final LongSparseArray<InterfaceC0022a> b = new LongSparseArray<>();

    /* renamed from: ru.mail.libverify.utils.permissions.a$a  reason: collision with other inner class name */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/utils/permissions/a$a.class */
    public interface InterfaceC0022a {
        void a(@NonNull String str);

        void b(@NonNull String str);

        void onCompleted(boolean z);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.Class<ru.mail.libverify.utils.permissions.a>] */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v6 */
    public static void a(int i, String[] strArr, int[] iArr) {
        InterfaceC0022a interfaceC0022a;
        ?? r0 = a.class;
        synchronized (r0) {
            LongSparseArray<InterfaceC0022a> longSparseArray = b;
            long j = i;
            interfaceC0022a = longSparseArray.get(j);
            if (interfaceC0022a != null) {
                longSparseArray.remove(j);
            }
            r0 = interfaceC0022a;
        }
        if (r0 != 0) {
            int i2 = 0;
            for (int i3 = 0; i3 < strArr.length; i3++) {
                if (iArr[i3] == 0) {
                    interfaceC0022a.a(strArr[i3]);
                    i2++;
                } else {
                    interfaceC0022a.b(strArr[i3]);
                }
            }
            interfaceC0022a.onCompleted(i2 == strArr.length);
        }
    }

    @TargetApi(23)
    public static void a(@NonNull Context context, @NonNull String[] strArr, @NonNull InterfaceC0022a interfaceC0022a) {
        FileLog.v("PermissionRequester", "income permissions %s", Arrays.toString(strArr));
        if (Build.VERSION.SDK_INT < 23) {
            for (String str : strArr) {
                interfaceC0022a.a(str);
            }
            interfaceC0022a.onCompleted(true);
            return;
        }
        ArrayList arrayList = new ArrayList(strArr.length);
        for (String str2 : strArr) {
            if (ContextCompat.checkSelfPermission(context, str2) == 0) {
                FileLog.v("PermissionRequester", "permission granted %s", str2);
                interfaceC0022a.a(str2);
            } else {
                arrayList.add(str2);
            }
        }
        if (arrayList.isEmpty()) {
            FileLog.v("PermissionRequester", "no permissions to request");
            interfaceC0022a.onCompleted(true);
            return;
        }
        FileLog.v("PermissionRequester", "permissions to request %s", arrayList);
        Intent intent = new Intent(context, ShadowActivity.class);
        int andIncrement = a.getAndIncrement();
        synchronized (a.class) {
            b.put(andIncrement, interfaceC0022a);
        }
        intent.setFlags(268435456);
        intent.putExtra("request_id", andIncrement);
        intent.putExtra("permissions", (String[]) arrayList.toArray(new String[arrayList.size()]));
        context.startActivity(intent);
    }
}
