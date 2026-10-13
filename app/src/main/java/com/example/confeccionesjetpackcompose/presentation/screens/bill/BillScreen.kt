import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlarmOn
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.confeccionesjetpackcompose.presentation.screens.bill.BillViewModel
import com.example.confeccionesjetpackcompose.utils.dataclass.Uniformes
import com.example.confeccionesjetpackcompose.data.database.viewmodel.UniformesDbViewModel
import com.example.confeccionesjetpackcompose.presentation.screens.bill.AlertDialogFinishBill
import com.example.confeccionesjetpackcompose.presentation.screens.bill.MenuDesplegableConOpciones
import com.example.confeccionesjetpackcompose.presentation.screens.bill.ProductsSinModifiers
import com.example.confeccionesjetpackcompose.presentation.screens.bill.dataCustomerRelevant
import com.example.confeccionesjetpackcompose.presentation.screens.bill.manualEntranceProduct
import com.example.confeccionesjetpackcompose.utils.createpdf.ApacheCreatePdf
import com.example.confeccionesjetpackcompose.utils.createpdf.ApacheCreatePdf58mm
import com.example.confeccionesjetpackcompose.utils.dataclass.DataCustomer
import com.example.confeccionesjetpackcompose.utils.dataclass.DataHeaderCustomer2
import com.example.confeccionesjetpackcompose.presentation.components.BackgroundImage
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

@Composable
fun ProductosScreen(navController: NavController, viewModel: BillViewModel = viewModel(), viewModeldD:UniformesDbViewModel) {
    val uniformesdb by viewModeldD.uniformesdb.collectAsState(emptyList())
    val myNavController = navController
    val productos by viewModel.productos

    val total = viewModel.calcularTotal()
    val apachePdfCreator = ApacheCreatePdf();
    val dataCustomerSee by viewModel.datacustomer
    // 1. **Estado** para controlar la visibilidad del diálogo numero 2
    var showDialog by remember { mutableStateOf(false) }
    var showDialogFinishBill by remember { mutableStateOf(false) }


    BackgroundImage {
        LazyColumn (contentPadding = PaddingValues(top = 10.dp))
        {

            item{
                MenuDesplegableConOpciones(uniformesdb=uniformesdb)
                manualEntranceProduct()
        }

        item{
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(Color(0xFF152042))
                    .border(
                        width = 2.dp,
                        color = Color(0xFFFFFFFF)
                    )
            ){
                Text(text="Nombre", modifier = Modifier.weight(0.18f), textAlign = TextAlign.Center, color = Color(0xFFFFFFFF))
                Text(text="T", modifier = Modifier.weight(0.05f), textAlign = TextAlign.Center, color = Color(0xFFFFFFFF))
                Text(text = "Prec U", modifier = Modifier.weight(0.14f), textAlign = TextAlign.Center, color = Color(0xFFFFFFFF))
                Text(text = "Cant", modifier = Modifier.weight(0.05f), textAlign = TextAlign.Center, color = Color(0xFFFFFFFF))
                Text(text = "+", modifier = Modifier.weight(0.05f), textAlign = TextAlign.Center, color = Color(0xFFFFFFFF))
                Text(text = "-", modifier = Modifier.weight(0.05f), textAlign = TextAlign.Center, color = Color(0xFFFFFFFF))
                Text(text = "Total", modifier = Modifier.weight(0.18f), textAlign = TextAlign.Center, color = Color(0xFFFFFFFF))
                Text(text = "Del", modifier = Modifier.weight(0.1f), textAlign = TextAlign.Center, color = Color(0xFFFFFFFF))
            }
        }

        items(productos) { item ->
            ListItemRow(
                item = item,
                onQuantityChange = viewModel::onQuantityChange,
                onDeleteItem = viewModel::onDeleteItem
            )
        }

        //funcion muestra el total de los productos
        item {
            if(total>0) {


                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    thickness = 5.dp,
                    color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface.copy(
                        alpha = 0.12f
                    )
                )
                Row(
                    modifier = Modifier
                        .height(60.dp)
                        .fillMaxWidth()
                        /*.padding( top = 5.dp,
                        bottom = 5.dp)*/
                        .background(Color(0xFF152042))
                    /* .border(
                        width = 2.dp,
                        color = Color.White
                        // shape = RoundedCornerShape(8.dp)  // Bordes redondeados opcional
                    )*/,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {

                    Text(
                        text = "TOTAL: $ ",
                        fontSize = 30.sp,
                        color = Color(0xFFFFFFFF)
                    )
                    Text(
                        text = "${formatNumberES(total)}",
                        fontSize = 30.sp,        // Tamaño de la letra en sp (scaled pixels)
                        color = Color.Red
                    )

                }
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    thickness = 5.dp,
                    color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface.copy(
                        alpha = 0.12f
                    )
                )
            }
        }

        //dos botones uno para llenar los datos del cliente y el siguiente  para crear la factura en pdf
        item {
            if(productos.isEmpty()){

            }else {


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
                ) {

                    var mostrarDialogo by remember { mutableStateOf(false) }

                    Button(
                        onClick = { mostrarDialogo = true },
                        modifier = Modifier
                            .height(50.dp)
                            .weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4693E0)),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Datos cliente", fontSize = 14.sp)
                    }
                    DialogoConInputs(
                        showDialog = mostrarDialogo,
                        onDismiss = { mostrarDialogo = false },
                        onConfirm = { nombre, idCard ->
                            showDialog=true
                        }
                    )

                    Button(
                        onClick = { showDialog = true },
                        modifier = Modifier
                            .height(50.dp)
                            .weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Description,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Generar Factura", fontSize = 14.sp)
                    }

                    Button(
                        onClick = { },
                        modifier = Modifier
                            .height(50.dp)
                            .weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800)),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Save,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Guardar Cotizacion", fontSize = 14.sp)
                    }

                    // 2. **Llamada condicional** a tu función AlertDialogExample
                    if (showDialog) {
                        AlertDialogExample(
                            dataCustomerSee,
                            myNavController,

                            // Función a llamar cuando se pide "Dismiss" (fuera del diálogo o botón Dismiss)
                            onDismissRequest = {
                                showDialog = false // Oculta el diálogo
                            },

                            // Función a llamar cuando se presiona el botón "Confirm"
                            onConfirmation = {
                                // AQUÍ VA LA LÓGICA DE LO QUE PASA AL CONFIRMAR (ej. guardar datos, etc.)
                                println("¡Acción confirmada!")
                                showDialogFinishBill=true
                                showDialog = false // Oculta el diálogo después de la acción

                            },
                            dialogTitle = "CONFIRMAR DATOS DE FACTURA",
                            dialogText = "¿Estás seguro de que deseas continuar con esta acción?",
                            icon = Icons.Default.AlarmOn // Usamos un ícono predefinido para el ejemplo,

                        )
                    }


                    /*
                    * bloque alert dialog cuando ya se crea el pdf
                    * y se manda el mensaje con el nombre del archivo
                    * creado
                    * */

                    val namePdfBill by viewModel.nameBillFile
                    if(showDialogFinishBill){
                        AlertDialogFinishBill(

                            // Función a llamar cuando se pide "Dismiss" (fuera del diálogo o botón Dismiss)
                            onDismissRequest = {
                                myNavController.navigate("screen3")
                                showDialogFinishBill = false // Oculta el diálogo
                            },

                            // Función a llamar cuando se presiona el botón "Confirm"
                            onConfirmation = {
                                // AQUÍ VA LA LÓGICA DE LO QUE PASA AL CONFIRMAR (ej. guardar datos, etc.)
                                println("¡Acción confirmada!")
                                myNavController.navigate("screen3")
                                showDialogFinishBill = false // Oculta el diálogo después de la acción

                            },
                            dialogTitle = "Archivo creado satisfactoriamente",
                            dialogText = namePdfBill,
                            icon = Icons.Default.AlarmOn // Usamos un ícono predefinido para el ejemplo,
                        )
                    }



                }


            }
        }
        /////////
        item {
            if(dataCustomerSee.name.isEmpty()){
            }else{
                dataCustomerRelevant(dataCustomerSee)
            }
        }

        }
    }
}


