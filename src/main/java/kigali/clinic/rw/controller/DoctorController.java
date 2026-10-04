package kigali.clinic.rw.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kigali.clinic.rw.domain.Doctor;
import kigali.clinic.rw.service.DoctorService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController 
@RequestMapping (value = "/api/doctor")
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
}
