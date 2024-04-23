package ru.mail.verify.core.utils.bouncycastle;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/utils/bouncycastle/c.class */
abstract class c {
    public static int a(byte[] bArr, int i) {
        int i2 = i + 1;
        int i3 = i2 + 1;
        return (bArr[i] << 24) | ((bArr[i2] & 255) << 16) | ((bArr[i3] & 255) << 8) | (bArr[i3 + 1] & 255);
    }

    public static long a(byte[] bArr) {
        return ((a(bArr, 8) & 4294967295L) << 32) | (a(bArr, 12) & 4294967295L);
    }

    public static void a(byte[] bArr, int i, int i2) {
        bArr[i2] = (byte) (i >>> 24);
        int i3 = i2 + 1;
        bArr[i3] = (byte) (i >>> 16);
        int i4 = i3 + 1;
        bArr[i4] = (byte) (i >>> 8);
        bArr[i4 + 1] = (byte) i;
    }
}
