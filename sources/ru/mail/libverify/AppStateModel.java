package ru.mail.libverify;

import android.util.Log;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ProcessLifecycleOwner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.mail.verify.core.storage.BroadcastManager;
import ru.mail.verify.core.utils.FileLog;

@Metadata(mv = {BroadcastManager.FLAG_NETWORK_RECEIVER, 7, BroadcastManager.FLAG_NETWORK_RECEIVER}, k = BroadcastManager.FLAG_NETWORK_RECEIVER, d1 = {"��$\n\u0002\u0018\u0002\n\u0002\u0010��\n\u0002\u0018\u0002\n��\n\u0002\u0018\u0002\n��\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\bÀ\u0002\u0018��2\u00020\u0001:\u0002\r\u000eB\t\b\u0002¢\u0006\u0004\b\u000b\u0010\fJ\b\u0010\u0003\u001a\u00020\u0002H\u0007J\u0010\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0004H\u0007J\u0012\u0010\b\u001a\u00020\u00062\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004H\u0007J\b\u0010\n\u001a\u00020\tH\u0007¨\u0006\u000f"}, d2 = {"Lru/mail/libverify/AppStateModel;", "", "Lru/mail/libverify/AppStateModel$b;", "getState", "Lru/mail/libverify/AppStateModel$a;", "listener", "", "register", "unregister", "", "canUseLifecycle", "<init>", "()V", "a", "b", "libverify_release"})
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/AppStateModel.class */
public final class AppStateModel {
    @Nullable
    private static LifecycleEventObserver a;
    @NotNull
    public static final AppStateModel INSTANCE = new AppStateModel();
    @NotNull
    private static final ArrayList<a> b = new ArrayList<>();
    @NotNull
    private static final AtomicReference<b> c = new AtomicReference<>(b.UNTRACKED);

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/AppStateModel$a.class */
    public interface a {
        void onResume();

        void a();
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/AppStateModel$b.class */
    public enum b {
        ACTIVE,
        INACTIVE,
        UNTRACKED
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/AppStateModel$c.class */
    public /* synthetic */ class c {
        public static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[Lifecycle.Event.values().length];
            try {
                iArr[Lifecycle.Event.ON_RESUME.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Lifecycle.Event.ON_PAUSE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            a = iArr;
        }
    }

    private AppStateModel() {
    }

    @JvmStatic
    @NotNull
    public static final b getState() {
        b bVar = c.get();
        Intrinsics.checkNotNullExpressionValue(bVar, "appState.get()");
        return bVar;
    }

    @JvmStatic
    public static final void register(@NotNull a listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        if (!canUseLifecycle()) {
            Log.e("AppStateListener", "androidx.lifecycle doesn't present in this app.");
            return;
        }
        try {
            if (!(a instanceof LifecycleEventObserver)) {
                LifecycleEventObserver lifecycleEventObserver = AppStateModel::a;
                ProcessLifecycleOwner.Companion.get().getLifecycle().addObserver(lifecycleEventObserver);
                a = lifecycleEventObserver;
            }
            b.add(listener);
        } catch (Throwable th) {
            FileLog.e("AppStateListener", "Failed to execute AppStateModel#register", th);
        }
    }

    @JvmStatic
    public static final void unregister(@Nullable a listener) {
        try {
            ArrayList<a> arrayList = b;
            TypeIntrinsics.asMutableCollection(arrayList).remove(listener);
            if (arrayList.isEmpty()) {
                if (a instanceof LifecycleEventObserver) {
                    Lifecycle lifecycle = ProcessLifecycleOwner.Companion.get().getLifecycle();
                    LifecycleEventObserver lifecycleEventObserver = a;
                    Intrinsics.checkNotNull(lifecycleEventObserver, "null cannot be cast to non-null type androidx.lifecycle.LifecycleEventObserver");
                    lifecycle.removeObserver(lifecycleEventObserver);
                }
                a = null;
                c.set(b.UNTRACKED);
            }
        } catch (Throwable th) {
            FileLog.e("AppStateListener", "Failed to execute AppStateModel#unregister", th);
        }
    }

    @JvmStatic
    public static final boolean canUseLifecycle() {
        Class<?> cls;
        try {
            INSTANCE.getClass();
            Class a2 = a("androidx.lifecycle.ProcessLifecycleOwner");
            if (a2 == null) {
                return false;
            }
            Class a3 = a("androidx.lifecycle.LifecycleEventObserver");
            if (a3 == null) {
                return false;
            }
            Class a4 = a("androidx.lifecycle.Lifecycle");
            if (a4 == null) {
                return false;
            }
            Class a5 = a("androidx.lifecycle.Lifecycle$Event");
            if (a5 == null) {
                return false;
            }
            Class a6 = a("androidx.lifecycle.LifecycleObserver");
            if (a6 == null) {
                return false;
            }
            Class a7 = a("androidx.lifecycle.LifecycleOwner");
            if (a7 == null) {
                return false;
            }
            try {
                cls = a2.getDeclaredField("Companion").getType();
            } catch (NoSuchFieldException unused) {
                cls = null;
            }
            Class[] clsArr = new Class[0];
            INSTANCE.getClass();
            if (!a(a2, "get", clsArr)) {
                if ((cls == null || a(cls, "get", new Class[0])) ? false : true) {
                    return false;
                }
            }
            if (a(a7, "getLifecycle", new Class[0]) && a(a4, "removeObserver", a6) && a(a4, "addObserver", a6)) {
                return a(a3, "onStateChanged", a7, a5);
            }
            return false;
        } catch (Throwable th) {
            FileLog.e("AppStateListener", "Failed to invoke canUseLifecycle", th);
            return false;
        }
    }

    private static Class a(String str) {
        Class<?> cls;
        try {
            cls = Class.forName(str);
        } catch (ClassNotFoundException unused) {
            cls = null;
        }
        return cls;
    }

    private static boolean a(Class cls, String str, Class... clsArr) {
        boolean z;
        try {
            cls.getMethod(str, (Class[]) Arrays.copyOf(clsArr, clsArr.length));
            return true;
        } catch (Exception unused) {
            return z;
        }
    }

    private static final void a(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
        Intrinsics.checkNotNullParameter(lifecycleOwner, "<anonymous parameter 0>");
        Intrinsics.checkNotNullParameter(event, "event");
        int i = c.a[event.ordinal()];
        if (i == 1) {
            c.set(b.ACTIVE);
            for (a aVar : b) {
                aVar.onResume();
            }
        } else if (i == 2) {
            c.set(b.INACTIVE);
            for (a aVar2 : b) {
                aVar2.a();
            }
        }
    }
}
