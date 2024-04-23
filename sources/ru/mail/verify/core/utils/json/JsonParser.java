package ru.mail.verify.core.utils.json;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.mail.verify.core.utils.DebugUtils;
import ru.mail.verify.core.utils.Gsonable;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/utils/json/JsonParser.class */
public class JsonParser {
    private static final String LOG_TAG = "JsonParser";
    private static final ConcurrentHashMap<Object, Boolean> supportedObjects = new ConcurrentHashMap<>();

    public static <T> T fromJson(@NonNull String json, @NonNull Class<T> classOfT) throws JsonParseException {
        if (TextUtils.isEmpty(json)) {
            throw new JsonParseException("Empty json");
        }
        if (a((Class<?>) classOfT)) {
            try {
                return (T) b(new JSONObject(json), classOfT);
            } catch (JSONException e) {
                throw new JsonParseException(json, e);
            }
        } else if (classOfT.isArray() && a(classOfT.getComponentType())) {
            try {
                return (T) a(new JSONArray(json), (Class<?>) classOfT);
            } catch (JSONException e2) {
                throw new JsonParseException(json, e2);
            }
        } else {
            throw new IllegalArgumentException("Type deserialization is not supported ".concat(classOfT.getName()));
        }
    }

    public static <T> T fromJsonSafe(@NonNull String json, @NonNull Class<T> classOfT) {
        try {
            return (T) fromJson(json, classOfT);
        } catch (JsonParseException e) {
            DebugUtils.safeThrow(LOG_TAG, "json parse error", e);
            return null;
        }
    }

    public static String toJson(@NonNull Object object) throws JsonParseException {
        if (e(object)) {
            return d(object).toString();
        }
        if (object.getClass().isArray()) {
            return a(object).toString();
        }
        if (object instanceof Map) {
            return c(object).toString();
        }
        if ((object instanceof List) || (object instanceof Set)) {
            return b(object).toString();
        }
        throw new IllegalArgumentException("Type serialization is not supported");
    }

    public static <T> List<T> listFromJson(@NonNull String json, @NonNull Class<T> classOfT) throws JsonParseException {
        try {
            return Arrays.asList((Object[]) a(new JSONArray(json), (Class<?>) classOfT));
        } catch (JSONException e) {
            throw new JsonParseException(json, e);
        }
    }

