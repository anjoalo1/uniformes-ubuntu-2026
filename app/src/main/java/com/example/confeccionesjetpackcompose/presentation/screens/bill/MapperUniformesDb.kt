package com.example.confeccionesjetpackcompose.presentation.screens.bill

import com.example.confeccionesjetpackcompose.data.database.entities.UniformesDb
import com.example.confeccionesjetpackcompose.utils.dataclass.Uniformes

class MapperUniformesDb() {


    fun toUniformes(uniformesDb: List<UniformesDb>): List<Uniformes> {
        val listaUniformesModel: List<Uniformes> = uniformesDb.map { dbItem ->
            Uniformes(
                id = dbItem.id,
                nameArticle = dbItem.nameArticle,
                size = dbItem.size,
                price = dbItem.price,
                amount = dbItem.amount) }

        return listaUniformesModel
    }


}