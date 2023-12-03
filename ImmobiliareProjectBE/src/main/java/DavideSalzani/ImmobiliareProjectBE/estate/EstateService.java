package DavideSalzani.ImmobiliareProjectBE.estate;

import DavideSalzani.ImmobiliareProjectBE.address.Address;
import DavideSalzani.ImmobiliareProjectBE.address.AddressService;
import DavideSalzani.ImmobiliareProjectBE.client.Customer;
import DavideSalzani.ImmobiliareProjectBE.client.CustomerService;
import DavideSalzani.ImmobiliareProjectBE.estate.payloads.NewEstateDTO;
import DavideSalzani.ImmobiliareProjectBE.exceptions.NotFoundException;
import DavideSalzani.ImmobiliareProjectBE.supportClasses.supportEnum.*;
import DavideSalzani.ImmobiliareProjectBE.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EstateService {
    @Autowired
    EstateRepository estateRepo;
    @Autowired
    AddressService addressService;
    @Autowired
    CustomerService customerService;

    public Estate getById(UUID id){
        return estateRepo.findById(id).orElseThrow(() -> new NotFoundException("Proprietà "));
    }
    public Page<Estate> getEstate(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return estateRepo.findAll(pageable);
    }
    public List<Estate> getAllEstate() {
        return estateRepo.findAll();
    }
    public void delete(UUID id) {
        Estate found = this.getById(id);
        estateRepo.delete(found);
    }
    public Estate createEstate(NewEstateDTO body){
        Address aFound = addressService.findById(body.addressId());
        Customer cFound = customerService.findSingleCustomer(body.customerId());

        Estate e = new Estate();
        e.setSurface(Integer.parseInt(body.surface()));
        e.setNumberOfFloors(Integer.parseInt(body.numberOfFloors()));
        e.setNumberOfBathrooms(Integer.parseInt(body.numberOfBathrooms()));
        e.setParkingSpace(Integer.parseInt(body.parkingSpace()));
        e.setToRent(body.isToRent());
        e.setHabitability(body.habitability());
        for (String room: body.numberOfRooms()) {
            e.getNumberOfRooms().add(room);
        }
        EnergyClass en = EnergyClass.valueOf(body.energyClass());
        e.setEnergyClass(en);
        Condition c = Condition.valueOf(body.condition());
        e.setCondition(c);
        TypeOfProperty t = TypeOfProperty.valueOf(body.typeOfProperty());
        e.setTypeOfProperty(t);
        for (String otherCharacteristic: body.otherCharacteristics()) {
            e.getOtherCharacteristics().add(otherCharacteristic);
        }
        e.setFloor(Integer.parseInt(body.floor()));
        e.setYearOfConstruction(Integer.parseInt(body.yearOfConstruction()));
        e.setCondominiumFees(Integer.parseInt(body.condominiumFees()));
        e.setPrice(Long.parseLong(body.price()));
        e.setAvailability(body.availability());
        e.setAddress(aFound);
        e.setCustomer(cFound);
        e.setHeating(body.heating());
        estateRepo.save(e);
        return e;
    }
}
