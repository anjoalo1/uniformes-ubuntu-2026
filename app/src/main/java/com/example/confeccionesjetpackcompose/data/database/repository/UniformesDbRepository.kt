package com.example.confeccionesjetpackcompose.data.database.repository

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.confeccionesjetpackcompose.data.database.entities.UniformesDb
import kotlinx.coroutines.flow.Flow

interface UniformesDbRepository {
    fun getUniformes(): Flow<List<UniformesDb>>
    suspend fun insertUniformesList(uniformesList: List<UniformesDb>)
    suspend fun insertUniformesDb(uniformeDb: UniformesDb)
    suspend fun updateUniforme(uniformeDb: UniformesDb)
    suspend fun deleteUniforme(uniformeDb: UniformesDb)
}