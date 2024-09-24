package com.apirest.cristhiants.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.apirest.cristhiants.entity.Career;
import com.apirest.cristhiants.entity.Subject;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.apirest.cristhiants.entity.Faculty;
import org.springframework.test.annotation.Rollback;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class FacultyRepositoryTest {

    @Autowired
    private FacultyRepository facultyRepository;

    @Test
    @Transactional
    @Rollback(false)
    void createFaculty() {
        // create a new faculty
        Faculty engineering = new Faculty();
        engineering.setName("Engineering");

        // create the faculty careers
        Career systemsEngineering = new Career();
        systemsEngineering.setName("Systems Engineering");

        // add subjects to the careers
        Subject programming = new Subject();
        programming.setName("Programming");
        programming.setSemester(1);

        Subject calculusSystems = new Subject();
        calculusSystems.setName("Calculus for Systems");
        calculusSystems.setSemester(2);

        // initialize the sets (mutable collections)
        systemsEngineering.setSubjects(new HashSet<>());

        // add subjects to careers
        systemsEngineering.getSubjects().add(programming);
        systemsEngineering.getSubjects().add(calculusSystems);

        // set the faculty to the careers
        systemsEngineering.setFaculty(engineering);

        // initialize careers set for faculty
        engineering.setCareers(new HashSet<>());

        // add careers to the faculty
        engineering.getCareers().add(systemsEngineering);

        // set the career for each subject (bidirectional consistency)
        programming.setCareer(systemsEngineering);
        calculusSystems.setCareer(systemsEngineering);

        // save the faculty
        facultyRepository.save(engineering);

        // assert that the faculty's id is not null
        assertThat(engineering.getId()).isNotNull();
    }

    @Test
    @Transactional
    @Rollback(false)
    void readAllFacultiesWithCareersAndSubjects() {
        List<Faculty> faculties = facultyRepository.findAll();

        for (Faculty faculty : faculties) {
            System.out.println("Faculty: " + faculty.getName());

            for (Career career : faculty.getCareers()) {
                System.out.println("\tCareer: " + career.getName());

                for (Subject subject : career.getSubjects()) {
                    System.out.println("\t\tSubject: " + subject.getName() + " - Semester: " + subject.getSemester());
                }
            }
        }
    }
    
    @Test
    @Transactional
    @Rollback(false)
    void deleteAllFaculties() {
        assertThat(facultyRepository.findAll()).isNotEmpty();
        facultyRepository.deleteAll();
        assertThat(facultyRepository.findAll()).isEmpty();
        System.out.println("Faculties deleted");
    }
    
    @Test
    @Transactional
    @Rollback(false)
    void updateFaculty() {
        // Fetch an existing faculty (ensure that a faculty with {id} exists
        Optional<Faculty> facultyOptional = facultyRepository.findById(2L);

        // Ensure that the faculty exists
        assertThat(facultyOptional).isPresent();

        Faculty faculty = facultyOptional.get();

        // Update faculty name
        faculty.setName("Updated Engineering");

        // Update the first career (e.g., change its name)
        Career systemsEngineering = faculty.getCareers().stream()
                .filter(career -> career.getName().equals("Systems Engineering"))
                .findFirst()
                .orElse(null);

        assertThat(systemsEngineering).isNotNull(); // Ensure Systems Engineering exists
        systemsEngineering.setName("Updated Systems Engineering");

        // Update the first subject of the systems engineering career
        Subject programming = systemsEngineering.getSubjects().stream()
                .filter(subject -> subject.getName().equals("Programming"))
                .findFirst()
                .orElse(null);

        assertThat(programming).isNotNull(); // Ensure the Programming subject exists
        programming.setName("Advanced Programming");
        programming.setSemester(2); // Update semester

        // Save the updated faculty
        facultyRepository.save(faculty);

        // Assert the updates (e.g., re-fetch and check if the updates persist)
        Faculty updatedFaculty = facultyRepository.findById(faculty.getId()).get();

        assertThat(updatedFaculty.getName()).isEqualTo("Updated Engineering");
        Career updatedCareer = updatedFaculty.getCareers().stream()
                .filter(career -> career.getName().equals("Updated Systems Engineering"))
                .findFirst()
                .orElse(null);
        assertThat(updatedCareer).isNotNull();

        Subject updatedSubject = updatedCareer.getSubjects().stream()
                .filter(subject -> subject.getName().equals("Advanced Programming"))
                .findFirst()
                .orElse(null);
        assertThat(updatedSubject).isNotNull();
        assertThat(updatedSubject.getSemester()).isEqualTo(2);

        // Print confirmation
        System.out.println("Updated faculty: " + updatedFaculty);
    }
}