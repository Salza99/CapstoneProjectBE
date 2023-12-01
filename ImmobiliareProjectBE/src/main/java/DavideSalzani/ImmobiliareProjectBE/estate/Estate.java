package DavideSalzani.ImmobiliareProjectBE.estate;

import DavideSalzani.ImmobiliareProjectBE.address.Address;
import DavideSalzani.ImmobiliareProjectBE.client.Customer;
import DavideSalzani.ImmobiliareProjectBE.supportClasses.CommonRequestEstate;
import DavideSalzani.ImmobiliareProjectBE.supportClasses.Heating;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Estate extends CommonRequestEstate {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private int floor;
    private int yearOfConstruction;
    private int condominiumFees;
    private long price;
    private String description;
    private boolean availability;
    @OneToOne
    private Address address;
    @ManyToOne
    @JsonIgnore
    private Customer customer;
    @ManyToOne
    private Heating heating;
}
