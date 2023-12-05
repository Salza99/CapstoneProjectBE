package DavideSalzani.ImmobiliareProjectBE.request;

import DavideSalzani.ImmobiliareProjectBE.client.Customer;
import DavideSalzani.ImmobiliareProjectBE.notification.Notification;
import DavideSalzani.ImmobiliareProjectBE.supportClasses.CommonRequestEstate;
import DavideSalzani.ImmobiliareProjectBE.supportClasses.supportEnum.TypeOfProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;


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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private List<String> regions;
    private List<String> cities;
    private List<String> hamlets;
    private List<Double> surface= new ArrayList<>(2);
    private List<Integer> numberOfBathrooms= new ArrayList<>(2);
    private List<Integer> parkingSpace = new ArrayList<>(2);
    @Enumerated(EnumType.STRING)
    private List<TypeOfProperty> typeOfProperty = new ArrayList<>();
    private long maximal;
    private String note;
    @OneToOne
    @JsonIgnore
    private Customer customer;
    @ManyToMany(mappedBy = "requestsMatch")
    @JsonIgnore
    @Cascade(CascadeType.ALL)
    private List<Notification> notifications;
}
