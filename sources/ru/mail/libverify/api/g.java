package ru.mail.libverify.api;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.HashMap;
import ru.mail.libverify.j.n;
import ru.mail.verify.core.utils.FileLog;
import ru.mail.verify.core.utils.Utils;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/g.class */
final class g {
    private static final HashMap<String, b> a = new HashMap<>();

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/g$a.class */
    static class a {
        public String[] a;
        public int b = 0;
        public n.a c;
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/g$b.class */
    static class b {
        final c[] a;
        final boolean b;

        /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/g$b$a.class */
        private static class a {
            int a;
            int b;
            int c;
            int d;

            private a() {
            }
        }

        b() {
            this.b = false;
            this.a = null;
        }

        b(@NonNull ArrayList arrayList) {
            this.b = true;
            this.a = (c[]) arrayList.toArray(new c[arrayList.size()]);
        }
    }

    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/api/g$c.class */
    static class c {
        final String a;
        final String b;
        final String c;

        c(String str, String str2, String str3) {
            this.a = str;
            this.b = str2;
            this.c = str3;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String a(boolean z, @NonNull String str, a aVar) {
        boolean z2;
        String[] strArr;
        String[] strArr2;
        char[] charArray;
        if (z) {
            StringBuilder sb = new StringBuilder();
            for (char c2 : str.toCharArray()) {
                if (Character.isDigit(c2)) {
                    sb.append(c2);
                }
                if (c2 == '\n') {
                    break;
                }
            }
            return sb.toString();
        }
        if (TextUtils.isEmpty(str) || aVar == null || (strArr2 = aVar.a) == null || strArr2.length == 0) {
            FileLog.e("CodeParser", "not enough arguments to parse code");
            z2 = false;
        } else if (aVar.b <= 0 || str.length() >= aVar.b) {
            z2 = true;
        } else {
            FileLog.e("CodeParser", "message text is too small to start parsing");
            z2 = false;
        }
        if (z2) {
            for (String str2 : aVar.a) {
                FileLog.v("CodeParser", "try to parse using regular expression");
                if (str2.startsWith("^") && str2.endsWith("$") && str.matches(str2)) {
                    String replaceFirst = str.replaceFirst(str2, "$1");
                    if (!TextUtils.isEmpty(replaceFirst)) {
                        return replaceFirst;
                    }
                } else {
                    FileLog.v("CodeParser", "try to parse using template");
                    int indexOf = str2.indexOf("%");
                    if (indexOf < 0) {
                        continue;
                    } else {
                        String substring = str2.substring(0, indexOf);
                        int indexOf2 = str2.indexOf("%", indexOf + 1);
                        if (indexOf2 >= 0 && indexOf2 > indexOf) {
                            String substring2 = indexOf2 != str2.length() - 1 ? str2.substring(indexOf2 + 1) : "";
                            if (str.startsWith(substring) && str.endsWith(substring2)) {
                                String trim = str.substring(substring.length(), str.length() - substring2.length()).trim();
                                if (!TextUtils.isEmpty(trim) && (aVar.b == 0 || trim.length() == aVar.b) && (aVar.c != n.a.NUMERIC || Utils.isNumeric(trim))) {
                                    FileLog.v("CodeParser", "successfully extracted code %s", trim);
                                    return trim;
                                }
                            }
                        }
                    }
                }
            }
            return null;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String a(@NonNull String str, a aVar) {
        boolean z;
        String[] strArr;
        String[] strArr2;
        if (TextUtils.isEmpty(str) || aVar == null || (strArr2 = aVar.a) == null || strArr2.length == 0) {
            FileLog.e("CodeParser", "not enough arguments to parse code");
            z = false;
        } else if (aVar.b <= 0 || str.length() >= aVar.b) {
            z = true;
        } else {
            FileLog.e("CodeParser", "message text is too small to start parsing");
            z = false;
        }
        if (z) {
            for (String str2 : aVar.a) {
                int i = 0;
                HashMap hashMap = new HashMap();
                b bVar = a.get(str2);
                b bVar2 = bVar;
                if (bVar == null) {
                    b.a aVar2 = null;
                    b.a aVar3 = null;
                    ArrayList arrayList = new ArrayList();
                    for (int i2 = 0; i2 < str2.length(); i2++) {
                        if (str2.charAt(i2) == '%') {
                            if (aVar2 == null) {
                                if (aVar3 != null) {
                                    aVar3.d = i2;
                                    b.a aVar4 = aVar3;
                                    b.a aVar5 = aVar3;
                                    b.a aVar6 = aVar3;
                                    arrayList.add(new c(str2.substring(aVar6.a, aVar6.b), str2.substring(aVar5.c + 1, aVar5.d), str2.substring(aVar4.b + 1, aVar4.c)));
                                }
                                b.a aVar7 = aVar3;
                                aVar2 = r1;
                                b.a aVar8 = new b.a();
                                aVar8.b = i2;
                                if (aVar7 != null) {
                                    aVar2.a = aVar3.d;
                                }
                            } else {
                                aVar2.c = i2;
                                b.a aVar9 = aVar2;
                                aVar2 = null;
                                aVar3 = aVar9;
                            }
                        }
                    }
                    if (aVar3 != null) {
                        aVar3.d = str2.length();
                        b.a aVar10 = aVar3;
                        b.a aVar11 = aVar3;
                        b.a aVar12 = aVar3;
                        arrayList.add(new c(str2.substring(aVar12.a, aVar12.b), str2.substring(aVar11.c + 1, aVar11.d), str2.substring(aVar10.b + 1, aVar10.c)));
                    }
                    if (arrayList.isEmpty()) {
                        bVar2 = r0;
                        b bVar3 = new b();
                    } else {
                        bVar2 = r0;
                        b bVar4 = new b(arrayList);
                    }
                    a.put(str2, bVar2);
                }
                if (bVar2.b && bVar2.a != null) {
                    int i3 = 0;
                    while (true) {
                        int i4 = i3;
                        c[] cVarArr = bVar2.a;
                        if (i4 >= cVarArr.length) {
                            break;
                        }
                        c cVar = cVarArr[i3];
                        if (str.indexOf(cVar.a, i) == -1) {
                            break;
                        }
                        int length = TextUtils.isEmpty(cVar.b) ? str.length() : str.indexOf(cVar.b, cVar.a.length() + i);
                        if (length < 0) {
                            break;
                        }
                        int i5 = -1;
                        int i6 = length - 1;
                        while (true) {
                            if (i6 < 0) {
                                break;
                            } else if (str.charAt(i6) != ' ') {
                                i5 = i6;
                                break;
                            } else {
                                i6--;
                            }
                        }
                        if (i5 < 0) {
                            break;
                        }
                        int lastIndexOf = str.lastIndexOf(32, i5);
                        int i7 = lastIndexOf;
                        if (lastIndexOf < 0) {
                            i7 = 0;
                        }
                        hashMap.put(cVar.c, str.substring(i7, i5 + 1).trim());
                        i = cVar.b.length() + length;
                        i3++;
                    }
                }
                if (!hashMap.isEmpty()) {
                    String str3 = (String) hashMap.get("code");
                    if (!TextUtils.isEmpty(str3) && (aVar.b == 0 || str3.length() == aVar.b) && (aVar.c != n.a.NUMERIC || Utils.isNumeric(str3))) {
                        return str3;
                    }
                    String str4 = (String) hashMap.get("verify_url");
                    if (!TextUtils.isEmpty(str4)) {
                        return str4;
                    }
                }
            }
            return null;
        }
        return null;
    }
}
