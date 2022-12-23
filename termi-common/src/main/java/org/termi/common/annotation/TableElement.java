package org.termi.common.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
public @interface TableElement {
    String label();

    String linkUrl() default "";

    String linkParamKey() default "id";

    boolean indent() default false;

    /**
     * Grid number at bootstrap
     */
    int grid() default 2;
}