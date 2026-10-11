package com.example.confeccionesjetpackcompose.data.database.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.confeccionesjetpackcompose.data.database.entities.UniformesDb
import com.example.confeccionesjetpackcompose.data.database.repository.UniformesDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UniformesDbViewModel @Inject constructor(
    private val uniformesDbRepository: UniformesDbRepository
): ViewModel() {

    val uniformesdb: Flow<List<UniformesDb>> = uniformesDbRepository.getUniformes()

    fun insertUniformesDbList(listUniformesDb: List<UniformesDb>) = viewModelScope.launch {
        uniformesDbRepository.insertUniformesList(listUniformesDb)
    }

    fun insertUniformesDb(uniformesDb: UniformesDb) = viewModelScope.launch {
        uniformesDbRepository.insertUniformesDb(uniformesDb)
    }

    fun updateUniformesDb(uniformesDb: UniformesDb) = viewModelScope.launch {
        uniformesDbRepository.updateUniforme(uniformesDb)
    }

    fun deleteUniformesDb(uniformesDb: UniformesDb) = viewModelScope.launch {
        uniformesDbRepository.deleteUniforme(uniformesDb)
    }
}