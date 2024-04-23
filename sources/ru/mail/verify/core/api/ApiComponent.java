package ru.mail.verify.core.api;

import dagger.Component;
import javax.inject.Singleton;
import ru.mail.verify.core.accounts.SimCardReader;
import ru.mail.verify.core.storage.LocationProvider;
import ru.mail.verify.core.storage.LockManager;
import ru.mail.verify.core.utils.SessionIdGenerator;
import ru.mail.verify.core.utils.components.MessageBus;

@Singleton
@Component(modules = {ApiModule.class, ApplicationModule.class})
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/api/ApiComponent.class */
public interface ApiComponent {
    ApiManager get();

    LockManager getLock();

    MessageBus getBus();

    AlarmManager getAlarmManager();

    LocationProvider getLocation();

    SessionIdGenerator getSessionIdGenerator();

    SimCardReader getSimCardReader();
}
