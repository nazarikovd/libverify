package ru.mail.libverify.n;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/n/c.class */
final class c {
    static final Charset a = Charset.forName("US-ASCII");

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(File file) throws IOException {
        File[] listFiles = file.listFiles();
        if (listFiles == null) {
            throw new IOException("not a readable directory: " + file);
        }
        for (File file2 : listFiles) {
            if (file2.isDirectory()) {
                a(file2);
            }
            if (!file2.delete()) {
                throw new IOException("failed to delete file: " + file2);
            }
        }
    }

    static {
        Charset.forName("UTF-8");
    }
}
