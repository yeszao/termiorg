package org.termi.common.annotation.form;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Documented
public @interface File {
    long maxFilesize() default -1L;
    String[] acceptedFiles() default {".zip", ".pdf"};
    int maxFiles() default 1;
}