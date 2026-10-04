package kigali.clinic.rw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kigali.clinic.rw.domain.Office;
import kigali.clinic.rw.service.OfficeService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController 
@RequestMapping (value = "/api/office")
public class OfficeController {
  
    @Autowired 
    private OfficeService officeService;

    
     @PostMapping(value="/save", consumes = MediaType.APPLICATION_JSON_VALUE, 
        produces = MediaType.APPLICATION_JSON_VALUE
     )
    public ResponseEntity<?> saveOffice(@RequestBody Office office){
        
      String returnedMessage =  officeService.saveOffice(office);

      System.out.println(returnedMessage);
     
        return new ResponseEntity<>(returnedMessage,HttpStatus.CREATED );
      
    }
    

    @DeleteMapping (value="/delete", consumes = MediaType.APPLICATION_JSON_VALUE, 
        produces = MediaType.APPLICATION_JSON_VALUE
     )
    public ResponseEntity<?> deleteOffice(@RequestBody Office office){

      String returnedMessage =  officeService.deleteOneOffice(office.getOfficeNumber());
      
        return new ResponseEntity<>(returnedMessage,HttpStatus.ALREADY_REPORTED );
      
    }

    @PutMapping(value="/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, 
        produces = MediaType.APPLICATION_JSON_VALUE
     )
    public ResponseEntity<?> updateOffice(@RequestBody Office office){

      
      String returnedMessage =  officeService.updateOffice(office);
      
        return new ResponseEntity<>(returnedMessage,HttpStatus.CREATED );
      
    }

    @GetMapping (value="/all", produces = MediaType.APPLICATION_JSON_VALUE
     )
    public ResponseEntity<?> allOffices(){
      
      List<Office> returnedMessage =  officeService.allOffice();

      if(returnedMessage.size() > 0) {
      
        return new ResponseEntity<>(returnedMessage,HttpStatus.CREATED );
      } else {
        return new ResponseEntity<>("No Office available", HttpStatus.CREATED);
      }
      
    }
}