/*
* funcion para mostrar los productos
* con su nombre, precio talla, cantidad y to tal
* */
@Composable
fun ListItemRow(item: Uniformes, onQuantityChange: (Int, Int) -> Unit, onDeleteItem: (Int) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF152042))
            .border(
                width = 2.dp,
                color = Color(0xFFFFFFFF)
            )
    ){
        Text(text="${item.nameArticle}", modifier = Modifier.weight(0.18f), textAlign = TextAlign.Center, color = Color(0xFFFFFFFF))
        Text(text = "${item.size}", modifier = Modifier.weight(0.05f), textAlign = TextAlign.Center, color = Color(0xFFFFFFFF))
        Text(text="${item.price}", modifier = Modifier.weight(0.14f), textAlign = TextAlign.Center, color = Color(0xFFFFFFFF))
        Text(text = "${item.amount}", modifier = Modifier.weight(0.05f), textAlign = TextAlign.Center, color = Color(0xFFFFFFFF))

        Button(
            modifier = Modifier
                .height(25.dp)
                .width(25.dp),
            onClick = { onQuantityChange(item.id, item.amount + 1) },
            contentPadding = PaddingValues(1.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
        ) { Text("+") }

        Button(
            modifier = Modifier
                .height(25.dp)
                .width(25.dp),
            onClick = {
                if (item.amount > 1) onQuantityChange(item.id, item.amount - 1)
            },
            contentPadding = PaddingValues(1.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) { Text("-") }

        Text(text = "${item.total}", modifier = Modifier.weight(0.18f), textAlign = TextAlign.Center, color = Color(0xFFFFFFFF))

        Button(
            modifier = Modifier
                .clip(RoundedCornerShape(0.dp))
                .height(30.dp)
                .width(30.dp),
            onClick = { onDeleteItem(item.id) },
            contentPadding = PaddingValues(1.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Delete, contentDescription = "Eliminar",
                modifier = Modifier.size(30.dp)
            )
        }
    }
}



//alert para mostrar llenar los datos del cliente
//nombre, idcard,  telefono etc
@Composable
fun DialogoConInputs(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (String, String) -> Unit,
    viewModel: BillViewModel = viewModel()
) {

    if (showDialog) {
        var nombre by remember { mutableStateOf("") }
        var idCard by remember { mutableStateOf("") }
        var address by remember { mutableStateOf("") }
        var cellNumber by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var aclaration by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = Color(0xFF4693E0),
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Ingrese sus datos",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center
                    )
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    TextField(
                        value = nombre,
                        onValueChange = { nombre = it },
                        label = { Text("Nombre") },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = Color(0xFF4693E0)) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    TextField(
                        value = idCard,
                        onValueChange = { idCard = it },
                        label = { Text("ID Card") },
                        leadingIcon = { Icon(Icons.Default.CreditCard, contentDescription = null, tint = Color(0xFF4CAF50)) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    TextField(
                        value = address,
                        onValueChange = { address = it },
                        label = { Text("Direccion") },
                        leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null, tint = Color(0xFFFF9800)) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    TextField(
                        value = cellNumber,
                        onValueChange = { cellNumber = it },
                        label = { Text("Telefono") },
                        leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null, tint = Color(0xFF9C27B0)) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    TextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = Color(0xFFE91E63)) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    TextField(
                        value = aclaration,
                        onValueChange = { aclaration = it },
                        label = { Text("Aclaraciones") },
                        leadingIcon = { Icon(Icons.Default.Info, contentDescription = null, tint = Color(0xFF607D8B)) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val dataCustomerInputDialog = DataCustomer(
                            name = nombre,
                            cardId = idCard,
                            address = address,
                            cellNumber = cellNumber,
                            email = email
                        )
                        viewModel.loadDataCustomer(dataCustomerInputDialog)
                        onConfirm(nombre, idCard)
                        onDismiss()
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                ) {
                    Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Confirmar")
                }
            },
            dismissButton = {
                Button(
                    onClick = onDismiss,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53935)),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                ) {
                    Icon(Icons.Default.Close, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Cancelar")
                }
            }
        )
    }
}


