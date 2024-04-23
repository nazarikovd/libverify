package ru.mail.verify.core.api;

import dagger.Lazy;
import java.util.List;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/api/ApiGroup.class */
public interface ApiGroup {
    List<Lazy<ApiPlugin>> getPlugins();

    void initialize();
}
