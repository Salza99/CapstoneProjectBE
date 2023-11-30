package DavideSalzani.ImmobiliareProjectBE.exceptions.exceptionsPayloads;

import java.time.LocalDate;

public record ErrorsResponseDTO(String message, LocalDate timestamp) {
}
