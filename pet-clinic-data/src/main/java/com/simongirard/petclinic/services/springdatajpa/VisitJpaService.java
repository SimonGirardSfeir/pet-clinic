package com.simongirard.petclinic.services.springdatajpa;

import com.simongirard.petclinic.model.Visit;
import com.simongirard.petclinic.repositories.VisitRepository;
import com.simongirard.petclinic.services.VisitService;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@Profile("jpa")
public class VisitJpaService implements VisitService {

    private final VisitRepository visitRepository;

    public VisitJpaService(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    @Override
    public Set<Visit> findAll() {
        log.info("Find all Visits");

        Set<Visit> visits = new HashSet<>();
        visitRepository.findAll().forEach(visits::add);

        return visits;
    }

    @Override
    public Visit findById(Long id) throws NotFoundException {
        log.info("Find visit with id {}", id);

        return visitRepository.findById(id).orElseThrow(() -> new NotFoundException("Visit not found. Visit id: " + id));
    }

    @Override
    public Visit save(Visit visit) {
        log.info("Visit saved");

        return visitRepository.save(visit);
    }

    @Override
    public void delete(Visit visit) {
        log.info("Delete visit");

        visitRepository.delete(visit);
    }

    @Override
    public void deleteById(Long id) {
        log.info("Delete visit with id {}", id);

        visitRepository.deleteById(id);
    }
}
