package kigali.clinic.rw.repository;

import java.util.List;
import java.util.UUID;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kigali.clinic.rw.domain.Patient;


@Repository 
public interface PatientRepository extends JpaRepository<Patient,UUID> {

    List<Patient> findByFirstName(String name);
    List<Patient> findByLastNameIgnoreCaseOrderByFirstNameAsc(String name);

    Patient findByFirstNameAndLastName(String firstName, String lastName);

    // Optional<Patient> findByOffice(Patient patient);

   //  Patient findById(int officeNumber);

    @Query (value = "SELECT o FROM Patient o")
    List<Patient> findPatients();
}
