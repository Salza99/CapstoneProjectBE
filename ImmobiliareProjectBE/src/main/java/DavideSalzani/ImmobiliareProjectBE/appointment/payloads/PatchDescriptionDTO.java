package DavideSalzani.ImmobiliareProjectBE.appointment.payloads;

import jakarta.validation.constraints.NotEmpty;

public record PatchDescriptionDTO(
        @NotEmpty(message = "Inserisci una descrizione dell'avviso")
        String description
) {
}
