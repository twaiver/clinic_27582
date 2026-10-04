package kigali.clinic.rw.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kigali.clinic.rw.domain.Specialization;
import kigali.clinic.rw.service.SpecializationService;

@RestController 
@RequestMapping (value = "/api/specialization")
public class SpecializationController {
    
    @Autowired 
    private SpecializationService specialService;

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> specializationSave(@RequestBody Specialization specialization) {

        String result = specialService.saveSpecialization(specialization);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping (value = "/all")
    public ResponseEntity<?> getAllSpecialization() {

        List<Specialization> result = specialService.getAllSpecialization();

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping (value = "/update/{id}")
    public ResponseEntity<?> updateSpecialization(@PathVariable UUID id, @RequestBody Specialization specialization) {

        String result = specialService.updateSpecialization(specialization);
        
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
    
    @DeleteMapping (value = "/delete/{id}")
    public ResponseEntity<?> deleteSpecialization(@PathVariable UUID id) {

        String result = specialService.deleteSpecialization(id);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
