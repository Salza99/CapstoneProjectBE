package DavideSalzani.ImmobiliareProjectBE.client;

import DavideSalzani.ImmobiliareProjectBE.address.Address;
import DavideSalzani.ImmobiliareProjectBE.client.payload.ChangeCustomerInfoDTO;
import DavideSalzani.ImmobiliareProjectBE.client.payload.NewCustomerDTO;
import DavideSalzani.ImmobiliareProjectBE.exceptions.AlreadyExistException;
import DavideSalzani.ImmobiliareProjectBE.exceptions.BadRequestException;
import DavideSalzani.ImmobiliareProjectBE.exceptions.NotFoundException;
import DavideSalzani.ImmobiliareProjectBE.user.User;
import DavideSalzani.ImmobiliareProjectBE.user.UserRepository;
import DavideSalzani.ImmobiliareProjectBE.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepo;
    @Autowired
    UserRepository userRepo;
    public Customer findSingleCustomer(UUID id){
        return customerRepo.findById(id).orElseThrow(()-> new NotFoundException("Cliente "));
    }
    public List<Customer> getAllCustomer(){
        return customerRepo.findAll();
    }

    public Customer createNewCustomer(NewCustomerDTO body, UUID userId){
        if (customerRepo.findByEmail(body.email()) != null) {
            throw new AlreadyExistException("email ");
        } else {
            if (userRepo.findById(userId).isEmpty()){
                throw new BadRequestException("la richiesta di inserimento di un cliente deve essere associata ad uno user");
            } else {
                User u = userRepo.findById(userId).orElseThrow(() -> new NotFoundException("utente "));
                Customer c = new Customer();
                c.setBirthDay(body.birthDay());
                c.setEmail(body.email());
                c.setName(body.name());
                c.setSurname(body.surname());
                c.setPhone(Long.parseLong(body.phone()));
                c.setFollowedByUser(u);

                customerRepo.save(c);

                return c;
            }
        }
    }
    public Customer changeInfo(UUID id, ChangeCustomerInfoDTO body) {
        Customer toUpdate = this.findSingleCustomer(id);
        toUpdate.setEmail(body.email());
        toUpdate.setPhone(Long.parseLong(body.phone()));
        toUpdate.setBirthDay(body.birthdate());
        customerRepo.save(toUpdate);
        return toUpdate;
    }
    public void deleteCustomer(UUID id) {
        Customer toDelete = this.findSingleCustomer(id);
        customerRepo.delete(toDelete);
    }
}
