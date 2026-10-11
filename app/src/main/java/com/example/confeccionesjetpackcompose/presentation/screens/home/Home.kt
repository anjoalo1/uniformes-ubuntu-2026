package com.example.confeccionesjetpackcompose.presentation.screens.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.confeccionesjetpackcompose.presentation.components.BackgroundImage

@Composable
fun MyHome(){
    BackgroundImage {
        Text(text = "Hola home")
    }
}