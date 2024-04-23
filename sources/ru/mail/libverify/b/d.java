package ru.mail.libverify.b;

import dagger.Component;
import ru.mail.verify.core.api.ApiComponent;
import ru.mail.verify.core.api.ApplicationModule;

@Component(modules = {e.class, ApplicationModule.class}, dependencies = {ApiComponent.class})
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/b/d.class */
public interface d {
}
