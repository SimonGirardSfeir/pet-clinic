package com.simongirard.petclinic.services.springdatajpa;

import com.simongirard.petclinic.model.Speciality;
import com.simongirard.petclinic.repositories.SpecialityRepository;
import com.simongirard.petclinic.services.SpecialityService;
import javassist.NotFoundException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("jpa")
public class SpecialityJpaService implements SpecialityService {

    private final SpecialityRepository specialityRepository;

    public SpecialityJpaService(SpecialityRepository specialityRepository) {
        this.specialityRepository = specialityRepository;
    }

    @Override
    public Set<Speciality> findAll() {

        Set<Speciality> specialities = new HashSet<>();

        specialityRepository.findAll().forEach(specialities::add);

        return specialities;
    }

    @Override
    public Speciality findById(Long id) throws NotFoundException {
        return specialityRepository.findById(id).orElseThrow(() -> new NotFoundException("Speciality not found. Speciality id: " + id));
    }

    @Override
    public Speciality save(Speciality speciality) {
        return specialityRepository.save(speciality);
    }

    @Override
    public void delete(Speciality speciality) {
        specialityRepository.delete(speciality);
    }

    @Override
    public void deleteById(Long id) {
        specialityRepository.deleteById(id);
    }
}
