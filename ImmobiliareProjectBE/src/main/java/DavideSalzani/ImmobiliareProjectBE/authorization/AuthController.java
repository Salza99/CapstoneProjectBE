package DavideSalzani.ImmobiliareProjectBE.authorization;

import DavideSalzani.ImmobiliareProjectBE.exceptions.BadRequestException;
import DavideSalzani.ImmobiliareProjectBE.user.User;
import DavideSalzani.ImmobiliareProjectBE.user.payloads.NewUserDTO;
import DavideSalzani.ImmobiliareProjectBE.user.payloads.UserLoginDTO;
import DavideSalzani.ImmobiliareProjectBE.user.payloads.UserSuccessLoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/register")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public User saveUser(@RequestBody @Validated NewUserDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        } else {
            return authService.save(body);
        }
    }
    @PostMapping("/login")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public UserSuccessLoginDTO login(@RequestBody UserLoginDTO body) throws Exception {
        return new UserSuccessLoginDTO(authService.authenticateUser(body));
    }
}
