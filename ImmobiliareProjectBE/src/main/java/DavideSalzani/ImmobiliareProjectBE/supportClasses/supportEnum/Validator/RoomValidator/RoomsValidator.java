package DavideSalzani.ImmobiliareProjectBE.supportClasses.supportEnum.Validator.RoomValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.annotation.Annotation;

public class RoomsValidator implements ConstraintValidator<ValidRooms, String> {
    private Enum<?>[] enums;

    @Override
    public void initialize(ValidRooms constraintAnnotation) {
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
