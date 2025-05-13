package com.example.safepass.storage

object AuthManager {
    private var registeredUser: String? = null
    private var registeredPass: String? = null

    fun register(username: String, password: String) {
        registeredUser = username
        registeredPass = password
    }

    fun login(username: String, password: String): Boolean =
        (username == "admin" && password == "1234") ||
                (username == registeredUser && password == registeredPass)
}