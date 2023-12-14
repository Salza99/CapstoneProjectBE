package DavideSalzani.ImmobiliareProjectBE.address;

import DavideSalzani.ImmobiliareProjectBE.address.payloads.NewAddressDTO;
import DavideSalzani.ImmobiliareProjectBE.exceptions.BadRequestException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/addresses")
public class AddressController {
    @Autowired
    AddressService addressService;
@Value("${spring.geo.regions}")
    private String geoNamesUrl;
    @PostMapping("")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Address createAddress(@RequestBody @Validated NewAddressDTO body, BindingResult validation){
        if (validation.hasErrors()){
            throw new BadRequestException(validation.getAllErrors());
        }else {
            return addressService.createAddress(body);
        }
    }
    @GetMapping("")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public List<Address> getAll(){
        return addressService.findAll();
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public Address getSingleAddress(long id){
        return addressService.findById(id);
    }
    @GetMapping("/geoRegions")
    public ResponseEntity<?> getGeoNamesData() throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String jsonData = restTemplate.getForObject(geoNamesUrl, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        Object data = objectMapper.readValue(jsonData, Object.class);
        return ResponseEntity.ok(data);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAddress(@PathVariable("id") long id){
        addressService.delete(id);
    }
}
