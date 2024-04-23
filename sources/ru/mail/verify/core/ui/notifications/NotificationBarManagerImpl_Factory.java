package ru.mail.verify.core.ui.notifications;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.inject.Provider;
import ru.mail.libverify.g.b;
import ru.mail.libverify.o.f;
import ru.mail.verify.core.api.ApiManager;
import ru.mail.verify.core.utils.components.MessageBus;

@ScopeMetadata
@DaggerGenerated
@QualifierMetadata
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/ui/notifications/NotificationBarManagerImpl_Factory.class */
public final class NotificationBarManagerImpl_Factory implements Factory<NotificationBarManagerImpl> {
    private final Provider<Context> contextProvider;
    private final Provider<MessageBus> busProvider;
    private final Provider<ApiManager> managerProvider;
    private final Provider<NotificationChannelSettings> notificationChannelSettingsProvider;
    private final Provider<b> notificationRepositoryProvider;
    private final Provider<f> imageDownloadManagerProvider;

    public NotificationBarManagerImpl_Factory(Provider<Context> contextProvider, Provider<MessageBus> busProvider, Provider<ApiManager> managerProvider, Provider<NotificationChannelSettings> notificationChannelSettingsProvider, Provider<b> provider, Provider<f> provider2) {
        this.contextProvider = contextProvider;
        this.busProvider = busProvider;
        this.managerProvider = managerProvider;
        this.notificationChannelSettingsProvider = notificationChannelSettingsProvider;
        this.notificationRepositoryProvider = provider;
        this.imageDownloadManagerProvider = provider2;
    }

    public static NotificationBarManagerImpl_Factory create(Provider<Context> contextProvider, Provider<MessageBus> busProvider, Provider<ApiManager> managerProvider, Provider<NotificationChannelSettings> notificationChannelSettingsProvider, Provider<b> provider, Provider<f> provider2) {
        return new NotificationBarManagerImpl_Factory(contextProvider, busProvider, managerProvider, notificationChannelSettingsProvider, provider, provider2);
    }

    public static NotificationBarManagerImpl newInstance(Context context, MessageBus messageBus, ApiManager apiManager, NotificationChannelSettings notificationChannelSettings, b bVar, f fVar) {
        return new NotificationBarManagerImpl(context, messageBus, apiManager, notificationChannelSettings, bVar, fVar);
    }

    /* renamed from: get */
    public NotificationBarManagerImpl m137get() {
        return newInstance((Context) this.contextProvider.get(), (MessageBus) this.busProvider.get(), (ApiManager) this.managerProvider.get(), (NotificationChannelSettings) this.notificationChannelSettingsProvider.get(), (b) this.notificationRepositoryProvider.get(), (f) this.imageDownloadManagerProvider.get());
    }
}
