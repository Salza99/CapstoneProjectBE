package DavideSalzani.ImmobiliareProjectBE.exceptions.exceptionsPayloads;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public record ErrorsListResponseDTO(LocalDate timeStamp,
                                    List<String> errorsList) {
}
