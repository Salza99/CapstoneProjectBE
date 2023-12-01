package DavideSalzani.ImmobiliareProjectBE.supportClasses;

import DavideSalzani.ImmobiliareProjectBE.estate.Estate;
import DavideSalzani.ImmobiliareProjectBE.supportClasses.supportEnum.HeatingSupply;
import DavideSalzani.ImmobiliareProjectBE.supportClasses.supportEnum.MethodOfHeating;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Heating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean autonomous;
    private MethodOfHeating methodOfHeating;
    private HeatingSupply heatingSupply;
    @OneToMany
    private List<Estate> estates;
}
