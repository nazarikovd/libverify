package ru.mail.libverify.h;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.DrawableCompat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import ru.mail.libverify.R;
import ru.mail.libverify.api.VerificationApi;
import ru.mail.libverify.notifications.l;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/h/c.class */
public final class c extends ru.mail.libverify.h.a {
    private final VerificationApi.SmsDialogChangedListener f;
    private final long g;
    private final int h;
    private int i;
    private final ArrayList<VerificationApi.SmsItem> j;

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/h/c$a.class */
    class a implements VerificationApi.SmsListener {

        /* renamed from: ru.mail.libverify.h.c$a$a  reason: collision with other inner class name */
        /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/h/c$a$a.class */
        class RunnableC0011a implements Runnable {
            final /* synthetic */ List a;

            RunnableC0011a(List list) {
                this.a = list;
            }

            @Override // java.lang.Runnable
            public final void run() {
                c.this.d();
                if (this.a.isEmpty()) {
                    return;
                }
                c.this.j.addAll(this.a);
                c.this.notifyDataSetChanged();
            }
        }

        /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/h/c$a$b.class */
        class b implements Runnable {
            b() {
            }

            @Override // java.lang.Runnable
            public final void run() {
                c.this.e();
            }
        }

        a() {
        }

        @Override // ru.mail.libverify.api.VerificationApi.SmsListener
        public final void onCompleted(@NonNull List<VerificationApi.SmsItem> list) {
            c.this.a.post(new RunnableC0011a(list));
        }

        @Override // ru.mail.libverify.api.VerificationApi.SmsListener
        public final void onError() {
            c.this.a.post(new b());
        }
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/h/c$b.class */
    private class b implements VerificationApi.SmsDialogChangedListener {

        /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/h/c$b$a.class */
        class a implements Runnable {
            a() {
            }

            @Override // java.lang.Runnable
            public final void run() {
                c cVar = c.this;
                cVar.j.clear();
                cVar.notifyDataSetChanged();
            }
        }

        private b() {
        }

        @Override // ru.mail.libverify.api.VerificationApi.SmsDialogChangedListener
        public final void onChanged(@Nullable VerificationApi.SmsDialogItem smsDialogItem) {
            if (smsDialogItem == null || smsDialogItem.getId() == c.this.g) {
                c.this.a.post(new a());
            }
        }
    }

    public c(@NonNull Context context, @NonNull VerificationApi verificationApi, long j, @LayoutRes int i) {
        super(context, verificationApi);
        this.f = new b();
        this.j = new ArrayList<>(50);
        this.g = j;
        this.h = i;
    }

    @Override // ru.mail.libverify.h.a
    public final void a() {
        this.c.addSmsDialogChangedListener(this.f);
        if (this.j.isEmpty()) {
            return;
        }
        this.i = this.j.size();
        this.j.clear();
        notifyDataSetChanged();
    }

    @Override // ru.mail.libverify.h.a
    public final void b() {
        this.c.removeSmsDialogChangedListener(this.f);
    }

    @Override // android.widget.Adapter
    public final int getCount() {
        if (this.j.isEmpty()) {
            f();
        }
        return this.j.size();
    }

    @Override // android.widget.Adapter
    public final Object getItem(int i) {
        if (i >= this.j.size() / 2) {
            f();
        }
        return this.j.get(i);
    }

    @Override // android.widget.Adapter
    public final long getItemId(int i) {
        if (i >= this.j.size() / 2) {
            f();
        }
        return this.j.get(i).getId();
    }

    @Override // android.widget.Adapter
    public final View getView(int i, View view, ViewGroup viewGroup) {
        if (i >= this.j.size() / 2) {
            f();
        }
        VerificationApi.SmsItem smsItem = this.j.get(i);
        if (view == null) {
            View inflate = LayoutInflater.from(this.b).inflate(this.h, viewGroup, false);
            view = inflate;
            ((ImageView) inflate.findViewById(R.id.image)).setColorFilter(l.a(smsItem.getFrom()));
            Drawable wrap = DrawableCompat.wrap(view.getResources().getDrawable(R.drawable.sms_background));
            DrawableCompat.setTint(wrap, l.a(smsItem.getFrom()));
            l.a((RelativeLayout) inflate.findViewById(R.id.textArea), wrap);
        }
        View view2 = view;
        ((TextView) view2.findViewById(R.id.text)).setText(smsItem.getText());
        ((TextView) view2.findViewById(R.id.time)).setText(c().format(new Date(smsItem.getTimestamp())));
        return view2;
    }

    @Override // ru.mail.libverify.h.a
    protected final void g() {
        Long valueOf;
        int i;
        VerificationApi verificationApi = this.c;
        Long valueOf2 = Long.valueOf(this.g);
        if (this.j.isEmpty()) {
            valueOf = null;
        } else {
            ArrayList<VerificationApi.SmsItem> arrayList = this.j;
            valueOf = Long.valueOf(arrayList.get(arrayList.size() - 1).getId());
        }
        verificationApi.querySms(null, valueOf2, valueOf, Integer.valueOf((!this.j.isEmpty() || (i = this.i) == 0 || i <= 50) ? 50 : i + 50), new a());
    }

    @Override // ru.mail.libverify.h.a
    public final void a(int i) {
        this.i = i;
    }
}
