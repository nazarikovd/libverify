package ru.mail.libverify.q;

import java.util.HashSet;
import java.util.List;
import javax.inject.Inject;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import ru.mail.libverify.m.l;
import ru.mail.libverify.platform.storage.KeyValueStorage;
import ru.mail.verify.core.utils.json.JsonParser;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/q/d.class */
public final class d implements c {
    @NotNull
    private final l a;

    @Inject
    public d(@NotNull l lVar) {
        Intrinsics.checkNotNullParameter(lVar, "instanceData");
        this.a = lVar;
    }

    @Override // ru.mail.libverify.q.c
    @NotNull
    /* renamed from: a */
    public final HashSet<a> get() {
        try {
            String value = this.a.getSettings().getValue("api_start_timings_data");
            String str = value;
            if (value == null) {
                str = "[]";
            }
            List listFromJson = JsonParser.listFromJson(str, a.class);
            Intrinsics.checkNotNullExpressionValue(listFromJson, "timings");
            return CollectionsKt.toHashSet(listFromJson);
        } catch (Throwable unused) {
            return new HashSet<>();
        }
    }

    @Override // ru.mail.libverify.q.c
    public final void clear() {
        this.a.getSettings().removeValue("api_start_timings_data").commit();
    }

    @Override // ru.mail.libverify.q.c
    public final void a(@NotNull a aVar) {
        Intrinsics.checkNotNullParameter(aVar, "element");
        HashSet<a> hashSet = get();
        hashSet.remove(aVar);
        hashSet.add(aVar);
        KeyValueStorage settings = this.a.getSettings();
        String json = JsonParser.toJson(hashSet);
        Intrinsics.checkNotNullExpressionValue(json, "toJson(hashSet)");
        settings.putValue("api_start_timings_data", json).commit();
    }
}
