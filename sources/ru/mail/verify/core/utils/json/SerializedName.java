package ru.mail.verify.core.utils.json;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
/* loaded from: libverify-2.11.0.aar:classes.jar:ru/mail/verify/core/utils/json/SerializedName.class */
public @interface SerializedName {
    String value();
}
