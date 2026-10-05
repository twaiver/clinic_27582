package kigali.clinic.rw.repository;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kigali.clinic.rw.domain.Appointment;
import kigali.clinic.rw.domain.AppointmentStatus;
import kigali.clinic.rw.domain.Doctor;
import kigali.clinic.rw.domain.Patient;

@Repository 
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {

    List<Appointment> findByDoctorIdAndStatusOrderByAppointmentDateAsc(UUID doctor, AppointmentStatus status);
    List<Appointment> findByAppointmentDateLessThanAndStatusOrderByAppointmentDateAsc(Date date, AppointmentStatus status);

    List<Appointment> findByStatusOrderByAppointmentDateAsc(AppointmentStatus status);

    List<Appointment> findByAppointmentDateBetween(Date start, Date end);

    Appointment findByDoctorAndPatient(Doctor doctor, Patient patient);


    boolean existsByDoctorAndStatusNotAndAppointmentDate(Doctor doctor, AppointmentStatus status, Date appointmentDate);

    @Query(value = "SELECT a.status, COUNT(a.patient) AS patientsCount FROM Appointment a GROUP BY a.status")
    List<Object> appointmentsPerStatus();
    
}