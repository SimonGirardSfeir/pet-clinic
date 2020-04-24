package com.simongirard.petclinic.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "pet")
public class Pet extends NamedEntity {

    @Builder
    public Pet(Long id, String name, PetType petType, Owner owner, LocalDate birthDay, Set<Visit> visits) {
        super(id, name);
        this.petType = petType;
        this.owner = owner;
        this.birthDay = birthDay;
        if (visits == null || visits.size() > 0 ) {
            this.visits = visits;
        }
    }

    @ManyToOne
    @JoinColumn(name = "type_id")
    @NotNull
    private PetType petType;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @Column(name = "birth_day")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    @PastOrPresent
    private LocalDate birthDay;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pet")
    private Set<Visit>  visits = new HashSet<>();
}
