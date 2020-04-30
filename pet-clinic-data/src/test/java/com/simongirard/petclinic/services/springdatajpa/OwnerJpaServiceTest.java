package com.simongirard.petclinic.services.springdatajpa;

import com.simongirard.petclinic.model.Owner;
import com.simongirard.petclinic.repositories.OwnerRepository;
import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OwnerJpaServiceTest {

    @Mock
    private OwnerRepository ownerRepository;

    @InjectMocks
    private OwnerJpaService ownerJpaService;

    private Long ownerId = 1L;

    private String lastName = "Girard";

    private Owner owner;

    private Set<Owner> owners;

    @BeforeEach
    void setUp() {
        owner = Owner.builder().id(ownerId).lastName(lastName).build();
        owners = new HashSet<>();
        owners.add(Owner.builder().id(1L).build());
        owners.add(Owner.builder().id(2L).build());
    }

    @Test
    void findByLastName() throws NotFoundException {
        when(ownerRepository.findByLastName(any())).thenReturn(Optional.of(owner));

        Owner foundOwner = ownerJpaService.findByLastName(lastName);

        assertEquals(ownerId, foundOwner.getId());
        assertEquals(lastName, foundOwner.getLastName());

        verify(ownerRepository).findByLastName(eq(lastName));
    }

    @Test
    void findByLastNameNotFound() {
        when(ownerRepository.findByLastName(any())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> ownerJpaService.findByLastName(lastName));
    }

    @Test
    void findByLastNameLike() {
        when(ownerRepository.findAllByLastNameLike(any())).thenReturn(Collections.singletonList(owner));

        List<Owner> foundOwners = ownerJpaService.findAllByLastNameLike(lastName);

        assertEquals(Collections.singletonList(owner), foundOwners);

        verify(ownerRepository).findAllByLastNameLike(eq(lastName));
    }

    @Test
    void findAll() {
        when(ownerRepository.findAll()).thenReturn(owners);

        Set<Owner> ownersReturn = ownerJpaService.findAll();

        assertEquals(2, ownersReturn.size());

        verify(ownerRepository).findAll();
    }

    @Test
    void findById() throws NotFoundException {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(owner));

        Owner ownerFound = ownerJpaService.findById(ownerId);

        assertNotNull(ownerFound);
        assertEquals(lastName, ownerFound.getLastName());
        assertEquals(ownerId, ownerFound.getId());

        verify(ownerRepository).findById(eq(ownerId));
    }

    @Test
    void findByIdNotFound() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> ownerJpaService.findById(ownerId));
    }

    @Test
    void save() {
        Owner ownerToSave = Owner.builder().id(1L).lastName(lastName).build();

        when(ownerRepository.save(any())).thenReturn(owner);

        Owner savedOwner = ownerJpaService.save(ownerToSave);

        assertNotNull(savedOwner);
        assertEquals(owner, savedOwner);

        verify(ownerRepository).save(ownerToSave);
    }

    @Test
    void delete() {
        ownerJpaService.delete(owner);

        verify(ownerRepository).delete(owner);
    }

    @Test
    void deleteById() {
        ownerJpaService.deleteById(ownerId);

        verify(ownerRepository).deleteById(ownerId);
    }
}