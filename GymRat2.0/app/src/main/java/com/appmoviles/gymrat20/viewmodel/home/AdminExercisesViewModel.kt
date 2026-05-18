package com.appmoviles.gymrat20.viewmodel.home

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.appmoviles.gymrat20.clases.Ejercicio
import com.appmoviles.gymrat20.repository.EjercicioRepository
import kotlinx.coroutines.launch

class AdminExercisesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = EjercicioRepository()

    private val _ejercicios = mutableStateOf<List<Ejercicio>>(emptyList())
    val ejercicios: State<List<Ejercicio>> = _ejercicios

    private val _categoriaSeleccionada = mutableStateOf<String?>(null)
    val categoriaSeleccionada: State<String?> = _categoriaSeleccionada

    private val _ejercicioSeleccionado = mutableStateOf<Ejercicio?>(null)
    val ejercicioSeleccionado: State<Ejercicio?> = _ejercicioSeleccionado

    private val _loading = mutableStateOf(false)
    val loading: State<Boolean> = _loading

    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> = _error

    private val _mensaje = mutableStateOf<String?>(null)
    val mensaje: State<String?> = _mensaje

    init {
        cargarTodos()
    }

    fun cargarTodos() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                _ejercicios.value = repository.obtenerTodosAdmin()
            } catch (e: Exception) {
                _error.value = "Error al cargar ejercicios: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun filtrarPorCategoria(categoria: String) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            _categoriaSeleccionada.value = categoria
            try {
                _ejercicios.value = repository.obtenerPorCategoria(categoria)
            } catch (e: Exception) {
                _error.value = "Error al filtrar por categoría: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun limpiarFiltro() {
        _categoriaSeleccionada.value = null
        cargarTodos()
    }

    fun seleccionarEjercicio(ejercicio: Ejercicio) {
        _ejercicioSeleccionado.value = ejercicio
    }

    fun limpiarSeleccion() {
        _ejercicioSeleccionado.value = null
    }

    fun crearEjercicio(ejercicio: Ejercicio) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                repository.crear(ejercicio)
                _mensaje.value = "Ejercicio creado correctamente"
                cargarTodos()
            } catch (e: Exception) {
                _error.value = "Error al crear ejercicio: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun actualizarEjercicio(ejercicio: Ejercicio) {
        val id = ejercicio.id ?: return
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                repository.actualizar(id, ejercicio)
                _mensaje.value = "Ejercicio actualizado correctamente"
                cargarTodos()
                limpiarSeleccion()
            } catch (e: Exception) {
                _error.value = "Error al actualizar ejercicio: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun eliminarEjercicio() {
        val id = _ejercicioSeleccionado.value?.id ?: return
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                repository.eliminar(id)
                _mensaje.value = "Ejercicio eliminado correctamente"
                cargarTodos()
                limpiarSeleccion()
            } catch (e: Exception) {
                _error.value = "Error al eliminar ejercicio: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun consumirMensaje() {
        _mensaje.value = null
    }

    fun consumirError() {
        _error.value = null
    }
}