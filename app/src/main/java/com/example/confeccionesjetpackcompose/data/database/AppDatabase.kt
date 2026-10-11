package com.example.confeccionesjetpackcompose.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.confeccionesjetpackcompose.data.database.dao.UniformesDbDao
import com.example.confeccionesjetpackcompose.data.database.entities.UniformesDb

@Database(entities = [UniformesDb::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun uniformesDbDao(): UniformesDbDao
}


