package ru.mail.verify.core.api;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.inject.Provider;
import ru.mail.libverify.platform.storage.KeyValueStorage;
import ru.mail.verify.core.platform.TimeProvider;

@ScopeMetadata
@DaggerGenerated
@QualifierMetadata
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/api/ApplicationModule_ProvideCurrentTimeProviderFactory.class */
public final class ApplicationModule_ProvideCurrentTimeProviderFactory implements Factory<TimeProvider> {
    private final ApplicationModule module;
    private final Provider<KeyValueStorage> settingsProvider;

    public ApplicationModule_ProvideCurrentTimeProviderFactory(ApplicationModule module, Provider<KeyValueStorage> settingsProvider) {
        this.module = module;
        this.settingsProvider = settingsProvider;
    }

    public static ApplicationModule_ProvideCurrentTimeProviderFactory create(ApplicationModule module, Provider<KeyValueStorage> settingsProvider) {
        return new ApplicationModule_ProvideCurrentTimeProviderFactory(module, settingsProvider);
    }

    public static TimeProvider provideCurrentTimeProvider(ApplicationModule instance, KeyValueStorage settings) {
        return (TimeProvider) Preconditions.checkNotNullFromProvides(instance.provideCurrentTimeProvider(settings));
    }

    /* renamed from: get */
    public TimeProvider m100get() {
        return provideCurrentTimeProvider(this.module, (KeyValueStorage) this.settingsProvider.get());
    }
}
