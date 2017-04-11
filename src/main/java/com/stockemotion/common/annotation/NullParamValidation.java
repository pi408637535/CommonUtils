package com.stockemotion.common.annotation;

import java.lang.annotation.*;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Inherited
public @interface NullParamValidation
{
    int[] ignoreFieldIndices() default {};
    String[] allFieldNames() default{};
}

