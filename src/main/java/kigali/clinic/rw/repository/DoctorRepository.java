package kigali.clinic.rw.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kigali.clinic.rw.domain.Doctor;


@Repository 
public interface DoctorRepository extends JpaRepository<Doctor,UUID> {

    List<Doctor> findByFirstName(String name);
    List<Doctor> findByLastName(String name);
    
    Optional<Doctor> findById(UUID id);

    Doctor findByFirstNameAndLastName(String firstName, String lastName);

    // Optional<Doctor> findByOffice(Office office);

    // Doctor findByDoctorNumber(int officeNumber);

    @Query (value = "SELECT o FROM Doctor o")
    List<Doctor> findDoctors();
}
