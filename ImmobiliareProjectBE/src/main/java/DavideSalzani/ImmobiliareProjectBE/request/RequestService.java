package DavideSalzani.ImmobiliareProjectBE.request;

import DavideSalzani.ImmobiliareProjectBE.client.Customer;
import DavideSalzani.ImmobiliareProjectBE.client.CustomerService;
import DavideSalzani.ImmobiliareProjectBE.exceptions.BadRequestException;
import DavideSalzani.ImmobiliareProjectBE.exceptions.NotFoundException;
import DavideSalzani.ImmobiliareProjectBE.request.payloads.NewRequestDTO;
import DavideSalzani.ImmobiliareProjectBE.supportClasses.supportEnum.Condition;
import DavideSalzani.ImmobiliareProjectBE.supportClasses.supportEnum.EnergyClass;
import DavideSalzani.ImmobiliareProjectBE.supportClasses.supportEnum.TypeOfProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RequestService {
    @Autowired
    RequestRepository requestRepo;
    @Autowired
    CustomerService customerService;
    public List<Request> getAll(){
        return requestRepo.findAll();
    }
    public Request getById(UUID id){
        return requestRepo.findById(id).orElseThrow(() -> new NotFoundException("richiesta "));
    }
    public Request createNewRequest(NewRequestDTO body){
        Request r = new Request();
        Customer found = customerService.findSingleCustomer(body.customerId());
        if (found.getRequest() == null) {
            r.setSurface(body.surface());
            r.setNumberOfFloors(body.numberOfFloors());
            r.setNumberOfBathrooms(body.numberOfBathrooms());
            r.setNumberOfRooms(body.numberOfRooms());
            r.setParkingSpace(body.parkingSpace());
            r.setToRent(body.isToRent());
            r.setHabitability(body.habitability());
            Condition c = Condition.valueOf(body.condition());
            r.setCondition(c);
            r.setTypeOfProperty(body.typeOfProperty());
            r.setOtherCharacteristics(body.otherCharacteristics());
            r.setCondominiumFees(body.condominiumFees());
            r.setRangeOfPrice(body.price());
            r.setRegions(body.regions());
            r.setCities(body.cities());
            r.setHamlets(body.hamlets());
            r.setNote(body.note());
            r.setCustomer(found);
            requestRepo.save(r);
            return r;
        }else {
            throw new BadRequestException("il cliente possiede già una richiesta a suo carico, eliminare o aggiornare quest'ultima per poter continuare!")
        }

    }
}
