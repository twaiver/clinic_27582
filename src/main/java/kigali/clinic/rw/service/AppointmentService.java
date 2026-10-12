package kigali.clinic.rw.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import kigali.clinic.rw.domain.Appointment;
import kigali.clinic.rw.domain.AppointmentDTO;
import kigali.clinic.rw.domain.AppointmentStatus;
import kigali.clinic.rw.domain.Doctor;
import kigali.clinic.rw.repository.AppointmentRepository;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appRepo;

    public String saveAppointment(Appointment appointment) {

        LocalDate isBefore = appointment.getAppointmentDate().toLocalDate();
        if (isBefore.isBefore(LocalDate.now())) {
            return "Appointment date cannot be in past";
        } else {

            AppointmentStatus sendThis = AppointmentStatus.CANCELLED;

            boolean result = appRepo.existsByDoctorAndStatusNotAndAppointmentDate(appointment.getDoctor(), sendThis,
                    appointment.getAppointmentDate());

            if (!result) {
                appointment.setStatus(AppointmentStatus.SCHEDULED);
                appRepo.save(appointment);
                return "The appointment saved successfully";

            } else {
                return "Doctor is already booked on that date";
            }

        }

    }

    public String updateAppointment(Appointment appointment, UUID id) {

        try {
            boolean result = appRepo.existsById(id);

            if (result) {

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

    public String deleteAppointment(UUID id) {
        try {
            boolean result = appRepo.existsById(id);

            if (result) {
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
        List<Appointment> result = appRepo.findByAppointmentDateLessThanAndStatusOrderByAppointmentDateAsc(today,
                status);

        List<AppointmentDTO> ne = new ArrayList<>();

        for (Appointment c : result) {
            AppointmentDTO dec = new AppointmentDTO();
            dec.setAppointmentDate(c.getAppointmentDate());
            dec.setDoctorName(c.getDoctor().getFirstName() + " " + c.getDoctor().getLastName());
            dec.setPatientName(c.getPatient().getFirstName() + " " + c.getPatient().getLastName());
            ne.add(dec);
        }

        return ne;
    }

    public List<Appointment> searchByStatus(AppointmentStatus status) {

        List<Appointment> appointments = appRepo.findByStatusOrderByAppointmentDateAsc(status);

        return appointments;
    }

    public List<Appointment> searchByRange(Date start, Date end) {

        List<Appointment> appointments = appRepo.findByAppointmentDateBetween(start, end);

        return appointments;
    }

    public List<Object> appointmentsPerStatus() {

        List<Object> appointments = appRepo.appointmentsPerStatus();

        return appointments;
    }

    public int cancelAppointment(UUID doctorId, Date appDate) {

        Doctor doc = new Doctor();
        doc.setId(doctorId);
        int result = appRepo.cancelAppointments(doc, appDate, AppointmentStatus.CANCELLED, AppointmentStatus.COMPLETED);

        return result;
    }

    public Page<Appointment> getPaging(int page, int size, String sort) {

        Pageable sendThis = PageRequest.of(page, size, Sort.by(sort).descending());

        Page<Appointment> result = appRepo.findAll(sendThis);

        return result;
    }

    public int deleteAppointment(Date appDate) {

        int result = appRepo.deleteAppointment(appDate);

        return result;
    }

    public String changeStatus(UUID id, AppointmentStatus status) {
        Optional<Appointment> app = appRepo.findById(id);
        if (app.isEmpty()) {
            return "The appointment with that id is does not exist";
        } else {

            if (app.get().getStatus().equals(AppointmentStatus.SCHEDULED)
                    && !(status.equals(AppointmentStatus.CANCELLED) || status.equals(AppointmentStatus.CONFIRMED))) {
                return "Cannot change status from " + app.get().getStatus() + " to " + status;
            } else if (app.get().getStatus().equals(AppointmentStatus.CONFIRMED)
                    && !(status.equals(AppointmentStatus.CANCELLED) || status.equals(AppointmentStatus.COMPLETED))) {
                return "Cannot change status from " + app.get().getStatus() + " to " + status;
            } else if (app.get().getStatus().equals(AppointmentStatus.COMPLETED)
                    || app.get().getStatus().equals(AppointmentStatus.CANCELLED)) {
                return "Cannot change status from " + app.get().getStatus() + " to " + status;
            } else {

                app.get().setStatus(status);
                appRepo.save(app.get());

                return "Appointment status updated successfully";
            }

        }
    }

    public String reschangeAppointment(Date newDate, UUID id) {

        LocalDate isBefore = newDate.toLocalDate();

        if (isBefore.isBefore(LocalDate.now())) {
            return "Appointment date cannot be in the past";
        } else {
            Optional<Appointment> app = appRepo.findById(id);
            if (app.isEmpty()) {
                return "the id does not exist";
            } else {

                if (app.get().getStatus().equals(AppointmentStatus.COMPLETED)
                        || app.get().getStatus().equals(AppointmentStatus.CANCELLED)) {
                    return "" + app.get().getStatus() + " appointment cannot rescheduled";
                } else {
                    boolean result = appRepo.existsByDoctorAndStatusNotAndStatusNotAndAppointmentDate(
                            app.get().getDoctor(), AppointmentStatus.CANCELLED, AppointmentStatus.CONFIRMED, newDate);
                    if (result) {
                        return "Doctor is already booked on that day";
                    } else {
                        app.get().setAppointmentDate(newDate);
                        app.get().setStatus(AppointmentStatus.SCHEDULED);
                        appRepo.save(app.get());
                        return "Appointment rescheduled successfully";
                    }
                }
            }
        }
    }

}
