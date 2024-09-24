package com.apirest.cristhiants.repository;

import com.apirest.cristhiants.entity.Student;
import com.apirest.cristhiants.entity.Subject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private SubjectRepository subjectRepository;

    @Test
    @Transactional
    @Rollback(false)
    void createStudentWithExistingSubject() {
        Optional<Subject> subjectOptional = subjectRepository.findById(3L);
        assertThat(subjectOptional).isPresent();
        Subject existingSubject = subjectOptional.get();
        
        Student newStudent = new Student();
        newStudent.setName("Cristhian");
        newStudent.setSubjects(new HashSet<>());
        newStudent.getSubjects().add(existingSubject);
        studentRepository.save(newStudent);
        Student savedStudent = studentRepository.findById(newStudent.getId()).get();
        assertThat(savedStudent.getSubjects()).contains(existingSubject);

        // Print the student with their associated subjects
        System.out.println("Student: " + savedStudent.getName());
        for (Subject subject : savedStudent.getSubjects()) {
            System.out.println("\tSubject: " + subject.getName());
        }
    }
    
    @Test
    @Transactional
    @Rollback(false)
    void getAllStudentsWithSubjects() {
        Iterable<Student> students = studentRepository.findAll();
        for (Student student : students) {
            System.out.println("Student: " + student.getName());
            Set<Subject> subjects = student.getSubjects();
            for (Subject subject : subjects) {
                System.out.println("\tSubject: " + subject.getName());
            }
        }
    }
    
    @Test
    @Transactional
    @Rollback(false)
    void updateStudent() {
        Optional<Student> studentOptional = studentRepository.findById(1L);
        assertThat(studentOptional).isPresent();
        Student student = studentOptional.get();
        student.setName("Cris");
        studentRepository.save(student);
    }
    
    @Test
    @Transactional
    @Rollback(false)
    void deleteStudent() {
        studentRepository.deleteById(1L);
    }
}
