package com.uaemex.mx.features.alumno.model.entity;

import com.uaemex.mx.features.carrera.model.entity.Carrera;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Alumno")
public class Alumno implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombreAlumno", length = 100, nullable = false)
    private String nombreAlumno;
    @Column(name = "paternoAlumno", length = 100, nullable = false)
    private String paternoAlumno;
    @Column(name = "maternoAlumno", length = 100)
    private String maternoAlumno;
    @Column(name = "emailAlumno", length = 150, unique = true,  nullable = false)
    private String emailAlumno;
    @Column(name = "estatus", nullable = false)
    private Boolean estatus;
    @Column(name = "eliminado", nullable = false)
    private Boolean eliminado;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idCarrera", nullable = false,
            foreignKey = @ForeignKey(name = "fk_alumno_carrera"))
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Carrera carrera;
}