@Composable
fun AlertDialogExample(
    dataCustomer: DataCustomer,
    MynavController: NavController,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
    viewModel: BillViewModel = viewModel(),

) {

    val productos by viewModel.productos
    val datacustomer by viewModel.datacustomer

    val totalSpacesDataCustomer = countNumberItemsCustomer(dataCustomer)
    val countCellsProducts = productos.size
    val cellHeigh = 7f

    val heighPage58mm = (countCellsProducts * cellHeigh) + 30f + (totalSpacesDataCustomer*cellHeigh)

    val coroutineScope = rememberCoroutineScope()
    val estadoTerminacion = remember { mutableStateOf(false) }
    val apachePdfCreator = ApacheCreatePdf();
    val apachePdfCreator58 = ApacheCreatePdf58mm(58f, heighPage58mm);

    AlertDialog(
        icon = {
            Icon(
                icon,
                contentDescription = null,
                tint = Color(0xFF4693E0),
                modifier = Modifier.size(40.dp)
            )
        },
        title = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = dialogTitle,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    color = Color(0xFF1A1A1A)
                )
            }
        },
        text = {
            ProductsSinModifiers(productos, dataCustomer)
        },
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            Button(
                onClick = {
                    onConfirmation()
                    coroutineScope.launch {
                        apachePdfCreator.headerPdfLocal(datacustomer)
                        val nameFileEnd = apachePdfCreator.crearPDFConTabla(productos)
                        viewModel.loadNameBillFile(nameFileEnd)
                        apachePdfCreator58.headerPdfLocal(datacustomer, productos)
                    }
                    estadoTerminacion.value = true
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
            ) {
                Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Crear Factura")
            }
        },
        dismissButton = {
            Button(
                onClick = { onDismissRequest() },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53935)),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
            ) {
                Icon(Icons.Default.Close, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Cancelar")
            }
        }
    )
}




fun formatNumberES(numero: Double): String {
    val symbols = DecimalFormatSymbols(Locale("es", "ES"))
    val formatter = DecimalFormat("#,###.00", symbols)
    return formatter.format(numero.toDouble())
}

fun countNumberItemsCustomer(dataCustomer: DataCustomer): Int{
    val datos4 = listOf(
        DataHeaderCustomer2("Nombre: ", dataCustomer.name),
        DataHeaderCustomer2("Ident: ", dataCustomer.cardId),
        DataHeaderCustomer2("Direccion : ", dataCustomer.address),
        DataHeaderCustomer2("Telefono: ", dataCustomer.cellNumber),
        DataHeaderCustomer2("Email : ", dataCustomer.email),
    )
    var listaCustomerFinal = mutableListOf<DataHeaderCustomer2>()

    for(persona in datos4 ){
        if(persona.secondElement.isBlank()){

        }else{
            val customerTemporal = DataHeaderCustomer2(persona.firstElement, persona.secondElement)
            listaCustomerFinal.add(customerTemporal)
        }
    }

    return listaCustomerFinal.size
}