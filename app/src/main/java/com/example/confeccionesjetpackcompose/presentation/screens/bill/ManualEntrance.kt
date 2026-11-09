package com.example.confeccionesjetpackcompose.presentation.screens.bill

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.confeccionesjetpackcompose.utils.CalculaFechas
import com.example.confeccionesjetpackcompose.utils.dataclass.Uniformes


/*
* funcion para definir manualmente un producto
*
* */


@Composable
fun manualEntranceProduct(viewModel: BillViewModel = viewModel()){
    val colorLetter = Color(0xFF000000)
    var nameArticle by remember { mutableStateOf("") }
    var size by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var nameError by rememberSaveable { mutableStateOf(false) }
    var sizeError by rememberSaveable { mutableStateOf(false) }
    var showDialogInputsProducts by remember { mutableStateOf(false) }
    ExtendedFloatingActionButton(onClick = { showDialogInputsProducts = true },

        icon = { Icon(Icons.Filled.Add, contentDescription = "Icono") },
        text = { Text(text = "Ingresar Manualmente") },
        // 1. Configura la forma (Shape) igual que en tu botón
        shape = RoundedCornerShape(10.dp),
        // 2. Usa FloatingActionButtonDefaults para asignar el color de fondo
        containerColor = Color(0xFFE53935),
        // 3. Usa FloatingActionButtonDefaults para la elevación
        elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 4.dp)

    )

    if(showDialogInputsProducts){



        AlertDialog(
            onDismissRequest = { /* cerrar dialogo */ },
            title = { Text("Agregar uniforme") },
            text = {
                Column {
                    // Aquí van los TextField y demás componentes como en tu código
                    TextField(
                        value = nameArticle,
                        onValueChange = {
                            nameArticle = it
                            nameError = it.isNullOrEmpty()
                        },
                        label = { Text("Nombre") },
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = Color.Red,   // 👈 Color cuando el usuario está escribiendo
                            unfocusedTextColor = Color.Black  // 👈 Color cuando el campo no está seleccionado
                        )
                    )

                    if (nameError) {
                        Text("⚠️ Debes ingresar un nombre", color = Color.Red)
                    }
                    TextField(
                        value = size,
                        onValueChange = {
                            size = it
                            sizeError = it.isNullOrEmpty()
                        },
                        label = { Text("Talla") },
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = Color.Red,   // 👈 Color cuando el usuario está escribiendo
                            unfocusedTextColor = Color.Black  // 👈 Color cuando el campo no está seleccionado
                        )
                    )
                    if(sizeError){
                        Text("⚠️ Debes ingresar un size", color = Color.Red)
                    }

                    TextField(
                        value = price,
                        onValueChange = { input ->
                            if (input.matches(Regex("^(-)?\\d+(\\.\\d{1,2})?$"))) { // Permite solo números y punto decimal
                                price = input
                            }
                        },
                        label = { Text("Precio Unidad") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = Color.Red,   // 👈 Color cuando el usuario está escribiendo
                            unfocusedTextColor = Color.Black  // 👈 Color cuando el campo no está seleccionado
                        )
                    )


                    TextField(
                        value = amount,
                        onValueChange = { newValue ->
                            if (newValue.matches(Regex("^\\d*\$"))) { // Solo dígitos permitidos
                                amount = newValue
                            }
                        },
                        label = { Text("Cantidad") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = Color.Red,   // 👈 Color cuando el usuario está escribiendo
                            unfocusedTextColor = Color.Black  // 👈 Color cuando el campo no está seleccionado
                        )
                    )


                    //Button(onClick = {}) { Text("Add Uniforme") }
                }
            },
            confirmButton = {
                Button(onClick = {
                    // lógica para insertar uniforme
                    if(price.toDoubleOrNull() == null || amount.toIntOrNull()== null){

                    }else{
                        val calculaFechas = CalculaFechas().obtenerNewFecha2().takeLast(4)
                        val concatenado = calculaFechas.joinToString(separator = "").toInt()
                        val uniformeManual = Uniformes(id= concatenado, nameArticle= nameArticle, size=size, price= price.toDouble(), amount = amount.toInt())
                        viewModel.addOrUpdateUniforme(uniformeManual)
                    }
                },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                ) {
                    Text("Add Uniforme")
                }
            },
            dismissButton = {
                Button(onClick = {
                    /* cerrar dialogo */
                    showDialogInputsProducts = false

                },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53935)),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                ) {
                    Text("Cancelar")
                }
            }
        )

    }
}