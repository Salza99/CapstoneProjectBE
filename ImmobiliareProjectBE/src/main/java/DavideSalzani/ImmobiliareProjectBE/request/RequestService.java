package DavideSalzani.ImmobiliareProjectBE.request;

import DavideSalzani.ImmobiliareProjectBE.client.Customer;
import DavideSalzani.ImmobiliareProjectBE.client.CustomerRepository;
import DavideSalzani.ImmobiliareProjectBE.client.CustomerService;
import DavideSalzani.ImmobiliareProjectBE.estate.Estate;
import DavideSalzani.ImmobiliareProjectBE.exceptions.BadRequestException;
import DavideSalzani.ImmobiliareProjectBE.exceptions.NotFoundException;
import DavideSalzani.ImmobiliareProjectBE.request.payloads.ChangeRequestInfoDTO;
import DavideSalzani.ImmobiliareProjectBE.request.payloads.NewRequestDTO;
import DavideSalzani.ImmobiliareProjectBE.supportClasses.supportEnum.Condition;
import DavideSalzani.ImmobiliareProjectBE.supportClasses.supportEnum.EnergyClass;
import DavideSalzani.ImmobiliareProjectBE.supportClasses.supportEnum.TypeOfProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RequestService {
    @Autowired
    RequestRepository requestRepo;
    @Autowired
    CustomerRepository customerRepo;
    public List<Request> getAll(){
        return requestRepo.findAll();
    }
    public Request getById(long id){
        return requestRepo.findById(id).orElseThrow(() -> new NotFoundException("richiesta "));
    }
    public Page<Request> getRequests(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return requestRepo.findAll(pageable);
    }
    public Request createNewRequest(NewRequestDTO body){
        Request r = new Request();
        Customer found = customerRepo.findById(body.customerId()).orElseThrow(() -> new NotFoundException("cliente "));
        if (found.getRequest() == null) {
            r.setSurface(body.surface());
            r.setNumberOfBathrooms(body.numberOfBathrooms());
            r.setNumberOfRooms(body.numberOfRooms());
            r.setParkingSpace(body.parkingSpace());
            r.setToRent(body.isToRent());
            r.setHabitability(body.habitability());
            Condition c = Condition.valueOf(body.condition());
            r.setCondition(c);
            for (String s: body.typeOfProperty()) {
                TypeOfProperty t = TypeOfProperty.valueOf(s);
                r.getTypeOfProperty().add(t);
            }
            r.setOtherCharacteristics(body.otherCharacteristics());
            r.setCondominiumFees(body.condominiumFees());
            r.setMaximal(body.maximal());
            r.setRegions(body.regions());
            r.setCities(body.cities());
            r.setNote(body.note());
            r.setCustomer(found);
            requestRepo.save(r);
            found.setRequest(r);
            customerRepo.save(found);
            return r;
        }else {
            throw new BadRequestException("il cliente possiede già una richiesta a suo carico, eliminare o aggiornare quest'ultima per poter continuare!");
        }
    }
    public Request changeRequestInfo(long id, ChangeRequestInfoDTO body){
        Request toUpdate = this.getById(id);
        toUpdate.setSurface(body.surface());
        toUpdate.setNumberOfBathrooms(body.numberOfBathrooms());
        toUpdate.setParkingSpace(body.parkingSpace());
        toUpdate.setToRent(body.isToRent());
        toUpdate.setHabitability(body.habitability());
        toUpdate.setNumberOfRooms(body.numberOfRooms());
        Condition c = Condition.valueOf(body.condition());
        toUpdate.setCondition(c);
        for (String s: body.typeOfProperty()) {
            TypeOfProperty t = TypeOfProperty.valueOf(s);
            toUpdate.getTypeOfProperty().add(t);
        }
        toUpdate.setOtherCharacteristics(body.otherCharacteristics());
        toUpdate.setCondominiumFees(body.condominiumFees());
        toUpdate.setMaximal(body.price());
        toUpdate.setRegions(body.regions());
        toUpdate.setCities(body.cities());
        toUpdate.setNote(body.note());
        requestRepo.save(toUpdate);
        return toUpdate;
    }
    public void delete(long id){
        Request toRemove = this.getById(id);
        requestRepo.delete(toRemove);
    }
}
