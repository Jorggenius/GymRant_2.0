package com.appmoviles.gymrat20.viewmodel.category

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appmoviles.gymrat20.clases.Ejercicio
import com.appmoviles.gymrat20.repository.EjercicioRepository
import kotlinx.coroutines.launch

open class CategoriaEjerciciosViewModel(
    private val categoria: String
) : ViewModel() {

    private val repository = EjercicioRepository()

    var ejercicios by mutableStateOf<List<Ejercicio>>(emptyList())
        private set

    var loading by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    init {
        cargarEjercicios()
    }

    fun cargarEjercicios() {
        viewModelScope.launch {
            loading = true
            error = null

            try {
                ejercicios = repository.obtenerPorCategoria(categoria)
            } catch (e: Exception) {
                error = e.message ?: "Error al cargar ejercicios"
            } finally {
                loading = false
            }
        }
    }
}