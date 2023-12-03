package DavideSalzani.ImmobiliareProjectBE.user.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record UserLoginDTO(
        @NotEmpty(message = "l'username è richiesto")
        String username,
        @NotEmpty(message = "la password è richiesto")
        String password
) {
}
