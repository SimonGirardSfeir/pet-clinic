package com.simongirard.petclinic.services.springdatajpa;

import com.simongirard.petclinic.model.PetType;
import com.simongirard.petclinic.repositories.PetTypeRepository;
import com.simongirard.petclinic.services.PetTypeService;
import javassist.NotFoundException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("jpa")
public class PetTypeJpaService implements PetTypeService {


    private final PetTypeRepository petTypeRepository;

    public PetTypeJpaService(PetTypeRepository petTypeRepository) {
        this.petTypeRepository = petTypeRepository;
    }

    @Override
    public Set<PetType> findAll() {
        Set<PetType> petTypes = new HashSet<>();

        petTypeRepository.findAll().forEach(petTypes::add);

        return petTypes;
    }

    @Override
    public PetType findById(Long id) throws NotFoundException {
        return petTypeRepository.findById(id).orElseThrow(() -> new NotFoundException("Pet Type not found. Pet Type id: " + id));
    }

    @Override
    public PetType save(PetType petType) {
        return petTypeRepository.save(petType);
    }

    @Override
    public void delete(PetType petType) {
        petTypeRepository.delete(petType);
    }

    @Override
    public void deleteById(Long id) {
        petTypeRepository.deleteById(id);
    }
}
