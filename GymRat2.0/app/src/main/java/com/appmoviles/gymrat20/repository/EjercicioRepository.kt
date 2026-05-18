package com.appmoviles.gymrat20.repository

import com.appmoviles.gymrat20.clases.Ejercicio
import com.appmoviles.gymrat20.network.RetrofitClient

class EjercicioRepository {

    suspend fun obtenerPorCategoria(categoria: String): List<Ejercicio> {
        return RetrofitClient.apiService.getEjerciciosPorCategoria(categoria)
    }

    suspend fun obtenerTodos(): List<Ejercicio> {
        return RetrofitClient.apiService.getEjercicios()
    }

    suspend fun obtenerTodosAdmin(): List<Ejercicio> {
        return RetrofitClient.apiService.getAdminEjercicios()
    }

    suspend fun obtenerPorId(id: Long): Ejercicio {
        return RetrofitClient.apiService.getEjercicioPorId(id)
    }

    suspend fun crear(ejercicio: Ejercicio): Ejercicio {
        return RetrofitClient.apiService.crearEjercicio(ejercicio)
    }

    suspend fun actualizar(id: Long, ejercicio: Ejercicio): Ejercicio {
        return RetrofitClient.apiService.actualizarEjercicio(id, ejercicio)
    }

    suspend fun eliminar(id: Long): String {
        return RetrofitClient.apiService.eliminarEjercicio(id)
    }
}