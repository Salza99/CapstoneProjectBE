package DavideSalzani.ImmobiliareProjectBE.estate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EstateRepository extends JpaRepository<Estate, Long> {
}
