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
@Table(name = "type")
public class PetType extends NamedEntity {

    @Builder
    public PetType(Long id, String name) {
        super(id, name);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
