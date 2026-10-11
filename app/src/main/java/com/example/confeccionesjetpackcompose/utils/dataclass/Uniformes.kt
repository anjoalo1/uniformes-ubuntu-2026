package com.example.confeccionesjetpackcompose.utils.dataclass

data class Uniformes(
    val id:Int,
    val nameArticle: String,
    var size:String,
    var price: Double,
    var amount: Int
)
{
    val total: Double // Total calculado
        get() = amount * price // Calcula el total
}

data class Size(
    val size:String
)
