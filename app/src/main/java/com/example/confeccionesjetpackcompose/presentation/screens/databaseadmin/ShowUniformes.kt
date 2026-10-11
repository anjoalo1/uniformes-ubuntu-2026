package com.example.confeccionesjetpackcompose.presentation.screens.databaseadmin


import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ModeEdit
import androidx.compose.material3.Icon
import androidx.compose.material3.Text

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember

import androidx.compose.ui.draw.clip

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.confeccionesjetpackcompose.data.database.entities.UniformesDb
import com.example.confeccionesjetpackcompose.data.database.viewmodel.UniformesDbViewModel
import com.example.confeccionesjetpackcompose.presentation.components.BackgroundImage
import java.io.File


import android.app.Activity
import android.content.Intent

import android.os.Environment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton


import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext





@Composable
fun ListUniformesDb(navController: NavController, viewModeldD:UniformesDbViewModel) {

    val uniformesdb by viewModeldD.uniformesdb.collectAsState(emptyList())
    val viewModel: UniformesDbViewModel = hiltViewModel()

    BackgroundImage {
        LazyColumn {
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(text = "NOMBRE", modifier = Modifier.weight(0.10f))
                    Text(text = "PRECIO",  modifier = Modifier.weight(0.10f))
                    Text(text = "SIZE", modifier = Modifier.weight(0.05f), textAlign = TextAlign.Center)
                    Text(text = "CANT", modifier = Modifier.weight(0.05f), textAlign = TextAlign.Center)
                    Text(text = "EDIT", modifier = Modifier.weight(0.08f), textAlign = TextAlign.Center)
                    Text(text = "ELIM", modifier = Modifier.weight(0.08f), textAlign = TextAlign.Center)
                }
            }


            items(uniformesdb){uniforme->
                UniformesItem(uniforme = uniforme, onDeleteUniformesDb = {uniformesdbToDelete->
                    viewModel.deleteUniformesDb(uniformesdbToDelete) },
                onEditUniformesDb = {uniformesDbToEdit -> viewModel.updateUniformesDb(uniformesDbToEdit)}
            )

            }

            item { AddUniformes() }

            item {
                CsvFilePickerButton { file ->
                    val datos = readCsv(file)
                    viewModel.insertUniformesDbList(datos);
                }
            }

        }
    }
}

//funcion para insertar un elemento nuevo a la base de datos
@Composable
fun AddUniformes(viewModel: UniformesDbViewModel= hiltViewModel()){
    var nameArticle by remember { mutableStateOf("") }
    var size by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var nameError by rememberSaveable { mutableStateOf(false) }
    var sizeError by rememberSaveable { mutableStateOf(false) }
    var showDialogInputsProducts by remember { mutableStateOf(false) }
    Column {

        ExtendedFloatingActionButton(onClick = { showDialogInputsProducts = true }) {
            //Text("Mostrar diálogo")
            Icon(Icons.Filled.Add, "add product.")

            Text(text = "Add Uniform")
        }
        if (showDialogInputsProducts) {
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
                            label = { Text("Nombre") }
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
                            label = { Text("Talla") }
                        )
                        if(sizeError){
                            Text("⚠️ Debes ingresar un size", color = Color.Red)
                        }

                        TextField(
                            value = price,
                            onValueChange = { input ->
                                if (input.matches(Regex("^\\d*\\.?\\d*\$"))) { // Permite solo números y punto decimal
                                    price = input
                                }
                            },
                            label = { Text("Precio Unidad") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )


                        TextField(
                            value = amount,
                            onValueChange = { newValue ->
                                if (newValue.matches(Regex("^\\d*\$"))) { // Solo dígitos permitidos
                                    amount = newValue
                                }
                            },
                            label = { Text("Cantidad") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )


                        Button(onClick = {


                        }) {
                            Text("Add Uniforme")
                        }
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        // lógica para insertar uniforme
                        if(price.toDoubleOrNull() == null || amount.toIntOrNull()== null){

                        }else{
                            viewModel.insertUniformesDb(UniformesDb(nameArticle=nameArticle, size = size, price= price.toDouble(), amount = amount.toInt()))

                        }
                    }) {
                        Text("Add Uniforme")
                    }
                },
                dismissButton = {
                    Button(onClick = {
                    /* cerrar dialogo */
                        showDialogInputsProducts = false
                    }) {
                        Text("Cancelar")
                    }
                }
            )
        }



        //////
    }
}


