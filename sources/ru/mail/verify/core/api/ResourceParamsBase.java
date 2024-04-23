package ru.mail.verify.core.api;

import android.content.Context;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import java.util.Locale;
import ru.mail.verify.core.utils.FileLog;
import ru.mail.verify.core.utils.Utils;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/api/ResourceParamsBase.class */
public abstract class ResourceParamsBase {
    protected final Context context;
    protected String name;
    protected String key;
    protected String serverId;
    private volatile boolean a = false;

    public ResourceParamsBase(@NonNull Context context) {
        this.context = context;
    }

    public String getName() {
        if (!this.a) {
            synchronized (this) {
                if (!this.a) {
                    loadFromResources();
                    this.a = true;
                }
            }
        }
        return this.name;
    }

    public String getKey() {
        if (!this.a) {
            synchronized (this) {
                if (!this.a) {
                    loadFromResources();
                    this.a = true;
                }
            }
        }
        return this.key;
    }

    public String getServerId() {
        if (!this.a) {
            synchronized (this) {
                if (!this.a) {
                    loadFromResources();
                    this.a = true;
                }
            }
        }
        return this.serverId;
    }

    protected abstract void loadFromResources();

    /* JADX INFO: Access modifiers changed from: protected */
    public String getFromResources(int resId, @NonNull String sdkName, @NonNull String resName, @NonNull String manifestName, boolean checkNotEmpty) {
        String string = this.context.getString(resId);
        String str = string;
        if (TextUtils.isEmpty(string)) {
            str = Utils.getStringFromManifest(this.context, manifestName);
        }
        if (checkNotEmpty && TextUtils.isEmpty(str)) {
            FileLog.e("ResourceParamsBase", String.format(Locale.US, "String resource must be set in file %s.xml ('%s') or in Manifest ('%s')", sdkName, resName, manifestName));
            throw new IllegalArgumentException("String resource must be set in resource file or in Manifest");
        }
        return str;
    }
}
