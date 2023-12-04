package DavideSalzani.ImmobiliareProjectBE.request;

import DavideSalzani.ImmobiliareProjectBE.estate.Estate;
import DavideSalzani.ImmobiliareProjectBE.exceptions.BadRequestException;
import DavideSalzani.ImmobiliareProjectBE.request.payloads.NewRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/requests")
public class RequestController {
    @Autowired
    RequestService requestService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Request createNewRequest(@RequestBody @Validated NewRequestDTO body, BindingResult validation){
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }else {
            return requestService.createNewRequest(body);
        }
    }
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<Request> getAll(){
        return requestService.getAll();
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Request getSingleAddress(UUID id){
        return requestService.getById(id);
    }
}
