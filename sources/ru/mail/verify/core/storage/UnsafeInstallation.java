package ru.mail.verify.core.storage;

import android.content.Context;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.core.util.AtomicFile;
import java.io.File;
import ru.mail.verify.core.storage.InstallationHelper;
import ru.mail.verify.core.utils.DebugUtils;
import ru.mail.verify.core.utils.FileLog;
import ru.mail.verify.core.utils.Utils;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/storage/UnsafeInstallation.class */
public final class UnsafeInstallation {
    private static String a;
    private static final InstallationHelper b = new InstallationHelper();
    private static File c;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v19, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r0v3, types: [ru.mail.verify.core.storage.InstallationHelper] */
    /* JADX WARN: Type inference failed for: r0v4, types: [java.lang.Throwable] */
    public static synchronized String id(@NonNull Context context) {
        String generateId;
        String str;
        if (TextUtils.isEmpty(a)) {
            ?? r0 = b;
            r0.setIdState(InstallationHelper.IDState.INITIALIZING);
            try {
                if (c == null) {
                    c = new File(Utils.getInstallationDir(context), "NOTIFY_INSTALLATION");
                }
                File file = c;
                if (file.exists()) {
                    String readAtomicTextFile = Utils.readAtomicTextFile(file);
                    a = readAtomicTextFile;
                    if (TextUtils.isEmpty(readAtomicTextFile)) {
                        reset(context);
                        generateId = InstallationHelper.generateId();
                        str = generateId;
                    }
                    r0.setIdState(InstallationHelper.IDState.HAS_INSTALLATION);
                } else {
                    generateId = InstallationHelper.generateId();
                    str = generateId;
                }
                a = generateId;
                r0 = str;
                Utils.writeAtomicTextFile(r0, file);
                r0.setIdState(InstallationHelper.IDState.HAS_INSTALLATION);
            } catch (Throwable th) {
                DebugUtils.safeThrow("UnsafeInstallation", "failed to create installation file", new RuntimeException(th));
                reset(context);
                a = InstallationHelper.generateId();
                b.setIdState(InstallationHelper.IDState.HAS_INSTALLATION);
            }
        }
        return a;
    }

    public static boolean hasInstallation(@NonNull Context context) {
        InstallationHelper installationHelper = b;
        if (c == null) {
            c = new File(Utils.getInstallationDir(context), "NOTIFY_INSTALLATION");
        }
        return installationHelper.hasInstallation(c);
    }

    /* JADX WARN: Type inference failed for: r0v4, types: [java.lang.Throwable, ru.mail.verify.core.storage.InstallationHelper] */
    public static synchronized void reset(@NonNull Context context) {
        try {
            ?? r0 = b;
            r0.setIdState(InstallationHelper.IDState.RESETTING);
            a = null;
            if (c == null) {
                c = new File(Utils.getInstallationDir(context), "NOTIFY_INSTALLATION");
            }
            new AtomicFile(c).delete();
            FileLog.d("UnsafeInstallation", "installation file deleted");
            r0.setIdState(InstallationHelper.IDState.NO_INSTALLATION);
        } catch (Throwable th) {
            FileLog.e("UnsafeInstallation", "failed to reset installation file", th);
            b.setIdState(InstallationHelper.IDState.NO_INSTALLATION);
        }
    }
}
