package com.mnc.network.service

import com.mnc.network.model.Home
import com.mnc.network.response.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface HomeService {

    @GET("home")
    suspend fun getHomeData(@Header("Authorization") token: String): Response<Home>

}