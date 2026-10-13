package com.example.confeccionesjetpackcompose.presentation.screens.bill


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ModeEdit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Transgender
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.confeccionesjetpackcompose.R
import com.example.confeccionesjetpackcompose.data.database.entities.UniformesDb
import com.example.confeccionesjetpackcompose.utils.CalculaFechas
import com.example.confeccionesjetpackcompose.utils.dataclass.DataCustomer
import com.example.confeccionesjetpackcompose.utils.dataclass.Uniformes
import com.example.confeccionesjetpackcompose.utils.dataclass.dataUniformes
import com.example.confeccionesjetpackcompose.utils.dataclass.sizesUniformes
import kotlin.collections.forEach



//menu para cargar diferentes tipos de busqueda
//ya sea por talla unica
//tallas diferentes
@Composable
fun MenuDesplegableConOpciones(uniformesdb: List<UniformesDb>) {

    val uniformesdbpasar = uniformesdb;
    var expanded by remember { mutableStateOf(false) }
    var opcionSeleccionada by remember { mutableStateOf<String?>(null) }

    Box {
        IconButton(onClick = { expanded = true }) {
            //Text(text = opcionSeleccionada ?: "Selecciona una opción")
            Icon(Icons.Default.Menu, contentDescription = "Menú")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem({Text("Talla Unica")},onClick = {
                opcionSeleccionada = "Opción A"
                expanded = false
            })


            DropdownMenuItem({  Text("Tallas diferentes")},onClick = {
                opcionSeleccionada = "Opción B"
                expanded = false
            })


            DropdownMenuItem({  Text("Por Colegio")},onClick = {
                opcionSeleccionada = "Opción C"
                expanded = false
            })
            // Agrega más opciones si quieres
        }
    }

    when (opcionSeleccionada) {
        "Opción A" -> SelectSizeProduct(uniformesdbpasar)
        "Opción B" -> dropDownNameArticcle(uniformesdbpasar)
        "Opción C" -> SearchBySchool(uniformesdbpasar)
        // Agrega más casos según las opciones
    }
}


//funcion para seleccionar talla(size) y genero(gender)

@Composable
fun SelectSizeProduct(uniformesdbpasar: List<UniformesDb>, viewModel: BillViewModel = viewModel()){

    //variables para seleccionar la talla
    var expanded1 by remember { mutableStateOf(false) }
    var selectedSize: String by remember { mutableStateOf("") }
    //variables para seleccionar gender
    var expanded2 by remember { mutableStateOf(false) }
    var selectedGender: String by remember { mutableStateOf("") }


    //menu para seleccionar talla
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(onClick = { expanded1 = true }) {
                    // Icon(Icons.Default.base, contentDescription = "Person")
                    Icon(painter = painterResource(id = R.drawable.baseline_format_list_numbered_24), contentDescription = "null")

                }
                Text(text = selectedSize,
                    color = Color(0xFF000000)
                )
            }
            DropdownMenu(expanded = expanded1, onDismissRequest = { expanded1 = false }) {
                sizesUniformes.forEach { sizeItem ->
                    DropdownMenuItem(
                        text = { Text(sizeItem.size) },
                        onClick = {
                            selectedSize = sizeItem.size
                            expanded1 = false
                        }
                    )
                }
            }
        }


        //menu para seleccionar genero
        Box {

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(onClick = { expanded2 = true }) {
                    Icon(
                        imageVector = Icons.Filled.Transgender, contentDescription = "Modifier",
                        modifier = Modifier.size(30.dp)
                    )
                }
                Text(text = selectedGender,
                        color = Color(0xFF000000)
                )
            }


            DropdownMenu(
                expanded = expanded2,
                onDismissRequest = { expanded2 = false }
            ) {
                DropdownMenuItem(
                    { Text(text = "Masculino") },
                    onClick = {
                        selectedGender = "male"
                        expanded2 = false
                    }
                )
                DropdownMenuItem(
                    { Text(text = "Femenino") },
                    onClick = {
                        selectedGender = "female"
                        expanded2 = false
                    }
                )


            }
        }

        //boton para realizar la busqueda
        Button(
            modifier = Modifier
                .clip(RoundedCornerShape(0.dp))
                .height(30.dp) // Establece una altura de 20 dp
                .width(70.dp), // Establece una anchura de 30 dp,
            onClick = {
                if(selectedSize == "" || selectedGender==""){

                }else{
                    val searchUniformes = viewModel.searchUniformesSize(uniformesdbpasar, selectedSize, selectedGender)
                }
            },
            contentPadding = PaddingValues(1.dp)
        ){Text("Search")}
    }

}



