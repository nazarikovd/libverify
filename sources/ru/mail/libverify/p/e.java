package ru.mail.libverify.p;

import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Message;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.lang.Thread;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TreeMap;
import ru.mail.libverify.api.CommonContext;
import ru.mail.libverify.api.VerificationApi;
import ru.mail.verify.core.api.ApiPlugin;
import ru.mail.verify.core.api.ComponentDispatcher;
import ru.mail.verify.core.storage.BroadcastManager;
import ru.mail.verify.core.utils.FileLog;
import ru.mail.verify.core.utils.components.BusMessageType;
import ru.mail.verify.core.utils.components.MessageBus;
import ru.mail.verify.core.utils.components.MessageBusUtils;
import ru.mail.verify.core.utils.components.MessageHandler;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/p/e.class */
public final class e implements MessageHandler, ApiPlugin {
    private final HashSet<VerificationApi.SmsDialogChangedListener> a = new HashSet<>();
    private final CommonContext b;
    private final b c;
    private final MessageBus d;
    private final ComponentDispatcher e;
    private TreeMap<Long, c> f;
    private HashMap<String, c> g;
    private SQLiteStatement h;
    private SQLiteStatement i;
    private SQLiteStatement j;
    private SQLiteStatement k;
    private SQLiteStatement l;
    private SQLiteStatement m;
    private SQLiteStatement n;

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/p/e$a.class */
    static /* synthetic */ class a {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[BusMessageType.values().length];
            a = iArr;
            try {
                iArr[BusMessageType.SMS_STORAGE_QUERY_SMS_DIALOGS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[BusMessageType.SMS_STORAGE_QUERY_SMS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[BusMessageType.SMS_STORAGE_REMOVE_SMS_DIALOG_ID.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                a[BusMessageType.SMS_STORAGE_REMOVE_SMS_DIALOG_NAME.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                a[BusMessageType.SMS_STORAGE_REMOVE_SMS_ID.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                a[BusMessageType.SMS_STORAGE_REMOVE_SMS_NAME.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                a[BusMessageType.SMS_STORAGE_INSERT_SMS.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                a[BusMessageType.SMS_STORAGE_CLEAR.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                a[BusMessageType.VERIFY_API_RESET.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                a[BusMessageType.API_RESET.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                a[BusMessageType.API_SHUTDOWN.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
        }
    }

    @Override // ru.mail.verify.core.api.ApiPlugin
    public final void initialize() {
        this.d.register(Arrays.asList(BusMessageType.API_RESET, BusMessageType.API_SHUTDOWN, BusMessageType.VERIFY_API_RESET), this);
    }

    public final synchronized void a(@NonNull VerificationApi.SmsDialogChangedListener smsDialogChangedListener) {
        this.a.add(smsDialogChangedListener);
    }

    @Override // ru.mail.verify.core.utils.components.MessageHandler
    public final boolean handleMessage(@NonNull Message message) {
        switch (a.a[MessageBusUtils.getType(message, "SmsStorage").ordinal()]) {
            case BroadcastManager.FLAG_NETWORK_RECEIVER /* 1 */:
                VerificationApi.SmsDialogsListener smsDialogsListener = (VerificationApi.SmsDialogsListener) MessageBusUtils.getArg(message, VerificationApi.SmsDialogsListener.class);
                try {
                    if (this.f == null) {
                        a((String) null, (Long) null);
                    }
                    ArrayList arrayList = new ArrayList(this.f.size());
                    for (c cVar : this.f.values()) {
                        arrayList.add(cVar);
                    }
                    Collections.sort(arrayList);
                    smsDialogsListener.onCompleted(arrayList);
                    this.d.post(MessageBusUtils.createOneArg(BusMessageType.SMS_STORAGE_SMS_DIALOGS_REQUESTED, (Object) null));
                    return true;
                } catch (Exception e) {
                    FileLog.e("SmsStorage", "failed to query sms dialogs", e);
                    smsDialogsListener.onError();
                    return true;
                }
            case 2:
                String str = (String) MessageBusUtils.getArg(message, String.class, 0);
                Long l = (Long) MessageBusUtils.getArg(message, Long.class, 1);
                Long l2 = (Long) MessageBusUtils.getArg(message, Long.class, 2);
                Integer num = (Integer) MessageBusUtils.getArg(message, Integer.class, 3);
                VerificationApi.SmsListener smsListener = (VerificationApi.SmsListener) MessageBusUtils.getArg(message, VerificationApi.SmsListener.class, 4);
                try {
                    c a2 = a(str, l);
                    if (a2 == null) {
                        smsListener.onError();
                    } else {
                        List<VerificationApi.SmsItem> b = b(a2, l2, num);
                        a(a2);
                        smsListener.onCompleted(b);
                    }
                    return true;
                } catch (Exception e2) {
                    Object[] objArr = new Object[1];
                    if (str == null) {
                        str = l;
                    }
                    objArr[0] = str;
                    FileLog.e("SmsStorage", e2, "failed to query sms for dialog %s", objArr);
                    smsListener.onError();
                    return true;
                }
            case 3:
                try {
                    c a3 = a((String) null, (Long) MessageBusUtils.getArg(message, Long.class));
                    if (a3 == null) {
                        return true;
                    }
                    b(a3);
                    return true;
                } catch (Exception e3) {
                    FileLog.e("SmsStorage", e3, "failed to remove sms dialog %s", null);
                    return true;
                }
            case 4:
                String str2 = (String) MessageBusUtils.getArg(message, String.class);
                try {
                    c a4 = str2 == null ? a((String) null, (Long) null) : a(str2, (Long) null);
                    if (a4 == null) {
                        return true;
                    }
                    b(a4);
                    return true;
                } catch (Exception e4) {
                    FileLog.e("SmsStorage", e4, "failed to remove sms dialog %s", str2);
                    return true;
                }
            case 5:
                Long l3 = (Long) MessageBusUtils.getArg(message, Long.class, 0);
                long longValue = ((Long) MessageBusUtils.getArg(message, Long.class, 1)).longValue();
                try {
                    c a5 = l3 == null ? a((String) null, (Long) null) : a((String) null, l3);
                    if (a5 == null) {
                        return true;
                    }
                    a(a5, longValue);
                    return true;
                } catch (Exception e5) {
                    FileLog.e("SmsStorage", e5, "failed to remove sms from dialog %d %d", l3, Long.valueOf(longValue));
                    return true;
                }
            case 6:
                String str3 = (String) MessageBusUtils.getArg(message, String.class, 0);
                long longValue2 = ((Long) MessageBusUtils.getArg(message, Long.class, 1)).longValue();
                try {
                    c a6 = a(str3, (Long) null);
                    if (a6 == null) {
                        return true;
                    }
                    a(a6, longValue2);
                    return true;
                } catch (Exception e6) {
                    FileLog.e("SmsStorage", e6, "failed to remove sms from dialog %d %d", null, Long.valueOf(longValue2));
                    return true;
                }
            case 7:
                String[] strArr = (String[]) MessageBusUtils.getArgArray(message, String.class, 0);
                String[] strArr2 = (String[]) MessageBusUtils.getArgArray(message, String.class, 1);
                Long[] lArr = (Long[]) MessageBusUtils.getArgArray(message, Long.class, 2);
                Long[] lArr2 = (Long[]) MessageBusUtils.getArgArray(message, Long.class, 3);
                try {
                    if (strArr.length == 1) {
                        Object[] objArr2 = new Object[4];
                        objArr2[0] = strArr[0];
                        objArr2[1] = strArr2[0];
                        objArr2[2] = lArr[0];
                        objArr2[3] = lArr2[0];
                        FileLog.v("SmsStorage", "insert sms from %s text %s timestamp %d (%d)", objArr2);
                    } else {
                        Object[] objArr3 = new Object[1];
                        objArr3[0] = Integer.valueOf(strArr.length);
                        FileLog.v("SmsStorage", "insert sms %d sms", objArr3);
                    }
                    SQLiteDatabase writableDatabase = this.c.getWritableDatabase();
                    HashMap<String, c> hashMap = new HashMap<>();
                    writableDatabase.beginTransaction();
                    a(writableDatabase, hashMap, strArr, lArr, strArr2, lArr2);
                    if (!hashMap.isEmpty()) {
                        for (c cVar2 : hashMap.values()) {
                            VerificationApi.SmsItem a7 = cVar2.a(false);
                            if (a7 != null) {
                                a(writableDatabase, cVar2, a7, true);
                            }
                        }
                    }
                    writableDatabase.setTransactionSuccessful();
                    a(hashMap);
                    for (c cVar3 : hashMap.values()) {
                        this.d.post(MessageBusUtils.createOneArg(BusMessageType.SMS_STORAGE_ADDED, cVar3.getFrom()));
                    }
                    if (strArr.length == 1) {
                        Object[] objArr4 = new Object[2];
                        objArr4[0] = strArr[0];
                        objArr4[1] = strArr2[0];
                        FileLog.v("SmsStorage", "sms from %s text %s has been inserted", objArr4);
                    } else {
                        Object[] objArr5 = new Object[1];
                        objArr5[0] = Integer.valueOf(strArr.length);
                        FileLog.v("SmsStorage", "sms inserted count %d", objArr5);
                    }
                    writableDatabase.endTransaction();
                    return true;
                } catch (Throwable th) {
                    FileLog.e("SmsStorage", "Failed to insert sms", th);
                    return true;
                }
            case 8:
                HashMap<String, c> hashMap2 = this.g;
                if (hashMap2 != null) {
                    hashMap2.clear();
                }
                TreeMap<Long, c> treeMap = this.f;
                if (treeMap != null) {
                    treeMap.clear();
                }
                this.h = null;
                this.i = null;
                this.j = null;
                this.k = null;
                this.l = null;
                this.m = null;
                this.n = null;
                this.c.close();
                if (!this.b.getConfig().getContext().deleteDatabase("verifications.db")) {
                    FileLog.e("SmsStorage", "failed to delete database");
                }
                a((VerificationApi.SmsDialogItem) null);
                this.d.post(MessageBusUtils.createOneArg(BusMessageType.SMS_STORAGE_CLEARED, (Object) null));
                FileLog.d("SmsStorage", "database has been dropped successfully");
                return true;
            case 9:
            case 10:
                a();
                return true;
            case 11:
                this.e.shutdown();
                return true;
            default:
                throw new IllegalArgumentException("StorageMsgType is not implemented");
        }
    }

    public e(@NonNull CommonContext commonContext, @NonNull Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.b = commonContext;
        this.d = commonContext.getBus();
        this.e = new ComponentDispatcher("libverify_storage_worker", this, uncaughtExceptionHandler);
        this.c = new b(commonContext.getConfig().getContext());
    }

    private List<VerificationApi.SmsItem> b(@NonNull c cVar, @Nullable Long l, @Nullable Integer num) {
        if (num == null) {
            a(cVar, (Long) null, (Integer) null);
            return cVar.a();
        } else if (l == null) {
            ArrayList a2 = cVar.a(num.intValue());
            if (a2.size() == 0) {
                a(cVar, (Long) null, num);
                return cVar.a(num.intValue());
            }
            return a2;
        } else {
            d dVar = (d) cVar.a(l.longValue());
            if (dVar == null) {
                a(cVar, l, num);
                return cVar.a(l.longValue(), num.intValue());
            }
            List<VerificationApi.SmsItem> a3 = cVar.a(dVar, num.intValue());
            if (a3.size() == 0) {
                a(cVar, Long.valueOf(dVar.getId()), num);
                return cVar.a(dVar.getId(), num.intValue());
            }
            return a3;
        }
    }

    public final void b(@Nullable String str, @Nullable Long l) {
        if (str == null && l == null) {
            throw new IllegalArgumentException("Either from or dialogId must be not null");
        }
        if (str != null) {
            this.e.getDispatcher().sendMessage(MessageBusUtils.createOneArg(BusMessageType.SMS_STORAGE_REMOVE_SMS_DIALOG_NAME, str));
        } else {
            this.e.getDispatcher().sendMessage(MessageBusUtils.createOneArg(BusMessageType.SMS_STORAGE_REMOVE_SMS_DIALOG_ID, l));
        }
    }

    public final void a() {
        this.e.getDispatcher().sendMessage(MessageBusUtils.createOneArg(BusMessageType.SMS_STORAGE_CLEAR, (Object) null));
    }

    public final void a(@NonNull String str, @NonNull String str2, long j, long j2) {
        this.e.getDispatcher().sendMessage(MessageBusUtils.createMultipleArgs(BusMessageType.SMS_STORAGE_INSERT_SMS, new String[]{str}, new String[]{str2}, new Long[]{Long.valueOf(j)}, new Long[]{Long.valueOf(j2)}));
    }

    public final synchronized void b(@NonNull VerificationApi.SmsDialogChangedListener smsDialogChangedListener) {
        this.a.remove(smsDialogChangedListener);
    }

    private void b(@NonNull VerificationApi.SmsDialogItem smsDialogItem) {
        SQLiteDatabase writableDatabase = this.c.getWritableDatabase();
        try {
            writableDatabase.beginTransaction();
            if (this.l == null) {
                this.l = writableDatabase.compileStatement(b.c);
            }
            SQLiteStatement sQLiteStatement = this.l;
            c cVar = (c) smsDialogItem;
            sQLiteStatement.bindLong(1, cVar.getId());
            if (sQLiteStatement.executeUpdateDelete() != 1) {
                FileLog.e("SmsStorage", "Failed to remove sms dialog " + cVar.getFrom());
                throw new SQLiteConstraintException("Failed to remove sms dialog");
            }
            if (this.m == null) {
                this.m = writableDatabase.compileStatement(b.f);
            }
            SQLiteStatement sQLiteStatement2 = this.m;
            sQLiteStatement2.bindLong(1, cVar.getId());
            if (sQLiteStatement2.executeUpdateDelete() < 1) {
                FileLog.e("SmsStorage", "Failed to remove sms for dialog " + cVar.getFrom());
                throw new SQLiteConstraintException("Failed to remove sms for dialog");
            }
            writableDatabase.setTransactionSuccessful();
            this.f.remove(Long.valueOf(cVar.getId()));
            this.g.remove(cVar.getFrom());
            a(smsDialogItem);
            this.d.post(MessageBusUtils.createOneArg(BusMessageType.SMS_STORAGE_SMS_DIALOG_REMOVED, cVar.getFrom()));
            Object[] objArr = new Object[1];
            objArr[0] = cVar.getFrom();
            FileLog.v("SmsStorage", "dialog %s has been removed", objArr);
        } finally {
            writableDatabase.endTransaction();
        }
    }

    private void a(@NonNull SQLiteDatabase sQLiteDatabase, @NonNull HashMap<String, c> hashMap, @NonNull String[] strArr, @NonNull Long[] lArr, @NonNull String[] strArr2, @NonNull Long[] lArr2) {
        for (int i = 0; i < strArr.length; i++) {
            c a2 = a(strArr[i], (Long) null);
            if (a2 == null) {
                FileLog.e("SmsStorage", "Failed to get dialog with name " + strArr[i]);
                throw new IllegalStateException("Failed to get dialog with name");
            }
            if (this.i == null) {
                this.i = sQLiteDatabase.compileStatement(b.e);
            }
            SQLiteStatement sQLiteStatement = this.i;
            sQLiteStatement.bindLong(1, lArr[i].longValue());
            sQLiteStatement.bindString(4, strArr2[i]);
            sQLiteStatement.bindLong(2, lArr2[i].longValue());
            sQLiteStatement.bindLong(3, a2.getId());
            a2.a(new d(strArr[i], strArr2[i], sQLiteStatement.executeInsert(), lArr[i].longValue(), lArr2[i].longValue()));
            hashMap.put(strArr[i], a2);
        }
        FileLog.v("SmsStorage", "%d sms inserted into %d dialogs", Integer.valueOf(strArr.length), Integer.valueOf(hashMap.size()));
    }

    private int a(long j, @Nullable Long l, @Nullable Integer num) {
        FileLog.v("SmsStorage", "load sms items offset %s, count %s", l, num);
        SQLiteDatabase readableDatabase = this.c.getReadableDatabase();
        String str = b.i;
        String[] strArr = new String[3];
        strArr[0] = Long.toString(l == null ? Long.MAX_VALUE : l.longValue());
        strArr[1] = Long.toString(j);
        strArr[2] = Integer.toString(num == null ? -1 : num.intValue());
        Cursor rawQuery = readableDatabase.rawQuery(str, strArr);
        try {
            if (!rawQuery.moveToFirst()) {
                rawQuery.close();
                return rawQuery.getCount();
            }
            do {
                long j2 = rawQuery.getLong(0);
                long j3 = rawQuery.getLong(3);
                long j4 = rawQuery.getLong(1);
                long j5 = rawQuery.getLong(2);
                String string = rawQuery.getString(4);
                c a2 = a((String) null, Long.valueOf(j3));
                if (a2 == null) {
                    Locale locale = Locale.US;
                    Object[] objArr = new Object[1];
                    objArr[0] = 0L;
                    FileLog.e("SmsStorage", String.format(locale, "Found a reference to not existing dialog id %d", objArr));
                    throw new IllegalStateException("Found a reference to not existing dialog id");
                }
                d dVar = new d(a2.getFrom(), string, j2, j4, j5);
                dVar.d();
                a2.a(dVar);
            } while (rawQuery.moveToNext());
            rawQuery.close();
            return rawQuery.getCount();
        } catch (Throwable th) {
            if (rawQuery != null) {
                rawQuery.close();
            }
            throw th;
        }
    }

    private c a(@Nullable String str, @Nullable Long l) {
        c cVar = null;
        TreeMap<Long, c> treeMap = this.f;
        if (treeMap == null) {
            FileLog.v("SmsStorage", "read dialogs from db");
            this.f = new TreeMap<>();
            this.g = new HashMap<>();
            Cursor cursor = null;
            try {
                Cursor rawQuery = this.c.getReadableDatabase().rawQuery(b.h, null);
                if (rawQuery.moveToFirst()) {
                    do {
                        long j = rawQuery.getLong(0);
                        String string = rawQuery.getString(1);
                        long j2 = rawQuery.getLong(2);
                        String string2 = rawQuery.getString(3);
                        Boolean valueOf = Boolean.valueOf(rawQuery.getInt(4) == 1);
                        c cVar2 = new c(string, j);
                        cVar2.a(string2);
                        cVar2.c(j2);
                        cVar2.b(valueOf.booleanValue());
                        this.f.put(Long.valueOf(j), cVar2);
                        if (this.g.put(string, cVar2) != null) {
                            FileLog.e("SmsStorage", String.format("Dialog %s has been already added", string));
                            throw new IllegalStateException("Dialog has been already added");
                        } else if (cVar == null && ((str != null && TextUtils.equals(str, string)) || (l != null && l.longValue() == j))) {
                            cVar = cVar2;
                        }
                    } while (rawQuery.moveToNext());
                    Object[] objArr = new Object[1];
                    objArr[0] = Integer.valueOf(this.f.size());
                    FileLog.d("SmsStorage", "found dialogs count %d", objArr);
                    rawQuery.close();
                } else {
                    Object[] objArr2 = new Object[1];
                    objArr2[0] = Integer.valueOf(this.f.size());
                    FileLog.d("SmsStorage", "found dialogs count %d", objArr2);
                    rawQuery.close();
                }
            } catch (Throwable th) {
                if (0 != 0) {
                    cursor.close();
                }
                throw th;
            }
        } else if (str != null) {
            cVar = this.g.get(str);
        } else if (l != null) {
            cVar = treeMap.get(l);
        }
        if (cVar == null && str != null) {
            SQLiteDatabase writableDatabase = this.c.getWritableDatabase();
            writableDatabase.beginTransaction();
            try {
                if (this.h == null) {
                    this.h = writableDatabase.compileStatement(b.a);
                }
                SQLiteStatement sQLiteStatement = this.h;
                sQLiteStatement.bindString(1, str);
                long executeInsert = sQLiteStatement.executeInsert();
                c cVar3 = new c(str, executeInsert);
                this.f.put(Long.valueOf(executeInsert), cVar3);
                if (this.g.put(str, cVar3) != null) {
                    FileLog.e("SmsStorage", String.format("Dialog %s has been already added", str));
                    throw new IllegalStateException("Dialog has been already added");
                }
                FileLog.d("SmsStorage", "added dialog from %s %s", str, cVar3);
                writableDatabase.setTransactionSuccessful();
                writableDatabase.endTransaction();
                cVar = cVar3;
            } catch (Throwable th2) {
                writableDatabase.endTransaction();
                throw th2;
            }
        }
        return cVar;
    }

    private void a(@NonNull c cVar, @Nullable Long l, @Nullable Integer num) {
        if (cVar.b()) {
            FileLog.v("SmsStorage", "load items is not necessary for dialog %s", cVar.getFrom());
        } else if (a(cVar.getId(), l, num) == 0) {
            FileLog.v("SmsStorage", "all items for dialog %s has been already loaded", cVar.getFrom());
            cVar.c();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [ru.mail.libverify.p.e] */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v6 */
    private void a(@Nullable VerificationApi.SmsDialogItem smsDialogItem) {
        ?? r0 = this;
        synchronized (r0) {
            Iterator<VerificationApi.SmsDialogChangedListener> it = r0.a.iterator();
            while (it.hasNext()) {
                it.next().onChanged(smsDialogItem);
            }
            r0 = this;
        }
    }

    private void a(@NonNull HashMap<String, c> hashMap) {
        if (hashMap.isEmpty()) {
            return;
        }
        synchronized (this) {
            for (c cVar : hashMap.values()) {
                Iterator<VerificationApi.SmsDialogChangedListener> it = this.a.iterator();
                while (it.hasNext()) {
                    it.next().onChanged(cVar);
                }
            }
        }
    }

    public final void a(@Nullable String str, @Nullable Long l, @Nullable Long l2, @Nullable Integer num, @NonNull VerificationApi.SmsListener smsListener) {
        if (str == null && l == null) {
            throw new IllegalArgumentException("Either from or dialogId must be not null");
        }
        this.e.getDispatcher().sendMessage(MessageBusUtils.createMultipleArgs(BusMessageType.SMS_STORAGE_QUERY_SMS, str, l, l2, num, smsListener));
    }

    public final void a(@NonNull VerificationApi.SmsDialogsListener smsDialogsListener) {
        this.e.getDispatcher().sendMessage(MessageBusUtils.createOneArg(BusMessageType.SMS_STORAGE_QUERY_SMS_DIALOGS, smsDialogsListener));
    }

    public final void a(@Nullable String str, @Nullable Long l, long j) {
        if (str == null && l == null) {
            throw new IllegalArgumentException("Either from or dialogId must be not null");
        }
        if (str != null) {
            this.e.getDispatcher().sendMessage(MessageBusUtils.createMultipleArgs(BusMessageType.SMS_STORAGE_REMOVE_SMS_NAME, str, Long.valueOf(j)));
        } else {
            this.e.getDispatcher().sendMessage(MessageBusUtils.createMultipleArgs(BusMessageType.SMS_STORAGE_REMOVE_SMS_ID, l, Long.valueOf(j)));
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x007f, code lost:
        if (r0 != null) goto L25;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void a(@androidx.annotation.NonNull ru.mail.libverify.p.c r15, long r16) {
        /*
            Method dump skipped, instructions count: 236
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: ru.mail.libverify.p.e.a(ru.mail.libverify.p.c, long):void");
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void a(@NonNull c cVar) {
        if (cVar.hasUnread()) {
            SQLiteDatabase writableDatabase = this.c.getWritableDatabase();
            try {
                writableDatabase.beginTransaction();
                if (this.k == null) {
                    this.k = writableDatabase.compileStatement(b.d);
                }
                SQLiteStatement sQLiteStatement = this.k;
                sQLiteStatement.bindLong(1, cVar.getId());
                if (sQLiteStatement.executeUpdateDelete() != 1) {
                    FileLog.e("SmsStorage", "Failed to update sms dialog " + cVar.getFrom());
                    throw new SQLiteConstraintException("Failed to update sms dialog");
                }
                cVar.b(false);
                writableDatabase.setTransactionSuccessful();
                Object[] objArr = new Object[1];
                objArr[0] = cVar.getFrom();
                FileLog.v("SmsStorage", "dialog %s marked as read", objArr);
                a((VerificationApi.SmsDialogItem) cVar);
                this.d.post(MessageBusUtils.createOneArg(BusMessageType.SMS_STORAGE_SMS_DIALOG_REQUESTED, cVar.getFrom()));
                writableDatabase.endTransaction();
            } catch (Throwable th) {
                th.endTransaction();
                throw this;
            }
        }
    }

    private void a(@NonNull SQLiteDatabase sQLiteDatabase, @NonNull c cVar, @NonNull VerificationApi.SmsItem smsItem, boolean z) {
        if (this.j == null) {
            this.j = sQLiteDatabase.compileStatement(b.b);
        }
        SQLiteStatement sQLiteStatement = this.j;
        d dVar = (d) smsItem;
        sQLiteStatement.bindLong(1, dVar.getTimestamp());
        sQLiteStatement.bindString(2, dVar.getText());
        sQLiteStatement.bindLong(3, z ? 1L : 0L);
        sQLiteStatement.bindLong(4, cVar.getId());
        if (sQLiteStatement.executeUpdateDelete() != 1) {
            FileLog.e("SmsStorage", "Failed to update sms dialog " + cVar.getFrom());
            throw new SQLiteConstraintException("Failed to update sms dialog");
        }
        cVar.a(dVar.getText());
        cVar.c(dVar.getTimestamp());
        if (z) {
            cVar.b(true);
            FileLog.v("SmsStorage", "dialog %s marked as unread", cVar.getFrom());
        }
        FileLog.v("SmsStorage", "dialog %s updated with last sms %s", cVar.getFrom(), dVar.getText());
    }
}
