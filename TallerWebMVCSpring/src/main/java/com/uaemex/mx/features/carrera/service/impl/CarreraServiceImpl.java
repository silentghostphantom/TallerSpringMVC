package com.uaemex.mx.features.carrera.service.impl;

import com.uaemex.mx.features.alumno.model.repository.AlumnoRepository;
import com.uaemex.mx.features.carrera.model.dto.CarreraDTO;
import com.uaemex.mx.features.carrera.model.dto.CreateOrUpdateCarreraDTO;
import com.uaemex.mx.features.carrera.model.entity.Carrera;
import com.uaemex.mx.features.carrera.model.repository.CarreraRepository;
import com.uaemex.mx.features.carrera.service.CarreraService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarreraServiceImpl implements CarreraService {
    private final CarreraRepository carreraRepository;
    private final AlumnoRepository alumnoRepository;

    public CarreraServiceImpl(
            CarreraRepository carreraRepository,
            AlumnoRepository alumnoRepository) {
        this.carreraRepository = carreraRepository;
        this.alumnoRepository = alumnoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CarreraDTO> listar() {
        return carreraRepository.findAll().stream()
                .map(this::mapearADTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CarreraDTO buscarPorId(Long id) {
        Carrera carrera = carreraRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Carrera no encontrada con id: " + id));
        return mapearADTO(carrera);
    }

    @Override
    @Transactional
    public CarreraDTO actualizar(Long id, CreateOrUpdateCarreraDTO dto) {
        // Buscar la entidad existente
        Carrera carreraExistente = carreraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carrera no encontrada con ID: " + id));

        // Validar duplicados solo si cambiaron los valores
        if (!carreraExistente.getSiglas().equals(dto.getSiglas())
                && carreraRepository.existsBySiglas(dto.getSiglas())) {
            throw new RuntimeException("Las siglas ya están en uso por otra carrera");
        }
        if (!carreraExistente.getNombre().equals(dto.getNombre())
                && carreraRepository.existsByNombre(dto.getNombre())) {
            throw new RuntimeException("El nombre ya está en uso por otra carrera");
        }

        // Actualizar los campos permitidos
        carreraExistente.setSiglas(dto.getSiglas());
        carreraExistente.setNombre(dto.getNombre());
        carreraExistente.setDescripcion(dto.getDescripcion());
        carreraExistente.setEstatus(dto.getEstatus());
        carreraExistente.setEliminado(dto.getEliminado());

        Carrera actualizada = carreraRepository.save(carreraExistente);
        return mapearADTO(actualizada);
    }

    @Override
    @Transactional
    public CarreraDTO guardar(CreateOrUpdateCarreraDTO dto) {
        // Validar duplicados (Unicidad de siglas y nombre)
        if (carreraRepository.existsBySiglas(dto.getSiglas())) {
            throw new RuntimeException("Ya existe una carrera con las siglas: " + dto.getSiglas());
        }
        if (carreraRepository.existsByNombre(dto.getNombre())) {
            throw new RuntimeException("Ya existe una carrera con el nombre: " + dto.getNombre());
        }

        Carrera nuevaCarrera = mapearAEntity(dto);
        Carrera guardada = carreraRepository.save(nuevaCarrera);
        return mapearADTO(guardada);
    }


    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!carreraRepository.existsById(id)) {
            throw new IllegalArgumentException("Carrera no encontrada con id: " + id);
        }

        if (alumnoRepository.existsByCarreraIdCarrera(id)) {
            throw new IllegalStateException("No se puede eliminar la carrera porque tiene alumnos registrados.");
        }
        carreraRepository.deleteById(id);
    }


    private CarreraDTO mapearADTO(Carrera carrera) {
        CarreraDTO dto = new CarreraDTO();
        dto.setIdCarrera(carrera.getIdCarrera());
        dto.setSiglas(carrera.getSiglas());
        dto.setNombre(carrera.getNombre());
        dto.setDescripcion(carrera.getDescripcion());
        dto.setEstatus(carrera.getEstatus());
        dto.setEliminado(carrera.getEliminado());
        return dto;
    }

    private Carrera mapearAEntity(CreateOrUpdateCarreraDTO dto) {
        Carrera carrera = new Carrera();
        carrera.setSiglas(dto.getSiglas());
        carrera.setNombre(dto.getNombre());
        carrera.setDescripcion(dto.getDescripcion());
        carrera.setEstatus(dto.getEstatus());
        carrera.setEliminado(dto.getEliminado());
        return carrera;
    }
}
