package com.uaemex.mx.features.alumno.model.dto;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrUpdateAlumnoDTO implements Serializable {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no debe superar los 100 caracteres")
    private String nombreAlumno;

    @NotBlank(message = "El apellido paterno es obligatorio")
    @Size(max = 100, message = "El apellido paterno no debe superar los 100 caracteres")
    private String paternoAlumno;

    @Size(max = 100, message = "El apellido materno no debe superar los 100 caracteres")
    private String maternoAlumno;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no tiene un formato válido")
    @Size(max = 150, message = "El email no debe superar los 150 caracteres")
    private String emailAlumno;

    @NotNull(message = "El estatus es obligatorio")
    private Boolean estatus;

    @NotNull(message = "El campo eliminado es obligatorio")
    private Boolean eliminado;

    @NotNull(message = "Debe seleccionar una carrera")
    private Long idCarrera;
}