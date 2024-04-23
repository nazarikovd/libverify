package ru.mail.libverify.h;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import ru.mail.libverify.R;
import ru.mail.libverify.api.VerificationApi;
import ru.mail.libverify.notifications.l;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/h/b.class */
public final class b extends ru.mail.libverify.h.a {
    private final VerificationApi.SmsDialogChangedListener f;
    private final int g;
    private final ArrayList<VerificationApi.SmsDialogItem> h;

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/h/b$a.class */
    class a implements VerificationApi.SmsDialogsListener {

        /* renamed from: ru.mail.libverify.h.b$a$a  reason: collision with other inner class name */
        /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/h/b$a$a.class */
        class RunnableC0008a implements Runnable {
            final /* synthetic */ List a;

            RunnableC0008a(List list) {
                this.a = list;
            }

            @Override // java.lang.Runnable
            public final void run() {
                b.this.d();
                if (this.a.isEmpty()) {
                    return;
                }
                b.this.h.addAll(this.a);
                b.this.notifyDataSetChanged();
            }
        }

        /* renamed from: ru.mail.libverify.h.b$a$b  reason: collision with other inner class name */
        /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/h/b$a$b.class */
        class RunnableC0009b implements Runnable {
            RunnableC0009b() {
            }

            @Override // java.lang.Runnable
            public final void run() {
                b.this.e();
            }
        }

        a() {
        }

        @Override // ru.mail.libverify.api.VerificationApi.SmsDialogsListener
        public final void onCompleted(@NonNull List<VerificationApi.SmsDialogItem> list) {
            b.this.a.post(new RunnableC0008a(list));
        }

        @Override // ru.mail.libverify.api.VerificationApi.SmsDialogsListener
        public final void onError() {
            b.this.a.post(new RunnableC0009b());
        }
    }

    /* renamed from: ru.mail.libverify.h.b$b  reason: collision with other inner class name */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/h/b$b.class */
    private class C0010b implements VerificationApi.SmsDialogChangedListener {

        /* renamed from: ru.mail.libverify.h.b$b$a */
        /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/h/b$b$a.class */
        class a implements Runnable {
            a() {
            }

            @Override // java.lang.Runnable
            public final void run() {
                b bVar = b.this;
                bVar.h.clear();
                bVar.notifyDataSetChanged();
            }
        }

        private C0010b() {
        }

        @Override // ru.mail.libverify.api.VerificationApi.SmsDialogChangedListener
        public final void onChanged(@Nullable VerificationApi.SmsDialogItem smsDialogItem) {
            b.this.a.post(new a());
        }
    }

    public b(@NonNull Context context, @NonNull VerificationApi verificationApi, @LayoutRes int i) {
        super(context, verificationApi);
        this.f = new C0010b();
        this.h = new ArrayList<>();
        this.g = i;
    }

    @Override // ru.mail.libverify.h.a
    public final void a() {
        this.c.addSmsDialogChangedListener(this.f);
        this.h.clear();
        notifyDataSetChanged();
        if (this.h.isEmpty()) {
            f();
        }
    }

    @Override // ru.mail.libverify.h.a
    public final void b() {
        this.c.removeSmsDialogChangedListener(this.f);
    }

    @Override // android.widget.Adapter
    public final int getCount() {
        if (this.h.isEmpty()) {
            f();
        }
        return this.h.size();
    }

    @Override // android.widget.Adapter
    public final Object getItem(int i) {
        return this.h.get(i);
    }

    @Override // android.widget.Adapter
    public final long getItemId(int i) {
        return this.h.get(i).getId();
    }

    @Override // android.widget.Adapter
    public final View getView(int i, View view, ViewGroup viewGroup) {
        VerificationApi.SmsDialogItem smsDialogItem = this.h.get(i);
        if (view == null) {
            view = LayoutInflater.from(this.b).inflate(this.g, viewGroup, false);
        }
        TextView textView = (TextView) view.findViewById(R.id.name);
        View view2 = view;
        TextView textView2 = (TextView) view2.findViewById(R.id.lastText);
        TextView textView3 = (TextView) view2.findViewById(R.id.lastTime);
        ((ImageView) view2.findViewById(R.id.image)).setColorFilter(l.a(smsDialogItem.getFrom()));
        textView.setText(smsDialogItem.getFrom());
        if (!TextUtils.isEmpty(smsDialogItem.getLastText())) {
            textView2.setText(smsDialogItem.getLastText());
            textView3.setText(c().format(new Date(smsDialogItem.getLastTimestamp())));
        }
        if (smsDialogItem.hasUnread()) {
            textView.setTypeface(null, 1);
            textView2.setTypeface(null, 1);
        } else {
            textView.setTypeface(null, 0);
            textView2.setTypeface(null, 0);
        }
        return view;
    }

    @Override // ru.mail.libverify.h.a
    protected final void g() {
        this.c.querySmsDialogs(new a());
    }
}
