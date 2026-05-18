package com.appmoviles.gymrat20

import android.content.Context
import com.appmoviles.gymrat20.clases.Usuario

class UserStorage {

    private val PREFS_NAME = "gymrat_prefs"
    private val KEY_ID = "id"
    private val KEY_EMAIL = "email"
    private val KEY_PASSWORD = "password"
    private val KEY_FULLNAME = "fullname"
    private val KEY_AGE = "age"
    private val KEY_WEIGHT = "weight_kg"
    private val KEY_HEIGHT = "height_cm"
    private val KEY_CREATED_AT = "created_at"
    private val KEY_IS_LOGGED = "is_logged"

    fun saveUser(context: Context, user: Usuario) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        prefs.edit()
            .putString(KEY_ID, user.id)
            .putString(KEY_FULLNAME, user.nombre)
            .putString(KEY_EMAIL, user.email)
            .putString(KEY_PASSWORD, user.password)
            .putInt(KEY_AGE, user.edad)
            .putFloat(KEY_WEIGHT, user.pesoKg.toFloat())
            .putFloat(KEY_HEIGHT, user.estaturaCm.toFloat())
            .putLong(KEY_CREATED_AT, user.createdAt)
            .putBoolean(KEY_IS_LOGGED, true)
            .apply()
    }

    fun getUser(context: Context): Usuario? {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        val email = prefs.getString(KEY_EMAIL, null) ?: return null
        val password = prefs.getString(KEY_PASSWORD, null) ?: return null
        val nombre = prefs.getString(KEY_FULLNAME, "") ?: ""

        return Usuario(
            id = prefs.getString(KEY_ID, "") ?: "",
            nombre = nombre,
            email = email,
            password = password,
            edad = prefs.getInt(KEY_AGE, 0),
            pesoKg = prefs.getFloat(KEY_WEIGHT, 0f).toDouble(),
            estaturaCm = prefs.getFloat(KEY_HEIGHT, 0f).toDouble(),
            createdAt = prefs.getLong(KEY_CREATED_AT, System.currentTimeMillis())
        )
    }

    fun isLogged(context: Context): Boolean {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean(KEY_IS_LOGGED, false)
    }

    fun logout(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit()
            .putBoolean(KEY_IS_LOGGED, false)
            .apply()
    }

    fun updateProfile(
        context: Context,
        edad: Int,
        pesoKg: Double,
        estaturaCm: Double
    ) {
        val currentUser = getUser(context) ?: return

        val updatedUser = currentUser.copy(
            edad = edad,
            pesoKg = pesoKg,
            estaturaCm = estaturaCm
        )

        saveUser(context, updatedUser)
    }

    fun getFullName(context: Context): String? = getUser(context)?.nombre

    fun getEmail(context: Context): String? = getUser(context)?.email

    fun getPassword(context: Context): String? = getUser(context)?.password
}