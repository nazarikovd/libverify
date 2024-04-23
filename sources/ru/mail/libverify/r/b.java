package ru.mail.libverify.r;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import ru.mail.libverify.utils.PackageStateReceiver;
import ru.mail.verify.core.utils.FileLog;
import ru.mail.verify.core.utils.Utils;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/r/b.class */
public final class b {
    private final ResolveInfo a;
    private final PackageInfo b;
    private Integer c;

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/r/b$a.class */
    class a implements Comparator<b> {
        a() {
        }

        @Override // java.util.Comparator
        public final int compare(b bVar, b bVar2) {
            b bVar3 = bVar;
            b bVar4 = bVar2;
            int compareInt = Utils.compareInt(bVar3.a(), bVar4.a());
            int i = compareInt;
            if (compareInt == 0) {
                i = Utils.compareLong(bVar3.b(), bVar4.b());
            }
            return i;
        }
    }

    private b(@NonNull Context context, @NonNull ResolveInfo resolveInfo) throws PackageManager.NameNotFoundException {
        this.a = resolveInfo;
        this.b = context.getPackageManager().getPackageInfo(resolveInfo.activityInfo.packageName, 0);
    }

    public static boolean a(@NonNull Context context, @Nullable String str) {
        if (TextUtils.isEmpty(str)) {
            return true;
        }
        try {
            Intent intent = new Intent();
            intent.setAction(PackageStateReceiver.class.getName());
            List<ResolveInfo> queryBroadcastReceivers = context.getPackageManager().queryBroadcastReceivers(intent, 0);
            if (queryBroadcastReceivers.size() == 0) {
                return true;
            }
            ArrayList arrayList = new ArrayList(queryBroadcastReceivers.size());
            boolean z = false;
            for (ResolveInfo resolveInfo : queryBroadcastReceivers) {
                boolean z2 = z;
                try {
                    b bVar = new b(context, resolveInfo);
                    arrayList.add(bVar);
                    if (!z2 && TextUtils.equals(bVar.b.packageName, str)) {
                        z = true;
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    Object[] objArr = new Object[1];
                    objArr[0] = resolveInfo;
                    FileLog.e("PackageInfo", e, "failed to find info for package %s", objArr);
                }
            }
            if (z) {
                Collections.sort(arrayList, new a());
                int i = -1;
                int size = arrayList.size();
                for (int i2 = 0; i2 < arrayList.size(); i2++) {
                    String str2 = ((b) arrayList.get(i2)).b.packageName;
                    if (TextUtils.equals(str2, str)) {
                        i = i2;
                    } else if (TextUtils.equals(str2, context.getPackageName())) {
                        size = i2;
                    }
                }
                return size > i;
            }
            return true;
        } catch (Throwable th) {
            FileLog.e("PackageInfo", "failed to query packages info", th);
            return true;
        }
    }

    private long b() {
        return this.b.lastUpdateTime;
    }

    @NonNull
    public final String toString() {
        return "PackageInfo{name=" + this.b.packageName + ", lastUpdateTime=" + this.b.lastUpdateTime + ", instanceVersion=" + a() + '}';
    }

    private int a() {
        if (this.c == null) {
            ActivityInfo activityInfo = this.a.activityInfo;
            if (activityInfo == null || TextUtils.isEmpty(activityInfo.nonLocalizedLabel)) {
                return 0;
            }
            String[] split = this.a.activityInfo.nonLocalizedLabel.toString().split(":");
            if (split.length != 2) {
                return 0;
            }
            this.c = Integer.valueOf(Integer.parseInt(split[1]));
        }
        return this.c.intValue();
    }
}
