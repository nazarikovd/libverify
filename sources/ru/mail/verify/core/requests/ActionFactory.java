package ru.mail.verify.core.requests;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.net.MalformedURLException;
import ru.mail.verify.core.utils.json.JsonParseException;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/requests/ActionFactory.class */
public interface ActionFactory {
    @Nullable
    RequestBase createRequest(@NonNull ActionDescriptor actionDescriptor) throws MalformedURLException, JsonParseException;

    @Nullable
    ActionDescriptor createDescriptor(@NonNull RequestBase requestBase) throws JsonParseException;
}
