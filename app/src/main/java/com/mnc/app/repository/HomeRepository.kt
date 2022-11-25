package com.mnc.app.repository

import com.mnc.app.shared.AppPreferences
import com.mnc.network.model.Home
import com.mnc.network.response.Response
import com.mnc.network.service.HomeService
import javax.inject.Inject

class HomeRepository @Inject constructor(private val remoteDataSource: HomeService, private val preferences: AppPreferences) {

    suspend fun getHomeData(): Response<Home> {
        return remoteDataSource.getHomeData(preferences.accessToken)
    }

}