package kigali.clinic.rw.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kigali.clinic.rw.domain.Appointment;
import kigali.clinic.rw.domain.AppointmentStatus;
import kigali.clinic.rw.domain.Prescription;
import kigali.clinic.rw.repository.PrescriptionRepository;

@Service
public class PrescriptionService {
    @Autowired
    private PrescriptionRepository prescriRepo;

    public String savePrescription(Prescription prescription, UUID id) {

        Appointment result = prescriRepo.checkAppointments(id);
        if (result == null) {
            return "The appointment with that id does not exist";
        } else {

            if (result.getStatus().equals(AppointmentStatus.COMPLETED)) {
                prescription.setIssuedDate(LocalDate.now());
                Appointment sec = new Appointment();
                sec.setId(id);
                prescription.setAppointment(sec);
                prescriRepo.save(prescription);
                return "Prescription saved successfully";
            } else {
                return "Prescriptions are only for " + AppointmentStatus.COMPLETED + " appointments";
            }
        }
    }

    public Appointment findPrescription(UUID id) {

        Appointment result = prescriRepo.checkAppointments(id);


        return result;

    }

    public List<Prescription> findPrescriptionPateint(String ssNumber) {

        List<Prescription> allPret = prescriRepo.byPateints(ssNumber);

        return allPret;
    } 
}
