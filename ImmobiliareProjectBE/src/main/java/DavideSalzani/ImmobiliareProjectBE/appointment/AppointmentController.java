package DavideSalzani.ImmobiliareProjectBE.appointment;

import DavideSalzani.ImmobiliareProjectBE.appointment.payloads.NewAppointmentDTO;
import DavideSalzani.ImmobiliareProjectBE.appointment.payloads.PatchDescriptionDTO;
import DavideSalzani.ImmobiliareProjectBE.appointment.payloads.PatchStatusDTO;
import DavideSalzani.ImmobiliareProjectBE.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {
    @Autowired
    AppointmentService appointmentService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public Appointment createNewAppointment(@RequestBody @Validated NewAppointmentDTO body, BindingResult validation){
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }else  {
            return appointmentService.createNewAppointment(body);
        }
    }
    @GetMapping("/today/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public List<Appointment> getTodayAppointments(@PathVariable("id") UUID id){
        return appointmentService.getTodayAppointment(id);
    }
    @GetMapping("/date/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public List<Appointment> getDateAppointments(@PathVariable("id") UUID id,@RequestBody LocalDate date){
        return appointmentService.getAppointmentByDate(date, id);
    }
    @PatchMapping("/status/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public void changeStatus(@PathVariable("id") long id,@RequestBody @Validated PatchStatusDTO status, BindingResult validation){
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        appointmentService.changeAppointmentStatus(status, id);
    }
    @PatchMapping("/status/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public void changeDescription(@PathVariable("id") long id, @RequestBody @Validated PatchDescriptionDTO description, BindingResult validation){
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        appointmentService.changeDescription(id, description);
    }
    @PatchMapping("/priority/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public void changeNotes(@PathVariable("id") long id,@RequestBody String notes){
        appointmentService.changenotes(id, notes);
    }
    @PatchMapping("/priority/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public void changePriority(@PathVariable("id") long id,@RequestBody int priority){
        appointmentService.changeAppointmentPriority(priority, id);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public void deleteAppointment(@PathVariable("id") long id){
        appointmentService.deleteAppointment(id);
    }
}
