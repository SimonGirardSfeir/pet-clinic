package com.simongirard.petclinic.services;

import com.simongirard.petclinic.model.Vet;

public interface VetService extends CrudService<Vet, Long> {

    Vet findByLastName(String lastName);
}
