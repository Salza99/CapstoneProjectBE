package DavideSalzani.ImmobiliareProjectBE.notification;

import DavideSalzani.ImmobiliareProjectBE.estate.Estate;
import DavideSalzani.ImmobiliareProjectBE.estate.EstateService;
import DavideSalzani.ImmobiliareProjectBE.notification.payloads.NewNotificationByRequestDTO;
import DavideSalzani.ImmobiliareProjectBE.request.Request;
import DavideSalzani.ImmobiliareProjectBE.request.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
public class NotificationService {
    @Autowired
    NotificationRepository notificationRepo;
    @Autowired
    RequestService requestService;
    @Autowired
    EstateService estateService;

    public Notification createNotificationByRequest(NewNotificationByRequestDTO body){
        List<Estate> result = requestToEstateMatchingAlgorithm(body);
        Notification n = new Notification();
        if (result.size() != 1){
            n.setMessage("La richiesta appena inserita ha trovato riscontro in " + result.size() + " immobili.");
        }else {
            n.setMessage("La richiesta appena inserita ha trovato riscontro in " + result.size() + " immobile.");
        }
        n.setEstatesMatch(result);
        n.setTimeStamp(LocalDate.now());
        notificationRepo.save(n);
        return n;
    }

    public List<Estate> requestToEstateMatchingAlgorithm(NewNotificationByRequestDTO body) {
        Request toMatch = requestService.getById(body.requestId());
        List<Estate> allEstate = estateService.getAllEstate();
        List<Estate> matchedResult = allEstate.stream()
                .filter(estate -> toMatch.getRegions().isEmpty() ||toMatch.getRegions().contains(estate.getAddress().getRegion()))
                .filter(estate -> toMatch.getCities().isEmpty() || toMatch.getCities().contains(estate.getAddress().getCity()))
                .filter(estate -> toMatch.getHamlets().isEmpty() || toMatch.getHamlets().contains(estate.getAddress().getHamlet()))
                .filter(estate -> estate.getPrice() < toMatch.getMaximal())
                .filter(estate -> toMatch.getTypeOfProperty().isEmpty() || toMatch.getTypeOfProperty().contains(estate.getTypeOfProperty()))
                .filter(estate -> estate.getCondition().equals(toMatch.getCondition()))
                .filter(estate -> estate.isToRent() == toMatch.isToRent())
                .toList();

        return matchedResult;
    }
}
