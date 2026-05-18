package com.appmoviles.gymrat20.clases

data class Dieta(
    val id: String = "",
    val nombre: String,
    val caloriasDiarias: Int,
    val objetivo: String // ej: "Bajar grasa", "Subir masa", "Mantener"
)