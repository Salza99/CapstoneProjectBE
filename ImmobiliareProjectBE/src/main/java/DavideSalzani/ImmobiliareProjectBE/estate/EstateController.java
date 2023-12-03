package DavideSalzani.ImmobiliareProjectBE.estate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EstateController {
    @Autowired
    EstateService estateService;


}
