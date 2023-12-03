package DavideSalzani.ImmobiliareProjectBE.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}
