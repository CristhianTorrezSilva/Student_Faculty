package com.apirest.cristhiants.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "subjects")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int semester;

    @ManyToOne
    @JoinColumn(name = "career_id")
    private Career career;

    @ManyToMany(mappedBy = "subjects")
    private Set<Student> students = new HashSet<>();

    public void addSubject(Student student) {
        this.students.add(student);
        student.getSubjects().add(this);
    }

    public void removeSubject(Student student) {
        this.students.remove(student);
        student.getSubjects().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return semester == subject.semester
                && Objects.equals(id, subject.id)
                && Objects.equals(name, subject.name);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, name, semester);
    }
}
