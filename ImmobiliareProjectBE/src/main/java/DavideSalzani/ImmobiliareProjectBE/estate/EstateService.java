package DavideSalzani.ImmobiliareProjectBE.estate;

import DavideSalzani.ImmobiliareProjectBE.estate.payloads.NewEstateDTO;
import DavideSalzani.ImmobiliareProjectBE.exceptions.NotFoundException;
import DavideSalzani.ImmobiliareProjectBE.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EstateService {
    @Autowired
    EstateRepository estateRepo;

    public Estate getById(UUID id){
        return estateRepo.findById(id).orElseThrow(() -> new NotFoundException("Proprietà "));
    }
    public Page<Estate> getEstate(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return estateRepo.findAll(pageable);
    }
    public List<Estate> getAllEstate() {
        return estateRepo.findAll();
    }
    public void delete(UUID id) {
        Estate found = this.getById(id);
        estateRepo.delete(found);
    }
    public Estate createEstate(NewEstateDTO body){
        return null;
    }
}
