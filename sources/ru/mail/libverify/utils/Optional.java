package ru.mail.libverify.utils;

import androidx.annotation.NonNull;
import java.util.NoSuchElementException;
import java.util.Objects;

/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/utils/Optional.class */
public final class Optional<T> {
    private static final Optional<?> EMPTY = new Optional<>();
    private final T value;

    @FunctionalInterface
    /* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/libverify/utils/Optional$Action.class */
    public interface Action<T> {
        void accept(@NonNull T t);
    }

    private Optional() {
        this.value = null;
    }

    @NonNull
    public static <T> Optional<T> empty() {
        return (Optional<T>) EMPTY;
    }

    @NonNull
    public static <T> Optional<T> of(T value) {
        return new Optional<>(value);
    }

    @NonNull
    public static <T> Optional<T> ofNullable(T value) {
        return value == null ? empty() : of(value);
    }

    public T get() {
        T t = this.value;
        if (t != null) {
            return t;
        }
        throw new NoSuchElementException("No value present");
    }

    public boolean isPresent() {
        return this.value != null;
    }

    public void ifPresent(@NonNull Action<T> consumer) {
        T t = this.value;
        if (t != null) {
            consumer.accept(t);
        }
    }

    public T orElse(T other) {
        T t = this.value;
        if (t != null) {
            other = t;
        }
        return other;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Optional) {
            return Objects.equals(this.value, ((Optional) obj).value);
        }
        return false;
    }

    public int hashCode() {
        return Objects.hashCode(this.value);
    }

    @NonNull
    public String toString() {
        T t = this.value;
        return t != null ? String.format("Optional[%s]", t) : "Optional.empty";
    }

    private Optional(T value) {
        this.value = (T) Objects.requireNonNull(value);
    }
}
