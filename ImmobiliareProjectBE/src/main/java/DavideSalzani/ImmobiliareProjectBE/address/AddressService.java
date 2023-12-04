package DavideSalzani.ImmobiliareProjectBE.address;

import DavideSalzani.ImmobiliareProjectBE.address.payloads.NewAddressDTO;
import DavideSalzani.ImmobiliareProjectBE.exceptions.AlreadyExistException;
import DavideSalzani.ImmobiliareProjectBE.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
