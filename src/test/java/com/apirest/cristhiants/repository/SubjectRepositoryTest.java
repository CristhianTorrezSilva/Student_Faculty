package com.apirest.cristhiants.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

public class SubjectRepositoryTest {
    @Autowired SubjectRepository subjectRepository;
    
    @Test
    @Transactional
    @Rollback(false)
    void addSubjectToCareer() {
        
    }
}
