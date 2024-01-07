package com.developaw.harupuppy.global.utils;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = KoreanNickNameValidator.class)
public @interface KoreanNickname {
    String message() default "닉네임은 최대 한글 8자리까지 설정할 수 있습니다";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

