package kigali.clinic.rw.controller;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kigali.clinic.rw.domain.Appointment;
import kigali.clinic.rw.domain.AppointmentDTO;
import kigali.clinic.rw.domain.AppointmentStatus;
import kigali.clinic.rw.service.AppointmentService;

@RestController
@RequestMapping(value = "/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appoService;

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveAppointment(@RequestBody Appointment appointment) {

        String result = appoService.saveAppointment(appointment);

        if (result.equals("The appointment saved successfully")) {
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } else if(result.equals("Appointment date cannot be in past")){
            return new ResponseEntity<>(result, HttpStatus.valueOf(400));
        } else {
            return new ResponseEntity<>(result, HttpStatus.CONFLICT);
        }
    }

    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateAppointment(@PathVariable UUID id, @RequestBody Appointment appointment) {
        String result = appoService.updateAppointment(appointment, id);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteAppointment(@PathVariable UUID id) {
        String result = appoService.deleteAppointment(id);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllAppointment() {

        List<Appointment> result = appoService.allAppointments();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/pending/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllPendingAppointment(@PathVariable UUID id) {

        List<Appointment> result = appoService.allPendingAppointment(id);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/expired", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllExpiredAppointment() {

        List<AppointmentDTO> result = appoService.allExpiredAppointment();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/by-status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchByStatus(@RequestParam("status") AppointmentStatus status) {

        List<Appointment> appointments = appoService.searchByStatus(status);

        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @GetMapping(value = "/between", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchByRange(@RequestParam("start") Date start, @RequestParam("end") Date end) {

        List<Appointment> appointments = appoService.searchByRange(start, end);

        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @GetMapping(value = "/stats/by-status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> appointmentPerStatus() {

        List<Object> appointments = appoService.appointmentsPerStatus();

        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @PatchMapping (value = "/cancel-day")
    public ResponseEntity<?> cancelAppointment(@RequestParam ("doctorId") UUID doctorId, @RequestParam ("date") Date appDate) {

        int result = appoService.cancelAppointment(doctorId, appDate);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping (value = "/cancelled-before")
    public ResponseEntity<?> cancelAppointment(@RequestParam ("date") Date appDate) {

        int result = appoService.deleteAppointment(appDate);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPaging(@RequestParam("page") int page, @RequestParam("size") int size, @RequestParam ("sort") String sort) {

        Page<Appointment> result = appoService.getPaging(page, size, sort);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PatchMapping (value = "/{id}/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changeStatus(@PathVariable UUID id, @RequestParam ("status") AppointmentStatus status) {

        String result = appoService.changeStatus(id, status);
        if(result.equals("Appointment status updated successfully")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else if(result.equals("The appointment with that id is does not exist")) {
            return new ResponseEntity<>(result, HttpStatus.valueOf(404));
        } else {
            return new ResponseEntity<>(result, HttpStatus.valueOf(400));
        }
        
    }

    @PatchMapping (value = "/{id}/reschedule", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> rescheduleAppointment(@PathVariable UUID id, @RequestParam ("newDate") Date newDate) {

        String result = appoService.reschangeAppointment(newDate, id);
        if(result.equals("Appointment status updated successfully")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else if(result.equals("Appointment date cannot be in the past")){
            return new ResponseEntity<>(result, HttpStatus.valueOf(400));
        } else {
            return new ResponseEntity<>(result, HttpStatus.valueOf(404));
        }
        
    }

}
