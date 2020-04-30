package com.simongirard.petclinic.services.map;

import com.simongirard.petclinic.model.PetType;
import com.simongirard.petclinic.services.PetTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
@Profile({"default", "map"})
public class PetTypeMapService extends AbstractMapService<PetType, Long> implements PetTypeService {

    @Override
    public Set<PetType> findAll() {
        log.info("Find all pet types");

        return super.findAll();
    }

    @Override
    public PetType findById(Long id) {
        log.info("Find pet type with id {}", id);

        return super.findById(id);
    }

    @Override
    public PetType save(PetType petType) {
        log.info("Pet type saved");

        return super.save(petType);
    }

    @Override
    public void delete(PetType petType) {
        log.info("Delete pet type");

        super.delete(petType);
    }

    @Override
    public void deleteById(Long id) {
        log.info("Delete pet type with id {}", id);

        super.deleteById(id);
    }
}
