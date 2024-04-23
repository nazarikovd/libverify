package ru.mail.verify.core.utils.bouncycastle;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/utils/bouncycastle/a.class */
abstract class a implements b {
    private static final int BYTE_LENGTH = 64;
    private final byte[] xBuf;
    private int xBufOff;
    private long byteCount;

    /* JADX INFO: Access modifiers changed from: protected */
    public a() {
        this.xBuf = new byte[4];
        this.xBufOff = 0;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void copyIn(a t) {
        byte[] bArr = t.xBuf;
        System.arraycopy(bArr, 0, this.xBuf, 0, bArr.length);
        this.xBufOff = t.xBufOff;
        this.byteCount = t.byteCount;
    }

    public void update(byte in) {
        byte[] bArr = this.xBuf;
        int i = this.xBufOff;
        int i2 = i + 1;
        this.xBufOff = i2;
        bArr[i] = in;
        if (i2 == bArr.length) {
            processWord(bArr, 0);
            this.xBufOff = 0;
        }
        this.byteCount++;
    }

    public void finish() {
        long j = this.byteCount << 3;
        update(Byte.MIN_VALUE);
        while (this.xBufOff != 0) {
            update((byte) 0);
        }
        processLength(j);
        processBlock();
    }

    public void reset() {
        this.byteCount = 0L;
        this.xBufOff = 0;
        int i = 0;
        while (true) {
            int i2 = i;
            byte[] bArr = this.xBuf;
            if (i2 >= bArr.length) {
                return;
            }
            bArr[i] = 0;
            i++;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void populateState(byte[] state) {
        System.arraycopy(this.xBuf, 0, state, 0, this.xBufOff);
        c.a(state, this.xBufOff, 4);
        long j = this.byteCount;
        c.a(state, (int) (j >>> 32), 8);
        c.a(state, (int) (j & 4294967295L), 12);
    }

    public int getByteLength() {
        return BYTE_LENGTH;
    }

    protected abstract void processWord(byte[] bArr, int i);

    protected abstract void processLength(long j);

    protected abstract void processBlock();

    /* JADX INFO: Access modifiers changed from: protected */
    public a(a t) {
        this.xBuf = new byte[4];
        copyIn(t);
    }

    public void update(byte[] in, int inOff, int len) {
        int max = Math.max(0, len);
        int i = 0;
        if (this.xBufOff != 0) {
            while (true) {
                if (i >= max) {
                    break;
                }
                byte[] bArr = this.xBuf;
                int i2 = this.xBufOff;
                int i3 = i2 + 1;
                int i4 = i;
                this.xBufOff = i3;
                i++;
                bArr[i2] = in[inOff + i4];
                if (i3 == 4) {
                    processWord(bArr, 0);
                    this.xBufOff = 0;
                    break;
                }
            }
        }
        int i5 = ((max - i) & (-4)) + i;
        while (i < i5) {
            processWord(in, inOff + i);
            i += 4;
        }
        while (i < max) {
            byte[] bArr2 = this.xBuf;
            int i6 = i;
            int i7 = this.xBufOff;
            this.xBufOff = i7 + 1;
            i++;
            bArr2[i7] = in[inOff + i6];
        }
        this.byteCount += max;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public a(byte[] encodedState) {
        byte[] bArr = new byte[4];
        this.xBuf = bArr;
        System.arraycopy(encodedState, 0, bArr, 0, 4);
        this.xBufOff = c.a(encodedState, 4);
        this.byteCount = c.a(encodedState);
    }
}
