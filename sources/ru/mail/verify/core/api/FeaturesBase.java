package ru.mail.verify.core.api;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.Locale;
import java.util.Map;
import ru.mail.libverify.platform.storage.KeyValueStorage;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/api/FeaturesBase.class */
public abstract class FeaturesBase {
    protected final KeyValueStorage storage;

    public FeaturesBase(@NonNull KeyValueStorage storage) {
        this.storage = storage;
    }

    public boolean isEnabled(@NonNull String name) {
        Boolean bool = getDefault().get(name);
        if (bool != null) {
            Integer integerValue = this.storage.getIntegerValue(name, (Integer) null);
            return integerValue == null ? bool.booleanValue() : integerValue.intValue() == 1;
        }
        throw new IllegalArgumentException(String.format(Locale.US, "Feature %s is not defined", name));
    }

    public void set(@NonNull String name, @Nullable Boolean value) {
        if (value == null) {
            return;
        }
        this.storage.putValue(name, value.booleanValue() ? 1 : 0);
        process(name, value.booleanValue());
    }

    public void reset() {
        for (Map.Entry<String, Boolean> entry : getDefault().entrySet()) {
            this.storage.removeValue(entry.getKey());
        }
    }

    @NonNull
    protected abstract Map<String, Boolean> getDefault();

    protected void process(@NonNull String name, boolean value) {
    }
}
