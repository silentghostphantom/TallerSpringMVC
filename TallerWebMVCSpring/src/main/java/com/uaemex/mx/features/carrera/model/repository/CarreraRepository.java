package com.uaemex.mx.features.carrera.model.repository;

import com.uaemex.mx.features.carrera.model.entity.Carrera;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarreraRepository extends JpaRepository<Carrera, Long> {
    boolean existsBySiglas(String siglas);
    boolean existsByNombre(String nombre);
}
