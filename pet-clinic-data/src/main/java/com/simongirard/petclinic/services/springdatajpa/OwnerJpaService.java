package com.simongirard.petclinic.services.springdatajpa;

import com.simongirard.petclinic.model.Owner;
import com.simongirard.petclinic.repositories.OwnerRepository;
import com.simongirard.petclinic.services.OwnerService;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@Profile("jpa")
public class OwnerJpaService implements OwnerService {


    private final OwnerRepository ownerRepository;

    public OwnerJpaService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public Owner findByLastName(String lastName) throws NotFoundException {
        log.info("Find owner with last name {}", lastName);

        return ownerRepository.findByLastName(lastName).orElseThrow(() -> new NotFoundException("Owner not found. Owner lastName: " + lastName));
    }

    @Override
    public List<Owner> findAllByLastNameLike(String lastName) {
        log.info("Find owner with last name containing {}", lastName);

        return ownerRepository.findAllByLastNameLike(lastName);
    }

    @Override
    public Set<Owner> findAll() {
        log.info("Find all Owners");

        Set<Owner> owners = new HashSet<>();
        ownerRepository.findAll().forEach(owners::add);

        return owners;
    }

    @Override
    public Owner findById(Long id) throws NotFoundException {
        log.info("Find owner with id {}", id);

        return ownerRepository.findById(id).orElseThrow(() -> new NotFoundException("Owner not found. Owner id: " + id));
    }

    @Override
    public Owner save(Owner owner) {
        log.info("Owner saved.");

        return ownerRepository.save(owner);
    }

    @Override
    public void delete(Owner owner) {
        log.info("Delete owner");

        ownerRepository.delete(owner);
    }

    @Override
    public void deleteById(Long id) {
        log.info("Delete owner with id {}", id);

        ownerRepository.deleteById(id);
    }
}
