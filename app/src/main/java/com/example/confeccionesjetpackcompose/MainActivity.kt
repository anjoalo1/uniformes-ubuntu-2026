package com.example.confeccionesjetpackcompose


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.confeccionesjetpackcompose.presentation.screens.scaffold.ScaffoldWithSnackBar
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PDFBoxResourceLoader.init(applicationContext)
        enableEdgeToEdge()
        setContent {
            ScaffoldWithSnackBar()
        }
    }
}

/*probando git*/
@Preview
@Composable
fun MostrarScaffold(){
    ScaffoldWithSnackBar()
}