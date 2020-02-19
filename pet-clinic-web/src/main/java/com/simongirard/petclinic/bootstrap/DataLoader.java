package com.simongirard.petclinic.bootstrap;

import com.simongirard.petclinic.model.Owner;
import com.simongirard.petclinic.model.Vet;
import com.simongirard.petclinic.services.OwnerService;
import com.simongirard.petclinic.services.VetService;
import com.simongirard.petclinic.services.map.OwnerMapService;
import com.simongirard.petclinic.services.map.VetMapService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner
{
    private final OwnerService ownerService;
    private final VetService vetService;

    public DataLoader() {
        this.ownerService = new OwnerMapService();
        this.vetService = new VetMapService();
    }

    @Override
    public void run(String... args) {

        Owner owner1 = new Owner();
        owner1.setId(1L);
        owner1.setFirstName("Simon");
        owner1.setLastName("Girard");

        ownerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setId(2L);
        owner2.setFirstName("Jean");
        owner2.setLastName("Dupont");

        ownerService.save(owner2);

        Vet vet1 = new Vet();
        vet1.setId(1L);
        vet1.setFirstName("Pierre");
        vet1.setLastName("Durand");

        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setId(2L);
        vet2.setFirstName("Paul");
        vet2.setLastName("Martin");

        vetService.save(vet2);

        System.out.println("All data loaded");
    }
}
