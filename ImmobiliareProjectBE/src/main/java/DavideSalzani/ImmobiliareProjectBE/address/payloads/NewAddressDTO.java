package DavideSalzani.ImmobiliareProjectBE.address.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record NewAddressDTO(
        @NotEmpty(message = "il campo regione deve essere valorizzato")
        String region,
        @NotEmpty(message = "il campo città deve essere valorizzato")
        String city,
        @NotEmpty(message = "il campo paese deve essere valorizzato")
        String hamlet,
        @NotEmpty(message = "il campo via deve essere valorizzato")
        String street,
        @NotNull(message = "il campo codice postale deve essere valorizzato")
        int postalCode,
        @NotNull(message = "il campo codice postale deve essere valorizzato")
        int houseNumber
) {
}
