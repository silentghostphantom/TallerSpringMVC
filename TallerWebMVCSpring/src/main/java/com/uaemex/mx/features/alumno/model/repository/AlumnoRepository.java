package com.uaemex.mx.features.alumno.model.repository;

import com.uaemex.mx.features.alumno.model.entity.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
    boolean existsByEmailAlumno(String emailAlumno);
    Optional<Alumno> findByEmailAlumno(String emailAlumno);
    boolean existsByCarreraIdCarrera(Long idCarrera);

}
