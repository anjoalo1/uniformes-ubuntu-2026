package com.example.confeccionesjetpackcompose.presentation.screens.scaffold

import ProductosScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.SnackbarHostState

import  androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

/**********************/

import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.confeccionesjetpackcompose.data.database.viewmodel.UniformesDbViewModel
import com.example.confeccionesjetpackcompose.presentation.screens.billsucessfull.BillSuccessfull
import com.example.confeccionesjetpackcompose.presentation.screens.databaseadmin.ListUniformesDb
//import androidx.wear.compose.material3.IconButton
import com.example.confeccionesjetpackcompose.presentation.screens.home.MyHome

import com.example.confeccionesjetpackcompose.presentation.screens.scaffold.ScaffoldWithSnackBar
import com.example.confeccionesjetpackcompose.presentation.screens.startview.StartMenu


import kotlinx.coroutines.launch






@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldWithSnackBar() {
    var isMenuOpen  by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()


    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    var currentScreenTitle by remember { mutableStateOf("FACTURACION") }


    currentScreenTitle = when(currentRoute) {
        "start" -> "Inicio"
        "screen1"->"FACTURACION"
        "screen2"->"MY HOME"
        "screen3"->"BILL END"
        "screen4" -> "PRECIO BASE"
        else -> "Facturacion"

    }

 /*   val currentScreenTitle = when(currentRoute) {
        "start" -> "Inicio"
        "screen4" -> "PRECIO BASE"
        else -> "Facturacion"
    }*/

    /*
    *
    *  when(currentRoute) {
            "start" -> "Inicio"
            "screen1"->"FACTURACION"
            "screen2"->"MY HOME"
            "screen3"->"BILL END"
            "screen4" -> "PRECIO BASE"
            else -> "Facturacion"
        }
    * */


    Scaffold(



        topBar = {
            TopAppBar(

                colors = topAppBarColors(
                    //containerColor = MaterialTheme.colorScheme.primaryContainer,
                    containerColor = Color(0xFF4693E0),
                    titleContentColor = Color(0xFF4693E0),

                    ),
                    title = { Text(currentScreenTitle, color = Color(0xFF000000)) },
                    actions = {
                    IconButton(onClick = { isMenuOpen = true }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menú")
                    }
                    DropdownMenu(
                        expanded = isMenuOpen,
                        onDismissRequest = { isMenuOpen = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("ADMIN BD")},
                            onClick = { /* Acción Opción 1 */ isMenuOpen = false
                                navController.navigate("screen4"){
                                    launchSingleTop = true
                                }
                                //currentScreenTitle = "PRECIO BASE"
                            }
                        )


                        DropdownMenuItem(
                            text = {Text("START")},
                            onClick = { /* Acción Opción 2 */ isMenuOpen = false
                                navController.navigate("START"){
                                    launchSingleTop = true
                                }
                                //currentScreenTitle = "PRECIO BASE"
                            }
                        )
                    }
                }
            )
        },

        bottomBar = {
            BottomAppBar(
                containerColor = Color(0xFF4693E0),
                contentColor = Color(0xFF4693E0),
            ) {

                NavigationBar {
                    NavigationBarItem(
                        selected = currentScreenTitle == "Facturacion",
                        onClick = { navController.navigate("screen1"){
                            launchSingleTop = true
                                    }
                                    //currentScreenTitle = "Facturacion"
                                  },
                        label = { Text("Facturacion") },
                        icon = { Icon(Icons.Default.Home, contentDescription = null) }
                    )
                    // Otros items...
                    NavigationBarItem(
                        selected = false,
                        onClick = { navController.navigate("screen2"){
                            launchSingleTop = true
                        }
                           // currentScreenTitle = "MY HOME"
                                  },
                        label = { Text("Screen 2") },
                        icon = { Icon(Icons.Default.Settings, contentDescription = null) }
                    )
                }
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },



        content = { paddingValues ->

            NavHost(
                navController = navController,
                startDestination = "start",
                Modifier.padding(paddingValues)
            ) {
                composable("start"){ StartMenu(navController) }
                composable("screen1") {
                    val viewModeldD: UniformesDbViewModel = hiltViewModel()
                    ProductosScreen(navController, viewModeldD = viewModeldD)
                }

                composable("screen2") { MyHome() }
                composable( "screen3"){ BillSuccessfull(navController) }
                composable ("screen4"){
                    val viewModeldD: UniformesDbViewModel = hiltViewModel()
                    ListUniformesDb(navController, viewModeldD = viewModeldD)
                }
            }
        }
    )
}