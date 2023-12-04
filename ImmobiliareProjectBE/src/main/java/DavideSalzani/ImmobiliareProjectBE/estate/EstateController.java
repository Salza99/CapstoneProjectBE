package DavideSalzani.ImmobiliareProjectBE.estate;

import DavideSalzani.ImmobiliareProjectBE.client.Customer;
import DavideSalzani.ImmobiliareProjectBE.estate.payloads.NewEstateDTO;
import DavideSalzani.ImmobiliareProjectBE.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/estates")
public class EstateController {
    @Autowired
    EstateService estateService;


    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Estate createNewEstate(@RequestBody @Validated NewEstateDTO body, BindingResult validation){
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }else {
            return estateService.createEstate(body);
        }
    }
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<Estate> getAll(){
        return estateService.getAllEstate();
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Estate getSingleAddress(UUID id){
        return estateService.getById(id);
    }
}
