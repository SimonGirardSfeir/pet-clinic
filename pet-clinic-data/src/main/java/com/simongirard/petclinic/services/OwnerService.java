package com.simongirard.petclinic.services;

import com.simongirard.petclinic.model.Owner;

public interface OwnerService extends CrudService<Owner, Long> {

    Owner findByLastName(String lastName);

}
