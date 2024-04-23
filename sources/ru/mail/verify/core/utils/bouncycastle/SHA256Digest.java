package ru.mail.verify.core.utils.bouncycastle;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/utils/bouncycastle/SHA256Digest.class */
public class SHA256Digest extends a {
    private static final int DIGEST_LENGTH = 32;
    static final int[] K = {1116352408, 1899447441, -1245643825, -373957723, 961987163, 1508970993, -1841331548, -1424204075, -670586216, 310598401, 607225278, 1426881987, 1925078388, -2132889090, -1680079193, -1046744716, -459576895, -272742522, 264347078, 604807628, 770255983, 1249150122, 1555081692, 1996064986, -1740746414, -1473132947, -1341970488, -1084653625, -958395405, -710438585, 113926993, 338241895, 666307205, 773529912, 1294757372, 1396182291, 1695183700, 1986661051, -2117940946, -1838011259, -1564481375, -1474664885, -1035236496, -949202525, -778901479, -694614492, -200395387, 275423344, 430227734, 506948616, 659060556, 883997877, 958139571, 1322822218, 1537002063, 1747873779, 1955562222, 2024104815, -2067236844, -1933114872, -1866530822, -1538233109, -1090935817, -965641998};
    private int H1;
    private int H2;
    private int H3;
    private int H4;
    private int H5;
    private int H6;
    private int H7;
    private int H8;
    private int[] X;
    private int xOff;

    public SHA256Digest() {
        this.X = new int[64];
        reset();
    }

    private static int a(int i, int i2, int i3) {
        return (i & i2) ^ ((i ^ (-1)) & i3);
    }

    private static int b(int i, int i2, int i3) {
        return ((i & i2) ^ (i & i3)) ^ (i2 & i3);
    }

    public static byte[] getDigest(byte[] data) {
        SHA256Digest sHA256Digest = new SHA256Digest();
        sHA256Digest.update(data, 0, data.length);
        byte[] bArr = new byte[sHA256Digest.getDigestSize()];
        sHA256Digest.doFinal(bArr, 0);
        return bArr;
    }

    public String getAlgorithmName() {
        return "SHA-256";
    }

    public int getDigestSize() {
        return DIGEST_LENGTH;
    }

    @Override // ru.mail.verify.core.utils.bouncycastle.a
    protected void processWord(byte[] in, int inOff) {
        int i = inOff + 1;
        int i2 = i + 1;
        int i3 = (in[inOff] << 24) | ((in[i] & 255) << 16) | ((in[i2] & 255) << 8) | (in[i2 + 1] & 255);
        int[] iArr = this.X;
        int i4 = this.xOff;
        iArr[i4] = i3;
        int i5 = i4 + 1;
        this.xOff = i5;
        if (i5 == 16) {
            processBlock();
        }
    }

    @Override // ru.mail.verify.core.utils.bouncycastle.a
    protected void processLength(long bitLength) {
        if (this.xOff > 14) {
            processBlock();
        }
        int[] iArr = this.X;
        iArr[14] = (int) (bitLength >>> 32);
        iArr[15] = (int) (bitLength & (-1));
    }

    public int doFinal(byte[] out, int outOff) {
        finish();
        c.a(out, this.H1, outOff);
        c.a(out, this.H2, outOff + 4);
        c.a(out, this.H3, outOff + 8);
        c.a(out, this.H4, outOff + 12);
        c.a(out, this.H5, outOff + 16);
        c.a(out, this.H6, outOff + 20);
        c.a(out, this.H7, outOff + 24);
        c.a(out, this.H8, outOff + 28);
        reset();
        return DIGEST_LENGTH;
    }

    @Override // ru.mail.verify.core.utils.bouncycastle.a
    public void reset() {
        super.reset();
        this.H1 = 1779033703;
        this.H2 = -1150833019;
        this.H3 = 1013904242;
        this.H4 = -1521486534;
        this.H5 = 1359893119;
        this.H6 = -1694144372;
        this.H7 = 528734635;
        this.H8 = 1541459225;
        this.xOff = 0;
        int i = 0;
        while (true) {
            int i2 = i;
            int[] iArr = this.X;
            if (i2 == iArr.length) {
                return;
            }
            iArr[i] = 0;
            i++;
        }
    }

