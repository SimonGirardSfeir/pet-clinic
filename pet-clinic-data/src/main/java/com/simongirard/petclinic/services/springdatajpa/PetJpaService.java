package com.simongirard.petclinic.services.springdatajpa;

import com.simongirard.petclinic.model.Pet;
import com.simongirard.petclinic.repositories.PetRepository;
import com.simongirard.petclinic.services.PetService;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@Profile({"jpa", "mysql"})
public class PetJpaService implements PetService {

    private final PetRepository petRepository;

    public PetJpaService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    public Set<Pet> findAll() {
        log.info("Find all pets");

        Set<Pet> pets = new HashSet<>();
        petRepository.findAll().forEach(pets::add);

        return pets;
    }

    @Override
    public Pet findById(Long id) throws NotFoundException {
        log.info("Find pet with id {}", id);

        return petRepository.findById(id).orElseThrow(() -> new NotFoundException("Pet not found. Pet id: " + id));
    }

    @Override
    public Pet save(Pet pet) {
        log.info("Pet saved");

        return petRepository.save(pet);
    }

    @Override
    public void delete(Pet pet) {
        log.info("Delete pet");

        petRepository.delete(pet);
    }

    @Override
    public void deleteById(Long id) {
        log.info("Delete pet with id {}", id);

        petRepository.deleteById(id);
    }
}
