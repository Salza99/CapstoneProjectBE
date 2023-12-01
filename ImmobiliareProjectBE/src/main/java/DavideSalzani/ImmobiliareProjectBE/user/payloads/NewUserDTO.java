package DavideSalzani.ImmobiliareProjectBE.user.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record NewUserDTO(
        @NotEmpty(message = "il campo nome deve essere valorizzato!")
        String name,
        @NotEmpty(message = "il campo cognome deve essere valorizzato!")
        String surname,
        @NotEmpty(message = "il campo email deve essere valorizzato!")
        @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "inserisci un email valida")
        String email,
        @NotEmpty(message = "il campo telefono deve essere valorizzato!")
        @Pattern(regexp = "^\\+?\\d{1,3}(\\s*\\d){7,}$", message = "inserisci un numero di telefono valido!")
        String phone,
        @NotEmpty(message = "il campo data di nascita deve essere valorizzato!")
        LocalDate birthDay,
        @NotEmpty(message = "il campo username deve essere valorizzato!")
        @Size(min = 3, message = "il campo username deve contenere almeno 3 caratteri")
        String username
) {
}
