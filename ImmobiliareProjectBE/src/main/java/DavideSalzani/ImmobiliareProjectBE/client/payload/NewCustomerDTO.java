package DavideSalzani.ImmobiliareProjectBE.client.payload;

import java.time.LocalDate;

public record NewCustomerDTO(
        String name,
        String surname,
        String email,
        String phone,
        LocalDate birthDay
) {
}
