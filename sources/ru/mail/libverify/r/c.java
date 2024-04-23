package ru.mail.libverify.r;

import androidx.annotation.NonNull;
import java.util.Hashtable;
import ru.mail.libverify.gcm.ServerInfo;
import ru.mail.libverify.i.j;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/r/c.class */
public final class c {
    private Hashtable<String, Hashtable<ServerInfo, j.c>> a = new Hashtable<>();

    public final synchronized Boolean a(@NonNull String str, @NonNull ServerInfo serverInfo, @NonNull j.c cVar) {
        Hashtable<ServerInfo, j.c> hashtable;
        if (this.a.containsKey(str)) {
            hashtable = this.a.get(str);
        } else {
            hashtable = r1;
            Hashtable<ServerInfo, j.c> hashtable2 = new Hashtable<>();
            this.a.put(str, hashtable);
        }
        if (!hashtable.containsKey(serverInfo)) {
            hashtable.put(serverInfo, cVar);
            return Boolean.FALSE;
        } else if (cVar == hashtable.get(serverInfo)) {
            hashtable.put(serverInfo, cVar);
            return Boolean.FALSE;
        } else {
            hashtable.remove(serverInfo);
            return Boolean.TRUE;
        }
    }

    public final synchronized void a(String str) {
        if (this.a.containsKey(str)) {
            this.a.remove(str);
        }
    }
}
