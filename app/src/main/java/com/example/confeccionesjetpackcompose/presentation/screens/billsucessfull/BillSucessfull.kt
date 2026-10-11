package com.example.confeccionesjetpackcompose.presentation.screens.billsucessfull

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.confeccionesjetpackcompose.presentation.components.BackgroundImage


@Composable
fun BillSuccessfull(navController: NavController){
    BackgroundImage {
        Column(modifier = Modifier.padding(16.dp)) {
            Spacer(modifier = Modifier.height(40.dp))
            Text(text = "Bill Successfull")
            Spacer(modifier = Modifier.height(16.dp))
            TextButton(onClick = {
                navController.navigate("START") {}
            }) {
                Text("Volver al inicio")
            }
        }
    }
}