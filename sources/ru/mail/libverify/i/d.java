package ru.mail.libverify.i;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import dagger.Lazy;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Locale;
import ru.mail.verify.core.api.ApplicationModule;
import ru.mail.verify.core.requests.ConstantRequestData;
import ru.mail.verify.core.requests.response.ResponseBase;
import ru.mail.verify.core.utils.ClientException;
import ru.mail.verify.core.utils.FileLog;
import ru.mail.verify.core.utils.HttpConnection;
import ru.mail.verify.core.utils.ServerException;
import ru.mail.verify.core.utils.json.JsonParseException;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/i/d.class */
public final class d extends f<ru.mail.libverify.j.d> {
    private static final ApplicationModule.ApplicationStartConfig i = ru.mail.libverify.v.a.a().provideStartConfig();
    private final Lazy<ru.mail.libverify.o.b> f;
    private int g;
    private String h;

    public d(@NonNull ru.mail.libverify.m.l lVar, @NonNull Lazy lazy, @NonNull String str, @NonNull String str2) {
        super(lVar.getContext(), lVar.getNetwork(), i, new ConstantRequestData(str, str2));
        this.h = "";
        this.f = lazy;
        this.g = 10000;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0079  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00d2  */
    /* JADX WARN: Type inference failed for: r0v24, types: [ru.mail.libverify.j.d] */
    /* JADX WARN: Type inference failed for: r0v5, types: [ru.mail.libverify.i.d, ru.mail.verify.core.requests.RequestBase] */
    /* JADX WARN: Type inference failed for: r0v6, types: [java.lang.Throwable] */
    @Override // ru.mail.verify.core.requests.RequestBase
    @androidx.annotation.NonNull
    /* renamed from: b */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final ru.mail.libverify.j.d execute() throws java.security.NoSuchAlgorithmException, java.lang.IllegalArgumentException, ru.mail.verify.core.utils.ClientException, ru.mail.verify.core.utils.ServerException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 212
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: ru.mail.libverify.i.d.execute():ru.mail.libverify.j.d");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // ru.mail.verify.core.requests.RequestBase
    @Nullable
    public final String getIfNoneMatchHeader() {
        return !TextUtils.isEmpty(this.h) ? this.h : super.getIfNoneMatchHeader();
    }

    @Override // ru.mail.verify.core.requests.RequestBase
    protected final Integer getReadTimeout() {
        return Integer.valueOf(this.g);
    }

    @Override // ru.mail.verify.core.requests.RequestBase
    protected final Integer getConnectTimeout() {
        return Integer.valueOf(this.g);
    }

    @Override // ru.mail.verify.core.requests.RequestBase
    public final String getUrl() {
        return this.e.getData();
    }

    @Override // ru.mail.verify.core.requests.RequestBase
    public final boolean canRunOffline() {
        return true;
    }

    @Override // ru.mail.verify.core.requests.RequestBase
    @NonNull
    public final String getApiNameForStatistics() {
        return "content";
    }

    @Override // ru.mail.verify.core.requests.RequestBase
    protected final ResponseBase readResponse(@NonNull HttpConnection httpConnection) throws ClientException, ServerException, IOException {
        ru.mail.libverify.j.d dVar;
        String headerField = httpConnection.getHeaderField("Content-Length");
        if (TextUtils.isEmpty(headerField)) {
            throw new ClientException("Empty content length", ClientException.ClientReason.DEFAULT);
        }
        try {
            long parseLong = Long.parseLong(headerField);
            Object[] objArr = new Object[2];
            objArr[0] = this.e.getData();
            objArr[1] = Long.valueOf(parseLong);
            FileLog.v("ContentApiRequest", "Expected content length for id: %s is %d", objArr);
            if (parseLong <= 5000000) {
                ru.mail.libverify.o.d b = ((ru.mail.libverify.o.b) this.f.get()).b(this.e.getData());
                if (b == null) {
                    FileLog.e("ContentApiRequest", "Failed to get an editor for id: %s", this.e.getData());
                    FileLog.d("ContentApiRequest", "Download content to memory for id: %s", this.e.getData());
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    httpConnection.downloadToStream(byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    String headerField2 = httpConnection.getHeaderField("ETag");
                    String str = headerField2;
                    dVar = r1;
                    ru.mail.libverify.j.d dVar2 = new ru.mail.libverify.j.d();
                    dVar2.a(new ByteArrayInputStream(byteArray));
                    if (headerField2 == null) {
                        str = "";
                    }
                    dVar.a(str);
                } else {
                    try {
                        ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
                        httpConnection.downloadToStream(byteArrayOutputStream2);
                        ByteArrayOutputStream byteArrayOutputStream3 = new ByteArrayOutputStream();
                        new ObjectOutputStream(byteArrayOutputStream3).writeObject(new ru.mail.libverify.o.a(httpConnection.getHeaderField("ETag"), byteArrayOutputStream2.toByteArray()));
                        if (b.c() != null) {
                            b.c().write(byteArrayOutputStream3.toByteArray());
                            b.b();
                        }
                        byte[] byteArray2 = byteArrayOutputStream2.toByteArray();
                        String headerField3 = httpConnection.getHeaderField("ETag");
                        String str2 = headerField3;
                        ru.mail.libverify.j.d dVar3 = new ru.mail.libverify.j.d();
                        dVar3.a(new ByteArrayInputStream(byteArray2));
                        if (headerField3 == null) {
                            str2 = "";
                        }
                        dVar3.a(str2);
                        dVar = dVar3;
                    } catch (Throwable th) {
                        b.a();
                        throw th;
                    }
                }
                return dVar;
            }
            Locale locale = Locale.US;
            Object[] objArr2 = new Object[2];
            objArr2[0] = Long.valueOf(parseLong);
            objArr2[1] = 5000000;
            FileLog.e("ContentApiRequest", String.format(locale, "Too big content length %d (max: %d)", objArr2));
            throw new ClientException("Too big content length", ClientException.ClientReason.DEFAULT);
        } catch (NumberFormatException unused) {
            throw new ClientException("Wrong content length format", ClientException.ClientReason.DEFAULT);
        }
    }

    @Override // ru.mail.verify.core.requests.RequestBase
    protected final /* bridge */ /* synthetic */ ResponseBase parseJsonAnswer(String str) throws JsonParseException {
        return null;
    }
}
