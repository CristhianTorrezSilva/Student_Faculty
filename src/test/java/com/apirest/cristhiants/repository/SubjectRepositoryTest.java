package com.apirest.cristhiants.repository;

import com.apirest.cristhiants.entity.Career;
import com.apirest.cristhiants.entity.Subject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.HashSet;

@SpringBootTest
public class SubjectRepositoryTest {
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    CareerRepository careerRepository;

    @Test
    @Transactional
    @Rollback(false)
    void addSubjectToCareer() {
        Career career = careerRepository.findById(2L).orElseThrow();

        Subject subject1 = new Subject();
        subject1.setName("Subject 1");
        subject1.setSemester(1);

        Subject subject2 = new Subject();
        subject2.setName("Subject 2");
        subject2.setSemester(10);

        if (career.getSubjects() == null) {
            career.setSubjects(new HashSet<>());
        }

        career.getSubjects().add(subject1);
        career.getSubjects().add(subject2);

        subject1.setCareer(career);
        subject2.setCareer(career);

        careerRepository.save(career);
    }
}
