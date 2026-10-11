package com.example.confeccionesjetpackcompose.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.confeccionesjetpackcompose.data.database.entities.UniformesDb
import kotlinx.coroutines.flow.Flow

@Dao
interface UniformesDbDao {
    @Query("SELECT * FROM uniformestable")
    fun getUniformes(): Flow<List<UniformesDb>>

    @Insert
    suspend fun insertUniformesDbList(uniformesList: List<UniformesDb>)

    @Insert
    suspend fun insertUniformesDb(uniformesDb: UniformesDb)

    @Update
    suspend fun updateUniformesDb(uniformesDb: UniformesDb)

    @Delete
    suspend fun deleteUniformesDb(uniformesDb: UniformesDb)
}