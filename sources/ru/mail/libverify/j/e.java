package ru.mail.libverify.j;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;
import ru.mail.libverify.gcm.ServerInfo;
import ru.mail.libverify.j.c;
import ru.mail.verify.core.utils.Gsonable;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/j/e.class */
public final class e extends c {
    private final List<a> items;

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/j/e$a.class */
    public static class a implements Gsonable {
        private f fetcher_info;
        private String message;
        private String key;
        private c.b status;
        private ServerInfo server_info;

        @Nullable
        public final f a() {
            return this.fetcher_info;
        }

        @Nullable
        public final String c() {
            return this.message;
        }

        @Nullable
        public final String b() {
            return this.key;
        }

        @Nullable
        public final ServerInfo d() {
            return this.server_info;
        }
    }

    public e(@NonNull List<a> list) {
        this.items = list;
    }

    @NonNull
    public final List<a> e() {
        return this.items;
    }

    @Override // ru.mail.libverify.j.c
    @NonNull
    public final c.b d() {
        c.b bVar;
        for (a aVar : this.items) {
            if (aVar != null && (bVar = aVar.status) != c.b.OK) {
                return bVar;
            }
        }
        return c.b.OK;
    }
}
