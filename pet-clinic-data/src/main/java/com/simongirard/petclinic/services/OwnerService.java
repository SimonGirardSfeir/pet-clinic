package com.simongirard.petclinic.services;

import com.simongirard.petclinic.model.Owner;
import javassist.NotFoundException;

import java.util.List;

public interface OwnerService extends CrudService<Owner, Long> {

    Owner findByLastName(String lastName) throws NotFoundException;

    List<Owner> findAllByLastNameLike(String lastName);
}
