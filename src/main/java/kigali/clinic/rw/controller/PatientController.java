package kigali.clinic.rw.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kigali.clinic.rw.domain.Patient;
import kigali.clinic.rw.service.PatientService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController 
@RequestMapping (value = "/api/patients")
public class PatientController {
  
    @Autowired 
    private PatientService patientService;

    
     @PostMapping(value="/save", consumes = MediaType.APPLICATION_JSON_VALUE, 
        produces = MediaType.APPLICATION_JSON_VALUE
     )
    public ResponseEntity<?> savePatient(@RequestBody Patient patient){
        
      String returnedMessage =  patientService.savePatient(patient);
     
        return new ResponseEntity<>(returnedMessage, HttpStatus.CREATED );
      
    }
    

    @DeleteMapping (value="/delete/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
     )
    public ResponseEntity<?> deletePatient(@PathVariable UUID id){

      String returnedMessage =  patientService.deleteOnePatient(id);
      
        return new ResponseEntity<>(returnedMessage,HttpStatus.ALREADY_REPORTED );
      
    }

    @PutMapping (value="/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, 
        produces = MediaType.APPLICATION_JSON_VALUE
     )
    public ResponseEntity<?> updateOffice(@RequestBody Patient patient, @PathVariable UUID id){
      
      String returnedMessage =  patientService.updatePatient(patient, id);
      
        return new ResponseEntity<>(returnedMessage,HttpStatus.CREATED );
      
    }

    @GetMapping (value="/all", produces = MediaType.APPLICATION_JSON_VALUE
     )
    public ResponseEntity<?> allOffices(){
      
      List<Patient> returnedMessage =  patientService.allPatient();

      if(returnedMessage.size() > 0) {
      
        return new ResponseEntity<>(returnedMessage,HttpStatus.CREATED );
      } else {
        return new ResponseEntity<>("No Patient available", HttpStatus.CREATED);
      }
      
    }

    @GetMapping (value = "/by-last-name", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchByLastName(@RequestParam("lastName") String lastName) {

      List<Patient> patients = patientService.searchByLastName(lastName);

      return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @GetMapping (value = "/of-doctor/{doctorId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchByDoctor(@PathVariable  UUID doctorId) {

      List<Patient> patients = patientService.searchByDoctor(doctorId);


      if(!patients.isEmpty()) {

      return new ResponseEntity<>(patients, HttpStatus.OK);

      } else {
        return new ResponseEntity<>("The doctor with that id does not exist", HttpStatus.NOT_FOUND);
      }
    }
}
