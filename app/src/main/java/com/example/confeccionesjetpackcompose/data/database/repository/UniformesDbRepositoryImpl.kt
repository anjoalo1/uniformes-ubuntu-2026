package com.example.confeccionesjetpackcompose.data.database.repository

import com.example.confeccionesjetpackcompose.data.database.dao.UniformesDbDao
import com.example.confeccionesjetpackcompose.data.database.entities.UniformesDb
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UniformesDbRepositoryImpl @Inject constructor(
    private val uniformesDbDao: UniformesDbDao
) : UniformesDbRepository {
    override fun getUniformes(): Flow<List<UniformesDb>> {
        return uniformesDbDao.getUniformes()
    }

    override suspend fun insertUniformesList(uniformesList: List<UniformesDb>) {
       uniformesDbDao.insertUniformesDbList(uniformesList)
    }

    override suspend fun insertUniformesDb(uniformeDb: UniformesDb) {
        uniformesDbDao.insertUniformesDb(uniformeDb)
    }

    override suspend fun updateUniforme(uniformeDb: UniformesDb) {
        uniformesDbDao.updateUniformesDb(uniformeDb)
    }

    override suspend fun deleteUniforme(uniformeDb: UniformesDb) {
        uniformesDbDao.deleteUniformesDb(uniformeDb)
    }
}