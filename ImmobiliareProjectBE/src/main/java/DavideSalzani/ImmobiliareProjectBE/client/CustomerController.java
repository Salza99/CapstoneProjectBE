package DavideSalzani.ImmobiliareProjectBE.client;

import DavideSalzani.ImmobiliareProjectBE.client.payloads.ChangeCustomerInfoDTO;
import DavideSalzani.ImmobiliareProjectBE.client.payloads.NewCustomerDTO;
import DavideSalzani.ImmobiliareProjectBE.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @PostMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Customer createCustomer( @PathVariable("id") UUID userId, @RequestBody @Validated NewCustomerDTO body, BindingResult validation){
        if (validation.hasErrors()){
            throw new BadRequestException(validation.getAllErrors());
        }else {
            return customerService.createNewCustomer(body, userId);
        }
    }
    @GetMapping("")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public Page<Customer> getUsers(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(defaultValue = "id") String orderBy) {
        return customerService.getCustomers(page, size, orderBy);
    }
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public List<Customer> getAll(){
        return customerService.getAllCustomer();
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public Customer getSingleAddress(@PathVariable("id") UUID id){
        return customerService.findSingleCustomer(id);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public Customer changeUserInfo(@PathVariable("id") UUID id, @RequestBody @Validated ChangeCustomerInfoDTO body, BindingResult validation){
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }else {return customerService.changeInfo(id, body);}
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable("id") UUID id){
        customerService.deleteCustomer(id);
    }
}
