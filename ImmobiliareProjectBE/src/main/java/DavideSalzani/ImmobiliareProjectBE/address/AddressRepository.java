package DavideSalzani.ImmobiliareProjectBE.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findByHouseNumber(int houseNumber);
    List<Address> findByStreet(String street);
}