@Composable
fun UniformesItem(uniforme: UniformesDb, onDeleteUniformesDb:(UniformesDb)->Unit, onEditUniformesDb:(UniformesDb)->Unit){

    var showDeleteDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }
    var editedName by remember { mutableStateOf(uniforme.nameArticle) }
    var editedSize by remember { mutableStateOf(uniforme.size) }
    var editedPrice by remember { mutableDoubleStateOf(uniforme.price) }
    var editedPriceText by remember { mutableStateOf(editedPrice.toString()) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(1.dp),
        //.background(Color(0xFF603680)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(text = "${uniforme.nameArticle}", modifier = Modifier.weight(0.10f))
        Text(text = "${uniforme.price}", modifier = Modifier.weight(0.10f))
        Text(text = "${uniforme.size} ", modifier = Modifier.weight(0.05f), textAlign = TextAlign.Left)
        Text(text = "${uniforme.amount} ", modifier = Modifier.weight(0.05f), textAlign = TextAlign.Left)

        Button(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(0.dp))
                .height(30.dp) // Establece una altura de 20 dp
                .width(30.dp), // Establece una anchura de 30 dp,

            onClick = { showEditDialog = true },
            contentPadding = PaddingValues(1.dp)
        ) {
            Row {
                Icon(
                    imageVector = Icons.Filled.ModeEdit, contentDescription = "Modifier",
                    modifier = Modifier.size(30.dp)
                )
            }
        }


        Button(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(0.dp))
                .height(30.dp) // Establece una altura de 20 dp
                .width(30.dp), // Establece una anchura de 30 dp,
            onClick = { showDeleteDialog = true },
            contentPadding = PaddingValues(1.dp)
        ) {
            Row {
                Icon(
                    imageVector = Icons.Filled.Delete, contentDescription = "Delete",
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }


    if (showDeleteDialog) {

        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Confirmación") },
            //text = { Text("¿Estás seguro de que quieres eliminar este Elemento?") },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text("¿Estás seguro de que quieres eliminar este Elemento?")
                    Text("Una vez eliminado, no se podra recuperar")
                }
            },

            confirmButton = {
                Button(
                    onClick = {
                        onDeleteUniformesDb(uniforme)
                        showDeleteDialog = false
                    }
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    if (showEditDialog) {
        AlertDialog(
            onDismissRequest = { showEditDialog = false },
            title = { Text("Editar Uniforme") },
            text = {
                Column {
                    Text("Nombre Articulo:")
                    TextField(value = editedName, onValueChange = { editedName = it })
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Size:")
                    TextField(value = editedSize, onValueChange = { editedSize = it })
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Precio:")
                    TextField(
                        value = editedPriceText,
                        onValueChange = { newText ->
                            editedPriceText = newText
                            val newDouble = newText.toDoubleOrNull()
                            if (newDouble != null) {
                                editedPrice = newDouble
                            }
                        }
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onEditUniformesDb(uniforme.copy(nameArticle = editedName, size = editedSize, price= editedPriceText.toDouble(), amount = 1))
                        showEditDialog = false
                    }
                ) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

}


fun readCsv(file: File): List<UniformesDb> {
    val result = mutableListOf<UniformesDb>()
    file.bufferedReader().useLines { lines ->
        val iterator = lines.iterator()
        if (iterator.hasNext()) iterator.next() // Saltar encabezado
        iterator.forEach { line ->
            val parts = line.split(",")
            if (parts.size == 4) {
                val dataObject = UniformesDb(
                    nameArticle = parts[0].trim(),
                    size = parts[1].trim(),
                    price = parts[2].trim().toDouble(),
                    amount = parts[3].trim().toInt()
                )
                result.add(dataObject)
            }
        }
    }
    println(result)
    return result
}



@Composable
fun CsvFilePickerButton(onFilePicked: (File) -> Unit) {
    val context = LocalContext.current
    val fileUriState = remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            fileUriState.value = it
            val file = uriToFile(context, it)
            if (file != null) {
                onFilePicked(file)
            }
        }
    }

    Button(onClick = {
        // Abre el selector de archivos para archivos tipo CSV
        launcher.launch("*/*")
    }) {
        Text("Seleccionar archivo CSV")
    }
}

// Función auxiliar para convertir Uri a File
fun uriToFile(context: android.content.Context, uri: Uri): File? {
    // Puedes usar un método apropiado para convertir Uri a File.
    // Este puede variar según versión Android y permisos.
    // Como ejemplo simple puedes copiar el contenido del URI a un archivo temporal:
    return try {
        val inputStream = context.contentResolver.openInputStream(uri)
        val tempFile = File.createTempFile("temp", ".csv", context.cacheDir)
        inputStream?.use { input ->
            tempFile.outputStream().use { output ->
                input.copyTo(output)
            }
        }
        tempFile
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}


