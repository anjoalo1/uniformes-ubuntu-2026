package com.example.confeccionesjetpackcompose.utils.dataclass

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class NameNavigation(
    val nameNavigacion: String,
    val nameDestinationNavigate: String,
    val icon: ImageVector,
    val color: Color
)
