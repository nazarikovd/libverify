package ru.mail.verify.core.api;

import dagger.Binds;
import dagger.Module;
import javax.inject.Singleton;
import ru.mail.verify.core.accounts.SimCardReader;
import ru.mail.verify.core.accounts.SimCardReaderImpl;
import ru.mail.verify.core.storage.LocationProvider;
import ru.mail.verify.core.storage.LocationProviderImpl;
import ru.mail.verify.core.storage.LockManager;
import ru.mail.verify.core.storage.LockManagerImpl;
import ru.mail.verify.core.utils.SessionIdGenerator;
import ru.mail.verify.core.utils.SessionIdGeneratorImpl;
import ru.mail.verify.core.utils.components.MessageBus;
import ru.mail.verify.core.utils.components.MessageBusImpl;

@Module
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/api/ApiModule.class */
abstract class ApiModule {
    ApiModule() {
    }

    @Singleton
    @Binds
    abstract ApiManager provideApiManager(b bVar);

    @Singleton
    @Binds
    abstract LockManager provideLockManager(LockManagerImpl lockManagerImpl);

    @Singleton
    @Binds
    abstract AlarmManager provideAlarmManager(a aVar);

    @Singleton
    @Binds
    abstract MessageBus provideMessageBus(MessageBusImpl messageBusImpl);

    @Singleton
    @Binds
    abstract SessionIdGenerator provideSessionIdGenerator(SessionIdGeneratorImpl sessionIdGeneratorImpl);

    @Singleton
    @Binds
    abstract LocationProvider provideLocationProvider(LocationProviderImpl locationProviderImpl);

    @Singleton
    @Binds
    abstract SimCardReader provideSimCardReader(SimCardReaderImpl simCardReaderImpl);
}
