package com.cntt2.nowfood.utils;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Vanh
 * @version 1.0
 * @date 11/18/2021 9:19 PM
 */
@NoArgsConstructor
@Component
public class ValidateUtils<T> {
    public static <T> List<String> validate (T input) {
        List<String> errors = new ArrayList<>();
        Set<ConstraintViolation<T>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(input);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<T> violation : violations) {
                errors.add(violation.getPropertyPath() + " " + violation.getMessage());
            }
        }
        return errors;
    }
}
