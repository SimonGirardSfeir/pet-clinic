package com.simongirard.petclinic.services.map;

import com.simongirard.petclinic.model.Speciality;
import com.simongirard.petclinic.model.Vet;
import com.simongirard.petclinic.services.SpecialityService;
import com.simongirard.petclinic.services.VetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
@Profile({"default", "map"})
public class VetMapService extends AbstractMapService<Vet, Long> implements VetService {

    private final SpecialityService specialityService;

    public VetMapService(SpecialityService specialityService) {
        this.specialityService = specialityService;
    }

    @Override
    @Cacheable(value = "vets", key = "#root.methodName")
    public Set<Vet> findAll() {
        log.info("Find all Vets");

        return super.findAll();
    }

    @Override
    public Vet findById(Long id) {
        log.info("Find vet with id {}", id);

        return super.findById(id);
    }

    @Override
    public Vet save(Vet vet) {
        if(vet.getSpecialities() != null && vet.getSpecialities().size() > 0) {
           vet.getSpecialities().forEach(speciality -> {
               if(speciality.getId() == null) {
                   Speciality savedSpeciality = specialityService.save(speciality);
                   speciality.setId(savedSpeciality.getId());
               }
           });
        }

        log.info("Vet saved");

        return super.save(vet);
    }

    @Override
    public void delete(Vet vet) {
        log.info("Delete vet");

        super.delete(vet);
    }

    @Override
    public void deleteById(Long id) {
        log.info("Delete vet with id {}", id);

        super.deleteById(id);
    }

    @Override
    public Vet findByLastName(String lastName) {
        log.info("Find vet with last name {}", lastName);

        return this.findAll()
                .stream()
                .filter(vet -> vet.getLastName().equalsIgnoreCase(lastName))
                .findFirst()
                .orElse(null);
    }
}
