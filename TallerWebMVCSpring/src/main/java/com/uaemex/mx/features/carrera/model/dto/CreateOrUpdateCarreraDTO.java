package com.uaemex.mx.features.carrera.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrUpdateCarreraDTO implements Serializable {
    @NotBlank(message = "Las siglas son obligatorias")
    @Size(max = 5, message = "Las siglas no deben superar los 5 caracteres")
    private String siglas;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no debe superar los 100 caracteres")
    private String nombre;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 255, message = "La descripción no debe superar los 255 caracteres")
    private String descripcion;

    @NotNull(message = "El estatus es obligatorio")
    private Boolean estatus;

    @NotNull(message = "El campo eliminado es obligatorio")
    private Boolean eliminado;
}