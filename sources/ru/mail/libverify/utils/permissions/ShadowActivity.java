package ru.mail.libverify.utils.permissions;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.NonNull;

@TargetApi(23)
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/utils/permissions/ShadowActivity.class */
public class ShadowActivity extends Activity {
    @Override // android.app.Activity
    protected final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle == null) {
            requestPermissions(getIntent().getStringArrayExtra("permissions"), Integer.valueOf(getIntent().getIntExtra("request_id", 0)).intValue());
        }
    }

    @Override // android.app.Activity
    public final void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        a.a(i, strArr, iArr);
        finish();
    }
}
