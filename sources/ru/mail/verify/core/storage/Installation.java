package ru.mail.verify.core.storage;

import android.content.Context;
import androidx.annotation.NonNull;
import java.io.File;
import ru.mail.verify.core.utils.Utils;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/storage/Installation.class */
public abstract class Installation {
    protected static final String LOG_TAG = "Installation";
    protected static final String INSTALLATION_FILE_NAME = "VERIFY_INSTALLATION";
    protected static final InstallationHelper idHelper = new InstallationHelper();

    public static boolean hasInstallation(@NonNull Context context) {
        return idHelper.hasInstallation(getInstallationFile(context));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static File getInstallationFile(@NonNull Context context) {
        return new File(Utils.getInstallationDir(context), INSTALLATION_FILE_NAME);
    }
}
