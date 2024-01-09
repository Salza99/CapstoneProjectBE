package DavideSalzani.ImmobiliareProjectBE.request.payloads;

import DavideSalzani.ImmobiliareProjectBE.supportClasses.supportEnum.Condition;
import DavideSalzani.ImmobiliareProjectBE.supportClasses.supportEnum.Validator.conditionValidator.ValidCondition;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ChangeRequestInfoDTO(
        @NotNull(message = "inserisci almeno un valore per la superfice")
        double surface,

        int numberOfBathrooms,

        int parkingSpace,
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
        @NotNull(message = "devi inserire un valore di massimale spesa")
        long maximal,
        @NotEmpty(message = "devi inserire almeno una regione")
        List<String> regions,
        @NotEmpty(message = "devi inserire almeno una città")
        List<String> cities,
        String note
) {
}
