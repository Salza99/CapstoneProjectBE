package DavideSalzani.ImmobiliareProjectBE.supportClasses.supportEnum.Validator.appointmentStatusValidator;

import DavideSalzani.ImmobiliareProjectBE.supportClasses.supportEnum.Validator.conditionValidator.ValidCondition;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AppointmentStatusValidator implements ConstraintValidator<ValidAppointmentStatus, String> {
    private Enum<?>[] enums;
    @Override
    public void initialize(ValidAppointmentStatus validAppointmentStatus) {
        enums = validAppointmentStatus.enumClass().getEnumConstants();
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
