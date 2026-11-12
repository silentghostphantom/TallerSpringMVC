package com.uaemex.mx.features.alumno.service;
import com.uaemex.mx.features.alumno.model.dto.AlumnoDTO;
import com.uaemex.mx.features.alumno.model.dto.CreateOrUpdateAlumnoDTO;

import java.util.List;

public interface AlumnoService {
    List<AlumnoDTO> listar();
    AlumnoDTO buscarPorId(Long id);
    AlumnoDTO guardar(CreateOrUpdateAlumnoDTO dto);
    AlumnoDTO actualizar(Long id, CreateOrUpdateAlumnoDTO dto);
    void eliminar(Long id);
}