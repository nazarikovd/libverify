package ru.mail.libverify.k;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import ru.mail.libverify.k.c;
import ru.mail.verify.core.utils.FileLog;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/k/d.class */
public final class d implements c {
    private final Context a;

    public d(@NonNull Context context) {
        this.a = context;
    }

    @NonNull
    public final List<k> a(@NonNull c.a aVar) throws InterruptedException {
        int a = aVar.a();
        int b = aVar.b();
        long c = aVar.c();
        long d = aVar.d();
        Cursor cursor = null;
        FileLog.d("KnownSmsFinder", "start query with params: depth %d, maxCount %d, maxTimeShift %d, minTimeShift %d", Integer.valueOf(a), Integer.valueOf(b), Long.valueOf(c), Long.valueOf(d));
        try {
            try {
                Cursor query = this.a.getContentResolver().query(a.b, null, null, null, "date DESC LIMIT " + a);
                if (query == null) {
                    List<k> list = Collections.EMPTY_LIST;
                    if (query != null) {
                        query.close();
                    }
                    return list;
                }
                int columnIndex = query.getColumnIndex("address");
                int columnIndex2 = query.getColumnIndex("body");
                int columnIndex3 = query.getColumnIndex("date");
                HashSet hashSet = new HashSet();
                ArrayList arrayList = new ArrayList();
                if (!query.moveToFirst()) {
                    List<k> list2 = Collections.EMPTY_LIST;
                    query.close();
                    return list2;
                }
                int i = 0;
                while (i <= a) {
                    long currentTimeMillis = System.currentTimeMillis() - query.getLong(columnIndex3);
                    if (currentTimeMillis >= 0 && currentTimeMillis >= d) {
                        if (currentTimeMillis > c) {
                            break;
                        }
                        String string = query.getString(columnIndex);
                        String string2 = query.getString(columnIndex2);
                        if (aVar.b(string)) {
                            String a2 = aVar.a(string2);
                            if (!TextUtils.isEmpty(a2) && !hashSet.contains(a2)) {
                                hashSet.add(a2);
                                arrayList.add(new k(query.getLong(columnIndex3), string, a2));
                            }
                        }
                        if (arrayList.size() > b) {
                            break;
                        }
                        i++;
                    }
                    if (!query.moveToNext()) {
                        break;
                    }
                }
                query.close();
                return arrayList;
            } catch (InterruptedException e) {
                FileLog.e("KnownSmsFinder", "query sms database interrupted", e);
                throw e;
            } catch (Throwable th) {
                FileLog.e("KnownSmsFinder", "failed to query sms database", th);
                if (0 != 0) {
                    cursor.close();
                }
                return Collections.EMPTY_LIST;
            }
        } catch (Throwable th2) {
            if (0 != 0) {
                cursor.close();
            }
            throw th2;
        }
    }
}
