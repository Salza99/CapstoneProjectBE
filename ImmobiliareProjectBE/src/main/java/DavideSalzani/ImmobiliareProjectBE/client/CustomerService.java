package DavideSalzani.ImmobiliareProjectBE.client;

import DavideSalzani.ImmobiliareProjectBE.client.payload.NewCustomerDTO;
import DavideSalzani.ImmobiliareProjectBE.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepo;
    public Customer findSingleCustomer(UUID id){
        return customerRepo.findById(id).orElseThrow(()-> new NotFoundException("Cliente "));
    }
    public List<Customer> getAllCustomer(){
        return customerRepo.findAll();
    }

    public Customer createNewCustomer(NewCustomerDTO body){
        Customer c = new Customer();
        c.setBirthDay(body.birthDay());
        c.setEmail(body.email());
        c.setName(body.name());
        c.setPhone(Long.parseLong(body.phone()));
        customerRepo.save(c);
        return c;
    }
}
