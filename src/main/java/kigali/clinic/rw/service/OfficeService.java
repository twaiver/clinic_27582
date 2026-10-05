package kigali.clinic.rw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kigali.clinic.rw.domain.Office;
import kigali.clinic.rw.repository.OfficeRepository;

@Service 
public class OfficeService {
    

     @Autowired 
   private OfficeRepository officeRepo;

   public String saveOffice(Office office){
      try {
         officeRepo.save(office);
         return "Office saved successfully";
      } catch (Exception e) {
         return "office number is already taken";
      }
      
   }
   

   public String deleteOneOffice(int officeNumber){

      Office getOneOffice = officeRepo.findByOfficeNumber(officeNumber);

      
      if(getOneOffice != null){
            officeRepo.deleteById(getOneOffice.getId());

            return "Office with this number "+officeNumber+ " is deleted successfully";
      }else{

        return "we don't have office with that office Number "+officeNumber;
      }
      
   }

   public String updateOffice(Office office){

      Office getOneOffice = officeRepo.findByOfficeNumber(office.getOfficeNumber());
      
      
      if(getOneOffice != null){
         getOneOffice.setName(office.getName());
            officeRepo.save(getOneOffice);

            return "Office updated";
      }else{

        return "we don't have office with that office Number " + office.getOfficeNumber();
      }
      
   }

   public List<Office> allOffice(){

      List<Office> getAllOffice = officeRepo.findAll();
      
      return getAllOffice;
      
   }

   public List<Object> busiestOffice(){

      List<Object> getAllOffice = officeRepo.busiestOffice();
      
      return getAllOffice;
      
   }



}
