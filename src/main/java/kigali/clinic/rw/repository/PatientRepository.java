package kigali.clinic.rw.repository;

import java.util.List;
import java.util.UUID;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kigali.clinic.rw.domain.Appointment;
import kigali.clinic.rw.domain.Doctor;
import kigali.clinic.rw.domain.Patient;


@Repository 
public interface PatientRepository extends JpaRepository<Patient,UUID> {

    List<Patient> findByFirstName(String name);
    List<Patient> findByLastNameIgnoreCaseOrderByFirstNameAsc(String name);

    Patient findByFirstNameAndLastName(String firstName, String lastName);

    // Optional<Patient> findByOffice(Patient patient);

   //  Patient findById(int officeNumber);

   @Query(value = "SELECT DISTINCT a.patient FROM Appointment a WHERE a.doctor = :doctor")
   List<Patient> searchByDoctor(@Param("doctor") Doctor doctor);
}
