package DavideSalzani.ImmobiliareProjectBE.estate;

import DavideSalzani.ImmobiliareProjectBE.address.Address;
import DavideSalzani.ImmobiliareProjectBE.address.AddressRepository;
import DavideSalzani.ImmobiliareProjectBE.address.AddressService;
import DavideSalzani.ImmobiliareProjectBE.client.Customer;
import DavideSalzani.ImmobiliareProjectBE.client.CustomerRepository;
import DavideSalzani.ImmobiliareProjectBE.client.CustomerService;
import DavideSalzani.ImmobiliareProjectBE.estate.payloads.ChangeEstateInfoDTO;
import DavideSalzani.ImmobiliareProjectBE.estate.payloads.NewEstateDTO;
import DavideSalzani.ImmobiliareProjectBE.exceptions.BadRequestException;
import DavideSalzani.ImmobiliareProjectBE.exceptions.NotFoundException;
import DavideSalzani.ImmobiliareProjectBE.supportClasses.supportEnum.*;
import DavideSalzani.ImmobiliareProjectBE.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class EstateService {
    @Autowired
    EstateRepository estateRepo;
    @Autowired
    AddressRepository addressRepo;
    @Autowired
    CustomerRepository customerRepo;

    public Estate getById(long id){
        return estateRepo.findById(id).orElseThrow(() -> new NotFoundException("Proprietà "));
    }
    public Page<Estate> getEstate(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return estateRepo.findAll(pageable);
    }
    public List<Estate> getAllEstate() {
        return estateRepo.findAll();
    }
    public void delete(long id) {
        Estate found = this.getById(id);
        estateRepo.delete(found);
    }
    public Page<Estate> getEstates(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return estateRepo.findAll(pageable);
    }
    public Estate createEstate(NewEstateDTO body){
        Address aFound = addressRepo.findById(body.addressId()).orElseThrow(() -> new NotFoundException("indirizzo "));
        Customer cFound = customerRepo.findById(body.customerId()).orElseThrow(() -> new NotFoundException("cliente "));
        if (aFound.getEstate() == null){
            Estate e = new Estate();
            e.setSurface(body.surface());
            e.setNumberOfFloors(body.numberOfFloors());
            e.setNumberOfBathrooms(body.numberOfBathrooms());
            e.setParkingSpace(body.parkingSpace());
            e.setToRent(body.isToRent());
            e.setInsertDate(LocalDate.now());
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
            e.setFloor(body.floor());
            e.setYearOfConstruction(body.yearOfConstruction());
            e.setCondominiumFees(body.condominiumFees());
            e.setPrice(body.price());
            e.setAvailability(true);
            e.setAddress(aFound);
            e.setCustomer(cFound);
            e.setHeating(body.heating());
            estateRepo.save(e);
            aFound.setEstate(e);
            addressRepo.save(aFound);
            cFound.getSellingProperties().add(e);
            customerRepo.save(cFound);
            return e;
        }else {
            throw new BadRequestException("l'indirizzo è già stato assegnato ad un'immobile");
        }

    }
    public Estate updateEstateInfo(long id, ChangeEstateInfoDTO body){
        Estate toUpdate= this.getById(id);
        toUpdate.setSurface(body.surface());
        toUpdate.setNumberOfBathrooms(body.numberOfBathrooms());
        toUpdate.setParkingSpace(body.parkingSpace());
        toUpdate.setToRent(body.isToRent());
        toUpdate.setHabitability(body.habitability());
        EnergyClass e = EnergyClass.valueOf(body.energyClass());
        toUpdate.setEnergyClass(e);
        Condition c = Condition.valueOf(body.condition());
        toUpdate.setCondition(c);
        TypeOfProperty t = TypeOfProperty.valueOf(body.typeOfProperty());
        toUpdate.setTypeOfProperty(t);
        toUpdate.setOtherCharacteristics(body.otherCharacteristics());
        toUpdate.setCondominiumFees(body.condominiumFees());
        toUpdate.setPrice(body.price());
        toUpdate.setHeating(body.heating());
        estateRepo.save(toUpdate);
        return toUpdate;
    }
    public void deleteEstate(long id){
        Estate toRemove = this.getById(id);
        estateRepo.delete(toRemove);
    }
}
