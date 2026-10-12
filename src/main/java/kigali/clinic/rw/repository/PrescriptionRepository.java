package kigali.clinic.rw.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kigali.clinic.rw.domain.Appointment;
import kigali.clinic.rw.domain.Prescription;

@Repository 
public interface PrescriptionRepository extends JpaRepository<Prescription, UUID> {
    
    @Query (value = "SELECT a FROM Appointment a WHERE a.id = :id")
    Appointment checkAppointments(@Param("id") UUID id);

    Appointment findByAppointmentId(UUID id);

    @Query (value = "SELECT s FROM Appointment a JOIN a.patient p JOIN a.prescriptions s WHERE p.ssNumber = :ss ORDER BY s.issuedDate ASC")
    List<Prescription> byPateints(@Param ("ss") String ssNumber);
    
}
