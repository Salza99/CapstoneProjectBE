package DavideSalzani.ImmobiliareProjectBE.estate.payloads;

import DavideSalzani.ImmobiliareProjectBE.address.Address;
import DavideSalzani.ImmobiliareProjectBE.address.payloads.NewAddressDTO;
import DavideSalzani.ImmobiliareProjectBE.client.Customer;
import DavideSalzani.ImmobiliareProjectBE.supportClasses.supportEnum.*;
import DavideSalzani.ImmobiliareProjectBE.supportClasses.supportEnum.Validator.conditionValidator.ValidCondition;
import DavideSalzani.ImmobiliareProjectBE.supportClasses.supportEnum.Validator.energyValidator.ValidEnergyClass;
import DavideSalzani.ImmobiliareProjectBE.supportClasses.supportEnum.Validator.typeOfPropertyValidator.ValidTypeOfProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record NewEstateDTO(
        @NotNull(message = "il campo superfice non può essere lasciato vuoto")
        long surface,
        @NotNull(message = "il campo numero di piani non può essere lasciato vuoto")
        int numberOfFloors,
        @NotNull(message = "il campo numero di bagni non può essere lasciato vuoto")
        int numberOfBathrooms,
        @NotNull(message = "il campo numero posti auto non può essere lasciato vuoto")
        int parkingSpace,
        boolean isToRent,
        boolean habitability,
        List<String> numberOfRooms,
        @ValidEnergyClass(enumClass = EnergyClass.class, message = "devi inserire una classe energetica valida")
        String energyClass,
        @ValidCondition(enumClass = Condition.class, message = "devi inserire un volore di condizioni valido")
        String condition,
        @ValidTypeOfProperty(enumClass = TypeOfProperty.class, message = "devi inserire un tipo di proprietà valido")
        String typeOfProperty,
        List<String> otherCharacteristics,
        int floor,
        @NotNull(message = "il campo anno di costruzione non può essere lasciato vuoto")
        int yearOfConstruction,
        int condominiumFees,
        @NotNull(message = "devi inserire un prezzo per l'immobile")
        long price,
        @NotNull(message = "devi inserire la posizione dell'immobile")
        long addressId,
        @NotNull(message = "devi inserire a chi appartiene l'immobile")
        UUID customerId,
        @NotEmpty(message = "il campo riscaldamento non può essere lasciato vuoto")
        String heating
) {
}
