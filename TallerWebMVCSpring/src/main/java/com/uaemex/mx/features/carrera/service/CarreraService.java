package com.uaemex.mx.features.carrera.service;

import com.uaemex.mx.features.carrera.model.dto.CarreraDTO;
import com.uaemex.mx.features.carrera.model.dto.CreateOrUpdateCarreraDTO;

import java.util.List;

public interface CarreraService {
    List<CarreraDTO> listar();
    CarreraDTO buscarPorId(Long id);
    CarreraDTO guardar(CreateOrUpdateCarreraDTO dto);
    CarreraDTO actualizar(Long id, CreateOrUpdateCarreraDTO dto);
    void eliminar(Long id);
}
