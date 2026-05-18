package com.appMoviles.GymRat.service;

import com.appMoviles.GymRat.entities.Ejercicio;
import com.appMoviles.GymRat.repository.EjercicioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EjercicioService {

    private final EjercicioRepository ejercicioRepository;

    public EjercicioService(EjercicioRepository ejercicioRepository) {
        this.ejercicioRepository = ejercicioRepository;
    }

    public List<Ejercicio> listarEjercicios() {
        return ejercicioRepository.findAll();
    }

    public Ejercicio obtenerEjercicioPorId(Long id) {
        return ejercicioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ejercicio no encontrado con id: " + id));
    }

    public List<Ejercicio> listarPorCategoria(String categoria) {
        return ejercicioRepository.findByCategoriaIgnoreCase(categoria);
    }

    public Ejercicio crearEjercicio(Ejercicio ejercicio) {
        return ejercicioRepository.save(ejercicio);
    }

    public Ejercicio actualizarEjercicio(Long id, Ejercicio datos) {
        Ejercicio ejercicio = obtenerEjercicioPorId(id);

        ejercicio.setNombre(datos.getNombre());
        ejercicio.setCategoria(datos.getCategoria());
        ejercicio.setDescripcion(datos.getDescripcion());
        ejercicio.setMusculoPrincipal(datos.getMusculoPrincipal());
        ejercicio.setSeriesRecomendadas(datos.getSeriesRecomendadas());
        ejercicio.setRepeticionesRecomendadas(datos.getRepeticionesRecomendadas());
        ejercicio.setNivel(datos.getNivel());

        return ejercicioRepository.save(ejercicio);
    }

    public void eliminarEjercicio(Long id) {
        Ejercicio ejercicio = obtenerEjercicioPorId(id);
        ejercicioRepository.delete(ejercicio);
    }
}