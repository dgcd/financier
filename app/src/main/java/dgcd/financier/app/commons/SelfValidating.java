package dgcd.financier.app.commons;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;

import java.util.Set;

public interface SelfValidating<T> {

    default void validateSelf() {
        @SuppressWarnings("unchecked")
        Set<ConstraintViolation<T>> violations = Validation
                .buildDefaultValidatorFactory()
                .getValidator()
                .validate((T) this);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        additionalValidations();
    }

    default void additionalValidations() {}

}
