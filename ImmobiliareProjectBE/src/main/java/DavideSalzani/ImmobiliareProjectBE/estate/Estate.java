package DavideSalzani.ImmobiliareProjectBE.estate;

import DavideSalzani.ImmobiliareProjectBE.address.Address;
import DavideSalzani.ImmobiliareProjectBE.client.Customer;
import DavideSalzani.ImmobiliareProjectBE.notification.Notification;
import DavideSalzani.ImmobiliareProjectBE.supportClasses.CommonRequestEstate;
import DavideSalzani.ImmobiliareProjectBE.supportClasses.supportEnum.EnergyClass;
import DavideSalzani.ImmobiliareProjectBE.supportClasses.supportEnum.TypeOfProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
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
    private long surface;
    private int numberOfFloors;
    private int numberOfBathrooms;
    private int parkingSpace;
    private int yearOfConstruction;
    private EnergyClass energyClass;
    private TypeOfProperty typeOfProperty;
    private long price;
    private String description;
    private boolean availability;
    @OneToOne
    private Address address;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "customer_id")
    private Customer customer;
    private String heating;
    @ManyToMany(mappedBy = "estatesMatch")
    @JsonIgnore
    private List<Notification> notifications;

}
