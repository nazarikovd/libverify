package ru.mail.verify.core.api;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.inject.Provider;
import ru.mail.verify.core.api.ApplicationModule;

@ScopeMetadata
@DaggerGenerated
@QualifierMetadata
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/api/AlarmManagerImpl_Factory.class */
public final class AlarmManagerImpl_Factory implements Factory<a> {
    private final Provider<Context> contextProvider;
    private final Provider<ApplicationModule.NetworkPolicyConfig> configProvider;

    public AlarmManagerImpl_Factory(Provider<Context> contextProvider, Provider<ApplicationModule.NetworkPolicyConfig> configProvider) {
        this.contextProvider = contextProvider;
        this.configProvider = configProvider;
    }

    public static AlarmManagerImpl_Factory create(Provider<Context> contextProvider, Provider<ApplicationModule.NetworkPolicyConfig> configProvider) {
        return new AlarmManagerImpl_Factory(contextProvider, configProvider);
    }

    public static a newInstance(Context context, ApplicationModule.NetworkPolicyConfig config) {
        return new a(context, config);
    }

    /* renamed from: get */
    public a m97get() {
        return newInstance((Context) this.contextProvider.get(), (ApplicationModule.NetworkPolicyConfig) this.configProvider.get());
    }
}
