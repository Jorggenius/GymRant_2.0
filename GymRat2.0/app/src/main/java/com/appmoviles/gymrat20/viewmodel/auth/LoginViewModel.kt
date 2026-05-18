package com.appmoviles.gymrat20.viewmodel.auth

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.appmoviles.gymrat20.UserStorage

sealed class LoginNav {
    data object Home : LoginNav()
    data object Admin : LoginNav()
    data object SignUp : LoginNav()
}

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val storage = UserStorage()
    private val context = application.applicationContext

    private val _email = mutableStateOf("")
    val email: State<String> = _email

    private val _password = mutableStateOf("")
    val password: State<String> = _password

    private val _errorMsg = mutableStateOf<String?>(null)
    val errorMsg: State<String?> = _errorMsg

    private val _navigateTo = mutableStateOf<LoginNav?>(null)
    val navigateTo: State<LoginNav?> = _navigateTo

    fun onEmailChange(value: String) {
        _email.value = value
        _errorMsg.value = null
    }

    fun onPasswordChange(value: String) {
        _password.value = value
        _errorMsg.value = null
    }

    fun login() {
        val e = _email.value.trim()
        val p = _password.value.trim()

        if (e.isBlank() || p.isBlank()) {
            _errorMsg.value = "Completa email y password."
            return
        }

        // ✅ Admin hardcodeado
        if (e.equals("admin", ignoreCase = true) && p == "admin") {
            _navigateTo.value = LoginNav.Admin
            return
        }

        // ✅ Usuario desde tu clase Usuario (guardado en UserStorage)
        val user = storage.getUser(context)

        if (user != null && e == user.email && p == user.password) {
            _navigateTo.value = LoginNav.Home
        } else {
            _errorMsg.value = "Email o contraseña incorrectos."
        }
    }

    fun forgotPassword() {
        _errorMsg.value = "Aún no implementado: Forgot password"
    }

    fun goToSignUp() {
        _navigateTo.value = LoginNav.SignUp
    }

    fun consumeNavigation() {
        _navigateTo.value = null
    }
}