package com.appmoviles.gymrat20.clases

data class Rutina(
    val id: String = "",
    val nombre: String,
    val objetivo: String, // ej: "Hipertrofia", "Resistencia", "Funcional"
    val diasPorSemana: Int
)