package DavideSalzani.ImmobiliareProjectBE.client.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record NewCustomerDTO(
        @NotEmpty(message = "il campo nome deve essere valorizzato")
        String name,
        @NotEmpty(message = "il campo cognome deve essere valorizzato")
        String surname,
        @NotEmpty(message = "il campo email deve essere valorizzato")
        @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\\\.[a-zA-Z]{2,}$", message = "inserisci un email valida")
        String email,
        @NotEmpty(message = "il campo telefono deve essere valorizzato")
        @Pattern(regexp = "^\\+?\\d{1,3}(\\s*\\d){7,}$", message = "inserisci un numero di telefono valido!")
        String phone,
        LocalDate birthDay
) {
}
