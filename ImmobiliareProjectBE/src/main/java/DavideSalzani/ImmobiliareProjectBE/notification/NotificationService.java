package DavideSalzani.ImmobiliareProjectBE.notification;

import DavideSalzani.ImmobiliareProjectBE.estate.Estate;
import DavideSalzani.ImmobiliareProjectBE.estate.EstateService;
import DavideSalzani.ImmobiliareProjectBE.notification.payloads.NewNotificationByRequestDTO;
import DavideSalzani.ImmobiliareProjectBE.request.Request;
import DavideSalzani.ImmobiliareProjectBE.request.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {
    @Autowired
    NotificationRepository notificationRepo;
    @Autowired
    RequestService requestService;
    @Autowired
    EstateService estateService;

    private List<Estate> requestToEstateMatchingAlgorithm(NewNotificationByRequestDTO body){
        Request toMatch = requestService.getById(body.requestId());
        if (!toMatch.getRegions().isEmpty()) {
            List<Estate> allEstate = estateService.getAllEstate();
            List<Estate> matchedResultByRegion = new ArrayList<>(allEstate.stream().filter(estate -> toMatch.getRegions().stream().anyMatch(region -> estate.getAddress().getRegion().equals(region))).toList());
            if (!matchedResultByRegion.isEmpty() && !toMatch.getCities().isEmpty()) {
                List<Estate> matchedResultByCity = new ArrayList<>();
                matchedResultByCity = matchedResultByRegion.stream().filter(estate -> toMatch.getCities().stream().anyMatch(city -> estate.getAddress().getCity().equals(city))).toList();
                if (!matchedResultByCity.isEmpty() && !toMatch.getHamlets().isEmpty()) {
                    List<Estate> matchedResultByHamlet = new ArrayList<>();
                    matchedResultByHamlet = matchedResultByCity.stream().filter(estate -> toMatch.getHamlets().stream().anyMatch(hamlet -> estate.getAddress().getHamlet().equals(hamlet))).toList();
                    if (!matchedResultByHamlet.isEmpty() && toMatch.getRangeOfPrice().size() == 2) {
                        List<Estate> matchedResultByPrice = new ArrayList<>();
                        matchedResultByPrice = matchedResultByHamlet.stream().filter(estate -> estate.getPrice() > toMatch.getRangeOfPrice().get(0) && estate.getPrice() < toMatch.getRangeOfPrice().get(1)).toList();
                        return matchedResultByPrice;
                    } else if (!matchedResultByHamlet.isEmpty() && toMatch.getRangeOfPrice().size() == 1) {
                        List<Estate> matchedResultByPrice = new ArrayList<>();
                        matchedResultByPrice = matchedResultByHamlet.stream().filter(estate -> estate.getPrice() <= toMatch.getRangeOfPrice().get(0)).toList();
                        return matchedResultByPrice;
                    } else {
                        return matchedResultByHamlet;
                    }
                }else {
                    if (!matchedResultByCity.isEmpty() && toMatch.getRangeOfPrice().size() == 2) {
                        matchedResultByCity = matchedResultByCity.stream().filter(estate -> estate.getPrice() > toMatch.getRangeOfPrice().get(0) && estate.getPrice() < toMatch.getRangeOfPrice().get(1)).toList();
                        return matchedResultByCity;
                    } else if (!matchedResultByCity.isEmpty() && toMatch.getRangeOfPrice().size() == 1) {
                        matchedResultByCity = matchedResultByCity.stream().filter(estate -> estate.getPrice() > toMatch.getRangeOfPrice().get(0)).toList();
                        return matchedResultByCity;
                            }else {
                                return matchedResultByCity;
                            }
                }
            }else {
                if (!matchedResultByRegion.isEmpty() && toMatch.getRangeOfPrice().size() == 2) {
                    matchedResultByRegion = matchedResultByRegion.stream().filter(estate -> estate.getPrice() > toMatch.getRangeOfPrice().get(0) && estate.getPrice() < toMatch.getRangeOfPrice().get(1)).toList();
                    return matchedResultByRegion;
                }else if (!matchedResultByRegion.isEmpty() && toMatch.getRangeOfPrice().size() == 1){
                    matchedResultByRegion = matchedResultByRegion.stream().filter(estate -> estate.getPrice() > toMatch.getRangeOfPrice().get(0)).toList();
                    return matchedResultByRegion;
                }else {
                    return matchedResultByRegion;
                }
            }
        }else {
            return new ArrayList<>();
        }
    }
}
