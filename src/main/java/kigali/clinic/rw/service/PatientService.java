package kigali.clinic.rw.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kigali.clinic.rw.domain.Doctor;
import kigali.clinic.rw.domain.Patient;
import kigali.clinic.rw.repository.PatientRepository;

@Service
public class PatientService {

   @Autowired
   private PatientRepository patientRepo;

   public String savePatient(Patient patient) {
      try {
         Patient result = patientRepo.findByFirstNameAndLastName(patient.getFirstName(), patient.getLastName());
         if (result == null) {
            patientRepo.save(patient);
            return "Pateint saved successfully";
         } else {
            return "patient already exist";
         }

      } catch (Exception e) {
         return "Patient failed to save";
      }

   }

   public String deleteOnePatient(UUID id) {

      try {
         boolean result = patientRepo.existsById(id);

         if (result) {

            patientRepo.deleteById(id);

            return "Patient with names number is deleted successfully";

         } else {

            return "We don't have Patient with names ";
         }
      } catch (Exception e) {
         return "Failed to update the patient";
      }

   }

   public String updatePatient(Patient patient, UUID id) {

      try {

         boolean result = patientRepo.existsById(id);

         if (result) {

            Optional<Patient> pat = patientRepo.findById(id);
            Patient newP = pat.get();
            newP.setDateOfBirth(patient.getDateOfBirth());
            newP.setFirstName(patient.getFirstName());
            newP.setLastName(patient.getLastName());

            patientRepo.save(newP);

            return "Patient updated";
         } else {

            return "Patient is not available, we don't have him or her.";
         }
      } catch (Exception e) {
         return "Failed to update pateint information";
      }

   }

   public List<Patient> allPatient() {

      List<Patient> getAllPatient = patientRepo.findAll();

      return getAllPatient;

   }


   public List<Patient> searchByLastName(String lastName) {

      List<Patient> patients = patientRepo.findByLastNameIgnoreCaseOrderByFirstNameAsc(lastName);

      return patients;
   }


   public List<Patient> searchByDoctor(UUID id) {

      Doctor doc = new Doctor();
      doc.setId(id);


      List<Patient> patients = patientRepo.searchByDoctor(doc);

      return patients;
   }

   public List<Patient> frequentPatient(int min) {

      
      List<Patient> patients = patientRepo.frequentPatient(min);

      return patients;
   }

}
