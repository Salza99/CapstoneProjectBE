package DavideSalzani.ImmobiliareProjectBE.client;

import DavideSalzani.ImmobiliareProjectBE.estate.Estate;
import DavideSalzani.ImmobiliareProjectBE.request.Request;
import DavideSalzani.ImmobiliareProjectBE.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String surname;
    private String email;
    private Long phone;
    private LocalDate birthDay;
    private LocalDate insertDate;
    @OneToMany(mappedBy = "customer")
    @Cascade(CascadeType.ALL)
    private List<Estate> sellingProperties = new ArrayList<>();
    @OneToOne
    @Cascade(CascadeType.ALL)
    private Request request;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User followedByUser;

}
