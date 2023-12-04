package DavideSalzani.ImmobiliareProjectBE.request.payloads;

import DavideSalzani.ImmobiliareProjectBE.supportClasses.supportEnum.Condition;
import DavideSalzani.ImmobiliareProjectBE.supportClasses.supportEnum.EnergyClass;
import DavideSalzani.ImmobiliareProjectBE.supportClasses.supportEnum.TypeOfProperty;
import DavideSalzani.ImmobiliareProjectBE.supportClasses.supportEnum.Validator.conditionValidator.ValidCondition;
import DavideSalzani.ImmobiliareProjectBE.supportClasses.supportEnum.Validator.energyValidator.ValidEnergyClass;
import DavideSalzani.ImmobiliareProjectBE.supportClasses.supportEnum.Validator.typeOfPropertyValidator.ValidTypeOfProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record NewRequestDTO(
        @NotEmpty(message = "inserisci almeno un valore per la superfice")
        List<Double> surface,

        List<Integer> numberOfFloors,

        List<Integer> numberOfBathrooms,

        List<Integer> parkingSpace,
        @NotNull(message = "devi dichiarare se la proprietà è in vendità o in affitto")
        boolean isToRent,
        @NotNull(message = "devi dichiarare se la proprietà è abitabile o no!")
        boolean habitability,
        List<String> numberOfRooms,
        @ValidCondition(enumClass = Condition.class, message = "devi inserire un volore di condizioni valido")
        String condition,

        List<String> typeOfProperty,
        List<String> otherCharacteristics,
        Double condominiumFees,
        @NotEmpty(message = "devi inserire almeno un valore di massimale spesa")
        List<Long> price,
        @NotEmpty(message = "devi inserire almeno una regione")
        List<String> regions,
        @NotEmpty(message = "devi inserire almeno una città")
        List<String> cities,
        List<String> hamlets,
        String note,
        UUID customerId
) {
}
