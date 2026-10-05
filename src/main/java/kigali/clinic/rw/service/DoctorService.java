package kigali.clinic.rw.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kigali.clinic.rw.domain.Doctor;
import kigali.clinic.rw.repository.DoctorRepository;

@Service
public class DoctorService {

   @Autowired
   private DoctorRepository doctorRepo;

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

}
