package ru.mail.libverify.api;

import android.content.Context;
import android.view.accessibility.AccessibilityManager;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/b.class */
public class b {
    public static boolean a(Context context) throws Exception {
        return !((AccessibilityManager) context.getSystemService("accessibility")).getEnabledAccessibilityServiceList(-1).isEmpty();
    }
}