    @Override // ru.mail.verify.core.utils.bouncycastle.a
    protected void processBlock() {
        for (int i = 16; i <= 63; i++) {
            int[] iArr = this.X;
            int i2 = iArr[i - 2];
            int i3 = ((((i2 >>> 17) | (i2 << 15)) ^ ((i2 >>> 19) | (i2 << 13))) ^ (i2 >>> 10)) + iArr[i - 7];
            int i4 = iArr[i - 15];
            iArr[i] = i3 + ((((i4 >>> 7) | (i4 << 25)) ^ ((i4 >>> 18) | (i4 << 14))) ^ (i4 >>> 3)) + iArr[i - 16];
        }
        int i5 = this.H1;
        int i6 = this.H2;
        int i7 = this.H3;
        int i8 = this.H4;
        int i9 = this.H5;
        int i10 = this.H6;
        int i11 = this.H7;
        int i12 = this.H8;
        int i13 = 0;
        int i14 = 0;
        while (i14 < 8) {
            int b = b(i9) + a(i9, i10, i11);
            int[] iArr2 = K;
            int i15 = b + iArr2[i13] + this.X[i13] + i12;
            int i16 = i8 + i15;
            int a = a(i5) + b(i5, i6, i7) + i15;
            int i17 = i13 + 1;
            int b2 = b(i16) + a(i16, i9, i10) + iArr2[i17] + this.X[i17] + i11;
            int i18 = i7 + b2;
            int a2 = a(a) + b(a, i5, i6) + b2;
            int i19 = i17 + 1;
            int b3 = b(i18) + a(i18, i16, i9) + iArr2[i19] + this.X[i19] + i10;
            int i20 = i6 + b3;
            int a3 = a(a2) + b(a2, a, i5) + b3;
            int i21 = i19 + 1;
            int b4 = b(i20) + a(i20, i18, i16) + iArr2[i21] + this.X[i21] + i9;
            int i22 = i5 + b4;
            int a4 = a(a3) + b(a3, a2, a) + b4;
            int i23 = i21 + 1;
            int b5 = b(i22) + a(i22, i20, i18) + iArr2[i23] + this.X[i23] + i16;
            int i24 = a + b5;
            int a5 = a(a4) + b(a4, a3, a2) + b5;
            int i25 = i23 + 1;
            int b6 = b(i24) + a(i24, i22, i20) + iArr2[i25] + this.X[i25] + i18;
            int i26 = a2 + b6;
            int a6 = a(a5) + b(a5, a4, a3) + b6;
            int i27 = i25 + 1;
            int b7 = b(i26) + a(i26, i24, i22) + iArr2[i27] + this.X[i27] + i20;
            int i28 = a3 + b7;
            int a7 = a(a6) + b(a6, a5, a4) + b7;
            int i29 = i27 + 1;
            int b8 = b(i28) + a(i28, i26, i24) + iArr2[i29] + this.X[i29] + i22;
            i14++;
            i13 = i29 + 1;
            i12 = i24;
            i11 = i26;
            i10 = i28;
            i9 = a4 + b8;
            i8 = a5;
            i7 = a6;
            i6 = a7;
            i5 = a(a7) + b(a7, a6, a5) + b8;
        }
        this.H1 += i5;
        this.H2 += i6;
        this.H3 += i7;
        this.H4 += i8;
        this.H5 += i9;
        this.H6 += i10;
        this.H7 += i11;
        this.H8 += i12;
        this.xOff = 0;
        for (int i30 = 0; i30 < 16; i30++) {
            this.X[i30] = 0;
        }
    }

    public b copy() {
        return new SHA256Digest(this);
    }

    public byte[] getEncodedState() {
        byte[] bArr = new byte[(this.xOff * 4) + 52];
        super.populateState(bArr);
        c.a(bArr, this.H1, 16);
        c.a(bArr, this.H2, 20);
        c.a(bArr, this.H3, 24);
        c.a(bArr, this.H4, 28);
        c.a(bArr, this.H5, DIGEST_LENGTH);
        c.a(bArr, this.H6, 36);
        c.a(bArr, this.H7, 40);
        c.a(bArr, this.H8, 44);
        c.a(bArr, this.xOff, 48);
        for (int i = 0; i != this.xOff; i++) {
            c.a(bArr, this.X[i], (i * 4) + 52);
        }
        return bArr;
    }

    @Override // ru.mail.verify.core.utils.bouncycastle.a
    public /* bridge */ /* synthetic */ int getByteLength() {
        return super.getByteLength();
    }

    @Override // ru.mail.verify.core.utils.bouncycastle.a
    public /* bridge */ /* synthetic */ void finish() {
        super.finish();
    }

    @Override // ru.mail.verify.core.utils.bouncycastle.a
    public /* bridge */ /* synthetic */ void update(byte b) {
        super.update(b);
    }

    public SHA256Digest(SHA256Digest t) {
        super(t);
        this.X = new int[64];
        a(t);
    }

    private static int a(int i) {
        return (((i >>> 2) | (i << 30)) ^ ((i >>> 13) | (i << 19))) ^ ((i >>> 22) | (i << 10));
    }

    private static int b(int i) {
        return (((i >>> 6) | (i << 26)) ^ ((i >>> 11) | (i << 21))) ^ ((i >>> 25) | (i << 7));
    }

    public void reset(b other) {
        a((SHA256Digest) other);
    }

    @Override // ru.mail.verify.core.utils.bouncycastle.a
    public /* bridge */ /* synthetic */ void update(byte[] bArr, int i, int i2) {
        super.update(bArr, i, i2);
    }

    private void a(SHA256Digest sHA256Digest) {
        super.copyIn(sHA256Digest);
        this.H1 = sHA256Digest.H1;
        this.H2 = sHA256Digest.H2;
        this.H3 = sHA256Digest.H3;
        this.H4 = sHA256Digest.H4;
        this.H5 = sHA256Digest.H5;
        this.H6 = sHA256Digest.H6;
        this.H7 = sHA256Digest.H7;
        this.H8 = sHA256Digest.H8;
        int[] iArr = sHA256Digest.X;
        System.arraycopy(iArr, 0, this.X, 0, iArr.length);
        this.xOff = sHA256Digest.xOff;
    }

    public SHA256Digest(byte[] encodedState) {
        super(encodedState);
        this.X = new int[64];
        this.H1 = c.a(encodedState, 16);
        this.H2 = c.a(encodedState, 20);
        this.H3 = c.a(encodedState, 24);
        this.H4 = c.a(encodedState, 28);
        this.H5 = c.a(encodedState, DIGEST_LENGTH);
        this.H6 = c.a(encodedState, 36);
        this.H7 = c.a(encodedState, 40);
        this.H8 = c.a(encodedState, 44);
        this.xOff = c.a(encodedState, 48);
        for (int i = 0; i != this.xOff; i++) {
            this.X[i] = c.a(encodedState, (i * 4) + 52);
        }
    }
}
