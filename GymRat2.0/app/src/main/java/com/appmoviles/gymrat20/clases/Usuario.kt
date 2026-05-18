package com.appmoviles.gymrat20.clases

data class Usuario (
    val id: String = "",
    val nombre: String,
    val email: String,
    val password: String,
    val edad: Int,
    val pesoKg: Double,
    val estaturaCm: Double,
    val createdAt: Long = System.currentTimeMillis()
)
