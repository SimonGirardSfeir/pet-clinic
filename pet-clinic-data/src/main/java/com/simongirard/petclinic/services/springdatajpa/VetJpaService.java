package com.simongirard.petclinic.services.springdatajpa;

import com.simongirard.petclinic.model.Vet;
import com.simongirard.petclinic.repositories.VetRepository;
import com.simongirard.petclinic.services.VetService;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@Profile({"jpa", "mysql"})
public class VetJpaService implements VetService {

    private final VetRepository vetRepository;

    public VetJpaService(VetRepository vetRepository) {
        this.vetRepository = vetRepository;
    }

    @Override
    public Vet findByLastName(String lastName) {
        log.info("Find vet with last name {}", lastName);

        return vetRepository.findByLastName(lastName);
    }

    @Override
    @Transactional
    @Cacheable(value = "vets", key = "#root.methodName")
    public Set<Vet> findAll() {
        log.info("Find all Vets");

        Set<Vet> vets = new HashSet<>();
        vetRepository.findAll().forEach(vets::add);

        return vets;
    }

    @Override
    public Vet findById(Long id) throws NotFoundException {
        log.info("Find vet with id {}", id);

        return vetRepository.findById(id).orElseThrow(() -> new NotFoundException("Vet not found. Vet id: " + id));
    }

    @Override
    public Vet save(Vet vet) {
        log.info("Vet saved");

        return vetRepository.save(vet);
    }

    @Override
    public void delete(Vet vet) {
        log.info("Delete vet");

        vetRepository.delete(vet);
    }

    @Override
    public void deleteById(Long id) {
        log.info("Delete vet with id {}", id);

        vetRepository.deleteById(id);
    }
}
