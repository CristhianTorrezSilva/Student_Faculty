package com.apirest.cristhiants;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "subject")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String semester;

    // Relación Many-to-Many con Student (mapeado correctamente)
    @ManyToMany(mappedBy = "subjects")
    private Set<Student> students = new HashSet<>();
}
