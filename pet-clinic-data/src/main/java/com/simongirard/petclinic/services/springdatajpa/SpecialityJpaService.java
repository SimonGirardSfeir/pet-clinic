package com.simongirard.petclinic.services.springdatajpa;

import com.simongirard.petclinic.model.Speciality;
import com.simongirard.petclinic.repositories.SpecialityRepository;
import com.simongirard.petclinic.services.SpecialityService;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@Profile("jpa")
public class SpecialityJpaService implements SpecialityService {

    private final SpecialityRepository specialityRepository;

    public SpecialityJpaService(SpecialityRepository specialityRepository) {
        this.specialityRepository = specialityRepository;
    }

    @Override
    public Set<Speciality> findAll() {
        log.info("Find all Specialities");

        Set<Speciality> specialities = new HashSet<>();
        specialityRepository.findAll().forEach(specialities::add);

        return specialities;
    }

    @Override
    public Speciality findById(Long id) throws NotFoundException {
        log.info("Find speciality with id {}", id);

        return specialityRepository.findById(id).orElseThrow(() -> new NotFoundException("Speciality not found. Speciality id: " + id));
    }

    @Override
    public Speciality save(Speciality speciality) {
        log.info("Speciality saved");

        return specialityRepository.save(speciality);
    }

    @Override
    public void delete(Speciality speciality) {
        log.info("Delete speciality");

        specialityRepository.delete(speciality);
    }

    @Override
    public void deleteById(Long id) {
        log.info("Delete speciality with id {}", id);

        specialityRepository.deleteById(id);
    }
}
