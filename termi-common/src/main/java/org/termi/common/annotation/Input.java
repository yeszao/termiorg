package org.termi.common.annotation;

import org.termi.common.constant.PreGroup;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
public @interface Input {
    /**
     * If empty, will use capital column name instead
     */
    String label() default "";

    boolean required() default false;

    /**
     * Group name for bootstrap card
     */
    String group() default PreGroup.EMPTY;

    /**
     * The order at form, sort by asc
     */
    int order() default  7;

    /**
     * Grid number at bootstrap
     */
    byte grid() default 12;
}