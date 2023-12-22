package DavideSalzani.ImmobiliareProjectBE.appointment;

import DavideSalzani.ImmobiliareProjectBE.address.AddressRepository;
import DavideSalzani.ImmobiliareProjectBE.appointment.payloads.NewAppointmentDTO;
import DavideSalzani.ImmobiliareProjectBE.appointment.payloads.PatchDescriptionDTO;
import DavideSalzani.ImmobiliareProjectBE.appointment.payloads.PatchStatusDTO;
import DavideSalzani.ImmobiliareProjectBE.client.CustomerRepository;
import DavideSalzani.ImmobiliareProjectBE.exceptions.NotFoundException;
import DavideSalzani.ImmobiliareProjectBE.user.User;
import DavideSalzani.ImmobiliareProjectBE.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class AppointmentService {
    @Autowired
    AppointmentRepository appointmentRepo;
    @Autowired
    UserRepository userRepo;
    @Autowired
    AddressRepository addressRepo;
    @Autowired
    CustomerRepository customerRepo;

    public Appointment createNewAppointment(NewAppointmentDTO body){
        Appointment a = new Appointment();
        userRepo.findById(body.assignedAgent());
        a.setAssignedAgent(body.assignedAgent());
        if (body.clientInvolved() != null) {
            customerRepo.findById(body.clientInvolved());
            a.setClientInvolved(body.clientInvolved());
        }
        if (body.propertyAddress() != null) {
            addressRepo.findById(body.propertyAddress());
            a.setPropertyAddress(body.propertyAddress());
        }
        a.setDescription(body.description());
        a.setAdditionalNotes(body.additionalNotes());
        a.setPriority(body.priority());
        a.setType(body.type());
        a.setDateTime(body.dateTime());
        a.setAdditionalNotes(body.additionalNotes());
        a.setStatus(body.status());

        appointmentRepo.save(a);
        return a;
    }
    public List<Appointment> getAllAppointment(){
        return appointmentRepo.findAll();
    }
    public List<Appointment> getTodayAppointment(UUID id){
        List<Appointment> allAppointment = this.getAllAppointment();
        List<Appointment> todayAllAppointment = new ArrayList<>();
        for (Appointment a: allAppointment ) {
            if (a.getAssignedAgent() == id && Objects.equals(a.getDateTime().toLocalDate(), LocalDateTime.now().toLocalDate())) {
                todayAllAppointment.add(a);
            }
        }
        return todayAllAppointment;
    }
    public List<Appointment> getAppointmentByDate(LocalDate date, UUID id){
        List<Appointment> allAppointment = this.getAllAppointment();
        List<Appointment> allAppointmentByDate = new ArrayList<>();
        for (Appointment a: allAppointment ) {
            if (a.getAssignedAgent() == id && Objects.equals(a.getDateTime().toLocalDate(), date)) {
                allAppointmentByDate.add(a);
            }
        }
        return allAppointmentByDate;
    }
    public void changeAppointmentStatus(PatchStatusDTO status, long id) {
        Appointment found = appointmentRepo.findById(id).orElseThrow(() -> new NotFoundException("appuntamento"));
        found.setStatus(status.status());
        appointmentRepo.save(found);
    }
    public void changeAppointmentPriority(int priority, long id){
        Appointment found = appointmentRepo.findById(id).orElseThrow(() -> new NotFoundException("appuntamento"));
        found.setPriority(priority);
        appointmentRepo.save(found);
    }
    public void changenotes(long id, String note){
        Appointment found = appointmentRepo.findById(id).orElseThrow(() -> new NotFoundException("appuntamento"));
        found.setAdditionalNotes(note);
        appointmentRepo.save(found);
    }
    public void changeDescription(long id, PatchDescriptionDTO description){
        Appointment found = appointmentRepo.findById(id).orElseThrow(() -> new NotFoundException("appuntamento"));
        found.setDescription(description.description());
        appointmentRepo.save(found);
    }
    public void deleteAppointment(long id){
        Appointment toRemove = appointmentRepo.findById(id).orElseThrow(() -> new NotFoundException("appuntamento"));
        appointmentRepo.delete(toRemove);
    }
}
