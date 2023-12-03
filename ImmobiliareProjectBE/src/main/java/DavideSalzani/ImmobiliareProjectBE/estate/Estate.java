package DavideSalzani.ImmobiliareProjectBE.estate;

import DavideSalzani.ImmobiliareProjectBE.address.Address;
import DavideSalzani.ImmobiliareProjectBE.client.Customer;
import DavideSalzani.ImmobiliareProjectBE.supportClasses.CommonRequestEstate;
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
    private String heating;

    public Estate(int floor, int yearOfConstruction, int condominiumFees, long price, boolean availability, Address address, Customer customer, String heating) {
        this.floor = floor;
        this.yearOfConstruction = yearOfConstruction;
        this.condominiumFees = condominiumFees;
        this.price = price;
        this.availability = availability;
        this.address = address;
        this.customer = customer;
        this.heating = heating;
    }
}
