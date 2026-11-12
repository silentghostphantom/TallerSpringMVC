package com.uaemex.mx.features.carrera.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Carrera")
public class Carrera implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCarrera;
    @Column(name = "siglas", length = 5, unique = true, nullable = false)
    private String siglas;
    @Column(name = "nombre", length = 100, unique = true, nullable = false)
    private String nombre;
    @Column(name = "descripcion", length = 255, nullable = false)
    private String descripcion;
    @Column(name = "estatus", nullable = false)
    private Boolean estatus;
    @Column(name = "eliminado", nullable = false)
    private Boolean eliminado;
}
