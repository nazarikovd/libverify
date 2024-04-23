package ru.mail.libverify.p;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.Locale;
import ru.mail.verify.core.utils.FileLog;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/p/b.class */
final class b extends SQLiteOpenHelper {
    static final String a;
    static final String b;
    static final String c;
    static final String d;
    static final String e;
    static final String f;
    static final String g;
    static String h;
    static String i;

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/p/b$a.class */
    class a implements DatabaseErrorHandler {
        a() {
        }

        @Override // android.database.DatabaseErrorHandler
        public final void onCorruption(SQLiteDatabase sQLiteDatabase) {
            FileLog.e("SmsDbHelper", "sms db has been corrupted");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public b(Context context) {
        super(context, "verifications.db", null, 1, new a());
    }

    static {
        Locale locale = Locale.US;
        a = String.format(locale, "INSERT INTO %s (%s) VALUES (?)", "dialog", "name");
        b = String.format(locale, "UPDATE %s SET %s = ?, %s = ?, %s = ? WHERE %s = ?", "dialog", "last_timestamp", "last_text", "has_unread", "_id");
        c = String.format(locale, "DELETE FROM %s WHERE %s = ?", "dialog", "_id");
        d = String.format(locale, "UPDATE %s SET %s = 0 WHERE %s = ?", "dialog", "has_unread", "_id");
        e = String.format(locale, "INSERT INTO %s (%s, %s, %s, %s) VALUES (?, ?, ?, ?)", "sms", "timestamp", "server_timestamp", "dialog_id", "text");
        f = String.format(locale, "DELETE FROM %s WHERE %s = ?", "sms", "dialog_id");
        g = String.format(locale, "DELETE FROM %s WHERE %s = ?", "sms", "_id");
        h = String.format(locale, "SELECT * FROM %s", "dialog");
        i = String.format(locale, "SELECT * FROM %s WHERE %s < ? AND %s = ? ORDER BY %s DESC LIMIT ?", "sms", "_id", "dialog_id", "server_timestamp");
    }

    @Override // android.database.sqlite.SQLiteOpenHelper, java.lang.AutoCloseable
    public final synchronized void close() {
        super.close();
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("CREATE TABLE sms (_id INTEGER PRIMARY KEY NOT NULL, timestamp INTEGER NOT NULL, server_timestamp INTEGER NOT NULL, dialog_id INTEGER NOT NULL, text TEXT, CONSTRAINT server_id UNIQUE (server_timestamp, dialog_id) )");
        sQLiteDatabase.execSQL("CREATE TABLE dialog (_id INTEGER PRIMARY KEY NOT NULL, name TEXT NOT NULL, last_timestamp INTEGER, last_text TEXT, has_unread INTEGER )");
        FileLog.d("SmsDbHelper", "sms db created");
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i2, int i3) {
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onDowngrade(SQLiteDatabase sQLiteDatabase, int i2, int i3) {
    }
}
