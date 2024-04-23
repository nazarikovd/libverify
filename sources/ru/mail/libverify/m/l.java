package ru.mail.libverify.m;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.Locale;
import java.util.Map;
import ru.mail.libverify.platform.storage.KeyValueStorage;
import ru.mail.libverify.storage.InstanceConfig;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/m/l.class */
public interface l extends InstanceConfig {
    void resetId();

    @NonNull
    KeyValueStorage getSettings();

    void prepare();

    void setCustomLocale(@NonNull Locale locale);

    void setSimDataSendDisabled(boolean z);

    boolean setApiEndpoints(@NonNull Map<String, String> map);

    boolean b(@NonNull String str);

    void c();

    @NonNull
    InstanceConfig e();

    void acquireLock(@NonNull Object obj, boolean z, int i);

    void releaseLock(@NonNull Object obj);

    void releaseAllLocks();

    void d();

    void f();

    void a(boolean z);

    void a(@NonNull String str, @Nullable Boolean bool);

    boolean c(@NonNull String str);

    void a(@Nullable ru.mail.libverify.j.l lVar);

    @Nullable
    ru.mail.libverify.j.l b();

    boolean a();

    boolean a(@NonNull String str);

    boolean sendApplicationBroadcast(@NonNull String str, @Nullable Map<String, String> map);
}
