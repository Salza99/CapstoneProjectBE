package DavideSalzani.ImmobiliareProjectBE.address;

import DavideSalzani.ImmobiliareProjectBE.address.payloads.NewAddressDTO;
import DavideSalzani.ImmobiliareProjectBE.exceptions.AlreadyExistException;
import DavideSalzani.ImmobiliareProjectBE.exceptions.NotFoundException;
import DavideSalzani.ImmobiliareProjectBE.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AddressService {
    @Autowired
    AddressRepository addressRepo;

    public Address findById(long id){
        return addressRepo.findById(id).orElseThrow(()-> new NotFoundException("indirizzo "));
    }
    public List<Address> findAll(){
        return addressRepo.findAll();
    }
    public Address createAddress(NewAddressDTO body){
        if (addressRepo.findByHouseNumber(body.houseNumber()) == null){
            Address a = new Address();
            a.setRegion(body.region());
            a.setCity(body.city());
            a.setHamlet(body.hamlet());
            a.setStreet(body.street());
            a.setPostalCode(body.postalCode());
            a.setHouseNumber(body.houseNumber());
            addressRepo.save(a);
            return a;
        }else {
            throw new AlreadyExistException("Indirizzo ");
        }
    }
    public void delete(long id) {
        Address toDelete = this.findById(id);
        addressRepo.delete(toDelete);
    }
}
