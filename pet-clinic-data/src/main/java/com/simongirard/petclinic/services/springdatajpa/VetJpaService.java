package com.simongirard.petclinic.services.springdatajpa;

import com.simongirard.petclinic.model.Vet;
import com.simongirard.petclinic.repositories.VetRepository;
import com.simongirard.petclinic.services.VetService;
import javassist.NotFoundException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
@Profile("jpa")
public class VetJpaService implements VetService {

    private final VetRepository vetRepository;

    public VetJpaService(VetRepository vetRepository) {
        this.vetRepository = vetRepository;
    }

    @Override
    public Vet findByLastName(String lastName) {
        return vetRepository.findByLastName(lastName);
    }

    @Override
    @Transactional
    @Cacheable(value = "vets", key = "#root.methodName")
    public Set<Vet> findAll() {
        Set<Vet> vets = new HashSet<>();

        vetRepository.findAll().forEach(vets::add);

        return vets;
    }

    @Override
    public Vet findById(Long id) throws NotFoundException {
        return vetRepository.findById(id).orElseThrow(() -> new NotFoundException("Vet not found. Vet id: " + id));
    }

    @Override
    public Vet save(Vet vet) {
        return vetRepository.save(vet);
    }

    @Override
    public void delete(Vet vet) {
        vetRepository.delete(vet);
    }

    @Override
    public void deleteById(Long id) {
        vetRepository.deleteById(id);
    }
}
