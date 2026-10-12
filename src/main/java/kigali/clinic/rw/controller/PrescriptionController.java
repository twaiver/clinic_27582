package kigali.clinic.rw.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kigali.clinic.rw.domain.Appointment;
import kigali.clinic.rw.domain.Prescription;
import kigali.clinic.rw.service.PrescriptionService;

@RestController
@RequestMapping(value = "/api/prescriptions")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> savePrescription(@RequestBody Prescription prescription,
            @RequestParam("appointmentId") UUID id) {

        String result = prescriptionService.savePrescription(prescription, id);

        if (result.equals("Prescription saved successfully")) {
            return new ResponseEntity<>(result, HttpStatus.valueOf(201));
        } else if (result.equals("The appointment with that id does not exist")) {
            return new ResponseEntity<>(result, HttpStatus.valueOf(404));
        } else {
            return new ResponseEntity<>(result, HttpStatus.valueOf(400));
        }
    }

    @GetMapping(value = "/appointment/{appointmentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> savePrescription(@PathVariable UUID appointmentId) {

        Appointment result = prescriptionService.findPrescription(appointmentId);

        if (result != null) {
            return new ResponseEntity<>(result.getPrescriptions(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The appointment with that id does not exist", HttpStatus.valueOf(404));
        }
    }

    @GetMapping(value = "/patient/{ssNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> allPrescri(@PathVariable String ssNumber) {

        List<Prescription> result = prescriptionService.findPrescriptionPateint(ssNumber);

        return new ResponseEntity<>(result, HttpStatus.OK);

    }
}
