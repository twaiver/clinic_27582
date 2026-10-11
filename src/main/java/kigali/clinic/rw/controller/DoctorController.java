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

import kigali.clinic.rw.domain.Doctor;
import kigali.clinic.rw.service.DoctorService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController 
@RequestMapping (value = "/api/doctors")
public class DoctorController {
  
    @Autowired 
    private DoctorService doctorService;

    
     @PostMapping(value="/save", consumes = MediaType.APPLICATION_JSON_VALUE, 
        produces = MediaType.APPLICATION_JSON_VALUE
     )
    public ResponseEntity<?> saveOffice(@RequestBody Doctor doctor){
        
      String returnedMessage =  doctorService.saveDoctor(doctor);
     
        return new ResponseEntity<>(returnedMessage,HttpStatus.CREATED );
      
    }
    

    @DeleteMapping(value="/delete/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, 
        produces = MediaType.APPLICATION_JSON_VALUE
     )
    public ResponseEntity<?> deleteDoctor(@PathVariable UUID id){

      String returnedMessage =  doctorService.deleteOneDoctor(id);
      
        return new ResponseEntity<>(returnedMessage,HttpStatus.ALREADY_REPORTED );
      
    }

    @PutMapping (value="/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, 
        produces = MediaType.APPLICATION_JSON_VALUE
     )
    public ResponseEntity<?> updateDoctor(@PathVariable UUID id, @RequestBody Doctor doctor){

      String returnedMessage =  doctorService.updateDoctor(doctor, id);
      
        return new ResponseEntity<>(returnedMessage,HttpStatus.CREATED );
      
    }

    @GetMapping(value="/all", produces = MediaType.APPLICATION_JSON_VALUE
     )
    public ResponseEntity<?> allOffices(){
      
      List<Doctor> returnedMessage =  doctorService.allDoctor();

      if(returnedMessage.size() > 0) {
      
        return new ResponseEntity<>(returnedMessage,HttpStatus.CREATED );
      } else {
        return new ResponseEntity<>("No Doctor available", HttpStatus.CREATED);
      }
      
    }

    @GetMapping (value = "/by-specialization", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchBySpecialization(@RequestParam("name") String name) {

      List<Doctor> doctors = doctorService.searchBySpecialization(name);

      return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    @GetMapping (value = "/without-office", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchByNoOffice() {

      List<Doctor> doctors = doctorService.searchByNoOffice();

      return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    
    @PutMapping (value = "/{doctorId}/specializations/{specializationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addSpecialization(@PathVariable UUID doctorId, @PathVariable UUID specializationId) {

      String result = doctorService.addSpecializations(doctorId, specializationId);

      if(result.equals("Specialization added to the doctor")){
      return new ResponseEntity<>(result, HttpStatus.OK); 
      } else if(result.equals("The doctor already has that specialization")) {
        return new ResponseEntity<>(result, HttpStatus.valueOf(409));
      } else {
        return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
      }
    }

    @DeleteMapping (value = "/{doctorId}/specializations/{specializationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> removeSpecialization(@PathVariable UUID doctorId, @PathVariable UUID specializationId) {
      
      String result = doctorService.removeSpecializations(doctorId, specializationId);

      if(result.equals("Specialization removed from doctor")){
      return new ResponseEntity<>(result, HttpStatus.OK); 
      } else if(result.equals("The doctor does not have that specialization")) {
        return new ResponseEntity<>(result, HttpStatus.valueOf(409));
      } else {
        return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
      }
    }


    @PatchMapping (value = "/{doctorId}/office", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changeOffice(@PathVariable UUID doctorId, @RequestParam ("officeNumber") int officeNumber) {
      
      String result = doctorService.changeOffice(doctorId, officeNumber);

      if(result.equals("Office changed successfully")){
      return new ResponseEntity<>(result, HttpStatus.OK); 
      } else if(result.equals("The office is already taken by other doctor")) {
        return new ResponseEntity<>(result, HttpStatus.valueOf(409));
      } else {
        return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
      }
    }

    
}
