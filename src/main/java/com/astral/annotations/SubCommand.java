package com.astral.annotations;

import javax.annotation.Nullable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SubCommand {

    @Nullable String[] names() default {};

    @Nullable String[] permissions() default {};

    int requiredLength() default 0;

    boolean playerRequired() default false;

}