//esta funcion busca por nombre de articulo
//si selecciona camibuso busca los camibusos con sus diferentes tallas
//asi con los demas
@Composable
fun dropDownNameArticcle(uniformesdbpasar: List<UniformesDb>, viewModel: BillViewModel = viewModel()){
    var expanded1 by remember { mutableStateOf(false) }
    var selectedSize: String by remember { mutableStateOf("") }

    var expanded2 by remember { mutableStateOf(false) }
    var selectedGender: String by remember { mutableStateOf("") }

    val mapperUNiformesDb = MapperUniformesDb()
    //tranforma una List<UniformesDb> a List<Uniformes>
    val uniformesDbToUniformes = mapperUNiformesDb.toUniformes(uniformesdbpasar);
    val searchUniformesByNameArticle = uniformesDbToUniformes.map { it.nameArticle }.distinct()

   // val searchUniformesByNameArticle = viewModel.searchNameArticleArray()
    // Filtramos prendas/variedades o tallas para el segundo dropdown basándonos en el artículo seleccionado
    val searchUniformesBySizeArticle = if (selectedSize.isNotEmpty()) {
        uniformesDbToUniformes.filter { it.nameArticle == selectedSize }.map { it.size }
        //viewModel.searchSizerArticleArray(selectedSize) // Debes implementar esta función para filtrar por artículo
    } else {
        emptyList()
    }


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {


        Box {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(onClick = { expanded1 = true }) {
                    // Icon(Icons.Default.base, contentDescription = "Person")
                    Icon(
                        imageVector = Icons.Filled.Checkroom, contentDescription = "Modifier",
                        modifier = Modifier.size(30.dp)
                    )

                }
                Text(text = selectedSize)
            }
            DropdownMenu(expanded = expanded1, onDismissRequest = { expanded1 = false }) {
                searchUniformesByNameArticle.forEach { nameArticle ->
                    DropdownMenuItem(
                        text = { Text(nameArticle) },
                        onClick = {
                            selectedSize = nameArticle
                            expanded1 = false
                        }
                    )
                }
            }
        }

        //end box

        Box {

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(onClick = { expanded2 = true }) {
                    Icon(
                        imageVector = Icons.Filled.FormatListNumbered, contentDescription = "Modifier",
                        modifier = Modifier.size(30.dp)
                    )
                }
                Text(text = selectedGender)
            }


            DropdownMenu(expanded = expanded2, onDismissRequest = { expanded2 = false }) {
                searchUniformesBySizeArticle.forEach { sizeArticle ->
                    DropdownMenuItem(
                        text = { Text(sizeArticle) },
                        onClick = {
                            selectedGender = sizeArticle
                            expanded2 = false
                        }
                    )
                }
            }
        }
        //end box


        //boton para realizar la busqueda
        Button(
            modifier = Modifier
                .clip(RoundedCornerShape(0.dp))
                .height(30.dp) // Establece una altura de 20 dp
                .width(70.dp), // Establece una anchura de 30 dp,
            onClick = {
                if(selectedSize == "" || selectedGender==""){

                }else{
                    println("$selectedSize y:   $selectedGender")



                    val addUniforme = uniformesDbToUniformes.firstOrNull({ it.nameArticle == selectedSize && it.size == selectedGender })
                    if(addUniforme==null){
                        //println(addUniforme)
                    }else{
                        val searchUniformes = viewModel.addOrUpdateUniforme(addUniforme)
                    }

                }
            },
            contentPadding = PaddingValues(1.dp)
        ){Text("Search")}
    }

}


/*
* fucion para buscar unicamente por 1 solo colegio
* */

