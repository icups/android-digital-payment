package com.mnc.network.service

import com.mnc.network.model.Auth
import com.mnc.network.payload.Payload
import com.mnc.network.response.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthService {

    @POST("auth/login")
    suspend fun signIn(@Body payload: Payload): Response<Auth>

}