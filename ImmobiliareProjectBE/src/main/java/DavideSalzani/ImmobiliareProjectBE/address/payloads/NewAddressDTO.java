package DavideSalzani.ImmobiliareProjectBE.address.payloads;

public record NewAddressDTO(
        String region,
        String city,
        String hamlet,
        String street,
        int postalCode,
        int houseNumber
) {
}
