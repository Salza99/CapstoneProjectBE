package DavideSalzani.ImmobiliareProjectBE.appointment.payloads;

import DavideSalzani.ImmobiliareProjectBE.appointment.Appointment;
import DavideSalzani.ImmobiliareProjectBE.supportClasses.supportEnum.AppointmentStatus;
import DavideSalzani.ImmobiliareProjectBE.supportClasses.supportEnum.EnergyClass;
import DavideSalzani.ImmobiliareProjectBE.supportClasses.supportEnum.Validator.energyValidator.ValidEnergyClass;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record NewAppointmentDTO(
         LocalDateTime dateTime,
         @NotEmpty(message = "Devi specificare se stai inserendo una nota o un appuntamento")
         String type,
         @NotEmpty(message = "Inserisci una descrizione dell'avviso")
         String description,
         UUID assignedAgent,
         UUID clientInvolved,
         Long propertyAddress,
         @ValidEnergyClass(enumClass = AppointmentStatus.class, message = "devi inserire uno stato valido")
         AppointmentStatus status,
         @NotNull(message = "devi inserire un grado di priorità")
         int priority,
         String additionalNotes
) {
}
