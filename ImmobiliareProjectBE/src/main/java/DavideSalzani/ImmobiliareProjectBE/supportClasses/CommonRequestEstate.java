package DavideSalzani.ImmobiliareProjectBE.supportClasses;

import DavideSalzani.ImmobiliareProjectBE.supportClasses.supportEnum.*;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
public abstract class CommonRequestEstate {

    private boolean isToRent;
    private boolean habitability;
    private double condominiumFees;
    private List<String> numberOfRooms = new ArrayList<>();
    private EnergyClass energyClass;
    private Condition condition;
    private TypeOfProperty typeOfProperty;
    private List<String> otherCharacteristics;
}
