package ru.mail.verify.core.requests;

import androidx.annotation.NonNull;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.security.NoSuchAlgorithmException;
import ru.mail.verify.core.api.ApiPlugin;
import ru.mail.verify.core.utils.json.JsonParseException;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/requests/ActionExecutor.class */
public interface ActionExecutor extends ApiPlugin {
    @NonNull
    String execute(@NonNull RequestBase requestBase) throws UnsupportedEncodingException, NoSuchAlgorithmException, IllegalArgumentException, MalformedURLException, JsonParseException;

    @NonNull
    String execute(@NonNull RequestBase requestBase, int i) throws UnsupportedEncodingException, NoSuchAlgorithmException, IllegalArgumentException, MalformedURLException, JsonParseException;

    void remove(@NonNull String str);
}
