package com.appMoviles.GymRat.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ejercicios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ejercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String categoria;

    @Column(nullable = false, length = 300)
    private String descripcion;

    @Column(nullable = false, length = 100)
    private String musculoPrincipal;

    @Column(nullable = false)
    private Integer seriesRecomendadas;

    @Column(nullable = false)
    private Integer repeticionesRecomendadas;

    @Column(nullable = false, length = 30)
    private String nivel;
}