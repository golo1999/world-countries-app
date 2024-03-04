package com.example.worldcountriesapp.di

import android.app.Application
import android.content.Context
import com.example.worldcountriesapp.repository.CountryRepository
import com.example.worldcountriesapp.repository.CountryRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideApplicationContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideCountryRepository(countryRepositoryImpl: CountryRepositoryImpl): CountryRepository {
        return countryRepositoryImpl
    }
}