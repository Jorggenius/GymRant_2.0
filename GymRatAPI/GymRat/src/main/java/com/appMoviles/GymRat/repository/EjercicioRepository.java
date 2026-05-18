package com.appMoviles.GymRat.repository;

import com.appMoviles.GymRat.entities.Ejercicio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EjercicioRepository extends JpaRepository<Ejercicio, Long> {

    List<Ejercicio> findByCategoriaIgnoreCase(String categoria);
}