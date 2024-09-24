package com.apirest.cristhiants.repository;

import com.apirest.cristhiants.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
