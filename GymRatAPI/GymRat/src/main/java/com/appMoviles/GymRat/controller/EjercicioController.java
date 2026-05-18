package com.appMoviles.GymRat.controller;

import com.appMoviles.GymRat.entities.Ejercicio;
import com.appMoviles.GymRat.service.EjercicioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ejercicios")
public class EjercicioController {

    private final EjercicioService ejercicioService;

    public EjercicioController(EjercicioService ejercicioService) {
        this.ejercicioService = ejercicioService;
    }

    @GetMapping
    public List<Ejercicio> listarEjercicios() {
        return ejercicioService.listarEjercicios();
    }

    @GetMapping("/{id}")
    public Ejercicio obtenerPorId(@PathVariable Long id) {
        return ejercicioService.obtenerEjercicioPorId(id);
    }

    @GetMapping("/categoria/{categoria}")
    public List<Ejercicio> listarPorCategoria(@PathVariable String categoria) {
        return ejercicioService.listarPorCategoria(categoria);
    }
}