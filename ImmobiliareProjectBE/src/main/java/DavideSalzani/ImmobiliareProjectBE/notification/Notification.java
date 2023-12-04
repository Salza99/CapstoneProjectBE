package DavideSalzani.ImmobiliareProjectBE.notification;

import DavideSalzani.ImmobiliareProjectBE.estate.Estate;
import DavideSalzani.ImmobiliareProjectBE.request.Request;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String message;
    @ManyToMany
    @JoinTable(
            name = "notification_estate",
            joinColumns = @JoinColumn(name = "notification_id"),
            inverseJoinColumns = @JoinColumn(name = "estate_id")
    )
    private List<Estate> estatesMatch = new ArrayList<>();
    @ManyToMany
    @JoinTable(
            name = "notification_request", // Nome della tabella di join
            joinColumns = @JoinColumn(name = "notification_id"), // Colonna che fa riferimento a Notification
            inverseJoinColumns = @JoinColumn(name = "request_id") // Colonna che fa riferimento a Request
    )
    private List<Request> requestsMatch = new ArrayList<>();
    private LocalDate timeStamp;

}
