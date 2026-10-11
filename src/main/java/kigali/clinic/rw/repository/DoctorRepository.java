package kigali.clinic.rw.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kigali.clinic.rw.domain.Doctor;
import kigali.clinic.rw.domain.Office;
import kigali.clinic.rw.domain.Specialization;


@Repository 
public interface DoctorRepository extends JpaRepository<Doctor,UUID> {

    List<Doctor> findByFirstName(String name);
    List<Doctor> findByLastName(String name);
    
    Optional<Doctor> findById(UUID id);

    Doctor findByFirstNameAndLastName(String firstName, String lastName);

    // Optional<Doctor> findByOffice(Office office);

    // Doctor findByDoctorNumber(int officeNumber);

    @Query(value = "SELECT d FROM Doctor d JOIN d.specializations s WHERE LOWER(s.name) = LOWER(?1)")
    List<Doctor> findBySpecializations(@Param("name") String name);

    @Query(value = "SELECT d FROM Doctor d WHERE d.office is null")
    List<Doctor> findByNoOffice();


    @Query (value = "SELECT s FROM Specialization s WHERE s.id = :id")
    Specialization existsBySpecializationsId(@Param ("id") UUID specialization);

    boolean existsByIdAndSpecializationsId(UUID doctor, UUID specialization);

    boolean existsByIdAndOffice(UUID doctor, Office office);

    @Query (value = "SELECT o FROM Office o WHERE o.officeNumber = :officeNumber")
    Office existsByOfficeNumber(@Param ("officeNumber") int office_number);
    
}
