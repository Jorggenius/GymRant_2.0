package com.appmoviles.gymrat20.clases

data class Ejercicio(
    val id: Long? = null,
    val nombre: String,
    val categoria: String,
    val descripcion: String,
    val musculoPrincipal: String,
    val seriesRecomendadas: Int,
    val repeticionesRecomendadas: Int,
    val nivel: String
)