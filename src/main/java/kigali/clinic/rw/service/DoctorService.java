package kigali.clinic.rw.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kigali.clinic.rw.domain.Doctor;
import kigali.clinic.rw.domain.Office;
import kigali.clinic.rw.domain.Specialization;
import kigali.clinic.rw.repository.DoctorRepository;
import kigali.clinic.rw.repository.SpecializationRepository;

@Service
public class DoctorService {

   @Autowired
   private DoctorRepository doctorRepo;

   @Autowired
   private SpecializationRepository specRepo;

   public String saveDoctor(Doctor doctor) {
      try {
         Doctor result = doctorRepo.findByFirstNameAndLastName(doctor.getFirstName(), doctor.getLastName());

         if (result == null) {
            doctorRepo.save(doctor);
            return "Doctor saved successfully";
         } else {
            return "doctor already exist";
         }

      } catch (Exception e) {
         return "Doctor failed saved";
      }

   }

   public String deleteOneDoctor(UUID id) {

      boolean result = doctorRepo.existsById(id);

      if (result) {
         doctorRepo.deleteById(id);

         return "Doctor with this number is deleted successfully";
      } else {

         return "we don't have Doctor with that names";
      }

   }

   public String updateDoctor(Doctor doctor, UUID id) {

      try {

         boolean dec = doctorRepo.existsById(id);

         if (dec) {

            Optional<Doctor> result = doctorRepo.findById(id);

            Doctor send = result.get();

            send.setFirstName(doctor.getFirstName());
            send.setLastName(doctor.getLastName());
            send.setDateOfBirth(doctor.getDateOfBirth());

            doctorRepo.save(send);

            return "Doctor updated";

         } else {

            return "we don't have Doctor with that name ";
         }
      } catch (Exception e) {

         return "Failed to update the doctor";
      }

   }

   public List<Doctor> allDoctor() {

      List<Doctor> getAllOffice = doctorRepo.findAll();

      return getAllOffice;

   }

   public List<Doctor> searchBySpecialization(String name) {

      List<Doctor> result = doctorRepo.findBySpecializations(name);

      return result;
   }

   public List<Doctor> searchByNoOffice() {

      List<Doctor> result = doctorRepo.findByNoOffice();

      return result;
   }

   public String addSpecializations(UUID doctor, UUID specialization) {

      Optional<Doctor> isDoctorAvailable = doctorRepo.findById(doctor);

      boolean isDoctorHas = doctorRepo.existsByIdAndSpecializationsId(doctor, specialization);

      Specialization isSpecializationAvailable = doctorRepo.existsBySpecializationsId(specialization);

      if (!isDoctorAvailable.isEmpty()) {

         if (isSpecializationAvailable != null) {

            if (isDoctorHas) {

               return "The doctor already has that specialization";

            } else {

               Set<Doctor> sendThis = isSpecializationAvailable.getDoctors();
               Doctor useThis = new Doctor();
               useThis.setId(doctor);
               sendThis.add(useThis);
               isSpecializationAvailable.setDoctors(sendThis);

               specRepo.save(isSpecializationAvailable);

               return "Specialization added to the doctor";
            }
         } else {
            return "The specialization does not exist";
         }
      } else {
         return "The doctor with that id does not exist";
      }
   }

   public String removeSpecializations(UUID doctor, UUID specialization) {

      Optional<Doctor> isDoctorAvailable = doctorRepo.findById(doctor);

      boolean isDoctorHas = doctorRepo.existsByIdAndSpecializationsId(doctor, specialization);

      Specialization isSpecializationAvailable = doctorRepo.existsBySpecializationsId(specialization);

      if (!isDoctorAvailable.isEmpty()) {

         if (isSpecializationAvailable != null) {

            if (!isDoctorHas) {

               return "The doctor does not have that specialization";

            } else {

               isSpecializationAvailable.getDoctors().remove(isDoctorAvailable.get());

               specRepo.save(isSpecializationAvailable);

               return "Specialization removed from doctor";
            }
         } else {
            return "The specialization does not exist";
         }
      } else {
         return "The doctor with that id does not exist";
      }
   }

   public String changeOffice(UUID doctor, int officeNumber) {

      Optional<Doctor> isDoctorAvailable = doctorRepo.findById(doctor);

      Office isOfficeAvailable = doctorRepo.existsByOfficeNumber(officeNumber);

      boolean isDoctorHas = doctorRepo.existsByIdAndOffice(doctor, isOfficeAvailable);

      if (!isDoctorAvailable.isEmpty()) {

         if (isOfficeAvailable != null) {

            if (isDoctorHas) {

               return "The office is already taken by other doctor";

            } else {

               isDoctorAvailable.get().setOffice(isOfficeAvailable);
               doctorRepo.save(isDoctorAvailable.get());

               return "Office changed successfully";
            }
         } else {
            return "The office with that office number does not exist";
         }
      } else {
         return "The doctor with that id does not exist";
      }
   }

}