@Composable
fun SearchBySchool(uniformesdbpasar: List<UniformesDb>, viewModel: BillViewModel = viewModel()){
    var expanded1 by remember { mutableStateOf(false) }
    var selectedSchool: String by remember { mutableStateOf("") }

    var expanded2 by remember { mutableStateOf(false) }
    var selectedSize: String by remember { mutableStateOf("") }

    var expanded3 by remember { mutableStateOf(false) }
    var selectedGender: String by remember { mutableStateOf("") }

    val schoolNameArray = arrayOf("Milagrosa", "Cervantes")

    val mapperUNiformesDb = MapperUniformesDb()
    //tranforma una List<UniformesDb> a List<Uniformes>
    val uniformesDbToUniformes = mapperUNiformesDb.toUniformes(uniformesdbpasar);


    val searchUniformesBySizeArticle = if (selectedSize.isNotEmpty() && selectedSchool.isNotEmpty() && selectedSchool =="Milagrosa") {
       val uniformeOnlyGirl = uniformesDbToUniformes.filter { (it.nameArticle == "camibuso" || it.nameArticle == "chaqueta" || it.nameArticle == "sudadera"
                || it.nameArticle == "jardinera" ) && it.size == selectedSize }
        //viewModel.searchSizerArticleArray(selectedSize) // Debes implementar esta función para filtrar por artículo
        uniformeOnlyGirl
    }else if(selectedSize.isNotEmpty() && selectedSchool.isNotEmpty() && selectedSchool !="Milagrosa"){
        val uniformeBoy = uniformesDbToUniformes.filter { (it.nameArticle == "camibuso" || it.nameArticle == "chaqueta" || it.nameArticle == "sudadera"
                || it.nameArticle == "pantalon" ) && it.size == selectedSize }
        uniformeBoy
    }

    else {
        emptyList()
    }


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        //box para seleccionar el colegio
        Box{
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(onClick = { expanded1 = true }) {
                    //Icon(Icons.Default.Person, contentDescription = "Person")
                    Icon(
                        imageVector = Icons.Filled.School, contentDescription = "Modifier",
                        modifier = Modifier.size(30.dp)
                    )
                }
                Text(text = selectedSchool)
            }

            DropdownMenu(expanded = expanded1, onDismissRequest = { expanded1 = false }) {
                schoolNameArray.forEach { nameSchool ->
                    DropdownMenuItem(
                        text = { Text(nameSchool) },
                        onClick = {
                            selectedSchool = nameSchool
                            expanded1 = false
                        }
                    )
                }
            }


        }


        //box para seleccionar size
        Box {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(onClick = { expanded2 = true }) {
                    // Icon(Icons.Default.base, contentDescription = "Person")
                    Icon(painter = painterResource(id = R.drawable.baseline_format_list_numbered_24), contentDescription = "null")

                }
                Text(text = selectedSize)
            }
            DropdownMenu(expanded = expanded2, onDismissRequest = { expanded2 = false }) {
                sizesUniformes.forEach { sizeItem ->
                    DropdownMenuItem(
                        text = { Text(sizeItem.size) },
                        onClick = {
                            selectedSize = sizeItem.size
                            expanded2 = false
                        }
                    )
                }
            }
        }

        Button(
            modifier = Modifier
                .clip(RoundedCornerShape(0.dp))
                .height(30.dp) // Establece una altura de 20 dp
                .width(70.dp), // Establece una anchura de 30 dp,
            onClick = {
                println(searchUniformesBySizeArticle)

                val addUniformeBySchool = viewModel.addUniformeBySchool(searchUniformesBySizeArticle)
            },
            contentPadding = PaddingValues(1.dp)
        ){Text("Search")}

    }
}





/*funcion para mostrar los datos del cliente
* abajo del los productos
* */
@Composable
fun dataCustomerRelevant(dataCustomerSee: DataCustomer){
    Column {
        Text(text = "Data Customer")
        Text(text = "Nombre: ${dataCustomerSee.name}")
        Text(text = "Identificación: ${dataCustomerSee.cardId}")
        Text(text = "Direccion: ${dataCustomerSee.address}")
        Text(text = "Teléfono: ${dataCustomerSee.cellNumber}")
        Text(text = "Email: ${dataCustomerSee.email}")
    }
}


//funcion que despues de crear la factura
//lleva a una pantalla final de compra

@Composable
fun AlertDialogFinishBill(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}


/*
* funcion para mostrar los productos en el alert dialog
* antes de crear el pdf, a modo de confimacion si los
* datos son correctos antes de crear el pdf
* */


