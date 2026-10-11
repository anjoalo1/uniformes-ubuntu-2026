package com.example.confeccionesjetpackcompose.data.database.di

import android.content.Context
import androidx.room.Room
import com.example.confeccionesjetpackcompose.data.database.AppDatabase
import com.example.confeccionesjetpackcompose.data.database.dao.UniformesDbDao
import com.example.confeccionesjetpackcompose.data.database.repository.UniformesDbRepository
import com.example.confeccionesjetpackcompose.data.database.repository.UniformesDbRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDao(database: AppDatabase): UniformesDbDao {
        return database.uniformesDbDao()
    }

    @Provides
    @Singleton
    fun provideRepository(dao: UniformesDbDao): UniformesDbRepository {
        return UniformesDbRepositoryImpl(dao)
    }
}