    public static <T> HashMap<String, T> mapFromJson(@NonNull String json, @NonNull Class<T> classOfT) throws JsonParseException {
        try {
            return a(new JSONObject(json), classOfT);
        } catch (JSONException e) {
            throw new JsonParseException(json, e);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:72:0x00a6 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:82:0x0034 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static org.json.JSONObject d(@androidx.annotation.NonNull java.lang.Object r5) throws ru.mail.verify.core.utils.json.JsonParseException {
        /*
            Method dump skipped, instructions count: 287
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: ru.mail.verify.core.utils.json.JsonParser.d(java.lang.Object):org.json.JSONObject");
    }

    private static JSONObject c(@NonNull Object obj) throws JsonParseException {
        JSONObject jSONObject;
        JSONObject d;
        try {
            JSONObject jSONObject2 = new JSONObject();
            Map map = (Map) obj;
            for (Object obj2 : map.keySet()) {
                String str = (String) obj2;
                Object obj3 = map.get(obj2);
                if (e(obj3)) {
                    jSONObject = jSONObject2;
                    d = d(obj3);
                } else if ((obj3 instanceof List) || (obj3 instanceof Set)) {
                    jSONObject2.put(str, b(obj3));
                } else if (obj3 instanceof Map) {
                    jSONObject = jSONObject2;
                    d = c(obj3);
                } else {
                    jSONObject2.put(str, obj3);
                }
                jSONObject.put(str, d);
            }
            return jSONObject2;
        } catch (Throwable th) {
            throw new JsonParseException(obj.toString(), th);
        }
    }

    private static JSONArray b(@NonNull Object obj) throws JsonParseException {
        try {
            JSONArray jSONArray = new JSONArray();
            for (Object obj2 : (Collection) obj) {
                if (e(obj2)) {
                    jSONArray.put(d(obj2));
                } else {
                    jSONArray.put(obj2);
                }
            }
            return jSONArray;
        } catch (Throwable th) {
            throw new JsonParseException(obj.toString(), th);
        }
    }

    private static JSONArray a(@NonNull Object obj) throws JsonParseException {
        try {
            JSONArray jSONArray = new JSONArray();
            for (int i = 0; i < Array.getLength(obj); i++) {
                Object obj2 = Array.get(obj, i);
                if (e(obj2)) {
                    jSONArray.put(d(obj2));
                } else {
                    jSONArray.put(obj2);
                }
            }
            return jSONArray;
        } catch (Throwable th) {
            throw new JsonParseException(obj.toString(), th);
        }
    }

    private static boolean e(@Nullable Object obj) {
        if (obj == null || (obj instanceof Map) || (obj instanceof List) || (obj instanceof Set) || (obj instanceof String)) {
            return false;
        }
        return a(obj.getClass());
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static <T> HashMap<String, T> a(@NonNull JSONObject jSONObject, @NonNull Class<T> cls) throws JsonParseException {
        try {
            HashMap<String, T> hashMap = (HashMap<String, T>) new HashMap();
            Iterator<String> keys = jSONObject.keys();
            if (a((Class<?>) cls)) {
                while (keys.hasNext()) {
                    String next = keys.next();
                    hashMap.put(next, b((JSONObject) jSONObject.get(next), cls));
                }
            } else {
                while (keys.hasNext()) {
                    String next2 = keys.next();
                    hashMap.put(next2, jSONObject.get(next2));
                }
            }
            return hashMap;
        } catch (Throwable th) {
            throw new JsonParseException(jSONObject.toString(), th);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:106:0x00c4 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:124:0x0073 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static <T> T b(@androidx.annotation.NonNull org.json.JSONObject r6, @androidx.annotation.NonNull java.lang.Class<T> r7) throws ru.mail.verify.core.utils.json.JsonParseException {
        /*
            Method dump skipped, instructions count: 508
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: ru.mail.verify.core.utils.json.JsonParser.b(org.json.JSONObject, java.lang.Class):java.lang.Object");
    }

    private static Object a(@NonNull JSONArray jSONArray, Class<?> cls) throws JSONException, IllegalAccessException, InstantiationException, JsonParseException {
        Class<?> componentType = cls.getComponentType();
        Class<?> cls2 = componentType;
        if (componentType == null) {
            cls2 = cls;
        }
        Class<?> cls3 = cls2;
        Object newInstance = Array.newInstance(cls3, jSONArray.length());
        if (a(cls3)) {
            for (int i = 0; i < jSONArray.length(); i++) {
                Array.set(newInstance, i, b(jSONArray.getJSONObject(i), cls2));
            }
        } else if (cls2.isEnum()) {
            for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                Array.set(newInstance, i2, a(jSONArray.getString(i2), cls2));
            }
        } else if (cls == Integer.TYPE || cls == Integer.class) {
            for (int i3 = 0; i3 < jSONArray.length(); i3++) {
                Array.set(newInstance, i3, Integer.valueOf(jSONArray.getInt(i3)));
            }
        } else if (cls == Long.TYPE || cls == Long.class) {
            for (int i4 = 0; i4 < jSONArray.length(); i4++) {
                Array.set(newInstance, i4, Long.valueOf(jSONArray.getLong(i4)));
            }
        } else if (cls == Double.TYPE || cls == Double.class) {
            for (int i5 = 0; i5 < jSONArray.length(); i5++) {
                Array.set(newInstance, i5, Double.valueOf(jSONArray.getDouble(i5)));
            }
        } else {
            for (int i6 = 0; i6 < jSONArray.length(); i6++) {
                Array.set(newInstance, i6, jSONArray.get(i6));
            }
        }
        return newInstance;
    }

    private static AbstractCollection a(@NonNull JSONObject jSONObject, Field field, String str, Class cls) throws JSONException, IllegalAccessException, InstantiationException, JsonParseException {
        AbstractCollection abstractCollection;
        JSONArray jSONArray = jSONObject.getJSONArray(str);
        if (cls == Set.class) {
            abstractCollection = r0;
            AbstractCollection hashSet = new HashSet();
        } else if (cls == LinkedList.class) {
            abstractCollection = r0;
            AbstractCollection linkedList = new LinkedList();
        } else if (cls == LinkedHashSet.class) {
            abstractCollection = r0;
            AbstractCollection linkedHashSet = new LinkedHashSet();
        } else {
            abstractCollection = r0;
            AbstractCollection arrayList = new ArrayList();
        }
        Type genericType = field.getGenericType();
        Class cls2 = null;
        if (genericType instanceof ParameterizedType) {
            Type[] actualTypeArguments = ((ParameterizedType) genericType).getActualTypeArguments();
            if (actualTypeArguments.length != 1) {
                throw new IllegalArgumentException();
            }
            cls2 = (Class) actualTypeArguments[0];
        }
        if (cls2 != null) {
            for (int i = 0; i < jSONArray.length(); i++) {
                if (cls2.isEnum()) {
                    abstractCollection.add(a(jSONArray.getString(i), cls2));
                } else if (a((Class<?>) cls2)) {
                    abstractCollection.add(b(jSONArray.getJSONObject(i), cls2));
                } else {
                    abstractCollection.add(jSONArray.get(i));
                }
            }
            return abstractCollection;
        }
        throw new IllegalArgumentException();
    }

    private static Enum a(String str, Class cls) throws JSONException {
        Field[] fields;
        if (str != null) {
            for (Field field : cls.getFields()) {
                if (field.isAnnotationPresent(SerializedName.class) && TextUtils.equals(((SerializedName) field.getAnnotation(SerializedName.class)).value(), str)) {
                    str = field.getName();
                    break;
                }
            }
            try {
                return Enum.valueOf(cls, str);
            } catch (IllegalArgumentException unused) {
                return null;
            }
        }
        return null;
    }

    private static HashMap a(@NonNull JSONObject jSONObject, Field field) throws JSONException, IllegalAccessException, InstantiationException, JsonParseException {
        SerializedName serializedName;
        JSONObject jSONObject2 = jSONObject.getJSONObject((!field.isAnnotationPresent(SerializedName.class) || (serializedName = (SerializedName) field.getAnnotation(SerializedName.class)) == null || serializedName.value() == null) ? field.getName() : serializedName.value());
        HashMap hashMap = new HashMap();
        Type genericType = field.getGenericType();
        Class cls = null;
        Class cls2 = null;
        if (genericType instanceof ParameterizedType) {
            Type[] actualTypeArguments = ((ParameterizedType) genericType).getActualTypeArguments();
            if (actualTypeArguments.length != 2) {
                throw new IllegalArgumentException();
            }
            cls = (Class) actualTypeArguments[0];
            cls2 = (Class) actualTypeArguments[1];
        }
        if (cls == null || cls2 == null) {
            throw new IllegalArgumentException();
        }
        Iterator<String> keys = jSONObject2.keys();
        while (keys.hasNext()) {
            String next = keys.next();
            if (cls2.isEnum()) {
                hashMap.put(next, a(jSONObject2.getString(next), cls2));
            } else if (a((Class<?>) cls2)) {
                hashMap.put(next, b(jSONObject2.getJSONObject(next), cls2));
            } else {
                hashMap.put(next, jSONObject2.getString(next));
            }
        }
        return hashMap;
    }

    private static boolean a(Class cls, Class cls2) {
        if (cls == cls2) {
            return true;
        }
        if (cls2.isInterface()) {
            Class superclass = cls.getSuperclass();
            if (superclass == null || !a(superclass, cls2)) {
                for (Class<?> cls3 : cls.getInterfaces()) {
                    if (a(cls3, cls2)) {
                        return true;
                    }
                }
                return false;
            }
            return true;
        }
        return cls.isAssignableFrom(cls2);
    }

    private static boolean a(@Nullable Class<?> cls) {
        if (cls == null || cls.isPrimitive() || cls.isEnum() || cls.isArray() || cls == String.class || cls == Map.class || cls == List.class || cls == Set.class) {
            return false;
        }
        if (supportedObjects.containsKey(cls)) {
            return true;
        }
        Class<?> cls2 = cls;
        while (true) {
            Class<?> cls3 = cls2;
            if (cls3.getSuperclass() == null) {
                return false;
            }
            for (Class<?> cls4 : cls3.getInterfaces()) {
                if (cls4 == Gsonable.class) {
                    supportedObjects.put(cls, Boolean.TRUE);
                    return true;
                }
            }
            cls2 = cls3.getSuperclass();
        }
    }
}
