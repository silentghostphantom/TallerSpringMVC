package com.uaemex.mx.features.alumno.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlumnoDTO implements Serializable {
    private Long id;
    private String nombreAlumno;
    private String paternoAlumno;
    private String maternoAlumno;
    private String emailAlumno;
    private Boolean estatus;
    private Boolean eliminado;

    // Datos de la carrera
    private Long idCarrera;
    private String siglasCarrera;
    private String nombreCarrera;
}
