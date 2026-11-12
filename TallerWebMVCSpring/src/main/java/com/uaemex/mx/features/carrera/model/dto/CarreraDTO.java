package com.uaemex.mx.features.carrera.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarreraDTO implements Serializable {
    private Long idCarrera;
    private String siglas;
    private String nombre;
    private String descripcion;
    private Boolean estatus;
    private Boolean eliminado;
}