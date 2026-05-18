package com.appMoviles.GymRat.controller;

import com.appMoviles.GymRat.entities.Ejercicio;
import com.appMoviles.GymRat.service.EjercicioService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/ejercicios")
public class AdminEjercicioController {

    private final EjercicioService ejercicioService;

    public AdminEjercicioController(EjercicioService ejercicioService) {
        this.ejercicioService = ejercicioService;
    }

    @GetMapping
    public List<Ejercicio> listarEjercicios() {
        return ejercicioService.listarEjercicios();
    }

    @PostMapping
    public Ejercicio crearEjercicio(@RequestBody @Valid Ejercicio ejercicio) {
        return ejercicioService.crearEjercicio(ejercicio);
    }

    @PutMapping("/{id}")
    public Ejercicio actualizarEjercicio(@PathVariable Long id, @RequestBody @Valid Ejercicio ejercicio) {
        return ejercicioService.actualizarEjercicio(id, ejercicio);
    }

    @DeleteMapping("/{id}")
    public String eliminarEjercicio(@PathVariable Long id) {
        ejercicioService.eliminarEjercicio(id);
        return "Ejercicio eliminado correctamente.";
    }
}