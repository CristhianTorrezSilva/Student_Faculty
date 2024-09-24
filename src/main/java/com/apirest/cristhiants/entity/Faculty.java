package com.apirest.cristhiants.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "faculties")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = "careers")
public class Faculty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "faculty", orphanRemoval = true,fetch = FetchType.EAGER)
    private Set<Career> careers = new HashSet<>();

    public void addCareer(Career career) {
        this.careers.add(career);
        career.setFaculty(this);
    }

    public void removeCareer(Career career) {
        this.careers.remove(career);
        career.setFaculty(null);
    }
}
