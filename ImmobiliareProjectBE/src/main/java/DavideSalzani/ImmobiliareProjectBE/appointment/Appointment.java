package DavideSalzani.ImmobiliareProjectBE.appointment;

import DavideSalzani.ImmobiliareProjectBE.supportClasses.supportEnum.AppointmentStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dateTime;
    private String type;
    private String description;
    private UUID assignedAgent;
    private UUID clientInvolved;
    private long propertyAddress;
    private AppointmentStatus status;
    private int priority;
    private String additionalNotes;

}
