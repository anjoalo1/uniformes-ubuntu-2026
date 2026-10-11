package com.example.confeccionesjetpackcompose.presentation.screens.bill

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.example.confeccionesjetpackcompose.data.database.entities.UniformesDb
import com.example.confeccionesjetpackcompose.utils.dataclass.DataCustomer
import com.example.confeccionesjetpackcompose.utils.dataclass.Uniformes
import com.example.confeccionesjetpackcompose.utils.dataclass.dataUniformes

class BillViewModel : ViewModel(){

    // Estado mutable interno de la lista
    private var _productos = mutableStateOf<List<Uniformes>>(listOf())
    val productos: State<List<Uniformes>> = _productos

    private var _datacustomer = mutableStateOf(DataCustomer("","","","",""))
    val datacustomer: State<DataCustomer> = _datacustomer

    private var _nameBillFile = mutableStateOf<String>("")
    val nameBillFile: State<String> = _nameBillFile

    fun loadNameBillFile(nameFile: String){
        _nameBillFile.value = nameFile
    }

    fun loadDataCustomer(dataCustomerInit: DataCustomer){
        _datacustomer.value = dataCustomerInit;
    }

    fun onQuantityChange(id: Int, newCantidad: Int) {
        _productos.value = _productos.value.map {
            if (it.id == id) it.copy(amount = newCantidad) else it
        }
    }

    fun onDeleteItem(id: Int) {
        _productos.value = _productos.value.filter { it.id != id }
    }

    // Funcion para cargar la lista inicial si se quiere
    fun loadProductos(productosIniciales: List<Uniformes>) {
        _productos.value = productosIniciales
    }

    //Funcion para calcular el total
    fun calcularTotal(): Double {
        return _productos.value.sumOf { it.total }
    }

    //Funcion para cargar list con uniformes por talla y genero
    fun searchUniformesSize(uniformesdbpasar: List<UniformesDb>, size: String, gender: String){

        val mapperUNiformesDb = MapperUniformesDb()
        val uniformesDbToUniformes = mapperUNiformesDb.toUniformes(uniformesdbpasar);


        var filterUniformes = listOf<Uniformes>()
        if(gender=="female"){
            filterUniformes= uniformesDbToUniformes.filter { (it.nameArticle == "camibuso" || it.nameArticle == "chaqueta" || it.nameArticle == "sudadera"
                    || it.nameArticle == "falda" || it.nameArticle == "pantaloneta") && it.size == size }
            //loadProductos(filterUniformes)
            filterUniformes.forEach { article->
                addOrUpdateUniforme(article)
            }

        }
        if(gender=="male"){
            filterUniformes= uniformesDbToUniformes.filter { (it.nameArticle == "camibuso" || it.nameArticle == "chaqueta" || it.nameArticle == "sudadera"
                    || it.nameArticle == "pantalon" || it.nameArticle == "pantaloneta") && it.size == size }
            println(filterUniformes)
            filterUniformes.forEach { article->
                addOrUpdateUniforme(article)
            }
            //loadProductos(filterUniformes)
        }
    }

    //funcion para sacar en un array el nameArticle pero sin repeticiones solo tipo de nombre sin repetir
    fun searchNameArticleArray():List<String>{
        val distinctNameArticles = dataUniformes.map { it.nameArticle }.distinct()
        return distinctNameArticles
    }

    //funcion para sacar en un las tallas dependiendo de la prenda elegida
    fun searchSizerArticleArray(nameArticle: String):List<String>{
        val sizerArticles = dataUniformes.filter { it.nameArticle == nameArticle }.map { it.size }
        return sizerArticles
    }


    //Funcion para cargar list con uniformes por tipo de colegio

    fun addUniformeBySchool(uniformesPasar: List<Uniformes>){
        uniformesPasar.forEach { article->
            addOrUpdateUniforme(article)
        }
    }

    //funcion para buscar si existe un elemento en la lista
    fun addOrUpdateUniforme(article: Uniformes) {

       // val index = _productos.value.firstOrNull({ it.nameArticle == article.nameArticle && it.size == article.size })
        val index = _productos.value.firstOrNull({ it.id == article.id })
        if (index == null) {
            //si el elemento no existe agrega como nuevo articulo a la lista
            /*val addUniforme =
                dataUniformes.first({ it.nameArticle === nameArticle && it.size == sizeArticle })
                */

            val currentList = _productos.value.toMutableList()
            currentList.add(article)

            _productos.value = currentList
        } else {
            //si existe sacamos el id y aumentamos en 1
            val saveIndex = index.id
            val amountProducts = index.amount
            _productos.value = _productos.value.map {
                if (it.id == saveIndex) it.copy(amount = amountProducts + 1) else it
            }

        }
    }

}