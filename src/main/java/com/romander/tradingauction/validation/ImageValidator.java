package com.romander.tradingauction.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ImageValidator implements ConstraintValidator<Image, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        return value.matches("([^\\s]+(\\.(?i)(jpg|jpeg|png|gif|bmp|webp))$)");
    }
}
