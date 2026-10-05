package kigali.clinic.rw.repository;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

    @Modifying 
    @Transactional 
    @Query (value = "UPDATE Appointment a SET a.status = :stat WHERE a.doctor = :doctor AND NOT a.status  = :notIn AND a.appointmentDate = :appointDate")
    int cancelAppointments(@Param("doctor") Doctor doctor, @Param("appointDate") Date appDate, @Param("stat") AppointmentStatus status, @Param("notIn") AppointmentStatus notIn);

    @Modifying 
    @Transactional 
    @Query (value = "DELETE FROM Appointment a WHERE a.status = 'CANCELLED' AND a.appointmentDate < :appointDate")
    int deleteAppointment(@Param ("appointDate") Date appointDate);
    
}