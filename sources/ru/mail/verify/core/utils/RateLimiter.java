package ru.mail.verify.core.utils;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/utils/RateLimiter.class */
public class RateLimiter {
    private static final long c = TimeUnit.SECONDS.toNanos(1);
    private final LinkedList<Long> a = new LinkedList<>();
    private final int b;

    public RateLimiter(int maxRPS) {
        this.b = maxRPS;
    }

    public boolean allow() {
        long nanoTime = System.nanoTime();
        if (this.a.isEmpty()) {
            this.a.addLast(Long.valueOf(nanoTime));
            return true;
        }
        if (this.a.size() == this.b) {
            while (!this.a.isEmpty()) {
                long longValue = nanoTime - this.a.peekFirst().longValue();
                if (longValue >= 0) {
                    if (longValue <= c) {
                        break;
                    }
                    this.a.removeFirst();
                } else {
                    this.a.clear();
                    return true;
                }
            }
            if (this.a.size() == this.b) {
                this.a.removeFirst();
                this.a.addLast(Long.valueOf(nanoTime));
                return false;
            }
        }
        this.a.addLast(Long.valueOf(nanoTime));
        return true;
    }
}
