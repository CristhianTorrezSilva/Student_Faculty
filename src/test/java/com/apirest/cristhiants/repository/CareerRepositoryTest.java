package com.apirest.cristhiants.repository;

import com.apirest.cristhiants.entity.Career;
import com.apirest.cristhiants.entity.Faculty;
import com.apirest.cristhiants.entity.Subject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashSet;
import java.util.Optional;

@SpringBootTest
class CareerRepositoryTest {

    @Autowired
    CareerRepository careerRepository;
    @Autowired
    FacultyRepository facultyRepository;

    @Test
    @Transactional
    @Rollback(false)
    void addCareerWithSubjectsToFaculty() {
        Faculty faculty = facultyRepository.findById(2L).orElseThrow();
        Career newCareer = new Career();
        newCareer.setName("Software Engineering");

        Subject thermodynamics = new Subject();
        thermodynamics.setName("Thermodynamics");
        thermodynamics.setSemester(8);

        Subject mechanics = new Subject();
        mechanics.setName("Mechanics");
        mechanics.setSemester(5);

        newCareer.setSubjects(new HashSet<>());

        newCareer.getSubjects().add(thermodynamics);
        newCareer.getSubjects().add(mechanics);

        newCareer.setFaculty(faculty);

        faculty.addCareer(newCareer);

        thermodynamics.setCareer(newCareer);
        mechanics.setCareer(newCareer);

        facultyRepository.save(faculty);
    }
}