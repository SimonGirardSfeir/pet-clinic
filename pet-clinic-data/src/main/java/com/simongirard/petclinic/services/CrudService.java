package com.simongirard.petclinic.services;

import javassist.NotFoundException;

import java.util.Set;

public interface CrudService<T, ID> {

    Set<T> findAll();

    T findById(ID id) throws NotFoundException;

    T save(T object);

    void delete(T object);

    void deleteById(ID id);
}
