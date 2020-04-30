package com.simongirard.petclinic.services.map;

import com.simongirard.petclinic.model.Speciality;
import com.simongirard.petclinic.services.SpecialityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
@Profile({"default", "map"})
public class SpecialityMapService extends AbstractMapService<Speciality, Long> implements SpecialityService {

    @Override
    public Set<Speciality> findAll() {
        log.info("Find all Specialities");

        return super.findAll();
    }

    @Override
    public Speciality findById(Long id) {
        log.info("Find speciality with id {}", id);

        return super.findById(id);
    }

    @Override
    public Speciality save(Speciality speciality) {
        log.info("Speciality saved");

        return super.save(speciality);
    }

    @Override
    public void delete(Speciality speciality) {
        log.info("Delete speciality");

        super.delete(speciality);
    }

    @Override
    public void deleteById(Long id) {
        log.info("Delete speciality with id {}", id);

        super.deleteById(id);
    }
}
