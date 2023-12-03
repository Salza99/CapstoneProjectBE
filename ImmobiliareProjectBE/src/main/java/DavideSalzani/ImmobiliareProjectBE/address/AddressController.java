package DavideSalzani.ImmobiliareProjectBE.address;

import DavideSalzani.ImmobiliareProjectBE.address.payloads.NewAddressDTO;
import DavideSalzani.ImmobiliareProjectBE.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addresses")
public class AddressController {
    @Autowired
    AddressService addressService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Address createAddress(@RequestBody @Validated NewAddressDTO body, BindingResult validation){
        if (validation.hasErrors()){
            throw new BadRequestException(validation.getAllErrors());
        }else {
            return addressService.createAddress(body);
        }
    }
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<Address> getAll(){
        return addressService.findAll();
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Address getSingleAddress(long id){
        return addressService.findById(id);
    }
}
