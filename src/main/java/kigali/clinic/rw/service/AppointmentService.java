package kigali.clinic.rw.service;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kigali.clinic.rw.domain.Appointment;
import kigali.clinic.rw.domain.AppointmentDTO;
import kigali.clinic.rw.domain.AppointmentStatus;
import kigali.clinic.rw.repository.AppointmentRepository;

@Service 
public class AppointmentService {

    @Autowired 
    private AppointmentRepository appRepo;

    public String saveAppointment(Appointment appointment) {

        try {

            Appointment result = appRepo.findByDoctorAndPatient(appointment.getDoctor(), appointment.getPatient());

            if(result == null) {

            appRepo.save(appointment);
            return "The appointment saved successfully";

            } else {
                return "The appointment is already exist";
            }
        } catch (Exception e) {
            return "The appointment failed to save";
        }
    }

    public String updateAppointment(Appointment appointment, UUID id) {

        try {
            boolean result = appRepo.existsById(id);

            if(result) {

                Optional<Appointment> app = appRepo.findById(id);

                Appointment sendThis = app.get();
                appointment.setId(sendThis.getId());

                appRepo.save(appointment);

                return "the Appointmet updated well";
            } else {
                return "we don't have that appoinintmet";
            }
        } catch (Exception e) {
            return "Failed to update the appointment";
        }
    }

    public String deleteAppointment(UUID id){
        try {
            boolean result = appRepo.existsById(id);

            if(result) {
                appRepo.deleteById(id);
                return "Appointment deleted";
            } else {
                return "We don't have the appointment with that id";
            }
        } catch (Exception e) {
            return "Failed to delete in database";
        }
    }

    public List<Appointment> allAppointments() {
        List<Appointment> result = appRepo.findAll();

        return result;
    }

    public List<Appointment> allPendingAppointment(UUID id) {

        AppointmentStatus status = AppointmentStatus.SCHEDULED;


        List<Appointment> result = appRepo.findByDoctorIdAndStatusOrderByAppointmentDateAsc(id, status);


        return result;
    }

    public List<AppointmentDTO> allExpiredAppointment() {


        AppointmentStatus status = AppointmentStatus.SCHEDULED;


        Long mills = System.currentTimeMillis();
        Date today = new Date(mills);
        System.out.println(today);
        List<Appointment> result = appRepo.findByAppointmentDateLessThanAndStatusOrderByAppointmentDateAsc(today, status);

        List<AppointmentDTO> ne = new ArrayList<>();

        for(Appointment c: result) {
            AppointmentDTO dec = new AppointmentDTO();
            dec.setAppointmentDate(c.getAppointmentDate());
            dec.setDoctorName(c.getDoctor().getFirstName() + " " + c.getDoctor().getLastName());
            dec.setPatientName(c.getPatient().getFirstName() + " " + c.getPatient().getLastName());
            ne.add(dec);
        }

        return ne;
    }

}
