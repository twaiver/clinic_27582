package kigali.clinic.rw.domain;


import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity 
public class Specialization {

    @Id 
    @GeneratedValue (strategy = GenerationType.UUID)
     private UUID id;

     @Column (name="name")
     private String name;

     

     @ManyToMany 
     @JoinTable (
        name = "doctor_specialization",
        joinColumns = @JoinColumn (name="specialization_id"),
        inverseJoinColumns = @JoinColumn (name="doctor_id")
     )
     @JsonIgnore 
     private Set<Doctor> doctors = new HashSet<>();

     public UUID getId() {
        return id;
     }


     public String getName() {
         return name;
     }

     public void setName(String name) {
         this.name = name;
     }


     public void setDoctors(Set<Doctor> docs) {
        this.doctors = docs;
     }

     public Set<Doctor> getDoctors() {
        return doctors;
     }

     

}