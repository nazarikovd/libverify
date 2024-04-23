package ru.mail.verify.core.api;

import dagger.Lazy;
import java.util.LinkedList;
import java.util.List;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/api/PluginListBuilder.class */
public final class PluginListBuilder {
    private final LinkedList<Lazy<ApiPlugin>> a = new LinkedList<>();

    public <T extends ApiPlugin> PluginListBuilder add(Lazy<T> plugin) {
        this.a.add(plugin);
        return this;
    }

    public List<Lazy<ApiPlugin>> build() {
        return this.a;
    }
}
