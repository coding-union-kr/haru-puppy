package com.developaw.harupuppy.global.utils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class KoreanNickNameValidator implements ConstraintValidator<KoreanNickname, String> {
    private static final Pattern KOREAN_PATTERN = Pattern.compile("^[가-힣]*$");

    @Override
    public void initialize(KoreanNickname koreanNickname) {}

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        boolean isLengthValid = value.length() >= 1 && value.length() <= 8;
        return KOREAN_PATTERN.matcher(value).matches() && isLengthValid;
    }
}
