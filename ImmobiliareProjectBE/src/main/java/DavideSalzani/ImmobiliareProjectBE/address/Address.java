package DavideSalzani.ImmobiliareProjectBE.address;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private long id;
    private String region;
    private String city;
    private String hamlet;
    private int postalCode;
    private String street;
    private int houseNumber;

    public Address(String region, String city, String hamlet, int postalCode, String street, int houseNumber) {
        this.region = region;
        this.city = city;
        this.hamlet = hamlet;
        this.postalCode = postalCode;
        this.street = street;
        this.houseNumber = houseNumber;
    }
}
