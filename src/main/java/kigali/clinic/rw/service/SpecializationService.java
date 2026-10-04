package kigali.clinic.rw.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kigali.clinic.rw.domain.Specialization;
import kigali.clinic.rw.repository.SpecializationRepository;

@Service
public class SpecializationService {
    @Autowired
    private SpecializationRepository specializationRepo;

    public String saveSpecialization(Specialization specialization) {
        try {
            
            Specialization result = specializationRepo.findByName(specialization.getName());

            if (result == null) {

                specializationRepo.save(specialization);
                return "Specialization data saved";

            } else {
                return "Specialization is already saved";
            }
        } catch (Exception e) {
            return "Specialization Data is not saved";
        }
    }

    public String updateSpecialization(Specialization specialization) {
        try {
            specializationRepo.save(specialization);
            return "Specialization data updated";
        } catch (Exception e) {
            return "Specialization data not updated";
        }
    }

    public String deleteSpecialization(UUID id) {
        try {
            boolean result = specializationRepo.existsById(id);

            if (result) {
                specializationRepo.deleteById(id);

                return "Specialization data deleted";
            } else {
                return "We don't have Specialization data with that id";
            }
        } catch (Exception e) {
            return "Specialization data failed to delete";
        }
    }

    public List<Specialization> getAllSpecialization() {
        List<Specialization> result = specializationRepo.findAll();

        return result;
    }
}
