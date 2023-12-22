package DavideSalzani.ImmobiliareProjectBE.user;

import DavideSalzani.ImmobiliareProjectBE.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    UserRepository userRepo;

    public User getById(UUID id){
        return userRepo.findById(id).orElseThrow(() -> new NotFoundException("Utente"));
    }

    public Page<User> getUsers(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return userRepo.findAll(pageable);
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }
    public void delete(UUID id) {
        User found = this.getById(id);
        userRepo.delete(found);
    }
    public User findByUsername(String username) {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(username));
    }

}
