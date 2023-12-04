package DavideSalzani.ImmobiliareProjectBE.estate.payloads;

import DavideSalzani.ImmobiliareProjectBE.supportClasses.supportEnum.Condition;
import DavideSalzani.ImmobiliareProjectBE.supportClasses.supportEnum.EnergyClass;
import DavideSalzani.ImmobiliareProjectBE.supportClasses.supportEnum.TypeOfProperty;
import DavideSalzani.ImmobiliareProjectBE.supportClasses.supportEnum.Validator.conditionValidator.ValidCondition;
import DavideSalzani.ImmobiliareProjectBE.supportClasses.supportEnum.Validator.energyValidator.ValidEnergyClass;
import DavideSalzani.ImmobiliareProjectBE.supportClasses.supportEnum.Validator.typeOfPropertyValidator.ValidTypeOfProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ChangeEstateInfoDTO(
        @NotNull(message = "il campo superfice non può essere lasciato vuoto")
        long surface,
        @NotNull(message = "il campo numero di bagni non può essere lasciato vuoto")
        int numberOfBathrooms,
        @NotNull(message = "il campo numero posti auto non può essere lasciato vuoto")
        int parkingSpace,
        boolean isToRent,
        boolean habitability,
        @ValidEnergyClass(enumClass = EnergyClass.class, message = "devi inserire una classe energetica valida")
        String energyClass,
        @ValidCondition(enumClass = Condition.class, message = "devi inserire un volore di condizioni valido")
        String condition,
        @ValidTypeOfProperty(enumClass = TypeOfProperty.class, message = "devi inserire un tipo di proprietà valido")
        String typeOfProperty,
        List<String> otherCharacteristics,
        int condominiumFees,
        @NotNull(message = "devi inserire un prezzo per l'immobile")
        long price,
        @NotEmpty(message = "il campo riscaldamento non può essere lasciato vuoto")
        String heating
) {
}
