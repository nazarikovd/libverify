package ru.mail.libverify.storage;

import android.content.Context;
import androidx.annotation.NonNull;
import java.io.File;
import java.io.RandomAccessFile;
import ru.mail.verify.core.utils.DebugUtils;
import ru.mail.verify.core.utils.FileLog;
import ru.mail.verify.core.utils.Utils;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/storage/g.class */
final class g {
    private int a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public g(@NonNull Context context) {
        this.a = 0;
        this.a = a(context);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean a() {
        return (this.a & 1) == 1;
    }

    private static int a(@NonNull Context context) {
        try {
            File file = new File(Utils.getInstallationDir(context), "VERIFY_INSTALLATION_FLAGS");
            if (!file.exists()) {
                return 0;
            }
            RandomAccessFile randomAccessFile = null;
            try {
                RandomAccessFile randomAccessFile2 = new RandomAccessFile(file, "r");
                try {
                    int readInt = randomAccessFile2.readInt();
                    randomAccessFile2.close();
                    return readInt;
                } catch (Throwable th) {
                    th = th;
                    randomAccessFile = randomAccessFile2;
                    if (randomAccessFile != null) {
                        randomAccessFile.close();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Throwable th3) {
            DebugUtils.safeThrow("InstallationFlags", "failed to read installation flags file", new RuntimeException(th3));
            return 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void a(@NonNull Context context, boolean z) {
        RandomAccessFile randomAccessFile;
        if (z != a()) {
            if (z) {
                this.a |= 1;
            } else {
                this.a &= -2;
            }
            FileLog.d("InstallationFlags", "no encryption option set to value %s", Boolean.valueOf(z));
            RandomAccessFile randomAccessFile2 = null;
            try {
                randomAccessFile = new RandomAccessFile(new File(Utils.getInstallationDir(context), "VERIFY_INSTALLATION_FLAGS"), "rw");
            } catch (Throwable th) {
                th = th;
            }
            try {
                randomAccessFile.writeInt(this.a);
                try {
                    randomAccessFile.close();
                } catch (Throwable th2) {
                    DebugUtils.safeThrow("InstallationFlags", "failed to save installation flags file", new RuntimeException(th2));
                }
            } catch (Throwable th3) {
                th = th3;
                randomAccessFile2 = randomAccessFile;
                if (randomAccessFile2 != null) {
                    randomAccessFile2.close();
                }
                throw th;
            }
        }
    }
}
