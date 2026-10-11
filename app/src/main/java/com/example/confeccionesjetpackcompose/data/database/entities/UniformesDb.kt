package com.example.confeccionesjetpackcompose.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="uniformestable")
data class UniformesDb(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nameArticle: String,
    val size: String,
    val price: Double,
    val amount: Int

)
