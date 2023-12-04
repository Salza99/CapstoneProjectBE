package DavideSalzani.ImmobiliareProjectBE.request;

import DavideSalzani.ImmobiliareProjectBE.client.Customer;
import DavideSalzani.ImmobiliareProjectBE.supportClasses.CommonRequestEstate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



import java.util.ArrayList;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Request extends CommonRequestEstate {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private List<String> regions;
    private List<String> cities;
    private List<String> hamlets;
    private List<Double> surface= new ArrayList<>(2);
    private List<Integer> numberOfFloors= new ArrayList<>(2);
    private List<Integer> numberOfBathrooms= new ArrayList<>(2);
    private List<Integer> parkingSpace = new ArrayList<>(2);
    private List<Long> rangeOfPrice = new ArrayList<>(2);
    private String note;
    @OneToOne
    @JsonIgnore
    private Customer customer;
}
