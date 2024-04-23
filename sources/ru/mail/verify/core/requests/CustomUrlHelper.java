package ru.mail.verify.core.requests;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import ru.mail.verify.core.utils.FileLog;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/requests/CustomUrlHelper.class */
public final class CustomUrlHelper {
    private final URL a;
    private String b;
    private String c;

    public CustomUrlHelper(@NonNull String url) throws MalformedURLException {
        this.a = new URL(url);
    }

    public String getApiHost() {
        if (TextUtils.isEmpty(this.c)) {
            String url = this.a.toString();
            int indexOf = url.indexOf(this.a.getPath());
            if (indexOf == -1) {
                this.c = url;
            } else {
                this.c = url.substring(0, indexOf);
            }
        }
        return this.c;
    }

    public String getApiPath() {
        return this.a.getPath();
    }

    public String getMethodName() {
        if (TextUtils.isEmpty(this.b)) {
            String path = this.a.getPath();
            if (TextUtils.isEmpty(path)) {
                FileLog.e("CustomUrlHelper", String.format(Locale.US, "Can't get method name from provided URL: %s", this.a));
                throw new IllegalArgumentException("Can't get method name from provided URL");
            }
            int lastIndexOf = path.lastIndexOf(47);
            if (lastIndexOf < 0) {
                this.b = path;
            } else if (lastIndexOf == path.length() - 1) {
                FileLog.e("CustomUrlHelper", String.format(Locale.US, "Can't get method name from provided URL: %s", this.a));
                throw new IllegalArgumentException("Can't get method name from provided URL");
            } else {
                this.b = path.substring(lastIndexOf + 1);
            }
        }
        return this.b;
    }

    public String getQuery() {
        return this.a.getQuery();
    }
}
