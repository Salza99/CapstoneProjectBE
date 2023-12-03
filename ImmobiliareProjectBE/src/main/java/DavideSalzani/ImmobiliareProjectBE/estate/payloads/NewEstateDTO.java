package DavideSalzani.ImmobiliareProjectBE.estate.payloads;

import DavideSalzani.ImmobiliareProjectBE.address.Address;
import DavideSalzani.ImmobiliareProjectBE.address.payloads.NewAddressDTO;
import DavideSalzani.ImmobiliareProjectBE.client.Customer;
import DavideSalzani.ImmobiliareProjectBE.supportClasses.supportEnum.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record NewEstateDTO(
        @NotEmpty(message = "il campo superfice non può essere lasciato vuoto")
        String surface,
        @NotEmpty(message = "il campo numero di piani non può essere lasciato vuoto")
        String numberOfFloors,
        @NotEmpty(message = "il campo numero di bagni non può essere lasciato vuoto")
        String numberOfBathrooms,
        @NotEmpty(message = "il campo numero posti auto non può essere lasciato vuoto")
        String parkingSpace,
        boolean isToRent,
        boolean habitability,
        List<String> numberOfRooms,
        String energyClass,
        String condition,
        String typeOfProperty,
        String otherCharacteristics,
        String floor,
        @NotEmpty(message = "il campo anno di costruzione non può essere lasciato vuoto")
        String yearOfConstruction,
        String condominiumFees,
        @NotEmpty(message = "devi inserire un prezzo per l'immobile")
        String price,
        boolean availability,
        long addressId,
        UUID customerId,
        @NotEmpty(message = "il campo riscaldamento non può essere lasciato vuoto")
        String heating
) {
}
