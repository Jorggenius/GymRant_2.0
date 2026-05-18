package com.appmoviles.gymrat20.viewmodel.auth

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.appmoviles.gymrat20.UserStorage
import com.appmoviles.gymrat20.clases.Usuario
import java.util.UUID

sealed class SignUpNav {
    data object Home : SignUpNav()
    data object Login : SignUpNav()
}

class SignUpViewModel(application: Application) : AndroidViewModel(application) {

    private val storage = UserStorage()
    private val context = application.applicationContext

    private val _email = mutableStateOf("")
    val email: State<String> = _email

    private val _password = mutableStateOf("")
    val password: State<String> = _password

    private val _fullName = mutableStateOf("")
    val fullName: State<String> = _fullName

    private val _errorMsg = mutableStateOf<String?>(null)
    val errorMsg: State<String?> = _errorMsg

    private val _navigateTo = mutableStateOf<SignUpNav?>(null)
    val navigateTo: State<SignUpNav?> = _navigateTo

    fun onEmailChange(value: String) {
        _email.value = value
        _errorMsg.value = null
    }

    fun onPasswordChange(value: String) {
        _password.value = value
        _errorMsg.value = null
    }

    fun onFullNameChange(value: String) {
        _fullName.value = value
        _errorMsg.value = null
    }

    fun signUp() {
        val e = _email.value.trim()
        val p = _password.value.trim()
        val n = _fullName.value.trim()

        if (e.isBlank() || p.isBlank() || n.isBlank()) {
            _errorMsg.value = "Completa todos los campos."
            return
        }

        val user = Usuario(
            id = UUID.randomUUID().toString(),
            nombre = n,
            email = e,
            password = p,
            edad = 0,          // pendiente (Settings)
            pesoKg = 0.0,      // pendiente (Settings)
            estaturaCm = 0.0   // pendiente (Settings)
        )

        storage.saveUser(context, user)
        _navigateTo.value = SignUpNav.Home
    }

    fun goToLogin() {
        _navigateTo.value = SignUpNav.Login
    }

    fun consumeNavigation() {
        _navigateTo.value = null
    }
}