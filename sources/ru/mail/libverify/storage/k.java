package ru.mail.libverify.storage;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import ru.mail.verify.core.utils.DebugUtils;
import ru.mail.verify.core.utils.FileLog;
import ru.mail.verify.core.utils.Utils;
import ru.mail.verify.core.utils.json.JsonParseException;
import ru.mail.verify.core.utils.json.JsonParser;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/storage/k.class */
final class k {
    private static ru.mail.libverify.j.l a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static synchronized boolean b(@NonNull Context context) {
        return new File(Utils.getInstallationDir(context), "SMS_TEMPLATES").exists();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    public static synchronized ru.mail.libverify.j.l a(@NonNull Context context) {
        ru.mail.libverify.j.l lVar = a;
        if (lVar != null) {
            return lVar;
        }
        try {
            if (new File(Utils.getInstallationDir(context), "SMS_TEMPLATES").exists()) {
                FileLog.v("SmsTemplatesStorage", "start file read");
                long nanoTime = System.nanoTime();
                String readFile = Utils.readFile(new File(Utils.getInstallationDir(context), "SMS_TEMPLATES"));
                if (!TextUtils.isEmpty(readFile)) {
                    a = (ru.mail.libverify.j.l) JsonParser.fromJson(readFile, ru.mail.libverify.j.l.class);
                    Object[] objArr = new Object[1];
                    objArr[0] = Long.valueOf((System.nanoTime() - nanoTime) / 1000000);
                    FileLog.v("SmsTemplatesStorage", "file read competed (%d ms)", objArr);
                }
            }
        } catch (IOException e) {
            e = e;
            Log.e("SmsTemplatesStorage", "Failed to read sms info file", e);
        } catch (JsonParseException e2) {
            e = e2;
            Log.e("SmsTemplatesStorage", "Failed to read sms info file", e);
        } catch (Throwable th) {
            DebugUtils.safeThrow("SmsTemplatesStorage", "Failed to read sms info file", th);
        }
        return a;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static synchronized void a(@NonNull Context context, @Nullable ru.mail.libverify.j.l lVar) {
        try {
            a = lVar;
            File file = new File(Utils.getInstallationDir(context), "SMS_TEMPLATES");
            if (lVar != null) {
                FileLog.v("SmsTemplatesStorage", "start file write");
                long currentTimeMillis = System.currentTimeMillis();
                Utils.writeFile(file, JsonParser.toJson(lVar));
                Object[] objArr = new Object[1];
                objArr[0] = Long.valueOf((System.nanoTime() - currentTimeMillis) / 1000000);
                FileLog.v("SmsTemplatesStorage", "file write competed (%d ms)", objArr);
            } else if (file.exists()) {
                boolean delete = file.delete();
                boolean z = delete;
                if (!delete) {
                    File file2 = new File(Utils.getInstallationDir(context), "SMS_TEMPLATES_TMP");
                    boolean renameTo = file.renameTo(file2);
                    z = renameTo ? file2.delete() : renameTo;
                }
                FileLog.d("SmsTemplatesStorage", "sms info delete result " + z);
            }
        } catch (IOException e) {
            e = e;
            Log.e("SmsTemplatesStorage", "Failed to write sms info file", e);
        } catch (JsonParseException e2) {
            e = e2;
            Log.e("SmsTemplatesStorage", "Failed to write sms info file", e);
        } catch (Throwable th) {
            DebugUtils.safeThrow("SmsTemplatesStorage", "Failed to write sms info file", th);
        }
    }
}
