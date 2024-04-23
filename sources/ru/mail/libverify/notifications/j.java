package ru.mail.libverify.notifications;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import ru.mail.libverify.R;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/notifications/j.class */
class j extends ru.mail.libverify.g.a {
    private ru.mail.libverify.h.a a;
    private ListView b;
    private int c = 0;
    private final DataSetObserver d = new a();

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/notifications/j$a.class */
    class a extends DataSetObserver {
        a() {
        }

        @Override // android.database.DataSetObserver
        public final void onChanged() {
            ru.mail.libverify.h.a aVar;
            super.onChanged();
            j.this.invalidateOptionsMenu();
            j jVar = j.this;
            if (jVar.b == null || (aVar = jVar.a) == null) {
                return;
            }
            if (jVar.c != 0) {
                int count = aVar.getCount();
                j jVar2 = j.this;
                int i = jVar2.c;
                if (count >= i) {
                    jVar2.b.setSelection(i);
                    j.this.c = 0;
                    return;
                }
            }
            j jVar3 = j.this;
            if (jVar3.c == 0 && jVar3.a.isEmpty()) {
                j jVar4 = j.this;
                jVar4.c = jVar4.b.getFirstVisiblePosition();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void a(@NonNull ListView listView, @NonNull ru.mail.libverify.h.a aVar) {
        this.a = aVar;
        this.b = listView;
        aVar.a(this.c);
        this.b.setAdapter((ListAdapter) aVar);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sms_dialogs_menu, menu);
        MenuItem findItem = menu.findItem(R.id.history_clear);
        ru.mail.libverify.h.a aVar = this.a;
        if (aVar == null || aVar.getCount() == 0) {
            findItem.setEnabled(false);
            findItem.setVisible(false);
            return true;
        }
        findItem.setEnabled(true);
        findItem.setVisible(true);
        return true;
    }

    protected final void onPause() {
        super/*androidx.fragment.app.FragmentActivity*/.onPause();
        ru.mail.libverify.h.a aVar = this.a;
        if (aVar != null) {
            aVar.unregisterDataSetObserver(this.d);
            this.a.b();
        }
    }

    protected final void onDestroy() {
        super.onDestroy();
        ListView listView = this.b;
        if (listView != null) {
            listView.setAdapter((ListAdapter) null);
        }
    }

    protected final void onResume() {
        super/*androidx.fragment.app.FragmentActivity*/.onResume();
        ru.mail.libverify.h.a aVar = this.a;
        if (aVar != null) {
            aVar.registerDataSetObserver(this.d);
            this.a.a();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onCreate(@Nullable Bundle bundle) {
        super/*androidx.fragment.app.FragmentActivity*/.onCreate(bundle);
        if (bundle != null) {
            this.c = bundle.getInt("list_position");
        }
    }

    protected final void onSaveInstanceState(Bundle bundle) {
        super/*androidx.activity.ComponentActivity*/.onSaveInstanceState(bundle);
        ListView listView = this.b;
        if (listView != null) {
            int firstVisiblePosition = listView.getFirstVisiblePosition();
            this.c = firstVisiblePosition;
            bundle.putInt("list_position", firstVisiblePosition);
        }
    }
}
