package DavideSalzani.ImmobiliareProjectBE.user;

import DavideSalzani.ImmobiliareProjectBE.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    UserRepository userRepo;

    public User getById(UUID id){
        return userRepo.findById(id).orElseThrow(() -> new NotFoundException("Utente"));
    }
}
