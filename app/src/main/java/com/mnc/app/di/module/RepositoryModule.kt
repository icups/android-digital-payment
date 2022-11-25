package com.mnc.app.di.module

import com.mnc.app.repository.AuthRepository
import com.mnc.app.repository.HomeRepository
import com.mnc.app.shared.AppPreferences
import com.mnc.network.service.AuthService
import com.mnc.network.service.HomeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @ViewModelScoped
    @Provides
    fun provideAuthRepository(service: AuthService, preferences: AppPreferences): AuthRepository {
        return AuthRepository(service, preferences)
    }

    @ViewModelScoped
    @Provides
    fun provideHomeRepository(service: HomeService, preferences: AppPreferences): HomeRepository {
        return HomeRepository(service, preferences)
    }

}