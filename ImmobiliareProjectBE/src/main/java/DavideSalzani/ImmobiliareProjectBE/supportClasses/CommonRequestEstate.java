package DavideSalzani.ImmobiliareProjectBE.supportClasses;

import DavideSalzani.ImmobiliareProjectBE.supportClasses.supportEnum.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
@Getter
@Setter
public abstract class CommonRequestEstate {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private boolean isToRent;
    private boolean habitability;
    private double condominiumFees;
    private List<String> numberOfRooms = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    private Condition condition;
    private LocalDate insertDate;
    private List<String> otherCharacteristics = new ArrayList<>();
}
