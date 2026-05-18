package com.appmoviles.gymrat20.network

import com.appmoviles.gymrat20.clases.Ejercicio
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    // Públicas
    @GET("api/ejercicios")
    suspend fun getEjercicios(): List<Ejercicio>

    @GET("api/ejercicios/{id}")
    suspend fun getEjercicioPorId(
        @Path("id") id: Long
    ): Ejercicio

    @GET("api/ejercicios/categoria/{categoria}")
    suspend fun getEjerciciosPorCategoria(
        @Path("categoria") categoria: String
    ): List<Ejercicio>

    // Admin CRUD
    @GET("api/admin/ejercicios")
    suspend fun getAdminEjercicios(): List<Ejercicio>

    @POST("api/admin/ejercicios")
    suspend fun crearEjercicio(
        @Body ejercicio: Ejercicio
    ): Ejercicio

    @PUT("api/admin/ejercicios/{id}")
    suspend fun actualizarEjercicio(
        @Path("id") id: Long,
        @Body ejercicio: Ejercicio
    ): Ejercicio

    @DELETE("api/admin/ejercicios/{id}")
    suspend fun eliminarEjercicio(
        @Path("id") id: Long
    ): String
}