@Composable
fun ProductsSinModifiers(productos: List<Uniformes>, myCustomer: DataCustomer){
    val scrollState = rememberScrollState()


    Column(modifier = Modifier.fillMaxWidth()
        .verticalScroll(scrollState)
    ) {
        // Header de productos
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF152042))
                .padding(vertical = 8.dp)
        ){
            Text(text="Articulo", modifier = Modifier.weight(0.2f), color = Color.White, fontWeight = FontWeight.Bold)
            Text(text="Precio", modifier = Modifier.weight(0.14f), color = Color.White, fontWeight = FontWeight.Bold)
            Text(text = "Talla", modifier = Modifier.weight(0.1f), textAlign = TextAlign.Center, color = Color.White, fontWeight = FontWeight.Bold)
            Text(text = "Cant", modifier = Modifier.weight(0.1f), textAlign = TextAlign.Center, color = Color.White, fontWeight = FontWeight.Bold)
            Text(text = "Total", modifier = Modifier.weight(0.18f), textAlign = TextAlign.Center, color = Color.White, fontWeight = FontWeight.Bold)
        }

        // Filas de productos
        productos.forEach { uniforme->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ){
                Text(text="${uniforme.nameArticle}", modifier = Modifier.weight(0.2f))
                Text(text="${uniforme.price}", modifier = Modifier.weight(0.14f))
                Text(text = "${uniforme.size}", modifier = Modifier.weight(0.1f), textAlign = TextAlign.Center)
                Text(text = "${uniforme.amount}", modifier = Modifier.weight(0.1f), textAlign = TextAlign.Center)
                Text(text = "${uniforme.total}", modifier = Modifier.weight(0.18f), textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
            }
        }

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
            thickness = 2.dp,
            color = Color(0xFF4693E0)
        )

        // Total
        val total: Double = productos.sumOf { it.total }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF152042))
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ){
            if(total<=0)
            {
                Text(text = "No hay productos", color = Color.White)
            }else{
                Text(text = "TOTAL: \$ ${total}",
                    fontSize = 18.sp,
                    color = Color(0xFFFFEB3B),
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Sección datos del cliente
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF4693E0))
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "DATOS DEL CLIENTE",
                fontSize = 16.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Campos del cliente con iconos
        if(myCustomer.name.isNotEmpty()){
            Row(modifier = Modifier.padding(vertical = 2.dp)){
                Icon(Icons.Default.Person, contentDescription = null, tint = Color(0xFF4693E0), modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Nombre: ${myCustomer.name}")
            }
        }
        if(myCustomer.cardId.isNotEmpty()){
            Row(modifier = Modifier.padding(vertical = 2.dp)){
                Icon(Icons.Default.CreditCard, contentDescription = null, tint = Color(0xFF4CAF50), modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "ID: ${myCustomer.cardId}")
            }
        }
        if(myCustomer.address.isNotEmpty()){
            Row(modifier = Modifier.padding(vertical = 2.dp)){
                Icon(Icons.Default.LocationOn, contentDescription = null, tint = Color(0xFFFF9800), modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Direccion: ${myCustomer.address}")
            }
        }
        if(myCustomer.cellNumber.isNotEmpty()){
            Row(modifier = Modifier.padding(vertical = 2.dp)){
                Icon(Icons.Default.Phone, contentDescription = null, tint = Color(0xFF9C27B0), modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Telefono: ${myCustomer.cellNumber}")
            }
        }
        if(myCustomer.email.isNotEmpty()){
            Row(modifier = Modifier.padding(vertical = 2.dp)){
                Icon(Icons.Default.Email, contentDescription = null, tint = Color(0xFFE91E63), modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Email: ${myCustomer.email}")
            }
        }
    }

}


@Composable
fun searchUniformByGenderAndSize(uniformesdbpasar: List<UniformesDb>, size: String, gender: String ){


    val mapperUNiformesDb = MapperUniformesDb()
    val uniformesDbToUniformes = mapperUNiformesDb.toUniformes(uniformesdbpasar);



    if(gender=="female"){
        val filterUniformes = uniformesDbToUniformes.filter{ (it.nameArticle == "camibuso" || it.nameArticle == "chaqueta" || it.nameArticle == "sudadera"
                || it.nameArticle == "falda" || it.nameArticle == "pantaloneta") && it.size == size }
    }

}


/*
* funcion para definir manualmente un producto
*
* */
@Composable
fun manualEntranceProduct(viewModel: BillViewModel = viewModel()){

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
                        if (input.matches(Regex("^(-)?\\d+(\\.\\d{1,2})?$"))) { // Permite solo números y punto decimal
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