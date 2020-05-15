package com.simongirard.petclinic.services.springdatajpa;

import com.simongirard.petclinic.model.PetType;
import com.simongirard.petclinic.repositories.PetTypeRepository;
import com.simongirard.petclinic.services.PetTypeService;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@Profile({"jpa", "mysql"})
public class PetTypeJpaService implements PetTypeService {


    private final PetTypeRepository petTypeRepository;

    public PetTypeJpaService(PetTypeRepository petTypeRepository) {
        this.petTypeRepository = petTypeRepository;
    }

    @Override
    public Set<PetType> findAll() {
        log.info("Find all pet types");

        Set<PetType> petTypes = new HashSet<>();
        petTypeRepository.findAll().forEach(petTypes::add);

        return petTypes;
    }

    @Override
    public PetType findById(Long id) throws NotFoundException {
        log.info("Find pet type with id {}", id);

        return petTypeRepository.findById(id).orElseThrow(() -> new NotFoundException("Pet Type not found. Pet Type id: " + id));
    }

    @Override
    public PetType save(PetType petType) {
        log.info("Pet type saved");

        return petTypeRepository.save(petType);
    }

    @Override
    public void delete(PetType petType) {
        log.info("Delete pet type");

        petTypeRepository.delete(petType);
    }

    @Override
    public void deleteById(Long id) {
        log.info("Delete pet type with id {}", id);

        petTypeRepository.deleteById(id);
    }
}
