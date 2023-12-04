package DavideSalzani.ImmobiliareProjectBE.user;

import DavideSalzani.ImmobiliareProjectBE.client.Customer;
import DavideSalzani.ImmobiliareProjectBE.estate.Estate;
import DavideSalzani.ImmobiliareProjectBE.request.Request;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String username;
    private String password;
    private String name;
    private String surname;
    private String email;
    private Long phone;
    private LocalDate birthDay;
    private LocalDate insertDate;
    private UserRole role;
    @OneToMany(mappedBy = "id")
    private List<Customer> customersFollowed = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public User(String username, String password, String name, String surname, String email, Long phone, LocalDate birthDay, LocalDate insertDate) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.birthDay = birthDay;
        this.insertDate = insertDate;
    }
}
