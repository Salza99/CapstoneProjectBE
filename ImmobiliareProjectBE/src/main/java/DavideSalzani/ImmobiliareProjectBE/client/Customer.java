package DavideSalzani.ImmobiliareProjectBE.client;

import DavideSalzani.ImmobiliareProjectBE.estate.Estate;
import DavideSalzani.ImmobiliareProjectBE.request.Request;
import DavideSalzani.ImmobiliareProjectBE.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    private String name;
    private String surname;
    private String email;
    private Long phone;
    private LocalDate birthDay;
    private List<Estate> sellingProperties;
    private Request request;
    private User followedByUser;
}
