package com.simongirard.petclinic.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "speciality")
public class Speciality extends NamedEntity {

    @Builder
    public Speciality(Long id, String name) {
        super(id, name);
    }
}
