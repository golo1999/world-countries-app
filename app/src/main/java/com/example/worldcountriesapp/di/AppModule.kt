package com.example.worldcountriesapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.worldcountriesapp.data.dao.CountryDao
import com.example.worldcountriesapp.data.dao.RegionDao
import com.example.worldcountriesapp.data.database.AppDatabase
import com.example.worldcountriesapp.repository.CountryRepository
import com.example.worldcountriesapp.repository.CountryRepositoryImpl
import com.example.worldcountriesapp.repository.RegionRepository
import com.example.worldcountriesapp.repository.RegionRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideCountryDao(roomDatabase: AppDatabase): CountryDao {
        return roomDatabase.countryDao()
    }

    @Provides
    @Singleton
    fun provideCountryRepository(countryRepositoryImpl: CountryRepositoryImpl): CountryRepository {
        return countryRepositoryImpl
    }

    @Provides
    @Singleton
    fun provideRegionDao(roomDatabase: AppDatabase): RegionDao {
        return roomDatabase.regionDao()
    }

    @Provides
    @Singleton
    fun provideRegionRepository(regionRepositoryImpl: RegionRepositoryImpl): RegionRepository {
        return regionRepositoryImpl
    }

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext applicationContext: Context): AppDatabase {
        return Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "APP_DATABASE"
        ).build()
    }
}