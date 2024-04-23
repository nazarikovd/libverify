package ru.mail.libverify.k;

import android.database.Cursor;
import ru.mail.libverify.k.m;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/k/f.class */
public final class f {
    private Cursor a;
    private int b;
    private int c;
    private int d;
    private int e;

    /* JADX INFO: Access modifiers changed from: package-private */
    public f(Cursor cursor) {
        this.a = cursor;
        this.b = cursor.getColumnIndex("_id");
        this.c = cursor.getColumnIndex("type");
        this.d = cursor.getColumnIndex("address");
        this.e = cursor.getColumnIndex("body");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean b() {
        return this.a.moveToFirst();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean c() {
        return this.a.moveToNext();
    }

    public final b a() {
        return new b(this.a.getLong(this.b), m.c.a(this.a.getInt(this.c)), this.a.getString(this.d), this.a.getString(this.e));
    }
}
