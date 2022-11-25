package com.mnc.app.di.module

import android.app.Application
import android.content.Context
import com.mnc.app.shared.AppPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideContext(app: Application): Context {
        return app
    }

    @Singleton
    @Provides
    fun provideAppPreferences(context: Context): AppPreferences {
        return AppPreferences(context)
    }

}