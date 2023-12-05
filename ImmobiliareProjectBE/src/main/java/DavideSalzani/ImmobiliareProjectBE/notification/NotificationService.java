package DavideSalzani.ImmobiliareProjectBE.notification;

import DavideSalzani.ImmobiliareProjectBE.estate.Estate;
import DavideSalzani.ImmobiliareProjectBE.estate.EstateService;
import DavideSalzani.ImmobiliareProjectBE.notification.payloads.NewNotificationByEstateDTO;
import DavideSalzani.ImmobiliareProjectBE.notification.payloads.NewNotificationByRequestDTO;
import DavideSalzani.ImmobiliareProjectBE.request.Request;
import DavideSalzani.ImmobiliareProjectBE.request.RequestService;
import DavideSalzani.ImmobiliareProjectBE.supportClasses.CommonRequestEstate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
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
        List<CommonRequestEstate> result = requestToEstateMatchingAlgorithm(body);
        Notification n = new Notification();
        int size = result.size() - 1;
        if (result.size() > 2){
            n.setMessage("La richiesta appena inserita ha trovato riscontro in " + size + " immobili.");
        }else if (result.size() == 2){
            n.setMessage("La richiesta appena inserita ha trovato riscontro in " + size + " immobile.");
        }else {
            n.setMessage("La richiesta appena inserita non ha trovato alcun riscontro");
        }
        n.getRequestsMatch().add((Request) result.get(0));
        for (int i = 1; i < result.size(); i++) {
            n.getEstatesMatch().add((Estate) result.get(i));
        }

        n.setTimeStamp(LocalDate.now());
        notificationRepo.save(n);
        return n;
    }
    public Notification createNotificationByEstate(NewNotificationByEstateDTO body){
        List<CommonRequestEstate> result = estateToRequestMatchingAlgorithm(body);
        Notification n = new Notification();
        int size = result.size() - 1;
        if (result.size() > 2){
            n.setMessage("L'immobile' appena inserito ha trovato riscontro in " + size + " richieste.");
        }else if(result.size() == 2){
            n.setMessage("L'immobile appena inserito ha trovato riscontro in " + size + " richiesta.");
        }else {
            n.setMessage("L'immobile appena inserito non ha trovato alcun riscontro");
        }
        n.getEstatesMatch().add((Estate) result.get(0));
        for (int i = 1; i < result.size(); i++) {
            n.getRequestsMatch().add((Request) result.get(i));
        }

        n.setTimeStamp(LocalDate.now());
        notificationRepo.save(n);
        return n;
    }

    public List<CommonRequestEstate> requestToEstateMatchingAlgorithm(NewNotificationByRequestDTO body) {
        List<CommonRequestEstate> result = new ArrayList<>();
        Request toMatch = requestService.getById(body.requestId());
        List<Estate> allEstate = estateService.getAllEstate();
        List<Estate> matchedResult = new ArrayList<>(allEstate);
        // Filter for Regions
        matchedResult = matchedResult.stream()
                .filter(estate -> toMatch.getRegions().isEmpty() || toMatch.getRegions().contains(estate.getAddress().getRegion()))
                .toList();
        if (matchedResult.isEmpty()) {
            result.add(toMatch);
            result.addAll(allEstate);
            return result;
        }

        // Filter for Cities
        matchedResult = matchedResult.stream()
                .filter(estate -> toMatch.getCities().isEmpty() || toMatch.getCities().contains(estate.getAddress().getCity()))
                .toList();
        if (matchedResult.isEmpty()) {
            result.add(toMatch);
            result.addAll(allEstate);
            return result;
        }

        // Filter for Hamlets
        matchedResult = matchedResult.stream()
                .filter(estate -> toMatch.getHamlets().isEmpty() || toMatch.getHamlets().contains(estate.getAddress().getHamlet()))
                .toList();
        if (matchedResult.isEmpty()) {
            result.add(toMatch);
            result.addAll(allEstate);
            return result;
        }

        // Filter for Price
        matchedResult = matchedResult.stream()
                .filter(estate -> estate.getPrice() < toMatch.getMaximal())
                .toList();
        if (matchedResult.isEmpty()) {
            result.add(toMatch);
            result.addAll(allEstate);
            return result;
        }

        // Filter for TypeOfProperty
        matchedResult = matchedResult.stream()
                .filter(estate -> toMatch.getTypeOfProperty().isEmpty() || toMatch.getTypeOfProperty().contains(estate.getTypeOfProperty()))
                .toList();
        if (matchedResult.isEmpty()) {
            result.add(toMatch);
            result.addAll(allEstate);
            return result;
        }

        // Filter for ToRent
        matchedResult = matchedResult.stream()
                .filter(estate -> estate.isToRent() == toMatch.isToRent())
                .toList();
        if (matchedResult.isEmpty()) {
            result.add(toMatch);
            result.addAll(allEstate);
            return result;
        }

        result.add(toMatch);
        result.addAll(allEstate);
        return result;
    }
    public List<CommonRequestEstate> estateToRequestMatchingAlgorithm(NewNotificationByEstateDTO body) {
        List<CommonRequestEstate> result = new ArrayList<>();
        Estate toMatch = estateService.getById(body.estateId());
        List<Request> allRequest = requestService.getAll();
        List<Request> matchedResult = new ArrayList<>(allRequest);
        // Filter for Regions
        matchedResult = matchedResult.stream()
                .filter(request -> request.getRegions().isEmpty() || request.getRegions().contains(toMatch.getAddress().getRegion()) )
                .toList();
        if (matchedResult.isEmpty()) {
            result.add(toMatch);
            result.addAll(matchedResult);
            return result;
        }

        // Filter for Cities
        matchedResult = matchedResult.stream()
                .filter(request -> request.getRegions().isEmpty() || request.getCities().contains(toMatch.getAddress().getCity()))
                .toList();
        if (matchedResult.isEmpty()) {
            result.add(toMatch);
            result.addAll(matchedResult);
            return result;
        }

        // Filter for Hamlets
        matchedResult = matchedResult.stream()
                .filter(request ->request.getHamlets().isEmpty() || request.getHamlets().contains(toMatch.getAddress().getHamlet()))
                .toList();
        if (matchedResult.isEmpty()) {
            result.add(toMatch);
            result.addAll(matchedResult);
            return result;
        }

        // Filter for Price
        matchedResult = matchedResult.stream()
                .filter(request -> request.getMaximal() < toMatch.getPrice())
                .toList();
        if (matchedResult.isEmpty()) {
            result.add(toMatch);
            result.addAll(matchedResult);
            return result;
        }

        // Filter for TypeOfProperty
        matchedResult = matchedResult.stream()
                .filter(request -> request.getTypeOfProperty().isEmpty()  || request.getTypeOfProperty().contains(toMatch.getTypeOfProperty()))
                .toList();
        if (matchedResult.isEmpty()) {
            result.add(toMatch);
            result.addAll(matchedResult);
            return result;
        }
        // Filter for ToRent
        matchedResult = matchedResult.stream()
                .filter(request -> request.isToRent() == toMatch.isToRent())
                .toList();
        if (matchedResult.isEmpty()) {
            result.add(toMatch);
            result.addAll(matchedResult);
            return result;
        }
        result.add(toMatch);
        result.addAll(matchedResult);
        return result;
    }
}
