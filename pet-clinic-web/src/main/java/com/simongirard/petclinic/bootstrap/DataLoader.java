package com.simongirard.petclinic.bootstrap;

import com.simongirard.petclinic.model.Owner;
import com.simongirard.petclinic.model.PetType;
import com.simongirard.petclinic.model.Vet;
import com.simongirard.petclinic.services.OwnerService;
import com.simongirard.petclinic.services.PetTypeService;
import com.simongirard.petclinic.services.VetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner
{
    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;

    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
    }

    @Override
    public void run(String... args) {

        PetType dog = new PetType();
        dog.setName("Dog");
        PetType cat = new PetType();
        cat.setName("Cat");
        PetType lion = new PetType();
        lion.setName("Lion");
        PetType tiger = new PetType();
        tiger.setName("Tiger");

        petTypeService.save(dog);
        petTypeService.save(cat);
        petTypeService.save(lion);
        petTypeService.save(tiger);

        Owner owner1 = new Owner();
        owner1.setFirstName("Simon");
        owner1.setLastName("Girard");

        ownerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setFirstName("Jean");
        owner2.setLastName("Dupont");

        ownerService.save(owner2);

        Vet vet1 = new Vet();
        vet1.setFirstName("Pierre");
        vet1.setLastName("Durand");

        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("Paul");
        vet2.setLastName("Martin");

        vetService.save(vet2);

        System.out.println("All data loaded");
    }
}
