package DavideSalzani.ImmobiliareProjectBE.estate;

import DavideSalzani.ImmobiliareProjectBE.client.Customer;
import DavideSalzani.ImmobiliareProjectBE.estate.payloads.ChangeEstateInfoDTO;
import DavideSalzani.ImmobiliareProjectBE.estate.payloads.NewEstateDTO;
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
@RequestMapping("/estates")
public class EstateController {
    @Autowired
    EstateService estateService;


    @PostMapping("")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Estate createNewEstate(@RequestBody @Validated NewEstateDTO body, BindingResult validation){
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }else {
            return estateService.createEstate(body);
        }
    }
    @GetMapping("")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public Page<Estate> getUsers(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size,
                                   @RequestParam(defaultValue = "id") String orderBy) {
        return estateService.getEstates(page, size, orderBy);
    }
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Estate> getAll(){
        return estateService.getAllEstate();
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Estate getSingleEstate(UUID id){
        return estateService.getById(id);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public Estate changeEstateInfo(@PathVariable("id") UUID id, @RequestBody @Validated ChangeEstateInfoDTO body, BindingResult validation){
        if (validation.hasErrors() ) {
            throw new BadRequestException(validation.getAllErrors());
        }else {
            return estateService.updateEstateInfo(id,body);
        }
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEstate(@PathVariable("id") UUID id){
        estateService.deleteEstate(id);
    }
}

