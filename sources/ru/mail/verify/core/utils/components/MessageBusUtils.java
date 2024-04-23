package ru.mail.verify.core.utils.components;

import android.os.Message;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import dagger.Lazy;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import ru.mail.verify.core.utils.FileLog;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/utils/components/MessageBusUtils.class */
public class MessageBusUtils {
    private static final String LOG_TAG = "MessageBusUtils";
    public static final BusMessageType[] values = BusMessageType.values();

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/utils/components/MessageBusUtils$TraceType.class */
    public enum TraceType {
        EXTENDED,
        NORMAL,
        NONE
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/utils/components/MessageBusUtils$a.class */
    public static /* synthetic */ class a {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[TraceType.values().length];
            a = iArr;
            try {
                iArr[TraceType.EXTENDED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[TraceType.NORMAL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[TraceType.NONE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public static BusMessageType getType(@NonNull Message msg, @NonNull String logTag) {
        return getType(msg, logTag, TraceType.NORMAL);
    }

    public static Message createCopyMessage(BusMessageType type, @NonNull Message msg) {
        Message obtain = Message.obtain();
        obtain.what = msg.what;
        obtain.obj = msg.obj;
        return createOneArg(type.ordinal(), obtain);
    }

    public static Message createOneArg(int type, @Nullable Object arg) {
        Message obtain = Message.obtain();
        obtain.what = type;
        obtain.obj = arg;
        return obtain;
    }

    public static Message createMultipleArgs(int type, @NonNull Object... arguments) {
        Message obtain = Message.obtain();
        obtain.what = type;
        obtain.obj = arguments;
        return obtain;
    }

    @NonNull
    public static <T> T getArg(@NonNull Message msg, @NonNull Class<T> type) {
        T t = (T) msg.obj;
        if (t != null) {
            return t;
        }
        FileLog.e(LOG_TAG, String.format(Locale.US, "Argument must be non null (%s)", values[msg.what]));
        throw new IllegalArgumentException("Argument must not be null");
    }

    @NonNull
    public static <T> Collection<T> getArgCollection(@NonNull Message msg, @NonNull Class<T> type) {
        Object obj = msg.obj;
        if (obj != null) {
            return (Collection) obj;
        }
        FileLog.e(LOG_TAG, String.format(Locale.US, "Argument collection must be non null (%s)", values[msg.what]));
        throw new IllegalArgumentException("Argument collection must be non null");
    }

    @NonNull
    public static <T> List<T> getArgList(@NonNull Message msg, @NonNull Class<T> type) {
        Object obj = msg.obj;
        if (obj != null) {
            return (List) obj;
        }
        FileLog.e(LOG_TAG, String.format(Locale.US, "Argument list must be non null (%s)", values[msg.what]));
        throw new IllegalArgumentException("Argument list must be non null");
    }

    @NonNull
    public static <T> T[] getArgArray(@NonNull Message msg, @NonNull Class<T> type) {
        Object obj = msg.obj;
        if (obj != null) {
            return (T[]) ((Object[]) obj);
        }
        FileLog.e(LOG_TAG, String.format(Locale.US, "Argument array must be non null (%s)", values[msg.what]));
        throw new IllegalArgumentException("Argument array must be non null");
    }

    @NonNull
    public static <K, V> Map<K, V> getArgMap(@NonNull Message msg, @NonNull Class<K> kType, @NonNull Class<V> vType) {
        Object obj = msg.obj;
        if (obj != null) {
            return (Map) obj;
        }
        FileLog.e(LOG_TAG, String.format(Locale.US, "Argument map must be non null (%s)", values[msg.what]));
        throw new IllegalArgumentException("Argument map must be non null");
    }

    @NonNull
    public static <T> Collection<Lazy<T>> getArgLazyCollection(@NonNull Message msg, @NonNull Class<T> type) {
        Object obj = msg.obj;
        if (obj != null) {
            return (Collection) obj;
        }
        FileLog.e(LOG_TAG, String.format(Locale.US, "Argument lazy collection must be non null (%s)", values[msg.what]));
        throw new IllegalArgumentException("Argument lazy collection must be non null");
    }

    @Nullable
    public static <T> T getNullableArg(@NonNull Message msg, @NonNull Class<T> type) {
        T t = (T) msg.obj;
        if (t == null) {
            return null;
        }
        return t;
    }

    public static BusMessageType getType(@NonNull Message msg, @NonNull String logTag, TraceType trace) {
        int i = msg.what;
        BusMessageType[] busMessageTypeArr = values;
        if (i < busMessageTypeArr.length && i >= 0) {
            BusMessageType busMessageType = busMessageTypeArr[i];
            int i2 = a.a[trace.ordinal()];
            if (i2 == 1) {
                Object obj = msg.obj;
                if (obj == null || !obj.getClass().isArray()) {
                    FileLog.v(logTag, "handle msg %s (data = %s)", busMessageType, msg.obj);
                } else {
                    FileLog.v(logTag, "handle msg %s (data = %s)", busMessageType, Arrays.toString((Object[]) msg.obj));
                }
            } else if (i2 == 2) {
                FileLog.v(logTag, "handle msg %s (data = %s)", busMessageType, msg.obj);
            }
            return busMessageType;
        }
        throw new IllegalArgumentException("msg.what must be a member of BusMessageType");
    }

    public static Message createOneArg(BusMessageType type, @Nullable Object arg) {
        return createOneArg(type.ordinal(), arg);
    }

    public static Message createMultipleArgs(BusMessageType type, @NonNull Object... arguments) {
        return createMultipleArgs(type.ordinal(), arguments);
    }

    @NonNull
    public static <K, V> Map<K, V> getArgMap(@NonNull Message msg, @NonNull Class<K> kType, @NonNull Class<V> vType, int index) {
        Object obj = msg.obj;
        if (obj == null || !(obj instanceof Object[])) {
            FileLog.e(LOG_TAG, String.format(Locale.US, "Argument map must be non null (%s)", values[msg.what]));
            throw new IllegalArgumentException("Argument map must be non null");
        }
        return (Map) ((Object[]) obj)[index];
    }

    @Nullable
    public static <T> T getArg(@NonNull Message msg, @NonNull Class<T> type, int index) {
        Object obj = msg.obj;
        if (obj == null || !(obj instanceof Object[])) {
            FileLog.e(LOG_TAG, String.format(Locale.US, "Argument arrays must be non null (%s)", values[msg.what]));
            throw new IllegalArgumentException("Argument arrays must be non null");
        }
        return (T) ((Object[]) obj)[index];
    }

    @NonNull
    public static <T> Collection<T> getArgCollection(@NonNull Message msg, @NonNull Class<T> type, int index) {
        Object obj = msg.obj;
        if (obj == null || !(obj instanceof Object[])) {
            FileLog.e(LOG_TAG, String.format(Locale.US, "Argument collections must be non null (%s)", values[msg.what]));
            throw new IllegalArgumentException("Argument collections must be non null");
        }
        return (Collection) ((Object[]) obj)[index];
    }

    @NonNull
    public static <T> List<T> getArgList(@NonNull Message msg, @NonNull Class<T> type, int index) {
        Object obj = msg.obj;
        if (obj == null || !(obj instanceof Object[])) {
            FileLog.e(LOG_TAG, String.format(Locale.US, "Argument list must be non null (%s)", values[msg.what]));
            throw new IllegalArgumentException("Argument list must be non null");
        }
        return (List) ((Object[]) obj)[index];
    }

    @NonNull
    public static <T> T[] getArgArray(@NonNull Message msg, @NonNull Class<T> type, int index) {
        Object obj = msg.obj;
        if (obj == null || !(obj instanceof Object[])) {
            FileLog.e(LOG_TAG, String.format(Locale.US, "Arguments array must be non null (%s)", values[msg.what]));
            throw new IllegalArgumentException("Arguments array must be non null");
        }
        return (T[]) ((Object[]) ((Object[]) obj)[index]);
    }
}
