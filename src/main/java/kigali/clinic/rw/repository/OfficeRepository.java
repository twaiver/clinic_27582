package kigali.clinic.rw.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kigali.clinic.rw.domain.Doctor;
import kigali.clinic.rw.domain.Office;

@Repository 
public interface OfficeRepository  extends JpaRepository<Office, UUID>{
    
    List<Office> findByName(String name);

    Optional<Office> findByNameAndOfficeNumber(String name, String officeNumber);

    Optional<Office> findByDoctor(Doctor doctor);

    Office findByOfficeNumber(int officeNumber);

    @Query (value = "SELECT o FROM Office o")
    List<Office> findOffices();
    

    // @Query (value = "SELECT o FROM Office o WHERE o.officeNumber = :office")
    // Optional<Office>  findByOfficeNumber(@Param("office") int officeNumber);

    // @Query ("""
    //         SELECT FROM Office O WHERE O.doctor.name : = name
    //         """)
    // List<Office> findByDoctorName(@Param("name") String name);


}
