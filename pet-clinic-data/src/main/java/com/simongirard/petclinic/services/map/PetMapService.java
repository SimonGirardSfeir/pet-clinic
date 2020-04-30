package com.simongirard.petclinic.services.map;

import com.simongirard.petclinic.model.Pet;
import com.simongirard.petclinic.services.PetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
@Profile({"default", "map"})
public class PetMapService extends AbstractMapService<Pet, Long> implements PetService {

    @Override
    public Set<Pet> findAll() {
        log.info("Find all pets");

        return super.findAll();
    }

    @Override
    public Pet findById(Long id) {
        log.info("Find pet with id {}", id);

        return super.findById(id);
    }

    @Override
    public Pet save(Pet pet) {
        log.info("Pet saved");

        return super.save(pet);
    }

    @Override
    public void delete(Pet pet) {
        log.info("Delete pet");

        super.delete(pet);
    }

    @Override
    public void deleteById(Long id) {
        log.info("Delete pet with id {}", id);

        super.deleteById(id);
    }
}
