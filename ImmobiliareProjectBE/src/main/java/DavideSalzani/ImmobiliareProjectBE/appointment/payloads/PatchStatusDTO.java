package DavideSalzani.ImmobiliareProjectBE.appointment.payloads;

import DavideSalzani.ImmobiliareProjectBE.supportClasses.supportEnum.AppointmentStatus;
import DavideSalzani.ImmobiliareProjectBE.supportClasses.supportEnum.Validator.energyValidator.ValidEnergyClass;
import jakarta.validation.constraints.NotEmpty;

public record PatchStatusDTO(
        @ValidEnergyClass(enumClass = AppointmentStatus.class, message = "devi inserire uno stato valido")
        AppointmentStatus status
) {
}
