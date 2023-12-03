package DavideSalzani.ImmobiliareProjectBE.supportClasses.supportEnum.Validator.conditionValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ConditionValidator implements ConstraintValidator<ValidCondition, String> {
    private Enum<?>[] enums;
    @Override
    public void initialize(ValidCondition constraintAnnotation) {
        enums = constraintAnnotation.enumClass().getEnumConstants();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }

        for (Enum<?> enumValue : enums) {
            if (enumValue.name().equals(value)) {
                return true;
            }
        }
        return false;
    }
}
