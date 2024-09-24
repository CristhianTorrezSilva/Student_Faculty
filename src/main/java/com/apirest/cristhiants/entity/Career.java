package com.apirest.cristhiants.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.engine.internal.Cascade;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "careers")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"faculty", "subjects"})

public class Career {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;

    @OneToMany(mappedBy = "career", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Subject> subjects = new HashSet<>();

    public void addSubject(Subject subject) {
        this.subjects.add(subject);
        subject.setCareer(this);
    }

    public void removeSubject(Subject subject) {
        this.subjects.remove(subject);
        subject.setCareer(null);
    }
}
