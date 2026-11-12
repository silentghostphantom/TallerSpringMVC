package com.uaemex.mx.features.alumno.service.impl;

import com.uaemex.mx.features.alumno.model.dto.AlumnoDTO;
import com.uaemex.mx.features.alumno.model.dto.CreateOrUpdateAlumnoDTO;
import com.uaemex.mx.features.alumno.model.entity.Alumno;
import com.uaemex.mx.features.alumno.model.repository.AlumnoRepository;
import com.uaemex.mx.features.alumno.service.AlumnoService;
import com.uaemex.mx.features.carrera.model.entity.Carrera;
import com.uaemex.mx.features.carrera.model.repository.CarreraRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlumnoServiceImpl implements AlumnoService {

    private final AlumnoRepository alumnoRepository;
    private final CarreraRepository carreraRepository;

    public AlumnoServiceImpl(AlumnoRepository alumnoRepository,
                             CarreraRepository carreraRepository) {
        this.alumnoRepository = alumnoRepository;
        this.carreraRepository = carreraRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AlumnoDTO> listar() {
        return alumnoRepository.findAll().stream()
                .map(this::mapearADTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public AlumnoDTO buscarPorId(Long id) {
        Alumno alumno = alumnoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Alumno no encontrado con id: " + id));
        return mapearADTO(alumno);
    }

    @Override
    @Transactional
    public AlumnoDTO guardar(CreateOrUpdateAlumnoDTO dto) {
        if (alumnoRepository.existsByEmailAlumno(dto.getEmailAlumno())) {
            throw new RuntimeException("Ya existe un alumno con el email: " + dto.getEmailAlumno());
        }

        Carrera carrera = carreraRepository.findById(dto.getIdCarrera())
                .orElseThrow(() -> new RuntimeException("Carrera no encontrada con id: " + dto.getIdCarrera()));

        Alumno alumno = new Alumno();
        alumno.setNombreAlumno(dto.getNombreAlumno());
        alumno.setPaternoAlumno(dto.getPaternoAlumno());
        alumno.setMaternoAlumno(dto.getMaternoAlumno());
        alumno.setEmailAlumno(dto.getEmailAlumno());
        alumno.setEstatus(dto.getEstatus());
        alumno.setEliminado(dto.getEliminado());
        alumno.setCarrera(carrera);

        return mapearADTO(alumnoRepository.save(alumno));
    }

    @Override
    @Transactional
    public AlumnoDTO actualizar(Long id, CreateOrUpdateAlumnoDTO dto) {
        Alumno existente = alumnoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado con ID: " + id));

        // Validar email duplicado solo si cambió
        if (!existente.getEmailAlumno().equals(dto.getEmailAlumno())
                && alumnoRepository.existsByEmailAlumno(dto.getEmailAlumno())) {
            throw new RuntimeException("El email ya está en uso por otro alumno");
        }

        Carrera carrera = carreraRepository.findById(dto.getIdCarrera())
                .orElseThrow(() -> new RuntimeException("Carrera no encontrada con id: " + dto.getIdCarrera()));

        existente.setNombreAlumno(dto.getNombreAlumno());
        existente.setPaternoAlumno(dto.getPaternoAlumno());
        existente.setMaternoAlumno(dto.getMaternoAlumno());
        existente.setEmailAlumno(dto.getEmailAlumno());
        existente.setEstatus(dto.getEstatus());
        existente.setEliminado(dto.getEliminado());
        existente.setCarrera(carrera);

        return mapearADTO(alumnoRepository.save(existente));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!alumnoRepository.existsById(id)) {
            throw new IllegalArgumentException("Alumno no encontrado con id: " + id);
        }
        alumnoRepository.deleteById(id);
    }

    private AlumnoDTO mapearADTO(Alumno alumno) {
        AlumnoDTO dto = new AlumnoDTO();
        dto.setId(alumno.getId());
        dto.setNombreAlumno(alumno.getNombreAlumno());
        dto.setPaternoAlumno(alumno.getPaternoAlumno());
        dto.setMaternoAlumno(alumno.getMaternoAlumno());
        dto.setEmailAlumno(alumno.getEmailAlumno());
        dto.setEstatus(alumno.getEstatus());
        dto.setEliminado(alumno.getEliminado());
        if (alumno.getCarrera() != null) {
            dto.setIdCarrera(alumno.getCarrera().getIdCarrera());
            dto.setNombreCarrera(alumno.getCarrera().getNombre());
            dto.setSiglasCarrera(alumno.getCarrera().getSiglas());
        }
        return dto;
    }
}