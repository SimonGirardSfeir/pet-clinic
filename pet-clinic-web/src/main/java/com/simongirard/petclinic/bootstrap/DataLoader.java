package com.simongirard.petclinic.bootstrap;

import com.simongirard.petclinic.model.Owner;
import com.simongirard.petclinic.model.Pet;
import com.simongirard.petclinic.model.PetType;
import com.simongirard.petclinic.model.Speciality;
import com.simongirard.petclinic.model.Vet;
import com.simongirard.petclinic.model.Visit;
import com.simongirard.petclinic.services.OwnerService;
import com.simongirard.petclinic.services.PetTypeService;
import com.simongirard.petclinic.services.SpecialityService;
import com.simongirard.petclinic.services.VetService;
import com.simongirard.petclinic.services.VisitService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner
{
    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final SpecialityService specialityService;
    private final VisitService visitService;

    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService, SpecialityService specialityService, VisitService visitService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.specialityService = specialityService;
        this.visitService = visitService;
    }

    @Override
    public void run(String... args) {

        int count = petTypeService.findAll().size();

        if(count == 0) {
            loadData();
        }
    }

    private void loadData() {
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

        Speciality radiology = new Speciality();
        radiology.setName("Radiology");
        Speciality surgery = new Speciality();
        surgery.setName("Surgery");
        Speciality dentistry = new Speciality();
        dentistry.setName("Dentistry");

        Speciality savedRadiology = specialityService.save(radiology);
        Speciality savedSurgery = specialityService.save(surgery);
        Speciality savedDentistry = specialityService.save(dentistry);

        Owner owner1 = new Owner();
        owner1.setFirstName("Simon");
        owner1.setLastName("Girard");
        owner1.setAddress("120 rue de Belleville");
        owner1.setTelephone("0123456789");
        owner1.setCity("Paris");

        Pet pet1 = new Pet();
        pet1.setName("Simba");
        pet1.setPetType(lion);
        pet1.setOwner(owner1);
        pet1.setBirthDay(LocalDate.now());
        owner1.getPets().add(pet1);

        ownerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setFirstName("Tin");
        owner2.setLastName("Tin");
        owner2.setAddress("26 rue du Labrador");
        owner2.setCity("Bruxelles");
        owner2.setTelephone("0987654321");

        Pet pet2 = new Pet();
        pet2.setName("Milou");
        pet2.setPetType(dog);
        pet2.setOwner(owner2);
        pet2.setBirthDay(LocalDate.now());
        owner2.getPets().add(pet2);

        ownerService.save(owner2);


        Visit visit1 = new Visit();
        visit1.setPet(pet1);
        visit1.setDate(LocalDate.now());
        visit1.setDescription("The lion is too dangerous");

        visitService.save(visit1);

        Vet vet1 = new Vet();
        vet1.setFirstName("Pierre");
        vet1.setLastName("Durand");
        vet1.getSpecialities().add(savedRadiology);
        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("Paul");
        vet2.setLastName("Martin");
        vet2.getSpecialities().add(savedDentistry);
        vet2.getSpecialities().add(savedSurgery);

        vetService.save(vet2);
    }
}
