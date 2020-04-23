package com.simongirard.petclinic.services.springdatajpa;

import com.simongirard.petclinic.model.Pet;
import com.simongirard.petclinic.repositories.PetRepository;
import com.simongirard.petclinic.services.PetService;
import javassist.NotFoundException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("jpa")
public class PetJpaService implements PetService {

    private final PetRepository petRepository;

    public PetJpaService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    public Set<Pet> findAll() {
        Set<Pet> pets = new HashSet<>();

        petRepository.findAll().forEach(pets::add);

        return pets;
    }

    @Override
    public Pet findById(Long id) throws NotFoundException {
        return petRepository.findById(id).orElseThrow(() -> new NotFoundException("Pet not found. Pet id: " + id));
    }

    @Override
    public Pet save(Pet pet) {
        return petRepository.save(pet);
    }

    @Override
    public void delete(Pet pet) {
        petRepository.delete(pet);
    }

    @Override
    public void deleteById(Long id) {
        petRepository.deleteById(id);
    }
}
