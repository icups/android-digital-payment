package com.mnc.app.repository

import com.mnc.app.shared.AppPreferences
import com.mnc.network.model.Auth
import com.mnc.network.payload.Payload
import com.mnc.network.response.Response
import com.mnc.network.service.AuthService
import javax.inject.Inject

class AuthRepository @Inject constructor(private val remoteDataSource: AuthService, private val preferences: AppPreferences) {

    suspend fun signIn(email: String, password: String): Response<Auth> {
        return remoteDataSource.signIn(Payload(email, password))
    }

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    fun storeSession(data: Auth) {
        preferences.storeSession(data)
    }

}